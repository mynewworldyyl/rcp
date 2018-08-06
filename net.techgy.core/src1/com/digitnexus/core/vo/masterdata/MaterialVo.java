package com.digitnexus.core.vo.masterdata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.dept.Employee;
import com.digitnexus.core.masterdata.Material;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.Children;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.NamedParam;
import com.digitnexus.core.uidef.annotation.Parent;
import com.digitnexus.core.uidef.annotation.TreeViewEditor;
import com.digitnexus.core.vo.AbstractVo;

@TreeViewEditor(
		name="Material",
		entityCls=Material.class,		
		queryConditions = {
				@ItemField(name="name",dataType=DataType.String,order=1),		
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
	},
		namedParams = {
			@NamedParam(name=UIConstants.UPDATE_PATH,value="crudService/update"),
			@NamedParam(name=UIConstants.UPDATE__METHOD,value="GET"),
			@NamedParam(name=UIConstants.VALIDATE_PARENT_NODE_TYPE,value="com.digitnexus.core.vo.masterdata.MaterialTypeVo"),
			//fontName,fontHeight,fontStyle
			@NamedParam(name="com.digitnexus.core.vo.masterdata.MaterialVo-Font",value=",,2"),
			@NamedParam(name="com.digitnexus.core.vo.masterdata.MaterialTypeVo-Font",value=",,1"),
		}
)
public class MaterialVo  extends AbstractVo implements Serializable, Comparable<MaterialVo>{

	@ItemField(name="Name",order=2,lengthByChar=20)	
	private String name = "";
	
	@ItemField(name="Descripton",uiType=UIType.Text,order=3,lengthByChar=20)
	private String desc;
	
	@ItemField(name="Code",order=4,lengthByChar=20,editable=false)
	private String code="";
	
	@ItemField(name="ClientName",uiType=UIType.Text,order=5,lengthByChar=20,editable=false)	
	private String clientName;
	
	@Parent
	private String parentId = null;
	
	@Children
	private List<MaterialVo> subMaterials = new ArrayList<MaterialVo>();

	@Override
	public int compareTo(MaterialVo o) {
		return name.compareTo(o.name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public List<MaterialVo> getSubMaterials() {
		return subMaterials;
	}

	public void setSubMaterials(List<MaterialVo> subMaterials) {
		this.subMaterials = subMaterials;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public int hashCode() {
		String str = this.getId()+name+this.code;
		return str.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MaterialVo)) {
			return false;
		}
		MaterialVo o = (MaterialVo)obj;
		if(!o.nodeType.equals(this.nodeType)) {
			return false;
		}
		
		boolean f = super.equals(obj);
		if(!f) {
			return false;
		}
		f = this.name.equals(o.name);
		if(!f) {
			return false;
		}
		f = this.code.equals(o.code);
		return f;
	}
	
}


