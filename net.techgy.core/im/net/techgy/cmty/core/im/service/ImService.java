package net.techgy.cmty.core.im.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import net.techgy.cmty.core.im.FriendGroup;
import net.techgy.cmty.core.im.ImAccount;
import net.techgy.cmty.core.im.ImAccountManager;
import net.techgy.cmty.core.im.ImMessageManager;
import net.techgy.cmty.core.im.vo.FriendVo;
import net.techgy.cmty.core.im.vo.ImAccountVo;
import net.techgy.cmty.core.im.vo.MessageVo;

import org.osgi.service.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digitnexus.base.event.CmtyEventAdmin;
import com.digitnexus.base.protocol.Response;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.base.utils.Utils;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.account.Account;
import com.digitnexus.core.account.AccountManager;
import com.digitnexus.core.osgiservice.impl.SpringContext;

@Component("imService")
@Path("/imService")
@Scope("singleton")
public class ImService {
	
	private ExecutorService exec = Executors.newCachedThreadPool();
	
	@Autowired
	private ImAccountManager fm;
	
	@Autowired
	private AccountManager am;
	
	@Autowired
	private ImMessageManager imMsgManager;
	
	@POST
	@Path("/loginIm")
	@Transactional
	public String loginIm() {
		Account acc = am.getLoginAccount(null);
		if(acc == null) {
			return Utils.getInstance().getResponse(null, false, "AccountNotLogin");
		}
		ImAccountVo ia = fm.getImAccountVo(acc.getAccountName(),false);
		return Utils.getInstance().getResponse(ia, true, null);
	}
	
	@POST
	@Path("/loginoutIm")
	@Transactional
	public String logoutIm() {
		ImAccountVo ia = fm.getImAccountVo(UserContext.getAccount().getAccountName(),false);
		return Utils.getInstance().getResponse(ia, true, null);
	}
	
	@POST
	@Path("/getFriendList")
	@Transactional
	public String getFriendList() {
		Set<FriendVo> fl =this.fm.getFriends();
		return Utils.getInstance().getResponse(fl, true, null);
	}
	
	@POST
	@Path("/updateImAccount")
	@Transactional
	public String updateImAccount(@FormParam("vo") ImAccountVo vo) {
		fm.updateImAccount(vo);
		return Utils.getInstance().getResponse(null, true, null);
	}

	@POST
	@Path("/createGroup")
	@Transactional
	public String newGroup(@FormParam("gn") String gn ) {
		ImAccount ia = this.fm.getImAccount(UserContext.getAccount().getAccountName());
		FriendGroup g = this.fm.createGroup(ia,gn);
		boolean f = g != null;
		
		Response resp = new Response(f);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		if( f) {
			sendRefleshList();
		}
		return respStr;
	}
	
	@POST
	@Path("/updateGroup")
	@Transactional
	public String updateGroup(@FormParam("grp") FriendVo groupVo ) {
		boolean f = this.fm.updateFriendGroup(groupVo);
		Response resp = new Response(f);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		if( f) {
			sendRefleshList();
		}
		return respStr;
	}

	@POST
	@Path("/addFriend")
	public String addFriend(@FormParam("an") String an, @FormParam("gn") String gn) {
		boolean f = this.fm.addFriend(an, gn);
		Response resp = new Response(f);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		if( f) {
			sendRefleshList();
		}
		return respStr;
	}

	@POST
	@Path("/deleteFriend")
	@Transactional
	public String deleteFriend(@FormParam("an") String an, @FormParam("gid") String gid) {
		boolean f = this.fm.deleteFriend(an, gid);
		Response resp = new Response(f);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		if( f) {
			sendRefleshList();
		}
		return respStr;
	}
	
