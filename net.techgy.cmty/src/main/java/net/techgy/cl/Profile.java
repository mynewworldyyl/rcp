package net.techgy.cl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_profile")
public class Profile {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="namespace")
	private String nameSpace = "default";
	
	@Column(name="desc")
	private String desc = "";

	@Column(nullable=false,name="name")
	private String name;
	
	@Column(nullable=false,name="type")
	private String type;
	
	@ManyToOne
    @JoinColumn(name="related_cls",nullable=false)
	private Class relatedCls;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Class getRelatedCls() {
		return relatedCls;
	}

	public void setRelatedCls(Class relatedCls) {
		this.relatedCls = relatedCls;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	
	
}
