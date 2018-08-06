package com.cmty.e3.ui.editor;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import com.digitnexus.base.uidef.BaseDef;
import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.ui.tableview.HeaderViewComposite;

import net.cmty.ui.core.editor.AbstractEditorPart;
import net.cmty.ui.core.editor.BaseDefEditorInput;
import net.cmty.ui.core.editor.CompositeFactoryManager;
import net.techgy.cmty.service.DataDto;
import net.techgy.ui.core.CmtyServiceManaer;
import net.techgy.ui.core.network.WSConn;
import net.techgy.ui.core.utils.UIUtils;

public class CommonVoMutilPageEditor extends FormEditor {

	public static final String ID = CommonVoMutilPageEditor.class.getName();
	
	@Inject
	private CmtyServiceManaer sm;
	
	private AttributeVoEditorPage page ;
	
	@Override
	protected void addPages() {
		CommonVoEditorInput input = (CommonVoEditorInput)this.getEditorInput();
		//调协编辑器上方标题
		this.setPartName(input.getName());
		
		IEclipseContext context = this.getSite().getWorkbenchWindow().getWorkbench()
				.getService(IEclipseContext.class);
		
		BaseDef def = this.getEditorDef(input.getMenu().getObjType());
		page = new AttributeVoEditorPage(this,input.getMenu().getObjType(),input.getName(),def);
		try {
			this.addPage(page);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private BaseDef getEditorDef(String objType) {
		Map<String,String> params = new HashMap<String,String>();
		//input的ID为打开的VO的类名
		params.put("clsName",objType);
		//由类型名请求服务器找到VO的定义
		DataDto resp = WSConn.ins().call(UIConstants.DEF_URL,params);
		if(resp.isSuccess()) {
			if(resp.getObj() == null) {
				throw new NullPointerException("object def is null");
			}
			return (BaseDef) resp.getObj();
		} else {
			UIUtils.getInstance().showNodeDialog(this.getSite().getShell(),resp.getMsg());
			throw new NullPointerException("object def is null");
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		
	}

	@Override
	public void doSaveAs() {
		
	}

	@Override
	public boolean isSaveAsAllowed() {
		
		return false;
	}

	@Override
	protected void setSite(IWorkbenchPartSite site) {
		super.setSite(site);
		IEclipseContext context = this.getSite().getWorkbenchWindow()
				.getWorkbench().getService(IEclipseContext.class);
		this.sm = context.get(CmtyServiceManaer.class);
	}

}
