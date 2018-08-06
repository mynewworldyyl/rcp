package net.techgy;

import java.io.Serializable;

import org.springframework.data.mongodb.core.MongoTemplate;



public interface IBaseDao<T,ID extends Serializable> {

	void save(T entity);
	
	void remove(Class<T> cls, ID id);
	
	T update(T entity);
	
	T find(Class<T> cls, ID id);
	
}
