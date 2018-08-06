package net.techgy.im.msghandler;

import net.techgy.common.models.IMessage;
import net.techgy.common.models.IMessageHandler;
import net.techgy.common.models.MessageImpl;
import net.techgy.common.models.MessageState;
import net.techgy.common.models.MessageType;
import net.techgy.im.manager.MessageManager;
import net.techgy.usercenter.AccountService;

import org.springframework.beans.factory.annotation.Autowired;


public class LogoutHandler implements IMessageHandler {
	
	@Autowired
	private AccountService accountService;
	
	private MessageType type  = MessageType.REQ_LOGOUT;
	
	@Autowired
	private MessageManager messageManager;
	
	@Override
	public MessageType getMessageCode() {
		return this.type;
	}

	@Override
	public IMessage handlerMessage(IMessage message) {
		// TODO Auto-generated method stub
		MessageImpl resultMessage = this.messageManager.createRespMessage(MessageType.RESP_LOGOUT,message);
		String r = this.accountService.logout(message.getUserId());
		if(AccountService.SUCCESS.equals(r)) {
			resultMessage.setStatus(MessageState.OK);
		}else {
			resultMessage.setStatus(MessageState.FAIL);
		}		
		return resultMessage;
	}

}
