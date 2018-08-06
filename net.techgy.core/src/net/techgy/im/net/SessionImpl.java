package net.techgy.im.net;

import java.io.IOException;

import javax.websocket.Session;

import net.techgy.common.models.IMessage;

import org.apache.log4j.Logger;

import com.digitnexus.base.utils.JsonUtils;


public class SessionImpl /*extends MessageInbound*/ implements ISession {

	private Logger logger = Logger.getLogger(this.getClass());

	private String msg;
	private Session wSession;

	private ISessionManager manager = null;
	private long sessionId = -1;
	private String userId = null;
	private String un;
	private String pw;
	
	public SessionImpl(Long id,String msg, Session wSession,
			ISessionManager manager,String un,String pw) {
		this.msg = userId;
		this.userId = un;
		this.wSession = wSession;
		this.manager = manager;
		this.sessionId = id;
		this.un = un;
		this.pw = pw;
	}

	@Override
	public long getSessionId() {
		// TODO Auto-generated method stub
		return this.sessionId;
	}

	
	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.userId;
	}


	@Override
	public boolean writeMessage(IMessage msg) {
        String json =  JsonUtils.getInstance().toJson(msg,false);
        try {
			this.wSession.getBasicRemote().sendText(json);
		} catch (IOException e) {
			//e.printStackTrace();
			logger.warn(e.getMessage());
			return false;
		}
		return true;
	}

	
}