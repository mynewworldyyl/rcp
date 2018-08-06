package net.techgy.cl.manager.impl;

import java.util.List;

import net.techgy.cl.CfgFeed;
import net.techgy.cl.dao.IAttributeDao;
import net.techgy.cl.dao.ICfgFeedDao;
import net.techgy.cl.manager.ICfgFeedManager;
import net.techgy.cl.services.CfgFeedVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("CfgFeedManagerImpl1")
public class CfgFeedManagerImpl implements ICfgFeedManager{

	@Autowired
	private ICfgFeedDao feedDao;
	
	@Autowired
	private IAttributeDao attrDao;
	
	@Transactional
	@Override
	public boolean createCfgFeed(CfgFeedVO attr) {
		/*CfgFeed feed = ClUtils.getInstance().cfgFeedVOToCfgFeed(attr, attrDao);
		feedDao.save(feed);*/
		return true;
	}

	@Transactional
	@Override
	public boolean updateCfgFeed(CfgFeedVO attr) {
		//CfgFeed feed = ClUtils.getInstance().cfgFeedVOToCfgFeed(attr, attrDao);
		//this.feedDao.update(feed);
		return true;
	}

	@Transactional
	@Override
	public boolean delCfgFeed(Long id) {
		this.feedDao.remove(CfgFeed.class, id);
		return true;
	}

	@Transactional
	@Override
	public CfgFeedVO findICfgFeed(Long id) {
		/*CfgFeed feed = this.feedDao.find(CfgFeed.class, id);
		return ClUtils.getInstance().cfgFeedToCfgFeedVO(feed, attrDao);*/
		return null;
	}

	@Transactional
	@Override
	public List<CfgFeedVO> findCfgFeed(String name) {
		/*name = name == null ?"":name.trim();
		List<CfgFeed> cfs = this.feedDao.findCfgFeedByName(name);
		List<CfgFeedVO> cfvos = new ArrayList<CfgFeedVO>();
		for(CfgFeed cf : cfs) {
			cfvos.add(ClUtils.getInstance().cfgFeedToCfgFeedVO(cf, attrDao));
		}*/
		return null;
	}

	@Override
	public List<CfgFeedVO> findCfgFeedByNameOrNS(String qStr) {
		/*qStr = qStr == null ?"":qStr.trim();
		List<CfgFeed> cfs = this.feedDao.findCfgFeedByNameOrNS(qStr);
		List<CfgFeedVO> cfvos = new ArrayList<CfgFeedVO>();
		for(CfgFeed cf : cfs) {
			cfvos.add(ClUtils.getInstance().cfgFeedToCfgFeedVO(cf, attrDao));
		}*/
		return null;
	}	
	
	
}
