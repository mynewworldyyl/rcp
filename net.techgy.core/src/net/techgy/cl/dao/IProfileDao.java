package net.techgy.cl.dao;

import net.techgy.IBaseDao;
import net.techgy.cl.Attribute;
import net.techgy.cl.Profile;

public interface IProfileDao  extends IBaseDao<Profile,Long>{

void saveProfile(Profile profile);
	
	void delProfile(Profile profile);
	
	void delProfile(Long profileId);
	
	Profile updateProfile(Profile profile);
	
	Profile findProfile(Long profileId);
	
}
