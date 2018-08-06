package net.techgy.cmty;


import java.util.Hashtable;

import javax.servlet.ServletException;

import net.techgy.ui.core.files.CmtyFileUploadServlet;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;
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
	private ServiceTracker fileUploadTrack = null;
	
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
		doStart(context);
		
		final String path = "/fileUpload";
		fileUploadTrack = new ServiceTracker(context, HttpService.class.getName(), null) {
		      public void removedService(ServiceReference reference, Object service) {
		    	  // HTTP service is no longer available, unregister our servlet...
		          try {
		             ((HttpService) service).unregister(path);
		          } catch (IllegalArgumentException exception) {
		          }
		      }
		      public Object addingService(ServiceReference reference) {
		    	  HttpService httpService = context.getService(reference);
		    	  //HttpContext httpCxt = new HttpContext();
		    	  try {
					httpService.registerServlet(path, new CmtyFileUploadServlet(),
							  null, null);					
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (NamespaceException e) {
					e.printStackTrace();
				}
		    	return httpService;
		      }
		 };
		 fileUploadTrack.open();
		
	
		plugin = this;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void doStart(BundleContext context) {
		
		ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(configFiles);
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
		if(fileUploadTrack != null) {
			fileUploadTrack.close();
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
