package com.cmty.e3.ui;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.util.PrefUtil;

import net.techgy.ui.core.utils.LifeCycleManager;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	@SuppressWarnings("restriction")
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		
		configurer.setInitialSize(new Point(800, 600));
		configurer.setTitle("CMTY");
		this.init();
		/*configurer.setInitialSize(new Point(
				Display.getCurrent().getBounds().width, Display.getCurrent()
						.getBounds().height));*/
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(false);

		//configurer.setShowFastViewBars(false);

		configurer.setShowPerspectiveBar(false);

		configurer.setShowMenuBar(true);

		configurer.setShowProgressIndicator(true);
		
		configurer.setShellStyle(configurer.getShellStyle() | SWT.MAX);
		
		IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();
		//apiStore.setValue(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR, IWorkbenchPreferenceConstants.TOP_RIGHT);
		//apiStore.setValue(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS, false);
		 
	}

	@Override
	public void postWindowOpen() {
		super.postWindowOpen();
	}
	
	private void init() {
		//IEclipseContext appContext=
		 IEclipseContext ecContext = this.getWindowConfigurer().getWindow().getWorkbench().getService(IEclipseContext.class);
		 LifeCycleManager lifeManager = ContextInjectionFactory.make(LifeCycleManager.class, ecContext);
		 lifeManager.postContextCreate(ecContext);
		
	}
}
