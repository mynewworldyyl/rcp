package net.techgy.core.profile;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.account.Account;
import com.digitnexus.core.account.AccountManager;
import com.digitnexus.core.idgenerator.CacheBaseIDManager;
import com.digitnexus.core.vo.masterdata.ProfileVo;

@Component
public class ProfileManager {

	@Autowired
	private ProfileDaoImpl proDao;
	
	@Autowired
	private CacheBaseIDManager generator;
	
	@Autowired
	private AccountManager acManager;
	
	public Profile getKeyValue(String mid,String key,String accoutName) {
		return (Profile)this.createNameQuery(mid, "getProfileForKeyValue", key, accoutName)
				.getSingleResult();
	}
	
	public Profile getContent(String mid,String key,String accoutName) {
		return (Profile)this.createNameQuery(mid, "getProfileForContent", key, accoutName)
				.getSingleResult();
	}
	
	public Profile getData(String mid,String key,String accoutName) {
		return (Profile)this.createNameQuery(mid, "getProfileForData", key, accoutName)
				.getSingleResult();
	}
	
	public Profile getProfile(String mid,String key,String accoutName) {
		try {
			return (Profile)this.createNameQuery(mid, "getProfile", key, accoutName)
					.getSingleResult();
		} catch (NoResultException e) {
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Profile> getProfile(String accoutName) {
		if(accoutName == null) {
			accoutName = UserContext.getAccount().getAccountName();
		}
		return proDao.getEntityManager().createNamedQuery("getProfileByAccount")
				.setParameter("an", accoutName)
				.getResultList();
	}
	
	private Query createNameQuery(String mid,String nq,String key,String accoutName){
		return proDao.getEntityManager().createNamedQuery(nq)
				.setParameter("an", accoutName)
				.setParameter("key", key)
				.setParameter("mid", mid);
	}
	
	public void save(Profile pro) {
		if(pro.getId() == null) {
			pro.setId(generator.getStringId(Profile.class));
		}
		proDao.save(pro);
	}
	
	public void save(ProfileVo vo) {
		Profile p =this.voToEntityForSave(vo);
		this.save(p);
	}
	
	public Profile voToEntityForUpdate(ProfileVo vo) {
		Profile p = this.getProfile(vo.getMid(), vo.getKey(), 
				UserContext.getAccount().getAccountName());
		p.setValue(vo.getValue());
		p.setContent(vo.getContent());
		return p;
	}
	
	public Profile voToEntityForSave(ProfileVo vo) {
		if(vo.getId() == null) {
			vo.setId(generator.getStringId(Profile.class));
		}
		Profile existPro = null;
		
		Profile p = new Profile();
		p.setId(vo.getId());
		
		if(vo.getAccountName() == null) {
			throw new CommonException("AccountNameCannotBeNull",vo.getKey());
		}
		
		try {
			 existPro = (Profile)this.createNameQuery(vo.getMid(), "getProfile", vo.getKey(),
					 vo.getAccountName()).getSingleResult();
		} catch (Exception e) {
		}
		
		if(existPro != null) {
			throw new CommonException("ProfileKeyRepeatForAccount",vo.getAccountName(),vo.getKey());
		}
		
		baseVoToProfile(vo,p);
		return p;
	}
	
	private void baseVoToProfile(ProfileVo vo,Profile p) {
		String cid = UserContext.getCurrentClientId();
		Account a = this.acManager.getActieAccountByName(vo.getAccountName(), cid);
		p.setAccount(a);
		p.setAvaiValues(vo.getAvaiValues());
		p.setClient(UserContext.getCurrentUser().getLoginClient());
		p.setContent(vo.getContent());
		p.setDataType(vo.getDataType());
		p.setDefValue(vo.getDefValue());
		p.setKey(vo.getKey());
		p.setMaxValue(vo.getMaxValue());
		p.setMinValue(vo.getMinValue());
		p.setProviders(vo.getProviders());
		p.setTypecode(vo.getTypecode());
		p.setUiType(vo.getUiType());
		p.setValidators(vo.getValidators());
		p.setValue(vo.getValue());
		p.setData(vo.getData());
	}
	
	public void updateKeyValue(String mid, String key,String value) {
		Profile p = this.getProfile(mid, key, UserContext.getAccount().getAccountName());
		p.setValue(value);
		this.update(p);
	}
	
	public void updateContent(String mid, String key,String content) {
		Profile p = this.getProfile(mid, key, UserContext.getAccount().getAccountName());
		p.setContent(content);;
		this.update(p);
	}
	
	public void deleteByKey(String key) {
		proDao.getEntityManager().createNamedQuery("deleteProfileByKey")
		.setParameter("an", UserContext.getAccount().getAccountName())
		.setParameter("key", key)
		.executeUpdate();
	}
	
	public ProfileVo entityToVo(Profile vo) {
		ProfileVo p = new ProfileVo();
		p.setAccountName(vo.getAccount().getAccountName());
		p.setAvaiValues(vo.getAvaiValues());
		p.setClientId(vo.getClient().getId());
		p.setContent(vo.getContent());
		p.setDataType(vo.getDataType());
		p.setDefValue(vo.getDefValue());
		p.setKey(vo.getKey());
		p.setMaxValue(vo.getMaxValue());
		p.setMinValue(vo.getMinValue());
		p.setProviders(vo.getProviders());
		p.setTypecode(vo.getTypecode());
		p.setUiType(vo.getUiType());
		p.setValidators(vo.getValidators());
		p.setValue(vo.getValue());
		p.setData(vo.getData());
		return p;
	}
	
	public void update(Profile pro) {
		proDao.update(pro);
	}
	
	public void delete(Profile pro) {
		this.delete(pro.getId());
	}
	
	public void delete(String id) {
		proDao.remove(Profile.class, id);
	}

	public ProfileVo saveOUpdateKeyValue(String mid, String key, String value) {
		Account a = UserContext.getAccount();
		Profile p = this.getProfile(mid,key, a.getAccountName());
		if(p != null) {
			this.updateKeyValue(mid, key, value);
		} else {
			p = newProfile(mid,key);
			p.setTypecode(Profile.TYPE_KEY_VALUE);
			p.setValue(value);
			this.save(p);
		}
		return this.entityToVo(p);
	}
	
	private Profile newProfile(String mid, String key) {

		Profile p = new Profile();
		p.setId(this.generator.getStringId(Profile.class));
		p.setAccount(UserContext.getAccount());
		p.setModelId(mid);
		p.setAvaiValues(null);
		p.setClient(UserContext.getCurrentUser().getLoginClient());
		p.setContent("");
		p.setDataType("string");
		p.setDefValue("");
		p.setKey(key);
		p.setMaxValue(-1);
		p.setMinValue(-1);
		p.setProviders(null);
		p.setUiType(UIType.Text.name());
		p.setValidators(null);
		
		return p;
	
	}
	
	public ProfileVo saveOUpdateContent(String mid, String key, String content) {
		Account a = UserContext.getAccount();
		Profile p = this.getProfile(mid,key, a.getAccountName());
		if(p != null) {
			this.updateContent(mid, key, content);
		} else {
			p = this.newProfile(mid,key);
			p.setContent(content);
			p.setTypecode(Profile.TYPE_CONTENT);
			this.save(p);
		}
		return this.entityToVo(p);
	}
	
	public ProfileVo saveOUpdateBinary(String mid, String key, byte[] data) {
		Account a = UserContext.getAccount();
		Profile p = this.getProfile(mid,key, a.getAccountName());
		if(p != null) {
			this.updateBinary(mid, key, data);
		} else {
			p = this.newProfile(mid,key);
			p.setData(data);
			p.setTypecode(Profile.TYPE_BINARY);
			this.save(p);
		}
		return this.entityToVo(p);
	}

	private void updateBinary(String mid, String key, byte[] data) {
		Profile p = this.getProfile(mid, key, UserContext.getAccount().getAccountName());
		p.setData(data);;
		this.update(p);
		
	}
	
}
