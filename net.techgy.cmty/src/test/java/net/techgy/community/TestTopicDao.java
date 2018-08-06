package net.techgy.community;


import java.sql.Date;
import java.util.List;

import net.techgy.community.models.ITopicDao;
import net.techgy.community.models.Topic;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/app-*.xml" })
public class TestTopicDao {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ITopicDao topicDao;	

	
	@Test
	@Transactional
	@Rollback(false)
	public void testSave01() {
      Topic t1 = new Topic();
      t1.setCreator("test1");
      t1.setDesc("wefdsest5 opintion");
      t1.setCreateDate(new Date(System.currentTimeMillis()));
      t1.setTitle("test titledfa");
      topicDao.save(t1);
      
      Topic t2 = new Topic();
      t2.setCreateDate(new Date(System.currentTimeMillis()));
      t2.setCreator("test222");
      t2.setDesc("testsadfsd2's topic");
      t2.setTitle("aboudsadsat technology");
      topicDao.save(t2);
      
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testQueryByTitle1() {
      List<Topic> tl = this.topicDao.queryByTitle("test");
      logger.debug(tl);
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testQueryByCreator() {
      List<Topic> tl = this.topicDao.queryByCreator("test1");
      logger.debug(tl);
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testQueryByDesc() {
      List<Topic> tl = this.topicDao.queryByDesc("new");
      logger.debug(tl);
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testQueryByAll() {
      List<Topic> tl = this.topicDao.queryByTitle("test");
      logger.debug(tl);
	}
	
	
	@Test
	@Transactional
	@Rollback(false)
	public void testQuery() {
      Topic t1 = this.topicDao.find(Topic.class, 1251l);
      Assert.notNull(t1);
      Assert.notNull(t1.getCreator());
      Assert.notNull(t1.getDesc());  
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testRemove() {
      this.topicDao.deleteById(1401);
	}
	
}
