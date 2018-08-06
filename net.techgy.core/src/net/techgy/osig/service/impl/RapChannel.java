package net.techgy.osig.service.impl;


public interface RapChannel {

	void onTextMessage(String json);
	
	void onClose(int status);
	
	void onOpen();
}
