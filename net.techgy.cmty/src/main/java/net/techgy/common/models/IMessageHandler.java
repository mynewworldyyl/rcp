package net.techgy.common.models;


public interface IMessageHandler {

     MessageType getMessageCode();
     
     IMessage handlerMessage(IMessage message);
     
	
}
