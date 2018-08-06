package net.techgy.cl.dao;

import java.util.List;

import net.techgy.IBaseDao;
import net.techgy.cl.Attribute;

public interface IAttributeDao  extends IBaseDao<Attribute,Long>{

	void saveAttribute(Attribute attr);
	
	void delAttribute(Attribute attr);
	
	void delAttribute(Long attrId);
	
	Attribute updateAttribute(Attribute attr);
	
	Attribute findAttribute(Long attrId);
	
	List<Attribute> findAttributesByClsId(Long clsId);
	
	List<Attribute> findAttributesByNamspaceName(String namespace);
	
	public List<Attribute> findAttributeByConditionStr(String namespace);
}
