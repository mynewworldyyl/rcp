package com.digitnexus.base.protocol;



public class AnnotationUtils {

	private static final AnnotationUtils instance = new AnnotationUtils();
	private AnnotationUtils() {
		
	}
	
	public static AnnotationUtils getInstance() {
		return instance;
	}
	
	public String[] getObjectInfoNames(Class<?> cls,String key) {
		Info in = this.getObjectInfo(cls, key);
		if(in == null) {
			return null;
		}
		return in.fields();
	}
	
	public Info getObjectInfo(Class<?> cls,String key) {
		if(key == null || "".equals(key.trim())) {
			return null;
		}
		if(!cls.isAnnotationPresent(Infos.class)) {
			return null;
		}
		Infos infos = cls.getAnnotation(Infos.class);
		for(Info in : infos.values()) {
			if(in.name().equals(key)) {
				return in;
			}
		}
		return null;
	}
	
	public String[] getObjectInfoLabels(Class<?> cls,String key) {
		Info in = this.getObjectInfo(cls, key);
		if(in == null) {
			return null;
		}
		return in.labels();
	}
	
}
