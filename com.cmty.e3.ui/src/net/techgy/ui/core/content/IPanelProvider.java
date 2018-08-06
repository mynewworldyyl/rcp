package net.techgy.ui.core.content;

import net.techgy.ui.core.CoreUIActivator;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public interface IPanelProvider extends IProvider {

	public static final String ID = CoreUIActivator.PLUGIN_ID +"/"+IPanelProvider.class.getName();
	
	Control createControl(Composite parent, CmtyWindow window);
}
