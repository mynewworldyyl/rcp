package net.techgy.cmty.ui.preference;

import java.util.Map;

import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("serial")
public abstract class PreferencePagePanel extends Composite {

	private String modelId;
	
	public PreferencePagePanel(Composite parent,int style,String modelId) {
		super(parent,style);
		this.modelId = modelId;
	}

	public void apply(){
		Map<String,String> params = this.getValues();
		if(params == null || params.isEmpty()) {
			return;
		}
		ProfileManager.getProfile().saveValues(this.modelId, params);
	}
	
	protected String getString(String key) {
		return ProfileManager.getProfile().getString(this.modelId, key, "");
	}
	
	protected Byte getByte(String key) {
		return ProfileManager.getProfile().getByte(this.modelId, key, "");
	}
	
	protected Short getShort(String key) {
		return ProfileManager.getProfile().getShort(this.modelId, key, (short)0);
	}
	
	protected Integer getInt(String key) {
		return ProfileManager.getProfile().getInt(this.modelId, key, 0);
	}
	
	protected Long getLong(String key) {
		return ProfileManager.getProfile().getLong(this.modelId, key, 0l);
	}
	
	protected Float getFloat(String key) {
		return ProfileManager.getProfile().getFloat(this.modelId, key, 0f);
	}
	
	protected byte[] getBytes(String key) {
		return ProfileManager.getProfile().getBytes(this.modelId, key);
	}
	
	protected String getContent(String key) {
		return ProfileManager.getProfile().getContent(modelId, key);
	}
	
	protected boolean saveData(String key, byte[] data) {
		return ProfileManager.getProfile().saveData(this.modelId, key, data);
	}
	
	protected boolean saveContent(String key, String content) {
		return ProfileManager.getProfile().saveContent(this.modelId, key, content);
	}
	
	public void cancel(){
		
	}
	
	public void ok(){
		apply();
	}
	
	public abstract void restore();
	
	public abstract void createContent();
	
	public abstract Map<String,String> getValues();
	
}
