package com.digitnexus.core.permisson;

import java.util.ArrayList;
import java.util.List;

import com.digitnexus.base.uidef.TreeNode;
import com.digitnexus.core.osgiservice.impl.SpringContext;
import com.digitnexus.core.provider.ITreeNodeProvider;
import com.digitnexus.core.vo.dept.PermissionVo;

public class PermKeyValueProvider implements ITreeNodeProvider{

	
	@Override
	public List<TreeNode> getRoot() {
		PermManager permManager = SpringContext.getContext().getBean(PermManager.class);
		List<Permission> evos = permManager.permKeyValues();
		List<TreeNode> l = null;
		if(evos != null) {
			List<TreeNode>  roots = this.parseEmployeeToTreeNode(evos,null);
			if(roots != null) {
				l = new ArrayList<TreeNode>();
				l.addAll(roots);
			}
		}
		return l;
	}

	private List<TreeNode> parseEmployeeToTreeNode(List<Permission> evos,List<TreeNode>  roots) {
		
		if(evos == null || evos.isEmpty()) {
			return null;
		}
		
        if(roots == null) {
			roots = new ArrayList<TreeNode>();
		}
		
		for(Permission e : evos) {
			TreeNode parent = getParent(roots,e.getEntityType());
			if(parent == null) {
				parent = new TreeNode(e.getEntityType(),e.getEntityType(),PermissionVo.ENTITY_NODE_TYPE);
				roots.add(parent);
			}
			TreeNode sub = new TreeNode(e.getId(),e.getAction(),PermissionVo.class.getName());
			parent.getChildren().add(sub);
		}
		return roots;
	}

	private TreeNode getParent(List<TreeNode> roots,String entityType) {
		for(TreeNode p : roots) {
			if(p.getId().equals(entityType)) {
				return p;
			}
		}
		return null;
	}
	
}
