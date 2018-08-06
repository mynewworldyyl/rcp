package net.techgy.im.handler;

import net.techgy.common.models.MessageType;
import net.techgy.im.manager.MessageManager;
import net.techgy.im.net.ISession;
import net.techgy.im.net.ISessionListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IMSessionListener implements ISessionListener {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private MessageManager messageManager;
	
	@Override
	public void sessoionOpened(ISession session) {
		this.messageManager.sendFriendList(MessageType.RESP_GET_FRIEND_LIST, session.getUserId(), null, null);
		this.messageManager.sendOfflineMessage(session.getUserId());
	}

	@Override
	public void sessoionClose(ISession session) {
		// TODO Auto-generated method stub

	}

}
