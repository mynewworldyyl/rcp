package net.techgy.ui.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.ActionDef;

import net.cmty.ui.core.editor.IEditorProvider;
import net.cmty.ui.core.view.IViewProvider;
import net.techgy.ui.core.actions.ActionManager;
import net.techgy.ui.core.actions.IAction;
import net.techgy.ui.core.content.IPanelProvider;
import net.techgy.ui.core.menubar.IMenuDefProvider;
import net.techgy.ui.core.statubar.IStatuControlProvider;
import net.techgy.ui.core.toolbar.IToolActionDefProvider;

public class CmtyServiceManaer {

	private Map<String,IToolActionDefProvider> toolDefProviders = new HashMap<String,IToolActionDefProvider>();
	
	private Map<String,IMenuDefProvider> menuDefProviders = new HashMap<String,IMenuDefProvider>();
	
	private Map<String,IPanelProvider> panelProviders = new HashMap<String,IPanelProvider>();
	
	private Map<String,IStatuControlProvider> statuProviders = new HashMap<String,IStatuControlProvider>();
	
	private Map<String,IViewProvider> viewProviders = new HashMap<String,IViewProvider>();
	
	private Map<String,IEditorProvider> editorProviders = new HashMap<String,IEditorProvider>();
	
	@Inject
	public ActionManager am;
	 
	public List<ActionDef> getToolActionDefs(String soueceId) {
		if(soueceId == null || soueceId.trim().equals("") || toolDefProviders.isEmpty()) {
			return null;
		}
		
         List<ActionDef> acts = new ArrayList<ActionDef>();
		
		for(IToolActionDefProvider p : toolDefProviders.values()) {
			List<ActionDef> as = p.getActionDefs();
			for(ActionDef ad : as) {
				if(ad.getUrl() != null && ad.getUrl().equals(soueceId)) {
					acts.add(ad);
				}
			}
		}
		
		return acts;
	}
	
	public Collection<IStatuControlProvider> getStatuProvider() {
		return statuProviders.values();
	}
	
	public List<IMenuDefProvider> getMenuDefProvider(String soueceId) {
		if(soueceId == null || soueceId.trim().equals("")) {
			return null;
		}
		List<IMenuDefProvider> ps = new ArrayList<IMenuDefProvider>();
		for(IMenuDefProvider p : this.menuDefProviders.values()) {
			if(p.getId().equals(soueceId)) {
				ps.add(p);
			}
		}
		return ps;
	}
	
	public IPanelProvider getPanelProvider(String providerId) {
		if(providerId == null || providerId.trim().equals("")) {
			return null;
		}
		return this.panelProviders.get(providerId);
	}
	
	public IViewProvider getViewProvider(String providerId) {
		if(providerId == null || providerId.trim().equals("")) {
			return null;
		}
		return this.viewProviders.get(providerId);
	}
	
	public IEditorProvider getEditorProvider(String providerId) {
		if(providerId == null || providerId.trim().equals("")) {
			return null;
		}
		return this.editorProviders.get(providerId);
	}
	
	@SuppressWarnings("rawtypes")
	private ServiceTracker actionTracker = null;
	
	@SuppressWarnings("rawtypes")
	private ServiceTracker toolDefTracker = null;
	
	@SuppressWarnings("rawtypes")
	private ServiceTracker menuDefTracker = null;
	
	@SuppressWarnings("rawtypes")
	private ServiceTracker panelTracker = null;
	
	@SuppressWarnings("rawtypes")
	private ServiceTracker statuTracker = null;
	
	@SuppressWarnings("rawtypes")
	private ServiceTracker viewTracker = null;
	
