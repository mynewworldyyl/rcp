package net.techgy.cmty.ui.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.digitnexus.base.uidef.ActionDef;

import net.cmty.ui.core.i18n.I18NUtils;
import net.techgy.ui.core.RWT;
import net.techgy.ui.core.actions.ActionManager;
import net.techgy.ui.core.actions.IAction;
import net.techgy.ui.core.statubar.IStatuControlProvider;

public class AccountStatuControlProvider implements IStatuControlProvider{

	
	@Inject
	private ActionManager am;
	
	@Override
	public String getId() {
		return AccountStatuControlProvider.class.getName();
	}

	@Override
	public String getTargetId() {
		return IStatuControlProvider.STATU_BAR_ID;
	}
	
    /**
     * FormLayout for the statu bar
     */
	@Override
	public List<Control> createControl(Composite statuBar) {
		List<Control> controls = new ArrayList<Control>();
		
		Link register = new Link(statuBar,SWT.NONE);
		register.setData(RWT.MARKUP_ENABLED,true);
		register.setText("<a>"+I18NUtils.getInstance().getString("Register")+"</a>");
		register.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		    	  doAction(statuBar.getShell(),null,"Register");
		      }
		    });
		FormData formData = new FormData();
		formData.right = new FormAttachment(100,100,-5);
		formData.bottom = new FormAttachment(100,100,-5);
		register.setLayoutData(formData);
		controls.add(register);
		
		final Link logout = new Link(statuBar,SWT.NONE);
		final Link login = new Link(statuBar,SWT.NONE);
		Label loginAccount = new Label(statuBar,SWT.NONE);
		
		logout.setData(RWT.MARKUP_ENABLED,true);
		logout.setVisible(false);
		logout.setText("<a>"+I18NUtils.getInstance().getString("Logout")+"</a>");
		logout.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		    	  if(doAction(statuBar.getShell(),loginAccount,"Logout")) {
		    		  login.setVisible(true);;
                	  logout.setVisible(false);
		    	  }
		      }
		 });
		formData = new FormData();
		formData.right = new FormAttachment(register,-8,SWT.LEFT);
		formData.bottom = new FormAttachment(100,100,-5);
		logout.setLayoutData(formData);
		controls.add(logout);
		
		
		login.setData(RWT.MARKUP_ENABLED,true);
		login.setText("<a>"+I18NUtils.getInstance().getString("Login")+"</a>");
		login.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
                  if(doAction(statuBar.getShell(),loginAccount,"Login")) {
                	  login.setVisible(false);;
                	  logout.setVisible(true);
		    	  }
		      }
		 });
		formData = new FormData();
		formData.right = new FormAttachment(register,-8,SWT.LEFT);
		formData.bottom = new FormAttachment(100,100,-5);
		login.setLayoutData(formData);
		controls.add(login);
		
		loginAccount.setText(I18NUtils.getInstance().getString("NoLogin"));
		formData = new FormData(130,SWT.DEFAULT);
		formData.right = new FormAttachment(login,-8,SWT.LEFT);
		formData.bottom = new FormAttachment(100,100,-5);
		loginAccount.setLayoutData(formData);
		controls.add(loginAccount);
		
		return controls;
	}

	protected boolean doAction(Shell shell,Label loginAccount,String id) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(shell != null) {
			params.put("shell", shell);
		}
		
		if(loginAccount != null) {
			params.put("accountLabel", loginAccount);
		}
		
		int retCode = am.executeAction(new ActionDef(id), params);
		return retCode == IAction.OK;
	}
	
	public void active() {
		System.out.println("Hello");
	}
}
