package net.techgy.cmty.core.im.vo;

import java.util.HashSet;
import java.util.Set;

import net.techgy.cmty.core.im.ChatRoom;


public class ChatRoomVo {

	private String id;
	
	private String accountName;
	
	private Set<FriendVo> acces = new HashSet<FriendVo>();
	
	private String name;
	
	private String typecode = ChatRoom.TYPE_COMMON;
	
	public String nodeType = ChatRoomVo.class.getName();
	
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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

	public Set<FriendVo> getAcces() {
		return acces;
	}
	
	
}
