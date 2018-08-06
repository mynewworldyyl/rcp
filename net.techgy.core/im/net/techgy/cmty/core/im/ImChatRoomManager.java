package net.techgy.cmty.core.im;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import net.techgy.cmty.core.im.vo.ChatRoomVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.utils.Utils;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.account.AccountManager;
import com.digitnexus.core.idgenerator.CacheBaseIDManager;

@Component
public class ImChatRoomManager {

	private static final String DEFALUT_HEADER_NAME = "/img/ti01.png";
	
	private static byte[] DEFAULT_HEADER_IMG = null;
	
	@Autowired
	private ImAccountManager am;
	
	@Autowired
	private ImAccountDaoImpl iaDao;
	
	@Autowired
	private AccountManager imam;
	
	@Autowired
	private CacheBaseIDManager generator;
	
	public ChatRoomVo loadChatRoomsAcces(String rid) {
		if(DEFAULT_HEADER_IMG == null) {
			DEFAULT_HEADER_IMG = Utils.getInstance().loadStreamAsBtyes(
					ImChatRoomManager.class.getResourceAsStream(DEFALUT_HEADER_NAME));
		}
		ChatRoom cr = this.getChatRoom(rid);
		return am.getChatRoomVo(cr, UserContext.getAccount().getAccountName(), true);
	}
	
	public ChatRoom getChatRoom(String rid) {
		 return (ChatRoom)this.iaDao.getEntityManager().createNamedQuery("getChatRoomByRid")
			.setParameter("rid", rid)
			.getSingleResult();
	}

	public ChatRoomVo createRoom(String rn) {
		ChatRoom cr = new ChatRoom();
		ImAccount ima = am.getImAccount(UserContext.getAccount().getAccountName());
		cr.setAccount(ima);
		cr.setClient(UserContext.getAccount().getClient());
		cr.setId(this.generator.getStringId(ChatRoom.class));
		cr.setName(rn);
		cr.setTypecode(ChatRoom.TYPE_COMMON);
		cr.getAcces().add(ima);
		cr.setHeaderImg(DEFAULT_HEADER_IMG);
		ima.getChatRooms().add(cr);
		iaDao.getEntityManager().persist(cr);
		ChatRoomVo vo = am.getChatRoomVo(cr, ima.getAccount().getAccountName(), false);
		return vo;
	}

	public String deleteRoom(String rid) {
	    iaDao.getEntityManager().createNamedQuery("removeChatRoomById")
	    .setParameter("rid", rid)
	    .executeUpdate();
		return rid;
	}

	public ChatRoomVo updateRoom(ChatRoomVo roomVo) {
		/*ChatRoom cr = new ChatRoom();
		ImAccount ima = am.getImAccount(UserContext.getAccount().getAccountName());
		cr.setAccount(ima);
		cr.setClient(UserContext.getAccount().getClient());
		cr.setId(this.generator.getStringId(ChatRoom.class));
		cr.setName(rn);
		cr.setTypecode(ChatRoom.TYPE_COMMON);*/
		return null;
	}

	public List<ChatRoomVo> queryRoom(String rn, String rid) {
		StringBuffer sb =new StringBuffer("SELECT a FROM ChatRoom a WHERE 1=1 ");
		
		if(rid != null && !"".equals(rid.trim())) {
			sb.append(" AND a.id='").append(rid).append("' ");
		}else if(rn != null && !"".equals(rn.trim())) {
			sb.append(" AND a.name LIKE  '%")
			.append(rn).append("%' ");
		}
		
		Query q = this.iaDao.getEntityManager().createQuery(sb.toString());
		List<ChatRoom> l = q.getResultList();
		if(l == null || l.isEmpty()) {
			return null;
		}
		
		ImAccount ima = this.am.getImAccount(l.get(0).getAccount()
				.getAccount().getAccountName());
		
		Set<ChatRoom> set = new HashSet<ChatRoom>();
		set.addAll(l);
		
		List<ChatRoomVo> lvos = this.am.getChatRooms(set, ima, false);
		
		return lvos;
	}

	public boolean add(String rid) {
		return this.added(UserContext.getAccount().getAccountName(), rid);
	}

	public boolean added(String an, String rid) {
		ChatRoom cr = this.getChatRoom(rid);
		if(cr == null) {
			throw new CommonException("ChatRoomHaveBeenDeleted",rid);
		}
		
		ImAccount ima = this.am.getImAccount(an);
		if(ima == null) {
			throw new CommonException("AccountException",an);
		}
		
		cr.getAcces().add(ima);
		ima.getChatRooms().add(cr);
		this.iaDao.getEntityManager().merge(cr);
		
		return true;
	}
	
}
