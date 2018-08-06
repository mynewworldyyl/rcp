package net.techgy.ui.manager;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.techgy.cl.IConfigableManager;
import net.techgy.cl.manager.PersistenceListener;
import net.techgy.spring.ContextListener;
import net.techgy.utils.AOPUtil;
import net.techgy.utils.JsonUtils;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ConfiguableManagerImpl implements ContextListener,IConfigableManager {

	private static final Logger logger = Logger.getLogger(ConfiguableManagerImpl.class);  
	
	public static final String METHOD_CREATE="create";
	
	public static final String METHOD_UPDATE="update";
	
	public static final String METHOD_REMOVE="remove";
	
	public static final String METHOD_QUERY="query";
	
	private Map<String,Object> managers = new HashMap<String,Object>();
	
	private Map<String,PersistenceListener<?>> persistenceListeners = new HashMap<String,PersistenceListener<?>>();
	
	@Override
	public void afterContext(ApplicationContext context) {
       Map<String,Object> ms = context.getBeansWithAnnotation(VOManager.class);
       if(ms != null && !ms.isEmpty()) {
    	   Iterator<String> ite = ms.keySet().iterator();
           while(ite.hasNext()) {
        	   String key = ite.next();
        	   Object m = ms.get(key);
        	   VOManager vom = context.findAnnotationOnBean(key, VOManager.class);
        	   String name = vom.value();
        	   if("".equals(name)){
        		   Object targetManager = AOPUtil.getTarget(m);
        		   name = targetManager.getClass().getName();
        	   }
        	   if(this.managers.containsKey(name)){
        		   String msg = "VOManagerListener repeat definite with name:" + name;
        		   this.processError(msg);
        	   }
        	   managers.put(name, m);
           }
       }
       
       Map<String,PersistenceListener> ls = context.getBeansOfType(PersistenceListener.class);
       Iterator<PersistenceListener> itels = ls.values().iterator();
       while(itels.hasNext()) {
    	   PersistenceListener pl = itels.next();
    	   this.registerPersistenceListenrer(pl);
       }
       
	}	
	
    public <T> boolean create(String managerName,String methodName,Map<String,String> args){
    	methodName = methodName == null || methodName.trim().equals("") ? "create": methodName;
    	Object obj = this.commonProcessVO(managerName, methodName, args);
    	return (Boolean) obj;
    }
    
    public<T> boolean update(String managerName,String methodName,Map<String,String> args){
    	methodName = methodName == null || methodName.trim().equals("") ? "update": methodName;
    	Object obj = this.commonProcessVO(managerName, methodName, args);
    	return (Boolean) obj;
    }
    
    public boolean remove(String managerName,String methodName,Map<String,String> args){
    	methodName = methodName == null || methodName.trim().equals("") ? "remove": methodName;
    	Object obj = this.commonProcessVO(managerName, methodName, args);
    	return (Boolean) obj;
    }
    
    public<T> List<T> query(String managerName,String methodName,Map<String,String> args){
    	methodName = methodName == null || methodName.trim().equals("") ? "query": methodName;
    	Object obj = this.commonProcessVO(managerName,methodName, args);
    	return (List<T>) obj;
    }
    
    public<T> T execute(String managerName,String methodName,Map<String,String> args){
    	methodName = methodName == null || methodName.trim().equals("") ? "query": methodName;
    	Object obj = this.commonProcessVO(managerName,methodName, args);
    	return (T) obj;
    }
    
    @Override
	public <T> T queryUniqueResult(String managerName, String methodName,
			Map<String, String> args) {
    	methodName = methodName == null || methodName.trim().equals("") ? "query": methodName;
    	Object obj = this.commonProcessVO(managerName,methodName, args);
    	return (T) obj;
	}

	public Object commonProcessVO(String managerName,String methodName,Map<String,String> args) {
    	if(null == managerName || "".equals(managerName.trim())) {
    		 String msg = "Manager name cannot be NULL:" + managerName;
    		   this.processError(msg);
    	}
    	if(null == methodName || "".equals(methodName.trim())) {
   		 String msg = "Manager method name cannot be NULL:" + methodName;
   		   this.processError(msg);
   	    }
    	Object retObj = null;
    	Object manager = this.managers.get(managerName);
    	if(null == manager) {
    	   String msg = "Manager not found with name:" + managerName;
  		   this.processError(msg);
    	}
    	Method tMethod = null;
    	Object targetObj = AOPUtil.getTarget(manager);
		Method[] methods = targetObj.getClass().getDeclaredMethods();
		for(Method tm: methods) {
			if(tm.isAnnotationPresent(Operation.class)) {
				Operation qa = tm.getAnnotation(Operation.class);
				String name = qa.value();
				if(methodName.equals(name)) {
					tMethod = tm;
					break;
				}
			}
		}
		if(null == tMethod) {
	    	   String msg = "Manager have no method annotated with name " + methodName;
	  		   this.processError(msg);
	    }
    	
		Class<?>[] parameterTypes = tMethod.getParameterTypes();
		Object[] tArgs = this.getTargetArgs(args,tMethod);
    	try {
    		Method proxMethod = manager.getClass().getMethod(tMethod.getName(), parameterTypes);    		
    		if(null == proxMethod) {
    	    	   String msg = "Manager prox have no method with name " + tMethod.getName();
    	  		   this.processError(msg);
    	    }
    		 this.beforeProcessArgs(tArgs);
			 retObj =  proxMethod.invoke(manager, tArgs);
			 this.afterProcessArgs(retObj);
		} catch (IllegalArgumentException e) {
			this.processError("create IllegalArgumentException", e);
		} catch (IllegalAccessException e) {
			this.processError("create IllegalAccessException", e);
		} catch (InvocationTargetException e) {
			this.processError("create InvocationTargetException", e);
		} catch (SecurityException e) {
			this.processError("commonProcessVO SecurityException", e);
		} catch (NoSuchMethodException e) {
			this.processError("commonProcessVO NoSuchMethodException", e);
		}
    	return retObj;
    }

    @SuppressWarnings("unchecked")
    private Object afterProcessArgs(Object retObj) {
    	PersistenceListener pl = this.persistenceListeners.get(retObj.getClass());
    	if(null == pl) {
    		 return retObj;
    	}
    	return pl.after(retObj);
	}

	private Object[] beforeProcessArgs(Object[] tArgs) {
		if(tArgs == null || tArgs.length ==0) {
			return tArgs;
		}
		Object[] args = new Object[tArgs.length];
		for(int index = 0 ; index < tArgs.length; index++) {
			String n = tArgs[index].getClass().getName();
			PersistenceListener pl = this.persistenceListeners.get(n);
	    	if(null == pl) {
	    		 args[index] = tArgs[index];
	    	}else {
	    		args[index] = pl.beforce(tArgs[index]);
	    	}
		}
    	return args;
	}

	private Object[] getTargetArgs(Map<String,String> args, Method method) {
    	if(null == args || args.isEmpty()) {
    		return null;
    	}
    	Class<?>[] parameterType = method.getParameterTypes();
    	Annotation[][] annos = method.getParameterAnnotations();
    	Object[] objs = new Object[parameterType.length];
    	for(int index = 0 ; index < parameterType.length; index++) {
    		Class<?> paramType = parameterType[index];
    		PName pn = null;
    		Annotation[] as = annos[index];
    		for(Annotation a : as) {
    			if(a.annotationType() == PName.class) {
    				pn = (PName)a;
    				break;
    			}
    		}
    		String paramName = null;
    		if(pn != null && pn.value() != null && !"".equals(pn.value().trim())){
    			paramName = pn.value();
    		}else {
    			String msg = "Method '"+ method.getName() + "' parameter mush annotate with PName";
    			this.processError(msg);
    		}    		
    		Object o = args.get(paramName);
    		Object value = this.getArgValue(o,paramType);
    		objs[index] = value;
    	}
		return objs;
	}

	private Object getArgValue(Object o, Class<?> type) {
		Object value = null;
		if(type == Double.TYPE|| type == Double.class) {
			value = Double.valueOf(o.toString());
		}else if(type == Float.TYPE|| type == Float.class) {
			value = Float.valueOf(o.toString());
		}else if(type == Long.TYPE || type == Long.class) {
			value = Long.valueOf(o.toString());
		}else if(type == Integer.TYPE|| type == Integer.class) {
			value = Integer.valueOf(o.toString());
		}else if(type == Short.TYPE|| type == Short.class) {
			value = Short.valueOf(o.toString());
		}else if(type == Byte.TYPE|| type == Byte.class) {
			value = Byte.valueOf(o.toString());
		}else if(type == Boolean.TYPE|| type == Boolean.class) {
			value = Boolean.valueOf(o.toString());
		}else if(type == Character.TYPE|| type == Character.class) {
			value = o.toString().charAt(0);
		}else if(type == Void.TYPE|| type == Void.class) {
			value = Byte.valueOf(o.toString());
		}else if(type == String.class) {
			value = o.toString();
		}
		if(null != value) {
			return value;
		}
		value = JsonUtils.getInstance().fromJson(o.toString(), type);
		return value;
	}

	private void processError(String msg,Throwable ...e) {
		logger.error(msg);
		throw new RuntimeException(msg,(e != null ? e[0]: null));
    }
	
	public void registerPersistenceListenrer(PersistenceListener<?> l) {
		Class<?> cls = l.getProcessClass();
		PersistenceListener<?> pl = this.persistenceListeners.get(cls.getName());
		if(pl == null) {
			this.persistenceListeners.put(cls.getName(), l);
		} else {
			this.processError("Exist Persistence Listener for Class : " + cls.getName());
		}
	}
	
	public void unregisterPersistenceListenrer(Class<?> cls) {
		PersistenceListener<?> pl = this.persistenceListeners.get(cls.getName());
		if(pl == null) {
			logger.warn("Not Exist Persistence Listener for Class when unregister : " + cls.getName());
			return;
		}
		this.persistenceListeners.remove(cls.getName());
	}
}
