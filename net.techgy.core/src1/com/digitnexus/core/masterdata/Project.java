package com.digitnexus.core.masterdata;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.digitnexus.core.account.Account;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.dept.Department;
import com.digitnexus.core.idgenerator.IDStrategy;

@Entity
@Table(name = "t_project")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="getProjects",query="SELECT a FROM Project a"),
})
public class Project {

	public static final String STATE_ONGOING = "Ongoing";
	public static final String STATE_END = "End";
	
	@Id
	@Column(length = 64)
	private String id="";

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	//项目名称
	@Column(name = "name",unique=true, nullable = false, length = 64)
	private String name="";
	
    //编号
	@Column(name = "code", unique=true,length = 64)
	private String code;
	
	//状态
	@Column(name = "statu", length = 32)
	private String statu = STATE_ONGOING;
	
	//创建时间
	@Column(name = "start_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startOn;
	
	//交底单位
	@ManyToMany(fetch=FetchType.LAZY,cascade = {CascadeType.ALL})
	@JoinTable(name="t_prj_factory",joinColumns={@JoinColumn(name="prj_id")}, 
	inverseJoinColumns={@JoinColumn(name="factory_id")})  
    private Set<Client> factorys = new HashSet<Client>();
	
	//共享大区
	@ManyToMany(fetch=FetchType.LAZY,cascade = {CascadeType.ALL})
	@JoinTable(name="t_prj_area",joinColumns={@JoinColumn(name="prj_id")}, 
	inverseJoinColumns={@JoinColumn(name="area_id")})  
    private Set<Client> areas = new HashSet<Client>();
	
	//前缀
	@Column(name = "prefix", length = 12)
	private String prefix;
	
	//责任人
	@Column(name = "resp_person", length = 12)
	private String respPerson;

	//甲方
	@Column(name = "party_a", length = 128)
	private String partyA;
	
	//建设地点
	@Column(name = "buid_addr", length = 24)
	private String buildAddr;
	
	//工程规模
	@Column(name = "scale")
	private String scale;

	//结构类型
	@Column(name = "framework_type")
	private String frameworkType;
	
	//跨度
	@Column(name = "span")
	private String span;

	//承包方式
	@Column(name = "contract_mode")
	private String contractMode;

	//合同工期
	@Column(name = "period")
	private String period;
	
	//合同总价
	@Column(name = "contract_price")
	private double contractPrice;
	
    //中标价格
	@Column(name = "bid_price")
	private double bidPrice;
	
	//备注
	@Column(name = "others")
	private String others;
		
	//交底内容
	@Column(name = "description")
    private String desc;

	@Column(name = "contacts", length = 64)
	private String contacts;

	@Column(name = "ext0", length = 64)
	private String ext0;
	
	@Column(name = "ext1", length = 64)
	private String ext1;

	@Column(name = "ext2", length = 128)
	private String ext2;

	@Column(name = "ext3", length = 255)
	private String ext3;

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

	@Override
	public String toString() {
		return "id: " + this.id + ", Name: " + this.name;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Project)) {
			return false;
		}
		Project v = (Project)obj;
		return id.equals(v.id);
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public Date getStartOn() {
		return startOn;
	}

	public void setStartOn(Date startOn) {
		this.startOn = startOn;
	}

	public Set<Client> getFactorys() {
		return factorys;
	}

	public void setFactorys(Set<Client> factorys) {
		this.factorys = factorys;
	}

	public Set<Client> getAreas() {
		return areas;
	}

	public void setAreas(Set<Client> areas) {
		this.areas = areas;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	public String getRespPerson() {
		return respPerson;
	}

	public void setRespPerson(String respPerson) {
		this.respPerson = respPerson;
	}

	public String getPartyA() {
		return partyA;
	}

	public void setPartyA(String partyA) {
		this.partyA = partyA;
	}

	public String getBuildAddr() {
		return buildAddr;
	}

	public void setBuildAddr(String buildAddr) {
		this.buildAddr = buildAddr;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getFrameworkType() {
		return frameworkType;
	}

	public void setFrameworkType(String frameworkType) {
		this.frameworkType = frameworkType;
	}

	public String getSpan() {
		return span;
	}

	public void setSpan(String span) {
		this.span = span;
	}

	public String getContractMode() {
		return contractMode;
	}

	public void setContractMode(String contractMode) {
		this.contractMode = contractMode;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public double getContractPrice() {
		return contractPrice;
	}

	public void setContractPrice(double contractPrice) {
		this.contractPrice = contractPrice;
	}

	public double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
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
	
}
