package net.techgy.im.handler;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import net.techgy.common.models.IMessage;
import net.techgy.common.models.MessageImpl;
import net.techgy.spring.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class QueueManagerImpl implements IQueueManager {
	
	private int processorNum = Runtime.getRuntime().availableProcessors();
	
	private IProcessor[] processors = null;
	
	private Executor worker = Executors.newFixedThreadPool(processorNum);
	
	private AtomicInteger loop = new AtomicInteger();
	
	@Autowired
	@Qualifier("messageHanderDispatcher")
	private MessageDispatcher messageHandler;
	
	public void init() {
		processors = new IProcessor[processorNum];
		for(int num = 0; num < processorNum; num++) {
			IProcessor p = Context.getBean(IProcessor.class);
			p.setWorker(this.worker);
			processors[num] = p;
		}
	}

	private IProcessor nextPocessor(){
		int loopIndex = this.loop.getAndIncrement();
		return this.processors[loopIndex%this.processorNum];
	}
	
	@Override
	public void pushMessage(IMessage msg) {
		this.nextPocessor().pushMessage(msg);
	}

	@Override
	public void pushMessage(List<MessageImpl> msgs) {
		for(IMessage msg : msgs) {
			this.pushMessage(msg);
		}
	}
	
	
}
