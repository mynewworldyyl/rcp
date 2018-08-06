package net.techgy.cmty.ui.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.techgy.cmty.ui.CmtyUIActivator;
import net.techgy.ui.core.actions.AbstractAction;
import net.techgy.ui.core.actions.IAction;
import net.techgy.ui.core.toolbar.IToolActionDefProvider;

import org.osgi.framework.BundleContext;

import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.ui.map.MapComposite;

public class MapActionDefProvider implements IToolActionDefProvider{

	@Override
	public String getId() {
		return MapActionDefProvider.class.getName();
	}

	/*@Override
	public String getTargetId() {
		return MapComposite.MAP_ID;
	}*/
	
	@Override
	public List<ActionDef> getActionDefs() {

		if(!acs.isEmpty()) {
			return acs;
		}
		
		BundleContext cxt = CmtyUIActivator.getDefault().getContext();	
		
		/*String id = "SearchMap";
		acs.add(new ActionDef(id,id,getTargetId()));
		cxt.registerService(IAction.class, new AbstractAction(id) {
			@Override
			public int execute(ActionDef ad,Map<String,Object> params) {
				System.out.println("SearchMap");
				return OK;
			}
		}, null);*/
		
		return acs;
	}
	
	private static final List<ActionDef> acs = new ArrayList<ActionDef>();
	 
}
