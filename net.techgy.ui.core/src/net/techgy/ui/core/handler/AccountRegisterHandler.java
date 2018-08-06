package net.techgy.ui.core.handler;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import net.techgy.ui.core.actions.ActionManager;
import net.techgy.ui.core.network.WSConn;

public class AccountRegisterHandler {

	public static final String LOGIN="Login";
	public static final String LOGOUT="Logout";
	//public static final String LOGIN="Login";
	
	
	@Execute
	public static void execute(Shell shell,@Named("net.techgy.ui.account.optype") String opType,ActionManager am){
		if(opType == null || "".equals(opType)) {
			return;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("shell", shell);
		am.executeAction(opType, "", params);
	}
	
	@CanExecute
	public boolean canExecute(@Named("net.techgy.ui.account.optype") String opType) {
		if(LOGIN.equals(opType)) {
			return !WSConn.ins().isLoginClient();
		}else if(LOGOUT.equals(opType)) {
			return WSConn.ins().isLoginClient();
		}
	    return false;
	}
}