	@SuppressWarnings("rawtypes")
	private ServiceTracker editorTracker = null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init(BundleContext context) {
		actionTracker = new ServiceTracker(context, IAction.class.getName(), null) {
		      public void removedService(ServiceReference reference, Object service) {
		    	  IAction act = (IAction) service;
		    	  am.removeAction(act.getId());
		      }

		      public Object addingService(ServiceReference reference) {
		    	  IAction act = (IAction)context.getService(reference);
		    	  am.registerAction(act);
		        return act;
		      }
		 };
		 actionTracker.open();
		 
		 toolDefTracker = new ServiceTracker(context, IToolActionDefProvider.class.getName(), null) {
		      public void removedService(ServiceReference reference, Object service) {
		    	  IToolActionDefProvider act = (IToolActionDefProvider) service;
		    	  toolDefProviders.remove(act.getId());
		      }

		      public Object addingService(ServiceReference reference) {
		    	  IToolActionDefProvider act = (IToolActionDefProvider)context.getService(reference);
		    	  toolDefProviders.put(act.getId(), act);
		        return act;
		      }
		 };
		 toolDefTracker.open();
		 
		 menuDefTracker = new ServiceTracker(context, IMenuDefProvider.class.getName(), null) {
		      public void removedService(ServiceReference reference, Object service) {
		    	  IMenuDefProvider act = (IMenuDefProvider) service;
		    	  menuDefProviders.remove(act.getId());
		      }

		      public Object addingService(ServiceReference reference) {
		    	  IMenuDefProvider act = (IMenuDefProvider)context.getService(reference);
		    	  menuDefProviders.put(act.getId(), act);
		        return act;
		      }
		 };
		 menuDefTracker.open();
		 
		 
		 panelTracker = new ServiceTracker(context, IPanelProvider.class.getName(), null) {
		      public void removedService(ServiceReference reference, Object service) {
		    	  IPanelProvider act = (IPanelProvider) service;
		    	  panelProviders.remove(act.getId());
		      }

		      public Object addingService(ServiceReference reference) {
		    	  IPanelProvider act = (IPanelProvider)context.getService(reference);
		    	  if(panelProviders.containsKey(act.getId())) {
		    		  IPanelProvider existPanel = panelProviders.get(act.getId());
		    		  throw new CommonException("ExistRepeatPanel",
		    				  existPanel.getId(),act.getId());
		    	  }
		    	  panelProviders.put(act.getId(), act);
		        return act;
		      }
		 };
		 panelTracker.open();
		 
		 statuTracker = new ServiceTracker(context, IStatuControlProvider.class.getName(), null) {
		      public void removedService(ServiceReference reference, Object service) {
		    	  IStatuControlProvider act = (IStatuControlProvider) service;
		    	  statuProviders.remove(act.getId());
		      }

		      public Object addingService(ServiceReference reference) {
		    	  IStatuControlProvider act = (IStatuControlProvider)context.getService(reference);
		    	  statuProviders.put(act.getId(), act);
		        return act;
		      }
		 };
		 statuTracker.open();
		 
		 viewTracker = new ServiceTracker(context, IViewProvider.class.getName(), null) {
		      public void removedService(ServiceReference reference, Object service) {
		    	  IViewProvider act = (IViewProvider) service;
		    	  viewProviders.remove(act.getId());
		      }

		      public Object addingService(ServiceReference reference) {
		    	  IViewProvider act = (IViewProvider)context.getService(reference);
		    	  viewProviders.put(act.getId(), act);
		        return act;
		      }
		 };
		 viewTracker.open();
		 
		 editorTracker = new ServiceTracker(context, IEditorProvider.class.getName(), null) {
		      public void removedService(ServiceReference reference, Object service) {
		    	  IEditorProvider act = (IEditorProvider) service;
		    	  statuProviders.remove(act.getId());
		      }

		      public Object addingService(ServiceReference reference) {
		    	  IEditorProvider act = (IEditorProvider)context.getService(reference);
		    	  editorProviders.put(act.getId(), act);
		        return act;
		      }
		 };
		 editorTracker.open();
	}
	
	public void destroy(BundleContext context) {
		if(actionTracker != null) {
			actionTracker.close();
		}
		
		if(toolDefTracker != null) {
			toolDefTracker.close();
		}
		
		if(menuDefTracker != null) {
			menuDefTracker.close();
		}
		if(panelTracker != null) {
			panelTracker.close();
		}
		if(statuTracker != null) {
			statuTracker.close();
		}
	}
	
}
