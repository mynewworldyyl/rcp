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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.digitnexus.core.account.Account;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.idgenerator.IDStrategy;

@Entity
@Table(name = "t_im_account")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="removeImAccountById",
    query="DELETE net.techgy.cmty.core.im.ImAccount a Where a.id = :id"),
    @NamedQuery(name="getImAccountByAccountName",
    query="SELECT a FROM net.techgy.cmty.core.im.ImAccount a WHERE a.account.accountName=:an "),
})
public class ImAccount {

	public static final String IM_TYPECODE_COMMON= "Default";
	
	@Id
	@Column(length=64)
	private String id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=true)
	private Client client;
	
	@OneToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="account_id",nullable=false,unique=true)
	private Account account;
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.EAGER,mappedBy="account")
	private Set<FriendGroup> friendGroups = new HashSet<FriendGroup>();
	
	@Column(name="typecode",nullable=false,length=12)
	private String typecode = IM_TYPECODE_COMMON;
	
	@Column(name="nickname",nullable=false,length=12)
	private String nickname;
	
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name="header_img", /*columnDefinition="CLOB",*/ nullable=true) 
	private byte[] headerImg;

	@ManyToMany(fetch=FetchType.LAZY,cascade = {CascadeType.ALL}, mappedBy="acces")
	private Set<ChatRoom> chatRooms = new HashSet<ChatRoom>();
	
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Set<FriendGroup> getFriendGroups() {
		return friendGroups;
	}

	public void setFriends(Set<FriendGroup> friends) {
		this.friendGroups = friends;
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Set<ChatRoom> getChatRooms() {
		return chatRooms;
	}

	public byte[] getHeaderImg() {
		return headerImg;
	}

	public void setHeaderImg(byte[] headerImg) {
		this.headerImg = headerImg;
	}
	
	
}
