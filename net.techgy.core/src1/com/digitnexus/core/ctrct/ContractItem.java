package com.digitnexus.core.ctrct;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.digitnexus.core.dept.Client;
import com.digitnexus.core.idgenerator.IDStrategy;
import com.digitnexus.core.masterdata.CommonValue;
import com.digitnexus.core.masterdata.Material;
import com.digitnexus.core.masterdata.MaterialType;
import com.digitnexus.core.masterdata.Project;

@Entity
@Table(name = "t_contract_item")
@IDStrategy
public class ContractItem {

	@Id
	@Column(length=64)
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "contract_id", nullable = false)
	private Contract contract;
	
	//交底单位
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "factory_id")
	private Client factory;
	
	//材料名称
	@ManyToOne
	@JoinColumn(name = "asset_type")
	private MaterialType assetType;
	
	//材料名称
	@ManyToOne
	@JoinColumn(name = "asset_name")
	private Material assetName;
	
	//规格
	@ManyToOne
	@JoinColumn(name = "spec_model")
	private CommonValue specModel;
	
	//材质
	@ManyToOne
	@JoinColumn(name = "quality")
	private CommonValue quality;
	
	//计价方式
	@Column(name = "price_mode", length=16)
	private String priceMode;
	
	//计价长度
	@Column(name = "price_len", length=16)
	private double priceLen;
	
	//数量
	@Column(name = "num")
	private int num;
	
	//计价单位
	@Column(name = "price_unit")
	private String priceUnit;
	
	//重量
    @Column(name = "weight")
	private double weight;
	
	//重量单位
    @Column(name = "weight_unit",length=8)
	private String weightUnit;
	
    //单价
    @Column(name = "price")
	private double price;	
	
    //金额
    @Column(name = "total_money")
	private double totalMoney;
	
    //生产厂家
    @Column(name = "produceCop",length=64)
   	private String produceCop;
    
    //入库方式
    @Column(name = "checkin_mode",length=64)
   	private String checkinMode;
    
    //供货日期
  	@Column(name = "surp_start_date")
  	@Temporal(TemporalType.DATE)
  	private Date startOn;
    
    //截止日期
  	@Column(name = "surp_end_date")
  	@Temporal(TemporalType.DATE)
  	private Date endOn;
    
  	 //要求板幅
  	@Column(name = "panel")
 	private String panel;
    
  	//分布工程
  	@Column(name = "eng")
 	private String eng;
  	
  	//备注
  	@Column(name = "remark")
 	private String remark;
    
  	@Column(name = "ext0", length = 64)
	private String ext0;
	
	@Column(name = "ext1", length = 64)
	private String ext1;

	@Column(name = "ext2", length = 128)
	private String ext2;

	@Column(name = "ext3", length = 255)
	private String ext3;
	
	@Column(name = "ext4", length = 128)
	private String ext4;

	@Column(name = "ext5", length = 255)
	private String ext5;
	
}
