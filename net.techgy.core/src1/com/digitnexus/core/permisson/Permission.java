package com.digitnexus.core.permisson;

import java.io.Serializable;
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

import com.digitnexus.core.account.Account;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.idgenerator.IDStrategy;

@Entity
@Table(name = "t_permission")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="loadAll",query="SELECT a FROM Permission a ORDER BY a.clientTypecode"),
    @NamedQuery(name="findPermByIds",query="Select a from Permission a where a.id IN :ids"),
    @NamedQuery(name="deletePermByIds",query="DELETE Permission a where a.id IN :ids"),
    @NamedQuery(name="permKeyValues",query="SELECT a FROM Permission a WHERE a.clientTypecode=:typeCode"),

}) 
public class Permission implements Serializable{

	public static final String PERM_TYPE_VIEW = "view";
	public static final String PERM_TYPE_ACTION = "action";
	
	public Permission(){
	}
	
	public Permission(String ct,String entityType,String action,String type){
		this.setClientTypecode(ct);
		this.setEntityType(entityType);
		this.setAction(action);
		this.setPermType(type);
	}
	
	@Id
	@Column(length=64)
	private String id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;

	@Column(name = "action",length=64)
	private String action;

	@Column(name = "entity_type",length=128)
	private String entityType;
	
	@Column(name = "perm_type",length=255)
	private String permType=PERM_TYPE_ACTION;
	
	@Column(name = "client_typecode",length=255)
	private String clientTypecode=ClientType.Admin;

	@Column(name = "description",length=255)
	private String description;

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

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Permission)) {
			return false;
		}
		//ClientType: Admin, Entity: com.digitnexus.core.uidef.demo.common.AssetInRequestDemo,action: createdPerson,permType: view
		//ClientType: Admin, Entity: com.digitnexus.core.uidef.demo.common.AssetInRequestDemo,action: createdPerson,permType: view
		String str1 = this.toString();
		String str2 = obj.toString();
		return str1.equals(str2);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ClientType: ").append(this.clientTypecode)
		.append(", Entity: ").append(this.entityType)
		.append(",action: ").append(this.action)
		.append(",permType: ").append(this.permType);
		return  sb.toString();
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getClientTypecode() {
		return clientTypecode;
	}

	public void setClientTypecode(String clientTypecode) {
		this.clientTypecode = clientTypecode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getPermType() {
		return permType;
	}

	public void setPermType(String permType) {
		this.permType = permType;
	}
	
}
