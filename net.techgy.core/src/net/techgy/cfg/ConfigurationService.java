package net.techgy.cfg;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;

import net.techgy.cmty.Activator;

import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

public class ConfigurationService implements ManagedService{

	public static final String PID="net.cmty.cfg.ConfigurationService";
	public ConfigurationService() {
		System.out.println();
	}
	@Override
	public void updated(Dictionary<String, ?> properties) throws ConfigurationException {
		if(properties != null) {
			Enumeration<String> e =  properties.keys();
			for(;e.hasMoreElements();) { 
				String key = e.nextElement();
				System.out.println(key + "=" +properties.get(key).toString());
			}
		}
	}
	
	public static Object getProperty(String key) {
		 Configuration cfg = getCfg(PID);
		 if(cfg == null) {
			 return null;
		 }
		 return cfg.getProperties().get(key);
	}
	
	public static void setProperty(String key,Object value) {
		 Configuration cfg = getCfg(PID);
		 if(cfg != null) {
			 cfg.getProperties().put(key,value);
		 }
	}
	
	public static Configuration getCfg(String pid) {
        ServiceReference configurationAdminReference = 
            Activator.getDefault().getBundle().getBundleContext().getServiceReference(ConfigurationAdmin.class.getName());
        Configuration cfg = null;
        if (configurationAdminReference != null) {
            ConfigurationAdmin confAdmin = (ConfigurationAdmin) Activator.getDefault().getBundle().getBundleContext().getService(configurationAdminReference);
			try {
				cfg = confAdmin.getConfiguration(pid);
				  if(cfg == null) {
		            	cfg = confAdmin.createFactoryConfiguration(pid, null); 
		            }
		            
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } 
        return cfg;
	}
}
