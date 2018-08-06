package net.techgy.uidef;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.techgy.uidef.annotation.QueryCondition;
import net.techgy.uidef.annotation.Req;
import net.techgy.uidef.annotation.ReqItems;

public class UIDefUtils {

	private static final UIDefUtils instance = new UIDefUtils();
	private UIDefUtils() {}
	public static UIDefUtils getInstance(){return instance;}
	
	private Map<String,ReqDef> nameToReqdefs = new HashMap<String,ReqDef>();
	
	public ReqDef getReqDef(Class<?> cls) {
		if(cls == null) {
			throw new NullPointerException();
		}
		return this.getReqDef(cls.getName());
	}
	
    public ReqDef getReqDef(String clsName) {
    	if(clsName == null) {
			throw new NullPointerException();
		}
    	ReqDef def = this.nameToReqdefs.get(clsName);
    	if(null == def) {
    		try {
    			Class<?> clsIns = UIDefUtils.class.getClassLoader().loadClass(clsName);
    			def = parseReqDef(clsIns);
    			if(null != def) {
    				this.nameToReqdefs.put(clsName, def);
    			}
    		} catch (ClassNotFoundException e) {
    			throw new RuntimeException("getReqDef(String clsName)",e);
    		}
    	}
		
		return def;
	}
    
    public ReqDef parseReqDef(Class<?> cls) {
    	if(cls == null) {
			throw new NullPointerException();
		}
    	if(!cls.isAnnotationPresent(Req.class)) {
    		throw new RuntimeException("Clas is not Annotate with @Req: " + cls.getName());
    	}
    	Req reqAnno = cls.getAnnotation(Req.class);    	
    	ReqDef def = new ReqDef();
    	def.setName(reqAnno.name());
    	
    	List<ReqQueryDef> qeuryDef = new ArrayList<ReqQueryDef>();
    	for(QueryCondition qc: reqAnno.queryConditions()) {
    		ReqQueryDef qd = new ReqQueryDef();
    		qeuryDef.add(qd);
    		qd.setName(qc.name());
    		qd.setAvailables(qc.availables());
    		qd.setDataType(qc.dataType());
    		qd.setDefaultValue(qc.defaultValue());
    		qd.setLength(qc.length());
    		qd.setMaxValue(qc.maxValue());
    		qd.setMinValue(qc.minValue());
    		qd.setUiType(qc.uiType());
    		
    	}
    	def.setQeuryDef(qeuryDef);
    	
    	//request header fields
    	List<FieldDef> hfds = new ArrayList<FieldDef>();
    	Field[] fs = cls.getDeclaredFields();
    	for(Field f : fs) {
    		if(!f.isAnnotationPresent(net.techgy.uidef.annotation.Field.class)) {
    			continue;
    		}
    		net.techgy.uidef.annotation.Field rf = f.getAnnotation(net.techgy.uidef.annotation.Field.class);
    		FieldDef hfd = new FieldDef();
    		hfds.add(hfd);
    		hfd.setName(rf.name());
    		hfd.setOrder(rf.order());
    		
    	}
    	def.setHeaderFields(hfds);
    	
    	//Items
    	Field[] fields = cls.getDeclaredFields();
    	Field reqItemsField = null;
    	for(Field f : fields) {
    		if(f.isAnnotationPresent(ReqItems.class)) {
    			reqItemsField = f;
    			break;
    		}
    	}
    	Class<?> fieldType = reqItemsField.getType();
    	if(null != reqItemsField && (List.class.isAssignableFrom(fieldType)
    			|| Set.class.isAssignableFrom(fieldType)
    			|| Array.class.isAssignableFrom(fieldType) )) {
    		
    		ParameterizedType pt = (ParameterizedType)reqItemsField.getGenericType();
    		if(pt != null) {
    			Type[] types = pt.getActualTypeArguments();
    			if(types != null) {
    				Class<?> itemCls = (Class<?>)types[0];
    				ReqItemHeaderDef hd = this.parseReqItem(itemCls);
    				def.setItemDef(hd);
    			}
    		}
    	}
		return def;
	}
    
    public ReqItemHeaderDef parseReqItem(Class<?> itemCls) {
    	if(null == itemCls) {
    		throw new NullPointerException();
    	}
    	if(!itemCls.isAnnotationPresent(ReqItems.class)) {
    		throw new RuntimeException("Clas is not Annotated with @ReqItems: " + itemCls.getName()); 
    	}
    	
    	List<FieldDef> hs = new ArrayList<FieldDef>();
    	Field[] fs = itemCls.getDeclaredFields();
    	for(Field f: fs) {
    		if(f.isAnnotationPresent(net.techgy.uidef.annotation.Field.class)) {
    			net.techgy.uidef.annotation.Field c = f.getAnnotation(net.techgy.uidef.annotation.Field.class);
    			FieldDef d = new FieldDef();
    			d.setName(c.name());
    			d.setOrder(c.order());
    			hs.add(d);
    		}
    	}
    	ReqItemHeaderDef headerDef = new ReqItemHeaderDef();
    	headerDef.setHeaders(hs);
		return headerDef;
		
	}
}
