package com.digitnexus.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TouchEvent;
import org.eclipse.swt.events.TouchListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.TreeNode;
import com.digitnexus.base.utils.Utils;

@SuppressWarnings("serial")
public class TreeCellEditorDialog extends CellEditorDialog {
	
	private static final String DATA_NODE_KEY = "node";
	
	
	private Tree tree =  null;
	
	private SelectionListener sl = new SelectionListener(){
		@Override
		public void widgetSelected(SelectionEvent e) {
			//doCheckSelection(e);
		}
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			//doCheckSelection(e);
		}
	};
	
	private TouchListener tl = new TouchListener(){
		@Override
		public void touch(TouchEvent e) {
			Object o = e.getSource();
		}
	};
	
	private Listener s = new Listener(){
		@Override
		public void handleEvent(Event event) {
			if (event.detail == SWT.CHECK) {  
                TreeItem item = (TreeItem) event.item; 
                if(item.getChecked()) {
                      event.doit = doCheckSelection(item);
                      if(event.doit && !isMultil()) {
                    	  doSingleCheck(tree.getItems(),item);
                      }
                }
            }  
		}
	};
	
	private void doSingleCheck(TreeItem[] tis,TreeItem si) {
		if(tis == null || tis.length < 1) {
			return;
		}
		for(TreeItem ti : tis) {
			if(ti == si) {
				continue;
			}
			if(ti.getChecked()) {
				ti.setChecked(false);
			}
			doSingleCheck(ti.getItems(),si);
		}
	}
	
	private boolean doCheckSelection(TreeItem ti) {
		TreeNode tn = (TreeNode) ti.getData(DATA_NODE_KEY);
		boolean f = TreeCellEditorDialog.this.validate(tn);
		if(f) {
			f = TreeCellEditorDialog.super.validate(tn);
		} 
		if(!f) {
			ti.setChecked(false);
		}
		return f;
	}
	
	public TreeCellEditorDialog( Shell parent,FieldDef fd,Object value){
		super(parent,fd,value);
	}

	@Override
	protected Object getValue() {
		List<String> l = new ArrayList<String>();
		getValues(tree.getItems(),l);
		if(l.size() < 1) {
			return null;
		}
		String[] values = new String[l.size()];
		return l.toArray(values);
	}
	
	private void getValues(TreeItem[] tis,List<String> l) {
		if(tis == null || tis.length < 1) {
			return;
		}
		for(TreeItem ti : tis) {
			if(ti.getChecked()) {
				l.add(((TreeNode)ti.getData(DATA_NODE_KEY)).getId());
			}
			getValues(ti.getItems(),l);
		}
	}

	@Override
	protected void initilizeDialogArea() {
		  if(this.fd.getTreeRoots() == null) {
			  return;
		  }
		  int style = SWT.CHECK | SWT.V_SCROLL;
		  if(this.isMultil()) {
			  style = style | SWT.MULTI;
		  }else {
			  style = style | SWT.SINGLE;
		  }
		  tree = new Tree(this.contentContainer, style);
		  GridData gd = new GridData(SWT.FILL,SWT.FILL,true,true,1,1);
		  tree.setLayoutData(gd);
		  gd.minimumHeight=300;
		  
		  FillLayout layout = new FillLayout(SWT.VERTICAL);
		  tree.setLayout(layout);
		  tree.setBounds(0, 0, 189, 252); 
		  String[] selectValueIds = Utils.getInstance().getCollectionValues(this.getModel());
		  for(TreeNode tn: this.fd.getTreeRoots()) {
			  final TreeItem treeItem = new TreeItem(tree, SWT.NONE);
			  this.setTreeItemProp(treeItem, tn,selectValueIds);
			  //treeItem.setExpanded(true);
			  createTreeWithNode(treeItem,tn,selectValueIds);
		  }
		  //tree.addSelectionListener(sl);
		  //tree.addTouchListener(tl);
		  tree.addListener(SWT.Selection, s);
		  //tree.;		
	}
	
	private void createTreeWithNode(TreeItem treeItem, TreeNode treeNode,String[] selectValueIds) {
		if(treeNode == null || treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
			return;
		}
		
		for(TreeNode tn: treeNode.getChildren()) {
			  final TreeItem item = new TreeItem(treeItem, SWT.NONE);
			  //treeItem.setExpanded(true);	
			  this.setTreeItemProp(item, tn,selectValueIds);
			  createTreeWithNode(item,tn,selectValueIds);
		  }
	}
	
	private void setTreeItemProp(TreeItem ti, TreeNode node,String[] selectValueIds) {
		 ti.setText(node.getLabel());
		 ti.setData(DATA_NODE_KEY, node);
		 if(selectValueIds != null) {
			 for(String id: selectValueIds) {
				 if(id.equals(node.getId())) {
					 ti.setChecked(true);
					 break;
				 }
			 }
		 }
		 
		 //setSelect(node,ti);
	}

	@Override
	protected boolean validate(Object obj) {
		TreeNode tn = (TreeNode) obj;
		if(this.fd.getValidatedNodeType() == null 
				|| this.fd.getValidatedNodeType().equals(Void.class.getName())) {
			return true;
		}
		boolean f = this.fd.getValidatedNodeType().equals(tn.getNodeType());
		if(f) {
			return super.validate(obj);
		}else {
			return false;
		}
		
	}
}
