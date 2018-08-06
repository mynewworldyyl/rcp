package net.techgy.community;

import java.util.List;

import javax.ws.rs.QueryParam;

import net.techgy.community.models.Topic;

public interface ITopicManager {
	
	int TOPIC_PAGE_SIZE = 3;
	
	String Q_TYPE_TITLE = "title";
	
	String Q_TYPE_DESC = "desc";
	
	String Q_TYPE_CREATOR = "creator";
	
	String Q_TYPE_ALL = "all";
	
	void saveTopic(Topic topic);
	
	void removeTopic(long topicId);
	
	Topic queryTopicById(long tid);
	
	List<Topic>  queryTopic(String qType, String qStr, int pageIndex);

	List<Topic> queryTopicByTitle(String title,int pageIndex);
	
	List<Topic> queryTopicByDesc(String desc,int pageIndex);
	
	List<Topic> queryTopicByCreator(String creatorName,int pageIndex);
	
	List<Topic> queryTopicByAll(String qStr,int pageIndex);
	
    int totalPagesByTitle(String title);
	
    int totalPagesByDesc(String desc);
	
    int totalPagesByCreator(String creatorName);
	
    int totalPagesByAll(String qStr);
	
	TopicInfo topicInfo(String qStr,String qType);
}
