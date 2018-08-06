package net.techgy.ui.core.content;

public interface ICmtyWindow {

	public static final int TAB = 1;
	
	public static final int STACK = 2;
	
	public boolean isShow();
	
	public void show();
	
	public void hide();

	public int open();

	public void updateTitle(String title);
}
