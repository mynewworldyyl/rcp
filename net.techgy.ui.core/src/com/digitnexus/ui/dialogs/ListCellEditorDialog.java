package com.digitnexus.ui.dialogs;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.utils.Utils;

@SuppressWarnings("serial")
public class ListCellEditorDialog extends CellEditorDialog {
	
	private List list =  null;
	int style = SWT.NONE;
	
	public ListCellEditorDialog( Shell parent,int style,FieldDef fd,Object value){
		super(parent,fd,value);
		this.style = style;
	}

	@Override
	protected Object getValue() {
		String[] values = list.getSelection();
		if (fd.getKeyValues() != null && !fd.getKeyValues().isEmpty()) {
			values = Utils.getInstance().getMapKeys(fd.getKeyValues(), values);
		}
		return values;
	}

	@Override
	protected void initilizeDialogArea() {
		list = new List(this.contentContainer, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | this.style);
		GridData gd = new GridData(SWT.FILL,SWT.FILL,true,true,1,1);
		gd.minimumHeight=300;
		list.setLayoutData(gd);
		Map<String,String> values = this.fd.getKeyValues();
		String[] items = Utils.getInstance().getMapValues(values);
		list.setItems(items);
	}
}
