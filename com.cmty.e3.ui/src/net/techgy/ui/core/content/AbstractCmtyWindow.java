package net.techgy.ui.core.content;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;

public class AbstractCmtyWindow extends Window implements ICmtyWindow {

	private boolean isShow = false;
	
	protected Shell shell;
	
	public AbstractCmtyWindow(Shell parentShell) {
		super(parentShell);
	}
	
	public boolean isShow() {
		return this.isShow;
	}
	
	public void show() {
		if(this.isShow()) {
			return;
		}
		this.isShow = true;
		this.shell.setVisible(true);
	}
	
	public void hide() {
		if(!this.isShow()) {
			return;
		}
		this.isShow = false;
		this.shell.setVisible(false);
	}

	@Override
	public int open() {
		int code = super.open();
		this.show();
		return code;
	}

	@Override
	public boolean close() {
		this.hide();
		return false;
	}
	
	@Override
	protected int getShellStyle() {
		int style = /*SWT.MIN |*/ SWT.MAX | SWT.TITLE | SWT.RESIZE;
		return style;
	}

	@Override
	protected Layout getLayout() {
		FillLayout fl = new FillLayout(SWT.VERTICAL);
		fl.marginHeight=0;
		fl.marginWidth=0;
		fl.spacing=0;
		return fl;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		this.shell = newShell;
		newShell.addShellListener(new ShellAdapter(){
			public void shellClosed(ShellEvent e) {
				e.doit = false;
			}
		});
	}

	@Override
	public void updateTitle(String title) {
		if(title != null) {
			this.shell.setText(title);
		}
	}

}
