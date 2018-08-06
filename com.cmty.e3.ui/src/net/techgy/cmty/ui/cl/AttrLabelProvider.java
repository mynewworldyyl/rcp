package net.techgy.cmty.ui.cl;

import net.techgy.ui.core.utils.ImageUtils;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.digitnexus.ui.treeview.TreeNode;

public class AttrLabelProvider extends LabelProvider {

	public static Image DEFAULT_IMAGE_01;
	public static Image DEFAULT_IMAGE_02;
	
	static {
		DEFAULT_IMAGE_01= ImageUtils.getInstance().getImage(
				AttrLabelProvider.class.getResourceAsStream("/img/ti03.png"), 20, 20);
		DEFAULT_IMAGE_02= ImageUtils.getInstance().getImage(
				AttrLabelProvider.class.getResourceAsStream("/img/ti04.png"), 20, 20);
	}
	public AttrLabelProvider() {
		
	}
	
	@Override
	public Image getImage(Object element) {
		if(element instanceof TreeNode) {
			TreeNode g = (TreeNode)element;
			/*if(g.getImg() != null) {
				return g.getImg();
			}*/
		}
		return DEFAULT_IMAGE_01;
	}

	@Override
	public String getText(Object element) {
		if(element instanceof TreeNode) {
			TreeNode g = (TreeNode)element;
			/*if(g.getLabel() != null) {
				return g.getLabel();
			}*/
		}
		return "";
	}

	
}
