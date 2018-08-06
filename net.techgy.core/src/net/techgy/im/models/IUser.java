package net.techgy.im.models;

import java.util.Set;

import net.techgy.usercenter.IRole;

public interface IUser extends IRole{

	Set<GroupImpl> getFriendGroups();
	Set<ChannelImpl> getChannels();
}
