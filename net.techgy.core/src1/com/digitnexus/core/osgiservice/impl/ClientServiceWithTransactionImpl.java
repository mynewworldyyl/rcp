package com.digitnexus.core.osgiservice.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.utils.AOPUtil;
import com.google.gson.reflect.TypeToken;

@Component
public class ClientServiceWithTransactionImpl{

	@Autowired
	private SpringContext springContext;
	
	private static final String[] escapeUrl = {
		"/accountService/login","/accountService/register"
	};
	
	private void initContext(Map<String, String> ps,String url) {
		String accountName = ps.get(UIConstants.REQ_USER_ID);
		String clientId = ps.get(UIConstants.REQ_USER_CLIENT_ID);
		String localeStr = ps.get(UIConstants.REQ_USER_LOCALE);
		
		ps.remove(UIConstants.REQ_USER_ID);
		ps.remove(UIConstants.REQ_USER_CLIENT_ID);
		ps.remove(UIConstants.REQ_USER_LOCALE);
		
		UserContext.init(accountName,clientId,localeStr);
	}
	
	@Transactional(rollbackFor={Throwable.class})
	public String processCall(String url, String jsonParams) throws Exception {
		try {
			Map<String,String> ps = null;
			if(jsonParams != null && !"".equals(jsonParams.trim())) {
				Type type = new TypeToken<Map<String,String>>(){}.getType();
				ps = JsonUtils.getInstance().fromJson(jsonParams, type, false, false);
			}
			boolean needinit = true;
			for(String u : escapeUrl ) {
				if(url.indexOf(u) != -1) {
					needinit = false;
				}
			}
			if(needinit) {
				initContext(ps,url);
			}
			
			if(url == null || "".equals(url.trim())) {
				throw new NullPointerException("request URL cannot be null");
			}
			url = url.trim();
			if(!url.startsWith(UIConstants.ACTTION_LOCAL)) {
				throw new IllegalArgumentException("url not prefix with : " + UIConstants.ACTTION_LOCAL);
			}
			String tempUrl = url.substring(UIConstants.ACTTION_LOCAL.length());
			if(tempUrl == null || "".equals(tempUrl.trim())) {
				throw new IllegalArgumentException("Component name is NULL for :" + url);
			}
			String comName = null;
			if(tempUrl.startsWith("/")) {
				tempUrl = tempUrl.substring(1);
			}
			int index = tempUrl.indexOf("/");
			if(index == -1) {
				comName = tempUrl;
				tempUrl = null;
			}else {
				 comName = tempUrl.substring(0,index);
			}
			String methodPath = tempUrl.substring(comName.length());
			index = methodPath.indexOf("/");
			if(index != -1) {
				methodPath = methodPath.substring(1);
			}
			Object result = commonProcessVO(comName, methodPath,ps);
			if(result == null) {
				return null;
			}else if(result instanceof String)  {
			    return (String)result;
			} else {
				return JsonUtils.getInstance().toJson(result,false);
			}
		} finally {
			UserContext.releaseContext();
		}
	
	}
	
	private Object commonProcessVO(String managerName,String methodPath,Map<String,String> args) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	if(null == managerName || "".equals(managerName.trim())) {
    		 String msg = "Manager name cannot be NULL:" + managerName;
    		   this.processError(msg);
    	}
    	if(null == methodPath || "".equals(methodPath.trim())) {
   		 String msg = "Manager method name cannot be NULL:" + methodPath;
   		   this.processError(msg);
   	    }
    	Object retObj = null;
    	Object manager = null;//this.getBean(managerName);
		for(Map.Entry<String, Object> be : this.springContext.getBeans().entrySet()) {
			Object targetObj = AOPUtil.getTarget(be.getValue());
			Path p = targetObj.getClass().getAnnotation(Path.class);
			if(p == null) {
				continue;
			}
			String path = p.value();
			if(path == null || path.trim().equals("")) {
				 continue;
			}
			if(path.startsWith("/")) {
				path = path.substring(1);
			}
			if(managerName.equals(path)) {
				manager = be.getValue();
				break;
			}
		}
	
		
		if(manager == null) {
			this.processError("no manager with name : " + managerName);
		}
		if(methodPath.startsWith("/")) {
			methodPath = methodPath.substring(1);
		}
    	Method fullMatchMethod = null;
    	
    	Object targetObj = AOPUtil.getTarget(manager);
		Method[] methods = targetObj.getClass().getDeclaredMethods();
		
		for(Method tm: methods) {
			if(tm.isAnnotationPresent(Path.class)) {
				Path path = tm.getAnnotation(Path.class);
				String name = path.value();
				if(name.startsWith("/")) {
					name = name.substring(1);
				}
				if(name.equals(methodPath)) {
					fullMatchMethod = tm;
					break;
				}else if(name.startsWith(methodPath)) {
					String seperator = name.substring(methodPath.length(),methodPath.length()+1);
					if("/".equals(seperator)) {
						fullMatchMethod = tm;
						break;
					}
				}
			}
		}
		Method tMethod = null;
		if(fullMatchMethod != null) {
			tMethod = fullMatchMethod;
		}else {
			tMethod = fullMatchMethod;
		}
		
		if(null == tMethod) {
	    	   String msg = "Manager have no method annotated with path " + methodPath;
	  		   this.processError(msg);
	    }
    	
		Class<?>[] parameterTypes = tMethod.getParameterTypes();
		Object[] tArgs = this.getTargetArgs(args,tMethod);

