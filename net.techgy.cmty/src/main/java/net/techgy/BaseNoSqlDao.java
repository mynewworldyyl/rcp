package net.techgy;

import java.io.Serializable;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;


public abstract class BaseNoSqlDao<T,ID extends Serializable> implements IBaseNosqlDao<T,ID>{

	@Autowired
	private MongoTemplate mongoTemplate = null;

	public MongoTemplate getTemplate() {
		return mongoTemplate;
	}

	public void setTemplate(MongoTemplate template) {
		this.mongoTemplate = template;
	}

	@Override
	public void save(T entity) {
      this.mongoTemplate.save(entity);		
	}

	@Override
	public void remove(Class<T> cls, ID id) {
		
		//this.getTemplate().remove(cls, id);
	}

	@Override
	public T update(T entity) {
		//this.template.
		throw new RuntimeException("not support");
	}

	@Override
	public T find(Class<T> cls, ID id) {
		// TODO Auto-generated method stub
		return this.find(cls, id);
	}	
}
