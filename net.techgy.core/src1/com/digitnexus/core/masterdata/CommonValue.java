package com.digitnexus.core.masterdata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.digitnexus.core.account.Account;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.idgenerator.IDStrategy;

@Entity
@Table(name = "t_common")
@IDStrategy
public class CommonValue {

	public static final String CONSTANT="Constant";
	public static final String TAXRATE="TaxRate";
	public static final String QUALITY="Quality";
	public static final String MODELTYPE="Modeltype";
	public static final String VENDOR_TYPE="VendorType";
	
	@Id
	@Column(length=64)
	private String id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;
	
	@Column(name="name" ,nullable=false,length=64)
	private String name;
	
	@Column(name="code" ,length=64)
	private String code;
	
	@Column(name="value",length=12)
	private String value;
	
	@Column(name="unit",length=12)
	private String unit;
	
	@Column(name="description")
	private String desc;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="typecode")
	private String typecode;
	
	@Column(name="ext0",length=64)
    private String ext0;
	
	@Column(name="ext1",length=64)
    private String ext1;
	
	@Column(name="ext2",length=128)
    private String ext2;
	
	@Column(name="ext3",length=255)
    private String ext3;
	
	@Column(name="ext4",length=64)
    private String ext4;
	
	@Column(name="ext5",length=128)
    private String ext5;
	
	@Column(name="ext6",length=255)
    private String ext6;
	
    @Column(name="created_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@ManyToOne  
    @JoinColumn(name="created_by",nullable=true)
	private Account createdBy;

	@Column(name="updated_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	@ManyToOne  
    @JoinColumn(name="updated_by",nullable=true)
	private Account updatedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
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

	public String getExt4() {
		return ext4;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public String getExt5() {
		return ext5;
	}

	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

	public String getExt6() {
		return ext6;
	}

	public void setExt6(String ext6) {
		this.ext6 = ext6;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		if(id != null) {
			return id.hashCode();
		}else if( name != null){
			return name.hashCode();
		}else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof CommonValue)) {
			return false;
		}
		CommonValue cv = (CommonValue)obj;
		if(id != null) {
			return id.equals(cv.getId());
		}else if( name != null){
			return name.equals(cv.getName());
		}else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "id: " + this.id + ", Name: "+ this.name;
	} 
}
