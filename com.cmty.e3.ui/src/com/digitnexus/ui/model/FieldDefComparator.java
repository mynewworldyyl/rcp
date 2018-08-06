package com.digitnexus.ui.model;

import java.util.Comparator;

import com.digitnexus.base.uidef.FieldDef;

public class FieldDefComparator implements Comparator<FieldDef>{

	@Override
	public int compare(FieldDef o1, FieldDef o2) {
		if(o1 == null && o2 == null) {
			return 0;
		}else if(o1 == null) {
			return -1;
		}else if(o2 == null) {
			return 1;
		}
		int result = o1.getOrder() == o2.getOrder()?0:(o1.getOrder() > o2.getOrder()?1:-1);
		return result;
	}

}
