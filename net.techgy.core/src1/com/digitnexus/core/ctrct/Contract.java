package com.digitnexus.core.ctrct;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.digitnexus.core.account.Account;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.dept.Department;
import com.digitnexus.core.idgenerator.IDStrategy;

@Entity
@Table(name = "t_contract")
@IDStrategy
public class Contract   implements Serializable{

	@Id
	@Column(length=64)
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	//签订日期 
	@Column(name = "start_on")
	@Temporal(TemporalType.DATE)
	private Date signDate;
	
	//合同类型
	@Column(name = "type", length=16 )
	private String type;
	
	//签订地点
	@Column(name = "sign_addr", length = 24)
	private String signAddr;
	
	//合同状态
	@Column(name = "statu", length = 24)
	private String statu;
	
	//合同数量
	@Column(name = "sum", length = 24)
	private String sum;
	
	//税率
    @Column(name = "taxrate", length = 24)
	private String taxrate;
		
    //合同金额
    @Column(name = "total_money", length = 24)
	private double totalMoney;
    
    //乙方
    @Column(name = "party_b", length = 24)
	private String partyB;
    
    //乙方联系人
    @Column(name = "partyb_person", length = 24)
	private String partyBPerson;
    
    //乙方联系电话
    @Column(name = "partyb_phone", length = 24)
	private String partyBPhone;
    
    //乙方传真
    @Column(name = "partyb_fax", length = 24)
	private String partyBFax;
    
    //丙方
    @Column(name = "party_c", length = 24)
	private String partyC;
    
    //丙方联系人
    @Column(name = "partyc_person", length = 24)
	private String partyCPerson;
    
    //丙方联系电话
    @Column(name = "partyc_phone", length = 24)
	private String partyCPhone;
    
    //丙方传真
    @Column(name = "partyc_fax", length = 24)
	private String partyCFax;
    
    //验收标准
	@Column(name = "checked_std")
	private String checkedStd;
	
	//付款条件
	@Column(name = "pay_condition")
	private String payCondition;
	
	//备注 
	@Column(name = "remark")
	private String remark;
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy="contract")
	private Set<ContractItem> items;
	
	/*******************Common Content****************************/
	
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
	
	/*************************************************************/
	
	
	
}
