package net.techgy.ui.core;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import net.cmty.ui.core.i18n.I18NUtils;
import net.techgy.ui.core.content.IProvider;

@SuppressWarnings("rawtypes")
public class CmtyServiceTracker<T extends IProvider> extends ServiceTracker{

	private Map<String,T> services = null;
	
	@SuppressWarnings("unchecked")
	public CmtyServiceTracker(BundleContext context, 
			 Class<T> clazz, Map<String,T> services){
		super(context,clazz,null);
		if(services == null) {
			throw new NullPointerException(I18NUtils.getInstance().getString("ServiceCollectionIsNull"));
		}
		this.services = services;
		this.open();
	}
	
	@SuppressWarnings("unchecked")
	public void removedService(ServiceReference reference, Object service) {
  	  T act = (T) service;
  	  this.services.remove(act.getId());
    }

    public Object addingService(ServiceReference reference) {
  	  @SuppressWarnings("unchecked")
	T act = (T)context.getService(reference);
  	  this.services.put(act.getId(), act);
      return act;
    }
}
