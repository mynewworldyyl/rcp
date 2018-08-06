package net.techgy.cmty.ui;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import net.techgy.cmty.ui.editors.CompositeFactory;

public class CmtyUIActivator implements BundleActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.techgy.cmty.ui"; //$NON-NLS-1$
	
	// The shared instance
	private static CmtyUIActivator plugin;
	
	//@SuppressWarnings("rawtypes")
	//private ServiceTracker clientTracker = null;
	
	private BundleContext context;

	public boolean isCluster = false;
	
	/**
	 * The constructor
	 */
	public CmtyUIActivator() {
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void start(final BundleContext context) throws Exception {
		this.context = context;
		plugin = this;
        
        /*clientTracker = new ServiceTracker(context, IClientService.class.getName(), null) {
		      public void removedService(ServiceReference reference, Object service) {
		    	  Conn.ins().setRemoteClient(null);
		      }

		      public Object addingService(ServiceReference reference) {
		    	IClientService client = (IClientService)context.getService(reference);
				Conn.ins().setRemoteClient(client);
		        return client;
		      }
		 };
		 clientTracker.open();*/
		 
		 //CompositeFactory.getInstance().init();
		 
		/* context.registerService(IStatuControlProvider.class, 
					new AccountStatuControlProvider(), null);*/
		 
		 /*context.registerService(IToolActionDefProvider.class, 
					new CmtyMainToolActionDefProvider(), null);*/
		 
		/*	context.registerService(IToolActionDefProvider.class, 
					new OthersMainToolActionDefProvider(), null);*/
			/*
			context.registerService(IMenuDefProvider.class, 
					new FileMainMenuDefProvider(), null);
			
			context.registerService(IMenuDefProvider.class, 
					new WindowMainMenuDefProvider(), null);
			
			context.registerService(IViewProvider.class, 
					new MainMenuViewProvier(), null);*/
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		/*if(clientTracker != null) {
			clientTracker.close();
		}*/
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static CmtyUIActivator getDefault() {
		return plugin;
	}

	public BundleContext getContext() {
		return context;
	}
	
	
}
