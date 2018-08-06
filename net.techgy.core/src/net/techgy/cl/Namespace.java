package net.techgy.cl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.techgy.ui.UI;
import net.techgy.ui.UICreated;
import net.techgy.ui.UIElement;
import net.techgy.ui.UIQuery;
import net.techgy.ui.manager.VId;

@NamedQuery(name="findNamespace", query= "SELECT ns FROM net.techgy.cl.Namespace ns WHERE ns.namespace = :namespace")
@NamedQueries({
	@NamedQuery(name="findNSByLike", query= "SELECT a FROM Namespace a WHERE a.namespace like '%|:namespace|%'"),
	@NamedQuery(name="getNamespace2", query= "SELECT ns FROM net.techgy.cl.Namespace ns WHERE ns.id=:id")
})
@Entity
@UI(name="Namespace")
@Table(name = "t_namespace") 
public class Namespace {

	@Id
	@GeneratedValue
	@UIElement
	@UIQuery(displayName="ID", seq=1)
	@UICreated(notCreated=true)
	@VId
	private long id=0;
	
	@Column(name="namespace",unique=true)
	@UIElement
	@UIQuery(displayName="Name Space", seq=2)
	private String namespace = "";
	
	@UIElement
	@Column(name="descr")
	private String desc = "";

	@Column(nullable=true,name="created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(nullable=true,name="updated_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	@OneToMany(mappedBy ="namespace",cascade = {CascadeType.REFRESH},fetch=FetchType.LAZY)
	private Set<Attribute> attrs = new HashSet<Attribute>();
	
	@OneToMany(mappedBy ="namespace",cascade = {CascadeType.REFRESH},fetch=FetchType.LAZY)
	private Set<net.techgy.cl.Class> classes = new HashSet<net.techgy.cl.Class>();
	
	public Set<Attribute> getAttrs() {
		return attrs;
	}

	public void setAttrs(Set<Attribute> attrs) {
		this.attrs = attrs;
	}

	public Set<net.techgy.cl.Class> getClasses() {
		return classes;
	}

	public void setClasses(Set<net.techgy.cl.Class> classes) {
		this.classes = classes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.namespace.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Namespace)) {
			return false;
		}
		Namespace ns = (Namespace)obj;
		return this.namespace.equals(ns.namespace);
	}

	@Override
	public Namespace clone() throws CloneNotSupportedException {
		Namespace ns = new Namespace();
		ns.namespace = this.namespace;
		ns.desc = this.desc;
		ns.id = this.id;
		ns.createdDate = this.createdDate;
		ns.updateDate = this.updateDate;
		return ns;
	}

	@Override
	public String toString() {
		return this.namespace+"," + this.desc;
	}
	
	
}
