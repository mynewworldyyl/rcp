package net.techgy.common.models;

import net.techgy.im.handler.MessagePriority;

public interface IMessage {

	void addHeader(String key, String value);
	String getHeaderByName(String account);	
	MsgHeader getHeader(String name);
	
	void addBody(int key, Object value);
	
	MsgBody getSingleBody() ;
	
	MsgBody getBody(int key);
	
	 MessagePriority getPriority();
	 String getUserId();
	 String PRIORITY = "priority";
	 MessageType getType();
	 MT getMt();
	 boolean isClientMessage();
	 
	 
	 public static class MT{
		 private int resendCount = 0;

		public int getResendCount() {
			return resendCount;
		}

		public void setResendCount(int resendCount) {
			this.resendCount = resendCount;
		}
		 
	 }
}
