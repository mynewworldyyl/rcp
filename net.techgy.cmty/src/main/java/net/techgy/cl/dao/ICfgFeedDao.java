package net.techgy.cl.dao;

import java.util.List;

import net.techgy.IBaseDao;
import net.techgy.cl.CfgFeed;

public interface ICfgFeedDao  extends IBaseDao<CfgFeed,Long>{

	void saveCfgFeed(CfgFeed attrValue);
	
	void delCfgFeed(CfgFeed attrValue);
	
	void delCfgFeed(Long attrValueId);
	
	CfgFeed updateCfgFeed(CfgFeed attrValue);
	
	CfgFeed findCfgFeed(Long attrValueId);
	
	List<CfgFeed> findCfgFeedByName(String name);
	
	List<CfgFeed> findCfgFeedByNameOrNS(String name);
}
