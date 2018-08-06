package net.techgy.cmty.ui.actions;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import net.cmty.ui.core.workbench.WorkbenchWindow;
import net.techgy.cmty.ui.views.MainMenuViewProvier;
import net.techgy.ui.core.content.CmtyWindow;

public class OpenMainViewHandler {

	  @Execute
	  public static void execute(Shell shell/*@Named("myhandler.common.p1") String p1*/){
		  WorkbenchWindow workbench = (WorkbenchWindow)CmtyWindow.getCmtyWindow().getContext().get(WorkbenchWindow.WORKBENCH_WINDOW_ID);
		  workbench.getViewSite().showView(MainMenuViewProvier.VIEW_ID);
	  }
	
	  @CanExecute
	  public boolean canExecute() {
	    return true;
	  }
	
}
