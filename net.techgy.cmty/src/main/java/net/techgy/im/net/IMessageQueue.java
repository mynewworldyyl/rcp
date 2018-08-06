package net.techgy.im.net;

import net.techgy.common.models.IMessage;


public interface IMessageQueue {

	void push(IMessage msg);
	
	IMessage pop();
	
	int size();
	
	boolean isEmpty();
	
	boolean isNonEmpty();
}
