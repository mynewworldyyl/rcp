package net.techgy.osig.service;

import com.digitnexus.base.utils.JsonUtils;

public abstract class AbstractService implements IConfigurationService{

	public static final String SUCCESS = "success";
	
	public static final String FAIL = "fail";
	
	protected <T> T fromJson(String beanStr,Class<T> cls) {
		return JsonUtils.getInstance().fromJson(beanStr, cls,false,false);
	}
	
	protected String toJson(Object obj) {
		return JsonUtils.getInstance().toJson(obj,false);
	}
	
}
