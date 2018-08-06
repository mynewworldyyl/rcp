package net.techgy.ui;


public class VODefinition implements Comparable<VODefinition>{

	private String fieldName="";
	//indicate the java type, such as map, list, simple type
	private String fieldType="";
	
	private String defValue="";
	
	//private List<VODefinition> valueVODef;
	
	//the full class type name
	private String fieldValueClsName = "";

	private boolean complexIns = false;

	private boolean ableId = false;
	
	private CreatedDef createdDef = new CreatedDef();
	
	private QueryDef queryDef = new QueryDef();
	
	private RemoveDef removeDef =new RemoveDef();
	
	private UpdateDef updateDef = new UpdateDef();

	

	public boolean isAbleId() {
		return ableId;
	}

	public void setAbleId(boolean ableId) {
		this.ableId = ableId;
	}

	public QueryDef getQueryDef() {
		return queryDef;
	}

	public void setQueryDef(QueryDef queryDef) {
		this.queryDef = queryDef;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getDefValue() {
		return defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	public String getFieldValueClsName() {
		return fieldValueClsName;
	}

	public void setFieldValueClsName(String fieldValueClsName) {
		this.fieldValueClsName = fieldValueClsName;
	}

	public boolean isComplexIns() {
		return complexIns;
	}

	public void setComplexIns(boolean complexIns) {
		this.complexIns = complexIns;
	}

	public CreatedDef getCreatedDef() {
		return createdDef;
	}

	public void setCreatedDef(CreatedDef createdDef) {
		this.createdDef = createdDef;
	}

	@Override
	public int compareTo(VODefinition o) {
       return this.queryDef.compareTo(o.getQueryDef());
	}

	public RemoveDef getRemoveDef() {
		return removeDef;
	}

	public void setRemoveDef(RemoveDef removeDef) {
		this.removeDef = removeDef;
	}

	public UpdateDef getUpdateDef() {
		return updateDef;
	}

	public void setUpdateDef(UpdateDef updateDef) {
		this.updateDef = updateDef;
	}
	
	
}
