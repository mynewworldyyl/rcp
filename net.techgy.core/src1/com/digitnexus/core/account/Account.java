package com.digitnexus.core.account;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.digitnexus.core.dept.Client;
import com.digitnexus.core.dept.Employee;
import com.digitnexus.core.idgenerator.IDStrategy;
import com.digitnexus.core.permisson.Group;
import com.digitnexus.core.permisson.Permission;

@Entity
@Table(name = "t_account")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="removeById",query="DELETE Account a Where a.id = :id"),
    @NamedQuery(name="keyValues",query="SELECT a FROM Account a,IN(a.relatedClients) rc WHERE rc.id=:relatedClients"),
}) 
public class Account implements Serializable{

	public static final String STATU_ACTIVE = "Active";
	public static final String STATU_DISABLE = "Disabled";
	
	public static final String TYPE_COMMON = "Common";
	public static final String TYPE_ADMIN = "Admin";
	
	
	@Id
	@Column(length=64)
	private String id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;
	
	@Column(name="name" ,nullable=false,length=64)
	private String accountName;
	
	@Column(name="statu",nullable=false,length=12)
	private String statu;
	
	@Column(name="password")
	private String password;
	
	@Column(name="typeCode")
	private String typeCode = TYPE_COMMON;
	
	@ManyToOne
    @JoinColumn(name="emp_id",nullable=true)
	private Employee employee;
	
	@Column(name="ext0",length=64)
    private String ext0;
	
	@Column(name="ext1",length=64)
    private String ext1;
	
	@Column(name="ext2",length=128)
    private String ext2;
	
	@Column(name="ext3",length=255)
    private String ext3;
	
	@ManyToMany(fetch=FetchType.LAZY,cascade = {CascadeType.ALL})
	@JoinTable(name="t_account_client",joinColumns={@JoinColumn(name="acccount_id")}, 
	inverseJoinColumns={@JoinColumn(name="client_id")})  
    private Set<Client> relatedClients = new HashSet<Client>();
    
	@ManyToMany(cascade = CascadeType.DETACH, mappedBy = "accounts", fetch = FetchType.LAZY)
    private Set<Group> groups=new HashSet<Group>();
    
    @ManyToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinTable(name="t_account_perm",joinColumns={@JoinColumn(name="acccount_id")}, 
    inverseJoinColumns={@JoinColumn(name="perm_id")}) 
    private Set<Permission> permissons;

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
	
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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

	public Set<Client> getRelatedClients() {
		return relatedClients;
	}

	public void setRelatedClients(Set<Client> relatedClients) {
		this.relatedClients = relatedClients;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public Set<Permission> getPermissons() {
		return permissons;
	}

}
