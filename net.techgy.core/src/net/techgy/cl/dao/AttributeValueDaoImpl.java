package net.techgy.cl.dao;

import org.springframework.stereotype.Repository;

import net.techgy.BaseJpalDao;
import net.techgy.cl.AttrValue;

@Repository
public class AttributeValueDaoImpl extends BaseJpalDao<AttrValue,Long>   implements IAttributeValueDao {

	@Override
	public void saveAttributeValue(AttrValue attrValue) {
		 this.save(attrValue);
	}

	@Override
	public void delAttributeValue(AttrValue attrValue) {
		 this.remove(AttrValue.class, attrValue.getId());
	}

	@Override
	public void delAttributeValue(Long attrValueId) {
		this.remove(AttrValue.class, attrValueId);		
	}

	@Override
	public AttrValue updateAttributeValue(AttrValue attrValue) {
		return this.update(attrValue);
	}

	@Override
	public AttrValue findAttributeValue(Long attrValueId) {
		return this.find(AttrValue.class, attrValueId);
	}
}
