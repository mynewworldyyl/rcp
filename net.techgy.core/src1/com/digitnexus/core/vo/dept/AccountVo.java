package com.digitnexus.core.vo.dept;

import java.util.Set;

import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.account.Account;
import com.digitnexus.core.dept.ClientKeyValueProvider;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.dept.DefaultClientValueProvider;
import com.digitnexus.core.dept.EmpKeyValueProvider;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.TableViewEditor;
import com.digitnexus.core.vo.AbstractVo;

@TableViewEditor(
		name="accountEditor",
		entityCls=Account.class,
		queryConditions = {
				@ItemField(name="name",dataType=DataType.String,order=1),		
		},
		actions = {
			    @Action(name="Add", url="accountService/add",actionType=ActionType.Add,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Delete", url="accountService/delete" ,actionType=ActionType.Delete,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Query", url="accountService/query",actionType=ActionType.Query,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Save", url="accountService/save",actionType=ActionType.Save,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Modify", url="accountService/update",actionType=ActionType.Modify,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
		}
)
public class AccountVo  extends AbstractVo{

	@ItemField(name="Name",uiType=UIType.Text,order=2,lengthByChar=16)	
	private String name;
	
	@ItemField(name="Password",uiType=UIType.Password,order=2,lengthByChar=10)	
	private String pw;
	
	@ItemField(name="statu",uiType=UIType.Combo,order=3,lengthByChar=8,
			availables={Account.STATU_ACTIVE,Account.STATU_DISABLE})
	private String statu = Account.STATU_ACTIVE;
	
	@ItemField(name="Employee",uiType=UIType.Tree,order=4,lengthByChar=15,
			valueProvider=EmpKeyValueProvider.class,validatedNodeType = EmployeeVo.class)
	private String employee;
	
	@ItemField(name="id",order = 9,hide=true,editable=false)
	private String typeCode = Account.TYPE_COMMON;
	
	@ItemField(name="Client",uiType=UIType.Text,order=6,lengthByChar=15,editable=false, 
			defaultValueProvider=DefaultClientValueProvider.class)
	private String defaultClient;

	@ItemField(name="RelatedClients",uiType=UIType.List,order=7,lengthByChar=15,
			valueProvider=ClientKeyValueProvider.class)
	private Set<String> relatedClients;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getDefaultClient() {
		return defaultClient;
	}

	public void setDefaultClient(String defaultClient) {
		this.defaultClient = defaultClient;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public Set<String> getRelatedClients() {
		return relatedClients;
	}

	public void setRelatedClients(Set<String> relatedClients) {
		this.relatedClients = relatedClients;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

}
