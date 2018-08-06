package com.digitnexus.core.utils;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

import com.digitnexus.base.utils.JsonUtils;

public final class AOPUtil {
    private AOPUtil(){
    	
    }
    
    public static Object getTarget(Object proxy){
    	if(AopUtils.isAopProxy(proxy) && proxy instanceof Advised) {
    	    try {
				return ((Advised)proxy).getTargetSource().getTarget();
			} catch (Exception e) {
				return proxy;
			}
       	}
    	
    	return proxy;
    }
    
    public static String toJson(Object obj) {
		return JsonUtils.getInstance().builder().create().toJson(AOPUtil.getTarget(obj));
	}
}
