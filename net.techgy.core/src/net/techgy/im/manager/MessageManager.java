package net.techgy.im.manager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.techgy.common.models.IMessage;
import net.techgy.common.models.MessageImpl;
import net.techgy.common.models.MessageState;
import net.techgy.common.models.MessageType;
import net.techgy.common.models.MsgBody;
import net.techgy.common.models.MsgHeader;
import net.techgy.im.handler.IQueueManager;
import net.techgy.im.models.GroupImpl;
import net.techgy.im.models.IAccount;
import net.techgy.im.net.dao.IMessageDao;
import net.techgy.utils.AOPUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class MessageManager {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IQueueManager queueManager;
	
	@Autowired
	private IUserManager userManger;
	
	@Autowired
	private IMessageDao messageDao;
	
	@Autowired
	private Set<IMessageFactory> messageFactory = new HashSet<IMessageFactory>();
	
	public void saveMessage(IMessage orignalMessage){
		Object o = AOPUtil.getTarget(orignalMessage);
		this.messageDao.save((MessageImpl)o);		
	}
	
	public void sendOfflineMessage(String toAccount){
		//Query query = new Query(new Criteria("headers.youname").exists(true));
        Query query = new Query(new Criteria("headers."+ MsgHeader.USERNAME+".value").is(toAccount));
        List<MessageImpl> msgs = this.messageDao.getTemplate().find(query, MessageImpl.class);
        if(msgs != null && !msgs.isEmpty()) {
        	this.messageDao.getTemplate().remove(query, MessageImpl.class);
        	this.queueManager.pushMessage(msgs);
        }
	}
	
	
	
	public boolean sendVAMessage(IMessage orignalMessage){
		String from = orignalMessage.getUserId();		
		MsgHeader msgToHeader = orignalMessage.getHeader(MsgHeader.CHAT_MESSAGE_TO);
		MsgBody msgBody = orignalMessage.getSingleBody();
	
		IMessage message = this.createRespMessage(
				MessageType.RESP_VA_MESSAGE, msgToHeader.getValue());
		
		message.addHeader(MsgHeader.CHAT_MESSAGE_FROM, from);
		
		message.addBody(MessageType.RESP_VA_MESSAGE.getType(), msgBody.getContent());
		
		this.queueManager.pushMessage(message);
		return true;
	}
	
	public boolean sendChatMessage(IMessage orignalMessage){
		String from = orignalMessage.getUserId();		
		MsgHeader msgToHeader = orignalMessage.getHeader(MsgHeader.CHAT_MESSAGE_TO);
		MsgBody msgBody = orignalMessage.getSingleBody();
	
		IMessage message = this.createRespMessage(
				MessageType.RESP_CHAT_MESSAGE, msgToHeader.getValue());
		
		message.addHeader(MsgHeader.CHAT_MESSAGE_FROM, from);
		
		message.addBody(MessageType.RESP_CHAT_MESSAGE.getType(), msgBody.getContent());
		
		this.queueManager.pushMessage(message);
		return true;
	}
	
	
	public boolean sendFriendList(MessageType type,String accountName,Map<String,String> args,IMessage origal){
		IMessage message = this.createRespMessage(MessageType.RESP_GET_FRIEND_LIST, accountName);		
		List<GroupImpl> gl = this.userManger.getFriends(accountName);		
		MsgBody mb = new MsgBody();		
		mb.setContent(gl);
		message.addBody(MessageType.RESP_GET_FRIEND_LIST.getType(), mb);	
		this.queueManager.pushMessage(message);
		return true;
	}
	
	
	public boolean createAndSendMessage(MessageType type,String userName,Map<String,String> args,IMessage origal){
		IMessage message = createMessage( type, userName,args,origal);
		if(message == null){
			return false;
		}
		this.queueManager.pushMessage(message);
		return true;
		
	}
	
	public IMessage createMessage(MessageType type,String userName,Map<String,String> args,IMessage origal){
		IMessageFactory f = this.getFactory(type);
		if(null == f) {
			return null;
		}
		try {
			return f.createMessate(type, userName, args,origal);
		} catch (Exception e) {
			this.logger.error("fail to create Message type:" + type +",for user:"+userName,e);
			return null;
		}
	}
	
	public boolean sendMessage(IMessage message){
		this.queueManager.pushMessage(message);
		return true;
	}
	
	
	private IMessageFactory getFactory(MessageType type) {
		for(IMessageFactory f : this.messageFactory) {
			if(f.forType(type)) {
				return f;
			}
		}
		return null;
	}
	
	/**
	 * create message to response to orignal message.
	 * not use to forward message;
	 * @param orignal
	 * @return
	 */
	public MessageImpl createRespMessage(MessageType type,IMessage orignal) {
		String currentUser = orignal.getHeaderByName(MsgHeader.USERNAME);	
		MessageImpl resultMessage = this.createRespMessage(type,currentUser);
		return resultMessage;
	}
	public MessageImpl createRespMessage(MessageType type,IAccount account) {
		MessageImpl resultMessage = this.createRespMessage(type,account.getAccountName());
		return resultMessage;
	}
	
	public MessageImpl createRespMessage(MessageType type,String accountName) {
		MessageImpl resultMessage = new MessageImpl();
		resultMessage.setType(type);
		resultMessage.setClientMessage(true);
		resultMessage.setStatus(MessageState.OK);
		resultMessage.addHeader(MsgHeader.USERNAME, accountName);
		return resultMessage;
	}
	
	
	public MessageImpl createRespMessage(MessageType type,String accountName,Map<String,String> args) {
		MessageImpl resultMessage = this.createRespMessage(type, accountName);
		if(args == null){
			return resultMessage;
		}
		Iterator<String> ite = args.keySet().iterator();
		while(ite.hasNext()) {
			String key = ite.next();
			resultMessage.addHeader(key, args.get(key));
		}
		return resultMessage;
	}
}
