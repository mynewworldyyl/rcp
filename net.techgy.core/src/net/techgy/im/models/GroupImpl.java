package net.techgy.im.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
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
@Table(name="t_group_cmty")
public class GroupImpl implements IGroup {

	@Id
	@GeneratedValue
	private long id;
	@Column(name="description")
	private String desc = "";
	@Column(nullable=false)
	private String name = "group1";
	
	@ManyToOne  
    @JoinColumn(name="ownerId")
	private UserImpl owner;
	
	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="r_group_friends",joinColumns={@JoinColumn(name="group_id")},
     inverseJoinColumns={@JoinColumn(name="user_id")})  
	private Set<UserImpl> friends = new HashSet<UserImpl>(); 
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addFriend(UserImpl friend) {
		if(friend == this.getOwner()) {
			return;
		}
		this.friends.add(friend);
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return this.desc;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public Set<UserImpl> getFriends() {
		// TODO Auto-generated method stub
		return this.friends;
	}

	public UserImpl getOwner() {
		return owner;
	}

	public void setOwner(UserImpl owner) {
		this.owner = owner;
	}

	@Override
	public GroupImpl clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		GroupImpl cln  = new GroupImpl();
		cln.setDesc(this.getDesc());
		cln.setId(this.getId());
		cln.setName(this.getName());
		cln.setOwner(this.getOwner().clone());
		for(UserImpl u : this.getFriends()) {
			cln.getFriends().add(u.clone());
		}
		return cln;
	}

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("desc: ").append(this.desc)
        .append(",id:").append(this.getId())
        .append(",Owner:").append(this.getOwner().getName());
		return sb.toString();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (int)this.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof GroupImpl)) {
			return false;
		}
		return this.id == ((GroupImpl)obj).id;
	}

	
}
