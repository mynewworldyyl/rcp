package net.techgy.ui.core.handler;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.e4.core.commands.ExpressionContext;
import org.eclipse.swt.widgets.Shell;

import net.techgy.ui.core.actions.ActionManager;

public class E3AccountLoginHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Map<String,Object> params = new HashMap<String,Object>();
		ExpressionContext cxt =(ExpressionContext) event.getApplicationContext();
		params.put("shell", cxt.eclipseContext.get(Shell.class));
		ActionManager am = cxt.eclipseContext.get(ActionManager.class);
		am.executeAction(AccountLoginHandler.LOGIN, "", params);
		return null;
	}

}
