package com.digitnexus.base.protocol;

import java.io.Serializable;

public class Tag implements Serializable{

	public static final String BASE_TAG = "baseTag";
	public static final String CAR_TAG = "carTag";
	
	private String tagType;
	private String tagValue;
	private String barcode;
	
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	public String getTagValue() {
		return tagValue;
	}
	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	
}
