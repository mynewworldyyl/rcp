package net.techgy.ui.core.utils;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MyHandler {
	@Execute
	   public static void execute(Shell shell,@Named("myhandler.common.p1") String p1){
	      MessageDialog.openInformation(shell, "", "Hello World!--> "+"p1="+p1);
	   }
	
	@CanExecute
	  public boolean canExecute() {
	    return true;
	  }

	/*public static void main(String[] args) {
	   Display display = new Display();
	   Shell shell = new Shell(display);
	   shell.open();
	   MyHandler.execute(shell,"1");
	   while( !shell.isDisposed() ) {
	      if( ! display.readAndDispatch() ) {
	         display.sleep();
	      }
	   }
	}*/
}
