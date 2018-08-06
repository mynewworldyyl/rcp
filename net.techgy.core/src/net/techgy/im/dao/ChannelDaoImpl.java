package net.techgy.im.dao;

import net.techgy.BaseJpalDao;
import net.techgy.im.models.ChannelImpl;

import org.springframework.stereotype.Repository;

@Repository
public class ChannelDaoImpl  extends BaseJpalDao<ChannelImpl,Long>  implements IChannelDao {

	@Override
	public void saveChannel(ChannelImpl channel) {
		// TODO Auto-generated method stub
		 this.save(channel);
	}

	@Override
	public void removeChannel(ChannelImpl channel) {
		// TODO Auto-generated method stub
		this.remove(ChannelImpl.class, channel.getId());
	}

	@Override
	public ChannelImpl findChannel(long channelId) {
		// TODO Auto-generated method stub
		return this.find(ChannelImpl.class, channelId);
	}

	@Override
	public void updateChannel(ChannelImpl channel) {
		// TODO Auto-generated method stub
		this.update(channel);
	}
	
	
}
