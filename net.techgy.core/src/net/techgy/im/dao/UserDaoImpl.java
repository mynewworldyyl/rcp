package net.techgy.im.dao;

import javax.persistence.Query;

import net.techgy.BaseJpalDao;
import net.techgy.im.models.IAccount;
import net.techgy.im.models.UserImpl;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends BaseJpalDao<UserImpl, Long> implements IUserDao {

	@Override
	public UserImpl findUserById(long userId) {
		return this.getEntityManager().find(UserImpl.class, userId);
	}

	@Override
	public void saveUser(UserImpl user) {
		// TODO Auto-generated method stub
		this.save(user);
	}

	@Override
	public void removeUser(UserImpl user) {
		// TODO Auto-generated method stub
		this.remove(UserImpl.class, user.getId());
	}

	@Override
	public void updateUser(UserImpl user) {
		// TODO Auto-generated method stub
		this.update(user);
	}

	@Override
	public UserImpl findUserByAccount(String name) {
		//suppose nickname equals to account name
        return this.findUserByNickName(name);
	}

	@Override
	public UserImpl findUserByNickName(String name) {
		String jplStr = "SELECT a FROM UserImpl a WHERE a.name = :accountName";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("accountName", name);
		return (UserImpl)query.getSingleResult();
	}

	@Override
	public UserImpl findUserByAccountId(long accountId) {
		String jplStr = "SELECT u FROM UserImpl u WHERE u.account.id = :account";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("account", accountId);
		return (UserImpl)query.getSingleResult();
	}

	
}
