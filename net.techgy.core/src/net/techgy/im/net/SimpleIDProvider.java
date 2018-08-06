package net.techgy.im.net;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Qualifier("simpleIDProvider")
public class SimpleIDProvider implements IIDProvider {

	private AtomicLong longId = new AtomicLong(1);
	private AtomicInteger intId = new AtomicInteger(1);
	private AtomicInteger shortId = new AtomicInteger(1);
	private AtomicInteger byteId = new AtomicInteger(1);
	private AtomicLong stringId = new AtomicLong(1);
	
	@Override
	public long getLong() {
		// TODO Auto-generated method stub
		return this.longId.getAndIncrement();
	}

	@Override
	public int getInt() {
		// TODO Auto-generated method stub
		return intId.getAndIncrement();
	}

	@Override
	public byte getByte() {
		// TODO Auto-generated method stub
		return (byte)this.byteId.getAndIncrement();
	}

	@Override
	public short getShort() {
		return (short)this.shortId.getAndIncrement();
	}

	@Override
	public String getString() {
		// TODO Auto-generated method stub
		return "" + this.stringId.getAndIncrement();
	}

}
