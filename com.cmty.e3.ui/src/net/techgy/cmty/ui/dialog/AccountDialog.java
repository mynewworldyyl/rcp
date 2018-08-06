package net.techgy.cmty.ui.dialog;

import java.util.List;

import net.techgy.cmty.ui.i18n.I18NUtils;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.digitnexus.core.vo.dept.AccountVo;

public class AccountDialog extends Dialog {

	  private static final int LOGIN_ID = IDialogConstants.CLIENT_ID + 1;

	  private AccountVo accountVo;
	  
      private String accountName;
	  
	  private String password;
	  
	  private String employeeId;
	  
	  private String statu;
	  
	  private List<String> relatedClients;
	  
	  private Text nameText;
	  private Text pwText;
	  private Text cpwText;
	  private Button empButton;
	  private Combo clientList;
	  
	  private CLabel msg = null;
	  
	  public AccountDialog( Shell parent, AccountVo avo) {
	    super( parent );
	    accountVo = avo;
	  }

	  @Override
	  protected void configureShell( Shell shell ) {
	    super.configureShell( shell );
	    shell.setText( I18NUtils.getInstance().getString("SetAsSubSystem") );
	  }

	  @Override
	  protected Control createDialogArea( Composite parent ) {
	    Composite composite = ( Composite )super.createDialogArea( parent );
	    
	    GridLayout l = new GridLayout(2,false);
		l.marginTop=1;
		l.marginBottom=1;
		composite.setLayout(l);

		CLabel nlabel = new CLabel(composite, SWT.CENTER);
		GridData gd = new GridData(SWT.LEFT,SWT.CENTER,false,false);
		nlabel.setLayoutData(gd);
		nlabel.setText(I18NUtils.getInstance().getString("AccountName"));
		nameText = new Text(composite, SWT.BORDER);
		gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
		nameText.setLayoutData(gd);
		
		CLabel pwLabel = new CLabel(composite, SWT.CENTER);
	    gd = new GridData(SWT.LEFT,SWT.CENTER,false,false);
	    pwLabel.setLayoutData(gd);
	    pwLabel.setText(I18NUtils.getInstance().getString("pw"));
		pwText = new Text(composite, SWT.BORDER|SWT.PASSWORD);
		gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
		pwText.setLayoutData(gd);
		pwText.setData("pw");
		
		CLabel cpwLabel = new CLabel(composite, SWT.CENTER);
	    gd = new GridData(SWT.LEFT,SWT.CENTER,false,false);
	    cpwLabel.setLayoutData(gd);
	    cpwLabel.setText(I18NUtils.getInstance().getString("confirmPW"));
	    cpwText = new Text(composite, SWT.BORDER|SWT.PASSWORD);
		gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
		cpwText.setLayoutData(gd);
		cpwText.setData("confirmPW");
		
		CLabel empLabel = new CLabel(composite, SWT.CENTER);
	    gd = new GridData(SWT.LEFT,SWT.CENTER,false,false);
	    empLabel.setLayoutData(gd);
	    empLabel.setText(I18NUtils.getInstance().getString("Emp"));
	    empButton = new Button(composite,SWT.TOGGLE);
		gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
		empButton.setLayoutData(gd);
		empButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

			}
		});
		
		CLabel clientlabel = new CLabel(composite, SWT.CENTER);
		gd = new GridData(SWT.LEFT,SWT.CENTER,false,false);
		clientlabel.setLayoutData(gd);
		clientlabel.setText(I18NUtils.getInstance().getString("SystemType"));
		clientList = new Combo(composite,SWT.SINGLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
		clientList.setLayoutData(gd);
		
		this.msg = new CLabel(composite, SWT.CENTER);
		gd = new GridData(SWT.FILL,SWT.CENTER,true,true,2,1);
		this.msg.setLayoutData(gd);
		this.msg.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		
	    initilizeDialogArea();
	    return composite;
	  }
	  
	  @Override
	  protected void createButtonsForButtonBar( Composite parent ) {
	    createButton( parent, IDialogConstants.CANCEL_ID, I18NUtils.getInstance().getString("Cancel") , false );
	    createButton( parent, LOGIN_ID, I18NUtils.getInstance().getString("Confirm"), true );
	  }

	  @Override
	  protected void buttonPressed( int buttonId ) {
		  if(buttonId != Window.CANCEL) {
			  if(!this.pwText.getText().equals(this.cpwText.getText())) {
				  this.msg.setText(I18NUtils.getInstance().getString("pwNotEqual"));
				  return;
			  }
			  this.password = this.pwText.getText();
			  
		  }
		  super.buttonPressed(buttonId);
		  this.close();
	  }

	  private void initilizeDialogArea() {
	  }

	public String getPassword() {
		return password;
	}

}
