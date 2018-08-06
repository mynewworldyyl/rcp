package com.digitnexus.core.vo.masterdata;

import java.io.Serializable;
import java.util.Date;

import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.masterdata.Vendor;
import com.digitnexus.core.masterdata.VendorTypeProvider;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.TableViewEditor;
import com.digitnexus.core.vo.AbstractVo;

@TableViewEditor(
	    entityCls=Vendor.class,
		queryConditions = {
				@ItemField(name="name",dataType=DataType.String,order=1),		
		},
		actions = {
		    @Action(name="Add", url="crudService/add",actionType=ActionType.Add,
		    		permClientTypes={ClientType.Admin,ClientType.Headquarter}),
			@Action(name="Delete", url="crudService/delete" ,actionType=ActionType.Delete,
		    		permClientTypes={ClientType.Admin,ClientType.Headquarter}),
			@Action(name="Query", url="crudService/query",actionType=ActionType.Query,
		    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
		    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
			@Action(name="Save", url="crudService/save",actionType=ActionType.Save,
		    		permClientTypes={ClientType.Admin,ClientType.Headquarter}),
			@Action(name="Modify", url="crudService/update",actionType=ActionType.Modify,
		    		permClientTypes={ClientType.Admin,ClientType.Headquarter})
	    }
)
public class VendorVo  extends AbstractVo implements Serializable, Comparable<VendorVo>{

	//企业名称
	@ItemField(name="Name",order=2,lengthByChar=16)	
	private String name="";
	
    //编号
	@ItemField(name="code",order=3,lengthByChar=5,editable=false,hideInRow=true)	
	private String code;
	
    //供应商类别
	@ItemField(name="vendorType",uiType=UIType.Combo,order=8,lengthByChar=8,
			valueProvider=VendorTypeProvider.class)
	private String vendorTypeId;
	
	//企业性质
	@ItemField(name="entNature",uiType=UIType.Combo,order=5,lengthByChar=8,
			availables={"国有","外资","港资","合资","民营"})
	private String entNature;
	
	//执照号
	@ItemField(name="license",order=6,lengthByChar=12)
	private String license;
	
	//注册资金
	@ItemField(name="regMoney",order=7,lengthByChar=8,hideInRow=true)
	private float regMoney;

	////注册地址
	@ItemField(name="regAddress",order=8,lengthByChar=12,hideInRow=true)
	private String regAddress;
	
	//注册时间
	@ItemField(name="registedOn",uiType=UIType.Date,order=9,lengthByChar=12,hideInRow=true)
	private Date registedOn;

	//注册电话
	@ItemField(name="registedPhone",order=10,lengthByChar=13,hideInRow=true)
	private String registedPhone;
	
	//经营类型
	@ItemField(name="busineessType",order=11,lengthByChar=13,hideInRow=true)
	private String busineessType;

	//企业法人
	@ItemField(name="entLegalPerson",order=12,lengthByChar=13)
	private String entLegalPerson;

	//传真
	@ItemField(name="fax",order=13,lengthByChar=13,hideInRow=true)
	private String fax;
	
	//联系人
	@ItemField(name="contact",order=14,lengthByChar=13)
	private String contact;

	//固定电话
	@ItemField(name="bphone",order=15,lengthByChar=13,hideInRow=true)
	private String bphone;

	//移动电话
	@ItemField(name="mobile",order=16,lengthByChar=13)
	private String mobile;
	
	//是否合格
	@ItemField(name="isQualified", uiType=UIType.Combo, defaultValue="是",
			order=17,lengthByChar=6, availables={"是","否"})
	private String isQualified;
	
    //级别
	@ItemField(name="level", uiType=UIType.Combo, defaultValue="A",
			order=18,lengthByChar=6, availables={"A","B","C","D","E","F"})
	private String level;
	
	//实力及荣誉
	@ItemField(name="desc",order=19,lengthByChar=13,hideInRow=true)
    private String desc;
	
	//备注
	@ItemField(name="remark",order=20,lengthByChar=13,hideInRow=true)
	private String remark;


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


	public String getVendorTypeId() {
		return vendorTypeId;
	}

	public void setVendorTypeId(String vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}

	public String getEntNature() {
		return entNature;
	}

	public void setEntNature(String entNature) {
		this.entNature = entNature;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public float getRegMoney() {
		return regMoney;
	}

	public void setRegMoney(float regMoney) {
		this.regMoney = regMoney;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public Date getRegistedOn() {
		return registedOn;
	}

	public void setRegistedOn(Date registedOn) {
		this.registedOn = registedOn;
	}

	public String getRegistedPhone() {
		return registedPhone;
	}

	public void setRegistedPhone(String registedPhone) {
		this.registedPhone = registedPhone;
	}

	public String getBusineessType() {
		return busineessType;
	}

	public void setBusineessType(String busineessType) {
		this.busineessType = busineessType;
	}

	public String getEntLegalPerson() {
		return entLegalPerson;
	}

	public void setEntLegalPerson(String entLegalPerson) {
		this.entLegalPerson = entLegalPerson;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getBphone() {
		return bphone;
	}

	public void setBphone(String bphone) {
		this.bphone = bphone;
	}


	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIsQualified() {
		return isQualified;
	}

	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int compareTo(VendorVo o) {
		return this.name.compareTo(o.name);
	}
	
	
}
