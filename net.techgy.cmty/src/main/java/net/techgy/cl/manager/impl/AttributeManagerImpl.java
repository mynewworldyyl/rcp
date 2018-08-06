package net.techgy.cl.manager.impl;

import java.util.ArrayList;
import java.util.List;

import net.techgy.cl.Attribute;
import net.techgy.cl.dao.IAttributeDao;
import net.techgy.cl.manager.IAttributeManager;
import net.techgy.ui.manager.Operation;
import net.techgy.ui.manager.PName;
import net.techgy.ui.manager.VOManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@VOManager("attributeManager")
@Component("AttributeManagerImpl1")
public class AttributeManagerImpl implements IAttributeManager{

	private static Logger logger = Logger.getLogger(AttributeManagerImpl.class);
	
	@Autowired
	private IAttributeDao attrDao;
	
	@Operation(value="createAttribute")
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public boolean createAttribute(@PName("attr") Attribute attr) {
		//Attribute attr = ClUtils.getInstance().attrVOToAttr(attrVO,attrDao);
        this.attrDao.save(attr);
		return true;
	}

	@Operation(value="updateAttribute")
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public boolean updateAttribute(@PName("attr") Attribute attr) {
		//Attribute attr = ClUtils.getInstance().attrVOToAttr(attrVO,attrDao);
        this.attrDao.update(attr);
		return true;
	}

	@Operation(value="delAttribute")
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public boolean delAttribute(@PName("id") Long id) {
		boolean flag = false;
		Attribute attr = this.attrDao.findAttribute(id);
		if(attr.getClasses() == null || attr.getClasses().isEmpty()) {
			this.attrDao.delAttribute(id);
			flag=true;
		}
		return flag;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public Attribute findAttribute(@PName("id") Long id) {
		Attribute attr = this.attrDao.findAttribute(id);
		return attr;//ClUtils.getInstance().attrToAttrVO(attr);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public List<Attribute> findAttribute(@PName("namespace") String namespace) {
		List<Attribute> attrs = this.attrDao.findAttributesByNamspaceName(namespace);
		List<Attribute> as = new ArrayList<Attribute>();
		for(Attribute a : attrs) {
			try {
				as.add(a.clone());
			} catch (CloneNotSupportedException e) {
				logger.error("", e);
			}
		}
		return as;
	}

	@Operation("findAttributeByConditionStr")
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public List<Attribute> findAttributeByLike(@PName("queryStr") String queryStr) {
		List<Attribute> attrs = this.attrDao.findAttributeByConditionStr(queryStr);
		return attrs;
	}	
}