	@POST
	@Path("/deleteGroup")
	@Transactional
	public String deleteGroup(@FormParam("gn") String gn) {
		boolean f = this.fm.deleteGroup(gn);
		Response resp = new Response(f);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		if( f) {
			sendRefleshList();
		}
		return respStr;
	}

	
	@POST
	@Path("/moveFriendToGroup")
	@Transactional
	public String moveFriendToGroup(@FormParam("an") String an,
			@FormParam("from") String from, @FormParam("to") String to) {
		boolean f = this.fm.moveFriendToGroup(an,from,to);
		Response resp = new Response(f);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		if( f) {
			sendRefleshList();
		}
		return respStr;
	}
	
	@POST
	@Path("/sendMsg")
	@Transactional
	public String sendChatMsg(@FormParam("msg") MessageVo msg) {
		String respStr = null;
		Account acc = this.am.getLoginAccount(msg.getTo());
		if(acc == null) {
			this.imMsgManager.saveMessage(msg);
		} else {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put(MessageVo.MSG_KEY, msg);
			Event event = new Event(msg.getTopic(),params);
			CmtyEventAdmin.getEventBus().postEvent(event);
		}
		
		Response resp = new Response(true);
		respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}
	
	@POST
	@Path("/getMsg")
	@Transactional
	public String getChatMsg() {
		final String an = UserContext.getAccount().getAccountName();
		final String clientId = UserContext.getCurrentClientId();
		final Locale lo = UserContext.getCurrentUser().getLocale();
		this.exec.execute(new Runnable(){
			public void run() {
				UserContext.init(an, clientId, lo);
				ImMessageManager msgManager = SpringContext.getContext().getBean(ImMessageManager.class);
				List<MessageVo> messages = msgManager.getNewImMessages();
				if(messages == null) {
					return;
				}
				for(MessageVo vo : messages) {
					sendChatMsg(vo);
				}
			}
		});
		Response resp = new Response(true);
		String respStr = JsonUtils.getInstance().toJson(resp, true);
		return respStr;
	}
	
	
	@POST
	@Path("/queryFriend")
	@Transactional
	public String queryFriend(@FormParam("an") String an,@FormParam("aid") String aid) {
		List<FriendVo> fl = this.fm.queryFriend(an,aid);
		if(fl ==null) {
			fl = Collections.emptyList();
		}
		return Utils.getInstance().getResponse(fl, fl != null, null);
	}
	
	private void sendRefleshList() {
		String fl = getFriendList();
		if(fl == null) {
			return;
		}
		MessageVo msg = new MessageVo();
		msg.setMsgType(MessageVo.TYPE_REFLESH_LIST);
		msg.setModelId(MessageVo.MODEL_FRIEND);
		msg.setContent(fl);
		msg.setTo(UserContext.getAccount().getAccountName());
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(MessageVo.MSG_KEY, msg);
		Event event = new Event(MessageVo.CMTY_FRIEND_TOPIC+msg.getTo(),params);
		CmtyEventAdmin.getEventBus().postEvent(event);
		
	}
	
	/*@POST
	@Path("/loginIm")
	@Transactional*/
	public String getTestImAccoutVo() {
		ImAccountVo vo = new ImAccountVo();
		
		vo.setAccountVo(this.am.getOneVo(UserContext.getAccount()));
		vo.setId("01");
		vo.setNickname("Admin_ZJ");
		vo.setTypecode(ImAccount.IM_TYPECODE_COMMON);
		
		//group
		FriendVo fvo =new FriendVo();
		fvo.setAccountName(vo.getAccountVo().getName());
		fvo.setName(ImAccount.IM_TYPECODE_COMMON);
		fvo.setId("1");
		fvo.setNickname("Test01");
		fvo.nodeType = FriendVo.class.getName();
		
		//Friend
		FriendVo f = new FriendVo();
		f.setGroupId(fvo.getId());
		f.setAccountName("Test02");
		f.setId("01");
		f.setNickname("Test02");
		f.setFriendList(null);
		f.setName(f.getNickname());
		f.nodeType = ImAccountVo.class.getName();
		fvo.getFriendList().add(f);
		
		//add group to im account vo
		vo.getFriendGroups().add(fvo);
		
		Response resp = new Response(true);
		resp.setData(JsonUtils.getInstance().toJson(vo, true));
		resp.setClassType(vo.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}
	
}
