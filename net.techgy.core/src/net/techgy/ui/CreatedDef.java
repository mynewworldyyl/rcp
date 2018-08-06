package net.techgy.ui;

public class CreatedDef {

	private String uiType="text";
	
	private boolean notCreated=false;
	
	private String[] values={};
	
	private String defValue="";

	public String getUiType() {
		return uiType;
	}

	public void setUiType(String uiType) {
		this.uiType = uiType;
	}

	public boolean isNotCreated() {
		return notCreated;
	}

	public void setNotCreated(boolean notCreated) {
		this.notCreated = notCreated;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public String getDefValue() {
		return defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}
	
	
}
