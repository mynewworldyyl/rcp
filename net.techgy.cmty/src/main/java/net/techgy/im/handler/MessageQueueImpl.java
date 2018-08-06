package net.techgy.im.handler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.techgy.common.models.IMessage;
import net.techgy.im.net.IMessageQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MessageQueueImpl implements IMessageQueue {

	private Queue<IMessage> normalPriorityQueue = new ConcurrentLinkedQueue<IMessage>();
	private Queue<IMessage> lowPriorityQueue = new ConcurrentLinkedQueue<IMessage>();
	private Queue<IMessage> heightPriorityqueue = new ConcurrentLinkedQueue<IMessage>();
	
	@Override
	public void push(IMessage msg) {
		  switch(msg.getPriority()) {
	       case LOW:
	    	   this.lowPriorityQueue.offer(msg);
	    	   break;
	       case NORMAL:
	    	   this.normalPriorityQueue.offer(msg);
	    	   break;
	       case HEIGHT:
		       this.heightPriorityqueue.offer(msg);
		       break;
	       }  
	}

	@Override
	public IMessage pop() {
		// TODO Auto-generated method stub
		if(!this.heightPriorityqueue.isEmpty()) {
			return this.heightPriorityqueue.poll();
		}else if(!this.normalPriorityQueue.isEmpty()) {
			return this.normalPriorityQueue.poll();
		}else if(!this.lowPriorityQueue.isEmpty()) {
			return this.lowPriorityQueue.poll();
		}
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		int size = this.heightPriorityqueue.size() + this.normalPriorityQueue.size()+
				this.lowPriorityQueue.size();
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.size() < 1;
	}

	@Override
	public boolean isNonEmpty() {
		// TODO Auto-generated method stub
		return !this.isEmpty();
	}

	
}
