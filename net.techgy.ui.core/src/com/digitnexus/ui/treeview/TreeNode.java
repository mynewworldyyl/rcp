package com.digitnexus.ui.treeview;

import java.util.ArrayList;
import java.util.List;

import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.ui.model.PropertySourceModel;

public class TreeNode extends PropertySourceModel /* implements IPropertySource*/{

	private TreeNode parent;
	
	private List<TreeNode> children = new ArrayList<TreeNode>();
	
	public TreeNode(Object obj,List<FieldDef> fieldDefs,boolean modifialbe,String validNodeType ) {
		super(obj,fieldDefs,modifialbe,validNodeType);
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
}
