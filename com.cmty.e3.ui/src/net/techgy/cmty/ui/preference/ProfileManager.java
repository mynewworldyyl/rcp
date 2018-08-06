package net.techgy.cmty.ui.preference;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.digitnexus.base.event.CmtyEventAdmin;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.core.vo.masterdata.ProfileVo;
import com.google.gson.reflect.TypeToken;

import net.techgy.cmty.service.DataDto;
import net.techgy.ui.core.network.WSConn;

public class ProfileManager {

	public static final String PROFILE_ID = ProfileManager.class.getName().replaceAll("\\.", "/")+"/";
	
	private Map<String,Map<String,ProfileVo>> profiles = new HashMap<String,Map<String,ProfileVo>>();
	
	public ProfileManager() {
		String an = null;// (String)RWT.getUISession().getAttribute(UIConstants.LOGIN_ACCOUNT);
		CmtyEventAdmin.registerEventHandler(PROFILE_ID+an, new EventHandler(){
			@Override
			public void handleEvent(Event event) {
				String data = (String)event.getProperty("profile");
				if(data == null) {
					return;
				}
				refleshProfile(data);
			}
		});
	}
	
	public boolean refleshProfile() {
		Map<String, String> params = new HashMap<String, String>();
		
		DataDto resp = WSConn.ins().call("profile","getProfile", params);
		if (resp.isSuccess()) {
			Type type = new TypeToken<HashMap<String,Map<String,ProfileVo>>>(){}.getType();
			Map<String,Map<String,ProfileVo>> ps = JsonUtils.getInstance()
					.fromJson(resp.getData(), type, false, true);
			this.refleshProfile(ps);
		}
		return resp.isSuccess();
	}
	

	public static ProfileManager getProfile() {
		return null;//SingletonUtil.getSessionInstance(ProfileManager.class);
	}
	
	public String getString(String modelId,String key,String defValue){
		 return getNotNullProfile(modelId,key,defValue);
	}
	
	public Byte getByte(String modelId,String key,String defValue){
		return Byte.valueOf(getNotNullProfile(modelId,key,defValue));
	}
	
	public byte[] getBytes(String modelId,String key){
		ProfileVo vo = this.getProfileVo(modelId, key);
		if(vo == null) {
			return null;
		}
		return vo.getData();
	}
	
	public String getContent(String modelId,String key){
		ProfileVo vo = this.getProfileVo(modelId, key);
		return vo.getContent();
	}
	
	public Short getShort(String modelId,String key,Short defValue){
		return Short.valueOf(getNotNullProfile(modelId,key,defValue));
	}
	
	public Integer getInt(String modelId,String key, int defValue){
		return Integer.valueOf(getNotNullProfile(modelId,key,defValue));
	}
	
	public Long getLong(String modelId,String key,Long defValue){
		return Long.valueOf(getNotNullProfile(modelId,key,defValue));
	}
	
	public Float getFloat(String modelId,String key,Float defValue){
		return Float.valueOf(getNotNullProfile(modelId,key,defValue));
	}
	
	public Boolean getBoolean(String modelId,String key){
		return Boolean.valueOf(getNotNullProfile(modelId,key,false));
	}
	
	
	public boolean setString(String modelId, String key, String value){
		return  saveValue(modelId,key,value);
	}
	
	public boolean setByte(String modelId, String key, byte value){
		return saveValue(modelId,key, value);
	}
	
	public boolean setShort(String modelId, String key, short value){
		return saveValue(modelId,key, value);
	}
	
	public boolean setInteger(String modelId, String key, int value){
		return saveValue(modelId,key, value);
	}
	
	public boolean setLong(String modelId, String key, long value){
		return  saveValue(modelId,key, value);
	}
	
	public boolean setFloat(String modelId, String key, float value){
		return saveValue(modelId,key, value);
	}
	
	public boolean setBoolean(String modelId, String key, boolean value){
		return this.saveValue(modelId, key, value);
	}
	
