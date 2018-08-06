package com.digitnexus.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.TreeNode;

public class TreeSelectDialog extends Dialog {

	  private static final int LOGIN_ID = IDialogConstants.CLIENT_ID + 1;
	  
	  private final String title;
	  private FieldDef def= null;

	  private String selectValue[] = null;
	  
	  public TreeSelectDialog( Shell parent, String title, FieldDef def,String value) {
	    super( parent );
	    this.title = title;
	    this.def = def;
	    if(value != null) {
	    	this.selectValue = value.split(",");
	    }
	  }

	  @Override
	  protected void configureShell( Shell shell ) {
	    super.configureShell( shell );
	    shell.setSize(450, 600); 
	    shell.setBounds(200,100, 450, 600);
	    if( title != null ) {
	      shell.setText( title );
	    }
	  }

	  @Override
	  protected Control createDialogArea( Composite parent ) {
	    Composite composite = ( Composite )super.createDialogArea( parent );
	    composite.setLayout( new FillLayout(SWT.VERTICAL) );
	    createTree(composite);
	    initilizeDialogArea();
	    return composite;
	  }

	  private void createTree(Composite composite) {
		  if(this.def.getTreeRoots() == null) {
			  return;
		  }
		  Tree tree = new Tree(composite, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.VIRTUAL | SWT.MULTI);
		  //tree.setLayout( new FillLayout(SWT.VERTICAL) );
		  tree.setBounds(0, 0, 189, 252); 
		  for(TreeNode tn: this.def.getTreeRoots()) {
			  final TreeItem treeItem = new TreeItem(tree, SWT.NONE);
			  //treeItem.setText(tn.getLabel());
			  treeItem.setExpanded(true);
			  setSelect(tn,treeItem);
			  createTreeWithNode(treeItem,tn);
		  }
		  //tree.setExpanded(true);		
	}

	private void setSelect(TreeNode tn, TreeItem treeItem) {
		/*if(this.selectValue == null || this.selectValue.length <1) {
			return;
		}
		String tnId = tn.getId();
		if(tnId == null) {
			return;
		}
		for(String id: this.selectValue) {
			if(tnId.equals(id)) {
				treeItem.setChecked(true);
				break;
			}
		}*/
	}

	private void createTreeWithNode(TreeItem treeItem, TreeNode treeNode) {
		if(treeNode == null || treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
			return;
		}
		
		for(TreeNode tn: treeNode.getChildren()) {
			  final TreeItem item = new TreeItem(treeItem, SWT.NONE);
			  treeItem.setExpanded(true);	
			  //item.setText(tn.getLabel());
			  setSelect(tn,treeItem);
			  createTreeWithNode(item,tn);
		  }
	}

	@Override
	  protected void createButtonsForButtonBar( Composite parent ) {
	    createButton( parent, IDialogConstants.CANCEL_ID, "Cancel", false );
	    createButton( parent, LOGIN_ID, "Comfirm", true );
	  }

	  @Override
	  protected void buttonPressed( int buttonId ) {
	    if( buttonId == LOGIN_ID ) {
	      setReturnCode( OK );
	      close();
	    } else {
	    }
	    super.buttonPressed( buttonId );
	  }

	  private void initilizeDialogArea() {
	   
	  }

	  public String getValue() {
		  return "";
	  }
}
