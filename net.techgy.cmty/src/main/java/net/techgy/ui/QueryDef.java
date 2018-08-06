package net.techgy.ui;

public class QueryDef  implements Comparable<QueryDef>{

	private String displayName="";
	
	private int seq=0;
	
	private boolean unvisible=false;
	
	private String resKey = "";
	
	private String[] uiValueFields={};
	
	private String valueSeperator=",";
	
	private String elementSeperator=";";
	
	public String[] getUiValueFields() {
		return uiValueFields;
	}

	public void setUiValueFields(String[] uiValueFields) {
		this.uiValueFields = uiValueFields;
	}

	public String getValueSeperator() {
		return valueSeperator;
	}

	public void setValueSeperator(String valueSeperator) {
		this.valueSeperator = valueSeperator;
	}

	public String getElementSeperator() {
		return elementSeperator;
	}

	public void setElementSeperator(String elementSeperator) {
		this.elementSeperator = elementSeperator;
	}

	public String getResKey() {
		return resKey;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public boolean isUnvisible() {
		return unvisible;
	}

	public void setUnvisible(boolean unvisible) {
		this.unvisible = unvisible;
	}
	
	@Override
	public int compareTo(QueryDef o) {
		return this.seq > o.getSeq() ? 1:(this.seq == o.getSeq()?0: -1);
	}
	
}
