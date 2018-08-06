package net.techgy.cmty.ui.dialog;

import java.util.Map;

import net.techgy.cmty.ui.i18n.I18NUtils;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.digitnexus.core.vo.dept.DepartmentVo;

public class ClientDialog extends Dialog {

	  private static final int LOGIN_ID = IDialogConstants.CLIENT_ID + 1;

	  private DepartmentVo dept;
	  
	  Map<String,String> clientTypes;
	  
	  private Combo typeList;
	  
	  private String name ="";
	  
	  private String desc = "";
	  
      private String userName ="";
	  
	  private String password = "";
	  
	  private String clientType = "";
	  
	  private Text nameText;
	  private Text descText;
	  
	  private Text deptText;
	  
	  private Text adminText;
	  private Text pwText;
	  private Text cpwText;
	  
	  private CLabel msg = null;
	  
	  public ClientDialog( Shell parent, DepartmentVo dept, Map<String,String> clientTypes) {
	    super( parent );
	    name = dept.getName();
	    desc = dept.getDesc();
	    this.dept = dept;
	    this.clientTypes = clientTypes;
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

		CLabel tlabel = new CLabel(composite, SWT.CENTER);
		GridData gd = new GridData(SWT.LEFT,SWT.CENTER,false,false);
		tlabel.setLayoutData(gd);
		tlabel.setText(I18NUtils.getInstance().getString("SystemType"));
		typeList = new Combo(composite,SWT.SINGLE | SWT.DROP_DOWN | SWT.READ_ONLY);
		gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
		typeList.setLayoutData(gd);
		
		CLabel nlabel = new CLabel(composite, SWT.CENTER);
		gd = new GridData(SWT.LEFT,SWT.CENTER,false,false);
		nlabel.setLayoutData(gd);
		nlabel.setText(I18NUtils.getInstance().getString("Name"));
		nameText = new Text(composite, SWT.BORDER);
		gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
		nameText.setLayoutData(gd);
		nameText.setData("name");		
		
		
		CLabel descLabel = new CLabel(composite, SWT.CENTER);
	    gd = new GridData(SWT.LEFT,SWT.CENTER,false,false);
	    descLabel.setLayoutData(gd);
	    descLabel.setText(I18NUtils.getInstance().getString("Desc"));
		descText = new Text(composite, SWT.BORDER);
		gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
		descText.setLayoutData(gd);
		descText.setData("desc");		
		
		
		CLabel deptLabel = new CLabel(composite, SWT.CENTER);
	    gd = new GridData(SWT.LEFT,SWT.CENTER,false,false);
	    deptLabel.setLayoutData(gd);
	    deptLabel.setText(I18NUtils.getInstance().getString("Dept"));
		deptText = new Text(composite, SWT.BORDER);
		gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
		deptText.setLayoutData(gd);
		deptText.setData("Dept");
		deptText.setEnabled(false);
		
		
		CLabel adminLabel = new CLabel(composite, SWT.CENTER);
	    gd = new GridData(SWT.LEFT,SWT.CENTER,false,false);
	    adminLabel.setLayoutData(gd);
	    adminLabel.setText(I18NUtils.getInstance().getString("userName"));
		adminText = new Text(composite, SWT.BORDER);
		gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
		adminText.setLayoutData(gd);
		adminText.setData("userName");

		
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
			  String svalue = this.typeList.getItems()[this.typeList.getSelectionIndex()];
			  String key = null;
			  for(Map.Entry<String, String> e : this.clientTypes.entrySet()) {
				  if(e.getValue().equals(svalue)) {
					  key = e.getKey();
				  }
			  }
			  if(key == null || "".equals(key.trim())) {
				  this.msg.setText(I18NUtils.getInstance().getString("noClientTypeSelected"));
				  return;
			  }
			  this.clientType = key;
			  this.name = this.nameText.getText();
			  this.desc = this.deptText.getText();
			  this.userName = this.adminText.getText();
			  this.password = this.pwText.getText();
			  
		  }
		  super.buttonPressed(buttonId);
		  this.close();
	  }

	  private void initilizeDialogArea() {
		  this.nameText.setText(dept.getName());
		  this.descText.setText(this.dept.getDesc());
		  this.deptText.setText(dept.getName());
		  String[] values = new String[this.clientTypes.size()];
		  values = this.clientTypes.values().toArray(values);
		  this.typeList.setItems(values);
		  if(values.length == 1) {
			  this.typeList.select(0);
		  }
	  }

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getClientType() {
		return clientType;
	}

}
