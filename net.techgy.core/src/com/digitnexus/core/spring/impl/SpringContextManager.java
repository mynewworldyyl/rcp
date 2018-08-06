package com.digitnexus.core.spring.impl;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import com.digitnexus.base.service.IClientService;

public class SpringContextManager {

	private SpringContextManager(){};
	
	private static final SpringContextManager instance = new SpringContextManager();
	
	@SuppressWarnings({ "rawtypes", "unused" })
	private ServiceTracker clientTracker = null;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  void init(BundleContext context) {
		this.clientTracker = new ServiceTracker(context, IClientService.class.getName(), null) {
		      public void removedService(ServiceReference reference, Object service) {

		      }

		      public Object addingService(ServiceReference reference) {
		    	  IClientService client = (IClientService)context.getService(reference);

		    	  return client;
		      }
		 };
		 
	}
	

	
	
}
