package net.techgy.im.msghandler;

import net.techgy.common.models.IMessage;
import net.techgy.common.models.IMessageHandler;
import net.techgy.common.models.MessageType;
import net.techgy.im.manager.MessageManager;

import org.springframework.beans.factory.annotation.Autowired;

public class ChattingMessageHandler implements IMessageHandler {

	@Override
	public MessageType getMessageCode() {
		return MessageType.REQ_CHAT_MESSAGE;
	}

	@Autowired
	private MessageManager messageManager;
	
	@Override
	public IMessage handlerMessage(IMessage message) {
		messageManager.sendChatMessage(message);
		return null;
	}

}
