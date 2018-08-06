package net.techgy.ui;

import java.util.ArrayList;
import java.util.List;

public class UIRow {

	private List<UICell> cells = new ArrayList<UICell>();

	public List<UICell> getCells() {
		return cells;
	}

	public void setCells(List<UICell> cells) {
		this.cells = cells;
	}
	
	
}
