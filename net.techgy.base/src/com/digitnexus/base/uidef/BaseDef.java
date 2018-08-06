package com.digitnexus.base.uidef;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDef {

	private String id;
	
	private String clsName;
	//private boolean canModify = true;
	
	private Map<String,String> nameParams = new HashMap<String,String>();

	private String name;
	
	private List<String> permClientType;
	
	public List<String> getPermClientType() {
		return permClientType;
	}
	public void setPermClientType(List<String> permClientType) {
		this.permClientType = permClientType;
	}
	
	public Map<String, String> getNameParams() {
		return nameParams;
	}

	public void setNameParams(Map<String, String> nameParams) {
		this.nameParams = nameParams;
	}

	public String getClsName() {
		return clsName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
		if(id == null) {
			id=clsName;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
