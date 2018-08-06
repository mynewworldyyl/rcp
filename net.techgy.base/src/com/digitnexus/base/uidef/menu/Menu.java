package com.digitnexus.base.uidef.menu;

import java.util.ArrayList;
import java.util.List;

public class Menu implements Cloneable{

	public static final String COMMON_EDITOR = "com.digitnexus.ui.editor.CommonEditor";
	
	//private String clientTypeCode;
	
	private String name;
	
	private List<Menu> subMenus = new ArrayList<Menu>();
	
	private Menu parent;
	
	private String objType;
	
	private String editorId = null;

	public Menu(String name,String objType){
			this(name,objType,COMMON_EDITOR);
	}
    public Menu(String name,String objType,String editorId){
		this.name = name;
		this.objType = objType;
		this.editorId = editorId;
	}
    
	public void addSubMenu(Menu m) {
		this.subMenus.add(m);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Menu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public String getEditorId() {
		return editorId;
	}

	public void setEditorId(String editorId) {
		this.editorId = editorId;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return name.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Menu)) {
			return false;
		}
		return ((Menu)obj).name.equals(name);
	}
	@Override
	public Menu clone() throws CloneNotSupportedException {
		Menu m = (Menu)super.clone();
/*		m.setEditorId(this.editorId);
		m.setName(this.name);
		m.setObjType(this.objType);*/
		
		if(subMenus != null && !subMenus.isEmpty()) {
			List<Menu> ms = new ArrayList<Menu>();
			for(Menu sm : this.subMenus) {
				ms.add(sm.clone());
			}
			m.setSubMenus(ms);
		}
		return m;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
}
