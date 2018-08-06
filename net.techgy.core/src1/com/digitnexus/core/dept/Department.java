package com.digitnexus.core.dept;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.digitnexus.core.account.Account;
import com.digitnexus.core.idgenerator.IDStrategy;
import com.digitnexus.core.permisson.Permission;

@Entity
@Table(name = "t_dept")
@IDStrategy
public class Department implements Serializable {

	public static final String COMMON_TYPE = "CommonType";
	public static final String CLIENT_TYPE = "ClientType";
	
	public static final String ACTIVE = "Active";
	public static final String DISABLED = "Disabled";
	
	@Id
	@Column(length=64)
	private String id;

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	@Column(name = "deptName")
	private String deptName;

	@Column(name = "typecode")
	private String typecode;

	@Column(name = "statu")
	private String status;

	@Column(name = "description")
	private String description;

	@Column(name = "remark")
	private String remark;

	@ManyToOne
	@JoinColumn(name = "parent_id", nullable = true)
	private Department parent;

	/*
	 * 子部门
	 */
	@OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy="parent")
	private Set<Department> subDepts = new HashSet<Department>();
	
	/*
	 * 子部门
	 */
	@OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy="belongDept")
	private Set<Employee> employees = new HashSet<Employee>();
	
	/*
	 * relatedClient： 如果此部门是一个Client，则关联到所相应的Client
	 * client: 统一为总部的总部的Cient,即使此部门在非总部下面的Client创建
	 */
	@OneToOne(cascade=CascadeType.PERSIST , fetch = FetchType.LAZY)
    @JoinColumn(name="related_client_id", nullable = true)
	private Client relatedClient;

	@Column(name = "ext0")
	private String ext0;
	
	@Column(name = "ext1")
	private String ext1;
	
	@Column(name = "ext2")
	private String ext2;
	
	@Column(name = "ext3")
	private String ext3;

	/**
	 * 针对此部门相关的权限，没果没有针对此部门的权限，刚此列表为NULL
	 */
	@ManyToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REFRESH})
	@JoinTable(name="t_dept_perm",joinColumns={@JoinColumn(name="dept_id")}, 
	inverseJoinColumns={@JoinColumn(name="perm_id")}) 
	private Set<Permission> permissions;

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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public Set<Department> getSubDepts() {
		return subDepts;
	}

	public void setSubDepts(Set<Department> subDepts) {
		this.subDepts = subDepts;
	}

	public Client getRelatedClient() {
		return relatedClient;
	}

	public void setRelatedClient(Client relatedClient) {
		this.relatedClient = relatedClient;
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

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Department) {
			Department d = (Department)obj;
			return d.id.equals(this.id);
		}
		 return false;
	}

	@Override
	protected Department clone() throws CloneNotSupportedException {
		Department d = (Department)super.clone();
		d.setId(this.getId());
		d.setDeptName(this.getDeptName());
		d.setClient(this.getClient());
		d.setStatus(this.getStatus());
		d.setDescription(this.getDescription());
		d.setExt0(this.getExt0());
		d.setExt1(this.getExt1());
		d.setExt2(this.getExt2());
		d.setExt3(this.getExt3());
		d.setRelatedClient(this.getRelatedClient());
		d.setRemark(this.getRemark());
		d.setTypecode(this.getTypecode());
		/*if(this.parent != null) {
			d.setParent(this.parent.clone());
		}*/
		if(this.subDepts != null && !this.subDepts.isEmpty()) {
			for(Department dd : this.subDepts) {
				Department ss = dd.clone();
				ss.setParent(d);
				d.getSubDepts().add(ss);
			}
		}
		return d;
	}
	
	
}
