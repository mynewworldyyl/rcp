package net.techgy.usercenter;

import java.util.HashMap;
import java.util.Map;

import net.techgy.im.models.IAccount;

import org.springframework.stereotype.Component;

@Component
public class LoginManager {

	private Map<String, IAccount> loginAccounts = new HashMap<String, IAccount>();

	public boolean isLogin(String accountName) {
		if (accountName == null || "".equals(accountName.trim())) {
			return false;
		}
		return this.getAccount(accountName) != null;
	}

	public IAccount getAccount(String name) {
		return this.loginAccounts.get(name);
	}

	public IAccount removeAccount(String name) {
		return this.loginAccounts.remove(name);
	}

	public void addAccount(IAccount account) {
		this.loginAccounts.put(account.getAccountName(), account);
	}
}
