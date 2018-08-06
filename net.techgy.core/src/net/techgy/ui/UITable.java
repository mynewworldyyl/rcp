package net.techgy.ui;

import java.util.ArrayList;
import java.util.List;

public class UITable {

	private String tableName = null;
	
	private List<VODefinition> vodef = new ArrayList<VODefinition>();
	
	private List<UIRow> rows = new ArrayList<UIRow>();
	
	private List<Object> vos = new ArrayList<Object>();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public List<VODefinition> getVodef() {
		return vodef;
	}

	public void setVodef(List<VODefinition> vodef) {
		this.vodef = vodef;
	}

	public List<UIRow> getRows() {
		return rows;
	}

	public void setRows(List<UIRow> rows) {
		this.rows = rows;
	}

	public List<Object> getVos() {
		return vos;
	}

	public void setVos(List<Object> vos) {
		this.vos = vos;
	}

	
	
}
