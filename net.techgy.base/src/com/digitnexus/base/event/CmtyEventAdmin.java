package com.digitnexus.base.event;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicLong;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;

public class CmtyEventAdmin {

	public static final String CMTY_EVENT_TYPE="eventType";
	
	public static final String CMTY_TOPIC_KEY = "topicKey";
	
	private static final AtomicLong topicId  = new AtomicLong(1);
	
	public static String getTopicId() {
		return "" + topicId.getAndIncrement();
	}
	
	 /** 
     * The eventBus. 
     */  
    private static EventAdmin eventBus;  
      
    /** 
     * @return 
     */  
    public static EventAdmin getEventBus() {  
        return eventBus;  
    }  
      
    /** 
     * Method will be used by DS to set the <code>org.osgi.service.event.EventAdmin</code> service. 
     * @param eventBus 
     */  
    public synchronized void setEventBus(EventAdmin eventBus) {  
    	CmtyEventAdmin.eventBus = eventBus;  
        System.out.println("EventAdmin service is set!"); //$NON-NLS-1$  
    }  
      
    /** 
     * Method will be used by DS to unset the <code>org.osgi.service.event.EventAdmin</code> service. 
     * @param eventBus 
     */  
    public synchronized void unsetEventBus(EventAdmin eventBus) {  
        if (CmtyEventAdmin.eventBus == eventBus) {  
        	CmtyEventAdmin.eventBus = null;  
        }  
        System.out.println("EventAdmin service is unset!"); //$NON-NLS-1$  
    }
    
    public static ServiceRegistration<EventHandler> registerEventHandler(String topic,EventHandler handler) {
    	return registerEventHandler(topic,handler,null);
    }
    
    public static ServiceRegistration<EventHandler> registerEventHandler(String topic,EventHandler handler,
    		Dictionary<String,Object> params) {
		Bundle bundle = FrameworkUtil.getBundle(CmtyEventAdmin.class);
		if(params == null) {
			params = new Hashtable<String,Object>();
		}
		if(params.get("event.topics") == null) {
			params.put("event.topics", topic);
		}
		return bundle.getBundleContext().registerService(EventHandler.class, handler, params);
    }
    
}
