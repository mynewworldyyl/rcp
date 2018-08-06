package net.techgy.cmty.ui.views;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.digitnexus.base.uidef.menu.Menu;

/**
 * 左侧菜单栏显示列表
 * @author T440
 *
 */
public class ViewLabelProvider  extends LabelProvider {
    @Override
    public Image getImage( Object element ) {
     /* IWorkbench workbench = PlatformUI.getWorkbench();
      ISharedImages sharedImages = workbench.getSharedImages();
      return sharedImages.getImage( ISharedImages.IMG_OBJ_ELEMENT );*/
    return super.getImage(element);
    }

	@Override
	public String getText(Object element) {
		return ((Menu)element).getName();
	}
  }
