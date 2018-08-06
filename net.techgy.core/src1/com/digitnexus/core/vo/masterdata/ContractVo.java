package com.digitnexus.core.vo.masterdata;

import java.io.Serializable;

import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.ctrct.Contract;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.TableViewEditor;
import com.digitnexus.core.vo.AbstractVo;

@TableViewEditor(
		name="ContractEditor",
		entityCls=Contract.class,
		queryConditions = {
				@ItemField(name="name",dataType=DataType.String,order=1),		
		},
		actions = {
			    @Action(name="Add", url="crudService/add",actionType=ActionType.Add,
			    		permClientTypes={ClientType.Admin,ClientType.Headquarter,ClientType.Region}),
				@Action(name="Delete", url="crudService/delete" ,actionType=ActionType.Delete,
			    		permClientTypes={ClientType.Admin,ClientType.Headquarter,ClientType.Region}),
				@Action(name="Query", url="crudService/query",actionType=ActionType.Query,
			    		permClientTypes={ClientType.Admin,ClientType.Region,
						ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Save", url="crudService/save",actionType=ActionType.Save,
			    		permClientTypes={ClientType.Admin,ClientType.Headquarter,ClientType.Region}),
				@Action(name="Modify", url="crudService/update",actionType=ActionType.Modify,
			    		permClientTypes={ClientType.Admin,ClientType.Headquarter,ClientType.Region})
		}
)
public class ContractVo  extends AbstractVo   implements Serializable, Comparable<ContractVo>{

	@ItemField(name="Name",uiType=UIType.Text,order=2,lengthByChar=16)	
	private String name="";

	
	
	
	@Override
	public int compareTo(ContractVo o) {
		return name.compareTo(o.name);
	}
}
