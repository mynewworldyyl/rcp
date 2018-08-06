package net.techgy.cl.manager;

public interface PersistenceListener<T> {
	
	T beforce(T entity);
	
	T after(T entity);
	
	Class<?> getProcessClass();
}
