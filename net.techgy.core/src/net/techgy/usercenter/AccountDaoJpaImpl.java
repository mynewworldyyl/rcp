package net.techgy.usercenter;

import javax.persistence.Query;

import net.techgy.BaseJpalDao;
import net.techgy.im.models.IAccount;

import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoJpaImpl extends BaseJpalDao<IAccount,Long> implements IAccountDao {
	
	@Override
	public IAccount findAccountIdById(long accountId) {
		// TODO Auto-generated method stub
		return this.getEntityManager().find(AccountImpl.class, accountId);
	}

	@Override
	public IAccount findAccountByName(String accountName) {
		//SELECT DISTINCT t FROM Team t  WHERE t.players.name LIKE :name
		String jplStr = "SELECT a FROM AccountImpl a WHERE a.accountName = :accountName";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("accountName", accountName);
		return (IAccount)query.getSingleResult();
	}

	@Override
	public void saveAccount(IAccount account) {
		// TODO Auto-generated method stub
         this.save(account);
	}

	@Override
	public void removeAccount(IAccount account) {
		// TODO Auto-generated method stub
        this.getEntityManager().remove(account);
	}

	@Override
	public void removeAccountByName(String accountName) {
		// TODO Auto-generated method stub
		String jplStr = "DELETE FROM AccountImpl a WHERE a.accountName = :accountName";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("accountName", accountName);
		query.executeUpdate();
	}

	@Override
	public void removeAccountById(long accountId) {
		// TODO Auto-generated method stub
		IAccount account = this.findAccountIdById(accountId);
		if(null != account) {
			this.getEntityManager().remove(account);
		}      
	}

}
