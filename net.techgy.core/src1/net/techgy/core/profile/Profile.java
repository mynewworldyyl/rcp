package net.techgy.core.profile;

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
@Table(name = "t_profile")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="getProfileForContent",query="SELECT NEW Profile(a.content) FROM Profile a "
    		+ " WHERE a.account.accountName=:an  AND a.key=:key AND a.typecode='Content' "),
   @NamedQuery(name="getProfileForKeyValue",
		   query="SELECT  NEW net.techgy.core.profile.Profile(a.key,a.value)  "
    		+ " FROM net.techgy.core.profile.Profile a "
    		+" WHERE a.account.accountName=:an AND a.key=:key AND a.typecode='KeyValue'"),
  /* @NamedQuery(name="getProfileForData",
  		   query="SELECT  NEW net.techgy.core.profile.Profile(a.key,a.data)  "
      		+ " FROM net.techgy.core.profile.Profile a "
      		+" WHERE a.account.accountName=:an AND a.key=:key AND a.typecode='Binary'"),*/
    @NamedQuery(name="getProfile",query="SELECT a FROM net.techgy.core.profile.Profile a "
    		+" WHERE a.account.accountName=:an AND a.key=:key AND a.modelId=:mid "),
    @NamedQuery(name="getProfileByAccount",query="SELECT a FROM net.techgy.core.profile.Profile a "
    	    		+" WHERE a.account.accountName=:an "),		
    @NamedQuery(name="deleteProfileByKey",query="DELETE Profile a "
    		    		+" WHERE a.account.accountName=:an AND a.key=:key  AND a.modelId=:mid "),
})
public class Profile {

	public static final String DEFAULT_MODEL_ID=Profile.class.getName();
	
	public static final String TYPE_CONTENT="Content";
	
	public static final String TYPE_KEY_VALUE="KeyValue";
	
	public static final String TYPE_BINARY="Binary";
	
	public Profile(){
		
	}
	
    public Profile(String key,String value){
		this.key = key;
		this.value = value;
	}
    
    public Profile(String content){
		this.content = content;
	}
	
	@Id
	@Column(length=64)
	private String id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;
	
	@Column(name="model_id" ,nullable=false,length=126)
	private String modelId;
	
	@Column(name="value_key" ,nullable=false,length=64)
	private String key;
	
	@Column(name="value" ,length=250)
	private String value;
	
	@Column(name="data_type" ,nullable=false,length=24)
	private String dataType;
	
	@Column(name="min_value")
	private double minValue;
	
	@Column(name="max_value")
	private double maxValue;
	
	@Column(name="ui_type")
	private String uiType;
	
	@Column(name="validators")
	private String validators;
	
	@Column(name="def_value")
	private String defValue;
	
	@Column(name="providers")
	private String providers;
	
	@Column(name="avai_values")
	private String avaiValues;
	
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name="content", /*columnDefinition="CLOB",*/ nullable=true) 
	private String content;
	
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name="data", /*columnDefinition="CLOB",*/ nullable=true) 
	private byte[] data;
	
	@ManyToOne  
    @JoinColumn(name="account_id",nullable=false)
	private Account account;
	
	@Column(name="typecode")
	private String typecode = TYPE_CONTENT;
	
	@Column(name="ext0",length=64)
    private String ext0;
	
	@Column(name="ext1",length=64)
    private String ext1;
	
	@Column(name="ext2",length=128)
    private String ext2;
	
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


	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Profile)) {
			return false;
		}
		Profile cv = (Profile)obj;
		return this.toString().equals(obj.toString());
	}

	@Override
	public String toString() {
		return "key: " + this.key + ", Account: "+
	(this.account == null ? "" : this.account.getAccountName() );
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
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

	public String getUiType() {
		return uiType;
	}

	public void setUiType(String uiType) {
		this.uiType = uiType;
	}

	public String getValidators() {
		return validators;
	}

	public void setValidators(String validators) {
		this.validators = validators;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getDefValue() {
		return defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	public String getProviders() {
		return providers;
	}

	public void setProviders(String providers) {
		this.providers = providers;
	}

	public String getAvaiValues() {
		return avaiValues;
	}

	public void setAvaiValues(String avaiValues) {
		this.avaiValues = avaiValues;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
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

	public static String getTypeCommon() {
		return TYPE_CONTENT;
	}

	public static String getTypeKeyValue() {
		return TYPE_KEY_VALUE;
	} 
	
	
}
