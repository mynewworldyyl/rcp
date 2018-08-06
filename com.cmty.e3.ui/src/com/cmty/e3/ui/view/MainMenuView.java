package com.cmty.e3.ui.view;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class MainMenuView extends ViewPart {
	
	public static final String ID = MainMenuView.class.getName();

	private TableViewer viewer;


	public void createPartControl(Composite parent) {
		
	}


	public void setFocus() {
		viewer.getControl().setFocus();
	}
}