package com.digitnexus.base.uidef.menu;

import java.util.ArrayList;
import java.util.List;

import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.UIConstants.ActionType;

public class MenuActionDef extends ActionDef {

    private String parentId;
	
	private List<MenuActionDef> subActions = new ArrayList<MenuActionDef>();
	
	public MenuActionDef() {
		
	}
	
	public MenuActionDef(String id,String name,MenuActionDef parentMenu) {
			super(id,name);
			if(parentMenu != null) {
				this.setParentId(parentMenu.getParentId());
				parentMenu.addSubMenu(this);
			}
	}
	
    public MenuActionDef(String name,String url,ActionType actionType
    		,String parentId, List<MenuActionDef> subActions) {
		super(name,url,actionType);
		this.setParentId(parentId);
		this.setSubActions(subActions);
	}
    
    public MenuActionDef(String name,String url,ActionType actionType,String id,
    		String parentId, List<MenuActionDef> subActions){
		super(name,url,actionType,id);
		this.setParentId(parentId);
		this.setSubActions(subActions);
	}
    
    public void addSubMenu(MenuActionDef subMenu) {
    	this.subActions.add(subMenu);
    }

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<MenuActionDef> getSubActions() {
		return subActions;
	}

	public void setSubActions(List<MenuActionDef> subActions) {
		this.subActions = subActions;
	}
    
    
}
