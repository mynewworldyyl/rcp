package net.cmty.ui.core.workbench;

import net.techgy.ui.core.CoreUIActivator;
import net.techgy.ui.core.content.CmtyWindow;
import net.techgy.ui.core.content.IPanelProvider;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class WorkbenchPanelProvider implements IPanelProvider{

	public static final String WORKBENCH_ID = 
			CoreUIActivator.PLUGIN_ID + "/" + WorkbenchPanelProvider.class.getName();
	
	@Override
	public String getId() {
		return WORKBENCH_ID;
	}

	@Override
	public String getTargetId() {
		return WorkbenchWindow.WORKBENCH_WINDOW_ID;
	}

	@Override
	public Control createControl(Composite parent, CmtyWindow window) {
		return new WorkbenchWindow(parent,SWT.NONE,window);
	}

	
}
