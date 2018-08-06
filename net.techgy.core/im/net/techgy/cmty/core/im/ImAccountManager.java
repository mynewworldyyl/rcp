package net.techgy.cmty.core.im;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.techgy.cmty.core.im.vo.ChatRoomVo;
import net.techgy.cmty.core.im.vo.FriendVo;
import net.techgy.cmty.core.im.vo.ImAccountVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.utils.Utils;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.account.AccountManager;
import com.digitnexus.core.idgenerator.CacheBaseIDManager;

@Component
public class ImAccountManager {

	private static final String DEFALUT_HEADER_NAME = "/img/ti02.png";
	
	private static byte[] DEFAULT_HEADER_IMG = null;
	
	static {
		DEFAULT_HEADER_IMG = Utils.getInstance().loadStreamAsBtyes(
				ImChatRoomManager.class.getResourceAsStream(DEFALUT_HEADER_NAME));
	}
	
	@Autowired
	private AccountManager am;
	
	@Autowired
	private FriendGroupDaoImpl fgDao;
	
	@Autowired
	private ImAccountDaoImpl iaDao;
	
	@Autowired
	private AccountManager imam;
	
	@Autowired
	private CacheBaseIDManager generator;
	
	@SuppressWarnings("unchecked")
	public Set<FriendVo> getFriends() {
		Set<FriendVo> fl = null;
	    
		ImAccount ia = this.getImAccount(UserContext.getAccount().getAccountName());
		
	    if(ia == null) {
	    	throw new CommonException("AccountNotFound",UserContext.getAccount().getAccountName());
		}
		
		if(ia.getFriendGroups().isEmpty()) {
			createGroup(ia,FriendGroup.DEFAULT_GROUP_NAME);
			 ia = this.getImAccount(UserContext.getAccount().getAccountName());
		}
		
		fl = this.getFriendGroupList(ia.getFriendGroups());
		return fl;
	}
	
	public ImAccount getImAccount(String an) {
		ImAccount a = null;
		if(an == null) {
			throw new CommonException("AccountNotFound",an);
		}
		try {
			a = (ImAccount)iaDao.getEntityManager()
					.createNamedQuery("getImAccountByAccountName")
					.setParameter("an", an)
					.getSingleResult();
			if(a.getNickname() == null) {
				a.setNickname(a.getAccount().getAccountName());
			}
		} catch (NoResultException e) {
			if(an != null && UserContext.getAccount().getAccountName().equals(an.trim())) {
				return createImAccountForCurrentLogin();
			} else {
				throw new CommonException("AccountNotFound",an);
			}
		}
		if(a != null) {
			if(a.getFriendGroups().isEmpty()) {
				this.createGroup(a,FriendGroup.DEFAULT_GROUP_NAME);
			}
		}
		return a;
	}
	
	public ImAccount createImAccountForCurrentLogin() {
		ImAccount ia = new ImAccount();
		ia.setAccount(UserContext.getAccount());
		ia.setClient(UserContext.getAccount().getClient());
		ia.setId(this.generator.getStringId(ImAccount.class));
		ia.setNickname(ia.getAccount().getAccountName());
		ia.setTypecode(ImAccount.IM_TYPECODE_COMMON);
		ia.setHeaderImg(DEFAULT_HEADER_IMG);
		this.iaDao.save(ia);
		this.createGroup(ia,FriendGroup.DEFAULT_GROUP_NAME);
		return ia;
	}

	public ImAccountVo getImAccountVo(String an,boolean forRoom) {
		ImAccount ima = this.getImAccount(an);
		
		ImAccountVo vo = new ImAccountVo();
		vo.setAccountVo(this.am.getOneVo(ima.getAccount()));
		//friends
		if(!forRoom) {
			vo.getFriendGroups().addAll(this.getFriendGroupList(ima.getFriendGroups()));
		}
		
		vo.setId(ima.getId());
		vo.setNickname(ima.getNickname());
		vo.setTypecode(ima.getTypecode());
		vo.setHeaderImg(ima.getHeaderImg());
		//chat rooms lazy to load the room account
		if(!forRoom) {
			vo.getChatRooms().addAll(this.getChatRooms(ima.getChatRooms(),ima,false));
		}
		
		return vo;
	}
	
