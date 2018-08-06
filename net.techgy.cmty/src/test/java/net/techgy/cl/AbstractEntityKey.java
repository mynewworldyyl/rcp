package net.techgy.cl;

import java.io.Serializable;

public abstract class AbstractEntityKey implements Serializable{

	public static final String BASE_SERIA_KEY = "10000";
	public static final long serialVersionUID=Long.valueOf(BASE_SERIA_KEY+AbstractEntityKey.class.hashCode());
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
