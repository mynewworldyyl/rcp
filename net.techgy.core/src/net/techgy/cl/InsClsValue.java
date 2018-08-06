package net.techgy.cl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="t_cls_attr_value", uniqueConstraints = {
	      @UniqueConstraint(columnNames = {"cls_id", "attr_id"})
	})
public class InsClsValue {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="descr")
	private String desc = "";
	
	@ManyToOne
    @JoinColumn(name="attr_id")
	private Attribute attr;
	
	@ManyToOne
    @JoinColumn(name="cls_id")
	private Class cls;

	@Column(nullable=false,name="value")
	private String value;	

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

	public Attribute getAttr() {
		return attr;
	}

	public void setAttr(Attribute attr) {
		this.attr = attr;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Class getCls() {
		return cls;
	}

	public void setCls(Class cls) {
		this.cls = cls;
	}
	
	
}