	/**
	 * 
	 * @param chatRooms
	 * @param ima
	 * @param isLoadImAcco whether load the account in this room (true), false will not
	 * @return
	 */
	public List<ChatRoomVo> getChatRooms(Set<ChatRoom> chatRooms,
			ImAccount ima, boolean isLoadImAcco) {
		if(chatRooms == null || chatRooms.isEmpty()) {
			return Collections.emptyList();
		}
		List<ChatRoomVo> l = new ArrayList<ChatRoomVo>();
		
		for(ChatRoom cr : chatRooms) {
			l.add(this.getChatRoomVo(cr, ima.getAccount().getAccountName(), isLoadImAcco));
		}		
		return l;
	}
	
	public ChatRoomVo getChatRoomVo(ChatRoom cr, String an, boolean isLoadImAcco) {
		ChatRoomVo vo = new ChatRoomVo();
		vo.setAccountName(an);
		vo.setId(cr.getId());
		vo.setName(cr.getName());
		vo.setTypecode(cr.getTypecode());
		vo.setAccountName(cr.getAccount().getAccount().getAccountName());
		vo.setHeaderImg(cr.getHeaderImg());
		if(isLoadImAcco) {
			vo.getAcces().addAll(this.getImAccountForRoom(cr.getAcces()));
		}
		return vo;
	}

	private Collection<? extends FriendVo> getImAccountForRoom(Set<ImAccount> acces) {
		if(acces == null || acces.isEmpty()) {
			return Collections.emptyList();
		}
		List<FriendVo> l = new ArrayList<FriendVo>();
		for(ImAccount a : acces) {
			FriendVo vo = new FriendVo();
			vo.setAccountName(a.getAccount().getAccountName());
			vo.setId(a.getId());
			vo.setName(vo.getAccountName());
			vo.setNickname(vo.getAccountName());
			vo.setHeaderImg(a.getHeaderImg());
			l.add(vo);
		}
		return l;
	}

	private Set<FriendVo> getFriendGroupList(Set<FriendGroup> friendGroups) {
		Set<FriendVo> fl = new HashSet<FriendVo>();
		for(FriendGroup fg: friendGroups) {
			fl.add(fgEntityToVo(fg));
		}
		return fl;
	}

	public FriendGroup getFriendGroupByGroupID(String gid, String an) {
		if(gid == null) {
			throw new CommonException("AccountNotFound",gid);
		}
		if(an == null || an.trim().equals("")) {
			an = UserContext.getAccount().getAccountName();
		}
		try {
			 return (FriendGroup) fgDao.getEntityManager()
				.createNamedQuery("getFriendGroupByGid")
				.setParameter("an", an)
				.setParameter("gid", gid)
				.getSingleResult();
		} catch (NoResultException e) {
			//throw new CommonException("ImGroupNotFound",gn);
		}
		return null;
	}
	
	public FriendGroup getFriendGroup(String gn,String an) {
		if(gn == null) {
			throw new CommonException("AccountNotFound",gn);
		}
		if(an == null || an.trim().equals("")) {
			an = UserContext.getAccount().getAccountName();
		}
		try {
			 return (FriendGroup) fgDao.getEntityManager()
				.createNamedQuery("getFriendGroupByGn")
				.setParameter("an", an)
				.setParameter("gn", gn)
				.getSingleResult();
		} catch (NoResultException e) {
			//throw new CommonException("ImGroupNotFound",gn);
		}
		return null;
	}
	
	public boolean deleteFriend(String an, String gid) {
		ImAccount a = this.getImAccount(an);
		if(gid == null) {
			gid = FriendGroup.DEFAULT_GROUP_NAME;
		}
		
		FriendGroup fg = this.getFriendGroupByGroupID(gid,UserContext.getAccount().getAccountName());
		if(fg != null) {
			 fg.getFriends().remove(a);
			 this.fgDao.update(fg);
			 return true;
		}
	   return false;
		
	}	
	
	public boolean addFriend(String an,String groupName) {
		ImAccount ia = this.getImAccount(an);
		if(groupName == null || "".equals(groupName.trim())) {
			groupName = FriendGroup.DEFAULT_GROUP_NAME;
		}
		
		FriendGroup fg = null;
		try {
			fg = this.getFriendGroup(groupName, UserContext.getAccount().getAccountName());
		} catch (CommonException e) {
			fg = createGroup(ia,FriendGroup.DEFAULT_GROUP_NAME);
		}
		
		fg.getFriends().add(ia);
		this.fgDao.update(fg);
		
		return true;
	}

