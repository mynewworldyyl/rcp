package net.techgy.im.dao;

import net.techgy.BaseJpalDao;
import net.techgy.im.models.GroupImpl;

import org.springframework.stereotype.Repository;

@Repository("groupDaoImpl1")
public class GroupDaoImpl  extends BaseJpalDao<GroupImpl,Long> implements IGroupDao {

	@Override
	public void saveGroup(GroupImpl group) {
		// TODO Auto-generated method stub
	    this.save(group);
	}

	@Override
	public void removeGroup(GroupImpl group) {
		// TODO Auto-generated method stub
		this.remove(GroupImpl.class,group.getId());
	}

	@Override
	public GroupImpl findGroup(long groupId) {
		// TODO Auto-generated method stub
		return this.find(GroupImpl.class, groupId);
	}

	@Override
	public void updateGroup(GroupImpl group) {
		// TODO Auto-generated method stub
		this.update(group);
	}

	
}
