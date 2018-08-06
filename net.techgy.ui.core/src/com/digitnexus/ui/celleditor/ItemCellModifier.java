package com.digitnexus.ui.celleditor;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.ui.model.PropertySourceModel;

public class ItemCellModifier implements ICellModifier{

	private static final ItemCellModifier instance = new ItemCellModifier();
	private ItemCellModifier(){}
	public static ItemCellModifier getInstance() {
		return instance;
	}
	
	@Override
	public boolean canModify(Object element, String property) {
		PropertySourceModel pm = getPropertySource(element);
		if(pm == null) {
			throw new CommonException("SystemError");
		}
		return pm.canModify(property);
	}

	@Override
	public Object getValue(Object element, String property) {
		PropertySourceModel pm = getPropertySource(element);
		if(pm == null) {
			throw new CommonException("SystemError");
		}
		return pm.getPropertyValue(property);
	}

	@Override
	public void modify(Object element, String property, Object value) {
		PropertySourceModel pm = getPropertySource(element);
		if(pm == null) {
			throw new CommonException("SystemError");
		}
		pm.setPropertyValue(property, value);
	}

	private PropertySourceModel getPropertySource(Object elt) {
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
		if(!(elt instanceof PropertySourceModel)) {
			return null;
		}
		return (PropertySourceModel)elt;
	}

	public boolean isValidModel(Object obj) {
		if(obj == null) {
			return false;
		}
		return obj instanceof PropertySourceModel;
	}
}
