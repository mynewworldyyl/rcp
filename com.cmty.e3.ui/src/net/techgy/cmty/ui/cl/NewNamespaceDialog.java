package net.techgy.cmty.ui.cl;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewNamespaceDialog extends Dialog {

	  private static final int LOGIN_ID = IDialogConstants.CLIENT_ID + 1;
	  
	  private Text nsText;
	  private Text descText;
	  
	  private final String title;
	  private final String message;
	  
	  private String ns;
	  private String desc;
	  
	  public NewNamespaceDialog( Shell parent, String title, String message ) {
	    super( parent );
	    this.title = title;
	    this.message = message;
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
	    Label mesgLabel = new Label( composite, SWT.NONE );
	    GridData messageData = new GridData( SWT.FILL, SWT.CENTER, true, false );
	    messageData.horizontalSpan = 2;
	    mesgLabel.setLayoutData( messageData );
	    Label userLabel = new Label( composite, SWT.NONE );
	    userLabel.setText( "Namespace:" );
	    nsText = new Text( composite, SWT.BORDER );
	    nsText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
	    
	    Label descLabel = new Label( composite, SWT.NONE );
	    descLabel.setText( "Password:" );
	    descText = new Text( composite, SWT.BORDER | SWT.PASSWORD );
	    descText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
	    
	    initilizeDialogArea();
	    return composite;
	  }

	  @Override
	  protected void createButtonsForButtonBar( Composite parent ) {
	    createButton( parent, IDialogConstants.CANCEL_ID, "Cancel", false );
	    createButton( parent, LOGIN_ID, "Confirm", true );
	  }

	  @Override
	  protected void buttonPressed( int buttonId ) {
	    if( buttonId == LOGIN_ID ) {
	      ns = nsText.getText();
	      desc = descText.getText();
	      setReturnCode( OK );
	      close();
	    }
	    super.buttonPressed( buttonId );
	  }

	  private void initilizeDialogArea() {
	    nsText.setFocus();
	  }

	}
