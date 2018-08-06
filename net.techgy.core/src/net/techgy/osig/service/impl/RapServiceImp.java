package net.techgy.osig.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import net.techgy.common.models.IMessage;
import net.techgy.common.models.MessageType;
import net.techgy.im.manager.MessageManager;
import net.techgy.im.net.ISessionManager;
import net.techgy.osig.service.IImService;
import net.techgy.osig.service.IMessageListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class RapServiceImp implements IImService{

	private final Map<String,List<IMessageListener>> listeners = new HashMap<String,List<IMessageListener>>();
	
	@Autowired
	private MessageManager messageManager;
	
	//private Map<String,RapSession> accountToSessions = new HashMap<String,RapSession>();
	private int processorNum = Runtime.getRuntime().availableProcessors();
	private Executor worker = Executors.newFixedThreadPool(processorNum);
	
	@Autowired
	private ISessionManager manager;
	
	@Override
	public void addImListener(String accountName,IMessageListener listener) {
		List<IMessageListener> l = this.listeners.get(accountName);
		if(l == null) {
			l = new ArrayList<IMessageListener>();
			listeners.put(accountName,l);
		}
		l.add(listener);
	}

	@Override
	public void removeImListener(String accountName) {
		List<IMessageListener> l = this.listeners.get(accountName);
		if(l == null) {
			return;
		}
		listeners.remove(accountName);
	}
	
	public void notifyMessage(final String accountName,final IMessage msg) {
		worker.execute(new Runnable(){
			public void run() {
				Map<String,List<IMessageListener>> listeners = RapServiceImp.this.listeners;
				String an = accountName;
				IMessage message = msg;
				List<IMessageListener> ls = listeners.get(an);
				if(ls == null) {
					return;
				}
				for(IMessageListener l:ls) {
					l.onMessage(message);
				}
			}
		});
	}

	
	@Override
	public void sendMsg(final String accountName,final IMessage msg) {
		worker.execute(new Runnable(){
			public void run() {
				RapSession session = (RapSession)manager.getSessionByUserId(accountName);
				session.onTextMessage(msg);
			}
		});	
	}

	@Override
	public void getFriendList(String accountName) {
		messageManager.sendFriendList(MessageType.RESP_GET_FRIEND_LIST, accountName, null, null);
	}		
}
