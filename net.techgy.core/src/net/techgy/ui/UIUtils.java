package net.techgy.ui;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.techgy.ui.manager.VId;

import org.apache.log4j.Logger;


public class UIUtils {

private static UIUtils instance = new UIUtils();

    private Logger logger = Logger.getLogger(UIUtils.class);    

	private UIUtils() {};
	
	public static UIUtils getInstance() {
		return instance;
	}	
	
	public <T> UITable getTable(Class<T> cls, List<T> vos) {
		checkWithThrow(cls);		
		UITable table = getTableFormat(cls);
		table.setRows(getRows(cls,vos));
		return table;
	}
	
	public <T> UITable getTableFormat(Class<T> cls) {
		checkWithThrow(cls);
		UITable table = new UITable();
		table.setTableName(getTableName(cls));
		table.setVodef(this.getVODefinition(cls,null));
		return table;
	}
	
	public<T> void checkWithThrow(Class<T> cls) {
		if(!isAnnotedWith(cls,UI.class)){
			 throw new RuntimeException(cls.getName() +" is not a UI class");
		}
	}
	
	public <T> List<UIRow> getRows(Class<T> cls, List<T> vos) {
		List<UIRow>  rows = new ArrayList<UIRow>();
		List<VODefinition> lds = getVODefinition(cls,null);
		for(Object vo : vos) {
			UIRow r = this.getRow(vo,lds);
			if(null != r) {
				rows.add(r);
			}
		}
		return rows;
	}
	
	public String getStringValue(Object obj,Field field) throws IllegalArgumentException, IllegalAccessException {
		String value = null;
		Class objCls = field.getType();		
		String fName = field.getType().getName();
		if("byte".equals(fName) || "short".equals(fName) ||"int".equals(fName) ||"long".equals(fName) ||
				"float".equals(fName) ||"double".equals(fName) ||"boolean".equals(fName) ||"java.lang.String".equals(fName)) {
			return field.get(obj).toString();
		}
		if(Byte.class == objCls || Short.class== objCls ||
				Integer.class== objCls || Long.class== objCls ||
				Float.class== objCls || Double.class== objCls ||
				String.class== objCls || StringBuffer.class== objCls||
				StringBuilder.class== objCls || Boolean.class== objCls) {
			return field.get(obj).toString();
		}
		
		if(!field.isAnnotationPresent(UIQuery.class)) {
			throw new RuntimeException(obj.getClass().getName()+"."+field.getName() 
					+ " must be annotated with " + UIQuery.class.getName());
		}
		Object fieldObj = field.get(obj);
		if(null == fieldObj) {
			return null;
		}
		UIQuery annoUIValue = field.getAnnotation(UIQuery.class);
		String valueSeperator = annoUIValue.valueSeperator();
		String eltSeperator = annoUIValue.elementSeperator();
		String[] fieldNames = annoUIValue.uiValueFields();
		Object[] objs = null;
		
		if(Collection.class.isAssignableFrom(fieldObj.getClass())) {
			objs = ((Collection) fieldObj).toArray();
		}else if(obj.getClass().isArray()) {
			objs = (Object[])fieldObj;
		}else {
			objs = new Object[]{fieldObj};
		}
		
		String arrvs = "";
		for(int outIndex = 0; outIndex < objs.length ; outIndex++) {
			String ovs = "";
			Field[] ojbFields = objs[outIndex].getClass().getDeclaredFields();
			for(int innerIndex = 0; innerIndex < ojbFields.length ; innerIndex++) {
				ojbFields[innerIndex].setAccessible(true);
				for(String name: fieldNames){
					if(ojbFields[innerIndex].getName().equals(name)) {
						ovs += getStringValue(objs[outIndex],ojbFields[innerIndex]);
						if(innerIndex < objs.length-1) {
							ovs += valueSeperator;
						}	
					}
				}
			}
			arrvs += ovs;
			if(outIndex < objs.length-1) {
				arrvs += eltSeperator;
			}
			
		}
		value = arrvs;
		return value;
	}
	
	private Field getFieldByVODef(Object vo,VODefinition vod) {
		Field[] fields = vo.getClass().getDeclaredFields();
		for(Field f : fields) {
                if(vod.getFieldName().equals(f.getName())) {
                	return f;
                }
		}
		return null;
	}
	
