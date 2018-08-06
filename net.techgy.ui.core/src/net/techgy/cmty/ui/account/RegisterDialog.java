package net.techgy.cmty.ui.account;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import net.cmty.ui.core.i18n.I18NUtils;
import net.techgy.ui.core.utils.ImageUtils;

public class RegisterDialog extends Dialog {

	  private static final int LOGIN_ID = IDialogConstants.CLIENT_ID + 1;

	  private Text userText;
	  private Text passText;
	  private Text cmfPassText;
	  private Label mesgLabel;
	  private final String title;
	  private String username;
	  private String password;
	  private String conformPassword;
	  
	  //private FileUpload file;
	  
	  private Label photoImage;
	  
	  public RegisterDialog( Shell parent, String title ) {
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

	  public String getConformPassword() {
		return conformPassword;
	}

	public void setConformPassword(String conformPassword) {
		this.conformPassword = conformPassword;
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
	    
	    Label cmfPassLabel = new Label( composite, SWT.NONE );
	    cmfPassLabel.setText( "Comfirm:" );
	    cmfPassText = new Text( composite, SWT.BORDER | SWT.PASSWORD );
	    cmfPassText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
	    
	   /* file = new FileUpload( composite, SWT.NONE );
	    file.setText( "Photo" );
	    file.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );*/
	    
	    Image photo = ImageUtils.getInstance().getImage("/img/ti03.png", 32, 32);
	    photoImage = new Label( composite, SWT.NONE );
	    photoImage.setBackgroundImage(photo);
	    GridData gd = new GridData(32,32) ;
	    photoImage.setLayoutData(gd);
	    
	    initilizeDialogArea();
	    return composite;
	  }

	  protected boolean checkPhoto() {
		 
		    String fn = null;//file.getFileName();
			if(fn == null) {
				return true;
			}
			
			boolean f = fn.endsWith(".png");
			if(!f) {
				mesgLabel.setText(
						I18NUtils.getInstance().getString("PhotoImageNotAllow",fn));
			}
			
			if(!f) {
				photoImage.setBackgroundImage(null);
			}
			return f;
	}

	@Override
	  protected void createButtonsForButtonBar( Composite parent ) {
	    createButton( parent, IDialogConstants.CANCEL_ID, "Cancel", false );
	    createButton( parent, IDialogConstants.OK_ID, "Comfirm", true );
	  }

	  @Override
	  protected void buttonPressed( int buttonId ) {
	    if( buttonId == IDialogConstants.OK_ID ) {
	      username = userText.getText();
	      password = passText.getText();
	      this.conformPassword=this.cmfPassText.getText();
	      if(password==null || password.equals("")) {
	    	  mesgLabel.setText("Password cannot be null");
	    	  return;
	      }else if(conformPassword==null || conformPassword.equals("")){
	    	  mesgLabel.setText("comfirm Password cannot be null");
	    	  return;
	      }else if(!conformPassword.equals(this.password)){
	    	  mesgLabel.setText("comfirm Password not equals password!");
	    	  return;
	      }
	      
	      if(!this.checkPhoto()) {
	    	  return;
	      }
	      
	      setReturnCode( OK );
	     
	    } else {
	      password = null;
	    }
	    super.buttonPressed( buttonId );
	  }

	  private void initilizeDialogArea() {
	    if( username != null ) {
	      userText.setText( username );
	    }
	    userText.setFocus();
	  }
}
