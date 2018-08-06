package com.digitnexus.ui.tableview;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.ViewerDef;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.base.utils.ReflectUtils;
import com.digitnexus.ui.celleditor.CellEditorHelper;
import com.digitnexus.ui.celleditor.ItemCellModifier;
import com.digitnexus.ui.model.PropertySourceModel;

import net.cmty.ui.core.editor.IEditorInput;
import net.cmty.ui.core.i18n.I18NUtils;
import net.techgy.cmty.service.DataDto;
import net.techgy.ui.core.network.WSConn;
import net.techgy.ui.core.utils.UIUtils;

@SuppressWarnings("serial")
public abstract class ItemsViewComposite extends HeaderViewComposite{

	protected List<FieldDef> itemDefs;
	
	protected  ColumnViewer viewer;
	
	private CellEditor[] cellEditors = null;
	
	protected List<PropertySourceModel> updateElements = new ArrayList<PropertySourceModel>();
	
	protected List<PropertySourceModel> addElements = new ArrayList<PropertySourceModel>();
	
	protected List<PropertySourceModel> psms = new ArrayList<PropertySourceModel>();
	
	protected PropertyChangeListener pcl = new PropertyChangeListener(){
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			Object to = evt.getSource();
			//viewer中所有数据都经过PropertySourceModel封装，不支持非PropertySourceModel数据源
			if(!(to instanceof PropertySourceModel)) {
				throw new CommonException("SystemError");
			}
			viewer.refresh();
			if(addElements.contains(to)){
				//已包含在新增列表中
				return;
			}else if(!updateElements.contains(to)) {
				//作为修改数据保存
				updateElements.add((PropertySourceModel)to);
				changeButtonState();
			}
		}
	};
	
	private ISelectionChangedListener actionListener = new ISelectionChangedListener(){
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			//根据用户当前的选择改变按钮的状态
			changeButtonState();
		}
	};
	
	@Override
	protected boolean doAction(Button action) {
		ActionDef ad = (ActionDef)action.getData();
		//ext类型操作直接发到服务器处理，客户端不做任何处理。
		if(ad.getActionType().equals(ActionType.Ext)) {
			return this.doExt(ad);
    	}
		return false;
	}

	public ItemsViewComposite(Composite parent,int style,List<FieldDef> headerOrQeuryDefs,List<ActionDef> actionDefs,
			List<FieldDef> itemDefs, IEditorInput input,ViewerDef def) {
		super(parent,style,headerOrQeuryDefs,actionDefs, input, def);
		//表
		this.itemDefs = itemDefs;
		viewer = createContentViewer();
		viewer.addSelectionChangedListener(actionListener);
		this.addFocusListener(new FocusAdapter(){
			@Override
			public void focusLost(FocusEvent event) {
				doLostFocus();
			}
		});
		//按钮默认状态
		changeButtonState();
	}
	
	/**
	 * 创建字段编辑器，如当用户单击时激活编辑框，或弹出对话框
	 * @param itemDefs
	 * @param parent
	 * @return
	 */
	protected CellEditor[] getCellEditors(List<FieldDef> itemDefs,Composite parent) {
		List<CellEditor> es = new ArrayList<CellEditor>();
		for (FieldDef fd : itemDefs) {
			CellEditor editor = null;
			if (fd.isHide() || fd.isHideInRow()) {
				//隐藏字段无法编辑，在列表中不占位置，也就不显示
				continue;
			}
			if (fd.isIdable() || !fd.isEditable()) {
				//ID及不能编辑的字段也没有CellEditor，但其在列表中占居一个位置显示数据
				es.add(null);
				continue;
			}
			editor = CellEditorHelper.getInstance().createCellEditor(parent, fd);
			es.add(editor);
		}
		CellEditor[] cellEditors = new CellEditor[es.size()];
		cellEditors = es.toArray(cellEditors);
		return cellEditors;
	}
	
	protected void setDefaultValeu(Object elt) {
		for(FieldDef fd : this.itemDefs) {
			String dv = this.getDefaultValue(fd);
			if(dv != null) {
				ReflectUtils.getInstance().setFieldValue(elt, fd, dv);
			}
		}
	}
	
	/**
	 * 取字段定义的默认值
	 * @param fd
	 * @return
	 */
	private String getDefaultValue(FieldDef fd) {
		if(fd.getDefaultValue() != null && !fd.getDefaultValue().isEmpty()) {
			return fd.getDefaultValue().values().iterator().next().toString();
		}else {
			return null;
		}
	}
	
	protected abstract ColumnViewer createContentViewer();
	
	protected abstract Composite getViewerControl();
	
	/**
	 * 双击时，激活字段编辑器。
	 */
	protected void setDoubleClickEditor() {
		if(hasPermission(ActionType.Modify)) {
			this.viewer.addDoubleClickListener(new IDoubleClickListener(){
				@Override
				public void doubleClick(DoubleClickEvent event) {
				    doModify(getActionDef(ActionType.Modify));
 				}				
			});
		}
	}
	
	/**
	 * 是否具有指定的操作类型的权限，如果没有权限，则没有相应操作的定义
	 * @param modify
	 * @return
	 */
	protected boolean hasPermission(ActionType modify) {
		ActionDef ad = this.getActionDef(modify);
		return ad != null;
	}

	protected void startCellEditor(Object elt) {
		 if(!(elt instanceof PropertySourceModel)) {
			 return;
		 }
		 PropertySourceModel tm = (PropertySourceModel)elt;
		 Object nodeType = UIUtils.getInstance().getFieldValue(tm.getModel(),UIConstants.ModelProperty.NodeType.getFieldName());
		 if(nodeType == null) {
			 nodeType = tm.getModel().getClass().getName();
		 }
		 if(nodeType == null || !nodeType.equals(this.def.getEditNodeType())) {
			 UIUtils.getInstance().showNodeDialog(this.getShell(), "CanNotEditNode", this.def.getClsName());
			 return ;
		 }
		 if(this.cellEditors == null) {
			 this.cellEditors = this.getCellEditors(this.itemDefs,getViewerControl());
			
		 }
		 viewer.setCellModifier(ItemCellModifier.getInstance());
		 viewer.setCellEditors(cellEditors);
		 this.cellEditors[0].setFocus();
		 viewer.editElement(elt, 0);
	}
	
	protected void stopCellEditor() {
		 viewer.setCellEditors(null);
	}
	
	protected String getValue(FieldDef fd,Object obj) {
		if(obj == null) {
			return null;
		} 
		String value = ReflectUtils.getInstance().getFieldValue(obj, fd.getFieldName());
		return value;
	}
	
	
	protected boolean doCancel(ActionDef ad) {
		this.stopCellEditor();
		//PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(editor, false);
		changeButtonState();
		return true;
	}
	
	protected boolean doDelete(ActionDef ad) {
		List<String> ids = new ArrayList<String>();
		IStructuredSelection selection = (IStructuredSelection) this.viewer.getSelection();
		boolean isNeedReflesh = false;
		if (selection != null) {
			Iterator ite = selection.iterator();
			while (ite.hasNext()) {
				Object model = ite.next();
				if (model != null && model instanceof PropertySourceModel) {
					if(this.addElements.contains(model)) {
						this.addElements.remove(model);
						isNeedReflesh = true;
						continue;
					}
					PropertySourceModel pm = (PropertySourceModel) model;
					String id = (String) UIUtils.getInstance().getModelId(pm.getModel(),this.itemDefs);
					if (id != null) {
						ids.add(id);
					}
					pm.removePropertyChangeListener(this.pcl);
				}
			}
		}
		if (ids.isEmpty()) {
			if(!isNeedReflesh) {
				UIUtils.getInstance().showNodeDialog(getShell(),
						I18NUtils.getInstance().getString("NoSelectionNode"));
			}
		} else {
			Map<String, String> params = new HashMap<String, String>();
			params.put("clsName", this.def.getClsName());
			params.put("ids", JsonUtils.getInstance().toJson(ids, false));
			DataDto resp = WSConn.ins().call(ad, params);
			if (resp.isSuccess()) {
				//UIUtils.getInstance().showNodeDialog(getShell(),I18NUtils.getInstance().getString("DeleteSuccess"));
				isNeedReflesh = true;
				this.changeButtonState();
			} else {
				UIUtils.getInstance().showNodeDialog(getShell(),resp.getMsg());
			}
		}
		
		if(isNeedReflesh) {
			this.reflesh();
		}
		return true;
	}
	
	 protected boolean doCreate(ActionDef ad) {
		 return true;
	 }
	
	 protected boolean doOtherAction(ActionDef ad) {
		 return true;
	 }
	
	 protected boolean doNoDefAciton(Button action) {
		  throw new UnsupportedOperationException( "not support No DEF Action for: " + this.def.getName() );
	 }
	 
	 protected boolean doModify(ActionDef ad) {
		 if(!this.hasPermission(ActionType.Modify)) {
			 //没有修改的权限
			 UIUtils.getInstance().showNodeDialog(this.getShell(), "HasNoPermission", ActionType.Modify.getName());
			 return false;
		 }
		 PropertySourceModel[] els = this.getSelectElement();
		 if(els == null || els.length== 0 || els.length > 1) {
			 //当前没有选择数据，或选择数据多于一行，不能修改
			 UIUtils.getInstance().showNodeDialog(this.getShell(), "MustSelectOneNode", this.def.getClsName());
		 } else {
			 //开启编辑
			 startCellEditor(els[0]);
		 }
		 return true;
	 }
	 
	protected boolean doExport(ActionDef ad) {
		throw new UnsupportedOperationException( "not support Export Action for: " + def.getName() );
    }
	 
	 private boolean doExt(ActionDef ad) {
		 try {
			Method m = this.getClass().getDeclaredMethod(ad.getName(), ActionDef.class);
			if(m == null) {
				 UIUtils.getInstance().showNodeDialog(this.getShell(), "SystemError");
				 return false;
			}
			return (boolean)m.invoke(this, ad);
		}catch (Exception e) {
			 UIUtils.getInstance().showNodeDialog(this.getShell(), "SystemError");
			 return false;
		}
	 }
	 
	@Override
	public boolean isDirty() {
		return !this.addElements.isEmpty() || !this.updateElements.isEmpty();
	}

	@Override
	public boolean save() {
		ActionDef at = this.getActionDef(ActionType.Query);
		if(at != null) {
			return doSave(at);
		}
		return false;
	}

	protected boolean  doSave(ActionDef ad) {
    	this.stopCellEditor();
    	if(this.addElements.isEmpty() && this.updateElements.isEmpty()) {
    		MessageDialog.openError(this.getShell(), I18NUtils.getInstance().getString("Note"),
					 I18NUtils.getInstance().getString("NoDataToSave"));
    		return true;
    	}
    	DataDto saveResp = null;
    	DataDto updateResp = null;
    	if(!this.addElements.isEmpty()) {
    		ActionDef save = this.getActionDef(ActionType.Save);
    		List<Object> l = new ArrayList<Object>();
        	for(PropertySourceModel tn: this.addElements) {
        		l.add(tn.getModel());
        	}
        	String json = JsonUtils.getInstance().toJson(l, false);
        	String clsName = l.get(0).getClass().getName();
    		Map<String,String> ps = new HashMap<String,String>();
    		ps.put("clsName", clsName);
    		ps.put("vos", json);
    		saveResp = WSConn.ins().call(save, ps);
    	}
    	
    	if(!this.updateElements.isEmpty()) {
    		String updateUrl = null;
    		String updateMethod = null;
    		ActionDef update = this.getActionDef(ActionType.Modify);
    		if(update == null) {
    			updateUrl = this.def.getNameParams().get(UIConstants.UPDATE_PATH);
    			updateMethod = this.def.getNameParams().get(UIConstants.UPDATE__METHOD);
    		} else {
    			updateUrl = update.getUrl();
    			updateMethod = update.getMethod();
    		}
    		
    		if(updateUrl == null) {
    			UIUtils.getInstance().showNodeDialog(this.getShell(), "SystemError");
				return true;
    		}
    		List<Object> l = new ArrayList<Object>();
        	for(PropertySourceModel tn: this.updateElements) {
        		l.add(tn.getModel());
        	}
        	String json = JsonUtils.getInstance().toJson(l, false);
        	String clsName = l.get(0).getClass().getName();
    		Map<String,String> ps = new HashMap<String,String>();
    		ps.put("clsName", clsName);
    		ps.put("vos", json);
    		updateResp = WSConn.ins().call(updateUrl,updateMethod, ps);
    		
    	}
		
    	if((saveResp != null && saveResp.isSuccess()) ||  (updateResp != null && updateResp.isSuccess())) {
    		this.updateElements.clear();
    		this.addElements.clear();
    		this.reflesh();
    		this.changeButtonState();
    		//UIUtils.getInstance().showNodeDialog(this.getShell(), "SaveSuccess");
		} else {
			String msg = saveResp != null && !saveResp.isSuccess() ? saveResp.getMsg(): (updateResp != null && !updateResp.isSuccess()? updateResp.getMsg():"NotKnowException");
			UIUtils.getInstance().showNodeDialog(this.getShell(), "FailToSave",msg);
		}
    	return true;
	}
	
	protected PropertySourceModel[] getSelectElement() {
		IStructuredSelection selection = (IStructuredSelection)this.viewer.getSelection();
		if(selection != null) {
			Iterator<PropertySourceModel> iterator = selection.iterator();
			List<PropertySourceModel> l = new ArrayList<PropertySourceModel>();
			while(iterator.hasNext()) {
				l.add(iterator.next());
			}
			PropertySourceModel[] pses = new PropertySourceModel[l.size()];
			pses = l.toArray(pses);
			return pses;
		}
	   return null;
	}
	
	protected Object getSingleSelectElement() {
		PropertySourceModel[] pses = this.getSelectElement();
		if(pses == null || pses.length !=1) {
			return null;
		}else {
			return pses[0];
		}
	}
	
	protected Object getSingleSelectModel() {
		PropertySourceModel sm = (PropertySourceModel)this.getSingleSelectElement();
		if(sm != null) {
			return sm.getModel();
		}
	   return null;
	}
	
	/**
	 * 刷新时重新请求服务器做查询
	 */
	public void reflesh() {
		this.doQuery(this.getActionDef(ActionType.Query));
	}
	
	protected ActionDef getActionDef(ActionType type) {
		for(ActionDef def : this.actionDefs) {
			if(def.getActionType().equals(type)) {
				return def;
			}
		}
		return null;
	}
	
	protected abstract boolean doQuery(ActionDef ad);
	
    protected void changeButtonState() {
    	//根据操作类型取得按钮
    	Button saveButton = getButton(ActionType.Save);
    	Button deleteButton = getButton(ActionType.Delete);
    	Button updateButton = getButton(ActionType.Modify);
    	
    	boolean f = !this.addElements.isEmpty() || !this.updateElements.isEmpty();
    	if(saveButton != null) {
    		//如果有新增或修改数据，则装保存按钮置为可用，否则置为不可用
    		saveButton.setEnabled(f);
    	}
    	
    	PropertySourceModel[] selectionNodes = this.getSelectElement();
        if(deleteButton != null) {
           //如果选择了数据，则装删除按钮设置为可用，否则不可用
	       deleteButton.setEnabled(selectionNodes.length > 0);
    	}
    	
        if(updateButton != null) {
        	//如果选择了一行数据，可更新按扭可用，否则不可用
	        updateButton.setEnabled(selectionNodes.length == 1);
    	}
	}

	private Button getButton(ActionType at) {
		if(this.buttons.isEmpty()) {
			return null;
		}
		for(Button b : this.buttons) {
			ActionDef ad = (ActionDef)b.getData();
			if(ad != null && ad.getActionType().equals(at)) {
				return b;
			}
		}
		return null;
	}
	
	/**
	 * 
	 */
	protected void mergeLocal() {
		if(!this.addElements.isEmpty()) {
			//新增加元素，加入到显示列表中
			psms.addAll(this.addElements);
		}
		if(!this.updateElements.isEmpty()) {
			//先删除，然后再加入，表示更新
			for(PropertySourceModel ps : this.updateElements) {
				psms.remove(ps);
			}
			psms.addAll(this.updateElements);
		}
	}

	/**
	 * 失去输入焦点后，将选择置为空
	 */
	protected void doLostFocus() {
		this.viewer.setSelection(null);
	}
}
