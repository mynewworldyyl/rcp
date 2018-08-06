package net.techgy.uidef.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.techgy.uidef.ReqDef;
import net.techgy.uidef.UIDefUtils;

import org.springframework.stereotype.Component;

import com.digitnexus.base.utils.JsonUtils;

@Component
public class RequestService {
	
	private List<RequestDemo> reqList = new ArrayList<RequestDemo>();
	{
		
		RequestDemo rd = null;
		List<RequestItemDemo> items = null;
		RequestItemDemo item = null;
		
		rd = new RequestDemo();
		rd.setCheckPerson("ZhangSan1");
		rd.setCreatedPerson("ZhangSan1");
		rd.setReqNum("req01");
		rd.setSourceSite("site1");
		rd.setSourceSite("site2");
		
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
		reqList.add(rd);
		
		rd = new RequestDemo();
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
		rd.setItems(items);
		reqList.add(rd);
	}
	
	public String getReqDef(String cls) {
		ReqDef def = UIDefUtils.getInstance().getReqDef(cls);
		return JsonUtils.getInstance().toJson(def,false);
	}
	
	public String queryReqList(String cls,Map<String,String> params) {
		return JsonUtils.getInstance().toJson(reqList,false);
	}
	
    public String getReq(String cls,String reqNum) {
		Random r = new Random(System.currentTimeMillis());
		int index = r.nextInt()/2;
		return JsonUtils.getInstance().toJson(reqList.get(index),false);
	}
    
    public String saveReq(String clsName,String reqJson) {
    	Class<?> cls;
		try {
			cls = RequestService.class.getClassLoader().loadClass(clsName);
			RequestDemo req = (RequestDemo)JsonUtils.getInstance().fromJson(reqJson, cls, true, false);
	    	this.reqList.add(req);
			return "success";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return "fail";
	}
    
    public String delReq(String cls,String reqNum) {
		
		return "";
	}
 
    public String updateReq(String cls,String req) {
		
		return "";
	}
    
    public String extAction(String actionComponentName,String method,Map<String,String> params) {
		
		return "";
	}
}
