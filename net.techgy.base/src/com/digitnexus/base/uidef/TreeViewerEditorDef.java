package com.digitnexus.base.uidef;

import java.util.List;

public class TreeViewerEditorDef  extends ViewerDef{

	//树孩子集合类型
	private String subCollectionType;
	//除parent和Children之外的域定义
	private List<FieldDef> itemDefs;
	
	private String parentFieldName;
	
	private String subFieldName;
	
    private String parentClsName;
	
	private String subItemsClsName;
	
	public String getSubCollectionType() {
		return subCollectionType;
	}

	public void setSubCollectionType(String subCollectionType) {
		this.subCollectionType = subCollectionType;
	}

	public List<FieldDef> getItemDefs() {
		return itemDefs;
	}

	public void setItemDefs(List<FieldDef> itemDefs) {
		this.itemDefs = itemDefs;
	}

	public String getParentFieldName() {
		return parentFieldName;
	}

	public void setParentFieldName(String parentFieldName) {
		this.parentFieldName = parentFieldName;
	}

	public String getSubFieldName() {
		return subFieldName;
	}

	public void setSubFieldName(String subFieldName) {
		this.subFieldName = subFieldName;
	}

	public String getParentClsName() {
		return parentClsName;
	}

	public void setParentClsName(String parentClsName) {
		this.parentClsName = parentClsName;
	}

	public String getSubItemsClsName() {
		return subItemsClsName;
	}

	public void setSubItemsClsName(String subItemsClsName) {
		this.subItemsClsName = subItemsClsName;
	}

}
