package com.digitnexus.base.uidef;

import com.digitnexus.base.uidef.UIConstants.ActionType;

public class ActionDef extends BaseDef implements Comparable<ActionDef>{

	public static final String ACTION_KEY="actionKey";
	
	public Integer order=0;
	
	private String url;
	
	private String method = "POST";
	
	private ActionType actionType;
	
	public ActionDef(){
		
	}
	
    public ActionDef(String id){
		this.setId(id);
	}
	
    public ActionDef(String id,String name){
		this(id);
		this.setName(name);
	}
    
    public ActionDef(String id,String name,String url){
		this(id,name);
		this.setUrl(url);
	}
	
    public ActionDef(String name,String url,ActionType actionType){
    	this.setId(name);
		setName(name);
		this.setUrl(url);
		this.setActionType(actionType);
	}
    
    public ActionDef(String name,String url,ActionType actionType,String id){
		this(name,url,actionType);
		this.setId(id);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	@Override
	public int compareTo(ActionDef o) {
		return this.order.compareTo(o.order);
	}

}
