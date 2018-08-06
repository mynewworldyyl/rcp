package net.techgy;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class BaseJpalDao<T,ID extends Serializable> extends AbstractBaseDaoImpl<T,ID>{

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		this.getEntityManager().persist(entity);
	}

	@Override
	public void remove(Class<T> cls,ID id) {
		// TODO Auto-generated method stub
		T entity = this.find(cls, id);
		if(null == entity) {
			return;
		}
		this.getEntityManager().remove(entity);
	}

	@Override
	public T update(T entity) {
		// TODO Auto-generated method stub
		return this.getEntityManager().merge(entity);
	}

	@Override
	public T find(Class<T> cls, ID id) {
		return this.getEntityManager().find(cls, id);
	}
	
	protected String constructWhereConditions(String qStr,String asName) {
		
		if(null == qStr || "".equals(qStr.trim())) {
			return null;
		}
		String[] nameValues = qStr.split(",");
		if(null == nameValues || nameValues.length <= 0 ) {
			return null;
		}
		StringBuffer sb = new StringBuffer(" ");
		sb.append(asName).append(".").append(nameValues[0]);
		if(nameValues.length == 1) {
			return sb.toString();
		}
		//"name like '%name%'"
		for(int index = 1; index < nameValues.length; index++) {
			sb.append(" OR ").append(asName).append(".").append(nameValues[index]);
		}
		
		return sb.toString();
	}

}
