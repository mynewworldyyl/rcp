package net.techgy.cl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="t_class")
public class Class {

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne  
    @JoinColumn(name="namespace",nullable=false)
	private Namespace namespace = null;
	
	@Column(name="descr")
	private String desc = "";
	
	@Column(nullable=false)
	private String name;

	@Column(nullable=false)
	private int type;
	
	@ManyToOne  
    @JoinColumn(name="instance_of",nullable=true)
	private Class instanceOf;
	
	@ManyToOne
    @JoinColumn(name="parent_cls",nullable=true)
	private Class parentCls;
	
	@ManyToMany(fetch=FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name="t_cls_attr",joinColumns={@JoinColumn(name="cls_id")},
     inverseJoinColumns={@JoinColumn(name="attr_id")})  
	private List<Attribute> attrs = new ArrayList<Attribute>();	
	
	@OneToMany(mappedBy ="cls",cascade = {CascadeType.ALL},fetch=FetchType.LAZY)
	private List<InsClsValue> values = new ArrayList<InsClsValue>();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class getInstanceOf() {
		return instanceOf;
	}

	public void setInstanceOf(Class instanceOf) {
		this.instanceOf = instanceOf;
	}

	public Class getParentCls() {
		return parentCls;
	}

	public void setParentCls(Class parentCls) {
		this.parentCls = parentCls;
	}

	public Namespace getNamespace() {
		return namespace;
	}

	public void setNamespace(Namespace namespace) {
		this.namespace = namespace;
	}

	public List<Attribute> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<Attribute> attrs) {
		this.attrs = attrs;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<InsClsValue> getValues() {
		return values;
	}

	public void setValues(List<InsClsValue> values) {
		this.values = values;
	}
	
	
}
