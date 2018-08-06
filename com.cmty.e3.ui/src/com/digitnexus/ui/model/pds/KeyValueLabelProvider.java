package com.digitnexus.ui.model.pds;

import java.util.Map;

import org.eclipse.jface.viewers.LabelProvider;

import com.digitnexus.base.utils.Utils;

public class KeyValueLabelProvider  extends LabelProvider{

private Map<String,String> keyValues = null;
	
	public KeyValueLabelProvider(Map<String,String> keyValues) {
		this.keyValues = keyValues;
	}

	@Override
	public String getText(Object element) {
		if(element == null) {
			return "";
		}
		String key = element.toString();
		if("".equals(key.trim())) {
			return "";
		}
		String[] items = Utils.getInstance().getCollectionValues(element);
		StringBuffer sb = new StringBuffer();
		if(items != null && items.length > 0) {
			for(String it : items) {
				sb.append(keyValues.get(it)).append(",");
			}
		}else {
			sb.append(key);
		}
		sb.replace(sb.length()-1, sb.length(), "");
		return sb.toString();
	}
	
}
