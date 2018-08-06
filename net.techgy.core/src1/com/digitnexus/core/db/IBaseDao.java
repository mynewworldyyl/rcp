package com.digitnexus.core.db;

import java.io.Serializable;



public interface IBaseDao<T,ID extends Serializable> {

	void save(T entity);
	
	void remove(Class<T> cls, ID id);
	
	T update(T entity);
	
	T find(Class<T> cls, ID id);
	
	void removeById(Class<T> cls,ID id);
	
	void merge(T entity);
}
