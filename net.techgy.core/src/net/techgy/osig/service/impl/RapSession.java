package net.techgy.osig.service.impl;

import net.techgy.common.models.IMessage;
import net.techgy.common.models.MessageImpl;
import net.techgy.common.models.MessageState;
import net.techgy.common.models.MessageType;
import net.techgy.common.models.MsgHeader;
import net.techgy.im.net.ISession;
import net.techgy.im.net.ISessionManager;

import org.apache.log4j.Logger;

import com.digitnexus.base.utils.JsonUtils;

public class RapSession implements ISession,RapChannel {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private ISessionManager manager = null;
	
	private String accountName = null;
	
	private long sessionId = -1;
	
	private RapServiceImp rapService;	
	
	public RapSession(String accountName,ISessionManager manager,long sessionId, RapServiceImp rapService) {
		this.manager=manager;
		this.accountName = accountName;
		this.sessionId = sessionId;
		this.rapService = rapService;
	}
	
	@Override
	public long getSessionId() {		
		return this.sessionId;
	}

	@Override
	public String getUserId() {
		return accountName;
	}

	@Override
	public boolean writeMessage(IMessage msg) {	
		rapService.notifyMessage(msg.getUserId(), msg);
		return true;
	}

	@Override
	public void onTextMessage(String json) {
		IMessage message = JsonUtils.getInstance().fromJson(json, MessageImpl.class,false,false);
		if(message == null){
			logger.warn("receive NULL message");
			return;
		}
		this.manager.pushMessage(message);
	}
	
	public void onTextMessage(IMessage message) {
		if(message == null){
			logger.warn("receive NULL message");
			return;
		}
		this.manager.pushMessage(message);
	}

	@Override
	public void onClose(int status) {
		logger.debug("onClose");
		this.manager.removeSession(this.getSessionId());
	}

	@Override
	public void onOpen() {
		MessageImpl msg = new MessageImpl();
		msg.addHeader(MsgHeader.USERNAME, accountName);
		msg.setType(MessageType.RESP_LOGIN);
		if(!this.manager.getLoginManager().isLogin(this.accountName)) {
			msg.setStatus(MessageState.LOGIN_ERR_UN_PW);	
			this.writeMessage(msg);
			return;	
		}
		msg.setStatus(MessageState.OK);
		this.manager.addSession(this);
		this.writeMessage(msg);
	}

	public void setRapService(RapServiceImp rapService) {
		this.rapService = rapService;
	}	
	
	
}
