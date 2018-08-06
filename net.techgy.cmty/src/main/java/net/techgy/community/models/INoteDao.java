package net.techgy.community.models;

import java.util.List;

import net.techgy.IBaseDao;


public interface INoteDao extends  IBaseDao<Note,Long>{

	List<Note> queryByTopicId(long topicId);
	
	List<Note> queryByTopicId(long topicId,int firstIndex, int maxResult);
	
	int totalNumByTopicId(long topicId);
	
	void saveNode(Note note);
	
	void removeNodeById(long id);
	
}
