package net.techgy.im.handler;

import net.techgy.common.models.IMessage;
import net.techgy.common.models.IMessageHandler;
import net.techgy.common.models.MessageType;


public abstract class AbstractMessageHandler implements IMessageHandler {

	private MessageType code;
	public AbstractMessageHandler(MessageType code) {
		this.code = code;
	}
	@Override
	public MessageType getMessageCode() {
		// TODO Auto-generated method stub
		return this.code;
	}

	@Override
	public IMessage handlerMessage(IMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

}
