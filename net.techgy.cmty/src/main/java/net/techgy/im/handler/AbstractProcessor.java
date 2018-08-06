package net.techgy.im.handler;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import net.techgy.common.models.IMessage;
import net.techgy.im.net.IMessageQueue;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractProcessor implements IProcessor {

	
	private AtomicBoolean running = new AtomicBoolean();

	@Autowired
	private IMessageQueue queue;
	
	private Executor worker = null;
	
	@Autowired
	private MessageDispatcher messageHandler;
	
	
	public AbstractProcessor() {
	}
	
	abstract void send(IMessage msg);
	
	@Override
	public void pushMessage(IMessage msg) {
       this.queue.push(msg);
       if(!this.isRunning()) {
    	   this.worker.execute(this);
       }
	}


	@Override
	public void run() {
		IMessage msg = this.queue.pop();
		if(msg == null) {
			return;
		}
		while(msg != null) {
			msg = messageHandler.handlerMessage(msg);
			if(msg != null) {
				this.send(msg);
			}
			msg = this.queue.pop();
		}
	}

	
	public Executor getWorker() {
		return worker;
	}

	public void setWorker(Executor worker) {
		this.worker = worker;
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return this.running.get();
	}
}
