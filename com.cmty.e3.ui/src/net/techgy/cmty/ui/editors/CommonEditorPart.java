package net.techgy.cmty.ui.editors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.menu.Menu;

import net.cmty.ui.core.editor.AbstractEditorPart;
import net.cmty.ui.core.editor.BaseDefEditorInput;
import net.cmty.ui.core.editor.BaseDefEditorProvier;
import net.cmty.ui.core.editor.IEditorProvider;
import net.cmty.ui.core.workbench.WorkbenchSelectionManager;
import net.techgy.ui.core.CmtyServiceManaer;

public class CommonEditorPart {

	@Inject
	private CmtyServiceManaer sm;
	
	@Inject
	private WorkbenchSelectionManager selectionManager;
	
	@PostConstruct
	protected void createContents(Composite parent,IEclipseContext context) {

		IStructuredSelection selection = (IStructuredSelection)selectionManager.getSelection();
		if(selection == null || !(selection.getFirstElement() instanceof Menu)) {
			return;
		}
	
		Menu menu = (Menu)selection.getFirstElement();
		
		 String editorId = menu.getEditorId();
		 if(editorId == null) {
			 editorId = BaseDefEditorProvier.COMMON_EDITOR_ID;
		 }
		 
		//编辑器没有打开，通过编辑器提供者创建
		IEditorProvider p = sm.getEditorProvider(editorId);
		if(p == null) {
			throw new CommonException("EditorNotFound", editorId);
		}
		AbstractEditorPart editor = (AbstractEditorPart)p.createEditor(parent, new BaseDefEditorInput(menu),context);
		if(editor == null) {
			//创建失败
			throw new CommonException("EditorNotFound",editorId);
		}
		
		
	}
	
}
