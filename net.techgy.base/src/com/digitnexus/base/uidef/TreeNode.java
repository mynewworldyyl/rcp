package com.digitnexus.base.uidef;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于权利型字段输入框
 * @author ylye
 *
 */
public class TreeNode{

	private TreeNode parent;
	
	private List<TreeNode> children = new ArrayList<TreeNode>();

    private String id ;
    
    private String label;
    
    private String nodeType;
    
	public TreeNode(String id,String label,String nodeType){
			this.id = id;
			this.label= label;
			this.nodeType = nodeType;
	}
	
	public TreeNode(String id,String label){
	       this(id, label, null);
    }
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TreeNode) {
			TreeNode tn = (TreeNode)obj;
			return this.id.equals(tn.id);
		}else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "ID: " +  this.id + ", Label: " + this.label  ;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	
}
