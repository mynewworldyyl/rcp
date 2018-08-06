package com.digitnexus.core.uidef.demo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.digitnexus.base.protocol.Response;
import com.digitnexus.base.request.IRequestService;
import com.digitnexus.base.uidef.BaseDef;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.core.uidef.demo.common.AssetInRequestDemo;
import com.digitnexus.core.uidef.demo.common.RequestItemDemo;
import com.digitnexus.core.uidef.service.UIDefManager;
import com.google.gson.reflect.TypeToken;

@Component("requestDemo")
@Path("/requestDemo")
@Scope("singleton")
public class RequestService implements IRequestService{
	
	@Autowired
	private UIDefManager uiDefManager;
	
	@GET
	@Path("/def")
	public String getReqDef(@QueryParam("cls") String cls) {
		Response resp = new Response(true);
		BaseDef def = uiDefManager.getDef(cls);
		String json = JsonUtils.getInstance().toJson(def,true);
		resp.setData(json);
		resp.setClassType(def.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
	
	@POST
	@Path("/query")
	public String queryReqList(@FormParam("cls") String cls, @FormParam("body") String jsonBody) {
		Map<String,String> ps = null;
		if(jsonBody != null) {
			Type type = new TypeToken<Map<String,String>>(){}.getType();
			ps = JsonUtils.getInstance().fromJson(jsonBody, type, false, false);
		}
		List<AssetInRequestDemo> ql = new ArrayList<AssetInRequestDemo>();
		
		if(ps == null) {
			ql.addAll(reqList);
		}else {
			String name = ps.get("reqNum");
			if(name == null || "".equalsIgnoreCase(name.trim()))  {
				ql.addAll(reqList);
			}else {
				for(AssetInRequestDemo rd: this.reqList) {
					if(rd.getReqNum().indexOf(name) != -1) {
						ql.add(rd);
					}
				}
			}
		}
		Response resp = new Response(true);
		String json = JsonUtils.getInstance().toJson(ql,true);
		resp.setData(json);
		//resp.setClassType(def.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
	
	@GET
	@Path("/detail")
    public String getReq(@FormParam("cls") String cls,@FormParam("reqNum") String reqNum) {
		AssetInRequestDemo req = null;
    	for(AssetInRequestDemo rd: this.reqList) {
    		if(reqNum.equals(rd.getReqNum())) {
    			req = rd;
    			break;
    		}
    	}
    	Response resp =null;
    	if(req == null) {
    		 resp = new Response(false);
    		 resp.setMsg("Request not found");
    	}else {
    		resp = new Response(true);
    		resp.setData(JsonUtils.getInstance().toJson(req,true));
    	}
		//String json = JsonUtils.getInstance().toJson(ql,true);
		//resp.setData(json);
		//resp.setClassType(def.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
    
	
    @POST
	@Path("/save")
    public String saveReq(@FormParam("cls") String clsName,@FormParam("req") String reqJson) {
    	Class<?> cls;
		try {
			cls = RequestService.class.getClassLoader().loadClass(clsName);
			AssetInRequestDemo req = (AssetInRequestDemo)JsonUtils.getInstance().fromJson(reqJson, cls, false, false);
			AssetInRequestDemo er = null;
			for(AssetInRequestDemo r : this.reqList) {
	    		if(r.getReqNum().equals(req.getReqNum())) {
	    			er = r;
	    			break;
	    		}
	    	}
			if(er == null) {
				this.reqList.add(req);
			}else {
				this.reqList.remove(er);
    			this.reqList.add(req);
			}
	    	Response resp = new Response(true);
			//String json = JsonUtils.getInstance().toJson(ql,true);
			//resp.setData(json);
			//resp.setClassType(def.getClass().getName());
			String respStr = JsonUtils.getInstance().toJson(resp,false);
			return respStr;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Response resp = new Response(false);
			resp.setMsg(e.getLocalizedMessage());
			//String json = JsonUtils.getInstance().toJson(ql,true);
			//resp.setData(json);
			//resp.setClassType(def.getClass().getName());
			String respStr = JsonUtils.getInstance().toJson(resp,false);
			return respStr;
		}
	}
    
    @POST
 	@Path("/delete")
    public String delReq(@FormParam("cls") String cls, @FormParam("reqNum") String reqNum) {
		System.out.println("delete: " + cls + " for " + reqNum);
		for(AssetInRequestDemo r : this.reqList) {
    		if(r.getReqNum().equals(reqNum)) {
    			this.reqList.remove(r);
    			break;
    		}
    	}
		
		Response resp = new Response(true);
		//String json = JsonUtils.getInstance().toJson(ql,true);
		//resp.setData(json);
		//resp.setClassType(def.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
 
    @POST
 	@Path("/update")
    public String updateReq(String cls,String req) {
    	System.out.println("update: " + cls + " for " + req);
    	Response resp = new Response(true);
		//String json = JsonUtils.getInstance().toJson(ql,true);
		//resp.setData(json);
		//resp.setClassType(def.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
    
    @POST
 	@Path("/export")
    public String export(String cls,String req) {
    	System.out.println("update: " + cls + " for " + req);
    	Response resp = new Response(true);
		//String json = JsonUtils.getInstance().toJson(ql,true);
		//resp.setData(json);
		//resp.setClassType(def.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
    
    @POST
 	@Path("/ext")
    public String extAction(@FormParam("componentName") String actionComponentName,
    		String method, @FormParam("body") String jsonBody) {
    	Type type = new TypeToken<Map<String,String>>(){}.getType();
		Map<String,String> ps = JsonUtils.getInstance().fromJson(jsonBody, type, false, false);
		Response resp = new Response(true);
		//String json = JsonUtils.getInstance().toJson(ql,true);
		//resp.setData(json);
		//resp.setClassType(def.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
    
    private List<AssetInRequestDemo> reqList = new ArrayList<AssetInRequestDemo>();
	{
		
		AssetInRequestDemo rd = null;
		List<RequestItemDemo> items = null;
		RequestItemDemo item = null;
		
		rd = new AssetInRequestDemo();
		rd.setCheckPerson("ZhangSan1");
		rd.setCreatedPerson("ZhangSan1");
		rd.setReqNum("req01");
		rd.setSourceSite("site1");
		rd.setSourceSite("site2");
		rd.setEnd(true);
		
		items = new ArrayList<RequestItemDemo>();
		
		item = new RequestItemDemo();
		item.setItemId("item01");
		item.setName("ItemName01");
		item.setProjectName("Project01");
		item.setType("Changeng");
		item.setUnitPrice(22.0f);
		item.setVendorName("宝刚");
		items.add(item);
		rd.setItems(items);
		
		item = new RequestItemDemo();
		item.setItemId("item11");
		item.setName("ItemName11");
		item.setProjectName("Project01");
		item.setType("Changeng");
		item.setUnitPrice(22.0f);
		item.setVendorName("宝刚");
		items.add(item);
		rd.setItems(items);
		
		item = new RequestItemDemo();
		item.setItemId("item21");
		item.setName("ItemName21");
		item.setProjectName("Project01");
		item.setType("Changeng");
		item.setUnitPrice(56.0f);
		item.setVendorName("宝刚");
		items.add(item);
		rd.setItems(items);
		
		item = new RequestItemDemo();
		item.setItemId("item055");
		item.setName("ItemName31");
		item.setProjectName("Project01");
		item.setType("Changeng");
		item.setUnitPrice(22.0f);
		item.setVendorName("宝刚");
		items.add(item);
		rd.setItems(items);
		
		reqList.add(rd);
		
		rd = new AssetInRequestDemo();
		rd.setCheckPerson("ZhangSan2");
		rd.setCreatedPerson("ZhangSan2");
		rd.setReqNum("req02");
		rd.setSourceSite("site1");
		rd.setSourceSite("site2");
		
		items = new ArrayList<RequestItemDemo>();
		
		item = new RequestItemDemo();
		item.setItemId("item02");
		item.setName("ItemName02");
		item.setProjectName("Project01");
		item.setType("Changeng");
		item.setUnitPrice(233.0f);
		item.setVendorName("宝刚");
		items.add(item);
		
		item = new RequestItemDemo();
		item.setItemId("item03");
		item.setName("ItemName03");
		item.setProjectName("Project01");
		item.setType("Changeng");
		item.setUnitPrice(23.0f);
		item.setVendorName("宝刚");
		items.add(item);
		
		item = new RequestItemDemo();
		item.setItemId("item04");
		item.setName("ItemName05");
		item.setProjectName("Project01");
		item.setType("Changeng");
		item.setUnitPrice(22f);
		item.setVendorName("宝刚");
		items.add(item);
		
		item = new RequestItemDemo();
		item.setItemId("item06");
		item.setName("ItemName06");
		item.setProjectName("Project01");
		item.setType("Changeng");
		item.setUnitPrice(77f);
		item.setVendorName("鞍刚");
		items.add(item);
		
		rd.setItems(items);
		reqList.add(rd);
	}
}
