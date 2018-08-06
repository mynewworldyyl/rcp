package com.digitnexus.ui.treeview;

import java.util.Collection;

import net.cmty.ui.core.editor.IEditorInput;
import net.techgy.ui.core.utils.UIUtils;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.TreeViewerEditorDef;
import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.utils.ReflectUtils;

@SuppressWarnings("serial")
public class UpdatableTreeViewComposite extends ReadOnlyTreeViewComposite{

	public UpdatableTreeViewComposite(Composite parent,int style, IEditorInput input,TreeViewerEditorDef def,Object obj) {
		super( parent, style,input, def, obj);
		this.setDoubleClickEditor();
	}
	
	protected boolean doAction(Button action) {
		boolean f = super.doAction(action);
		if(f) {
			return f;
		}
		
		ActionDef ad = (ActionDef)action.getData();
		
		if(ad.getActionType().equals(ActionType.Create)) {
			return this.doCreate(ad);
    	}else if(ad.getActionType().equals(ActionType.Delete)) {
    		return this.doDelete(ad);
    	}else if(ad.getActionType().equals(ActionType.Modify)) {
    		return this.doModify(ad);
    	}else if(ad.getActionType().equals(ActionType.Add)) {
    		return this.doAdd(ad);
    	}else if(ad.getActionType().equals(ActionType.Save)) {
    		return this.doSave(ad);
    	}
		return false;
	}
	
	
	protected boolean doAdd(ActionDef ad) {
		String itemCls = this.def.getClsName();
		if (itemCls == null) {
			throw new CommonException("SystemError");
		}

		Object[] objs = UIUtils.getInstance().getSelections(this.viewer.getSelection());
		boolean canAddRoot = this.canAddRootElt();
		if (objs == null || objs.length > 1) {
			if (!canAddRoot) {
				UIUtils.getInstance().showNodeDialog(this.getShell(),"NeedSelectOneParent");
				return true;
			}
		}

		TreeNode psm = (TreeNode) this.getSingleSelectElement();
		if (psm != null) {
			String validateParentNodeType = this.def.getNameParams().get(UIConstants.VALIDATE_PARENT_NODE_TYPE);
			String parentNodeType = (String) UIUtils.getInstance()
					.getFieldValue(psm.getModel(),
							UIConstants.ModelProperty.NodeType.getFieldName());
			if (validateParentNodeType != null) {
				if (parentNodeType == null
						|| !validateParentNodeType.equals(parentNodeType)) {
					throw new CommonException("CurrentParentInvalide");
				}
			}
		}

		Object model = ReflectUtils.getInstance().newInstance(
				this.def.getClsName(),
				UpdatableTreeViewComposite.class.getClassLoader());
		if (model == null) {
			throw new CommonException("ResourceNotFound");
		}

		String id = UIUtils.getInstance()
				.getIdFromServer(this.def.getClsName());
		FieldDef fd = UIUtils.getInstance().getIdPropertyDef(this.itemDefs);
		if (fd == null) {
			throw new CommonException("NoIdFormClass",this.def.getClsName());
		}
		UIUtils.getInstance().setModelProperty(model, fd, id);

		if (psm != null) {
			Object parentId = UIUtils.getInstance().getModelProperty(psm.getModel(), fd);
			UIUtils.getInstance().setModelProperty(model,this.getTreeViewerDef().getParentFieldName(), parentId);
			Object itemsObj = UIUtils.getInstance().getModelProperty(psm.getModel(), this.getTreeViewerDef().getSubFieldName());
			if (!(itemsObj instanceof Collection)) {
				return true;
			}
			Collection<Object> l = (Collection) itemsObj;
			l.add(model);
		}
		TreeNode tn = new TreeNode(model,this.getTreeViewerDef().getItemDefs(), true,this.def.getClsName());
		tn.addPropertyChangeListener(this.pcl);
		if (psm != null) {
			tn.setParent(psm);
			psm.getChildren().add(tn);
		} else {
			this.psms.add(tn);
		}
		addElements.add(tn);
		this.viewer.refresh();
		this.startCellEditor(tn);
		return true;
	}

	protected boolean canAddRootElt() {
		return true;
	}
	
	@Override
	public String getPanelId() {
		return UpdatableTreeViewComposite.class.getName();
	}
}
