package com.digitnexus.core.account;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import net.techgy.cmty.core.im.ImAccountManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digitnexus.base.protocol.Response;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.i18n.I18NUtils;
import com.digitnexus.core.permisson.Perm;
import com.digitnexus.core.permisson.PermAction;
import com.digitnexus.core.permisson.PermActionType;
import com.digitnexus.core.permisson.Perms;
import com.digitnexus.core.vo.dept.AccountVo;
import com.google.gson.reflect.TypeToken;

@Component("accountService")
@Path("/accountService")
@Scope("singleton")
public class AccountService {

	@Autowired
	private AccountManager am;
	
	@Autowired
	private ImAccountManager iam;
	
	@POST
	@Path("/query")
	@Transactional
	public String query(@FormParam("body") Map<String,String> params) {
		if(params == null) {
			params = new HashMap<String,String>();
		}
		params.put(Client.CLIENT_ID_KEY, UserContext.getCurrentClientId());
		List<Account> al = am.queryList(params);
		List<AccountVo> alvos = new ArrayList<AccountVo>();
		for(Account a : al) {
			AccountVo vo = this.am.getOneVo(a);
			alvos.add(vo);
		}
		Response resp = new Response(true);
		resp.setData(JsonUtils.getInstance().toJson(alvos, true));
		resp.setClassType(al.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}

	@POST
	@Path("/update")
	@Transactional
	public String update(@FormParam("vos") String vos) {
		List<AccountVo> objList = getFromJson(vos);
		this.am.update(objList);
		Response resp = new Response(true);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}

	@POST
	@Path("/save")
	public String save(@FormParam("vos") String vos) {
		List<AccountVo> objList = getFromJson(vos);
		this.am.save(objList);
		Response resp = new Response(true);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}

	@POST
	@Path("/delete")
	@Transactional
	public String delete(@FormParam("ids") String ids) {
		List<String> idList = JsonUtils.getInstance().getStringValueList(ids, false);
		am.delete(idList);
		Response resp = new Response(true);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}

	private List<AccountVo> getFromJson(String json) {
		List<AccountVo> objList = null;
		try {
			Type type = new TypeToken<List<AccountVo>>() {}.getType();
			objList = JsonUtils.getInstance().fromJson(json, type, false, false);
		} catch (Exception e) {
		}
		return objList;
	}

	@POST
	@Path("/testquery")
	public String queryList( @FormParam("body") String jsonBody) {
		Map<String,String> ps = null;
		if(jsonBody != null) {
			Type type = new TypeToken<Map<String,String>>(){}.getType();
			ps = JsonUtils.getInstance().fromJson(jsonBody, type, false, false);
		}
		List<AccountVo> ql = new ArrayList<AccountVo>();
		
		if(ps == null) {
			ql.addAll(reqList);
		}else {
			String name = ps.get("reqNum");
			if(name == null || "".equalsIgnoreCase(name.trim()))  {
				ql.addAll(reqList);
			}else {
				for(AccountVo rd: this.reqList) {
					if(rd.getName().indexOf(name) != -1) {
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
	
	private List<AccountVo> reqList = new ArrayList<AccountVo>();
	{
		AccountVo a = new AccountVo();
		a.setDefaultClient("閹鍎�");
		a.setEmployee("ZhangSan");
		a.setId("1");
		a.setStatu(Account.STATU_ACTIVE);
		a.setName("ZhangSan1");
		reqList.add(a);
		
		a = new AccountVo();
		a.setDefaultClient("閹鍎�");
		a.setEmployee("ZhangSan");
		a.setId("2");
		a.setStatu(Account.STATU_ACTIVE);
		a.setName("ZhangSan2");
		reqList.add(a);
	}

	//private List<String> curLoginAccount = new ArrayList<String>();
	
	@Path("/login")
	@POST
	@Transactional
	public String login(@FormParam("username") String username,@FormParam("pw") String pw) {
		Response resp = null;
		Map<String,String> m = am.login(username, pw);
		if(m.isEmpty()) {
			resp = new Response(false);
			resp.setMsg(I18NUtils.getInstance().getString("AccountOrPwError"));
			return JsonUtils.getInstance().toJson(resp,false);
		}
		resp = new Response(true);
		resp.setData(JsonUtils.getInstance().toJson(m,true));
		return JsonUtils.getInstance().toJson(resp,false);
	}
	
	@Path("/register")
	@POST
	@Transactional
	public String register(@FormParam("username") String username,
			@FormParam("pw") String pw) {
		String clientId = "02";
		UserContext.init("Admin_ZJ", clientId, Locale.getDefault());
		Response resp = null;
		Account acc = new Account();
		acc.setAccountName(username);
		acc.setClient(UserContext.getCurrentUser().getLoginClient());
		acc.setPassword(pw);
		acc.setStatu(Account.STATU_ACTIVE);
		acc.getRelatedClients().add(acc.getClient());
		acc.setTypeCode(Account.TYPE_COMMON);
		this.am.save(acc);
		
		UserContext.init(username, clientId, Locale.getDefault());
		this.iam.createImAccountForCurrentLogin();
		resp = new Response(true);
		return JsonUtils.getInstance().toJson(resp,false);
	}
	
	@Path("/logout")
	@POST
	@Perms(perms={@Perm(actionType=PermActionType.AssetInRequest,action=PermAction.Query)})
	public String logout() {
		am.logout(UserContext.getAccount().getAccountName());
		Response resp = new Response(true);
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
	
	@Path("/lgClient")
	@GET
	@Perms(perms={@Perm(actionType=PermActionType.AssetInRequest,action=PermAction.Query)})
	public String setLogin(@FormParam("username") String username,@FormParam("pw") String pw,
			@FormParam("clientId") String clientId) {
		Response resp = new Response(true);
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
}
