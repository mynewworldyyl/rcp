package net.techgy.uidef.demo;

import java.util.List;

import net.techgy.uidef.UIConstants.DataType;
import net.techgy.uidef.UIConstants.UIType;
import net.techgy.uidef.annotation.QueryCondition;
import net.techgy.uidef.annotation.Req;
import net.techgy.uidef.annotation.Field;
import net.techgy.uidef.annotation.ReqItems;

@Req(
		queryConditions = {
				@QueryCondition(name="reqNum",dataType=DataType.String),
				@QueryCondition(name="Vendor",dataType=DataType.String),
		}
	)
public class RequestDemo {

	@Field(name="Request Num",uiType=UIType.Text)
	private String reqNum;
	
	@Field(name="Created Person",uiType=UIType.Text)
	private String createdPerson;
	
	@Field(name="Source Site",uiType=UIType.Text)
	private String sourceSite;
	
	@Field(name="Target Site",uiType=UIType.Text)
	private String targetSite;
	
	@Field(name="Check Person",uiType=UIType.Text)
	private String checkPerson;
	
	@ReqItems
	private List<RequestItemDemo> items = null;

	public String getReqNum() {
		return reqNum;
	}

	public void setReqNum(String reqNum) {
		this.reqNum = reqNum;
	}

	public String getCreatedPerson() {
		return createdPerson;
	}

	public void setCreatedPerson(String createdPerson) {
		this.createdPerson = createdPerson;
	}

	public String getSourceSite() {
		return sourceSite;
	}

	public void setSourceSite(String sourceSite) {
		this.sourceSite = sourceSite;
	}

	public String getTargetSite() {
		return targetSite;
	}

	public void setTargetSite(String targetSite) {
		this.targetSite = targetSite;
	}

	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	public List<RequestItemDemo> getItems() {
		return items;
	}

	public void setItems(List<RequestItemDemo> items) {
		this.items = items;
	}
	
	
}
