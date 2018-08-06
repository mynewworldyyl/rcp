package com.digitnexus.core.uidef.service;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.BaseDef;
import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.TableViewerEditorDef;
import com.digitnexus.base.uidef.TreeViewerEditorDef;
import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.ViewerDef;
import com.digitnexus.core.auth.IAuthorization;
import com.digitnexus.core.provider.DefaultValueProvider;
import com.digitnexus.core.provider.IKeyValueProvider;
import com.digitnexus.core.provider.ITreeNodeProvider;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.Children;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.ListItem;
import com.digitnexus.core.uidef.annotation.NamedParam;
import com.digitnexus.core.uidef.annotation.Parent;
import com.digitnexus.core.uidef.annotation.TableViewEditor;
import com.digitnexus.core.uidef.annotation.TreeViewEditor;

@Component
@Scope("singleton")
public class UIDefManager {

	@Value("#{configProperties['deploy.cluster']}")
	private boolean  isCluster;
	
	@Autowired
	private IAuthorization author;
	
	//private Map<String,BaseDef> nameToReqdefs = new HashMap<String,BaseDef>();
	
	private Map<String,Class> nameToClass = new HashMap<String,Class>();
	
	public BaseDef getDef(Class<?> cls) {
		return this.getDef(cls.getName());
	}
	
    public ViewerDef getDef(String clsName) {
    	if(clsName == null) {
			throw new NullPointerException();
		}
    	ViewerDef def = null;
    	Class<?> clsIns = nameToClass.get(clsName);
		try {
			if(clsIns == null) {
	    		clsIns = UIDefManager.class.getClassLoader().loadClass(clsName);
	    		if(clsIns == null) {
		    		throw new CommonException("ClassNotFound",clsName);
		    	}
	    		nameToClass.put(clsName, clsIns);
	    	}
	    	
			//必须是TableViewEditor或TreeViewEditor的注解的类
			if(!clsIns.isAnnotationPresent(TableViewEditor.class) && !clsIns.isAnnotationPresent(TreeViewEditor.class)) {
	    		throw new RuntimeException("Clas is not Annotate with @Req or  @TreeView" + clsIns.getName());
	    	}
			//first must be got the Query permission
			//当前账号对此VO是否有查询权限
			author.authorize(clsIns.getName(), ActionType.Query.name(), true);
			
			if(clsIns.isAnnotationPresent(TableViewEditor.class)) {
				//表格编辑器
				TableViewEditor reqAnno = clsIns.getAnnotation(TableViewEditor.class);
				TableViewerEditorDef fdef = parseTableViewerEditorDef(clsIns,reqAnno);
				fdef.setName(reqAnno.name());
		    	fdef.setNameParams(this.parseNamedParams(reqAnno.namedParams()));
		    	List<FieldDef> itemDefs = new ArrayList<FieldDef>();
		    	parseFieldDef(clsIns,itemDefs,clsIns.getName());
		    	fdef.setItemDefs(itemDefs);
		    	fdef.setQeuryDefs(parseQuery(reqAnno.queryConditions()));
		    	fdef.setActionDefs(parseActions(clsName,reqAnno.actions()));
		    	//fdef.setRowEditable(reqAnno.rowEditable());
		    	def = fdef;
		    	
	    	}else if(clsIns.isAnnotationPresent(TreeViewEditor.class)) {
	    		//树编辑器
	    		TreeViewEditor reqAnno = clsIns.getAnnotation(TreeViewEditor.class);
	    		TreeViewerEditorDef tdef = parseTreeViewerEditorDef(clsIns,reqAnno);
	    		
	    		tdef.setName(reqAnno.name());
	        	tdef.setQeuryDefs(this.parseQuery(reqAnno.queryConditions()));
	        	List<FieldDef> itemDefs = new ArrayList<FieldDef>();
		    	parseFieldDef(clsIns,itemDefs,clsIns.getName());
	        	tdef.setItemDefs(itemDefs);
	        	tdef.setActionDefs(this.parseActions(clsName,reqAnno.actions()));
	        	tdef.setNameParams(this.parseNamedParams(reqAnno.namedParams()));
	        	
	        	def = tdef;
	    	} else {
	    		throw new RuntimeException("Clas is not Annotate with @Req or  @TreeView" + clsIns.getName());
	    	}
			parseViewDef((ViewerDef)def,clsIns);
		} catch (ClassNotFoundException e) {
			throw new CommonException("ClassNotFound",clsName);
		}
		return def;
	}
    
