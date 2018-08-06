package com.digitnexus.core.vo.dept;

import java.util.HashSet;
import java.util.Set;

import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.account.AccountKeyValueProvider;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.dept.Employee;
import com.digitnexus.core.permisson.Group;
import com.digitnexus.core.permisson.PermKeyValueProvider;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.NamedParam;
import com.digitnexus.core.uidef.annotation.TableViewEditor;
import com.digitnexus.core.vo.AbstractVo;

@TableViewEditor(
		name="groupEditor",
		entityCls=Group.class,
		queryConditions = {
				@ItemField(name="name",dataType=DataType.String,order=1),		
		},
		actions = {
		    @Action(name="Add", url="groupService/add",actionType=ActionType.Add,
		    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
		    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
			@Action(name="Delete", url="groupService/delete" ,actionType=ActionType.Delete,
		    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
		    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
			@Action(name="Query", url="groupService/query",actionType=ActionType.Query,
		    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
		    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
			@Action(name="Save", url="groupService/save",actionType=ActionType.Save,
		    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
		    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
			@Action(name="Modify", url="groupService/update",actionType=ActionType.Modify,
		    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
		    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
	}
)
public class GroupVo   extends AbstractVo{

	@ItemField(name="Name",uiType=UIType.Text,order=2,lengthByChar=16)
	private String name;
	
	@ItemField(name="Description",uiType=UIType.Text,order=2,lengthByChar=16)
	private String description;
	
	@ItemField(name="Typecode",uiType=UIType.Combo,order=3,lengthByChar=8,availables={Group.GROUP_TYPE_COMMON})
	private String typecode = Group.GROUP_TYPE_COMMON;

	@ItemField(name="Account",uiType=UIType.List,order=7,lengthByChar=15,valueProvider=AccountKeyValueProvider.class)
	private Set<String> accounts = new HashSet<String>();

	@ItemField(name="Permission",uiType=UIType.Tree,order=5,lengthByChar=15,
			valueProvider=PermKeyValueProvider.class,validatedNodeType = PermissionVo.class)
	private Set<String> permissions = new HashSet<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	@Override
	public int hashCode() {
		if(this.getId() == null) {
			return "".hashCode();
		}
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof GroupVo)) {
			return false;
		}
		GroupVo v = (GroupVo)obj;
		if(this.getId() == null && v.getId() == null) {
			return true;
		}else {
			if(this.getId() == null || v.getId() == null) {
				return false;
			}
		}
		return ((GroupVo)obj).getId().equals(getId());
	}

	@Override
	public String toString() {
		return "Name: " + this.name + ", Desc: " + this.description;
	}

	public Set<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<String> accounts) {
		this.accounts = accounts;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}
	
	
}
