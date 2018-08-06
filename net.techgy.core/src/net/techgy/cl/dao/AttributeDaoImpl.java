package net.techgy.cl.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import net.techgy.BaseJpalDao;
import net.techgy.cl.Attribute;

import org.springframework.stereotype.Repository;

@Repository
public class AttributeDaoImpl extends BaseJpalDao< Attribute,Long>   implements IAttributeDao {

	@Override
	public void saveAttribute(Attribute attr) {
		 this.save(attr);
	}

	@Override
	public void delAttribute(Attribute attr) {
		 this.remove(Attribute.class, attr.getId());
	}

	@Override
	public void delAttribute(Long attrId) {
		 this.remove(Attribute.class, attrId);
	}

	@Override
	public Attribute updateAttribute(Attribute attr) {
		return this.update(attr);
	}

	@Override
	public Attribute findAttribute(Long attrId) {
		return this.find(Attribute.class, attrId);
	}

	@Override
	public List<Attribute> findAttributesByClsId(Long clsId) {
		return null;
	}

	@Override
	public List<Attribute> findAttributesByNamspaceName(String namespace) {
		String jplStr = "SELECT a FROM Attribute a WHERE a.namespace = :namespace";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("namespace", namespace);
		return query.getResultList();
	}

	@Override
	public List<Attribute> findAttributeByConditionStr(String qryStr) {
		//qryStr = this.constructWhereConditions(qryStr, "a");
		String[] qarra = qryStr.split(",");		
		StringBuffer jplStr = new StringBuffer("SELECT a FROM net.techgy.cl.Attribute a ");
		if(qarra != null && qarra.length > 1) {
			jplStr.append(" WHERE ");
			jplStr.append(qarra[0]).append(" like ");
			if(qarra[1] == null || qarra[1].trim().equals("")) {
				jplStr.append("'%'");
			}else {
				jplStr.append(" '%").append(qarra[1]).append("%' ");
			}
			
			int index = 2;
			for(; index < qarra.length ; index+=2) {				
				if(qarra[index+1] != null && !"".equals(qarra[index+1].trim())) {
					jplStr.append(" OR ").append(qarra[index]).append(" like ").append(" '%").append(qarra[index+1]).append("%'");
				}
			}
		}	
		Query query = this.getEntityManager().createQuery(jplStr.toString());
		return query.getResultList();
	}
}
