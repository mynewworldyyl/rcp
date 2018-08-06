package net.techgy.cl.dao;

import java.util.List;

import javax.persistence.Query;

import net.techgy.BaseJpalDao;
import net.techgy.cl.CLConstants;
import net.techgy.cl.Class;

import org.springframework.stereotype.Component;

@Component
public class VariantClassDaoImpl  extends BaseJpalDao<Class,Long>   implements IClassDao {

	@Override
	public void saveVariantClass(Class attrValue) {
		this.save(attrValue);
	}

	@Override
	public void delVariantClass(Class attrValue) {
		this.delVariantClass(attrValue.getId());
	}

	@Override
	public void delVariantClass(Long attrValueId) {
		this.remove(Class.class, attrValueId);
	}

	@Override
	public Class updateVariantClass(Class attrValue) {
		return this.update(attrValue);
	}

	@Override
	public Class findVariantClass(Long attrValueId) {
		return this.find(Class.class, attrValueId);
	}
	
	@Override
	public List<Class> findClassByLike(String qryStr) {
		qryStr = qryStr == null ? "":qryStr.trim();
		StringBuffer jplStr = new StringBuffer("SELECT a FROM net.techgy.cl.VariantClass a WHERE a.namespace LIKE :namespace ");
		jplStr.append(" OR a.name LIKE :name ")
		.append(" OR a.desc LIKE :desc ");		
		Query query = this.getEntityManager().createQuery(jplStr.toString());
		qryStr = "%"+qryStr+"%";
		
		query.setParameter("namespace",qryStr);
		query.setParameter("name",qryStr);
		query.setParameter("desc",qryStr);
		return query.getResultList();
	}

	@Override
	public List<Class> findVariantClass(String namespace) {
		String jplStr = "SELECT a FROM VariantClass a WHERE a.namespace like :namespace and a.type=:type";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("namespace","%" + namespace +"%");
		query.setParameter("type", CLConstants.CLS_CFG_TYPE);
		return query.getResultList();
	}

	@Override
	public List<Class> findInsClass(String namespace) {
		String jplStr = "SELECT a FROM VariantClass a WHERE a.namespace=:namespace and a.type=:type";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("namespace", namespace);
		query.setParameter("type", CLConstants.CLS_INS_TYPE);
		return query.getResultList();
	}

	

}
