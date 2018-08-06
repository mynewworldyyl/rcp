package com.digitnexus.core.uidef.demo.common;

import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.ListItem;

@ListItem
public class RequestItemDemo {

	@ItemField(name="ID", order=1,lengthByChar=12)
	private String itemId;
	
	@ItemField(name="Name", order=2,lengthByChar=15)
	private String name;
	
	@ItemField(name="Type", order=6,lengthByChar=10)
	private String type;
	
	@ItemField(name="Project Name",order=3,lengthByChar=13)
	private String projectName;
	
	@ItemField(name="Vendor Name",order=5,lengthByChar=15)
	private String vendorName;
	
	@ItemField(name="Unit Price",order=7,lengthByChar=12)
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

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return itemId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof RequestItemDemo)) {
			return false;
		}
		RequestItemDemo rid = (RequestItemDemo)obj;
		boolean f = this.itemId == null ? rid.getItemId() == null: this.itemId.equals(rid.getItemId());
		return f;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "item ID: " + this.itemId+", Name: " + this.name;
	}
	
	
}
