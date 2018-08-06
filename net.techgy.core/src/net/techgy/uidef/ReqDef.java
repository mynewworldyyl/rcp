package net.techgy.uidef;

import java.util.List;

public class ReqDef {

	private String name;
	
	private String clsName;
	
	private List<FieldDef> headerFields = null;
	
	private List<ReqQueryDef> qeuryDef;

	private ReqItemHeaderDef itemDef;
	
	public List<FieldDef> getHeaderFields() {
		return headerFields;
	}

	public void setHeaderFields(List<FieldDef> headerFields) {
		this.headerFields = headerFields;
	}

	public ReqItemHeaderDef getItemDef() {
		return itemDef;
	}

	public void setItemDef(ReqItemHeaderDef itemDef) {
		this.itemDef = itemDef;
	}

	public List<ReqQueryDef> getQeuryDef() {
		return qeuryDef;
	}

	public void setQeuryDef(List<ReqQueryDef> qeuryDef) {
		this.qeuryDef = qeuryDef;
	}

	public String getClsName() {
		return clsName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
