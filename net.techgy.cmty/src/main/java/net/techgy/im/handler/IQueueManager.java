package net.techgy.im.handler;

import java.util.List;

import net.techgy.common.models.IMessage;
import net.techgy.common.models.MessageImpl;

public interface IQueueManager {

	void pushMessage(IMessage msg);
	void pushMessage(List<MessageImpl> msgs);
	
	void init();
	
}
