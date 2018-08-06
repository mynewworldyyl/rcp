package com.digitnexus.base.uidef;

import java.util.List;

public class TableViewerEditorDef  extends ViewerDef{
	
	//private String itemFieldName;
	
	//private String itemFieldCls;
	private String itemCollectionType;
	
	private List<FieldDef> itemDefs;
	
	public List<FieldDef> getItemDefs() {
		return itemDefs;
	}

	public void setItemDefs(List<FieldDef> itemDefs) {
		this.itemDefs = itemDefs;
	}
}
