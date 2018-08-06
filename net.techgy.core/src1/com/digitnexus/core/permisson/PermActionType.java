package com.digitnexus.core.permisson;


public enum PermActionType {

	AssetInRequest("Asset check in request"),
	AssetOutRequest("Asset check out request"),
	AssetReturnStorageRequest("Asset return storage request"),
	AssetReturnVendorRequest("Asset return vendor request"),
	
	DemoNotUse("DemoNotUse");
	
	private String desc;
	
	PermActionType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
