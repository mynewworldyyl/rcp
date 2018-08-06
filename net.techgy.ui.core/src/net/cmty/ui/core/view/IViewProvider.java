package net.cmty.ui.core.view;

import net.techgy.ui.core.CoreUIActivator;
import net.techgy.ui.core.content.CmtyWindow;
import net.techgy.ui.core.content.IProvider;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public interface IViewProvider extends IProvider {

	public static final String ID = CoreUIActivator.PLUGIN_ID +"/"+IViewProvider.class.getName();
	
	Control createControl(Composite parent,int style);
	
	String getTitle();
}
