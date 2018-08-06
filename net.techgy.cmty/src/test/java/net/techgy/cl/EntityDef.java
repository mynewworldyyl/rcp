package net.techgy.cl;

public class EntityDef extends BaseEntity{

	public static final long serialVersionUID=Long.valueOf(BASE_SERIA_KEY+EntityDef.class.hashCode());
	
	private String typecode;
	private String targetEntity;
	private String name;
	private String createUrl;
	private String queryUrl;
	private String delUrl;
	private String updateUrl;

	private String ext4;
	private String ext5;
	public String getTypecode() {
		return typecode;
	}
	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	public String getTargetEntity() {
		return targetEntity;
	}
	public void setTargetEntity(String targetEntity) {
		this.targetEntity = targetEntity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreateUrl() {
		return createUrl;
	}
	public void setCreateUrl(String createUrl) {
		this.createUrl = createUrl;
	}
	public String getQueryUrl() {
		return queryUrl;
	}
	public void setQueryUrl(String queryUrl) {
		this.queryUrl = queryUrl;
	}
	public String getDelUrl() {
		return delUrl;
	}
	public void setDelUrl(String delUrl) {
		this.delUrl = delUrl;
	}
	public String getUpdateUrl() {
		return updateUrl;
	}
	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}
	public String getExt4() {
		return ext4;
	}
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}
	public String getExt5() {
		return ext5;
	}
	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}
	
}
