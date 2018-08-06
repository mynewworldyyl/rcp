package com.digitnexus.core.osgiservice;

public interface IServiceTrackerConnection<T> {

	void onService(T service);
	
}
