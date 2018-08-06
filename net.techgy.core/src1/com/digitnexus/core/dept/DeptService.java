package com.digitnexus.core.dept;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digitnexus.base.protocol.Response;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.core.i18n.I18NUtils;
import com.digitnexus.core.vo.dept.DepartmentVo;

@Component("deptService")
@Path("/deptService")
@Scope("singleton")
public class DeptService {

	@Autowired
	private ClientManager clientManager;
	
    @POST
 	@Path("/ext")
    public String extAction(@FormParam("componentName") String actionComponentName,
    		String method, @FormParam("body")  Map<String,String> params) {
		Response resp = new Response(true);
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
    
    @POST
	@Path("/setAsFactory")
    @Transactional
    public String setAsFactory(@FormParam("name") String name, @FormParam("desc") String desc, 
    		@FormParam("deptId") String deptId,@FormParam("an") String accountName,
    		@FormParam("pw") String pw,@FormParam("clientType") String clientType) {
    	Response resp = this.clientManager.createClient(name, desc, deptId,accountName,pw,clientType);
    	if(resp == null) {
    		resp = new Response(false,I18NUtils.getInstance().getString("NotKnowException"));
    	}
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
    
    @GET
	@Path("/clientTypeList")
    @Transactional
    public String clientTypeList() {
    	Response resp = new Response(true);
    	Map<String,String> l = this.clientManager.getCreatedList();
    	String innerJson = JsonUtils.getInstance().toJson(l, true);
    	resp.setData(innerJson);
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
	
	private List<DepartmentVo> reqList = new ArrayList<DepartmentVo>();
	{
		DepartmentVo dept = new DepartmentVo();
		dept.setDesc("desc 01");
		dept.setId("1");
		dept.setName("总部");
		dept.setParentId(null);
		
		DepartmentVo subdept1 = new DepartmentVo();
		subdept1.setDesc("desc 2");
		subdept1.setId("2");
		subdept1.setName("工厂");
		subdept1.setParentId("1");
		dept.getSubs().add(subdept1);
		
		DepartmentVo subdept2 = new DepartmentVo();
		subdept2.setDesc("desc 2");
		subdept2.setId("2");
		subdept2.setName("工厂");
		subdept2.setParentId("1");
		dept.getSubs().add(subdept2);
		
		reqList.add(dept);
		
		dept = new DepartmentVo();
		dept.setDesc("desc 3");
		dept.setId("7");
		dept.setName("总部2");
		dept.setParentId(null);
		
		subdept1 = new DepartmentVo();
		subdept1.setDesc("desc 4");
		subdept1.setId("4");
		subdept1.setName("工厂2");
		subdept1.setParentId("7");
		dept.getSubs().add(subdept1);
		
		subdept2 = new DepartmentVo();
		subdept2.setDesc("desc 5");
		subdept2.setId("5");
		subdept2.setName("工厂5");
		subdept2.setParentId("7");
		dept.getSubs().add(subdept2);
		
		reqList.add(dept);
	}
}
