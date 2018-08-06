package com.digitnexus.core.masterdata;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.digitnexus.core.account.Account;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.idgenerator.IDStrategy;

@Entity
@Table(name = "t_material_type")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="findRooMaterial",query="SELECT DISTINCT a FROM MaterialType a WHERE a.parentType IS NULL"),
    //@NamedQuery(name="keyValues",query="SELECT a FROM Account a,IN(a.relatedClients) rc WHERE rc.id=:relatedClients"),
}) 
public class MaterialType  implements Serializable{

	@Id
	@Column(length=64)
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String desc;
	
	@ManyToOne
	@JoinColumn(name = "parent_type", nullable = true)
	private MaterialType parentType;
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy="parentType")
	private Set<MaterialType> subTypes;
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy="parentType")
	private Set<Material> materials;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public MaterialType getParentType() {
		return parentType;
	}

	public void setParentType(MaterialType parentType) {
		this.parentType = parentType;
	}

	public Set<MaterialType> getSubTypes() {
		return subTypes;
	}

	public void setSubTypes(Set<MaterialType> subTypes) {
		this.subTypes = subTypes;
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

	public Set<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(Set<Material> materials) {
		this.materials = materials;
	}
	
	
}
