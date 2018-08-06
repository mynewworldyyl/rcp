package net.techgy.im.manager;

import java.util.Map;
import java.util.Set;

import net.techgy.common.models.IMessage;
import net.techgy.common.models.MessageType;

import org.springframework.stereotype.Component;

@Component
public interface IMessageFactory {

	Set<MessageType> getTypes();
	
	boolean forType(MessageType type);
	
	IMessage createMessate(MessageType type,String accountName,Map<String,String> headers,IMessage orignal);
}
