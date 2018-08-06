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
@Table(name = "t_client_type")
@IDStrategy
public class ClientType  implements Serializable{

	public static final String Headquarter = "Headquarter";
	public static final String Factory = "Factory";
	public static final String Project = "Project";
	public static final String Vendor = "Vendor";
	public static final String Region = "Region";
	public static final String Admin = "Admin";
	
	public static final String[] ALL_TYPES = {Headquarter,Factory,Project,Vendor,Region,Admin};
	
	@Id
	@Column(length=64)
	private String id;
	
	@Column(name = "name",nullable=false,length=64)
	private String name="";
	
	@Column(name = "description")
	private String description="";
	
	@Column(name = "typecode",nullable=false,unique=true)
	private String typeCode;

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

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
}
