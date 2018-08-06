package com.digitnexus.base.uidef;

import java.util.List;

public abstract class ViewerDef   extends BaseDef{

	/**
	 * 可编辑结点类型，也就是类的全名（包名和类名）
	 */
	private String editNodeType = null;
	/**
	 * 查询条件定义
	 */
	private List<FieldDef> qeuryDefs = null;
	/**
	 * 支持的操作定义
	 */
	private List<ActionDef> actionDefs;
	
	//private boolean rowEditable = false;
	
	public String getEditNodeType() {
		return editNodeType;
	}

	public void setEditNodeType(String editNodeType) {
		this.editNodeType = editNodeType;
	}

	public List<FieldDef> getQeuryDefs() {
		return qeuryDefs;
	}

	public void setQeuryDefs(List<FieldDef> qeuryDefs) {
		this.qeuryDefs = qeuryDefs;
	}

	public List<ActionDef> getActionDefs() {
		return actionDefs;
	}

	public void setActionDefs(List<ActionDef> actionDefs) {
		this.actionDefs = actionDefs;
	}

}
