package com.digitnexus.core.dept;

import java.io.Serializable;
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
import com.digitnexus.core.idgenerator.IDStrategy;

@Entity
@Table(name = "t_employee")
@IDStrategy
public class Employee  implements Serializable{

	public static final String STATU_ONBOARD = "OnBoard";
	public static final String STATU_LEAVE = "Leaved";
	
	public static final String TYPE_COMMON = "Common";
	public static final String TYPE_ADMIN = "Admin";
	
	@Id
	@Column(length=64)
	private String id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;
	
	@Column(name="typecode",length=12)
    private String typecode;

	@Column(name="name",length=64)
    private String name;

	@Column(name="on_board_time")
    private Date onBoardTime;

	@Column(name="statu",length=12)
    private String statu;
    
	@Column(name="emp_code",length=12)
    private String empCode;
    
	@ManyToOne  
    @JoinColumn(name="belong_dept",nullable=true)
    private Department belongDept;

	@Column(name="ext0",length=64)
    private String ext0;
	
	@Column(name="ext1",length=64)
    private String ext1;
	
	@Column(name="ext2",length=128)
    private String ext2;
	
	@Column(name="ext3",length=255)
    private String ext3;
	
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getOnBoardTime() {
		return onBoardTime;
	}

	public void setOnBoardTime(Date onBoardTime) {
		this.onBoardTime = onBoardTime;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public Department getBelongDept() {
		return belongDept;
	}

	public void setBelongDept(Department belongDept) {
		this.belongDept = belongDept;
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
    
}
