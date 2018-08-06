package com.digitnexus.core.idgenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_id_assignment")
public class IDAssignment implements Serializable{

	private static final long serialVersionUID = -232998889934l;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="CLIENT_ID")
	private String clientId = "0";
	
	@Column(name="SERVER_ID")
	private String serverId;
	
	@Column(name="ENTITY_ID")
	private String entityId;
	
	@Column(name="PREFIX_VALUE")
	private Integer prefixValue=0;
	
	@Column(name="PREFIX_VALUE_LEN")
	private Integer prefixValueLen=4;
	
	@Column(name="ID_VALUE")
	private Long idValue=1l;

	@Column(name="ID_VALUE_TYPE")
	private String idValueType="string";
	
	@Column(name="ID_INIT_VALUE")
	private Long idInitValue=1l;
	
	@Column(name="ID_STEP_LEN")
	private Integer idStepLen=1;
	
	@Column(name="STATU")
	private String statu="";
	
	@Column(name="TABLE_NAME")
	private String tableName="";
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Integer getPrefixValue() {
		return prefixValue;
	}

	public void setPrefixValue(Integer prefixValue) {
		this.prefixValue = prefixValue;
	}

	public Integer getPrefixValueLen() {
		return prefixValueLen;
	}

	public void setPrefixValueLen(Integer prefixValueLen) {
		this.prefixValueLen = prefixValueLen;
	}

	public Long getIdValue() {
		return idValue;
	}

	public void setIdValue(Long idValue) {
		this.idValue = idValue;
	}

	public String getIdValueType() {
		return idValueType;
	}

	public void setIdValueType(String idValueType) {
		this.idValueType = idValueType;
	}

	public Long getIdInitValue() {
		return idInitValue;
	}

	public void setIdInitValue(Long idInitValue) {
		this.idInitValue = idInitValue;
	}

	public Integer getIdStepLen() {
		return idStepLen;
	}

	public void setIdStepLen(Integer idStepLen) {
		this.idStepLen = idStepLen;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}
}
