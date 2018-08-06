package net.techgy.uidef;

import net.techgy.uidef.UIConstants.DataType;
import net.techgy.uidef.UIConstants.UIType;

public class ReqQueryDef {

	private String name;
	
	private DataType dataType;
	
	private String defaultValue;
	
	private UIType uiType;
	
	private String maxValue;
	
	private String minValue;
	
	private int length;
	
	private String[] availables;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public UIType getUiType() {
		return uiType;
	}

	public void setUiType(UIType uiType) {
		this.uiType = uiType;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String[] getAvailables() {
		return availables;
	}

	public void setAvailables(String[] availables) {
		this.availables = availables;
	}
	
	
}
