package com.digitnexus.core.uidef.demo.report;

import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.TableViewEditor;
import com.digitnexus.core.uidef.demo.TestKeyValueProvider;
import com.digitnexus.core.uidef.demo.TestTreeNodeProvider;

/*@TableViewEditor(
		queryConditions = {
				@ItemField(name="reqNum",dataType=DataType.String,order=1),
				@ItemField(name="vendor",dataType=DataType.String,order=2),
				@ItemField(name="createdPerson",dataType=DataType.String,order=3),
				@ItemField(name="sourceSite",dataType=DataType.String,order=4),
				@ItemField(order=6,name="listValues",dataType=DataType.String,uiType=UIType.Combo,valueProvider=TestKeyValueProvider.class),
				@ItemField(order=7,name="radioValues",dataType=DataType.String,uiType=UIType.Radiobox,valueProvider=TestKeyValueProvider.class),
				@ItemField(order=8,name="Dept",dataType=DataType.String,uiType=UIType.Tree,valueProvider=TestTreeNodeProvider.class),
				@ItemField(order=9,name="createdDate",dataType=DataType.String,uiType=UIType.Date),
				@ItemField(order=10,name="createdTime",dataType=DataType.String,uiType=UIType.Time),		
		},
		
		actions = {
				@Action(name="Query", url="requestDemo/query",actionType=ActionType.Query),
				@Action(name="Export", url="requestDemo/export",actionType=ActionType.Export),
		}
	)*/
public class AssetInReport{

	@ItemField(name="ID", order=1,lengthByChar=8)
	private String itemId;
	
	@ItemField(name="Name", order=2,lengthByChar=20)
	private String name;
	
	@ItemField(name="Project Name",order=3,lengthByChar=20)
	private String projectName;
	
	@ItemField(name="Vendor Name",order=5,lengthByChar=20)
	private String vendorName;
	
	@ItemField(name="Type",order=8,lengthByChar=15)
	private String assetType;

	@ItemField(name="Qualitity",order=9,lengthByChar=12)
	private float assetQualitity;
	
	@ItemField(name="Model Type",order=10,lengthByChar=12)
	private float modelType;
	
	@ItemField(name="Input Time",order=11,lengthByChar=12)
	private float inputTime;
	
	@ItemField(name="Unit Price",order=12,lengthByChar=12)
	private float unitPrice;
	
		
}
