package com.digitnexus.core.account;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.dept.ClientManager;
import com.digitnexus.core.dept.Employee;
import com.digitnexus.core.dept.EmployeeDaoImpl;
import com.digitnexus.core.idgenerator.ICacheIDGenerator;
import com.digitnexus.core.vo.dept.AccountVo;

@Component("AccountManager1")
public class AccountManager {

	@Autowired
	private AccountDaoImpl accountDao;
	
	@Autowired
	private EmployeeDaoImpl empDao;
	
	@Autowired
	private ICacheIDGenerator generator;
	
	@Autowired
	private ClientManager clientManager ;
	
	private Map<String,Account> loginAccounts = new HashMap<String,Account>();
	
	public Map<String,String> login(String username,String pw){
		Account account = null;
		try {
			account = accountDao.getActiveAccountByUsernameAndPassword(username, pw);
		} catch (NoResultException e) {
			//return Collections.emptyMap();
		}
		if(account == null) {
			return Collections.emptyMap();
		}
		Map<String,String> m = new HashMap<String,String>();
		for(Client c : account.getRelatedClients()) {
			m.put(c.getId(), c.getName());
		}
		loginAccounts.put(account.getAccountName(), account);
		
		return m;
	}
	
	public Account getActieAccountByName(String accountName,String clientId){
		Account account = accountDao.getActiveAccountByAccountName(accountName,clientId);
		return account;
	}
	
	public Account getAccountById(String accountId){
		Account account = accountDao.find(Account.class, accountId);
		return account;
	}
	
	public List<Account> queryList(Map<String,String> params) {
		StringBuffer sql = new StringBuffer("SELECT DISTINCT a FROM Account a ");
		String rootClientID = params.get(Client.CLIENT_ID_KEY);
		//append join
		List<String> cl = null;
		if(rootClientID != null) {
			cl = this.clientManager.getSubClientIds(rootClientID);
			sql.append(" LEFT JOIN a.relatedClients rc ");
		}	
		
		//append join where
		sql.append(" WHERE a.client.id !=0 ");
		
		sql.append(" AND (rc.id IN :clientList OR a.client.id=:loginClientId)");
		
		if(!this.isCurrentAdminAccount()) {
			sql.append(" AND a.typeCode=:typecode ");
			//.append(Account.TYPE_COMMON).append("' ");
		}
		//SELECT DISTINCT a FROM Account a  JOIN a.relatedClients rc  WHERE a.client.id !=0  
		//AND (rc.id IN :clientList OR a.client.id='')
		Query query = accountDao.getEntityManager().createQuery(sql.toString());
		
		//SET parameters
		query.setParameter("clientList", cl);
		query.setParameter("loginClientId", UserContext.getCurrentClientId());
		if(!this.isCurrentAdminAccount()) {
			query.setParameter("typecode", Account.TYPE_COMMON);
		}
		
		List<Account> l = query.getResultList();
		return l;
	}
	
	public AccountVo getOneVo(Account a) {
		AccountVo vo = new AccountVo();
		vo.setName(a.getAccountName());
		vo.setDefaultClient(a.getClient().getName());
        if(a.getEmployee() != null) {
        	vo.setEmployee(a.getEmployee().getId());
        }
		vo.setId(a.getId());
		vo.setPw(a.getPassword());
		Set<String> cids = new HashSet<String>();
		for(Client c : a.getRelatedClients()) {
			cids.add(c.getId());
		}
		vo.setRelatedClients(cids);
		vo.setStatu(a.getStatu());
		return vo;
	}

	
	public void delete(String id) {
		Account a = this.accountDao.find(Account.class, id);
		if(a == null) {
			throw new CommonException("AccountNotExist",id);
		}else {
			if(this.isAdminAccount(a)) {
				throw new CommonException("CannotDeleteAdminAccount",a.getAccountName());
			}
		}
		accountDao.removeById(Account.class, id);
	}
	
	public void delete(List<String> idList) {
		for(String id : idList) {
			this.delete(id);
		}
	}
	
    public void update(Account account) {
    	if(account.getId() == null || "".equals(account.getId().trim())) {
    		throw new CommonException("AccountNotExist",account.getAccountName());
    	}
		Account a = accountDao.find(Account.class, account.getId());
		if(a != null) {
			a.setPassword(account.getPassword());
			if(!this.isAdminAccount(a)) {
				a.setStatu(account.getStatu());
			}
			a.setRelatedClients(account.getRelatedClients());
		} else {
			throw new CommonException("AccountNotExist",account.getAccountName());
		}
	}
	
    public void save(Account account) {
    	if(account.getId() == null  || "".equals(account.getId().trim())) {
    		account.setId(generator.getStringId(Account.class));
    	} else {
    		Account a = accountDao.find(Account.class, account.getId());
    		if(a != null) {
    			throw new CommonException("AccountExist",account.getAccountName());
    		}
    	}
    	Account an = null;
		try {
			an = accountDao.getAccountByAccountName(account.getAccountName());
		} catch (NoResultException e) {
			
		}
		if(an != null) {
			throw new CommonException("AccountNameExist",account.getAccountName());
		}
		accountDao.save(account);
   	}
    
    public void save(List<AccountVo> avoList) {
    	for(AccountVo vo : avoList) {
    		Account account = voToEntity(vo);
    		this.save(account);
    	}
   	}

	private Account voToEntity(AccountVo vo) {
		Account a = new Account();
		a.setAccountName(vo.getName());
		a.setClient(UserContext.getCurrentUser().getLoginClient());
		if(vo.getEmployee() != null) {
			Employee emp = this.empDao.find(Employee.class, vo.getEmployee());
			if(emp == null) {
				throw new CommonException("EmployeeNotExist",vo.getEmployee());
			}
			a.setEmployee(emp);
		}
		a.setId(vo.getId());
		a.setPassword(vo.getPw());
		Set<Client> clientList = this.clientManager.getClients(vo.getRelatedClients());
		a.setRelatedClients(clientList);
		a.setStatu(vo.getStatu());
		a.setTypeCode(vo.getTypeCode());
		return a;
	}

	public void update(List<AccountVo> objList) {
		for(AccountVo vo : objList) {
    		Account account = voToEntity(vo);
    		this.update(account);
    	}
	}
	
	public boolean isCurrentAdminAccount() {
		return UserContext.isAdminAccount();
	}
	
	public boolean isAdminAccount(String typeCode) {
		return Account.TYPE_ADMIN.equals(typeCode);
	}

	public boolean isAdminAccount(AccountVo account) {
		return Account.TYPE_ADMIN.equals(account.getTypeCode());
	}
	
	public boolean isAdminAccount(Account account) {
		return Account.TYPE_ADMIN.equals(account.getTypeCode());
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,String> keyValues() {
		List<Account> as = this.accountDao.getEntityManager().createNamedQuery("keyValues")
				.setParameter("relatedClients", UserContext.getCurrentClientId()).getResultList();
		if(as == null || as.isEmpty()) {
			return null;
		}
		Map<String,String> kvs = new HashMap<String,String>();
		for(Account a : as) {
			kvs.put(a.getId(), a.getAccountName());
		}
		return kvs;
	}

	public void logout(String username) {
		this.loginAccounts.remove(username);
	}

	public Account getLoginAccount(String an) {
		if(an == null) {
			an = UserContext.getAccount().getAccountName();
		}
		return this.loginAccounts.get(an);
	}
}
