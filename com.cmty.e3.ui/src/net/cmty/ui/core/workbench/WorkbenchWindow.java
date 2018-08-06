package net.cmty.ui.core.workbench;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import net.cmty.ui.core.editor.EditorPartContainer;
import net.cmty.ui.core.editor.IEditorInput;
import net.cmty.ui.core.view.ViewPartContainer;
import net.techgy.ui.core.CoreUIActivator;
import net.techgy.ui.core.content.CmtyWindow;

/**
 * 视图（View）及编辑器（Editor）的窗口。左边显示视图，右边显示编辑器，中间可能过鼠标调整其所占大小。
 * @author T440
 */
@SuppressWarnings("serial")
public class WorkbenchWindow extends Composite{

	public static final String WORKBENCH_WINDOW_ID = CoreUIActivator.PLUGIN_ID +"/"+ 
	WorkbenchWindow.class.getName();
	
	private CmtyWindow cmtyWindow;
	
	private EditorPartContainer editorContainer;
	
	private ViewPartContainer viewContainer;
	
	private ViewSite viewSite;
	
	private EditorSite editorSite;
	
	public WorkbenchWindow(Composite parent,int style,CmtyWindow cmtyWindow) {
		super(parent,style);
		this.cmtyWindow = cmtyWindow;
		cmtyWindow.getContext().set(WORKBENCH_WINDOW_ID, this);
		this.setLayout(new FillLayout());
		createContent();
		//RWT.getUISession().setAttribute(WORKBENCH_WINDOW_ID, this);
		//this.getViewSite().showView(viewId);
	}

	/**
	 * 创建视图及编辑器容器
	 */
	private void createContent() {
		SashForm sashForm = new SashForm(this, SWT.HORIZONTAL);
		this.viewContainer = new ViewPartContainer(sashForm,SWT.NONE,cmtyWindow);
		this.editorContainer = new EditorPartContainer(sashForm,SWT.NONE);
		sashForm.setWeights(new int[]{25,75});
		sashForm.setSashWidth(3);
		sashForm.setBackground(this.getDisplay().getSystemColor(SWT.COLOR_GRAY));
	}
	
	public static interface ViewSite{
		void showView(String viewId);
	}
	
	public static interface EditorSite {
		void openEditor(String providerId,IEditorInput input);
	}
	
	public ViewSite getViewSite() {
		if(this.viewSite == null) {
			viewSite = new ViewSite(){
				@Override
				public void showView(String viewId) {
					viewContainer.showView(viewId);
				}
			};
		}
		return viewSite;
	}
	
	public EditorSite getEditorSite() {
		if(this.editorSite == null) {
			editorSite = new EditorSite(){
				@Override
				public void openEditor(String providerId,IEditorInput input) {
					editorContainer.openEditor(providerId, input);
				}
			};
		}
		return editorSite;
	}

	@Override
	public void dispose() {
		//RWT.getUISession().removeAttribute(WORKBENCH_WINDOW_ID);
		super.dispose();
	}
	
}
