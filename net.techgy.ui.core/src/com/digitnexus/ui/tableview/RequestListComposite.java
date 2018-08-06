package com.digitnexus.ui.tableview;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.TableViewerEditorDef;
import com.digitnexus.ui.model.PropertySourceModel;

import net.cmty.ui.core.editor.IEditorInput;
import net.techgy.cmty.service.DataDto;
import net.techgy.ui.core.network.WSConn;

/**
 * request list, the header for query condition, and the items for request list
 * @author ylye
 *
 */
public class RequestListComposite extends UpdatableTableViewComposite{

	public RequestListComposite(Composite parent,int style, IEditorInput input, TableViewerEditorDef def,Object reqObj) {
		super(parent,style,input,def/*,reqObj*/);
	}
	
	protected boolean doModify(ActionDef ad) {
		/*Object request = this.getRequest(getTableViewDef(),this.getSingleSelectModel());
		if(request == null) {
			return true;
		}
		try {
			  IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			  IEditorInput input = new RequestDetailEditorInput(request,getTableViewDef(),false);
			  IEditorPart editor = page.findEditor(input);
			  if(editor == null) {
				  page.openEditor(input,RequestDetailEditor.class.getName(),true );
			  } else {
				  page.activate(editor);
			  }
	        } catch( PartInitException e ) {
	          e.printStackTrace();
	     }*/
		return true;
	}
	
	protected boolean doDelete(ActionDef ad) {
		Object elt = this.getSingleSelectModel();
		if(elt == null) {
			return true;
		}
		if(elt instanceof PropertySourceModel) {
			elt = (PropertySourceModel)elt;
		}
		String cls = elt.getClass().getName();
		Map<String,String> ps = new HashMap<String,String>();
		ps.put("cls", cls);
		ps.put("reqNum", this.getSelectReqId(elt));
		DataDto resp = WSConn.ins().call(ad, ps);
		this.reflesh();
		if(resp.isSuccess()) {
			
		} else {
			
		}
		return true;
	}
	
	protected boolean doCreate(ActionDef ad) {
		/*try {
			  Object reqObj = ReflectUtils.getInstance().newInstance(this.def.getClsName(),
					  RequestListComposite.class.getClassLoader());
			  this.addModel(reqObj);
			  IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			  IEditorInput input = new RequestDetailEditorInput(reqObj,getTableViewDef(),false);
			  IEditorPart editor = page.findEditor(input);
			  if(editor == null) {
				  page.openEditor(input,RequestDetailEditor.class.getName(),true );
			  } else {
				  page.activate(editor);
			  }
	        } catch( PartInitException e ) {
	          e.printStackTrace();
	     }*/
		return true;
	}
}
