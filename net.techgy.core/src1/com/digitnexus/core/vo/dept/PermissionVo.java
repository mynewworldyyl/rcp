package com.digitnexus.core.vo.dept;

import java.util.ArrayList;
import java.util.List;

import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.permisson.Permission;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.Children;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.NamedParam;
import com.digitnexus.core.uidef.annotation.Parent;
import com.digitnexus.core.uidef.annotation.TreeViewEditor;
import com.digitnexus.core.vo.AbstractVo;

@TreeViewEditor(
		name="PermVo",
		entityCls=Permission.class,
		notNeedPerm=true,
		queryConditions = {
				@ItemField(name="name",dataType=DataType.String,order=1),		
		},
		
		actions = {
				@Action(name="Create", url="detpEmpService/createDept",actionType=ActionType.Create,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Delete", url="detpEmpService/deleteDept" ,actionType=ActionType.Delete,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Modify", url="detpEmpService/updateDept",actionType=ActionType.Modify,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				
				@Action(name="Query", url="detpEmpService/query",actionType=ActionType.Query,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				
				//@Action(name="Ext1", url="requestDemo/ext1",actionType=ActionType.Ext),
				//@Action(name="Ext2", url="requestDemo/ext2",actionType=ActionType.Ext)
		},
		namedParams = {
			@NamedParam(name=UIConstants.REQ_GET_COMPONENT_PATH,value="detpService"),
			@NamedParam(name=UIConstants.REQ_GET_METHOD_PATH,value="/getDept"),
			@NamedParam(name=UIConstants.REQ_GET_METHOD,value="GET")
		}
	)
public class PermissionVo  extends AbstractVo{

	public static final String ENTITY_NODE_TYPE = "com.digitnexus.entity.node.EntityType";
	
	@ItemField(name="DeptName",uiType=UIType.Text,order=1,isIdable=false,lengthByChar=20)	
	private String name;
	
	@ItemField(name="DeptName",uiType=UIType.Text,order=1,isIdable=false,lengthByChar=20)
	private String desc;
	
	@ItemField(name="DeptName",uiType=UIType.Text,order=1,isIdable=false,lengthByChar=20)
	private String code;
	
	@Parent
	private PermissionVo parent = null;
		
	@Children
	private List<PermissionVo> subs = new ArrayList<PermissionVo>();

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public PermissionVo getParent() {
		return parent;
	}

	public void setParent(PermissionVo parent) {
		this.parent = parent;
	}

	public List<PermissionVo> getSubs() {
		return subs;
	}

	public void setSubs(List<PermissionVo> subs) {
		this.subs = subs;
	}
}
