package com.digitnexus.base.protocol;

import java.util.Date;

@Infos(
values = {
	@Info(
		name="asset_load_info",
		fields = {"name","attr0","modelType","projectName","statusCode","remark","height","width","length","siteName","crewName"}, 
		labels = {"��������","����",  "����ͺ�",    "��Ŀ����",      "����״̬",     "¯����",  "��",     "��",    "��",    "����",      "����"}
	),
	@Info(
		name="asset_accept_info",
		fields = {"name","attr0","modelType","projectName","statusCode","remark","height","width","length","siteName","crewName"}, 
		labels = {"��������","����",  "����ͺ�",    "��Ŀ����",      "����״̬",     "¯����",  "��",     "��",    "��",    "����",      "����"}
    )
	
}
)
public class Asset {

    private String clientId;

    private String id;
    
    private String typecode;

    private String subTypecode;

    private String statusCode;

    private Integer grpId;

    private String externalId;

    private String name;

    private String manufacture;

    private Double height;

    private Integer tagQuantity;

    private Double length;

    private Double width;

    private Double weight;

    private Double volumn;

    private String unit;

    private String unitPrice;

    private Integer unitQty;

    private String attr0;

    private String attr1;

    private String attr2;

    private String attr3;

    private String description;

    private String remark;

    private String tagId;

    private String barcode;

    private Long vehicleId;

    private Long fleetId;

    private Long packageId;

    private Long reqitemId;

    private Date createdOn;

    private Long createdBy;

    private Date updatedOn;

    private Long updatedBy;

    private String unitnDes;

    private String modelType;

    private String pricingMode;

    private String unitDes;
    
	private String factorAttr1 = null;
	private String factorAttr2 = null;
	
	private String reqExternalnum;
	
	private String projectName;
	private String siteName;
	private String crewName;
	
	
    public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getCrewName() {
		return crewName;
	}

	public void setCrewName(String crewName) {
		this.crewName = crewName;
	}

	public String getReqExternalnum() {
		return reqExternalnum;
	}

	public void setReqExternalnum(String reqExternalnum) {
		this.reqExternalnum = reqExternalnum;
	}

	public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode == null ? null : typecode.trim();
    }

    public String getSubTypecode() {
        return subTypecode;
    }

    public void setSubTypecode(String subTypecode) {
        this.subTypecode = subTypecode == null ? null : subTypecode.trim();
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode == null ? null : statusCode.trim();
    }

    public Integer getGrpId() {
        return grpId;
    }

    public void setGrpId(Integer grpId) {
        this.grpId = grpId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId == null ? null : externalId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture == null ? null : manufacture.trim();
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getTagQuantity() {
        return tagQuantity;
    }

    public void setTagQuantity(Integer tagQuantity) {
        this.tagQuantity = tagQuantity;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolumn() {
        return volumn;
    }

    public void setVolumn(Double volumn) {
        this.volumn = volumn;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice == null ? null : unitPrice.trim();
    }

    public Integer getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(Integer unitQty) {
        this.unitQty = unitQty;
    }

    public String getAttr0() {
        return attr0;
    }

    public void setAttr0(String attr0) {
        this.attr0 = attr0 == null ? null : attr0.trim();
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1 == null ? null : attr1.trim();
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2 == null ? null : attr2.trim();
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3 == null ? null : attr3.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId == null ? null : tagId.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getFleetId() {
        return fleetId;
    }

    public void setFleetId(Long fleetId) {
        this.fleetId = fleetId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public Long getReqitemId() {
        return reqitemId;
    }

    public void setReqitemId(Long reqitemId) {
        this.reqitemId = reqitemId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType == null ? null : modelType.trim();
    }

    
    public String getUnitnDes() {
        return unitnDes;
    }

    
    public void setUnitnDes(String unitnDes) {
        this.unitnDes = unitnDes;
    }

    
    public String getPricingMode() {
        return pricingMode;
    }

    public void setPricingMode(String pricingMode) {
        this.pricingMode = pricingMode;
    }

    public String getUnitDes() {
        return unitDes;
    }

    public void setUnitDes(String unitDes) {
        this.unitDes = unitDes;
    }

	public String getFactorAttr1() {
		return factorAttr1;
	}

	public void setFactorAttr1(String factorAttr1) {
		this.factorAttr1 = factorAttr1;
	}

	public String getFactorAttr2() {
		return factorAttr2;
	}

	public void setFactorAttr2(String factorAttr2) {
		this.factorAttr2 = factorAttr2;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    

}
