package com.digitnexus.core.permisson;

public enum PermAction {

	Query("read or view data"),
	Create("Create or New data"),
	Update("Update exist data"),
	Delete("Delete exist data"),	
	Export("Export exist data"),
	
	Demo("Demo this definition");
	
	private String desc;
	
	PermAction(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
