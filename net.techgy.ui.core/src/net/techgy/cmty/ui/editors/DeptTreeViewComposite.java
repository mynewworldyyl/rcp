package net.techgy.cmty.ui.editors;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.TreeViewerEditorDef;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.core.vo.dept.DepartmentVo;
import com.digitnexus.ui.treeview.TreeNode;
import com.digitnexus.ui.treeview.UpdatableTreeViewComposite;

import net.cmty.ui.core.editor.IEditorInput;
import net.techgy.cmty.service.DataDto;
import net.techgy.cmty.ui.dialog.ClientDialog;
import net.techgy.ui.core.network.WSConn;
import net.techgy.ui.core.utils.UIUtils;

public class DeptTreeViewComposite extends UpdatableTreeViewComposite {

	private Map<String,String> clientTypes  = null;
	
	public DeptTreeViewComposite(Composite parent,int style, IEditorInput input,TreeViewerEditorDef def,Object obj) {
		super( parent, style, input, def, obj);
	}

	public boolean setAsFactory(ActionDef ad) {
		 TreeNode psm = (TreeNode)getSingleSelectElement();
		 if(psm == null) {
			 UIUtils.getInstance().showNodeDialog(this.getShell(), "NoDeptSelected");
			 return true;
		 }
		 DepartmentVo dept = (DepartmentVo) psm.getModel();
		 if(dept.getRelatedClientId() != null && !"".equals(dept.getRelatedClientId())) {
			 UIUtils.getInstance().showNodeDialog(this.getShell(), "RelatedSystemHasBeenSet",dept.getName());
			 return true;
		 }
		 
		 if(clientTypes == null) {
			 DataDto resp = WSConn.ins().call("deptService", "clientTypeList");
			 if(!resp.isSuccess()) {
				 UIUtils.getInstance().showNodeDialog(this.getShell(), resp.getMsg());
				 return true;
			 }
			 clientTypes = JsonUtils.getInstance().getStringMap(resp.getData(), true);;
		 }
		 if(clientTypes == null || clientTypes.isEmpty()) {
			 UIUtils.getInstance().showNodeDialog(this.getShell(), "NoCreateSystemPermission");
			 return true;
		 }
		 
		 ClientDialog dialog = new ClientDialog(this.getShell(),dept,clientTypes);
		 int returnCode = dialog.open();
		 if(returnCode == Window.CANCEL) {
			 return true;
		 }
		 
		 Map<String,String> params = new HashMap<String,String>();
		 params.put("name", dialog.getName());
		 params.put("desc", dialog.getDesc());
		 params.put("deptId", dept.getId());
		 params.put("an", dialog.getUserName());
		 params.put("pw", dialog.getPassword());
		 params.put("clientType", dialog.getClientType());
		 
		 //params.put("name", dialog.getName());
		 //params.put("name", dialog.getName());
		 DataDto  resp = WSConn.ins().call(ad, params);
		 if(!resp.isSuccess()) {
			 UIUtils.getInstance().showNodeDialog(this.getShell(), resp.getMsg());
		 }
		 return true;
	}

	@Override
	protected boolean isDisplayButton(ActionDef ad) {
		if("setAsFactory".equals(ad.getName())) {
			 DataDto resp = WSConn.ins().call("deptService", "clientTypeList");
			 if(!resp.isSuccess()) {
				 return false;
			 }
			 
			 clientTypes = JsonUtils.getInstance().getStringMap(resp.getData(), true);;
			 if(clientTypes == null || clientTypes.isEmpty()) {
				 return false;
			 }
			 return true;
		}
		return super.isDisplayButton(ad);
	}

	protected boolean canAddRootElt() {
		return false;
	}
}
