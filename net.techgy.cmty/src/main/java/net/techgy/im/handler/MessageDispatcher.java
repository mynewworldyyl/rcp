package net.techgy.im.handler;

import java.util.HashMap;
import java.util.Map;

import net.techgy.common.models.IMessage;
import net.techgy.common.models.IMessageHandler;
import net.techgy.common.models.MessageType;


public class MessageDispatcher  {


	private Map<Integer,IMessageHandler> handlers = new HashMap<Integer,IMessageHandler>();
	
	private MessageType code = MessageType.DISPATCH_HANDLER;

	public MessageType getMessageCode() {
		// TODO Auto-generated method stub
		return this.code;
	}
    /**
     * return true to stop the message,
     * return false to continue;
     * @param message
     * @return
     */
	public IMessage handlerMessage(IMessage message) {
		// TODO Auto-generated method stub
		IMessageHandler h = this.handlers.get(message.getType().getType());
		if(h != null){
			return h.handlerMessage(message);
		}else {
			return message;
		}
	}
	public Map<Integer, IMessageHandler> getHandlers() {
		return handlers;
	}
	public void setHandlers(Map<Integer, IMessageHandler> handlers) {
		this.handlers = handlers;
	}
	
	
}
