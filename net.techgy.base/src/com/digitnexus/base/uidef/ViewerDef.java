package com.digitnexus.base.uidef;

import java.util.List;

public abstract class ViewerDef   extends BaseDef{

	/**
	 * �ɱ༭������ͣ�Ҳ�������ȫ����������������
	 */
	private String editNodeType = null;
	/**
	 * ��ѯ��������
	 */
	private List<FieldDef> qeuryDefs = null;
	/**
	 * ֧�ֵĲ�������
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
