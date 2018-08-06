package com.cmty.e3.ui.editor;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.digitnexus.base.uidef.BaseDef;

import net.cmty.ui.core.editor.BaseDefEditorInput;
import net.cmty.ui.core.editor.CompositeFactoryManager;

public class AttributeVoEditorPage extends FormPage {

	private BaseDef def = null;
	
	public AttributeVoEditorPage(CommonVoMutilPageEditor editor,String id,String title,BaseDef def ) {
		super(editor,id,title);
		this.def = def;
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		//super.createFormContent(managedForm);
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		//form.setText(ruleSourceEditor.getEditorInput().getName());
		Composite body = form.getBody();
		toolkit.paintBordersFor(body);
		
		IEclipseContext context = this.getSite().getWorkbenchWindow().getWorkbench()
				.getService(IEclipseContext.class);
		CommonVoEditorInput input = (CommonVoEditorInput)this.getEditorInput();
		
		//以VO的定义为基础创建相应的VO编辑器
		CompositeFactoryManager cfm = context.get(CompositeFactoryManager.class);
		
		cfm.cretateComposite(body,def,new BaseDefEditorInput(input.getMenu()));
		
		//this.block.createContent(managedForm);
	}
	
	

	
}
