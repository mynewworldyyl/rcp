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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import net.techgy.ui.UI;
import net.techgy.ui.UIConstants;
import net.techgy.ui.UICreated;
import net.techgy.ui.UIElement;
import net.techgy.ui.UIQuery;
import net.techgy.ui.manager.VId;

@Entity
@UI(name="Attribute")
@Table(name = "t_attr", uniqueConstraints = {
	      @UniqueConstraint(columnNames = {"namespace", "name"})
	}) 
public class Attribute {

	@Id
	@GeneratedValue
	@UIElement
	@UIQuery(displayName="ID", seq=1)
	@UICreated(notCreated=true)
	@VId
	private long id;
	
	@ManyToOne  
    @JoinColumn(name="namespace",nullable=false)
	private Namespace namespace = null;
	
	@UIElement
	@Column(name="descr")
	private String desc = "";
	
	@UIElement
	@Column(nullable=false,name="name")
	private String name="";

	@Column(nullable=false,name="data_type")
	@UIElement
	@UIQuery(displayName="Data Type", seq=3)
	@UICreated(uiType=UIConstants.UI_TYPE_SELECT,values={UIConstants.DATA_TYPE_BOOLEAN,
			UIConstants.DATA_TYPE_BYTE,UIConstants.DATA_TYPE_DOUBLE,UIConstants.DATA_TYPE_FLOAT,
			UIConstants.DATA_TYPE_INTEGER,UIConstants.DATA_TYPE_LONG,UIConstants.DATA_TYPE_SHORT,
			UIConstants.DATA_TYPE_STRING})
	private String dataType;
	
	@UIElement
	@Column(nullable=false,name="default_value")
	private String defaultValue="";
	
	@UIElement
	@Column(nullable=false,name="min_value")
	private double minValue;
	
	@UIElement
	@Column(nullable=false,name="max_value")
	private double maxValue;
	
	@UIElement
	@Column(nullable=true,name="length")
	private int length;
	
	@Column(nullable=true,name="requried")
	@UIElement
	@UIQuery(displayName="Is Require")
	@UICreated(uiType=UIConstants.UI_TYPE_CHECKBOX,values={"Requried"})
	private boolean requried;
		
	@OneToMany(mappedBy ="attr",cascade = {CascadeType.ALL},fetch=FetchType.EAGER)
	@UIElement(valueCls=AttrValue.class)
	@UIQuery(uiValueFields={"value"},elementSeperator="/")
	@UICreated(uiType=UIConstants.UI_TYPE_BUTTON,values={"Requried"})
	private Set<AttrValue> attrValues = new HashSet<AttrValue>();
	
	@UIElement
	@ManyToMany(cascade=CascadeType.REFRESH, mappedBy="attrs", fetch=FetchType.LAZY)
	@UIQuery(unvisible=true)
	@UICreated(notCreated=true)
	private Set<Class> classes = new HashSet<Class>();
	
	@Column(nullable=true,name="created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(nullable=true,name="updated_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	public Set<AttrValue> getAttrValues() {
		return attrValues;
	}

	public void setAttrValues(Set<AttrValue> attrValues) {
		this.attrValues = attrValues;
	}

	public Set<Class> getClasses() {
		return classes;
	}

	public void setClasses(Set<Class> classes) {
		this.classes = classes;
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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isRequried() {
		return requried;
	}

	public void setRequried(boolean requried) {
		this.requried = requried;
	}

	public Namespace getNamespace() {
		return namespace;
	}

	public void setNamespace(Namespace namespace) {
		this.namespace = namespace;
	}

	@Override
	public Attribute clone() throws CloneNotSupportedException {
		Attribute attr = new Attribute();
		Set<AttrValue> la = new HashSet<AttrValue>();
		for(AttrValue av : this.attrValues) {
			la.add(av.clone());
		}
		attr.setAttrValues(la);
		attr.setDataType(this.getDataType());
		attr.setDefaultValue(this.defaultValue);
		attr.setDesc(this.desc);
		attr.setId(this.getId());
		attr.setLength(this.getLength());
		attr.setMaxValue(this.maxValue);
		attr.setMinValue(this.minValue);
		attr.setName(this.name);
		attr.setNamespace(this.namespace.clone());
		attr.setRequried(this.requried);
		return attr;
	}

	@Override
	public int hashCode() {
		return (this.namespace+this.name).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Attribute)) {
			return false;
		}
		Attribute o = (Attribute)obj;
		return this.namespace.equals(o.namespace) && this.name.equals(o.name);
	}	
	
	public String getFullName() {
		return this.namespace+"."+this.name;
	}
}
