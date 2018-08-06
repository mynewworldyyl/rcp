package net.techgy.im.net;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;

import net.techgy.common.models.IMessage;
import net.techgy.im.handler.IQueueManager;
import net.techgy.osig.service.impl.RapServiceImp;
import net.techgy.osig.service.impl.RapSession;
import net.techgy.usercenter.AccountService;
import net.techgy.usercenter.LoginManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionManager implements ISessionManager{

	private Logger logger = Logger.getLogger(this.getClass());
	
	private Map<Long,ISession> sessions = new HashMap<Long,ISession>();
	
	private Map<String,ISession> userIdToSession = new HashMap<String,ISession>();
	
	@Autowired
	private Set<ISessionListener> sessionListeners = new HashSet<ISessionListener>();
	
	@Autowired
	private IIDProvider idProvider;
	
	@Autowired
	private IQueueManager queueManager;
	
	@Autowired
	private LoginManager loginManager;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private RapServiceImp rapService;
	
	@Override
	public void addSession(ISession session) {
		this.sessions.put(session.getSessionId(), session);
		this.userIdToSession.put(session.getUserId(), session);
		Iterator<ISessionListener> ite = this.sessionListeners.iterator();
		while(ite.hasNext()){
			ISessionListener ls = ite.next();
			ls.sessoionOpened(session);
		}
	}

	@Override
	public void removeSession(Long sessionId) {
		ISession s = this.getSession(sessionId);
		Iterator<ISessionListener> ite = this.sessionListeners.iterator();
		while(ite.hasNext()){
			ISessionListener ls = ite.next();
			ls.sessoionClose(s);
		}
		if(s!= null) {
			this.userIdToSession.remove(s.getUserId());
		}
		this.sessions.remove(sessionId);
	}

	@Override
	public ISession getSession(Long sessionId) {
		// TODO Auto-generated method stub
		return this.sessions.get(sessionId);
	}

	@Override
	public ISession createSession(String msg,Session wSession,String un,String pw) {
		//logger.debug("Login Username:" + un + "Passowrd:" + pw);
		ISession session = null;
		if(wSession != null ) {
			 session = new SessionImpl(idProvider.getLong(),msg,wSession,this,un,pw);
		} else {
			session = new RapSession(un,this,this.idProvider.getLong(),rapService);
		}
		return session;
	}
	
	

	@Override
	public void pushMessage(IMessage msg) {
		// TODO Auto-generated method stub
		this.queueManager.pushMessage(msg);
	}

	@Override
	public ISession getSessionByUserId(String userId) {
		// TODO Auto-generated method stub
		return this.userIdToSession.get(userId);
	}

	public Map<Long, ISession> getSessions() {
		return sessions;
	}

	public void setSessions(Map<Long, ISession> sessions) {
		this.sessions = sessions;
	}

	public Map<String, ISession> getUserIdToSession() {
		return userIdToSession;
	}

	public void setUserIdToSession(Map<String, ISession> userIdToSession) {
		this.userIdToSession = userIdToSession;
	}

	public IQueueManager getQueueManager() {
		return queueManager;
	}

	public void setQueueManager(IQueueManager queueManager) {
		this.queueManager = queueManager;
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}	
	
	
}
