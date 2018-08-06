package net.techgy.cmty.core.im.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import net.techgy.cmty.core.im.ImChatRoomManager;
import net.techgy.cmty.core.im.vo.ChatRoomVo;
import net.techgy.cmty.core.im.vo.MessageVo;

import org.osgi.service.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digitnexus.base.event.CmtyEventAdmin;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.base.utils.Utils;
import com.digitnexus.core.UserContext;

@Component
@Path("/roomService")
@Scope("singleton")
public class RoomService {

	@Autowired
	private ImChatRoomManager crManager;
	
	
	@POST
	@Path("/loadChatRoomsAcces")
	@Transactional
	public String loadChatRoomsAcces(@FormParam("rid") String rid) {
        ChatRoomVo vo = this.crManager.loadChatRoomsAcces(rid);
		String respStr = Utils.getInstance().getResponse(vo, true, null);
		return respStr;
	}
	
	@POST
	@Path("/update")
	@Transactional
	public String update(@FormParam("roomVo") ChatRoomVo roomVo) {
        ChatRoomVo vo = this.crManager.updateRoom(roomVo);
		String respStr = Utils.getInstance().getResponse(vo, true, null);
		if( vo != null) {
			sendRefleshList(vo);
		}
		return respStr;
	}
	
	@POST
	@Path("/create")
	@Transactional
	public String create(@FormParam("rn") String rn) {
        ChatRoomVo vo = this.crManager.createRoom(rn);
		String respStr = Utils.getInstance().getResponse(vo, true, null);
		if( vo != null) {
			sendRefleshList(vo);
		}
		return respStr;
	}
	
	@POST
	@Path("/delete")
	@Transactional
	public String delete(@FormParam("rid") String rid) {
        rid = this.crManager.deleteRoom(rid);
		String respStr = Utils.getInstance().getResponse(rid, true, null);
		if( rid != null) {
			//sendRefleshList(rid);
		}
		return respStr;
	}
	
	@POST
	@Path("/query")
	@Transactional
	public String query(@FormParam("rn") String rn,@FormParam("rid") String rid) {
        List<ChatRoomVo> l = this.crManager.queryRoom(rn,rid);
		String respStr = Utils.getInstance().getResponse(l, true, null);
		return respStr;
	}
	
	@POST
	@Path("/add")
	@Transactional
	public String add(@FormParam("rid") String rid) {
        boolean f = this.crManager.add(rid);
		return Utils.getInstance().getResponse(null, f, null);
	}
	
	@POST
	@Path("/added")
	@Transactional
	public String added(@FormParam("an") String an,@FormParam("rid") String rid) {
        boolean f = this.crManager.added(an,rid);
		String respStr = Utils.getInstance().getResponse(null, f, null);
		return respStr;
	}
	
	private void sendRefleshList(ChatRoomVo vo) {
		if(vo == null) {
			return;
		}
		MessageVo msg = new MessageVo();
		msg.setModelId(MessageVo.MODEL_ROOM);
		msg.setMsgType(MessageVo.TYPE_REFLESH_LIST);
		msg.setContent(JsonUtils.getInstance().toJson(vo, true));
		msg.setTo(UserContext.getAccount().getAccountName());
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(MessageVo.MSG_KEY, msg);
		Event event = new Event(MessageVo.CMTY_FRIEND_TOPIC + vo.getId(),params);
		CmtyEventAdmin.getEventBus().postEvent(event);
		
	}
	
}
