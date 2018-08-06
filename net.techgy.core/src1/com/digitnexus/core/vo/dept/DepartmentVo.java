package com.digitnexus.core.vo.dept;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.dept.Department;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.Children;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.NamedParam;
import com.digitnexus.core.uidef.annotation.Parent;
import com.digitnexus.core.uidef.annotation.TreeViewEditor;
import com.digitnexus.core.vo.AbstractVo;

@TreeViewEditor(
		name="Department",
		entityCls=Department.class,
		queryConditions = {
				@ItemField(name="deptName",dataType=DataType.String,order=1),
				@ItemField(name="desc",dataType=DataType.String,order=2),
		},
		
		actions = {
				@Action(name="Add", url="crudService/save",actionType=ActionType.Add,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Delete", url="crudService/delete" ,actionType=ActionType.Delete,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Modify", url="crudService/update",actionType=ActionType.Modify,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Query", url="crudService/query",actionType=ActionType.Query,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Save", url="crudService/save",actionType=ActionType.Save,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="setAsFactory", url="deptService/setAsFactory",actionType=ActionType.Ext,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Headquarter}),
		},
		namedParams = {
			@NamedParam(name=UIConstants.UPDATE_PATH,value="crudService/update"),
			@NamedParam(name=UIConstants.UPDATE__METHOD,value="GET"),
			@NamedParam(name="com.digitnexus.core.uidef.vo.dept.DepartmentVo-Font",value=",,1"),
		}
	)
public class DepartmentVo  extends AbstractVo implements Serializable, Comparable<DepartmentVo>{

	@ItemField(name="DeptName",uiType=UIType.Text,order=1,lengthByChar=20)	
	private String name;
	
	@ItemField(name="Descripton",uiType=UIType.Text,order=1,lengthByChar=20)
	private String desc;
	
	@Parent
	private String parentId = null;
	
	@ItemField(name="RelatedSystemId",order=7, hide=true)
	private String relatedClientId = null;
	
	@ItemField(name="RelatedSystem",order=8,uiType=UIType.Text,editable=false)
	private String relatedClientName = null;
		
	@Children
	private List<DepartmentVo> subs = new ArrayList<DepartmentVo>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<DepartmentVo> getSubs() {
		return subs;
	}

	public void setSubs(List<DepartmentVo> subs) {
		this.subs = subs;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public int compareTo(DepartmentVo o) {
		return this.name.compareTo(o.name);
	}

	public String getRelatedClientId() {
		return relatedClientId;
	}

	public void setRelatedClientId(String relatedClientId) {
		this.relatedClientId = relatedClientId;
	}

	public String getRelatedClientName() {
		return relatedClientName;
	}

	public void setRelatedClientName(String relatedClientName) {
		this.relatedClientName = relatedClientName;
	}
}
