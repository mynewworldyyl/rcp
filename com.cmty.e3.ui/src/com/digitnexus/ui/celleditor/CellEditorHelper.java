package com.digitnexus.ui.celleditor;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;

import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.TreeNode;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.base.utils.ReflectUtils;
import com.digitnexus.base.utils.Utils;
import com.digitnexus.ui.model.PropertySourceModel;
import com.google.gson.reflect.TypeToken;

import net.techgy.cmty.service.DataDto;
import net.techgy.ui.core.network.WSConn;
import net.techgy.ui.core.utils.UIUtils;

public class CellEditorHelper {

	private static final CellEditorHelper instance = new CellEditorHelper();
	private CellEditorHelper(){}
	public static CellEditorHelper getInstance(){
		return instance;
	}
	
	public boolean modify(Object element, String property, Object value,List<FieldDef> fds) {
		FieldDef fd = this.getFieldDef(property,fds);
		if(fd == null) {
			throw new NullPointerException();
		}
		Object oldValue = this.getValue(element, property);
		if(oldValue.equals(value)) {
			return false;
		}
		ReflectUtils.getInstance().setFieldValue(this.getModel(element), fd, value);
		return true;
	}
	
	public Object getValue(Object element, String property) {
		Object o = this.getModel(element);
		String v = ReflectUtils.getInstance().getFieldValue(o, property);
		if(v == null) {
			v = "";
		}
		return v;
	}
	
	public boolean canModify(Object element, String property,List<FieldDef> fds) {
		if(element == null || property == null) {
			return false;
		}
		for(FieldDef fd: fds) {
			if(fd.getFieldName().equals(property)) {
				if(fd.isIdable() || fd.isHide()) {
					return false;
				} else {
					return fd.isEditable();
				}
			}
		}
		return false;
	}
	
	public  CellEditor createCellEditor(Composite parent,FieldDef fd){
		CellEditor editor = null;
		if (fd.isHide() || fd.isHideInRow()) {
			return null;
		}
		if (fd.isIdable() || !fd.isEditable()) {
			return null;
		}
		UIType uiType = fd.getUiType();
		if (UIType.Radiobox.equals(uiType)) {
			editor = new MapComboBoxCellEditor(parent, Utils.getInstance().getMapKeyValues(fd));
		} else if (UIType.Text.equals(uiType)) {
			editor = new TextCellEditor(parent);
		} else if (UIType.Password.equals(uiType)) {
			editor = new TextCellEditor(parent, SWT.PASSWORD);
		} else if (UIType.Checkbox.equals(uiType)) {
			editor = new CheckboxCellEditor(parent);
		} else if (UIType.Email.equals(uiType)) {
			editor = new TextCellEditor(parent);
		} else if (UIType.List.equals(uiType)) {
			editor = new ListDialogCellEditor(parent,fd);
		} else if (UIType.Combo.equals(uiType)) {
			editor = new MapComboBoxCellEditor(parent, Utils.getInstance().getMapKeyValues(fd));
		} else if (UIType.Table.equals(uiType)) {
			editor = new TextCellEditor(parent);
		} else if (UIType.Tree.equals(uiType)) {
			editor = new TreeDialogCellEditor(parent, fd);
		} else if (UIType.Date.equals(uiType)) {
			editor = new TextCellEditor(parent);
		} else if (UIType.Time.equals(uiType)) {
			editor = new TextCellEditor(parent);
		} else {
			throw new IllegalArgumentException(uiType.name()
					+ " is not support");
		}
		return editor;
	}
	
	private FieldDef getFieldDef(String fieldName,List<FieldDef> fds) {
		for(FieldDef fd: fds) {
			if(fd.getFieldName().equals(fieldName)) {
				return fd;
			}
		}
		return null;
	}
	
	
	private Object getModel(Object elt) {
		if(elt == null) {
			throw new NullPointerException();
		}
		if(elt instanceof TableItem) {
			TableItem ti = (TableItem)elt;
			elt = ti.getData();
		}else if(elt instanceof TreeItem) {
			TreeItem ti = (TreeItem)elt;
			elt = ti.getData();
		}
		if(elt instanceof PropertySourceModel) {
			return ((PropertySourceModel)elt).getModel();
		}
		return elt;
	}
	
	public boolean refreshList(Shell shell, FieldDef fd) {
		String clsName = fd.getDefaultValueProviderCls();
		if( clsName == null  || clsName.trim().equals("")) {
			clsName = fd.getKeyValuesProviderCls();
		}
		
		if( clsName == null  || clsName.trim().equals("")) {
			clsName = fd.getTreeRootsProviderCls();
		}
		
		if( clsName == null  || clsName.trim().equals("")) {
			UIUtils.getInstance().showNodeDialog(shell, "ValueProviderNotFound", fd.getName());
			return false;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("clsName", clsName);
		DataDto resp = WSConn.ins().call(FieldDef.VALUE_PROVIDER_URL,"GET", params);
		if (resp.isSuccess()) {
			boolean f = false;
			if(clsName.trim().equals(fd.getKeyValuesProviderCls())) {
				Map<String,String> values = JsonUtils.getInstance().getStringMap(resp.getData(), true);
				fd.setKeyValues(values);
				f = true;
			}else if(clsName.trim().equals(fd.getTreeRootsProviderCls())) {
				Type type = new TypeToken<List<TreeNode>>(){}.getType();
				List<TreeNode> values = JsonUtils.getInstance().fromJson(resp.getData(), type, false, true);
				fd.setTreeRoots(values);
				f = true;
			}else if(clsName.trim().equals(fd.getDefaultValueProviderCls())) {
				Map<String,String> values = JsonUtils.getInstance().getStringMap(resp.getData(), true);
				fd.setDefaultValue(values);
				f = true;
			}else {
				UIUtils.getInstance().showNodeDialog(shell,resp.getMsg());
			}
			return f;
		} else {
			UIUtils.getInstance().showNodeDialog(shell,resp.getMsg());
			return false;
		}
	}
}
