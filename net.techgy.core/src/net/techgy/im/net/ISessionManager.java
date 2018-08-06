package net.techgy.im.net;

import javax.websocket.Session;

import net.techgy.common.models.IMessage;
import net.techgy.usercenter.AccountService;
import net.techgy.usercenter.LoginManager;

public interface ISessionManager {

	void addSession(ISession session);
	void removeSession(Long sessionId);
	ISession getSession(Long sessionId);
	ISession getSessionByUserId(String userId);
	ISession createSession(String msg,Session session,String un,String pw);
	void pushMessage(IMessage msg);
	
	LoginManager getLoginManager();
	AccountService getAccountService();
	
}
