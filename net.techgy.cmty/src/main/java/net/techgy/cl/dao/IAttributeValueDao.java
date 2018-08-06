package net.techgy.cl.dao;

import net.techgy.IBaseDao;
import net.techgy.cl.Attribute;
import net.techgy.cl.AttrValue;

public interface IAttributeValueDao  extends IBaseDao<AttrValue,Long>{

	void saveAttributeValue(AttrValue attrValue);
	
	void delAttributeValue(AttrValue attrValue);
	
	void delAttributeValue(Long attrValueId);
	
	AttrValue updateAttributeValue(AttrValue attrValue);
	
	AttrValue findAttributeValue(Long attrValueId);
}
