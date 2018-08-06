package net.techgy.cmty;


import java.util.Hashtable;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.digitnexus.base.service.IClientService;

public class Activator extends Plugin {

	public static final String SPRING_CONFIG_DIR = "C:/Users/ylye/Dropbox/RAPws/net.techgy.cmty/config/spring/";
	// The plug-in ID
	public static final String PLUGIN_ID = "net.techgy.cmty"; //$NON-NLS-1$
	private static final String[] configFiles = {
		"/spring/app-jpa.xml",
		"/spring/app-im.xml",
		"/spring/app-mongodb.xml"};
	
	// The shared instance
	private static Activator plugin;
	
	@SuppressWarnings({ "rawtypes"})
	private ServiceTracker parentSpringTracker = null;
	
	/**
	 * The constructor
	 */
	public Activator() {		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		/*parentSpringTracker = new ServiceTracker(context, ISpringContextService.class.getName(), null) {
		      public void removedService(ServiceReference reference, Object service) {
		    	 
		      }
		      public Object addingService(ServiceReference reference) {
		    	  return null;
		      }
		 };
		 parentSpringTracker.open();*/
		
		doStart(context);
		plugin = this;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void doStart(BundleContext context) {
		
		ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(configFiles);
		  springContext.refresh();
	      Hashtable properties = new Hashtable();
		  IClientService ac  =springContext.getBean(IClientService.class);
		  context.registerService(IClientService.class, ac, properties);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		if(parentSpringTracker != null) {
			parentSpringTracker.close();
		}
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
}
