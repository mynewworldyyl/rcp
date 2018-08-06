package net.techgy.community;

import java.util.List;

import javax.persistence.Query;

import net.techgy.BaseJpalDao;
import net.techgy.community.models.Note;

import org.springframework.stereotype.Repository;

@Repository
public class NoteDao extends BaseJpalDao<Note, Long> implements INoteDao {

	@Override
	public List<Note> queryByTopicId(long topicId) {
		// TODO Auto-generated method stub
		return this.queryByTopicId(topicId, 0, Integer.MAX_VALUE);
	}

	@Override
	public List<Note> queryByTopicId(long topicId, int firstIndex, int maxResult) {
		// TODO Auto-generated method stub
		String jplStr = "SELECT n FROM Note n WHERE n.topic.id = :topicId ORDER BY n.createDate";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("topicId", topicId);
		query.setFirstResult(firstIndex);
		query.setMaxResults(maxResult);
		return query.getResultList();
	}

	
	@Override
	public int totalNumByTopicId(long topicId) {
		// TODO Auto-generated method stub
		String jplStr = "SELECT COUNT(n) FROM Note n WHERE n.topic.id = :topicId";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("topicId", topicId);
		return ((Long)query.getSingleResult()).intValue();
	}

	@Override
	public void saveNode(Note note) {
		// TODO Auto-generated method stub
		this.save(note);
	}

	@Override
	public void removeNodeById(long id) {
		// TODO Auto-generated method stub
		this.remove(Note.class, id);
	}
	
}
