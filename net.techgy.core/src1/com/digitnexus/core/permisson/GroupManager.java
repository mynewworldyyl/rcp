package com.digitnexus.core.permisson;

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
import com.digitnexus.core.account.Account;
import com.digitnexus.core.account.AccountDaoImpl;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.idgenerator.ICacheIDGenerator;
import com.digitnexus.core.vo.dept.GroupVo;

@Component
public class GroupManager {

	@Autowired
	private GroupDaoImpl groupDao;
	
	@Autowired
	private AccountDaoImpl accountDao;
	
	@Autowired
	private PermDaoImpl permDao;

	@Autowired
	private ICacheIDGenerator generator;
	
	public List<Group> queryList(Map<String, String> params) {
		StringBuffer sql = new StringBuffer("SELECT a FROM Group a WHERE a.client.id !=0 ");
		String rootClientID = params.get(Client.CLIENT_ID_KEY);
		if(rootClientID != null) {
			sql.append(" AND a.client.id='").append(params.get(Client.CLIENT_ID_KEY)).append("' ");
		}
		if(!UserContext.isAdminAccount()) {
			sql.append(" AND a.typecode='").append(Group.GROUP_TYPE_COMMON).append("' ");
		}
		
		String gt = params.get("groupType");
		if(gt != null && "".equals(gt.trim())) {
			sql.append(" AND a.typecode='").append(gt).append("' ");
		}
		
		sql.append(" ORDER BY a.name ");
		Query query = accountDao.getEntityManager().createQuery(sql.toString());
		List<Group> l = query.getResultList();
		return l;
	
	}

	public void update(List<GroupVo> objList) {
		 for(GroupVo vo : objList) {
			 update(vo);
		  }
	}
	
	public void update(GroupVo vo) {
		Group group = groupDao.find(Group.class, vo.getId());
		if(group == null) {
			throw new CommonException("GroupNotExist",vo.getName());
		}
		this.getFromVo(vo,group); 
        groupDao.merge(group);
	}
	
	public void save(GroupVo vo) {
		Group group = this.getFromVo(vo,null);
        if(group.getId() == null  || "".equals(group.getId().trim())) {
        	group.setId(generator.getStringId(Group.class));
    	} else {
    		Group a = groupDao.find(Group.class, group.getId());
    		if(a != null) {
    			throw new CommonException("GroupExist",group.getName());
    		}
    	}
        Group an = null;
		try {
			an = (Group)groupDao.getEntityManager().createNamedQuery("getGroupByName").setParameter("name", group.getId()).getSingleResult();
			
		} catch (NoResultException e) {
			
		}
		if(an != null) {
			throw new CommonException("AccountNameExist",group.getName());
		}
		 this.groupDao.save(group);
	}

	public void save(List<GroupVo> vos) {
		 for(GroupVo vo : vos) {
			this.save(vo);
	     }
	}

	@SuppressWarnings("unchecked")
	private Group getFromVo(GroupVo vo,Group g) {
		if(g == null) {
			g = new Group(vo.getName(),vo.getTypecode(),vo.getDescription());
		}
		g.setDescription(vo.getDescription());
		g.setName(vo.getName());
		g.setTypecode(vo.getTypecode());
		g.setClient(UserContext.getCurrentUser().getLoginClient());
		g.getAccounts().clear();
		if(vo.getAccounts() != null && !vo.getAccounts().isEmpty()) {
			List<Account> accounts = this.accountDao.getAccountByAccountIds(vo.getAccounts());
			if(accounts != null) {
				Set<Account> set = new HashSet<Account>();
				set.addAll(accounts);
				g.setAccounts(set);
			    for(Account a : accounts) {
			    	a.getGroups().add(g);
			    }
			}
		}
		g.getPermissions().clear();
		if(vo.getPermissions() != null && !vo.getPermissions().isEmpty()) {
			List<Permission> ps = this.permDao.getEntityManager().createNamedQuery("findPermByIds")
					.setParameter("ids", vo.getPermissions())
					.getResultList();
			if(ps != null) {
				Set<Permission> set = new HashSet<Permission>();
				set.addAll(ps);
				g.setPermissions(set);
			}
		}
		return g;
	}

	public void delete(List<String> ids) {
		if(ids == null || ids.isEmpty()) {
			return;
		}
		this.permDao.getEntityManager().createNamedQuery("removeGroupById")
		.setParameter("ids", ids)
		.executeUpdate();
	}
}
