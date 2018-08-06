package com.digitnexus.ui.dialogs;

import net.cmty.ui.core.i18n.I18NUtils;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.utils.Utils;
import com.digitnexus.ui.celleditor.CellEditorHelper;

@SuppressWarnings("serial")
public abstract class CellEditorDialog extends Dialog {

	  private static final int LOGIN_ID = IDialogConstants.CLIENT_ID + 1;

	  private static final int REFRESH = Window.OK + 1;
	  
	  protected FieldDef fd;
	  
	  protected Object value;
	  
	  protected Composite contentContainer;
	  
	  public CellEditorDialog( Shell parent,FieldDef fd,Object value) {
	    super( parent );
	    this.fd = fd;
	    this.value = value;
	  }

	  @Override
	  protected void configureShell( Shell shell ) {
	    super.configureShell( shell );
	    shell.setText( I18NUtils.getInstance().getString(fd.getName()) );
	  }

	  @Override
	  protected Control createDialogArea( Composite parent ) {
	    this.contentContainer = ( Composite )super.createDialogArea( parent );
	    GridLayout layouy = new GridLayout(1,false);
	    layouy.marginHeight=0;
	    layouy.marginWidth=0;
	    layouy.horizontalSpacing=0;
	    layouy.verticalSpacing=0;
	    contentContainer.setLayout(layouy);
	    this.clearContent();
	    initilizeDialogArea();
	    return contentContainer;
	  }

	  @Override
	  protected void createButtonsForButtonBar( Composite parent ) {
	    createButton( parent, Window.CANCEL, I18NUtils.getInstance().getString("Cancel") , false );
	    createButton( parent, Window.OK, I18NUtils.getInstance().getString("OK"), true );
	    if(fd.getDefaultValueProviderCls() != null 
	    		|| fd.getKeyValuesProviderCls() != null 
	    		|| fd.getTreeRootsProviderCls() != null) {
	    	createButton( parent, REFRESH, I18NUtils.getInstance().getString("Refresh"), false );
	    }
	  }

	  @Override
	  protected void buttonPressed( int buttonId ) {
		  if(buttonId == Window.OK) {
			  this.value = this.getValue();
		  }else if (buttonId == REFRESH) {
			  boolean f = CellEditorHelper.getInstance().refreshList(this.getShell(),fd);
			  if(f) {
					this.clearContent();
					this.initilizeDialogArea();
					this.contentContainer.layout(true);
				}
			  return;
		  }
		  super.buttonPressed(buttonId);
		  this.close();
	  }

	protected  Object getValue() {
		return this.value;
	}

	protected abstract void initilizeDialogArea();
	
	public Object getModel() {
		return this.value;
	}
	
	protected boolean isMultil() {
		return Utils.getInstance().isCollection(fd);
	}
	
	protected boolean validate(Object obj) {
		return true;
	}
	
	protected int getShellStyle() {
		int style = super.getShellStyle();
		return style | SWT.MAX | SWT.RESIZE;
	}
	
	protected void clearContent() {
		if(this.contentContainer == null) {
			return;
		}
		Control[]  controls = this.contentContainer.getChildren();
		if(controls != null) {
			for(Control c : controls)  {
				c.dispose();
			}
		}
	}
}
