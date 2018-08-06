package net.techgy.im.handler;

import java.util.concurrent.Executor;

import net.techgy.common.models.IMessage;

public interface IProcessor extends Runnable {

	boolean isRunning();
	
	void pushMessage(IMessage msg);
	
	void setWorker(Executor worker);
}
