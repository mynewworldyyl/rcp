package com.digitnexus.core.permisson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digitnexus.base.protocol.Response;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.account.Account;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.vo.dept.GroupVo;

@Component
@Path("/groupService")
@Scope("singleton")
public class GroupService {

	@Autowired
	private GroupManager gm;
	
	@POST
	@Path("/query")
	@Transactional
	public String query(@FormParam("body") Map<String,String> params) {
		if(params == null) {
			params = new HashMap<String,String>();
		}
		params.put(Client.CLIENT_ID_KEY, UserContext.getCurrentClientId());
		List<Group> al = gm.queryList(params);
		List<GroupVo> alvos = new ArrayList<GroupVo>();
		for(Group a : al) {
			GroupVo vo = getOneVo(a);
			alvos.add(vo);
		}
		Response resp = new Response(true);
		resp.setData(JsonUtils.getInstance().toJson(alvos, true));
		resp.setClassType(al.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}

	private GroupVo getOneVo(Group a) {
		GroupVo vo = new GroupVo();
		Set<String> aids = new HashSet<String>();
		if(a.getAccounts() != null) {
			for(Account aid : a.getAccounts()) {
				aids.add(aid.getId());
			}
		}
		vo.setAccounts(aids);
		Set<String> pids = new HashSet<String>();
		if(a.getPermissions() != null) {
			for(Permission aid : a.getPermissions()) {
				pids.add(aid.getId());
			}
		}
		vo.setPermissions(pids);
		vo.setDescription(a.getDescription());
		vo.setId(a.getId());
		vo.setName(a.getName());
		vo.setTypecode(a.getTypecode());
		return vo;
	}

	
	@POST
	@Path("/update")
	@Transactional
	public String update(@FormParam("vos")List<GroupVo> vos) {
		//List<GroupVo> objList = getFromJson(vos);
		this.gm.update(vos);
		Response resp = new Response(true);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}

	@POST
	@Path("/save")
	@Transactional
	public String save(@FormParam("vos") List<GroupVo> vos) {
		this.gm.save(vos);
		Response resp = new Response(true);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}

	@POST
	@Path("/delete")
	@Transactional
	public String delete(@FormParam("ids") List<String> ids) {
		//List<String> idList = JsonUtils.getInstance().getStringValueList(ids, false);
		gm.delete(ids);
		Response resp = new Response(true);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}

	@POST
	@Path("/query1")
	public String queryList(@FormParam("body") Map<String,String> params) {
		Response resp = new Response(true);
		String json = JsonUtils.getInstance().toJson(reqList,true);
		resp.setData(json);
		resp.setClassType(reqList.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
	
	private List<GroupVo> reqList = new ArrayList<GroupVo>();
	{
		GroupVo g = new GroupVo();
		g.setDescription("Test group01");
		g.setId("1");
		g.setName("group01");
		reqList.add(g);
		
	}

}
