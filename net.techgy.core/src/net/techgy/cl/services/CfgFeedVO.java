package net.techgy.cl.services;

import net.techgy.ui.UI;

@UI(name="Feed")
public class CfgFeedVO {

	private long id;
	
	private String desc = "";
	
	private String name;

	private ClassVO cls;

	private String namespace = "";
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClassVO getCls() {
		return cls;
	}

	public void setCls(ClassVO cls) {
		this.cls = cls;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	
}
