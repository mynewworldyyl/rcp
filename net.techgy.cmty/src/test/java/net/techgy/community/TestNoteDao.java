package net.techgy.community;

import java.sql.Date;
import java.util.List;

import net.techgy.community.models.INoteDao;
import net.techgy.community.models.ITopicDao;
import net.techgy.community.models.Note;
import net.techgy.community.models.Topic;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/app-*.xml" })
public class TestNoteDao {
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ITopicDao topicDao;
	
	@Autowired
	private INoteDao nodeDao;

	private Topic getTopic() {
		List<Topic> lt = topicDao.queryByCreator("test1");
		if(lt.size() > 0) {
			return lt.get(0);
		}
		 Topic t1 = new Topic();
	     t1.setCreator("test1");
	      t1.setDesc("welcome new comer");
	      t1.setTitle("test1 opintion");
	      topicDao.save(t1);
	      return t1;
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testSave01() {
      Topic t = getTopic();
      Note n1 = new Note();
      n1.setCreator("test1");
      n1.setText("very good topic");
      n1.setTopic(t);
      n1.setCreateDate(new Date(System.currentTimeMillis()));
      this.nodeDao.saveNode(n1);
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testQuery01() {
     Topic t = this.getTopic();
     
     List<Note> nl = this.nodeDao.queryByTopicId(t.getId());
     logger.debug(nl);
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testRemove01() {
     Topic t = this.getTopic();
     List<Note> nl = this.nodeDao.queryByTopicId(t.getId());
     this.nodeDao.removeNodeById(nl.get(0).getId());
	}
	
	
	
	
}
