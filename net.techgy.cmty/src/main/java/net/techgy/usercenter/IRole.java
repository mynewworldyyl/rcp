package net.techgy.usercenter;

import net.techgy.im.models.IAccount;

public interface IRole {

	enum RoleType{
		IM
	}
	long getId();
	String getName();
	RoleType getType();
	//this.role belong to which account
	IAccount getAccount();
}