	private void parseViewDef(ViewerDef def, Class<?> clsIns) {
    	def.setEditNodeType(clsIns.getName());
    	def.setClsName(clsIns.getName());
    	if(def.getName() == null || def.getName().trim().equals("")) {
    		def.setName(clsIns.getSimpleName());
    	}
	}

	private TreeViewerEditorDef parseTreeViewerEditorDef(Class<?> cls,TreeViewEditor reqAnno) {
		
    	TreeViewerEditorDef def = new TreeViewerEditorDef();
    	//def.setRowEditable(reqAnno.rowEditable());
    	
    	//Items
    	Field[] fields = cls.getDeclaredFields();
    	for(Field f : fields) {
    		if(f.isAnnotationPresent(Parent.class)) {
    			def.setParentFieldName(f.getName());
        		def.setParentClsName(f.getType().getName());
    			break;
    		}
    	}
    	
    	for(Field f : fields) {
    		if(!f.isAnnotationPresent(Children.class)) {
    			continue;
    		}
    		def.setSubFieldName(f.getName());
        	Class<?> fieldType = f.getType();
        	def.setSubCollectionType(fieldType.getName());
        	if(List.class.isAssignableFrom(fieldType)
        			|| Set.class.isAssignableFrom(fieldType)
        			|| Array.class.isAssignableFrom(fieldType)) {
        		
        		ParameterizedType pt = (ParameterizedType)f.getGenericType();
        		if(pt != null) {
        			Type[] types = pt.getActualTypeArguments();
        			if(types != null) {
        				Class<?> itemCls = (Class<?>)types[0];
        				def.setSubItemsClsName(itemCls.getName());
        				/*List<FieldDef> hd = this.parseReqItem(itemCls);
        				def.setItemDefs(hd);*/
        			}
        		}
        	}
			break;
    	}
		return def;
	}
	
	private List<FieldDef> parseQuery(ItemField[] queryConditions) {
		List<FieldDef> qeuryDef = new ArrayList<FieldDef>();
    	for(ItemField qc: queryConditions) {
    		FieldDef fd = this.getFieldDef(qc,null);
    		qeuryDef.add(fd);
    	}
    	return qeuryDef;
	}
	

	private TableViewerEditorDef parseTableViewerEditorDef(Class<?> cls,TableViewEditor reqAnno) {
    	TableViewerEditorDef def = new TableViewerEditorDef();
    	//Items
    	/*Field[] fields = cls.getDeclaredFields();
    	Field reqItemsField = null;
    	for(Field f : fields) {
    		if(f.isAnnotationPresent(ListItems.class)) {
    			reqItemsField = f;
    			break;
    		}
    	}
    	if(reqItemsField != null) {
    		def.setItemFieldName(reqItemsField.getName());
        	Class<?> fieldType = reqItemsField.getType();
        	def.setItemCollectionType(fieldType.getName());
        	if(null != reqItemsField && (List.class.isAssignableFrom(fieldType)
        			|| Set.class.isAssignableFrom(fieldType)
        			|| Array.class.isAssignableFrom(fieldType) )) {
        		
        		ParameterizedType pt = (ParameterizedType)reqItemsField.getGenericType();
        		if(pt != null) {
        			Type[] types = pt.getActualTypeArguments();
        			if(types != null) {
        				Class<?> itemCls = (Class<?>)types[0];
        				def.setItemFieldCls(itemCls.getName());
        				List<FieldDef> hd = this.parseReqItem(itemCls);
        				def.setItemDefs(hd);
        			}
        		}
        	}
    	}*/
    	
    	
    	/*Action[] itemActions = reqAnno.itemActions();
    	if(itemActions != null) {
    		List<ActionDef> ias = new ArrayList<ActionDef>();
    		for(Action a : itemActions) {
    			ias.add(this.getActionDef(a));
    		}
    		//def.setItemActionDefs(ias);
    	}*/
		return def;
	}
	
	private void parseFieldDef(Class<?> cls,List<FieldDef> itemDefs,String entityName) {
    	Field[] fs = cls.getDeclaredFields();
    	if(entityName == null) {
    		entityName = cls.getName();
    	}
    	for(Field f : fs) {
    		if(!f.isAnnotationPresent(ItemField.class)) {
    			//非显示字段
    			continue;
    		}
    		ItemField rf = f.getAnnotation(ItemField.class);
    		//隐藏字段或有权限查看字段才可以显示
    		if(rf.hide() || this.author.authorize(entityName, f.getName())) {
        		FieldDef fd = this.getFieldDef(rf,f);
        		itemDefs.add(fd);
    		}
    	}
    	//查看父类的字段定义
    	Class superCls = cls.getSuperclass();
    	if(superCls != Object.class) {
    		parseFieldDef(superCls,itemDefs,entityName);
    	}
	}
    
