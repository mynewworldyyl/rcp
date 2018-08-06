package com.digitnexus.core.vo.masterdata;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.dept.ClientKeyValueProvider;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.dept.DefaultClientValueProvider;
import com.digitnexus.core.dept.EmpKeyValueProvider;
import com.digitnexus.core.masterdata.Project;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.TableViewEditor;
import com.digitnexus.core.vo.AbstractVo;
import com.digitnexus.core.vo.dept.EmployeeVo;

@TableViewEditor(
	    entityCls=Project.class,
		queryConditions = {
				@ItemField(name="name",dataType=DataType.String,order=1),		
		},
		actions = {
		    @Action(name="Add", url="crudService/add",actionType=ActionType.Add,
		    		permClientTypes={ClientType.Admin,ClientType.Headquarter}),
			@Action(name="Delete", url="crudService/delete" ,actionType=ActionType.Delete,
		    		permClientTypes={ClientType.Admin,ClientType.Headquarter,ClientType.Region}),
			@Action(name="Query", url="crudService/query",actionType=ActionType.Query,
		    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
		    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
			@Action(name="Save", url="crudService/save",actionType=ActionType.Save,
		    		permClientTypes={ClientType.Admin,ClientType.Headquarter}),
			@Action(name="Modify", url="crudService/update",actionType=ActionType.Modify,
		    		permClientTypes={ClientType.Admin,ClientType.Headquarter})
	}
)
public class ProjectVo extends AbstractVo implements Serializable, Comparable<ProjectVo>{

	//项目名称
	@ItemField(name="Name",order=2,lengthByChar=16)	
	private String name="";
	
    //编号
	@ItemField(name="code",order=3,lengthByChar=6)	
	private String code;
	
	//状态
	@ItemField(name="statu",uiType=UIType.Combo,order=8,lengthByChar=8,
			availables={Project.STATE_ONGOING})
	private String statu = Project.STATE_ONGOING;
	
	//创建时间
	@ItemField(name="startOn",uiType=UIType.Date,order=9,lengthByChar=12,hideInRow=true)
	private Date startOn;
	
    //创建组织
	/*@ItemField(name="createDept",order=3,lengthByChar=16,editable=false)	
	private String createDept;*/
	
	//交底单位
	@ItemField(name="factoryIds",uiType=UIType.List,order=7,lengthByChar=15,
			valueProvider=ClientKeyValueProvider.class,hideInRow=true)
    private Set<String> factoryIds = new HashSet<String>();
	
	//共享大区
	@ItemField(name="areas",uiType=UIType.List,order=7,lengthByChar=15,
			valueProvider=ClientKeyValueProvider.class,hideInRow=true)
    private Set<String> areas = new HashSet<String>();
	
	//前缀
	@ItemField(name="prefix",order=3,lengthByChar=8,hideInRow=true)	
	private String prefix;
	
	//责任人
	@ItemField(name="respPerson",order=3,lengthByChar=16)	
	private String respPerson;

	//甲方
	@ItemField(name="partyA",order=3,lengthByChar=16)	
	private String partyA;
	
	//建设地点
	@ItemField(name="buildAddr",order=3,lengthByChar=16,hideInRow=true)
	private String buildAddr;
	
	//工程规模
	@ItemField(name="scale",order=3,lengthByChar=16,hideInRow=true)
	private String scale;

	//结构类型
	@ItemField(name="frameworkType",order=3,lengthByChar=16,hideInRow=true)
	private String frameworkType;
	
	//跨度
	@ItemField(name="span",order=3,lengthByChar=16,hideInRow=true)
	private String span;

	//承包方式
	@ItemField(name="contractMode",order=3,lengthByChar=16,hideInRow=true)
	private String contractMode;

	//合同工期
	@ItemField(name="period",order=3,lengthByChar=10)
	private String period;
	
	//合同总价
	@ItemField(name="contractPrice",order=3,lengthByChar=10)
	private double contractPrice;
	
    //中标价格
	@ItemField(name="bidPrice",order=3,lengthByChar=10)
	private double bidPrice;
	
	//备注
	@ItemField(name="others",order=3,lengthByChar=16,hideInRow=true)
	private String others;
		
	//交底内容
	@ItemField(name="desc",order=3,lengthByChar=16,hideInRow=true)
    private String desc;

	@ItemField(name="contacts",order=3,lengthByChar=16,hideInRow=true)
	private String contacts;

	@ItemField(name="ClientName",uiType=UIType.Text,order=30,lengthByChar=20,
			editable=false,hideInRow=true)	
	private String clientName;
	
	@ItemField(name="adminAccountName",uiType=UIType.Text,order=80,lengthByChar=16)
	private String adminAccountName;
	
	@ItemField(name="pw",uiType=UIType.Text,order=81,lengthByChar=6)
	private String pw;
	
	@Override
	public int compareTo(ProjectVo o) {
		return this.name.compareTo(o.name);
	}

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

	public Date getStartOn() {
		return startOn;
	}

	public void setStartOn(Date startOn) {
		this.startOn = startOn;
	}

	public Set<String> getFactoryIds() {
		return factoryIds;
	}

	public void setFactoryIds(Set<String> factoryIds) {
		this.factoryIds = factoryIds;
	}

	public Set<String> getAreas() {
		return areas;
	}

	public void setAreas(Set<String> areas) {
		this.areas = areas;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	public String getRespPerson() {
		return respPerson;
	}

	public void setRespPerson(String respPerson) {
		this.respPerson = respPerson;
	}

	public String getPartyA() {
		return partyA;
	}

	public void setPartyA(String partyA) {
		this.partyA = partyA;
	}

	public String getBuildAddr() {
		return buildAddr;
	}

	public void setBuildAddr(String buildAddr) {
		this.buildAddr = buildAddr;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getFrameworkType() {
		return frameworkType;
	}

	public void setFrameworkType(String frameworkType) {
		this.frameworkType = frameworkType;
	}

	public String getSpan() {
		return span;
	}

	public void setSpan(String span) {
		this.span = span;
	}

	public String getContractMode() {
		return contractMode;
	}

	public void setContractMode(String contractMode) {
		this.contractMode = contractMode;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public double getContractPrice() {
		return contractPrice;
	}

	public void setContractPrice(double contractPrice) {
		this.contractPrice = contractPrice;
	}

	public double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getAdminAccountName() {
		return adminAccountName;
	}

	public void setAdminAccountName(String adminAccountName) {
		this.adminAccountName = adminAccountName;
	}
}
