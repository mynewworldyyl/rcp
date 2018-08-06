package com.digitnexus.core.masterdata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.account.Account;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.idgenerator.IDStrategy;
import com.digitnexus.core.uidef.annotation.ItemField;

@Entity
@Table(name = "t_vendor")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="getVendors",query="SELECT a FROM Vendor a"),
})
public class Vendor {

	@Id
	@Column(length = 64)
	private String id="";

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	//企业名称
	@Column(name = "name",unique=true, nullable = false, length = 64)
	private String name="";
	
    //编号
	@Column(name = "code",unique=true, length = 64)
	private String code;
	
    //供应商类别
	@ManyToOne
	@JoinColumn(name = "vendor_type", nullable = false)
	private CommonValue vendorType;
	
	//企业性质
	@Column(name = "ent_nature", length = 12)
	private String entNature;
	
	//执照号
	@Column(name = "license", length = 32)
	private String license;
	
	//注册资金
	@Column(name = "reg_money", length = 12)
	private float regMoney;

	////注册地址
	@Column(name = "reg_address", length = 128)
	private String regAddress;
	
	//注册时间
	@Column(name = "reg_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date registedOn;

	//注册电话
	@Column(name = "reg_phone", length = 24)
	private String registedPhone;
	
	//经营类型
	@Column(name = "bss_type", length = 24)
	private String busineessType;

	//企业法人
	@Column(name = "ent_lp")
	private String entLegalPerson;

	//传真
	@Column(name = "fax")
	private String fax;
	
	//联系人
	@Column(name = "contact_p")
	private String contact;

	//固定电话
	@Column(name = "bphone")
	private String bphone;

	//移动电话
	@Column(name = "modile")
	private String modile;
	
	//是否合格
	@Column(name = "qualified",length=4)
	private String isQualified;
	
    //级别
	@Column(name = "level")
	private String level;
	
	//实力及荣誉
	@Column(name = "description")
    private String desc;
	
	//备注
	@Column(name = "remark")
	private String remark;

	@Column(name = "ext0", length = 64)
	private String ext0;

	@Column(name = "ext1", length = 64)
	private String ext1;

	@Column(name = "ext2", length = 128)
	private String ext2;

	@Column(name = "ext3", length = 255)
	private String ext3;

	@Column(name = "created_on", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = true)
	private Account createdBy;

	@Column(name = "updated_on", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	@ManyToOne
	@JoinColumn(name = "updated_by", nullable = true)
	private Account updatedBy;

	@Override
	public String toString() {
		return "id: " + this.id + ", Name: " + this.name;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Vendor)) {
			return false;
		}
		Vendor v = (Vendor)obj;
		return id.equals(v.id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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

	public CommonValue getVendorType() {
		return vendorType;
	}

	public void setVendorType(CommonValue vendorType) {
		this.vendorType = vendorType;
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

	public String getModile() {
		return modile;
	}

	public void setModile(String modile) {
		this.modile = modile;
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

	public String getExt0() {
		return ext0;
	}

	public void setExt0(String ext0) {
		this.ext0 = ext0;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Account getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Account createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Account getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Account updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
