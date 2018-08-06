package com.digitnexus.core.vo;

import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.vo.masterdata.QualityVo;

public abstract class AbstractVo {

	@ItemField(name="id",isIdable=true,order = 1,hide=true,editable=false)
	private String id="";
	
	@ItemField(name="nodeType",order=-1, hide=true)
	public String nodeType=this.getClass().getName();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		if(id != null) {
			return id.hashCode();
		}else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof AbstractVo)) {
			return false;
		}
		AbstractVo cv = (AbstractVo)obj;
		if(id != null) {
			return id.equals(cv.getId());
		} else {
			return false;
		}
	}
	
	
}
