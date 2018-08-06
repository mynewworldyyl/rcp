package net.techgy.cl.dao;

import java.util.List;

import javax.persistence.Query;

import net.techgy.BaseJpalDao;
import net.techgy.cl.CfgFeed;

import org.springframework.stereotype.Component;

@Component
public class CfgFeedDaoImpl extends BaseJpalDao<CfgFeed,Long>    implements ICfgFeedDao {

	@Override
	public void saveCfgFeed(CfgFeed attrValue) {
		// TODO Auto-generated method stub
        this.save(attrValue);
	}

	@Override
	public void delCfgFeed(CfgFeed attrValue) {
		// TODO Auto-generated method stub
        this.remove(CfgFeed.class, attrValue.getId());
	}

	@Override
	public void delCfgFeed(Long attrValueId) {
		// TODO Auto-generated method stub
        this.remove(CfgFeed.class, attrValueId);
	}

	@Override
	public CfgFeed updateCfgFeed(CfgFeed attrValue) {
		// TODO Auto-generated method stub
		return this.update(attrValue);
	}

	@Override
	public CfgFeed findCfgFeed(Long attrValueId) {
		// TODO Auto-generated method stub
		return this.find(CfgFeed.class,attrValueId);
	}

	@Override
	public List<CfgFeed> findCfgFeedByName(String name) {
		String jplStr = "select cf from CfgFeed cf where cf.name like :name ";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("name", "%" + name +"%");
		return query.getResultList();
	}

	@Override
	public List<CfgFeed> findCfgFeedByNameOrNS(String qstr) {
		String jplStr = "select cf from CfgFeed cf where cf.name like :name or  cf.namespace like :namespace";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("name", "%" + qstr +"%");
		query.setParameter("namespace", "%" + qstr +"%");
		return query.getResultList();
	}	
}
