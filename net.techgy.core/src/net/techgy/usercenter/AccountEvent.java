package net.techgy.usercenter;

import net.techgy.im.models.IAccount;

public class AccountEvent {

	private IAccount account;
    
	public AccountEvent(){
		
	}
	public AccountEvent(IAccount account) {
		this.account = account;
	}
	
	public IAccount getAccount() {
		return account;
	}

	public void setAccount(IAccount account) {
		this.account = account;
	}
	
	
}
