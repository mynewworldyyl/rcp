package net.techgy.cl.manager;

import java.util.List;

import net.techgy.cl.Attribute;
import net.techgy.ui.manager.PName;

public interface IAttributeManager {

	boolean createAttribute(Attribute attr);
	
	boolean updateAttribute(Attribute attr);
	
	boolean delAttribute(Long id);
	
	Attribute findAttribute(Long id);
	
	List<Attribute> findAttribute(String namespace);
	
	List<Attribute> findAttributeByLike(String namespace);
	
}
