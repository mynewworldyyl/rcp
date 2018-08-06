package net.cmty.ui.core.editor;

import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.uidef.BaseDef;
import com.digitnexus.ui.tableview.HeaderViewComposite;

public interface ICompositeFactory {

	HeaderViewComposite cretateComposite(Composite parent, BaseDef def,IEditorInput input);
	
}
