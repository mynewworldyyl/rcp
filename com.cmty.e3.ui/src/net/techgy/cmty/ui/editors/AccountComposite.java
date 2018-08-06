package net.techgy.cmty.ui.editors;

import net.cmty.ui.core.editor.IEditorInput;

import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.uidef.TableViewerEditorDef;
import com.digitnexus.ui.tableview.UpdatableTableViewComposite;

@SuppressWarnings("serial")
public class AccountComposite extends UpdatableTableViewComposite{

	public AccountComposite(Composite parent,int style, IEditorInput input,TableViewerEditorDef def) {
		super(parent, style, input,def);
	}
	
}
