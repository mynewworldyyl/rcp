package com.digitnexus.core.vo.masterdata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.masterdata.MaterialType;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.Children;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.NamedParam;
import com.digitnexus.core.uidef.annotation.Parent;
import com.digitnexus.core.uidef.annotation.TreeViewEditor;
import com.digitnexus.core.vo.AbstractVo;

@TreeViewEditor(
		entityCls=MaterialType.class,
		queryConditions = {
				@ItemField(name="name",dataType=DataType.String,order=1),
				@ItemField(name="desc",dataType=DataType.String,order=2),
		},
		actions = {
		    @Action(name="Add", url="crudService/add",actionType=ActionType.Add,
		    		permClientTypes={ClientType.Admin,ClientType.Headquarter}),
			@Action(name="Delete", url="crudService/delete" ,actionType=ActionType.Delete,
		    		permClientTypes={ClientType.Admin,ClientType.Headquarter}),
			@Action(name="Query", url="crudService/query",actionType=ActionType.Query,
		    		permClientTypes={ClientType.Admin,ClientType.Region,ClientType.Vendor,
		    		ClientType.Project,ClientType.Headquarter,ClientType.Factory}),
			@Action(name="Save", url="crudService/save",actionType=ActionType.Save,
		    		permClientTypes={ClientType.Admin,ClientType.Headquarter}),
			@Action(name="Modify", url="crudService/update",actionType=ActionType.Modify,
		    		permClientTypes={ClientType.Admin,ClientType.Headquarter})
	}
)
public class MaterialTypeVo  extends AbstractVo implements Serializable, Comparable<MaterialTypeVo>{

	@ItemField(name="Name",uiType=UIType.Text,order=1,lengthByChar=20)	
	private String name;
	
	@ItemField(name="Descripton",uiType=UIType.Text,order=2,lengthByChar=20)
	private String desc;
	
	@ItemField(name="Code",uiType=UIType.Text,order=3,lengthByChar=20,editable=false)	
	private String code;
	
	@ItemField(name="ClientName",uiType=UIType.Text,order=4,lengthByChar=20,editable=false)	
	private String clientName;
	
	@Parent
	private String parentId = null;
	
	@Children
	private List<MaterialTypeVo> subs = new ArrayList<MaterialTypeVo>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<MaterialTypeVo> getSubs() {
		return subs;
	}

	public void setSubs(List<MaterialTypeVo> subs) {
		this.subs = subs;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public int compareTo(MaterialTypeVo o) {
		return this.name.compareTo(o.name);
	}
}
