package net.techgy.core.profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.base.utils.Utils;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.vo.masterdata.ProfileVo;

@Component
@Path("/profile")
@Scope("singleton")
public class ProfileService {

	@Autowired
	private ProfileManager pm;
	
	@POST
	@Path("/setValue")
	@Transactional
	public String setValue(@FormParam("mid") String mid,
			@FormParam("key") String key,@FormParam("value") String value ) {
		ProfileVo vo = null;
		try {
			vo = pm.saveOUpdateKeyValue(mid,key,value);
		} catch (CommonException e) {
			return Utils.getInstance().getResponse(null, false, e.getMessage());
		}
		return Utils.getInstance().getResponse(vo, true, null);
	}
	
	@POST
	@Path("/setValues")
	@Transactional
	public String setValues(@FormParam("mid") String mid,
			@FormParam("values") String json) {
		List<ProfileVo> vos = new ArrayList<ProfileVo>();
		try {
			Map<String,String> kvalues = JsonUtils.getInstance().getStringMap(json, true);
			if(kvalues == null) {
				return Utils.getInstance().getResponse(null, false,"NoValues");
			}
			for(Map.Entry<String, String> e : kvalues.entrySet()) {
				ProfileVo vo = pm.saveOUpdateKeyValue(mid,e.getKey(),e.getValue());
				if(vo != null) {
					vos.add(vo);
				}
			}
		} catch (CommonException e) {
			return Utils.getInstance().getResponse(null, false, e.getMessage());
		}
		return Utils.getInstance().getResponse(vos, true, null);
	}
	
	
	@POST
	@Path("/setContent")
	@Transactional
	public String setContent(@FormParam("mid") String mid,
			@FormParam("key") String key,@FormParam("content") String content ) {
		ProfileVo vo = null;
		try {
			vo = pm.saveOUpdateContent(mid, key, content);
		} catch (CommonException e) {
			return Utils.getInstance().getResponse(null, false, e.getMessage());
		}
		return Utils.getInstance().getResponse(vo, true, null);
	}
	
	
	@POST
	@Path("/setData")
	@Transactional
	public String setData(@FormParam("mid") String mid,
			@FormParam("key") String key, @FormParam("data") byte[] data ) {
		ProfileVo vo = null;
		try {
			vo = pm.saveOUpdateBinary(mid, key, data);
		} catch (CommonException e) {
			return Utils.getInstance().getResponse(null, false, e.getMessage());
		}
		return Utils.getInstance().getResponse(vo, true, null);
	}
	
	
	@POST
	@Path("/getProfile")
	@Transactional
	public String getProfile() {
		List<Profile> pl = pm.getProfile(UserContext.getAccount().getAccountName());
		if(pl == null || pl.isEmpty()) {
			return Utils.getInstance().getResponse(null, false, "NotAnyProfieFound");
		}
		Map<String,Map<String,ProfileVo>> ps = new HashMap<String,Map<String,ProfileVo>>();
		
		for(Profile p : pl) {
			Map<String,ProfileVo> modelProfile = ps.get(p.getModelId());
			if(modelProfile == null) {
				modelProfile = new HashMap<String,ProfileVo>();
				ps.put(p.getModelId(), modelProfile);
			}
			modelProfile.put(p.getKey(), pm.entityToVo(p));
		}
		return Utils.getInstance().getResponse(ps, true, null);
	}
	
}