   public boolean saveContent(String modelId, String key, String content){
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("content", content);
		params.put("mid", modelId);
		params.put("key", key);
		DataDto resp = WSConn.ins().call("profile","setContent", params);
		//Response resp = Conn.ins().call("/profile/setContent", params);
		if (resp.isSuccess()) {
			updateOneProfileVo(resp.getData());
		}
		return resp.isSuccess();
	}

   public boolean saveData(String modelId, String key, byte[] data){
	
	Map<String, String> params = new HashMap<String, String>();
	params.put("data", JsonUtils.getInstance().toJson(data, true));
	params.put("mid", modelId);
	params.put("key", key);
	
	DataDto resp = WSConn.ins().call("profile", "setData", params);
	if (resp.isSuccess()) {
		this.updateOneProfileVo(resp.getData());
	}
	return resp.isSuccess();
}
	
	public boolean saveValue(String modelId, String key, Object value){
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("value", JsonUtils.getInstance().toJson(value, true));
		
		DataDto resp = WSConn.ins().call("profile", "setValue", params);
		//Response resp = Conn.ins().call("/profile/setValue", params);
		if (resp.isSuccess()) {
			this.updateOneProfileVo(resp.getData());
		}
		return resp.isSuccess();
	}
	
	public boolean saveValues(String modelId, Map<String,String> values){
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("mid", modelId);
		params.put("values", JsonUtils.getInstance().toJson(values, true));
		
		DataDto resp = WSConn.ins().call("profile", "setValues", params);
		
		if (resp.isSuccess()) {
			Type type = new TypeToken<List<ProfileVo>>(){}.getType();
			List<ProfileVo> l = JsonUtils.getInstance().fromJson(resp.getData(), type, false, true);
			for(ProfileVo vo : l) {
				this.updateOneProfileVo(vo);
			}
		}
		return resp.isSuccess();
	}
	
	private String getNotNullProfile(String modelId,String key,Object defValue) {
		ProfileVo value = this.getProfileVo(modelId, key);
		if(value == null) {
			if(defValue == null) {
				return "";
			}else {
				return defValue.toString();
			}
		}
		return value.getValue();
	}
	
	private ProfileVo getProfileVo(String modelId,String key) {
		Map<String,ProfileVo> modelProfile = profiles.get(modelId);
		if(modelProfile == null) {
			refleshProfile();
		}
		modelProfile = profiles.get(modelId);
		if(modelProfile == null ) {
			return null;
		} 
		return modelProfile.get(key);
	}
	
	private void refleshProfile(String json) {
		Type type = new TypeToken<HashMap<String,Map<String,ProfileVo>>>(){}.getType();
		Map<String,Map<String,ProfileVo>> ps = JsonUtils.getInstance()
				.fromJson(json, type, false, true);
		if(ps == null || ps.isEmpty()) {
			return;
		}
		this.refleshProfile(ps);
	}
	
	private void updateOneProfileVo(ProfileVo pvo) {
		if(pvo == null) {
			return;
		}
		if(profiles.containsKey(pvo.getMid())) {
			profiles.get(pvo.getMid()).put(pvo.getKey(),pvo);
		} else {
			Map<String,ProfileVo> modelProfile = new HashMap<String,ProfileVo>();
			modelProfile.put(pvo.getKey(), pvo);
			profiles.put(pvo.getMid(), modelProfile);
		}
	}
	
	private void updateOneProfileVo(String onvoJson) {
		ProfileVo pvo = JsonUtils.getInstance().fromJson(onvoJson, ProfileVo.class, false, false);
		if(pvo == null) {
			return;
		}
		updateOneProfileVo(pvo);
	}
	
	private void refleshProfile(Map<String,Map<String,ProfileVo>> ps) {
		if(ps == null || ps.isEmpty()) {
			return;
		}
		for(Entry<String,Map<String,ProfileVo>> e : ps.entrySet()) {
			String mid = e.getKey();
			Map<String,ProfileVo> p = e.getValue();
			if(profiles.containsKey(mid)) {
				profiles.get(mid).putAll(p);
			} else {
				Map<String,ProfileVo> modelProfile = new HashMap<String,ProfileVo>();
				modelProfile.putAll(p);
				profiles.put(mid, modelProfile);
			}
		}
	}
}
