package com.digitnexus.ui.celleditor;

import net.techgy.ui.core.utils.UIUtils;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.utils.Utils;
import com.digitnexus.ui.dialogs.CellEditorDialog;

@SuppressWarnings("serial")
public abstract class AbstractDialogCellEditor  extends DialogCellEditor {

	protected FieldDef fd = null;
	
	protected CLabel label;
	
	protected Object modelValue = null;
	
	public AbstractDialogCellEditor(Composite parent,FieldDef fd) {
		super(parent);
		this.fd = fd;
	}
	
	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		CellEditorDialog dialog = this.createCellDialog(cellEditorWindow);
	    int ok = dialog.open();
	    dialog.close();
	    if(ok == Window.OK) {
	       return dialog.getModel();
	    }
	    return null;
	}
	
	protected abstract CellEditorDialog createCellDialog(Control cellEditorWindow);
	
	@Override
	protected Control createContents(Composite cell) {
		label = new CLabel(cell, SWT.LEFT);
		label.setFont(cell.getFont());
		label.setBackground(cell.getBackground());
	    return label;
	}

	@Override
	protected void updateContents(Object value) {
		CLabel label =  this.label;
        if(value == null || value.toString().trim().equals("")) {
        	label.setText("");
		}else if(Utils.getInstance().isCollection(fd)) {
			String[] values = this.getDisplayValues(value);
			if(values == null || values.length < 1) {
				label.setText("");
			} else if(values.length == 1) {
				label.setText(values[0]);
			} else {
				label.setText(values[0]+"...");
				StringBuffer sb = new StringBuffer(values[0]);
				for(int index=1; index < values.length; index++) {
					sb.append("\n").append(values[index]);
				}
				label.setToolTipText(sb.toString());
			}
		} else {
			String[] values = this.getDisplayValues(value);
			if(values == null) {
				return;
			}
			if(values.length != 1) {
				UIUtils.getInstance().showNodeDialog(this.getControl().getShell(), "DataFormatError");
			}
			label.setText(values[0]);
		}
	}
	
	protected String[] getDisplayValues(Object value) {
		if(value instanceof String) {
			String v = (String)value;
			if(v.startsWith("[") && v.endsWith("]")) {
				v = v.substring(1);
				v = v.substring(0,v.length()-1);
				value = v.split(",");
			}
		}
		String[]  vs = Utils.getInstance().getCollectionValues(value);
		if(this.fd != null && this.fd.getKeyValues() != null && !this.fd.getKeyValues().isEmpty() ) {
			vs = Utils.getInstance().getMapValues(this.fd.getKeyValues(), vs);
		} else if(this.fd != null && this.fd.getTreeRoots() != null && !this.fd.getTreeRoots().isEmpty() ) {
			vs = Utils.getInstance().getTreeLabels(fd, vs);
		}
		return vs;
	}


	@Override
	protected void doSetValue(Object value) {
		// TODO Auto-generated method stub
		this.modelValue = value;
		super.doSetValue(value);
	}

}
