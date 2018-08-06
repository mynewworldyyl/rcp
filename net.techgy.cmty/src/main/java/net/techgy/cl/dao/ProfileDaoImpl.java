package net.techgy.cl.dao;

import net.techgy.BaseJpalDao;
import net.techgy.cl.Profile;

import org.springframework.stereotype.Repository;

@Repository
public class ProfileDaoImpl  extends BaseJpalDao<Profile,Long>   implements IProfileDao {

	@Override
	public void saveProfile(Profile profile) {
		this.save(profile);
	}

	@Override
	public void delProfile(Profile profile) {
        this.delProfile(profile.getId());		
	}

	@Override
	public void delProfile(Long profileId) {
        this.remove(Profile.class, profileId);	
	}

	@Override
	public Profile updateProfile(Profile profile) {
		return this.update(profile);
	}

	@Override
	public Profile findProfile(Long profileId) {
		return this.find(Profile.class, profileId);
	}

	
}
