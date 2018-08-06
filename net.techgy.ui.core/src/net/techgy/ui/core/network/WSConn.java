package net.techgy.ui.core.network;

import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.utils.I18NManager;
import com.digitnexus.base.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import net.techgy.cmty.service.CmtyServiceProxy;
import net.techgy.cmty.service.DataDto;

public class WSConn {

private static final WSConn instance = new WSConn();
	
	public static WSConn ins() {
		return instance;
	}
	
	private WSConn(){
		
	}
	
	private String loginKey = null;
	private String loginClient = null;
	
	public DataDto call(ActionDef ad, Map<String,String> params) {
		String[] args = ad.getUrl().split("/");
		return this.call(args[0], args[1], params);
	}
	
	public DataDto call(String module,String method) {
		String[] args = module.split("/");
		if(args.length == 2) {
			module = args[0];
			method=args[1];
		}
		
		DataDto dto = new DataDto();
		dto = this.call(module, method, dto);
		return dto;
	}
	
	public DataDto call(String module, Map<String,String> params) {
		String[] args = module.split("/");
		String method = null;
		module = args[0];
		method=args[1];
		
		DataDto dto = this.call(module, method, params);
		return dto;
	}


	public DataDto call(String module,String method, Map<String,String> params) {
		String[] args = module.split("/");
		if(args.length == 2) {
			module = args[0];
			method=args[1];
		}
		if(params == null) {
			params = new HashMap<String,String>();
		}
		
		Locale l = I18NManager.getInstance().getLocale();
		String lstr = l.getLanguage()+"," + l.getCountry();
		params.put(UIConstants.REQ_USER_LOCALE, lstr);
		
		String jsonParam = JsonUtils.getInstance().toJson(params, false);
		DataDto dto = new DataDto();
		dto.setClassType(Map.class.getName());
		dto.setData(jsonParam);
		
		dto = this.call(module, method, dto);
		
		return dto;
	}
	
	public DataDto call(String module,String method, DataDto dto) {
		String[] args = module.split("/");
		if(args.length == 2) {
			module = args[0];
			method=args[1];
		}
		dto.setLoginKey(this.loginKey);
		dto.setClientId(this.loginClient);
		
		CmtyServiceProxy proxy = new CmtyServiceProxy();
		try {
			dto = proxy.service(module,method,dto);
		} catch (net.techgy.cmty.service.Exception e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(dto.isSuccess()) {
			if(dto.getContainerType() != null && dto.getClassType() != null) {

				Class<?> eltCls = null;
				Class<?> conCls = null;
				try {
					conCls = WSConn.class.getClassLoader().loadClass(dto.getContainerType());
					eltCls = WSConn.class.getClassLoader().loadClass(dto.getClassType());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if(conCls == null) {
					return dto;
				}
				
				if(Map.class.isAssignableFrom(conCls)) {
					Map<String,Object> objMap=new HashMap<String,Object>();
					Map<String,String> jsonMap = JsonUtils.getInstance().getStringMap(dto.getData(), false);
					if(objMap != null) {
						for(Map.Entry<String, String> e: jsonMap.entrySet()) {
							Object o = JsonUtils.getInstance().fromJson(e.getValue(), eltCls, false, false);
							objMap.put(e.getKey(), o);
						}
					}
					dto.setObj(objMap);
				}else if(Collection.class.isAssignableFrom(conCls)){
					List<Object> objs=new ArrayList<Object>();
					
					Type type = new TypeToken<List>(){}.getType();
					List jsonList = JsonUtils.getInstance().fromJson(dto.getData(), type, false, true);
					
					if(jsonList != null) {
						for(Object e: jsonList) {
							String objJson = JsonUtils.getInstance().toJson(e, false);
							Object obj = JsonUtils.getInstance().fromJson(objJson, eltCls, false, false);
							objs.add(obj);
						}
					}
					dto.setObj(objs);
				}
				
				//dto.setObj(dataObject);
			
			} else if( dto.getClassType() != null && !"".equals(dto.getClassType().trim())){
				
				Class<?> cls = null;
				try {
					//if()
					cls = WSConn.class.getClassLoader().loadClass(dto.getClassType());
				} catch (ClassNotFoundException e) {
					//e.printStackTrace();
				}
				if(cls == null) {
					return dto;
				}
				Object dataObject=null;
				try {
					dataObject = JsonUtils.getInstance().fromJson(dto.getData(), cls, false, false);
				} catch (Exception e) {
					//e.printStackTrace();
				}
				
				if(dataObject == null) {
					try {
						dataObject = JsonUtils.getInstance().fromJson(dto.getData(), cls, true, true);
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}
				
				if(dataObject == null) {
					try {
						dataObject = JsonUtils.getInstance().fromJson(dto.getData(), cls, false, true);
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}
				
				
				if(dataObject == null) {
					try {
						dataObject = JsonUtils.getInstance().fromJson(dto.getData(), cls, true, false);
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}
				
				dto.setObj(dataObject);
				
			}
		}
		return dto;
	}
	
	
	public DataDto logout() {
		
		DataDto dto = new DataDto();
		dto.setLoginKey(this.loginKey);
		dto.setClientId(this.loginClient);		
		dto = this.call("accountService","logout", dto);
		if(dto.isSuccess()) {
			this.loginClient = null;
			this.loginKey = null;
		}
		return dto;
	}
	
	
	public boolean isLogin() {
		return this.loginKey != null;
	}
	
	public boolean isLoginClient() {
		return this.loginClient != null;
	}
	
	
	public DataDto login(String un,String pw){
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", un);
		params.put("pw", pw);
		
		DataDto dto = new DataDto();
		Type type = new TypeToken<HashMap<String,String>>(){}.getType();
		dto.setData(JsonUtils.getInstance().toJson(params, type, false));
		dto.setClassType(Map.class.getName());
		CmtyServiceProxy proxy = new CmtyServiceProxy();
		try {
			//登陆账号
			dto = proxy.service("accountService","login",dto);
			if(dto.isSuccess()) {
				params = JsonUtils.getInstance().fromJson(dto.getData(), type);
				dto.setClientId(params.keySet().iterator().next());
				//登陆租户
				dto = proxy.service("accountService", "lgClient", dto);
				if(dto.isSuccess()) {
					this.loginKey=dto.getLoginKey();
					this.loginClient=dto.getClientId();
				}
			} 
		} catch (RemoteException  e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	public DataDto loginClient(DataDto dto){

		CmtyServiceProxy proxy = new CmtyServiceProxy();
		try {
			//登陆租户
			dto = proxy.service("accountService", "lgClient", dto);
			if(dto.isSuccess()) {
				this.loginClient=dto.getClientId();
			}
		} catch (RemoteException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}

}
