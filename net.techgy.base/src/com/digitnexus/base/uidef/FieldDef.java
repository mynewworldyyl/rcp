package com.digitnexus.base.uidef;

import java.util.List;
import java.util.Map;

import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;


public class FieldDef  extends BaseDef{

	public static final String VALUE_PROVIDER_URL="/crudService/getValuesList";
	
	private String fieldName;
	
	private int order;
	
	private boolean isIdable = false;
	
	private String label;
	
	private DataType dataType;
	
	private Map<String,String> defaultValue = null;
	private String defaultValueProviderCls;
	
	private UIType uiType;
	
	private String maxValue;
	
	private String minValue;
	
	private int length;
	
	private String[] availables;
	
	private Map<String,String> keyValues = null;
	private String keyValuesProviderCls;
	
	private List<TreeNode> treeRoots = null;
	private String treeRootsProviderCls;
	
	private int lengthByChar;
	
	private boolean isHide = false;
	
	private boolean editable = false;
	
	private boolean hideInRow = false;
	
	private String validatedNodeType;
	
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean isHide() {
		return isHide;
	}
	public void setHide(boolean isHide) {
		this.isHide = isHide;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public Map<String, String> getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(Map<String, String> defaultValue) {
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
	public Map<String, String> getKeyValues() {
		return keyValues;
	}
	public void setKeyValues(Map<String, String> keyValues) {
		this.keyValues = keyValues;
	}
	public List<TreeNode> getTreeRoots() {
		return treeRoots;
	}
	public void setTreeRoots(List<TreeNode> treeRoots) {
		this.treeRoots = treeRoots;
	}
	public boolean isIdable() {
		return isIdable;
	}
	public void setIdable(boolean isIdable) {
		this.isIdable = isIdable;
	}

	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		return order;
	}
	@Override
	public boolean equals(Object obj) {
		return this.order == ((FieldDef)obj).order;
	}
	@Override
	public String toString() {
		return "order: "+this.order+",name: " + this.getName();
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public int getLengthByChar() {
		return lengthByChar;
	}
	public void setLengthByChar(int lengthByChar) {
		this.lengthByChar = lengthByChar;
	}
	public String getValidatedNodeType() {
		return validatedNodeType;
	}
	public void setValidatedNodeType(String validatedNodeType) {
		this.validatedNodeType = validatedNodeType;
	}
	public String getKeyValuesProviderCls() {
		return keyValuesProviderCls;
	}
	public void setKeyValuesProviderCls(String keyValuesProviderCls) {
		this.keyValuesProviderCls = keyValuesProviderCls;
	}
	public String getTreeRootsProviderCls() {
		return treeRootsProviderCls;
	}
	public void setTreeRootsProviderCls(String treeRootsProviderCls) {
		this.treeRootsProviderCls = treeRootsProviderCls;
	}
	public String getDefaultValueProviderCls() {
		return defaultValueProviderCls;
	}
	public void setDefaultValueProviderCls(String defaultValueProviderCls) {
		this.defaultValueProviderCls = defaultValueProviderCls;
	}
	public boolean isHideInRow() {
		return hideInRow;
	}
	public void setHideInRow(boolean hideInRow) {
		this.hideInRow = hideInRow;
	}
	
}
