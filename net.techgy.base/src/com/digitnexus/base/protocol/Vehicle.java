package com.digitnexus.base.protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Vehicle implements Serializable{

	private Long id;
	private String type;
	
	private String license;
	private String desc;
	
	
	private List<Tag> tags = new ArrayList<Tag>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
