package net.cmty.ui.core.i18n;

import java.util.Locale;

import com.digitnexus.base.utils.I18NManager;

public class I18NUtils {

	private static final I18NUtils instance = new I18NUtils();
	private I18NUtils(){};
	
	public static I18NUtils getInstance(){return instance;}
	
	public String getString(String key) {
		return I18NManager.getInstance().getString(key,Locale.getDefault()/*RWT.getLocale()*/,I18NUtils.class.getClassLoader(),I18NUtils.class);
	}
	
	public String getString(String key,String...args) {
		return I18NManager.getInstance().getString(key,Locale.getDefault(),I18NUtils.class.getClassLoader(),I18NUtils.class,args);
	}
	
	public String getString(Class cls,String key) {
		return I18NManager.getInstance().getString(key,Locale.getDefault(),cls.getClassLoader(),cls);
	}
	
	public String getString(Class cls,String key,String...args) {
		return I18NManager.getInstance().getString(key,Locale.getDefault(),cls.getClassLoader(),cls,args);
	}
}
