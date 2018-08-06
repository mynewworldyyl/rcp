package net.techgy.ui.core;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;

import com.digitnexus.ui.map.MapPanelProvider;

import net.cmty.ui.core.editor.BaseDefEditorProvier;
import net.cmty.ui.core.editor.IEditorProvider;
import net.cmty.ui.core.workbench.WorkbenchPanelProvider;
import net.techgy.cmty.ui.preference.internal.PreferencePageManager;
import net.techgy.ui.core.actions.ActionManager;
import net.techgy.ui.core.content.IPanelProvider;

public class CoreUIActivator implements BundleActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.techgy.ui"; //$NON-NLS-1$
	
	// The shared instance
	private static CoreUIActivator plugin;
	
	private BundleContext context;

	public boolean isCluster = false;
	
	/**
	 * The constructor
	 */
	public CoreUIActivator() {
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void start(final BundleContext context) throws Exception {
		this.context = context;
		plugin = this;
		
		//sr=context.registerService(HelloWord.class.getName(), new HelloWordImpl(), null);
		ServiceTracker st=new ServiceTracker(context,EventAdmin.class.getName(),null);
	    st.open();
	
		//CmtyServiceManaer.getInstance().init(context);
		//ActionManager.getInstace().initActions(context);
		//CmtyMobileServiceManager.getInstance().init(context);
		//PreferencePageManager.getInstance().init(context);
		
	/*	context.registerService(IPanelProvider.class, 
					new WorkbenchPanelProvider(), null);
		 */
		 context.registerService(IEditorProvider.class, 
					new BaseDefEditorProvier(), null);
		 
		 context.registerService(IPanelProvider.class, 
					new MapPanelProvider(), null);
		
		/*Hashtable properties = new Hashtable();
		ApplicationConfiguration ac  = new CmtyCoreConfiguration();
		properties.put("contextName", "cmty");
		context.registerService(ApplicationConfiguration.class, ac, properties);*/
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		//CmtyServiceManaer.getInstance().destroy(context);
		//CmtyMobileServiceManager.getInstance().destroy(context);
		
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static CoreUIActivator getDefault() {
		return plugin;
	}

	public BundleContext getContext() {
		return context;
	}
	
	
}
