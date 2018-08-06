package net.techgy.cmty.ui.preference.internal;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;

import net.techgy.cmty.ui.preference.IPreferencePage;
import net.techgy.ui.core.CmtyServiceTracker;

public class PreferencePageManager {

	private Map<String,IPreferencePage> pageServices = new HashMap<String,IPreferencePage>();

	public PreferencePageManager(){}
	
	/*private static final PreferencePageManager instance = new PreferencePageManager();
	
	public static PreferencePageManager getInstance() {
		return instance;
	}*/
	
	@SuppressWarnings("rawtypes")
	private CmtyServiceTracker pageTracker = null;
	
	public IPreferencePage getPage(String modelId) {
		if(!pageServices.containsKey(modelId)) {
			return null;
		}
		return pageServices.get(modelId);
	}
	
	public Map<String,String>  getPageIdToTitle() {
		Map<String,String> idtotitles = new HashMap<String,String>();
		for(Map.Entry<String,IPreferencePage> page : this.pageServices.entrySet()){
			idtotitles.put(page.getKey(), page.getValue().title());
		}
		return idtotitles;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init(BundleContext context) {
		
		pageTracker = new CmtyServiceTracker(context, IPreferencePage.class, pageServices);
		 
	}
	
	public void destroy(BundleContext context) {
		if(pageTracker != null) {
			pageTracker.close();
		}
		
	}
}
