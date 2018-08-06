package net.techgy.cl.services;

import java.util.Date;
import java.util.Iterator;

import net.techgy.cl.AttrValue;
import net.techgy.cl.Attribute;
import net.techgy.cl.manager.PersistenceListener;

import org.springframework.stereotype.Component;

@Component
public class AttributePersistenceListener implements PersistenceListener<Attribute> {

	@Override
	public Attribute beforce(Attribute entity) {
		if(entity.getAttrValues()== null || entity.getAttrValues().size()<1) {
			return entity;
		}
		Date d = new Date(System.currentTimeMillis());
		Iterator<AttrValue> avs = entity.getAttrValues().iterator();
		while(avs.hasNext()) {
			AttrValue av = avs.next();
			av.setAttr(entity);
			if(av.getUpdateDate() == null) {
				av.setUpdateDate(d);
			}
			if(av.getCreatedDate() == null) {
				av.setCreatedDate(d);
			}
		}
		if(entity.getUpdateDate() == null) {
			entity.setUpdateDate(d);
		}
		if(entity.getCreatedDate() == null) {
			entity.setCreatedDate(d);
		}
		return entity;
	}

	@Override
	public Attribute after(Attribute entity) {
		
		return entity;
	}

	@Override
	public Class<?> getProcessClass() {
		// TODO Auto-generated method stub
		return Attribute.class;
	}

	
}
