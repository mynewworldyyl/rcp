package net.techgy.cl.dao;

import java.util.List;

import net.techgy.IBaseDao;
import net.techgy.cl.Class;

public interface IClassDao  extends IBaseDao<Class,Long>{

    void saveVariantClass(Class cls);
	
	void delVariantClass(Class clsValue);
	
	void delVariantClass(Long clsId);
	
	Class updateVariantClass(Class cls);
	
	Class findVariantClass(Long classId);
	
	List<Class> findVariantClass(String namespace);
	
	List<Class> findInsClass(String namespace);
	
	List<Class> findClassByLike(String qryStr);
}
