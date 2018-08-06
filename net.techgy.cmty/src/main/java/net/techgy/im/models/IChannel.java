package net.techgy.im.models;

import java.util.Set;

public interface IChannel {

	static enum ChannelType{
		PUBLIC,
		PRIVATE,
	}
	String getChannelName();
	String getDescription();
	/**
	 * public: anyone can enter without approval
	 * private: need owner to approval
	 * @return
	 */
	ChannelType getChannelType();
	
	IUser getOWner();
	
	Set<UserImpl> getUsers();
	
	Set<IUser> getDeputy();
		
}
