package com.digitnexus.core.vo;

import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.TableViewEditor;
import com.digitnexus.core.vo.AbstractVo;

@TableViewEditor(
		queryConditions = {
				//@ItemField(name="name",dataType=DataType.String,order=1),		
		},
		actions ={
				@Action(name="Query", url="crudService/query",actionType=ActionType.Query,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory})
		}
)
public class ReportVo  extends AbstractVo{
	
}
