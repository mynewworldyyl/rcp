package net.techgy.core.profile;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.core.db.AbstractPersistenceListener;
import com.digitnexus.core.vo.masterdata.ProfileVo;

@Component
public class ProfileProcessor extends AbstractPersistenceListener<Profile,ProfileVo> {

	@Autowired
	private ProfileDaoImpl materialDao;
	
	@Autowired
	private ProfileManager proManager;

	
	@Override
	public String getVoClsName() {
		return ProfileVo.class.getName();
	}

	@Override
	protected Profile voToEntityForSave(ProfileVo vo) {
		return this.proManager.voToEntityForSave(vo);
	}

	@Override
	protected Profile voToEntityForUpdate(ProfileVo vo) {
		return this.proManager.voToEntityForUpdate(vo);
	}

	@Override
	public Object beforeQuery(Class<?> cls, Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT a FROM Profile a WHERE 1=1 ");
		//Map<String,Object> queryParams = new HashMap<String,Object>();
		if(params != null && !params.isEmpty()) {
			for(Map.Entry<String, String> e : params.entrySet()) {
				String fn = e.getKey();
				String fv = e.getValue();
				if(fv == null || fv.trim().equals("")) {
					continue;
				}
				try {
					Field f = cls.getDeclaredField(fn);
					Object value = getOneCondition(fv,f);
					if(value != null) {
						sb.append(" OR a.").append(f.getName()).append(" LIKE '%" ).append(value).append("%' ");
					}
				} catch (NoSuchFieldException e1) {
				} catch (SecurityException e1) {
				}
			}
		}
		return sb.toString();
	}


	@Override
	public String[] beforeDelete(String clsName, String ids) {
		String[] idss = super.beforeDelete(clsName, ids);
		return idss;
	}

	@Override
	protected ProfileVo entityToVo(Profile vo) {
		return this.proManager.entityToVo(vo);
	}
	
}
