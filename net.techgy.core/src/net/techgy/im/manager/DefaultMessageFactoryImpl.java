package net.techgy.im.manager;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.techgy.common.models.IMessage;
import net.techgy.common.models.MessageImpl;
import net.techgy.common.models.MessageType;
import net.techgy.common.models.MsgBody;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultMessageFactoryImpl implements IMessageFactory {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private Set<MessageType> types = new HashSet<MessageType>();
	
	@Autowired
	private MessageManager messageManager;
	
	public DefaultMessageFactoryImpl(){
		types.add(MessageType.RESP_ADD_FRIEND);
		types.add(MessageType.RESP_CHAT_MESSAGE);
		types.add(MessageType.RESP_GET_FRIEND_LIST);
		types.add(MessageType.RESP_LOGIN);
		types.add(MessageType.RESP_LOGOUT);
		types.add(MessageType.RESP_SEARCH_USER);
	}
	
	@Override
	public boolean forType(MessageType type) {
		return types.contains(type);
	}

	@Override
	public Set<MessageType> getTypes() {
		// TODO Auto-generated method stub
		return this.getTypes();
	}

	@Override
	public IMessage createMessate(MessageType type,String accountName,Map<String,String> headers,IMessage orignal) {
		MessageImpl message = this.messageManager.createRespMessage(type, accountName, headers);
		MsgBody body = getBody(type,accountName);
		return message;
	}

	private MsgBody getBody(MessageType type, String accountName) {
		MsgBody body = null;
		int t = type.getType();
		if(MessageType.RESP_ADD_FRIEND.getType() == t) {
			
		}else if(MessageType.RESP_CHAT_MESSAGE.getType() == t){
			
		}else if(MessageType.RESP_GET_FRIEND_LIST.getType() == t){
			
		}else if(MessageType.RESP_LOGIN.getType() == t){
			
		}else if(MessageType.RESP_LOGOUT.getType() == t){
			
		}else if(MessageType.RESP_SEARCH_USER.getType() == t){
			
		}
		return body;
	}
}
