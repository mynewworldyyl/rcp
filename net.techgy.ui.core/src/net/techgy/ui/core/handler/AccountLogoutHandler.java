package net.techgy.ui.core.handler;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import net.techgy.ui.core.actions.ActionManager;
import net.techgy.ui.core.network.WSConn;

public class AccountLogoutHandler {

	public static final String LOGOUT="Logout";
	//public static final String LOGIN="Login";
	
	
	@Execute
	public static void execute(Shell shell,ActionManager am){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("shell", shell);
		am.executeAction(LOGOUT, "", params);
	}
	
	@CanExecute
	public boolean canExecute() {
		return WSConn.ins().isLoginClient();
	}
}
