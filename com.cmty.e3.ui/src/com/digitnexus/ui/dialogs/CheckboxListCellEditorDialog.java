package com.digitnexus.ui.dialogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.utils.Utils;

@SuppressWarnings("serial")
public class CheckboxListCellEditorDialog extends CellEditorDialog {
	
	private Composite container =  null;
	
	private List<Button> checkBoxes = new ArrayList<Button>();
	
	public CheckboxListCellEditorDialog( Shell parent, FieldDef fd, Object value){
		super(parent,fd,value);
	}

	@Override
	protected Object getValue() {
		List<String> values = new ArrayList<String>();
		for(Button b : checkBoxes) {
			if(b.getSelection()) {
				values.add((String)b.getData());
			}
		}
		String[] vs = new String[values.size()];
		vs = values.toArray(vs);
		return vs;
	}

	@Override
	protected void initilizeDialogArea() {
		
		int style = this.isMultil() ? SWT.MULTI : SWT.SINGLE;
		container = new Composite(this.contentContainer, SWT.H_SCROLL | SWT.V_SCROLL | style);
		container.setLayout(new FillLayout(SWT.VERTICAL));
		
		GridData gd = new GridData(SWT.FILL,SWT.TOP,true,false,1,1);
		gd.minimumHeight=300;
	    container.setLayoutData(gd);
		
		Map<String, String> keyvalues = fd.getKeyValues();
		String[] items = Utils.getInstance().getCollectionValues(this.value);
		int selectionModel = this.isMultil() ? SWT.CHECK : SWT.RADIO;
		for (Map.Entry<String, String> item : keyvalues.entrySet()) {
			Button b = new Button(container, selectionModel);
			b.setText(item.getValue());
			b.setData(item.getKey());
			if (items != null) {
				for (String i : items) {
					if (i.equals(item.getKey())) {
						b.setSelection(true);
					}
				}
			}
			this.checkBoxes.add(b);
		}
	}

	@Override
	protected void clearContent() {
		if(this.container != null) {
			Control[] cs = this.container.getChildren();
			for(Control c : cs) {
				c.dispose();
			}
			container.dispose();
		}
		this.checkBoxes.clear();
		
		container = null;
		super.clearContent();
	}
	
	
}
