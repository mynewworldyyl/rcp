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

public class AddFriendHandler implements IMessageHandler {

	@Autowired
	private IUserManager userManager;
	
	private MessageType type  = MessageType.REQ_ADD_FRIEND;
	
	@Autowired
	private MessageManager messageManager;
	
	@Override
	public MessageType getMessageCode() {
		return this.type;
	}

	@Override
	public IMessage handlerMessage(IMessage message) {
		MessageImpl resultMessage = this.messageManager.createRespMessage(MessageType.RESP_ADD_FRIEND,message);			
		String addedAccount = message.getHeaderByName(MsgHeader.ADD_FRIEND_ACCOUNT);		
		if(addedAccount == null) {
			resultMessage.setStatus(MessageState.FAIL);
		}else if(userManager.addFriend(message.getUserId(), addedAccount)){
			resultMessage.setStatus(MessageState.OK);
			messageManager.sendFriendList(MessageType.RESP_GET_FRIEND_LIST, message.getUserId(), null, null);
		}		
		return resultMessage;
	}

}
