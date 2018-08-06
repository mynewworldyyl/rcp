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

public class FileMainMenuDefProvider implements IMenuDefProvider{

	@Override
	public String getId() {
		return FileMainMenuDefProvider.class.getName();
	}
	
	/*@Override
	public String getTargetId() {
		// TODO Auto-generated method stub
		return IMenuDefProvider.class.getName();
	}*/

	@Override
	public List<MenuActionDef> getMenuDefs() {

		if(!menuDefs.isEmpty()) {
			return menuDefs;
		}
		
		BundleContext cxt = CmtyUIActivator.getDefault().getContext();	
		
		MenuActionDef file = new MenuActionDef("&File","File",null);
		menuDefs.add(file);
		
		String id = "Save";
		new MenuActionDef(id,id,file);
		registerAct(cxt,id);
		
		id="SaveAll";
		new MenuActionDef(id,id,file);
		registerAct(cxt,id);
		
		id="SaveAs";
		new MenuActionDef(id,id,file);
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
