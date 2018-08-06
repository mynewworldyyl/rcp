package net.techgy.cmty.ui.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;

import com.digitnexus.base.uidef.ActionDef;

import net.cmty.ui.core.workbench.WorkbenchWindow;
import net.techgy.cmty.ui.CmtyUIActivator;
import net.techgy.ui.core.actions.AbstractAction;
import net.techgy.ui.core.actions.IAction;
import net.techgy.ui.core.content.CmtyWindow;
import net.techgy.ui.core.map.OLMapPanelProvider;
import net.techgy.ui.core.toolbar.IToolActionDefProvider;

public class CmtyMainToolActionDefProvider implements IToolActionDefProvider{

	
	@Override
	public String getId() {
		return CmtyMainToolActionDefProvider.class.getName();
	}

	@Override
	public String getTargetId() {
		return CmtyWindow.WINDOW_ID;
	}
	
	@Override
	public List<ActionDef> getActionDefs() {

		if(!acs.isEmpty()) {
			return acs;
		}
		
		/*
		 //for RAP
		 RWT.getApplicationContext().getServiceManager().registerServiceHandler("resourceLoader",
				new ResourceLoaderHandler(this.getClass().getClassLoader()));
		*/
		
		BundleContext cxt = CmtyUIActivator.getDefault().getContext();	
		
		String id = "News";
		/*acs.add(new ActionDef(id,id,CmtyWindow.WINDOW_ID));
		registerAct(cxt,id);
		
		id = "Map";
		acs.add(new ActionDef(id,id,CmtyWindow.WINDOW_ID));
		cxt.registerService(IAction.class, new AbstractAction(id) {
			@Override
			public int execute(ActionDef ad,Map<String,Object> params) {
				if(CmtyWindow.checkLogin()) {
					return CmtyWindow.exchange(MapComposite.MAP_ID) 
							? OK : FAIL;
				}
				return FAIL;
			}
		}, null);*/
		
		id = "OLMap";
		acs.add(new ActionDef(id,id,CmtyWindow.WINDOW_ID));
		cxt.registerService(IAction.class, new AbstractAction(id) {
			@Override
			public int execute(ActionDef ad,Map<String,Object> params) {
				if(CmtyWindow.checkLogin()) {
					return CmtyWindow.exchange(OLMapPanelProvider.MAP_PANEL_ID) 
							? OK : FAIL;
				}
				return FAIL;
			}
		}, null);
		
		/*id = "Feedback";
		acs.add(new ActionDef(id,id,CmtyWindow.WINDOW_ID));
		registerAct(cxt,id);*/
		
		id = "OpenWorkbench";
		acs.add(new ActionDef(id,id,CmtyWindow.WINDOW_ID));
		cxt.registerService(IAction.class, new AbstractAction(id) {
			@Override
			public int execute(ActionDef ad,Map<String,Object> params) {
				if(CmtyWindow.checkLogin()) {
					return CmtyWindow.exchange(WorkbenchWindow.WORKBENCH_WINDOW_ID) 
							? OK : FAIL;
				}
				return FAIL;
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
	  
	private static void registerAct(BundleContext cxt, String id) {
		cxt.registerService(IAction.class, new AbstractAction(id) {
			@Override
			public int execute(ActionDef ad,Map<String,Object> params) {
				// openImWindow();
				System.out.println(ad.getName());
				return OK;
			}
		}, null);
	}
	
	public void active() {
		this.getActionDefs();
	}

}
