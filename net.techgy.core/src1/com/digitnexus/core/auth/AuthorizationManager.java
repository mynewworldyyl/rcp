package com.digitnexus.core.auth;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.account.Account;
import com.digitnexus.core.account.AccountManager;
import com.digitnexus.core.i18n.I18NUtils;
import com.digitnexus.core.permisson.Group;
import com.digitnexus.core.permisson.Permission;
import com.digitnexus.core.vo.dept.AccountVo;

@Component
public class AuthorizationManager implements IAuthorization{
	
	@Autowired
	private AccountManager accountManager ;
	
	@Override
	public boolean authorize(String entityType, String action,boolean...throwExcepton) {
		return this.authorize(UserContext.getAccount(), entityType, action,throwExcepton);
	}
	
	@Override
	public boolean authorize(String accountId, String entityType, String action,boolean...throwExcepton) {
		return this.authorize(this.accountManager.getAccountById(accountId), entityType, action,throwExcepton);
	}

	@Override
	public boolean authorize(Account account, String entityType, String action,boolean...throwExcepton) {
		this.doCheck(account, entityType, action);
		boolean authorizeValid = authorizePermission(account.getPermissons(),entityType,action);
		if(authorizeValid) {
			return true;
		}
		for(Group g : account.getGroups()) {
			authorizeValid = authorizePermission(g.getPermissions(),entityType,action);
			if(authorizeValid) {
				return true;
			}
		}
		if(throwExcepton != null && throwExcepton.length > 0 && throwExcepton[0]) {
			throwException(account,entityType, action);
		}
		return false;
	}
	
	private void throwException(Account account, String entityType, String action) {
		throw new CommonException("NoPermission",account.getAccountName(),
				I18NUtils.getInstance().getString(entityType),I18NUtils.getInstance().getString(action));
	}
	
	private boolean authorizePermission(Set<Permission> permissions,
			String entityType, String action) {
		if(permissions == null || permissions.isEmpty()) {
			return false;
		}
		for(Permission p : permissions) {
			if(entityType.equals(p.getEntityType()) && action.equals(p.getAction())) {
				return true;
			}
		}
		return false;
	}

	private void doCheck(Account account, String entityType, String action) {
		if(account == null) {
			throw new CommonException("AccountIsNull");
		}
		if(entityType == null || "".equals(entityType.trim())) {
			throw new CommonException("EntityTypeIsNull");
		}
		if(action == null || "".equals(action.trim())) {
			throw new CommonException("ActionIsNull");
		}
	}

	@Override
	public boolean authorize(AccountVo account, String entityType, String action,boolean...throwExcepton) {
		return this.authorize(account.getId(), entityType, action,throwExcepton);
	}

	
}
