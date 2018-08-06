package net.techgy.im.dao;

import net.techgy.IBaseDao;
import net.techgy.im.models.ChannelImpl;

public interface IChannelDao  extends IBaseDao<ChannelImpl,Long>{

	void saveChannel(ChannelImpl channel);
	void removeChannel(ChannelImpl channel);
	ChannelImpl findChannel(long channel);
	void updateChannel(ChannelImpl channel);
}
