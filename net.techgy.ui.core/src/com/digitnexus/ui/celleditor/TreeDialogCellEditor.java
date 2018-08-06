package com.digitnexus.ui.celleditor;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.ui.dialogs.CellEditorDialog;
import com.digitnexus.ui.dialogs.TreeCellEditorDialog;

@SuppressWarnings("serial")
public class TreeDialogCellEditor extends AbstractDialogCellEditor {
	
	public TreeDialogCellEditor(Composite parent,FieldDef fd) {
		super(parent,fd);
	}

	@Override
	protected CellEditorDialog createCellDialog(Control cellEditorWindow) {
		TreeCellEditorDialog dialog = new TreeCellEditorDialog(cellEditorWindow.getShell(),fd,modelValue);
	    return dialog;
	}

}
