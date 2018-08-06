package net.cmty.ui.core.forum;

import net.techgy.ui.core.CoreUIActivator;
import net.techgy.ui.core.content.CmtyWindow;
import net.techgy.ui.core.content.IPanelProvider;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ForumPanelProvider implements IPanelProvider{

	public static final String FORUM_PANEL_ID = 
			CoreUIActivator.PLUGIN_ID + "/" + ForumPanelProvider.class.getName();
	
	@Override
	public String getId() {
		return FORUM_PANEL_ID;
	}

	/*@Override
	public String getTargetId() {
		return ForumWindow.FORUM_WINDOW_ID;
	}*/

	@Override
	public Control createControl(Composite parent, CmtyWindow window) {
		return new ForumWindow(parent,SWT.NONE,window);
	}

	
}
