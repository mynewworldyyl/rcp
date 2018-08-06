package net.techgy.osig.service;

import net.techgy.common.models.IMessage;


public interface IMessageListener {
	boolean onMessage(IMessage msg);
}
