package net.techgy.im.dao;

import net.techgy.IBaseDao;
import net.techgy.im.models.GroupImpl;

public interface IGroupDao  extends IBaseDao<GroupImpl,Long>{

	void saveGroup(GroupImpl group);
	void removeGroup(GroupImpl group);
	GroupImpl findGroup(long groupId);
	void updateGroup(GroupImpl group);
}
