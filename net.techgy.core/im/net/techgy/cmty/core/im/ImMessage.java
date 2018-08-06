package net.techgy.cmty.core.im;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.digitnexus.core.account.Account;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.idgenerator.IDStrategy;

@Entity
@Table(name = "t_im_message")
@IDStrategy
@NamedQueries({
	@NamedQuery(name="getNewImMessages",
		    query="SELECT a FROM ImMessage a WHERE a.to=:an AND a.handled=false "),
    @NamedQuery(name="deleteNewImMessage",query="DELETE ImMessage a Where a.id IN :ids"),
    
    /*
    @NamedQuery(name="getFriendGroupByGn",
    query="SELECT a FROM FriendGroup a WHERE a.account.account.accountName=:an AND a.name=:gn"),
    @NamedQuery(name="getFriendGroupByGid",
    query="SELECT a FROM FriendGroup a WHERE a.account.account.accountName=:an AND a.id=:gid"),*/
})
public class ImMessage {

	public static final String DEFAULT_GROUP_NAME = "Default";
	
	@Id
	@Column(length=64)
	private String id = null;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=true)
	private Client client;
	
	//account for this group
	@ManyToOne  
    @JoinColumn(name="im_acc_id",nullable=false)
	private ImAccount account;
	
	@Column(name="seq_id",nullable=false)
    private String seqId = "";
	
	@Column(name="msg_from",nullable=false)
	private String from = "" ;
	
	@Column(name="msg_to",nullable=false)
	private String to = "" ;
	
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name="content", /*columnDefinition="CLOB",*/ nullable=true) 
	private String content;
	
	@Column(name="msg_type",nullable=false)
	private String msgType;
	
	@Column(name="model_id",nullable=false)
	private String modelId="";
	
	@Column(name="topic",nullable=false)
	private String topic;
	
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name="data", /*columnDefinition="CLOB",*/ nullable=true) 
	private byte[] data;
	
	@Column(name="handled",nullable=false,length=12)
	private boolean handled = false;
	
	@Column(name="created_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@ManyToOne  
    @JoinColumn(name="created_by",nullable=true)
	private Account createdBy;

	@Column(name="updated_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	@ManyToOne  
    @JoinColumn(name="updated_by",nullable=true)
	private Account updatedBy;

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

	public ImAccount getAccount() {
		return account;
	}

	public void setAccount(ImAccount account) {
		this.account = account;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public boolean isHandled() {
		return handled;
	}

	public void setHandled(boolean handled) {
		this.handled = handled;
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
