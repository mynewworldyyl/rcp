package net.techgy.cl.dao;

import java.util.List;

import net.techgy.IBaseDao;
import net.techgy.cl.Attribute;
import net.techgy.cl.Namespace;

public interface INamespaceDao  extends IBaseDao<Namespace,Long>{

	void saveNamespace(Namespace attr);
	
	void delNamespace(Namespace attr);
	
	void delNamespace(Long attrId);
	
	Namespace updateNamespace(Namespace attr);
	
	Namespace findNamespace(Long attrId);
	
	Namespace findNamespace(String namespace);
	
	List<Namespace> findNSByLike(String namespace);
	
	List<Namespace> searchAttrNamespace(String queryStr);
	
	List<Namespace> searchClassNamespace(String queryStr);
}
