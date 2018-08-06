package com.digitnexus.core.utils;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import com.digitnexus.base.service.IClientService;
import com.digitnexus.core.osgiservice.IServiceConnection;
import com.digitnexus.core.osgiservice.IServiceTrackerConnection;
import com.digitnexus.core.osgiservice.impl.ClientServiceImpl;


public class ServiceManager{

	private BundleContext context;
	
	private Map<Class<?>,ServiceReference<?>> serviceRefs = new HashMap<Class<?>,ServiceReference<?>>();
	
	private Map<Class<?>,Object> services = new HashMap<Class<?>,Object>();
	
	public ServiceManager(BundleContext context) {
		this.context = context;
	}
	
	public <T> T getService(Class<T> cls) {
		if(this.services.get(cls) != null) {
			return (T) services.get(cls);
		}
		ServiceReference<?> ref = context.getServiceReference(cls.getName());
		T service = null;
		if (ref != null) {
			service = (T) context.getService(ref);
			if (service != null) {
				this.services.put(cls, service);
				this.serviceRefs.put(cls, ref);
			}
		}
		return service;
	}
	
	public void ungetService(Class<?> cls) {
		if(this.services.get(cls) != null) {
			services.remove(cls);
		}
		ServiceReference<?> ref = context.getServiceReference(cls);
		if (ref != null) {
			context.ungetService(ref);
		}
	}

	public <T> void getService(final IServiceConnection<T> connection, Class<T> cls) {
		try {
			context.addServiceListener(new ServiceListener() {
				              T service = null;
				              public void serviceChanged(ServiceEvent event) {
				            	  ServiceReference<?> ref = event.getServiceReference();
				                  switch (event.getType()) {
				                  case ServiceEvent.REGISTERED:
			                          service = (T) context.getService(ref);
			                          connection.onServiceEnable(service);
				                      break;
				                 case ServiceEvent.UNREGISTERING:
				                	  context.ungetService(ref);
				                	  connection.onServiceDisable(service);
				                      service = null;
				                      ref = null;
				                     break;
				                 }
				             }
				         }, "(objectclass="+cls.getName()+")");
		} catch (InvalidSyntaxException e) {
			connection.onError(e);
		}
		
	}
	
	public <T> void getService(IServiceTrackerConnection<T> conn, Class<T> cls) {
		ServiceTracker tracker = new ServiceTracker(context, cls.getName(),null);
		tracker.open();
		// T service = (T) tracker.getService();
		// conn.onService(service);
		// 获取多个Service
		T[] services = (T[]) tracker.getServices();
		if (services == null) {
			conn.onService(null);
		} else {
			for (T s : services) {
				conn.onService(s);
			}
		}
		// 获取Service的数量
		// int count = tracker.getTrackingCount();
		tracker.close();
	}
	
	/*public IClientService getClient() {
		return ClientServiceImpl.getInstance();
	}*/
}
