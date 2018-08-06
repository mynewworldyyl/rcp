package net.techgy.cl.services;

import java.util.ArrayList;
import java.util.List;

import net.techgy.cl.Attribute;
import net.techgy.cl.CLConstants;
import net.techgy.ui.UI;
import net.techgy.ui.UIConstants;
import net.techgy.ui.UICreated;
import net.techgy.ui.UIElement;
import net.techgy.ui.UIQuery;
import net.techgy.ui.manager.VId;

@UI(name="Class")
public class ClassVO {

	@UIElement()
	@UIQuery(displayName="ID", seq=1)
	@UICreated(notCreated=true)
	@VId
	private long id;
	
	@UIElement()
	@UIQuery(displayName="Name Space", seq=2,resKey="namespace")
    private String namespace = "default";
	
	@UIElement()
	@UIQuery(displayName="Description", seq=4,resKey="description")
	private String desc = "";
	
	@UIElement()
	@UIQuery(displayName="Name", seq=3,resKey="className")
	private String name;

	@UIElement()
	@UIQuery(displayName="Type", seq=5,resKey="classType")
	@UICreated(uiType=UIConstants.UI_TYPE_SELECT,values={""+CLConstants.CLS_CFG_TYPE,""+CLConstants.CLS_INS_TYPE})
	private int type;
	
	@UIElement(valueCls=ClassVO.class)
	@UIQuery(displayName="Name", seq=3,resKey="className", unvisible=true)
	@UICreated(notCreated=true)
	private ClassVO instanceOf;
	
	@UIElement(valueCls=ClassVO.class)
	@UIQuery(displayName="Parent Class", seq=3,resKey="parentClass", unvisible=true)
	@UICreated(notCreated=false,uiType=UIConstants.UI_TYPE_SELECT,values={"namespace","name"})
	private ClassVO parentCls;
	
	@UIElement(valueCls=Attribute.class)
	@UIQuery(displayName="Attributes", seq=8,resKey="attributes")
	@UICreated(notCreated=true,uiType=UIConstants.UI_TYPE_SELECT)
	private List<Attribute> attrs = new ArrayList<Attribute>();

	//for feed class instance
	@UIElement(valueCls=InsClsValueVO.class)
	@UIQuery(displayName="Attribute Values", seq=8,resKey="attributeValues")
	@UICreated(notCreated=true)
	private List<InsClsValueVO> values = new ArrayList<InsClsValueVO>();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public ClassVO getInstanceOf() {
		return instanceOf;
	}

	public void setInstanceOf(ClassVO instanceOf) {
		this.instanceOf = instanceOf;
	}

	public ClassVO getParentCls() {
		return parentCls;
	}

	public void setParentCls(ClassVO parentCls) {
		this.parentCls = parentCls;
	}

	public List<InsClsValueVO> getValues() {
		return values;
	}

	public void setValues(List<InsClsValueVO> values) {
		this.values = values;
	}

	public List<Attribute> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<Attribute> attrs) {
		this.attrs = attrs;
	}

	
	
}
