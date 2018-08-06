package net.techgy.cmty.ui.views;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class MainMenuViewPart {

	public static final String ID=MainMenuViewPart.class.getName();
	
	private MainMenuView view = null;
	
	@PostConstruct
	protected void createContents(Composite parent,IEclipseContext context) {
		//MBasicFactory.INSTANCE.
		//ContextInjectionFactory.make(MainMenuView.class, context);
		view = new MainMenuView(parent,SWT.NONE,context);
	}
}
