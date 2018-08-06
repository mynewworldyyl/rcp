package net.techgy.im.msghandler;

import net.techgy.common.models.IMessage;
import net.techgy.common.models.IMessageHandler;
import net.techgy.common.models.MessageImpl;
import net.techgy.common.models.MessageState;
import net.techgy.common.models.MessageType;
import net.techgy.common.models.MsgHeader;
import net.techgy.im.manager.IUserManager;
import net.techgy.im.manager.MessageManager;

import org.springframework.beans.factory.annotation.Autowired;

public class VAHandler implements IMessageHandler {
	@Override
	public MessageType getMessageCode() {
		return MessageType.REQ_VA_MESSAGE;
	}

	@Autowired
	private MessageManager messageManager;
	
	@Override
	public IMessage handlerMessage(IMessage message) {
		messageManager.sendVAMessage(message);
		return null;
	}
}
