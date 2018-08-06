package com.digitnexus.ui.celleditor;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.ui.dialogs.CellEditorDialog;
import com.digitnexus.ui.dialogs.CheckboxListCellEditorDialog;

@SuppressWarnings("serial")
public class ListDialogCellEditor extends AbstractDialogCellEditor {

	public ListDialogCellEditor(Composite parent,FieldDef fd) {
		super(parent,fd);
	}
	
	@Override
	protected CellEditorDialog createCellDialog(Control cellEditorWindow) {
		CheckboxListCellEditorDialog dialog = new CheckboxListCellEditorDialog(cellEditorWindow.getShell(),fd,modelValue);
	    return dialog;
	}
	
}
