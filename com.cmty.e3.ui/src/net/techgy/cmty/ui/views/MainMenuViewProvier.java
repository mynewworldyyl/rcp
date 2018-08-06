package net.techgy.cmty.ui.views;

import net.cmty.ui.core.view.IViewProvider;
import net.cmty.ui.core.workbench.WorkbenchPanelProvider;
import net.techgy.cmty.ui.CmtyUIActivator;
import net.techgy.cmty.ui.i18n.I18NUtils;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class MainMenuViewProvier implements IViewProvider{

	public static final String VIEW_ID = CmtyUIActivator.PLUGIN_ID+"/" + MainMenuViewProvier.class.getName();
	
	@Override
	public String getId() {
		return VIEW_ID;
	}

	/*@Override
	public String getTargetId() {
		return WorkbenchPanelProvider.WORKBENCH_ID;
	}*/

	@Override
	public Control createControl(Composite parent,int style) {
	    return null;//new MainMenuView(parent,style);
	}
	
	@Override
	public String getTitle() {
		return I18NUtils.getInstance().getString("MainMenu");
	}

	
}
