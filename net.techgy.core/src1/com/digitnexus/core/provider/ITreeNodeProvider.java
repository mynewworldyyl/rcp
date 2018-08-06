package com.digitnexus.core.provider;

import java.util.List;

import com.digitnexus.base.uidef.TreeNode;


public interface ITreeNodeProvider  extends IValueProvider{

	List<TreeNode> getRoot();
}
