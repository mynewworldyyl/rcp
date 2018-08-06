package net.techgy.cmty.ui.account;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.digitnexus.base.uidef.UIConstants;

import net.techgy.cmty.ui.preference.GlobalDataUtils;

public class LoginDialog extends Dialog {

	  private static final int LOGIN_ID = IDialogConstants.CLIENT_ID + 1;

	  private Text userText;
	  private Text passText;
	  private Label mesgLabel;
	  
	  private final String title;
	  private String username;
	  private String password;
	  private boolean isRem = false;
	  
	  private Button remButton;

	  public LoginDialog( Shell parent, String title ) {
	    super( parent );
	    this.title = title;
	  }

	  public String getPassword() {
	    return password;
	  }

	  public void setUsername( String username ) {
	    this.username = username;
	  }

	  public String getUsername() {
	    return username;
	  }

	  @Override
	  protected void configureShell( Shell shell ) {
	    super.configureShell( shell );
	    if( title != null ) {
	      shell.setText( title );
	    }
	  }

	  @Override
	  protected Control createDialogArea( Composite parent ) {
	    Composite composite = ( Composite )super.createDialogArea( parent );
	    composite.setLayout( new GridLayout( 2, false ) );
	    mesgLabel = new Label( composite, SWT.NONE );
	    GridData messageData = new GridData( SWT.FILL, SWT.CENTER, true, false );
	    messageData.horizontalSpan = 2;
	    mesgLabel.setLayoutData( messageData );
	    Label userLabel = new Label( composite, SWT.NONE );
	    userLabel.setText( "Username:" );
	    userText = new Text( composite, SWT.BORDER );
	    userText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
	    
	    Label passLabel = new Label( composite, SWT.NONE );
	    passLabel.setText( "Password:" );
	    passText = new Text( composite, SWT.BORDER | SWT.PASSWORD );
	    passText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
	    
	    remButton = new Button(composite, SWT.CHECK );
	    remButton.setText( "Remenber" );
	    initilizeDialogArea();
	    return composite;
	  }

	  @Override
	  protected void createButtonsForButtonBar( Composite parent ) {
	    createButton( parent, IDialogConstants.CANCEL_ID, "Cancel", false );
	    createButton( parent, LOGIN_ID, "Login", true );
	  }

	  @Override
	  protected void buttonPressed( int buttonId ) {
	    if( buttonId == LOGIN_ID ) {
	      username = userText.getText();
	      password = passText.getText();
	      isRem = this.remButton.getSelection();
	      setReturnCode( OK );
	      close();
	    } else {
	      password = null;
	    }
	    super.buttonPressed( buttonId );
	  }

	  private void initilizeDialogArea() {
	    //SettingStore ss = RWT.getSettingStore();
	    
	    String isRemStr = GlobalDataUtils.ins().getValue(UIConstants.IS_REMENBER_UUSER_PW);
	    //String isRemStr = ss.getAttribute(UIConstants.IS_REMENBER_UUSER_PW);
	    boolean isRem = Boolean.valueOf(isRemStr);
	    remButton.setSelection(isRem);	    
	    if(isRem) {
	    	passText.setText(GlobalDataUtils.ins().getValue(UIConstants.LAST_PASSWD));
	    	userText.setText(GlobalDataUtils.ins().getValue(UIConstants.LAST_UUSER));
	    } else {
	    	passText.setText("888888");
	    	userText.setText("Admin_ZJ");
	    }
	    userText.setFocus();
	  }

	public boolean isRem() {
		return isRem;
	}

	}
