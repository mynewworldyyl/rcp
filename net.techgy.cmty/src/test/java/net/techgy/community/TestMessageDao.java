package net.techgy.community;

import java.util.List;

import net.techgy.BaseNoSqlDao;
import net.techgy.common.models.MessageImpl;
import net.techgy.common.models.MessageState;
import net.techgy.common.models.MessageType;
import net.techgy.common.models.MsgBody;
import net.techgy.common.models.MsgHeader;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/app-*.xml" })
public class TestMessageDao {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private BaseNoSqlDao messageDao;

	@Test
	public void testEnv() {
		logger.debug("Hello mongodb dao");
	}
	
	@Test
	public void testSave01() {
        MessageImpl m = new MessageImpl();
        m.setType(MessageType.REQ_ADD_FRIEND);
        
        m.setClientMessage(true);
        m.setStatus(MessageState.OK);
        
        m.addHeader("youname", "headervalue");
        
        m.addBody(22, "Hell world");       
        
        this.messageDao.getTemplate().save(m);
	}
	
	@Test
	public void testQuery01() {
		 /*mongoOperations.remove(new Query(new Criteria("age").is(age)),  
	                Person.class); */
	        
		 Query query = new Query(new Criteria("type").ne(MessageType.REQ_ADD_FRIEND));
		 List<MessageImpl> msgs = this.messageDao.getTemplate().find(query, MessageImpl.class);
		 logger.debug(msgs);
		 Assert.notNull(msgs);
	}
	
	@Test
	public void testQueryMap02() {    
		 Query query = new Query(new Criteria("headers.youname").exists(true));
		 
		 List<MessageImpl> msgs = this.messageDao.getTemplate().find(query, MessageImpl.class);
		 logger.debug(msgs);

		 Assert.isTrue(msgs.size()>0);
		 
		/* BasicDBObject query = new BasicDBObject(); 
		 
		 BasicDBObject value = new BasicDBObject(); 
		 value.put("$exists",true); 
		 query.put("mapfiled.2", value); 
		 DBCursor cur = col.find(query);*/
		 
		 
	}
	
	@Test
	public void testQueryArray1() {    
		 Query query = new Query(new Criteria("bodies.type").is(22));
		 
		 List<MessageImpl> msgs = this.messageDao.getTemplate().find(query, MessageImpl.class);
		 for(MessageImpl m : msgs){
			 logger.debug(m.getBody(22).getContent());
		 }
		 Assert.isTrue(msgs.size()>0);
		 		 
	}
}
