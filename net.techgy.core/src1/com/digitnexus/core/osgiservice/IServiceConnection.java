package com.digitnexus.core.osgiservice;

public interface IServiceConnection<T> {

	void onServiceEnable(T service);
	
	void onServiceDisable(T service);
	
	void onError(Throwable e);

}
