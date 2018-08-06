package com.digitnexus.core.dept;

import java.util.ArrayList;
import java.util.List;

import com.digitnexus.base.uidef.TreeNode;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.osgiservice.impl.SpringContext;
import com.digitnexus.core.provider.ITreeNodeProvider;
import com.digitnexus.core.vo.dept.EmployeeVo;

public class EmpKeyValueProvider implements ITreeNodeProvider{

	@Override
	public List<TreeNode> getRoot() {
		EmpManager empManager = SpringContext.getContext().getBean(EmpManager.class);
		EmployeeVo evos = empManager.queryEmpAsDeptTree(UserContext.getCurrentClientId(), null);
		List<TreeNode> l = null;
		if(evos != null) {
			TreeNode root = this.parseEmployeeToTreeNode(evos);
			if(root != null) {
				l = new ArrayList<TreeNode>();
				l.add(root);
			}
		}
		return l;
	}

	private TreeNode parseEmployeeToTreeNode(EmployeeVo evo) {
		if(evo == null) {
			return null;
		}
		TreeNode parent = new TreeNode(evo.getId(),evo.getName(),evo.nodeType);
		for(EmployeeVo e : evo.getSubDeptOrEmp()) {
			TreeNode tn = parseEmployeeToTreeNode(e);
			if(tn != null) {
				parent.getChildren().add(tn);
				//tn.setParent(parent);
			}
		}
		return parent;
	}
	
}
