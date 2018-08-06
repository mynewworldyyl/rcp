package net.cmty.ui.core.forum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.techgy.ui.core.actions.AbstractAction;
import net.techgy.ui.core.actions.IAction;
import net.techgy.ui.core.content.CmtyWindow;
import net.techgy.ui.core.toolbar.IToolActionDefProvider;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.ui.map.MapComposite;

public class ForumToolActionDefProvider  implements IToolActionDefProvider{

	
	@Override
	public String getId() {
		return ForumToolActionDefProvider.class.getName();
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
		
		BundleContext cxt = FrameworkUtil.getBundle(
				ForumToolActionDefProvider.class).getBundleContext();
		
		String id = "Forum";
		acs.add(new ActionDef(id,id,CmtyWindow.WINDOW_ID));
		cxt.registerService(IAction.class, new AbstractAction(id) {
			@Override
			public int execute(ActionDef ad,Map<String,Object> params) {
				if(CmtyWindow.checkLogin()) {
					return CmtyWindow.exchange(ForumWindow.FORUM_WINDOW_ID) 
							? OK : FAIL;
				}
				return FAIL;
			}
		}, null);
		
		return acs;
	
	}

	private static final List<ActionDef> acs = new ArrayList<ActionDef>();
	  
	public void active() {
		this.getActionDefs();
	}

}
