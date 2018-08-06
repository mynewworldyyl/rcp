package net.techgy.cmty.core.im;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.digitnexus.core.dept.Client;
import com.digitnexus.core.idgenerator.IDStrategy;

@Entity
@Table(name = "t_chat_room")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="removeChatRoomById",query="DELETE ChatRoom a Where a.id = :rid"),
    @NamedQuery(name="getAllChatRoom",
    query="SELECT a FROM ChatRoom a WHERE a.account.account.accountName=:an "),
    @NamedQuery(name="getChatRoomByRn",
    query="SELECT a FROM ChatRoom a WHERE a.account.account.accountName=:an AND a.name=:gn"),
    @NamedQuery(name="getChatRoomByRid",
         query="SELECT a FROM ChatRoom a WHERE a.id=:rid "),
})
public class ChatRoom {

	public static final String TYPE_COMMON = "Common";
	
	@Id
	@Column(length=64)
	private String id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=true)
	private Client client;
	
	//account created this room
	@ManyToOne  
    @JoinColumn(name="im_acc_id",nullable=false)
	private ImAccount account;
	
	//IM account in this room
	@ManyToMany(fetch=FetchType.LAZY,cascade = {CascadeType.ALL})
	@JoinTable(name="t_cr_im_acc",joinColumns={@JoinColumn(name="cr_id")}, 
	inverseJoinColumns={@JoinColumn(name="im_acc_id")}) 
	private Set<ImAccount> acces = new HashSet<ImAccount>();
	
	@Column(name="name",nullable=false,length=32)
	private String name;
	
	@Column(name="typecode",nullable=false,length=32)
	private String typecode = TYPE_COMMON;
	
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name="header_img", /*columnDefinition="CLOB",*/ nullable=true) 
	private byte[] headerImg;

	public byte[] getHeaderImg() {
		return headerImg;
	}

	public void setHeaderImg(byte[] headerImg) {
		this.headerImg = headerImg;
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

	public ImAccount getAccount() {
		return account;
	}

	public void setAccount(ImAccount account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public Set<ImAccount> getAcces() {
		return acces;
	}
	
	
}
