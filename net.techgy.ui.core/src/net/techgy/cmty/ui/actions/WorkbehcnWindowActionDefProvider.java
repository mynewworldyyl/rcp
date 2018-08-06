package net.techgy.cmty.ui.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;

import com.digitnexus.base.uidef.ActionDef;

import net.cmty.ui.core.workbench.WorkbenchWindow;
import net.techgy.cmty.ui.CmtyUIActivator;
import net.techgy.cmty.ui.views.MainMenuViewProvier;
import net.techgy.ui.core.actions.AbstractAction;
import net.techgy.ui.core.actions.IAction;
import net.techgy.ui.core.content.CmtyWindow;
import net.techgy.ui.core.toolbar.IToolActionDefProvider;

public class WorkbehcnWindowActionDefProvider implements IToolActionDefProvider{

	@Override
	public String getId() {
		return WorkbehcnWindowActionDefProvider.class.getName();
	}

	@Override
	public String getTargetId() {
		return WorkbenchWindow.WORKBENCH_WINDOW_ID;
	}
	
	@Override
	public List<ActionDef> getActionDefs() {

		if(!acs.isEmpty()) {
			return acs;
		}
		
		BundleContext cxt = CmtyUIActivator.getDefault().getContext();	
		
		String id = "ShowMainMenu";
		acs.add(new ActionDef(id,id,WorkbenchWindow.WORKBENCH_WINDOW_ID));
		cxt.registerService(IAction.class, new AbstractAction(id) {
			@Override
			public int execute(ActionDef ad,Map<String,Object> params) {
				/*if(RWT.getUISession().getAttribute(UIConstants.LOGIN_ACCOUNT) == null) {
					throw new CommonException("NotLogin");
				}*/
				//WorkbenchWindow workbench = (WorkbenchWindow)RWT.getUISession().getAttribute(WorkbenchWindow.WORKBENCH_WINDOW_ID);
				WorkbenchWindow workbench = null;
				if(null != workbench) {
					workbench.getViewSite().showView(MainMenuViewProvier.VIEW_ID);
				}
				return OK;
			}
		}, null);
		
		return acs;
	}
	
	private int showPanel(String id){
		if(CmtyWindow.showPane(id)) {
			return IAction.OK;
		}else {
			return IAction.FAIL;
		}
	}
    
	private static final List<ActionDef> acs = new ArrayList<ActionDef>();
	 
	public void active() {
		System.out.println("Service Start :" + this.getId());
		this.getActionDefs();
	}
}
