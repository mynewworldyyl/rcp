package com.digitnexus.core.permisson;

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

import com.digitnexus.core.account.Account;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.idgenerator.IDStrategy;

@Entity
@Table(name = "t_group")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="getGroupByName",query="SELECT a FROM Group a WHERE a.name=:name"),
    @NamedQuery(name="removeGroupById",query="DELETE Group a Where a.id IN :ids"),
}) 
public class Group implements Serializable{

	public static final String GROUP_TYPE_ADMIN="Admin";
	public static final String GROUP_TYPE_COMMON="Common";
	
	public Group() {
		
	}
	
	public Group(String name,String typecode){
		this(name,typecode,name);
	}
	
	public Group(String name,String typecode,String description){
		this.name = name;
		this.typecode = typecode;
		this.description=description;
	}
	
	@Id
	@Column(length=64)
	private String id="";
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;

	@Column(name = "typecode",length=12)
	private String typecode = GROUP_TYPE_COMMON;

	@Column(name = "description",length=255)
	private String description;

	@Column(name = "name",length=255)
	private String name;

	@ManyToMany(fetch=FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinTable(name="t_group_account",joinColumns={@JoinColumn(name="group_id")}, 
    inverseJoinColumns={@JoinColumn(name="acccount_id")})  
	private Set<Account> accounts = new HashSet<Account>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(name = "t_group_perm", joinColumns = { @JoinColumn(name = "group_id") }, 
	inverseJoinColumns = { @JoinColumn(name = "perm_id") })
	private Set<Permission> permissions =  new HashSet<Permission>();;
	
	@Column(name="created_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@ManyToOne  
    @JoinColumn(name="updated_by",nullable=true)
	private Account updatedBy;
	
	@ManyToOne  
    @JoinColumn(name="created_by",nullable=true)
	private Account createdBy;

	@Column(name="updated_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

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

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Account getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Account updatedBy) {
		this.updatedBy = updatedBy;
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

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Group)) {
			return false;
		}
		return this.id.equals(((Group)obj).getId());
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	
}
