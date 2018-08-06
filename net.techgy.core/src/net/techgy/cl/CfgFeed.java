package net.techgy.cl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="t_cfg_feed")
public class CfgFeed {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="desc")
	private String desc = "";
	
	@Column(name="namespace")
	private String namespace = "";
	
	@Column(nullable=false,name="name")
	private String name;

	@OneToOne(cascade = {CascadeType.ALL},fetch=FetchType.EAGER)
	@JoinColumn(name="cls_id")
	private Class cls;

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

	public Class getCls() {
		return cls;
	}

	public void setCls(Class cls) {
		this.cls = cls;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	
}
