package net.techgy.cl.manager;

import java.util.List;

import net.techgy.cl.services.CfgFeedVO;

public interface ICfgFeedManager {

	boolean createCfgFeed(CfgFeedVO attr);
	
	boolean updateCfgFeed(CfgFeedVO attr);
	
	boolean delCfgFeed(Long id);
	
	CfgFeedVO findICfgFeed(Long id);
	
	List<CfgFeedVO> findCfgFeed(String name);
	
	List<CfgFeedVO> findCfgFeedByNameOrNS(String qStr);
}
