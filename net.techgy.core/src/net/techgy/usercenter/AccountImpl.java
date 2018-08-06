package net.techgy.usercenter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.techgy.im.models.IAccount;


@Entity
@Table(name="t_account_cmty")
public class AccountImpl implements IAccount{

	@Id
	@GeneratedValue
	private long id;
	
	@Column(unique=true, nullable=false)
	private String accountName;
	private String password;
	
	
	@Override
	public String getAccountName() {
		// TODO Auto-generated method stub
		return this.accountName;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
