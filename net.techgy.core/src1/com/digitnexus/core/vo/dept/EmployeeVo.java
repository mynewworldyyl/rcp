package com.digitnexus.core.vo.dept;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.account.Account;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.dept.Employee;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.Children;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.NamedParam;
import com.digitnexus.core.uidef.annotation.Parent;
import com.digitnexus.core.uidef.annotation.TreeViewEditor;
import com.digitnexus.core.vo.AbstractVo;

@TreeViewEditor(
		name="Employee",
		entityCls=Employee.class,		
		queryConditions = {
				@ItemField(name="name",dataType=DataType.String,order=1),		
		},
		
		actions = {
			    @Action(name="Add", url="empService/add",actionType=ActionType.Add,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Delete", url="empService/delete" ,actionType=ActionType.Delete,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Query", url="empService/query",actionType=ActionType.Query,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Save", url="empService/save",actionType=ActionType.Save,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
				@Action(name="Modify", url="empService/update",actionType=ActionType.Modify,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
		},
		namedParams = {
			@NamedParam(name=UIConstants.VALIDATE_PARENT_NODE_TYPE,value="com.digitnexus.core.vo.dept.DepartmentVo"),
			//fontName,fontHeight,fontStyle
			@NamedParam(name="com.digitnexus.core.vo.dept.EmployeeVo-Font",value=",,2"),
			@NamedParam(name="com.digitnexus.core.vo.dept.DepartmentVo-Font",value=",,1"),
		}
)
public class EmployeeVo  extends AbstractVo implements Serializable, Comparable<EmployeeVo>{

	@ItemField(name="Name",order=2,lengthByChar=20)	
	private String name = "";
	
	/*@ItemField(name="DeptName",uiType=UIType.Text,order=4,isIdable=false,lengthByChar=20)
	private String deptName;*/
	
	@ItemField(name="EmpCode",order=5,lengthByChar=20)
	private String code;
	
	@ItemField(name="typecode",uiType=UIType.Combo,order=9,lengthByChar=15,
	availables={/*Employee.TYPE_ADMIN,*/Employee.TYPE_COMMON})
	private String typecode;
	
	@ItemField(name="Statu",uiType=UIType.Combo,order=9,lengthByChar=15,
			availables={Employee.STATU_LEAVE,Employee.STATU_ONBOARD})
	private String statu;
	
	@ItemField(name="onBoardTime",uiType=UIType.Text,order=7,lengthByChar=20)
	private Date onBoardTime;
	
	@Parent
	private String parentDeptId = null;
	
	@Children
	private List<EmployeeVo> subDeptOrEmp = new ArrayList<EmployeeVo>();

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

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getParentDeptId() {
		return parentDeptId;
	}

	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
	}

	public List<EmployeeVo> getSubDeptOrEmp() {
		return subDeptOrEmp;
	}

	public void setSubDeptOrEmp(List<EmployeeVo> subDeptOrEmp) {
		this.subDeptOrEmp = subDeptOrEmp;
	}

	public Date getOnBoardTime() {
		return onBoardTime;
	}

	public void setOnBoardTime(Date onBoardTime) {
		this.onBoardTime = onBoardTime;
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	@Override
	public int compareTo(EmployeeVo o) {
		return (this.getId()+this.name).compareTo(o.name+o.getId());
	}

	@Override
	public int hashCode() {
		return (this.getId()+name).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		EmployeeVo v = (EmployeeVo)obj;
	    return this.getId().equals(v.getId()) && this.name.equals(v.name);
	}

}


