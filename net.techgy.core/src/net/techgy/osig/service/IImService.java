package net.techgy.osig.service;

import net.techgy.common.models.IMessage;

public interface IImService {

	void addImListener(String accountName,IMessageListener listener);
	
	void removeImListener(String accountName);
	
	void sendMsg(String accountName ,IMessage msg);
	
	void getFriendList(String accountName);
	
	//void createImChannel(String accountName);
	
	///void sendMessge(IMessage message);
}
