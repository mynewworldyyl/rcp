package net.techgy.im.handler;

public enum MessagePriority {

	LOW(0),
	NORMAL(1),
	HEIGHT(2);
	
	private int priority;

     MessagePriority(int priority) {
		this.priority = priority;
	}
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}
