package net.techgy.community;

import java.util.ArrayList;
import java.util.List;

import net.techgy.community.models.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TopicManagerImpl implements ITopicManager{
	
	@Autowired
	private ITopicDao topicDao;
	
	@Transactional
	public void saveTopic(Topic topic) {
           this.topicDao.save(topic);		
	}

	@Transactional
	public void removeTopic(long topicId) {
		this.topicDao.deleteById(topicId);
	}

	@Transactional
	public List<Topic> queryTopicByTitle(String title,int pageIndex) {
        int firstIndex = TOPIC_PAGE_SIZE * pageIndex;
		List<Topic> l = this.topicDao.queryByTitle(title, firstIndex, TOPIC_PAGE_SIZE);
		return l;
	}

	@Transactional
	public List<Topic> queryTopic(String qType, String qStr, int pageIndex) {
		if(qType == null || qType.trim().equals("")) {
			return null;
		}
		qStr = qStr == null ? "": qStr.trim();
		qType = qType.trim();
		List<Topic> lt = null;
		if(Q_TYPE_TITLE.equals(qType)) {
			lt = this.queryTopicByTitle(qStr, pageIndex);
		}else if(Q_TYPE_DESC.equals(qType)) {
			lt =  this.queryTopicByDesc(qStr, pageIndex);
		}else if(Q_TYPE_CREATOR.equals(qType)) {
			lt =  this.queryTopicByCreator(qStr, pageIndex);
		}else if(Q_TYPE_ALL.equals(qType)) {
			lt =  this.queryTopicByAll(qStr, pageIndex);
		}
		List<Topic> result = new ArrayList<Topic>();
		for(Topic t : lt) {
			result.add(t);
		}
		return result;
	}
	
	@Transactional
	public List<Topic> queryTopicByDesc(String desc, int pageIndex) {
		int firstIndex = TOPIC_PAGE_SIZE * pageIndex;
		List<Topic> l = this.topicDao.queryByDesc(desc, firstIndex, TOPIC_PAGE_SIZE);
		return l;
	}

	@Transactional
	public List<Topic> queryTopicByCreator(String creatorName, int pageIndex) {
		int firstIndex = TOPIC_PAGE_SIZE * pageIndex;
		List<Topic> l = this.topicDao.queryByCreator(creatorName, firstIndex, TOPIC_PAGE_SIZE);
		return l;
	}

	@Transactional
	public List<Topic> queryTopicByAll(String qStr, int pageIndex) {
		int firstIndex = TOPIC_PAGE_SIZE * pageIndex;
		List<Topic> l = this.topicDao.queryByAll(qStr, firstIndex, TOPIC_PAGE_SIZE);
		return l;
	}

	private int getPage(int count) {
		int c = count/TOPIC_PAGE_SIZE;
		if(count % TOPIC_PAGE_SIZE != 0) {
			c+=1;
		}
		return c;
	}
	@Transactional
	public int totalPagesByTitle(String title) {
		// TODO Auto-generated method stub
		return this.getPage(this.topicDao.totalCountByTitle(title)) ;
	}

	@Transactional
	public int totalPagesByDesc(String desc) {
		// TODO Auto-generated method stub
		return this.getPage(this.topicDao.totalCountByDesc(desc));
	}

	@Transactional
	public int totalPagesByCreator(String creatorName) {
		// TODO Auto-generated method stub
		return this.getPage(this.topicDao.totalCountByCreator(creatorName));
	}

	@Transactional
	public int totalPagesByAll(String qStr) {
		// TODO Auto-generated method stub
		return this.getPage(this.topicDao.totalCountByAll(qStr));
	}

	@Transactional
	public TopicInfo topicInfo(String qStr,String qType) {
		if(qType == null || qType.trim().equals("")) {
			return null;
		}
		qStr = qStr == null ? "": qStr.trim();
		qType = qType.trim();
		int size = -1;
		if(Q_TYPE_TITLE.equals(qType)) {
			size = this.totalPagesByTitle(qStr);
		}else if(Q_TYPE_DESC.equals(qType)) {
			size =  this.totalPagesByDesc(qStr);
		}else if(Q_TYPE_CREATOR.equals(qType)) {
			size =  this.totalPagesByCreator(qStr);
		}else if(Q_TYPE_ALL.equals(qType)) {
			size =  this.totalPagesByAll(qStr);
		}
		TopicInfo ti = new TopicInfo();
		ti.setTopicPageNum(size);
		ti.setPageSize(TOPIC_PAGE_SIZE);
		return ti;
	}

	@Override
	public Topic queryTopicById(long tid) {
		// TODO Auto-generated method stub
		return this.topicDao.find(Topic.class, tid);
	}
	
	
    
}
