package net.techgy.cmty.core.im.vo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import net.techgy.cmty.core.im.FriendGroup;
import net.techgy.cmty.core.im.ImAccount;

import com.digitnexus.core.account.Account;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.vo.dept.AccountVo;

public class ImAccountVo {

	public static final String SERVICE_NAME = "/imService/";
	
	public static final String ROOM_SERVICE = "/roomService/";
	
	public static final String ACT_LOGIN = SERVICE_NAME + "loginIm";
	
	public static final String ACT_GET_MSG = SERVICE_NAME + "getMsg";
	
	public static final String ACT_LOGOUT =  SERVICE_NAME + "loginoutIm";
	
	public static final String ACT_FRIEND_LIST =  SERVICE_NAME + "getFriendList";
	
	public static final String ACT_FRIEND_QUERY =  SERVICE_NAME + "queryFriend";
	
	public static final String ACT_ADD_FRIEND =  SERVICE_NAME + "addFriend";
	
	public static final String ACT_UPDATE_IM_ACCOUNT =  SERVICE_NAME + "updateImAccount";
	
	public static final String ACT_CREATE_GRP=  SERVICE_NAME + "createGroup";
	
	public static final String ACT_UPDATE_GRP=  SERVICE_NAME + "updateGroup";
	
	public static final String ACT_DELETE_GRP =  SERVICE_NAME + "deleteGroup";
	
	public static final String ACT_MOVE_FRIEND =  SERVICE_NAME + "moveFriendToGroup";
	
	public static final String ACT_DELETE_FRIEND =  SERVICE_NAME + "deleteFriend";
	
	public static final String ACT_LOAD_ROOM_MEMS =  ROOM_SERVICE + "loadChatRoomsAcces";
	public static final String ACT_ROOM_CREATE =  ROOM_SERVICE + "create";
	public static final String ACT_ROOM_DELETE =  ROOM_SERVICE + "delete";
	public static final String ACT_ROOM_UPDATE =  ROOM_SERVICE + "update";
	public static final String ACT_ROOM_QUERY =  ROOM_SERVICE + "query";
	
	//主动加入聊天组
	public static final String ACT_ROOM_ADD =  ROOM_SERVICE + "add";
	//被别人加入聊天组
	public static final String ACT_ROOM_ADDED =  ROOM_SERVICE + "added";
	
	public static final String ACT_SEND = SERVICE_NAME + "sendMsg";
	
	private String id;
	
	private AccountVo accountVo;
	
	private Set<FriendVo> friendGroups = new HashSet<FriendVo>();
	
	private Set<ChatRoomVo> chatRooms = new HashSet<ChatRoomVo>();
	
	private String typecode = ImAccount.IM_TYPECODE_COMMON;
	
	private String nickname;
	
	private byte[] headerImg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AccountVo getAccountVo() {
		return accountVo;
	}

	public void setAccountVo(AccountVo accountVo) {
		this.accountVo = accountVo;
	}

	public Set<FriendVo> getFriendGroups() {
		return friendGroups;
	}

	public void setFriendGroups(Set<FriendVo> friendGroups) {
		this.friendGroups = friendGroups;
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

	public Set<ChatRoomVo> getChatRooms() {
		return chatRooms;
	}

	public void setChatRooms(Set<ChatRoomVo> chatRooms) {
		this.chatRooms = chatRooms;
	}

	public byte[] getHeaderImg() {
		return headerImg;
	}

	public void setHeaderImg(byte[] headerImg) {
		this.headerImg = headerImg;
	}
}
