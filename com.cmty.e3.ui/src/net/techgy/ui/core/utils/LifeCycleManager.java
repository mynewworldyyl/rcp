package net.techgy.ui.core.utils;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import net.cmty.ui.core.editor.CompositeFactoryManager;
import net.cmty.ui.core.workbench.WorkbenchSelectionManager;
import net.techgy.cmty.ui.preference.internal.PreferencePageManager;
import net.techgy.ui.core.CmtyServiceManaer;
import net.techgy.ui.core.actions.ActionManager;

public class LifeCycleManager {
	
	  @PostContextCreate
	 public void postContextCreate(IEclipseContext appContext) {
		 
		  //MApplication app = context.get(MApplication.class);
		  //IEclipseContext appContext = app.getContext();
		  
		  BundleContext bcontext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		  
		  ActionManager am = ContextInjectionFactory.make(ActionManager.class, appContext);
		  appContext.set(ActionManager.class, am);
		  //ActionManager.instance = am;
				  
		  CmtyServiceManaer cm = ContextInjectionFactory.make(CmtyServiceManaer.class, appContext);
		  appContext.set(CmtyServiceManaer.class, cm);		 
		  
		  PreferencePageManager ppm = ContextInjectionFactory.make(PreferencePageManager.class, appContext);
		  appContext.set(PreferencePageManager.class, ppm);
		  
		  WorkbenchSelectionManager wsm = ContextInjectionFactory.make(WorkbenchSelectionManager.class, appContext);
		  appContext.set(WorkbenchSelectionManager.class, wsm);
		  
		  CompositeFactoryManager cfm = ContextInjectionFactory.make(CompositeFactoryManager.class, appContext);
		  appContext.set(CompositeFactoryManager.class, cfm);
		  
		  
		  cm.init(bcontext);
		  am.initActions(bcontext);
		  ppm.init(bcontext);
		  cfm.init();
		  
	      System.out.println("postContextCreate");
	  }
	  
	  @PreSave
	  void preSave() {
		  System.out.println("preSave");
	  }
	  
	  @ProcessAdditions
	  void processAdditions() {
		  System.out.println("processAdditions");
	  }
	  
	  @ProcessRemovals
	  void processRemovals(CmtyServiceManaer sm) {
		  System.out.println("processRemovals");
		  BundleContext bc = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		  if(sm != null) {
			  //sm.destroy(bc);
		  }
	  }
	  

	} 

