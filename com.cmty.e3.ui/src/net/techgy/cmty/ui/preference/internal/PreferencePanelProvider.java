package net.techgy.cmty.ui.preference.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import net.techgy.ui.core.CoreUIActivator;
import net.techgy.ui.core.content.CmtyWindow;
import net.techgy.ui.core.content.IPanelProvider;

public class PreferencePanelProvider implements IPanelProvider{

	public static final String PREFERENCE_PANEL_ID = 
			CoreUIActivator.PLUGIN_ID + "/" + PreferencePanelProvider.class.getName();
	
	@Override
	public String getId() {
		return PREFERENCE_PANEL_ID;
	}

	/*@Override
	public String getTargetId() {
		return PreferenceWindow.PREFERENCE_WINDOW_ID;
	}*/

	@Override
	public Control createControl(Composite parent, CmtyWindow window) {
		return new PreferenceWindow(parent,SWT.NONE,window);
	}

	
}