		Method proxMethod = manager.getClass().getMethod(tMethod.getName(), parameterTypes);    		
		if(null == proxMethod) {
	    	   String msg = "Manager prox have no method with name " + tMethod.getName();
	  		   this.processError(msg);
	    }
		 //this.beforeProcessArgs(tArgs);
		 retObj =  proxMethod.invoke(manager, tArgs);
		 //this.afterProcessArgs(retObj);
	
    	return retObj;
    }

		private Object[] getTargetArgs(Map<String,String> args, Method method) {
	    	if(null == args || args.isEmpty()) {
	    		return null;
	    	}
	    	Class<?>[] parameterType = method.getParameterTypes();
	    	Type[] genericParameterType = method.getGenericParameterTypes();
	    	
	    	Annotation[][] annos = method.getParameterAnnotations();
	    	Object[] objs = new Object[parameterType.length];
	    	for(int index = 0 ; index < parameterType.length; index++) {
	    		//参数类型
	    		Class<?> paramType = parameterType[index];
	    		Annotation[] as = annos[index];
	    		List<Annotation> noAnnotateds = new ArrayList<Annotation>();
	    		for(Annotation a : as) {
	    			if(a.annotationType() == QueryParam.class ||
	    			    a.annotationType() == FormParam.class ||
	    			    a.annotationType() == HeaderParam.class ) {
	    				continue;
	    			}
	    			noAnnotateds.add(a);
	    		}
	    		if(noAnnotateds.size() > 1) {
	    			String msg = "Method '"+ method.getName() + "too many args not annotated with QueryParam, FormParam, HeaderParam";
	    			this.processError(msg);
	    		}
	    		String paramName = null;
	    		for(Annotation a : as) {
	    			if(a.annotationType() == QueryParam.class) {
	    				paramName = ((QueryParam)a).value();
	    				break;
	    			}else if(a.annotationType() == FormParam.class) {
	    				paramName = ((FormParam)a).value();
	    				break;
	    			}else if(a.annotationType() == HeaderParam.class) {
	    				paramName = ((FormParam)a).value();
	    				break;
	    			}
	    		}
	    		if(paramName == null  || "".equals(paramName.trim())){
	    			if(String.class.isAssignableFrom(paramType)) {
	    				objs[index] = JsonUtils.getInstance().toJson(args, false);
	    			}else {
	    				throw new CommonException("ArgumentTypeMissMatch",paramName);
	    			}
	    		}else if("body".equalsIgnoreCase(paramName)) {
	    			if(Map.class.isAssignableFrom(paramType)) {
	    				objs[index] = args;
	    			}else if(String.class.isAssignableFrom(paramType)) {
	    				objs[index] = JsonUtils.getInstance().toJson(args, false);
	    			}else {
	    				throw new CommonException("ArgumentTypeMissMatch",paramName);
	    			}
	    		}else {
	    			Object o = args.get(paramName);
		    		if(o == null) {
		    			throw new CommonException("ArgumentNotExist",paramName);
		    		}else {
			    		objs[index] = this.getArgValue(o,paramType,genericParameterType[index]);
		    		}
	    		}
	    	}
			return objs;
		}

		private Object getArgValue(Object o, Class type, Type gt) {
			Object value = null;
			if(type.isPrimitive()) {
				if(type == double.class || type == Double.class) {
					value = Double.valueOf(o.toString());
				}else if(type == float.class || type == Float.class) {
					value = Float.valueOf(o.toString());
				}else if(type == long.class || type == Long.class) {
					value = Long.valueOf(o.toString());
				}else if(type == int.class || type == Integer.class) {
					value = Integer.valueOf(o.toString());
				}else if(type == short.class || type == Short.class) {
					value = Short.valueOf(o.toString());
				}else if(type == byte.class || type == Byte.class) {
					value = Byte.valueOf(o.toString());
				}else if(type == boolean.class || type == Boolean.class) {
					value = Boolean.valueOf(o.toString());
				}else if(type == char.class || type == Character.class) {
					value = o.toString().charAt(0);
				}else if(type == Void.class|| type == Void.class) {
					value = Byte.valueOf(o.toString());
				}
				if(null != value) {
					return value;
				}
			}else if(type == String.class) {
				value = o.toString();
			}else {
				value = JsonUtils.getInstance().fromJson(o.toString(), gt,false,true);
			}
			/*if(List.class.isAssignableFrom(type)) {
				Type eltType = null;
				if (gt instanceof ParameterizedType) {
					ParameterizedType pt = (ParameterizedType) gt;
					Type[] typeArguments = pt.getActualTypeArguments();
					Type t0 = pt.getOwnerType();
					//Class t1 = pt.getClass();
					//Type[] t2 = pt.getActualTypeArguments();
					//Type t3 = pt.getRawType();
					if (typeArguments.length > 0) { 
						 eltType = typeArguments[0];
					}
				}
				if(eltType != null) {
					List<Object> pl = new ArrayList<Object>();
					List l = (List) value;
					for(Object elt : l) {
						Object v = JsonUtils.getInstance().fromJson(JsonUtils.getInstance().toJson(elt, false),eltType , false, false);
						pl.add(v);
					}
					value = pl;
				}
			}*/
			return value;
		}

		private void processError(String msg,Throwable...e) {
			//logger.error(msg);
			if(e != null && e.length >0) {
				e[0].printStackTrace();
			}
			throw new RuntimeException(msg,(e == null || e.length < 1 ? null: e[0]) );
	    }
}
