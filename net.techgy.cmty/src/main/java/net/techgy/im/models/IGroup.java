package net.techgy.im.models;

import java.util.Set;

public interface IGroup {

	String DEFAULT_FRIEND ="my friends";
	
	long getId();
	String getDesc();
	String getName();
	Set<UserImpl> getFriends();
	
}
