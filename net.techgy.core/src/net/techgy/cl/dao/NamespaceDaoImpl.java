package net.techgy.cl.dao;

import java.util.List;

import javax.persistence.Query;

import net.techgy.BaseJpalDao;
import net.techgy.cl.Attribute;
import net.techgy.cl.Namespace;

import org.springframework.stereotype.Repository;

@Repository
public class NamespaceDaoImpl extends BaseJpalDao<Namespace,Long>   implements INamespaceDao {

	@Override
	public void saveNamespace(Namespace namespace) {
		this.save(namespace);
	}

	@Override
	public void delNamespace(Namespace namespace) {		
		this.delNamespace(namespace.getId());
	}

	@Override
	public void delNamespace(Long namespaceId) {
		this.remove(Namespace.class, namespaceId);		
	}

	@Override
	public Namespace updateNamespace(Namespace namespace) {
		return this.update(namespace);
	}

	@Override
	public Namespace findNamespace(Long namespaceId) {
		return this.find(Namespace.class, namespaceId);		
	}

	@Override
	public Namespace findNamespace(String namespace) {
		Query query = this.getEntityManager().createNamedQuery("findNamespace");
		query.setParameter("namespace", namespace);
		return (Namespace)query.getSingleResult();
	}

	@Override
	public List<Namespace> findNSByLike(String namespace) {
		Query query = this.getEntityManager().createNamedQuery("findNSByLike");
		query.setParameter("namespace", namespace);
		return query.getResultList();
	}
	
	public List<Namespace> searchAttrNamespace(String conStr) {
		//获取26 岁人的订单,Order 中必须要有OrderItem
		//select o from Order o inner join o.orderItems where o.ower.age=26 order by o.orderid
		StringBuffer jplStr = new StringBuffer("SELECT DISTINCT a FROM Namespace a LEFT JOIN FETCH a.attrs at WHERE ");		
		if(conStr != null && !"".equals(conStr.trim())) {
			jplStr.append("a.namespace LIKE '%").append(conStr).append("%'")
			.append(" OR at.name LIKE '%").append(conStr).append("%'");
		} else {
			jplStr.append("a.namespace LIKE '%' OR at.name LIKE '%'");
		}
		//jplStr.append(" )");
		Query query = this.getEntityManager().createQuery(jplStr.toString());
		Object o = query.getResultList();
		return (List<Namespace>)o;
	}

	@Override
	public List<Namespace> searchClassNamespace(String conStr) {
		StringBuffer jplStr = new StringBuffer("SELECT DISTINCT a FROM Namespace a, IN(a.classes) at "
				+ " WHERE ");		
		if(conStr != null && !"".equals(conStr.trim())) {
			jplStr.append("a.namespace LIKE '%").append(conStr).append("%'")
			.append(" OR at.name LIKE '%").append(conStr).append("%'");
		} else {
			jplStr.append("a.namespace LIKE '%' OR at.name LIKE '%'");
		}
		Query query = this.getEntityManager().createQuery(jplStr.toString());
		Object o = query.getResultList();
		return (List<Namespace>)o;
	}
	
	
}
