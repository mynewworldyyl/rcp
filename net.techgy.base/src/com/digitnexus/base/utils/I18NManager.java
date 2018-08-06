package com.digitnexus.base.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class I18NManager {

	private static final I18NManager instance = new I18NManager();

	private static Class<?> DEFAULT_RESOURCE_BUNDLE_NAME = I18NManager.class;
	
	public static I18NManager getInstance() {
		return instance;
	}
	
	public static void setDefauleCls(Class cls) {
		if(cls == null) {
			throw new NullPointerException();
		}
		DEFAULT_RESOURCE_BUNDLE_NAME = cls;
	}
	
	public String getString(String key,Locale locale,ClassLoader loader, Class<?> clazz, String...args) {
		String path = clazz.getName().replace('.', '/');
		ResourceBundle bundle = null;
		try {
			bundle = ResourceBundle.getBundle(path, locale, loader);
		} catch (Exception e) {
		}
		
		String msg = null;
		try {
			msg = bundle.getString(key);
		} catch (MissingResourceException e) {
			msg = key;
		}
		if(msg != null && args != null && args.length > 0) {
			msg = MessageFormat.format(msg, args);
		}
		return msg;
	}
	
	
	public String getString(String key,ClassLoader loader,Locale locale) {
		return this.getString(key,locale,loader,DEFAULT_RESOURCE_BUNDLE_NAME);
	}
	
	public String getString(String key,Locale locale,ClassLoader loader,String...args) {
		return this.getString(key,locale,loader,DEFAULT_RESOURCE_BUNDLE_NAME,args);
	}
	
	public String getString(String key,Locale locale) {
		return this.getString(key,locale,Thread.class.getClassLoader(),DEFAULT_RESOURCE_BUNDLE_NAME);
	}
	
	public String getString(String key,Locale locale,String...args) {
		return this.getString(key,locale,Thread.class.getClassLoader(),DEFAULT_RESOURCE_BUNDLE_NAME,args);
	}
	
	public Locale getLocale() {
		Locale locale = null;
		if(locale == null) {
			locale =  Locale.getDefault();
		}
		return locale;
	}
}
