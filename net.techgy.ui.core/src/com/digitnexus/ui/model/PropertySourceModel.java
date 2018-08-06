package com.digitnexus.ui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import net.techgy.ui.core.utils.UIUtils;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.utils.ReflectUtils;
import com.digitnexus.ui.celleditor.CellEditorHelper;

public class PropertySourceModel implements /*IPropertySource,*/ Comparable<Object>{

	private PropertyChangeSupport listener = null;
	private Object model = null;
	
	private List<FieldDef> fieldDefs;
	
	private boolean modifiable = false;
	
	private String validNodeType = "";
	
	public PropertySourceModel(Object obj,List<FieldDef> fieldDefs,boolean modifiable,String validNodeType) {
		if(obj == null) {
			throw new NullPointerException("PropertySourceModel source cannot be null");
		}else if(fieldDefs == null || fieldDefs.size() < 1) {
			throw new NullPointerException("FieldDef list cannot be null");
		}
		this.validNodeType = validNodeType;
		this.model = obj;
		this.fieldDefs = fieldDefs;
		listener = new PropertyChangeSupport(this);
		this.modifiable = modifiable ? isValidEditNode() : false;
	}

	public Object getEditableValue() {
		if(!this.modifiable) {
			return null;
		}
		return this;
	}
	
	public boolean isValidEditNode() {
		if(validNodeType != null) {
			String nodeType = (String) UIUtils.getInstance()
					.getFieldValue(this.model,UIConstants.ModelProperty.NodeType.getFieldName());
			return validNodeType.equals(nodeType);
		}
		return true;
	}

	public boolean canModify(String property) {
		if(!this.modifiable) {
			return false;
		}
		return CellEditorHelper.getInstance().canModify(this, property, this.fieldDefs);
	}
	
	public Object getPropertyValue(Object id) {
		return ReflectUtils.getInstance().getFieldValue(this.model, id.toString());
	}
	
	/*public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> pdls = new ArrayList<IPropertyDescriptor>();

		for (FieldDef fd : fieldDefs) {
			if (fd.isHide()) {
				continue;
			}
			if (fd.isIdable() || !fd.isEditable()) {
				pdls.add(new PropertyDescriptor(fd.getFieldName(), fd.getName() ));
				continue;
			}
			String label = I18NUtils.getInstance().getString(fd.getName());
			IPropertyDescriptor editor = null;
			UIType uiType = fd.getUiType();
			if (UIType.Radiobox.equals(uiType)) {
				if(this.modifiable) {
					editor = new PropertyDescriptor(fd.getFieldName(), label);
				}else {
					editor = new PropertyDescriptor(fd.getFieldName(), label);
				}
			} else if (UIType.Text.equals(uiType)) {
				if(this.modifiable) {
					editor = new TextPropertyDescriptor(fd.getFieldName(), label);
				}else {
					editor = new PropertyDescriptor(fd.getFieldName(), label);
				}
			} else if (UIType.Password.equals(uiType)) {
				if(this.modifiable) {
					editor = new TextPropertyDescriptor(fd.getFieldName(), label);
				}else {
					editor = new PropertyDescriptor(fd.getFieldName(), label);
				}
			} else if (UIType.Checkbox.equals(uiType)) {
				if(this.modifiable) {
					editor = new PropertyDescriptor(fd.getFieldName(), label);
				}else {
					editor = new PropertyDescriptor(fd.getFieldName(), label);
				}
			} else if (UIType.Email.equals(uiType)) {
				if(this.modifiable) {
					editor = new TextPropertyDescriptor(fd.getFieldName(), label);
				}else {
					editor = new PropertyDescriptor(fd.getFieldName(), label);
				}
			} else if (UIType.List.equals(uiType)) {
				if(this.modifiable) {
					editor = new CommonPropertyDescriptor(fd.getFieldName(), label,fd);
				}else {
					editor = new PropertyDescriptor(fd.getFieldName(), label);
				}
			} else if (UIType.Combo.equals(uiType)) {
				if(this.modifiable) {
					editor = new CustomComboBoxPropertyDescriptor(fd.getFieldName(), 
							label,fd);
				}else {
					editor = new PropertyDescriptor(fd.getFieldName(), label);
				}
			} else if (UIType.Table.equals(uiType)) {
				if(this.modifiable) {
					editor = new TextPropertyDescriptor(fd.getFieldName(), label);
				}else {
					editor = new PropertyDescriptor(fd.getFieldName(), label);
				}
			} else if (UIType.Tree.equals(uiType)) {
				if(this.modifiable) {
					editor = new CommonPropertyDescriptor(fd.getFieldName(), 
							label,fd);
				}else {
					editor = new PropertyDescriptor(fd.getFieldName(), label);
				}
			} else if (UIType.Date.equals(uiType)) {
				if(this.modifiable) {
					editor = new TextPropertyDescriptor(fd.getFieldName(), label);
				}else {
					editor = new PropertyDescriptor(fd.getFieldName(), label);
				}
			} else if (UIType.Time.equals(uiType)) {
				if(this.modifiable) {
					editor = new TextPropertyDescriptor(fd.getFieldName(), label);
				}else {
					editor = new PropertyDescriptor(fd.getFieldName(), label);
				}
			} else {
				throw new IllegalArgumentException(uiType.name()
						+ " is not support");
			}
			pdls.add(editor);
		}
	
		IPropertyDescriptor[] pds = new IPropertyDescriptor[pdls.size()];
		return pdls.toArray(pds);
	}

	
	

	@Override
	public boolean isPropertySet(Object id) {
		for(FieldDef fd: this.fieldDefs) {
			if(fd.isHide()) {
				return false;
			}
			if(fd.getName().equals(id)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		if(!this.modifiable) {
			return;
		}
	}

	*/
	public void setPropertyValue(Object id, Object value) {
		if(!this.modifiable) {
			return;
		}
		if(CellEditorHelper.getInstance().modify(this, id.toString(), value, this.fieldDefs)) {
			this.firePropertyChange(id.toString(), "", value);
		}
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.model.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof PropertySourceModel) {
			return this.model.equals(((PropertySourceModel)obj).getModel());
		}else {
			return false;
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return  this.model.toString();
	}

	public Object getModel() {
		return model;
	}
	
	@Override
	public int compareTo(Object o) {
		if((this.model instanceof Comparable) && (o instanceof Comparable)) {
			return ((Comparable)this.model).compareTo(o);
		}
		throw new CommonException("NotComparable");
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener)  {
		if(listener != null) { 
			this.listener.addPropertyChangeListener(listener);
		}
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener)  {
		if(listener != null) { 
			this.listener.removePropertyChangeListener(listener);
		}
	}
	
	 public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		 this.listener.firePropertyChange(propertyName, oldValue, newValue);
	 }
}
