package com.digitnexus.core.vo.masterdata;

public class ProfileVo {

	private String id;
	
	private String clientId;
	
	private String mid;
	
	private String key;
	
	private String value;
	
	private String dataType;
	
	private double minValue;
	
	private double maxValue;
	
	private String uiType;
	
	private String validators;
	
	private String defValue;
	
	private String providers;
	
	private String avaiValues;
	
	private String content;
	
	private String accountName;
	
	private String typecode = "Content";
	
	private byte[] data;
	
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ProfileVo)) {
			return false;
		}
		ProfileVo cv = (ProfileVo)obj;
		return this.toString().equals(obj.toString());
	}

	@Override
	public String toString() {
		return "key: " + this.key + ", mid: " + mid + ", Account: "+
	(this.accountName == null ? "" : this.accountName );
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public String getUiType() {
		return uiType;
	}

	public void setUiType(String uiType) {
		this.uiType = uiType;
	}

	public String getValidators() {
		return validators;
	}

	public void setValidators(String validators) {
		this.validators = validators;
	}

	public String getDefValue() {
		return defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	public String getProviders() {
		return providers;
	}

	public void setProviders(String providers) {
		this.providers = providers;
	}

	public String getAvaiValues() {
		return avaiValues;
	}

	public void setAvaiValues(String avaiValues) {
		this.avaiValues = avaiValues;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	
}
