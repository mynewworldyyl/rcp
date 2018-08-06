package com.digitnexus.core.vo.masterdata;

import java.io.Serializable;

import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.masterdata.CommonValue;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.TableViewEditor;
import com.digitnexus.core.vo.AbstractVo;

@TableViewEditor(
		name="ConstantEditor",
		entityCls=CommonValue.class,
		queryConditions = {
				@ItemField(name="name",dataType=DataType.String,order=1),		
		},
		actions = {
			    @Action(name="Add", url="crudService/add",actionType=ActionType.Add,
			    		permClientTypes={ClientType.Admin,ClientType.Headquarter}),
				@Action(name="Delete", url="crudService/delete" ,actionType=ActionType.Delete,
			    		permClientTypes={ClientType.Admin,ClientType.Headquarter}),
				@Action(name="Query", url="crudService/query",actionType=ActionType.Query,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Save", url="crudService/save",actionType=ActionType.Save,
			    		permClientTypes={ClientType.Admin,ClientType.Headquarter}),
				@Action(name="Modify", url="crudService/update",actionType=ActionType.Modify,
			    		permClientTypes={ClientType.Admin,ClientType.Headquarter})
		}
)
public class ConstantVo   extends AbstractVo  implements Serializable, Comparable<ConstantVo>{

	@ItemField(name="Name",uiType=UIType.Text,order=2,lengthByChar=16)	
	private String name="";
	
	@ItemField(name="Code",uiType=UIType.Text,order=3,lengthByChar=10)	
	private String code;
	
	@ItemField(name="Value",uiType=UIType.Text,order=4,lengthByChar=10)	
	private String value;
	
	@ItemField(name="Unit",uiType=UIType.Combo,order=3,lengthByChar=8,
			availables={"KG","米","公分","厘米","块","条","只","个"})
	private String unit = "KG";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "id: " + this.getId() + ", Name: "+ this.name;
	}

	@Override
	public int compareTo(ConstantVo o) {
		return this.name.compareTo(o.name);
	} 
	
}
