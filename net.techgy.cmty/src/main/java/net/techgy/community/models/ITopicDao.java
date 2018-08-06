package net.techgy.community.models;

import java.util.List;

import net.techgy.IBaseDao;

public interface ITopicDao extends  IBaseDao<Topic,Long>{

	List<Topic> queryByCreator(String creator);
	
	List<Topic> queryByCreator(String creator,int firstIndex,int max);
	
	List<Topic> queryByTitle(String title,int firstIndex,int max);
	
	List<Topic> queryByTitle(String title);
	
	List<Topic> queryByDesc(String desc,int firstIndex,int max);
	
	List<Topic> queryByDesc(String desc);
	
    List<Topic> queryByAll(String qStr,int firstIndex,int max);
    
    void deleteById(long id);
    
	
    int totalCountByTitle(String title);
	
    int totalCountByDesc(String desc);
	
    int totalCountByCreator(String creatorName);
	
    int totalCountByAll(String qStr);
	
}
