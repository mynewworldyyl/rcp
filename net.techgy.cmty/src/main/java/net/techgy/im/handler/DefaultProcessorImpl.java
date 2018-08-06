package net.techgy.im.handler;

import net.techgy.common.models.IMessage;
import net.techgy.common.models.MessageType;
import net.techgy.common.models.IMessage.MT;
import net.techgy.im.manager.MessageManager;
import net.techgy.im.net.ISession;
import net.techgy.im.net.ISessionManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class DefaultProcessorImpl extends AbstractProcessor {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ISessionManager sessionManager;
	
	@Autowired
	private MessageManager messageManager;
	
	public DefaultProcessorImpl(){
	}

	@Override
	void send(IMessage msg) {
		ISession session = this.sessionManager.getSessionByUserId(msg.getUserId());
		if(session == null) {
			MT mt = msg.getMt();
			if(mt.getResendCount() > 5) {
				logger.warn("Fail to send: "+ msg);
				if(msg.getType().getType() == MessageType.RESP_CHAT_MESSAGE.getType()) {
					this.messageManager.saveMessage(msg);
				}
				return;
			}
			msg.getMt().setResendCount(msg.getMt().getResendCount()+1);
			this.pushMessage(msg);
			return;
		}
		session.writeMessage(msg);
	}
}
