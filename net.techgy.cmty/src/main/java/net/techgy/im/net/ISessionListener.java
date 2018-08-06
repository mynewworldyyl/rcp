package net.techgy.im.net;

public interface ISessionListener {

	
	void sessoionOpened(ISession session);
	/**
	 * not write data to client in this method 
	 * since the connection maybe have been closed
	 * @param session
	 */
	void sessoionClose(ISession session);
}
