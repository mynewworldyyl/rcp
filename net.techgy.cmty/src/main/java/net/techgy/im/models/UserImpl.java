package net.techgy.im.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.techgy.usercenter.AccountImpl;

@Entity
@Table(name="t_user")
public class UserImpl extends AbstractRole implements IUser{
	
	@Id
	@GeneratedValue
	private long id;
	@Column(unique=true)
	private String name;
	
	private RoleType type;
	
	@ManyToOne
	@JoinColumn(name = "accountId", nullable=false)
	private AccountImpl account;
	
	
	@OneToMany(mappedBy ="owner",cascade = {CascadeType.MERGE,CascadeType.PERSIST,
			CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.LAZY)
	private Set<GroupImpl> friendGroups = new HashSet<GroupImpl>();
	
	
	@OneToMany(mappedBy ="creator",fetch=FetchType.LAZY)
	private Set<ChannelImpl> channels = new HashSet<ChannelImpl>();
	
	public Set<ChannelImpl> getChannels() {
		return channels;
	}
	public void setChannels(Set<ChannelImpl> channels) {
		this.channels = channels;
	}
	
	public Set<GroupImpl> getFriendGroups() {
		return friendGroups;
	}
	
	public void setFriendGroups(Set<GroupImpl> friendGroups) {
		this.friendGroups = friendGroups;
	}
	
	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public RoleType getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	@Override
	public IAccount getAccount() {
		// TODO Auto-generated method stub
		return this.account;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(RoleType type) {
		this.type = type;
	}

	public void setAccount(AccountImpl account) {
		this.account = account;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
	   return this.getAccount().getAccountName().hashCode();
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof UserImpl)) {
			return false;
		}		
		return ((UserImpl)obj).getAccount().getAccountName().equals(this.account.getAccountName());
	}
	
	@Override
	public UserImpl clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
	    UserImpl u = new UserImpl();
		u.setAccount(this.account);
		u.setId(id);
		u.setName(this.getName());
		u.setType(this.getType());		
		return u;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("name:").append(this.getName())
		.append(",id").append(this.getId())
		.append(",type").append(this.getType());
		return sb.toString();
	}

	

}
