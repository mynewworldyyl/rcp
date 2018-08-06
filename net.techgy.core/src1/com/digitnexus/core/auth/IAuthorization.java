package com.digitnexus.core.auth;

import com.digitnexus.core.account.Account;
import com.digitnexus.core.vo.dept.AccountVo;

public interface IAuthorization {

	boolean authorize(String entityType,String action,boolean...throwExcepton);
	
	boolean authorize(String accountId,String entityType,String action,boolean...throwExcepton);
	
	boolean authorize(Account account,String entityType,String action,boolean...throwExcepton);
	
	boolean authorize(AccountVo account,String entityType,String action,boolean...throwExcepton);
}