	public FriendGroup createGroup(ImAccount ia, String gn) {
		if(gn == null || "".equals(gn.trim())) {
			 gn = FriendGroup.DEFAULT_GROUP_NAME;
		}
		
		FriendGroup existGroup = this.getFriendGroup(gn, ia.getAccount().getAccountName());
		if(existGroup != null) {
			throw new CommonException("GroupExist",ia.getAccount().getAccountName(),gn);
		}
		FriendGroup fg = new FriendGroup();
		//host account
		fg.setAccount(ia);
		fg.setClient(UserContext.getCurrentUser().getLoginClient());
		fg.setId(generator.getStringId(FriendGroup.class));
		fg.setName(gn);
		//add friend
		ia.getFriendGroups().add(fg);
		this.fgDao.save(fg);
		return fg;
	}

	private FriendVo fgEntityToVo(FriendGroup fg) {
		FriendVo gvo = new FriendVo();
		gvo.setGroupId(null);
		gvo.setId(fg.getId());
		gvo.setName(fg.getName());
		gvo.setNickname(fg.getName());
		gvo.setAccountName(fg.getAccount().getAccount().getAccountName());
		gvo.nodeType = FriendVo.class.getName();
		
		if(fg.getFriends() == null || fg.getFriends().isEmpty()) {
			return gvo;
		}
		
		for(ImAccount a : fg.getFriends()) {
			FriendVo fvo = imAccountToVo(a);
			fvo.setGroupId(fg.getId());
			gvo.getFriendList().add(fvo);
		}
		return gvo;
	}

	private FriendVo imAccountToVo(ImAccount a) {
		FriendVo fvo = new FriendVo();
		fvo.nodeType = ImAccountVo.class.getName();
		
		fvo.setId(a.getId());
		fvo.setName(a.getAccount().getAccountName());
		fvo.setAccountName(a.getAccount().getAccountName());
		fvo.setNickname(a.getNickname());
		fvo.setHeaderImg(a.getHeaderImg());
		return fvo;
	}

	public boolean deleteGroup(String gn) {
		if(gn == null) {
			return false;
		}
		
		if(FriendGroup.DEFAULT_GROUP_NAME.equals(gn)) {
			throw new CommonException("CannotDeleteDefaultGroup",gn);
		}
		
		FriendGroup fg = this.getFriendGroup(gn,null);
		
		if(!fg.getFriends().isEmpty()){
			if(FriendGroup.DEFAULT_GROUP_NAME.equals(gn)) {
				throw new CommonException("CannotDeleteNotEmptyGroup",gn);
			}
		}
		
		this.fgDao.removeById(FriendGroup.class, fg.getId());
		
		return true;
	}

	/**
	 * 
	 * @param newGroup
	 * @return
	 */
	public boolean updateFriendGroup(FriendVo newGroup) {
		FriendGroup fg = (FriendGroup) fgDao.find(FriendGroup.class, newGroup.getId());
	
		if(fg == null) {
			new CommonException("SystemError");
		}
		fg.setName(newGroup.getName());
		
		this.fgDao.update(fg);
	
		return true;
	}

	public boolean moveFriendToGroup(String an, String fromId, String toId) {
		
		ImAccount ia = this.getImAccount(an);
		
		FriendGroup fromGrp = (FriendGroup) fgDao.find(FriendGroup.class, fromId);
		FriendGroup toGrp = (FriendGroup) fgDao.find(FriendGroup.class, toId);
		
		if(toGrp != null) {
			toGrp.getFriends().add(ia);
		}
		
		if(fromGrp != null) {
			fromGrp.getFriends().remove(ia);
		}
		
		return true;
	}

	public List<FriendVo>  queryFriend(String an, String aid) {

		StringBuffer sb =new StringBuffer("SELECT a FROM ImAccount a WHERE 1=1 ");
		
		if(aid != null && !"".equals(aid.trim())) {
			sb.append(" AND a.id='").append(aid).append("' ");
		}else if(an != null && !"".equals(an.trim())) {
			sb.append(" AND a.account.accountName LIKE  '%")
			.append(an).append("%' ");
		}
		
		Query q = this.iaDao.getEntityManager().createQuery(sb.toString());
		
		List<ImAccount> l = q.getResultList();
		if(l == null || l.isEmpty()) {
			return null;
		}
		
		List<FriendVo> fl = new ArrayList<FriendVo>();
		
		for(ImAccount a : l) {
			FriendVo fvo = imAccountToVo(a);
			fl.add(fvo);
		}
		return fl;
	}

	public void updateImAccount(ImAccountVo vo) {
		ImAccount imAccount = getImAccount(vo.getAccountVo().getName());
		imAccount.setNickname(vo.getNickname());
		imAccount.setHeaderImg(vo.getHeaderImg());
		this.iaDao.update(imAccount);
	}
	
}
