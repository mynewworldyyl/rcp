package com.digitnexus.core.db;

import java.util.List;
import java.util.Map;

import com.digitnexus.base.protocol.Response;

public interface IPersistenceListener <E,V> {

	String getVoClsName();
	
	List<E> beforeSave(Object obj);
	Response afterSave(List<E> obj,Response resp);
	
	List<E> beforeUpdate(Object obj);
    Response afterUpdate(List<E> obj,Response resp);	
	
    Class<?> beforeGet(String clsName, String id);
    Response afterGet(E obj,Response resp);	
	
    String[] beforeDelete(String clsName, String id);
    Response afterDelete(Class<?> cls,String[] idss,Response resp);	
    
    Object beforeQuery(Class<?> cls, Map<String,String> params);
    List<V> afterQuery(String clsName, Map<String,String> params,List<E> entityList);	
    
    Response afterSaveEntity(E e, V vo,Response resp);
    
    Response afterUpdateEntity(E e, V vo,Response resp);
}
