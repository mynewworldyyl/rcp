package net.techgy.cmty.core.im.vo;

import java.util.HashSet;
import java.util.Set;

import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.Children;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.Parent;
import com.digitnexus.core.uidef.annotation.TreeViewEditor;

@TreeViewEditor(
		notNeedPerm=true,
		actions = {
				@Action(name="Add", url="crudService/save",actionType=ActionType.Add,
			    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
			    		ClientType.Project,ClientType.Headquarter,ClientType.Factory})
		}
)
public class FriendVo {

	@ItemField(name="id",isIdable=true,order = 1,hide=true,editable=false)
	private String id="";
	
	@ItemField(name="nodeType",order=-1, hide=true)
	public String nodeType = ImAccountVo.class.getName();
	
	@ItemField(name="Name",uiType=UIType.Text,order=1,lengthByChar=20)
	private String name;
	
	@ItemField(name="accountName",uiType=UIType.Text,order=1,lengthByChar=20)
	private String accountName;
	
	@ItemField(name="nickname",uiType=UIType.Text,order=1,lengthByChar=20)
	private String nickname;
	
	private byte[] headerImg;
	
	@Children
	private Set<FriendVo> friendList =new HashSet<FriendVo>();
	
	@Parent
    private String groupId;

	@Override
	public int hashCode() {
		return (this.nodeType+this.id).hashCode();
	}

	public byte[] getHeaderImg() {
		return headerImg;
	}

	public void setHeaderImg(byte[] headerImg) {
		this.headerImg = headerImg;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof FriendVo)) {
			return false;
		}
		FriendVo fl = (FriendVo)obj;
		if(!this.nodeType.equals(fl.nodeType)) {
			return false;
		}
		return this.id.equals(fl.id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<FriendVo> getFriendList() {
		return friendList;
	}

	public void setFriendList(Set<FriendVo> friendList) {
		this.friendList = friendList;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}	
	
}
