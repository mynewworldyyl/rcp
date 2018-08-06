package net.techgy.ui.core.utils;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import net.cmty.ui.core.editor.NameValueComposite;
import net.cmty.ui.core.i18n.I18NUtils;
import net.techgy.cmty.service.DataDto;
import net.techgy.ui.core.network.WSConn;

public class UIUtils {

	private static final UIUtils instance = new UIUtils();
	
	private UIUtils() {};
	
	public static UIUtils getInstance() {
		return instance;
	}
	
	public FieldDef getIdPropertyDef(List<FieldDef> itemDefs){
		for(FieldDef d : itemDefs) {
			if(d.isIdable()) {
				return d;
			}
		}
		return null;
	}
	
	 public Object getModelId(Object model,List<FieldDef> itemDefs) {
		 FieldDef iddef = this.getIdPropertyDef(itemDefs);
		 return this.getModelProperty( model,iddef);
	}
	 
	public boolean setModelProperty(Object model,FieldDef fdef,Object value) {
		return this.setModelProperty(model,fdef.getFieldName(), value);
	}
	
	public boolean setModelProperty(Object model,String fieldName,Object value) {
		try {
			String getMehtod = "set"+ fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method setId = model.getClass().getMethod(getMehtod, String.class);
			setId.invoke(model,value);
			return true;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Object getModelProperty(Object model,FieldDef fdef) {
		return this.getModelProperty(model,fdef.getFieldName());
	}
	
	public Object getModelProperty(Object model,String fieldName) {
		try {
			String getMehtod = "get"+ fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method setId = model.getClass().getMethod(getMehtod);
			return setId.invoke(model);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}
	 
	public String getIdFromServer(String clsName) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("clsName", clsName);
		DataDto resp = WSConn.ins().call("crudService", "id", params);
		return resp.getData();
	}
	
	public Object getFieldValue(Object model,String fieldName) {
		try {
			//String idname = UIConstants.ModelProperty.NodeType.getFieldName();
			Field f = model.getClass().getField(fieldName);
			f.setAccessible(true);
			return f.get(model);
		}catch (SecurityException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (NoSuchFieldException e) {
		} 
		return null;
	
	}
	
	public Object[] getSelections(ISelection selection) {
		if(selection == null || selection.isEmpty()) {
			return null;
		}
		IStructuredSelection sel = (IStructuredSelection)selection;
		List<Object> objs = new ArrayList<Object>();
		Iterator<Object> ite = sel.iterator();
		while(ite.hasNext()) {
			objs.add(ite.next());
		}
		return objs.toArray();
	}
	
	public void showNodeDialog(Shell shell,String msgKey,String...args) {
		MessageDialog.openError(shell, I18NUtils.getInstance().getString("Note"),
				I18NUtils.getInstance().getString(msgKey,args));
	}
	
	  
	  public Map<String,String> getValueAsMap(List<NameValueComposite> nameValues) {
		  if(nameValues == null || nameValues.isEmpty()) {
			  return null;
		  }
		  Map<String,String> nvs = new HashMap<String,String>();
		  for(NameValueComposite nvc: nameValues) {
			  nvs.put(nvc.getName(), nvc.getValue());
		  }
		  return nvs;
	  }
	   
	public List<Object> commonObjectList(String json, Class cls) {
		List<Object> l = new ArrayList<Object>();
		Type type = new TypeToken<List>() {}.getType();
		List<Object> list = JsonUtils.getInstance().fromJson(json, type, false,true);
		for (Object o : list) {
			Object t = JsonUtils.getInstance().fromJson(JsonUtils.getInstance().toJson(o, false), cls, false, true);
			l.add(t);
		}
		return l;
	}
	
	public Class loadClass(String clsName) {
		Class<?> reqCls = null;
		try {
			reqCls = UIUtils.class.getClassLoader().loadClass(clsName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(reqCls == null) {
			throw new RuntimeException(clsName + " Not Found!");
		}
		return reqCls;
	}
	
	/*public String getAccountTopic(String topicPrefix) {
		String id = RWT.getUISession().getAttribute(
				CmtyEventAdmin.CMTY_TOPIC_KEY).toString();
		if(id == null) {
			throw new CommonException("NotValidRequest");
		}
		if(!topicPrefix.endsWith("/")) {
			topicPrefix = topicPrefix + "/";
		}
		return topicPrefix+"sys";
	}*/
	
	public static final String UserAgent = "User-Agent";
	
	public boolean isPCBrowser() {
		return !this.isAndroidBrowser();
	}
	
	public boolean isAndroidBrowser() {
		/*boolean flag = RWT.getClient() instanceof WebClient;
		if(!flag) {
			return false;
		}
		String ue = RWT.getRequest().getHeader(UserAgent);
		
		return ue.contains("Mozilla") && ue.contains("Android");*/
		return false;
	}
	
	public boolean isTabrisClient() {
		 /*  Platform platform = DeviceUtil.getPlatform();
		   return platform == ANDROID || platform == IOS;*/
		return false;
	}
	
	/**
	 * 加载自定义的JS脚本文件
	 * @param basePath 资源所在包名
	 * @param r 资源文件名
	 * @param clsLoader
	 */
	public void requireJs(String basePath, String r, ClassLoader clsLoader) {

		/*JavaScriptLoader loader = RWT.getClient().getService(JavaScriptLoader.class);
		ResourceManager rm = RWT.getApplicationContext().getResourceManager();
		
		if(basePath == null || basePath.trim().equals("")) {
			basePath = "";
		}

		//类的包路径，如/js/RTCDataChannel.js.js
		r = basePath + r;
		if(!rm.isRegistered(r)) {
			//还没有注册，需要注册资源
			rm.register(r,  clsLoader.getResourceAsStream(r));
		}
		//在加载路径为KEY取得资源的存放路径，然后根据此路径加载此文件
		String l = rm.getLocation(r);
		if(l != null) {
			loader.require(l);
		}*/
	
	
	}
	
	public static final String[] RES_DIRS =  {"/css","/img","/js","/theme","/resource"};
	
	/**
	 * 加载自定义的JS脚本文件
	 * @param basePath
	 * @param reses
	 * @param clsLoader
	 */
	public void requireJs(String basePath, String[] reses, ClassLoader clsLoader) {
		/*if(RWT.getClient() instanceof WebClient) {
			//只支持WEB浏览器
			//JavaScriptLoader loader = RWT.getClient().getService(JavaScriptLoader.class);
			//ResourceManager rm = RWT.getApplicationContext().getResourceManager();
			
			if(basePath == null || basePath.trim().equals("")) {
				basePath = "";
			}
			
			for(String r : reses) {
				this.requireJs(basePath, r, clsLoader);
			}
		}	*/
	}
	
	public void registerRes(String basePath, String[] reses, ClassLoader clsLoader) {
		/*ResourceManager rm = RWT.getApplicationContext().getResourceManager();
		if(basePath == null || basePath.trim().equals("")) {
			basePath = "";
		}
		for(String r : reses) {
			r = basePath + reses;
			if(!rm.isRegistered(r)) {
				rm.register(r,  clsLoader.getResourceAsStream(r));
			}
		}*/
	}
	
	public void registerDefaultRes(ClassLoader clsLoader, String res,FileListener listener) {
		 if(res == null || "".equals(res.trim())) {
			 return;
		 }
		 Enumeration<URL> dirs = null;

			try {
				dirs = clsLoader.getResources(res);
				while (dirs.hasMoreElements()) {
	                URL url = dirs.nextElement();  
	                String f = url.getFile();
	                String protocol = url.getProtocol();
	                String filePath = null;
	                if ("file".equals(protocol)) {
	                    filePath = URLDecoder.decode(url.getFile(), "UTF-8");
	                   // System.out.println(filePath);
	                    //findAndAddClassesInPackageByFile(pack, filePath,recursive, classes);  
	                } else if ("bundleresource".equals(protocol)) {
	                    //System.err.println("file类型的扫描");  
	                    // 获取包的物理路径  
	                   // String filePath = url.getFile(); 
	                    URL fileUrl = FileLocator.toFileURL(url);
	                    filePath = URLDecoder.decode(fileUrl.getPath(), "UTF-8"); 
	                    // 以文件的方式扫描整个包下的文件 并添加到集合中  
	                    //findAndAddClassesInPackageByFile(pack, filePath,recursive, classes);  
	                    //System.out.println(filePath);
	                }
	                
	                if(filePath == null) {
	                	continue;
	                }
                     File file = new File(filePath);
	                if(file.isDirectory()) {
	                	for(File subFile : file.listFiles()) {
	                		String fn = f+subFile.getName();
	                		registerDefaultRes(clsLoader, fn,listener);
	                	}
	                } else {
	                	listener.onFile(f, filePath);
	                }
	            }
			} catch (IOException e) {
				e.printStackTrace();
			}  
	
	}
	
	public void registerDefaultRes(ClassLoader classLoader,FileListener listener) {
		
		for(String res : RES_DIRS) {
			this.registerDefaultRes(classLoader, res,listener);
		}
	}
	
	public static interface FileListener{
		public void onFile(String relativePath, String fullPath) ;
	}
	
}
