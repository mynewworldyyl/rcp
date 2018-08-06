package net.techgy.cmty.core.im;

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

import com.digitnexus.core.dept.Client;
import com.digitnexus.core.idgenerator.IDStrategy;

@Entity
@Table(name = "t_friend_group")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="removeFriendById",query="DELETE FriendGroup a Where a.id = :id"),
    @NamedQuery(name="getAllFriendGroup",
    query="SELECT a FROM FriendGroup a WHERE a.account.account.accountName=:an "),
    @NamedQuery(name="getFriendGroupByGn",
    query="SELECT a FROM FriendGroup a WHERE a.account.account.accountName=:an AND a.name=:gn"),
    @NamedQuery(name="getFriendGroupByGid",
    query="SELECT a FROM FriendGroup a WHERE a.account.account.accountName=:an AND a.id=:gid"),
})
public class FriendGroup {

	public static final String DEFAULT_GROUP_NAME = "Default";
	
	@Id
	@Column(length=64)
	private String id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=true)
	private Client client;
	
	//account for this group
	@ManyToOne  
    @JoinColumn(name="im_acc_id",nullable=false)
	private ImAccount account;
	
	//friend list for this group
	@ManyToMany(fetch=FetchType.EAGER,cascade = {CascadeType.ALL})
	@JoinTable(name="t_fg_im_acc",joinColumns={@JoinColumn(name="grp_id")}, 
	inverseJoinColumns={@JoinColumn(name="im_acc_id")}) 
	private Set<ImAccount> friends = new HashSet<ImAccount>();
	
	@Column(name="name",nullable=false,length=12)
	private String name = DEFAULT_GROUP_NAME;

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

	public Set<ImAccount> getFriends() {
		return friends;
	}

	public void setFriends(Set<ImAccount> friends) {
		this.friends = friends;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
