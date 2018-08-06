package net.techgy.ui;

public class UICell  implements Comparable<UICell>{

	private String value;
	
	private int columnSeq;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getColumnSeq() {
		return columnSeq;
	}

	public void setColumnSeq(int columnSeq) {
		this.columnSeq = columnSeq;
	}
	
	@Override
	public int compareTo(UICell cell) {
        return this.columnSeq > cell.getColumnSeq() ? 1: (this.columnSeq == cell.getColumnSeq() ? 0:-1);
	}
}