    private List<ActionDef> parseActions(String entityType,Action[] actions) {
    	List<ActionDef> as = null;
    	if(actions != null) {
    		as = new ArrayList<ActionDef>();
    		for(Action a : actions) {
    			String act = a.actionType().name();
    			if(ActionType.Ext.name().equals(act)) {
    				act = a.name();
    			} 
    			//当前账号是否有权限，只有有权限时才显示该操作控件
    			if(this.author.authorize(entityType, act)) {
    				as.add(this.getActionDef(a));
    			}
    		}
    	}
    	return as;
	}

	private ActionDef getActionDef(Action a ) {
    	String url = a.url();
		if(this.isCluster) {
			url = UIConstants.ACTTION_REMOTE + url;
		}else {
			url = UIConstants.ACTTION_LOCAL + url;
		}
		ActionDef ad = new ActionDef(a.name(),url,a.actionType());
		ad.setMethod(a.method());
		ad.setPermClientType(parseClientTypes(a.permClientTypes()));
		ad.setNameParams(this.parseNamedParams(a.namedParams()));
		return ad;
    }
	
	private List<String>  parseClientTypes(String[] clientTypes) {
		if(clientTypes == null || clientTypes.length < 1) {
			return null;
		}
		
		List<String> l = new ArrayList<String>();
		for(String ct : clientTypes) {
			l.add(ct);
		}
		return l;
	}
    
    private FieldDef getFieldDef(ItemField qc,Field f) {
		FieldDef qd = new FieldDef();
		qd.setNameParams(this.parseNamedParams(qc.namedParams()));
		qd.setPermClientType(parseClientTypes(qc.permClientTypes()));
		qd.setName(qc.name());
		qd.setFieldName(qc.name());
		qd.setDataType(qc.dataType());
		
		qd.setLength(qc.length());
		qd.setMaxValue(qc.maxValue());
		qd.setMinValue(qc.minValue());
		qd.setUiType(qc.uiType());
		qd.setIdable(qc.isIdable());
		qd.setHide(qc.hide());
		qd.setEditable(qc.editable());
        qd.setValidatedNodeType(qc.validatedNodeType().getName());
        qd.setHideInRow(qc.hideInRow());
		if(f != null) {
			qd.setClsName(f.getType().getName());
			qd.setFieldName(f.getName());
		}
		int columnLen = qc.lengthByChar();
		if(columnLen < 0) {
			columnLen = qc.length();
		}
		qd.setLengthByChar(columnLen);
		if("".equals(qc.label())) {
			qd.setLabel(qc.name());
		}else {
			qd.setLabel(qc.label());
		}
		
		try {
				if(qc.valueProvider() != Void.class) {
				Object provider = qc.valueProvider().newInstance();
				if(IKeyValueProvider.class.isAssignableFrom(qc.valueProvider())) {
        			IKeyValueProvider p = (IKeyValueProvider)provider;
        			Map<String,String> kvs = p.keyValues();
    				qd.setKeyValues(kvs);
    				qd.setKeyValuesProviderCls(qc.valueProvider().getName());
        		}else if(ITreeNodeProvider.class.isAssignableFrom(qc.valueProvider())) {
        			ITreeNodeProvider p = (ITreeNodeProvider)provider;
        			qd.setTreeRoots(p.getRoot());
        			qd.setTreeRootsProviderCls(qc.valueProvider().getName());
        		}
			   }
				if(qc.defaultValueProvider() != Void.class) {
					DefaultValueProvider provider = (DefaultValueProvider)qc.defaultValueProvider().newInstance();
					qd.setDefaultValue(provider.value());
					qd.setDefaultValueProviderCls(qc.defaultValueProvider().getName());
				}else if(qc.defaultValue() != null && !qc.defaultValue().trim().equals("")) {
					Map<String,String> ps = new HashMap<String,String>();
					ps.put(qc.defaultValue(), qc.defaultValue());
					qd.setDefaultValue(ps);
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		if(qc.availables().length != 0) {
			qd.setAvailables(qc.availables());
		}
	return qd;
    }
    
    
    private Map<String,String> parseNamedParams(NamedParam[] nps) {
    	if(nps == null || nps.length <1) {
    		return null;
    	}
    	Map<String,String> ps = new HashMap<String,String>();
    	for(NamedParam np : nps) {
    		ps.put(np.name(), np.value());
    	}
    	return ps;
    }
   
}
