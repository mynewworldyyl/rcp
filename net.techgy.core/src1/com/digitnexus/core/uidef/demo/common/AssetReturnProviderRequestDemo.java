package com.digitnexus.core.uidef.demo.common;

import java.util.List;

import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.NamedParam;
import com.digitnexus.core.uidef.annotation.TableViewEditor;
import com.digitnexus.core.uidef.annotation.ListItems;
import com.digitnexus.core.uidef.demo.TestKeyValueProvider;
import com.digitnexus.core.uidef.demo.TestTreeNodeProvider;

/*@TableViewEditor(
		queryConditions = {
				@ItemField(name="reqNum",dataType=DataType.String,order=1),
				@ItemField(name="vendor",dataType=DataType.String,order=2),
				@ItemField(name="createdPerson",dataType=DataType.String,order=3),
				@ItemField(name="sourceSite",dataType=DataType.String,order=4),
				@ItemField(name="end",dataType=DataType.Boolean,uiType=UIType.Checkbox,order=5),
				@ItemField(order=6,name="listValues",dataType=DataType.String,uiType=UIType.Combo,valueProvider=TestKeyValueProvider.class),
				@ItemField(order=7,name="radioValues",dataType=DataType.String,uiType=UIType.Radiobox,valueProvider=TestKeyValueProvider.class),
				@ItemField(order=8,name="Dept",dataType=DataType.String,uiType=UIType.Tree,valueProvider=TestTreeNodeProvider.class),
				@ItemField(order=9,name="createdDate",dataType=DataType.String,uiType=UIType.Date),
				@ItemField(order=10,name="createdTime",dataType=DataType.String,uiType=UIType.Time),		
		},
		
		actions = {
				@Action(name="Create", url="requestDemo/create",actionType=ActionType.Create),
				@Action(name="Delete", url="requestDemo/delete" ,actionType=ActionType.Delete),
				@Action(name="Update", url="requestDemo/update",actionType=ActionType.Modify),
				@Action(name="Query", url="requestDemo/query",actionType=ActionType.Query),
				@Action(name="Export", url="requestDemo/export",actionType=ActionType.Export),
				@Action(name="Detail", url="requestDemo/detail",actionType=ActionType.Detail),
				//@Action(name="Ext1", url="requestDemo/ext1",actionType=ActionType.Ext),
				//@Action(name="Ext2", url="requestDemo/ext2",actionType=ActionType.Ext)
		},
		
		itemActions = {
				//save request
				@Action(name="save", url="requestDemo/save",actionType=ActionType.Save),
				//add item to this request
				@Action(name="Add", url="requestDemo/add",actionType=ActionType.Add),
				//Cancel this request for this update or create
				@Action(name="cancel", url="requestDemo/cancel" ,actionType=ActionType.Cancel),
		},
		namedParams = {
			@NamedParam(name=UIConstants.REQ_GET_COMPONENT_PATH,value="requestDemo"),
			@NamedParam(name=UIConstants.REQ_GET_METHOD_PATH,value="/getreq"),
			@NamedParam(name=UIConstants.REQ_GET_METHOD,value="GET")
		}
	)*/
public class AssetReturnProviderRequestDemo{

	@ItemField(name="Request Num",uiType=UIType.Text,order=1,isIdable=true,lengthByChar=20)	
	private String reqNum;
	
	@ItemField(name="Created Person",uiType=UIType.Text,order=4,lengthByChar=20)
	private String createdPerson;
	
	@ItemField(name="Source Site",uiType=UIType.Text,order=3,lengthByChar=12)
	private String sourceSite;
	
	@ItemField(name="Target Site",uiType=UIType.Text,order=5,lengthByChar=10)
	private String targetSite;
	
	@ItemField(name="Check Person",uiType=UIType.Text,order=2,lengthByChar=10)
	private String checkPerson;
	
	@ItemField(name="End",uiType=UIType.Checkbox,order=6,lengthByChar=10)
	private Boolean end;
	
	@ListItems
	private List<RequestItemDemo> items = null;

	public String getReqNum() {
		return reqNum;
	}

	public void setReqNum(String reqNum) {
		this.reqNum = reqNum;
	}

	public String getCreatedPerson() {
		return createdPerson;
	}

	public void setCreatedPerson(String createdPerson) {
		this.createdPerson = createdPerson;
	}

	public String getSourceSite() {
		return sourceSite;
	}

	public void setSourceSite(String sourceSite) {
		this.sourceSite = sourceSite;
	}

	public String getTargetSite() {
		return targetSite;
	}

	public void setTargetSite(String targetSite) {
		this.targetSite = targetSite;
	}

	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	public List<RequestItemDemo> getItems() {
		return items;
	}

	public void setItems(List<RequestItemDemo> items) {
		this.items = items;
	}

	@Override
	public int hashCode() {
		if(this.reqNum == null) {
			return 0;
		}
		return this.reqNum.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}

	public Boolean getEnd() {
		return end;
	}

	public void setEnd(Boolean end) {
		this.end = end;
	}
	
	
}
