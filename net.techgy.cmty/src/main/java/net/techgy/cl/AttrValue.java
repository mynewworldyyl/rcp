package net.techgy.cl;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import net.techgy.ui.UI;
import net.techgy.ui.UICreated;
import net.techgy.ui.UIElement;
import net.techgy.ui.UIQuery;
import net.techgy.ui.manager.VId;

@Entity
@UI(name="attrValue")
@Table(name = "t_attr_value", uniqueConstraints = {
	      @UniqueConstraint(columnNames = {"attr_id", "value"})
	}) 
public class AttrValue {

	@Id
	@GeneratedValue
	@UIElement
	@UIQuery(displayName="ID", seq=1)
	@UICreated(notCreated=true)
	@VId
	private long id;
	
	@Column(name="descr")
	private String desc = "";
	
	@ManyToOne
    @JoinColumn(name="attr_id")
	private Attribute attr;

	@Column(nullable=false,name="value")
	private String value;

	public long getId() {
		return id;
	}

	
	@Column(nullable=true,name="created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(nullable=true,name="updated_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	
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

	@Override
	public AttrValue clone() throws CloneNotSupportedException {
		AttrValue av = new AttrValue();
		av.setDesc(this.getDesc());
		av.setId(this.id);
		av.setValue(this.value);
		return av;
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof AttrValue)) {
			return false;
		}
		AttrValue av = (AttrValue)obj;
		if(av.attr == null && this.attr == null) {
			return av.value.equals(this.value);
		}else {
			return av.getAttr().equals(this.attr);
		}
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
}
