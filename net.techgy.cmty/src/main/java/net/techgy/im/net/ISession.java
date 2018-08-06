package net.techgy.im.net;

import net.techgy.common.models.IMessage;

public interface ISession {
	
	long getSessionId();
	
	String getUserId();
	
	boolean writeMessage(IMessage msg);
	
}
