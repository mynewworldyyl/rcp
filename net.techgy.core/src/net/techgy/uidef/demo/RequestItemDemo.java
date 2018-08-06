package net.techgy.uidef.demo;

import net.techgy.uidef.annotation.Field;
import net.techgy.uidef.annotation.ReqItem;

@ReqItem
public class RequestItemDemo {

	@Field(name="ID", order=1)
	private String itemId;
	
	@Field(name="Name", order=2)
	private String name;
	
	@Field(name="Type", order=2)
	private String type;
	
	@Field(name="Project Name")
	private String projectName;
	
	@Field(name="Vendor Name")
	private String vendorName;
	
	@Field(name="Unit Price")
	private float unitPrice;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	
}
