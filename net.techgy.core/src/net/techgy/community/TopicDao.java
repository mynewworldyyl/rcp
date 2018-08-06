package net.techgy.community;

import java.util.List;

import javax.persistence.Query;

import net.techgy.BaseJpalDao;
import net.techgy.community.models.Topic;

import org.springframework.stereotype.Repository;

@Repository
public class TopicDao extends BaseJpalDao<Topic, Long> implements ITopicDao {

	@Override
	public List<Topic> queryByCreator(String creator) {
		return this.queryByCreator(creator, 0, Integer.MAX_VALUE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Topic> queryByCreator(String creator,int firstIndex,int max) {
		String jplStr = "SELECT t FROM Topic t WHERE t.creator = :creator ORDER BY t.createDate";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("creator", creator);
		query.setFirstResult(firstIndex);
		query.setMaxResults(max);
		return query.getResultList();
	}
	
	public List<Topic> queryByTitle(String title) {
		return this.queryByTitle(title,0,Integer.MAX_VALUE);
	}
	
	@Override
	public List<Topic> queryByTitle(String title,int firstIndex,int max) {
		String jplStr = "SELECT t FROM Topic t WHERE UPPER(t.title) LIKE :title ORDER BY t.createDate";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("title", "%"+title.toUpperCase()+"%");
		query.setFirstResult(firstIndex);
		query.setMaxResults(max);
		return query.getResultList();
	}
	
	public List<Topic> queryByDesc(String desc) {
		return this.queryByDesc(desc,0,Integer.MAX_VALUE);
	}
	
	@Override
	public List<Topic> queryByDesc(String desc,int firstIndex,int max) {
		String jplStr = "SELECT t FROM Topic t WHERE UPPER(t.desc) LIKE :desc ORDER BY t.createDate";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("desc", "%"+desc.toUpperCase()+"%");
		query.setFirstResult(firstIndex);
		query.setMaxResults(max);
		return query.getResultList();
	}

	@Override
	public List<Topic> queryByAll(String qStr,int firstIndex,int max) {
		Query query =  null;
		
		if(qStr != null && !"".equals(qStr.trim())){
			String jplStr = "SELECT t FROM Topic t WHERE UPPER(t.desc)" +
					" LIKE :desc OR UPPER(t.title) LIKE :title OR UPPER(t.creator) LIKE :creator ORDER BY t.createDate";			
			query = this.getEntityManager().createQuery(jplStr);	
			qStr = "%"+qStr.toUpperCase()+"%";
			query.setParameter("desc",qStr );
			query.setParameter("creator",qStr );
			query.setParameter("title",qStr );
		}else {
			query = this.getEntityManager().createQuery("SELECT t FROM Topic t ORDER BY t.createDate");
		}		
		query.setFirstResult(firstIndex);
		query.setMaxResults(max);
		return query.getResultList();
	}

	@Override
	public void deleteById(long id) {
		this.remove(Topic.class, id);
	}

	@Override
	public int totalCountByTitle(String title) {
		// TODO Auto-generated method stub
		String jplStr = "SELECT COUNT(t) FROM Topic t WHERE UPPER(t.title) LIKE :title";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("title", "%"+title.toUpperCase()+"%");
		Integer count = ((Long)query.getSingleResult()).intValue();
		return count;
	}

	@Override
	public int totalCountByDesc(String desc) {
		String jplStr = "SELECT COUNT(t) FROM Topic t WHERE UPPER(t.desc) LIKE :desc";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("desc", "%"+desc.toUpperCase()+"%");
		Integer count = ((Long)query.getSingleResult()).intValue();
		return count;		
	}

	@Override
	public int totalCountByCreator(String creatorName) {
		String jplStr = "SELECT COUNT(t) FROM Topic t WHERE t.creator = :creator";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("creator", creatorName);
		Integer count = ((Long)query.getSingleResult()).intValue();
		return count;		
	}

	@Override
	public int totalCountByAll(String qStr) {
		String jplStr = "SELECT COUNT(t) FROM Topic t WHERE UPPER(t.desc)" +
				" LIKE :desc OR UPPER(t.title) LIKE :title OR UPPER(t.creator) LIKE :creator";
		Query query = this.getEntityManager().createQuery(jplStr);
		qStr = "%"+qStr.toUpperCase()+"%";
		query.setParameter("desc",qStr );
		query.setParameter("creator",qStr );
		query.setParameter("title",qStr );
		Integer count = ((Long)query.getSingleResult()).intValue();
		return count;	
	}	
}
