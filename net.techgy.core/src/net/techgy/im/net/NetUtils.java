package net.techgy.im.net;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.techgy.im.models.GroupImpl;
import net.techgy.im.models.IAccount;
import net.techgy.im.models.UserImpl;

import org.apache.log4j.Logger;

public class NetUtils {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private static NetUtils instance = null;

	private NetUtils() {

	}

	public synchronized static NetUtils getInstance() {
		if (instance == null) {
			instance = new NetUtils();
		}
		return instance;
	}
	
	public List<GroupImpl> copyGroupList(UserImpl user){
		Set<GroupImpl> gl = user.getFriendGroups();
		List<GroupImpl> result = new ArrayList<GroupImpl>();
		try {
			for(GroupImpl g : gl){
				result.add(g.clone());
			}
		} catch (CloneNotSupportedException e) {
			logger.error(e.getMessage());
		}
		return result;
	}
	
	
}
