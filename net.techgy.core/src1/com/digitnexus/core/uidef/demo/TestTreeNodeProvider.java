package com.digitnexus.core.uidef.demo;

import java.util.ArrayList;
import java.util.List;

import com.digitnexus.base.uidef.TreeNode;
import com.digitnexus.core.provider.ITreeNodeProvider;

public class TestTreeNodeProvider implements ITreeNodeProvider {

	@Override
	public List<TreeNode> getRoot() {
		TreeNode root = new TreeNode("总部","总部");
	
		TreeNode hd = new TreeNode("华东大区","华东大区");
		TreeNode c = new TreeNode("江苏靖江厂","江苏靖江厂");
		hd.getChildren().add(c);
		c = new TreeNode("江苏国金","江苏国金");
		hd.getChildren().add(c);
		c = new TreeNode("上海东方明珠塔","上海东方明珠塔");
		hd.getChildren().add(c);
		root.getChildren().add(hd);
		
		TreeNode hn = new TreeNode("华北大区","华北大区");
		 c = new TreeNode("天津厂","天津厂");
		 hn.getChildren().add(c);
		c = new TreeNode("江苏国金","江苏国金");
		hn.getChildren().add(c);
		c = new TreeNode("上海东方明珠塔","上海东方明珠塔");
		hn.getChildren().add(c);
		root.getChildren().add(hn);
		
		List<TreeNode> l = new ArrayList<TreeNode>();
		l.add(root);
		return l;
	}

}
