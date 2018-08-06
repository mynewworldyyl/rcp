package net.techgy.cmty.ui.cl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;

public class NullSelectionEditor extends ObjectEditor {
	
	public NullSelectionEditor(Composite parent,int style,Object obj,ConfigComposite configComposite) {
		super(parent,style,obj,true,configComposite);
	}
	
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return NullSelectionEditor.class.getName();
	}

	@Override
	public void createToolBar(ToolBar toolbar) {
		
	}

	@Override
	public String getTitle() {
		return this.obj.toString();
	}

	@Override
	public void createEditorContent(Composite container) {
		Label l = new Label(container,SWT.NONE);
		l.setText("No content to editable");
	}
	@Override
	public void save() {
		
	}
}
