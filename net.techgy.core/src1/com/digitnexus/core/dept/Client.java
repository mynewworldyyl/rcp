package com.digitnexus.core.dept;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.digitnexus.core.idgenerator.IDStrategy;

@Entity
@Table(name = "t_client")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="getClientByTypecode",query="SELECT a FROM Client a WHERE a.typecode=:typecode"),
})
public class Client  implements Serializable{

	public static final String CLIENT_ID_KEY="clientId";
	
	public static final String ADMIN_CLIENT_NAME="超级租户";
	
	@Id
	@Column(length=8,nullable=false)
	private String id;
	
	@ManyToOne  
    @JoinColumn(name="typecode",nullable=false)
    private ClientType typecode;

	@Column(name = "name",length=64,unique=true)
    private String name;

	@Column(name = "description",length=255)
    private String description;

	@Column(name = "remark",length=255)
    private String remark;
	
	@ManyToOne  
    @JoinColumn(name="parent_id",nullable=true)
	private Client parent;
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy="parent")
	private List<Client> subClients = new ArrayList<Client>();
	
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

	public ClientType getTypecode() {
		return typecode;
	}

	public void setTypecode(ClientType typecode) {
		this.typecode = typecode;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Client getParent() {
		return parent;
	}

	public void setParent(Client parent) {
		this.parent = parent;
	}

	public List<Client> getSubClients() {
		return subClients;
	}

	public void setSubClients(List<Client> subClients) {
		this.subClients = subClients;
	}

}
