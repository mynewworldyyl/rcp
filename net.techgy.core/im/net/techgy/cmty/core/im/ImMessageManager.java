package net.techgy.cmty.core.im;

import java.util.ArrayList;
import java.util.List;

import net.techgy.cmty.core.im.vo.MessageVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digitnexus.core.UserContext;
import com.digitnexus.core.idgenerator.CacheBaseIDManager;
import com.digitnexus.core.osgiservice.impl.SpringContext;

@Component
public class ImMessageManager {

	@Autowired
	private ImAccountManager am;
	
	@Autowired
	private ImMessageDaoImpl immDao;
	
	@Autowired
	private CacheBaseIDManager generator;

	public void saveMessage(MessageVo msg) {
		if(msg.getId() != null) {
			return;
		}
		ImMessage m = new ImMessage();
		m.setAccount(am.getImAccount(UserContext.getAccount().getAccountName()));
		m.setClient(UserContext.getCurrentUser().getLoginClient());
		m.setContent(msg.getContent());
		m.setData(msg.getData());
		m.setFrom(msg.getFrom());
		m.setHandled(msg.isHandled());
		m.setId(this.generator.getStringId(ImMessage.class));
		m.setModelId(msg.getModelId());
		m.setMsgType(msg.getMsgType());
		m.setSeqId(msg.getSeqId());
		m.setTo(msg.getTo());
		m.setTopic(msg.getTopic());
		immDao.save(m);
	}

	@SuppressWarnings({"unchecked" })
	@Transactional
	public List<MessageVo> getNewImMessages() {
		List<ImMessage> l = this.immDao.getEntityManager().createNamedQuery("getNewImMessages")
				.setParameter("an", UserContext.getAccount().getAccountName())
	            .getResultList();
		if(l == null || l.isEmpty()) {
			return null;
		}
		List<String> ids=new ArrayList<String>();
		
		List<MessageVo> lvo = new ArrayList<MessageVo>();
		
		for(ImMessage msg : l) {
		    MessageVo m=new MessageVo();
		    //m.setAccount(msg.getAccount().getAccount().getAccountName());
			//m.setClient(UserContext.getCurrentUser().getLoginClient());
			m.setContent(msg.getContent());
			m.setData(msg.getData());
			m.setFrom(msg.getFrom());
			m.setHandled(msg.isHandled());
			m.setId(msg.getId());
			m.setModelId(msg.getModelId());
			m.setMsgType(msg.getMsgType());
			m.setSeqId(msg.getSeqId());
			m.setTo(msg.getTo());
			m.setTopic(msg.getTopic());
			lvo.add(m);
			ids.add(msg.getId());
		}
		
		this.immDao.getEntityManager().createNamedQuery("deleteNewImMessage")
		.setParameter("ids",ids)
		.executeUpdate();
		
		return lvo;
	}

	
	
}
