package net.techgy.cl.services;

import net.techgy.cl.Attribute;
import net.techgy.ui.UI;
import net.techgy.ui.UICreated;
import net.techgy.ui.UIElement;
import net.techgy.ui.UIQuery;
import net.techgy.ui.manager.VId;

@UI(name="insClsValueVO")
public class InsClsValueVO {

	@UIElement()
	@UIQuery(displayName="ID", seq=1)
	@UICreated(notCreated=true)
	@VId
    private long id;
	
	private String desc = "";
	
	@UIElement()
	@UIQuery(displayName="attr", seq=1)
	@UICreated(notCreated=true)
	private Attribute attr;
	
	@UIElement()
	@UIQuery(unvisible=true)
	@UICreated(notCreated=true)
	private ClassVO cls;

	private String value;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}



	public Attribute getAttr() {
		return attr;
	}

	public void setAttr(Attribute attr) {
		this.attr = attr;
	}

	public ClassVO getCls() {
		return cls;
	}

	public void setCls(ClassVO cls) {
		this.cls = cls;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
	
	
}
