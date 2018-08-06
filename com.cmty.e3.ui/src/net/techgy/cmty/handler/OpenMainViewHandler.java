package net.techgy.cmty.handler;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.widgets.Shell;

public class OpenMainViewHandler {

	  @Execute
	  public static void execute(Shell shell,IWorkbench workbench,EModelService mservice){
		  //WorkbenchWindow workbench = (WorkbenchWindow)CmtyWindow.getCmtyWindow().getContext().get(WorkbenchWindow.WORKBENCH_WINDOW_ID);
		  //workbench.getViewSite().showView(MainMenuViewProvier.VIEW_ID);
		  //workbench.getApplication().
		  //mservice.
	  }
	  @CanExecute
	  public boolean canExecute() {
	    return true;
	  }
	
}