	public <T> UIRow getRow(Object vo,List<VODefinition> lds) {
		UIRow  row = new UIRow();
		for(VODefinition vod: lds) {
			Field f = this.getFieldByVODef(vo, vod);
			UICell cell = new UICell();
			try {
				f.setAccessible(true);
				String value = getStringValue(vo,f);
				cell.setValue(value);
				cell.setColumnSeq(vod.getQueryDef().getSeq());
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			row.getCells().add(cell);
		}
		Collections.sort(row.getCells());
		return row;
	}
	
	
	public<T> boolean isAnnotedWith(Class<T> cls, Class annoCls) {
		Annotation[] annos = cls.getAnnotations();;
		return null != annos;
	}
	
	public<T> String getTableName(Class<T> cls) {
		String name = null;
		AnnotatedElement ae = cls;
		UI ui = ae.getAnnotation(UI.class);
		if(null == ui) {
			return null;
		}
		name = ui.name();
		if(null == name || "".equals(name.trim())) {
			name = cls.getSimpleName();
		}
		return name;
	}
	
	public List<VODefinition> getVODefinition(Class cls,Map<String,List<VODefinition>> defs) {
		AnnotatedElement ae = cls;
		UI ui = ae.getAnnotation(UI.class);
		if(null == ui) {
			return null;
		}
		if(null == defs) {
			defs = new HashMap<String,List<VODefinition>>();
		}
		List<VODefinition> headers = new ArrayList<VODefinition>();
		Field[] fields = cls.getDeclaredFields();
		int seq = 0;
		List<Integer> uiseqs = new ArrayList<Integer>();
		for(Field f : fields) {
			UIQuery q = f.getAnnotation(UIQuery.class);
			if(null != q && q.seq() > 0) {
				uiseqs.add(q.seq());
			}
		}
		
		for(Field f : fields) {
			if(!f.isAnnotationPresent(UIElement.class)){
				continue;
			}
			VODefinition h = new VODefinition();
			
			VId vid = f.getAnnotation(VId.class);
			if(vid != null) {
				h.setAbleId(true);
			}
			
			UIQuery qry = f.getAnnotation(UIQuery.class);
			h.setFieldName(f.getName());		
			QueryDef qdf = h.getQueryDef();
			if(null == qry){
				qdf.setUnvisible(false);
				qdf.setDisplayName(f.getName());
				seq = getNextSeq(uiseqs,seq);
				qdf.setSeq(seq);
				qdf.setElementSeperator(",");
				qdf.setResKey(f.getName());
				qdf.setUiValueFields(new String[0]);
			} else {
				qdf.setUnvisible(qry.unvisible());
				String dn = qry.displayName();
				if("".equals(dn.trim())) {
					dn = f.getName();
				}				
				qdf.setDisplayName(dn);
				Integer q = qry.seq();
				if(-1 == q) {
					q = seq = getNextSeq(uiseqs,seq);
				}
				qdf.setSeq(q);
				qdf.setElementSeperator(qry.elementSeperator());
				qdf.setResKey(qry.resKey());
				qdf.setUiValueFields(qry.uiValueFields());
			}
			
			UICreated uc = f.getAnnotation(UICreated.class);
			CreatedDef cd = h.getCreatedDef();
			if(null == uc) {
				cd.setUiType(UIConstants.UI_TYPE_TEXT);
				cd.setNotCreated(false);
				cd.setDefValue("");
				cd.setValues(null);
			} else {
				cd.setUiType(uc.uiType());
				cd.setNotCreated(uc.notCreated());
				cd.setDefValue(uc.defValue());
				cd.setValues(uc.values());
			}
			
			UIUpdate ud = f.getAnnotation(UIUpdate.class);
			UpdateDef udf = h.getUpdateDef();
			if(null == ud) {
				
			} else {
				
			}
			
			UIRemove ue = f.getAnnotation(UIRemove.class);
			RemoveDef rd = h.getRemoveDef();
			if(null == ue) {
				
			} else {
				
			}
			
			h.setFieldName(f.getName());
			Class fieldType = f.getType();
			if(Collection.class.isAssignableFrom(fieldType) || fieldType.isArray()) {
				h.setFieldType(UIConstants.JAVA_TYPE_LIST);
			} else if(Map.class.isAssignableFrom(fieldType)) {
				h.setFieldType(UIConstants.JAVA_TYPE_MAP);
			} else {
				h.setFieldType(UIConstants.JAVA_TYPE_DEFAULT);
			}
			
			UIElement uiElt = f.getAnnotation(UIElement.class);
			if(null != uiElt && uiElt.valueCls() != null && uiElt.valueCls() != Void.class 
					&& uiElt.valueCls() != void.class) {
				h.setFieldValueClsName(uiElt.valueCls().getName());
			} else {
				h.setFieldValueClsName(f.getType().getName());
			}
			
			if(null != uiElt && uiElt.valueCls() != null && uiElt.valueCls() != Void.class 
			   && uiElt.valueCls() != void.class) {
				if(uiElt.valueCls().isAnnotationPresent(UI.class)) {
					h.setComplexIns(true);
				}else {
					h.setComplexIns(false);
				}
			} else {
				h.setComplexIns(false);
			}
			
		
			headers.add(h);
		}
		Collections.sort(headers);
		defs.put(cls.getName(), headers);
		return headers;
	}
	
	private List<VODefinition> getVodefForLoop(Map<String,List<VODefinition>> defs,String clsName) {
		 Set<String> clses = defs.keySet();		
		for(String c : clses) {
			if(c.equals(clsName)) {
				return defs.get(c);
			}
		}
		return null;
	}
	
	private int getNextSeq(List<Integer> uiids,int currSeq) {
		int seq = ++currSeq;
		for(; uiids.contains(seq); ++seq) ;
		return seq;
	}
	
	public Object getELValue(String el,Object obj) {
       Object value = obj;
       String[] fs = null;
       if(el.indexOf(".") >0) {
    	   fs = el.trim().split("\\.");
       }else {
    	   fs = new String[]{el};
       }
       
       for(String fn : fs) {
			String methodName  = fn.substring(0,1).toUpperCase();
			String sm = fn.substring(1,fn.length());
			methodName = "get"+methodName + sm;
			Method method = null;
			try {
				method = value.getClass().getMethod(methodName);
				value = method.invoke(value);
			} catch (Exception e) {
				throw new RuntimeException("methodName not found for class:" + obj.getClass().getName());
			} 	
       }
       return value;
	}
	
}
