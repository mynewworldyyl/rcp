package net.techgy.usercenter;

public interface IAccountListener {

	void accountLogin(AccountEvent event);
	
	void accountLogout(AccountEvent event);
	
    void accountRegister(AccountEvent event);
	
	void accountUnregister(AccountEvent event);
}
