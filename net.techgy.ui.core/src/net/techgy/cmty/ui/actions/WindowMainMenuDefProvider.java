package net.techgy.cmty.ui.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.techgy.cmty.ui.CmtyUIActivator;
import net.techgy.ui.core.actions.AbstractAction;
import net.techgy.ui.core.actions.IAction;
import net.techgy.ui.core.menubar.IMenuDefProvider;

import org.osgi.framework.BundleContext;

import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.menu.MenuActionDef;

public class WindowMainMenuDefProvider implements IMenuDefProvider{

	@Override
	public String getId() {
		return WindowMainMenuDefProvider.class.getName();
	}
	
	@Override
	public String getTargetId() {
		// TODO Auto-generated method stub
		return IMenuDefProvider.class.getName();
	}

	@Override
	public List<MenuActionDef> getMenuDefs() {

		if(!menuDefs.isEmpty()) {
			return menuDefs;
		}
		
		BundleContext cxt = CmtyUIActivator.getDefault().getContext();	
		
		MenuActionDef window = new MenuActionDef("&Window","Window",null);
		menuDefs.add(window);
		
		String id="Editor";
		new MenuActionDef(id,id,window);
		registerAct(cxt,id);
		
		id="NewWindow";
		new MenuActionDef(id,id,window);
		registerAct(cxt,id);
		
		id="ShowView";
		MenuActionDef showView = new MenuActionDef(id,id,window);
		
		id="Property";
		new MenuActionDef(id,id,showView);
		registerAct(cxt,id);
		
		id="Ant";
		new MenuActionDef(id,id,showView);
		registerAct(cxt,id);
		
		id="Console";
		new MenuActionDef(id,id,showView);
		registerAct(cxt,id);
		
		return menuDefs;
	}
	  
	  private static final List<MenuActionDef> menuDefs = new ArrayList<MenuActionDef>();
	  
	  
    private static void registerAct(BundleContext cxt,String id) {
    	cxt.registerService(IAction.class, new AbstractAction(id){
			@Override
			public int execute(ActionDef ad,Map<String,Object> params) {
				//openImWindow();
				System.out.println(ad.getName());
				return OK;
			}
		}, null);
    }

}
