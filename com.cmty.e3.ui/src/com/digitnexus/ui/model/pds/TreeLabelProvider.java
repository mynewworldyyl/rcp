package com.digitnexus.ui.model.pds;

import org.eclipse.jface.viewers.LabelProvider;

import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.utils.Utils;

public class TreeLabelProvider  extends LabelProvider{

    private FieldDef fd = null;
	
	public TreeLabelProvider(FieldDef fd) {
		this.fd = fd;
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
			String[] labels = Utils.getInstance().getTreeLabels(fd, items);
			if(labels != null && labels.length > 0) {
				for(String it : labels) {
					sb.append(it).append(",");
				}
			}
		}else {
			sb.append(key);
		}
		sb.replace(sb.length()-1, sb.length(), "");
		return sb.toString();
	}
	
}
