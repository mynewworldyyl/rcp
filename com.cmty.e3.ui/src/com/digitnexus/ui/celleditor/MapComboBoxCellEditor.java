package com.digitnexus.ui.celleditor;

import java.util.Map;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.utils.Utils;
/**
 * ComboBoxCellEditor ��String����Ϊ��ʾ��������������Ϊѡ�����
 * CustomComboBoxCellEditor ��MapΪ��������Map������֮����ת����
 * 
 * @author ylye
 *
 */
public class MapComboBoxCellEditor extends ComboBoxCellEditor{

	private Map<String,String> keyValues = null;
	private Map<String,String> valueKeys = null;
	
	public MapComboBoxCellEditor(Composite parent,Map<String,String> keyValues) {
		super(parent,Utils.getInstance().getMapValues(keyValues));
		this.keyValues = keyValues;
		this.valueKeys = Utils.getInstance().exchangeKeyValue(keyValues);
	}
	
	public MapComboBoxCellEditor(Composite parent,String[] items) {
		super(parent,items);
		this.valueKeys = this.keyValues = Utils.getInstance().arrayAsMap(items);
	}

	/**
	 * return the selected value map key
	 */
	@Override
	protected Object doGetValue() {
		String[] items = this.getItems();
		if(items == null || items.length < 1) {
		    return null;
		}
		int index = (Integer)super.doGetValue();
		return this.valueKeys.get(items[index]);
	}
    /**
     * the Map key as value which type is String
     */
	@Override
	protected void doSetValue(Object value) {
		if(value == null || "".equals(value.toString().trim())) {
			return;
		}
		String[] items = this.getItems();
		if(items == null || items.length < 1) {
			return ;
		}
		String v = this.keyValues.get(value.toString());
		int index = -1;
		for(int i = 0 ; i < items.length; i++) {
			if(items[i].equals(v)) {
				index = i;
				break;
			}
		}
		if(index != -1) {
			super.doSetValue(index);
		} else {
			throw new IllegalArgumentException(value.toString());
		}
	}
}
