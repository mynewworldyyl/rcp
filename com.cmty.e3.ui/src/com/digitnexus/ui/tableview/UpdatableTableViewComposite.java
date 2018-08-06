package com.digitnexus.ui.tableview;

import net.cmty.ui.core.editor.IEditorInput;
import net.techgy.ui.core.utils.UIUtils;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.TableViewerEditorDef;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.utils.ReflectUtils;
import com.digitnexus.ui.model.PropertySourceModel;

@SuppressWarnings("serial")
public class UpdatableTableViewComposite extends ReadOnlyTableViewComposite{
	
	//protected Object model;
	
	public UpdatableTableViewComposite(Composite parent,int style, IEditorInput input, TableViewerEditorDef def) {
		super(parent, style, input, def);
		//双击做修改
		this.setDoubleClickEditor();
	}

	protected boolean doAction(Button action) {
		boolean f = super.doAction(action);
		if(f) {
			return f;
		}
		ActionDef ad = (ActionDef)action.getData();
		if(ad == null) {
			doNoDefAciton(action);
		} else {
			if(ad.getActionType().equals(ActionType.Create)) {
				//创建新单，单据还有明细
	    		return this.doCreate(ad);
	    	}else if(ad.getActionType().equals(ActionType.Delete)) {
	    		return this.doDelete(ad);
	    	}else if(ad.getActionType().equals(ActionType.Modify)) {
	    		return this.doModify(ad);
	    	}else if(ad.getActionType().equals(ActionType.Add)) {
	    		//增加新元素
	    		return this.doAdd(ad);
	    	}else if(ad.getActionType().equals(ActionType.Cancel)) {
	    		return this.doCancel(ad);
	    	}else if(ad.getActionType().equals(ActionType.Save)) {
	    		return this.doSave(ad);
	    	}else {
	    		return this.doOtherAction(ad);
	    	}
		}
		return false;
	}
	
	protected boolean doAdd(ActionDef ad) {
		 String itemCls = this.def.getEditNodeType();
		 if(itemCls == null) {
			 return true;
		 }
		 Object elt = ReflectUtils.getInstance().newInstance(itemCls,
				 ReadOnlyTableViewComposite.class.getClassLoader());
		 if(elt == null) {
			 return true;
		 }
		 
		 //从服务器取得新增加实例ID
		String id = UIUtils.getInstance().getIdFromServer(this.def.getClsName());
		FieldDef fd = UIUtils.getInstance().getIdPropertyDef(this.itemDefs);
		if(fd == null) {
			//ID不能为空
			UIUtils.getInstance().showNodeDialog(this.getShell(), "NoIdFormClass", this.def.getClsName());
			return true;
		}
		//设置新元素的ID值
		UIUtils.getInstance().setModelProperty( elt,fd, id);
		//除ID之外的默认值
		setDefaultValeu(elt);
		
		//有修改或增加权限
		boolean modifialbe = hasPermission(ActionType.Modify) || hasPermission(ActionType.Add);
		PropertySourceModel obj = new PropertySourceModel(elt,this.itemDefs,modifialbe,this.def.getClsName());
		obj.addPropertyChangeListener(this.pcl);
		this.psms.add(obj);
		this.addElements.add(obj);
		this.viewer.refresh();
		this.startCellEditor(obj);
		return true;
	}
	
	@Override
	public String getPanelId() {
		return UpdatableTableViewComposite.class.getName();
	}
}
