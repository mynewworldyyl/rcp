package net.techgy.im.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_channel")
public class ChannelImpl implements IChannel{

	@Id
	@GeneratedValue
	private long id;
	
	private String channelName ;
	private String description ;
	private ChannelType channelType;
	
	@ManyToOne
	@JoinColumn(name="creator_id")
	private UserImpl creator;
	
	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="r_users_channel",joinColumns={@JoinColumn(name="user_id")},
    inverseJoinColumns={@JoinColumn(name="channel_id")})  
    private Set<UserImpl> users = new HashSet<UserImpl>();
	
	
	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="r_deputy_channel",joinColumns={@JoinColumn(name="deputy_id")},
    inverseJoinColumns={@JoinColumn(name="channel_id")})  
    private Set<UserImpl> Deputies = new HashSet<UserImpl>();
    
	@Override
	public String getChannelName() {
		// TODO Auto-generated method stub
		return this.channelName;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return this.description;
	}

	@Override
	public ChannelType getChannelType() {
		// TODO Auto-generated method stub
		return this.channelType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserImpl getCreator() {
		return creator;
	}

	public void setCreator(UserImpl creator) {
		this.creator = creator;
	}

	public Set<UserImpl> getUsers() {
		return users;
	}

	public void setUsers(Set<UserImpl> users) {
		this.users = users;
	}

	public Set<UserImpl> getDeputies() {
		return Deputies;
	}

	public void setDeputies(Set<UserImpl> deputies) {
		Deputies = deputies;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setChannelType(ChannelType channelType) {
		this.channelType = channelType;
	}

	@Override
	public IUser getOWner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IUser> getDeputy() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
