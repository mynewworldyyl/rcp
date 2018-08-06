package net.techgy.cl.manager.impl;

import java.util.List;

import net.techgy.cl.Attribute;
import net.techgy.cl.Namespace;
import net.techgy.cl.dao.IAttributeDao;
import net.techgy.cl.dao.IClassDao;
import net.techgy.cl.dao.INamespaceDao;
import net.techgy.cl.manager.INamespaceManager;
import net.techgy.ui.manager.Operation;
import net.techgy.ui.manager.PName;
import net.techgy.ui.manager.VOManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@VOManager("namespaceManager")
@Component("NamespaceManagerImpl1")
public class NamespaceManagerImpl implements INamespaceManager{

	private static Logger logger = Logger.getLogger(NamespaceManagerImpl.class);
	
	@Autowired
	private INamespaceDao nsDao;
	
	@Autowired
	private IAttributeDao attrDao;
	
	@Autowired
	private IClassDao classDao;
	
	@Operation(value="createNS")
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public boolean createNamespace(@PName("ns") Namespace attr) {
        this.nsDao.save(attr);
		return true;
	}

	@Operation(value="updateNS")
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public boolean updateNamespace(@PName("ns") Namespace attr) {
        this.nsDao.update(attr);
		return true;
	}

	@Operation(value="delNS")
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public boolean delNamespace(@PName("id") Long id) {
		Namespace ns = this.nsDao.findNamespace(id);
		List<Attribute>  attrs = this.attrDao.findAttributesByNamspaceName(ns.getNamespace());	
		if(!attrs.isEmpty()) {
			throw new RuntimeException(ns.getNamespace() + " is used by other attribute so cannot be deleted!");
		}
		List<net.techgy.cl.Class> lcs = this.classDao.findVariantClass(ns.getNamespace());
		if(!lcs.isEmpty()) {
			throw new RuntimeException(ns.getNamespace() + " is used by other Class so cannot be deleted!");
		}
		this.nsDao.delNamespace(ns.getId());
		return true;
	}
	
	@Operation("findNSByID")
	public Namespace findNamespace(@PName("id") Long id) {
		Namespace ns = this.nsDao.findNamespace(id);
		return ns;
	}

	@Operation("findNSByName")
	public Namespace findNamespace(@PName("ns") String namespace) {
		Namespace ns = this.nsDao.findNamespace(namespace);
		return ns;
	}

	@Operation("findNSByLike")
	public List<Namespace> findNSByLike(@PName("queryStr") String queryStr) {
		List<Namespace> attrs = this.nsDao.findNSByLike(queryStr);
		return attrs;
	}
	
	@Operation("findNSAttrByLike")
	public List<Namespace> findNSAttrByLike(@PName("queryStr") String queryStr) {
		List<Namespace> nss = this.nsDao.searchAttrNamespace(queryStr);
		return nss;
	}
	
	@Operation("findNSClassByLike")
	public List<Namespace> findNSClassByLike(@PName("queryStr") String queryStr) {
		List<Namespace> nss = this.nsDao.searchClassNamespace(queryStr);
		return nss;
	}	
}
