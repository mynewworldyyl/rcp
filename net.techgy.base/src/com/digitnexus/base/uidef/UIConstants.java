package com.digitnexus.base.uidef;

public class UIConstants {

	public static final String ACTTION_LOCAL = "local://";
	public static final String ACTTION_REMOTE = "remote://";
	
	public static final String DEF_URL = "crudService/def";
	
	public static final String REQ_USER_ID= "__user_id";
	public static final String REQ_USER_CLIENT_ID= "__client_id";
	public static final String REQ_USER_LOCALE= "__locale";
	
	public static final String REQ_GET_COMPONENT_PATH = "requestPath";
	public static final String REQ_GET_METHOD_PATH = "getMethodPath";
	public static final String REQ_GET_METHOD = "getMethod";
	
	public static final String UPDATE_PATH = "updatePath";
	public static final String UPDATE_COMPONENT = "updateComponent";
	public static final String UPDATE__METHOD = "updateMethod";
	
	public static String FAIL = "fail";
	public static String SUCCESS = "success";
	
	public static String LOGIN_ACCOUNT = "_LOGIN_ACCOUNT_KEY";
	public static String LOGIN_CLIENT = "_LOGIN_CLIENT_KEY";
	
	public static String IS_REMENBER_UUSER_PW = "_IS_REMENBER_UUSER_PW";
	
	public static String LAST_UUSER = "_LAST_UUSER";
	
	public static String LAST_PASSWD = "_LAST_PASSWD";
	
	public static String USER_CONTEXT = "_USER_CONTEXT";
	
	public static final String SHELL = "shell";
	
	public static final String DISPLAY = "display";
	
	public static final String IM_ACCOUNT = "imAccount";
	
	public static final String ACCOUNT_LOGIN_SUCCESS = "accountLoginSuccess";
	
	 /**
	   * The font style constant indicating a normal weight, non-italic font
	   * (value is 0).
	   */
	  public static final int FONT_NORMAL = 0;

	  /**
	   * The font style constant indicating a bold weight font
	   * (value is 1&lt;&lt;0).
	   */
	  public static final int FONT_BOLD = 1 << 0;

	  /**
	   * The font style constant indicating an italic font
	   * (value is 1&lt;&lt;1).
	   */
	  public static final int FONT_ITALIC = 1 << 1;
	
	public static final String VALIDATE_PARENT_NODE_TYPE = "addSubNodeKey";
	
	public enum ModelProperty{
		NodeType("nodeType");
		
		private String fieldName;	
		
		private ModelProperty(String fieldName) {
			this.fieldName = fieldName;
		}
		public boolean equals(ModelProperty mp) {
			return fieldName.equals(mp.fieldName);
		}
		public String getFieldName() {
			return fieldName;
		}
		
		
	}
	
	  public enum ActionType {
			
			Query("Query",new String[]{"Save"}),
			Delete("Delete",new String[]{"Query"}),
			Create("Create",new String[]{"Query","Modify","Add"}),
			Modify("Modify",new String[]{"Save","Query"}),
			Add("Add",new String[]{"Save"}),
			Export("Export",new String[]{"Query"}),
			Detail("Detail",new String[]{"Query"}),
			Save("Save",new String[]{}),
			Cancel("Cancel",new String[]{"Save"}),
			Ext("Ext",new String[]{"Save"}),
			;
			
			private String name;
			private String[] dependencies;
			
			private ActionType(String name,String[] dependencies) {
				this.name = name;
				this.dependencies = dependencies;
			}
			public boolean equals(ActionType type) {
				return name.equals(type.name);
			}
			public String getName() {
				return name;
			}
			public String[] getDependencies() {
				return dependencies;
			}
			
		}
	  
	public enum UIType {
		
		Text("Textinput"),
		Password("Password"),
		Radiobox("Radiobox"),
		Checkbox("Checkbox"),
		Email("Email"),
		List("List"),
		Combo("Combo"),
		Tree("Tree"),
		Table("table"),
		Date("Date"),
		Time("Time");
		
		private String name;		
		private UIType(String name) {
			this.name = name;
		}
		
		public boolean equals(UIType type) {
			return name.equals(type.name);
		}
		
	}
	
    public enum DataType {
		
		String("String"),
		Integer("Integer"),
		Byte("Byte"),
		Short("Short"),
		Long("Long"),
		Float("Float"),
		Double("Double"),
		Boolean("Boolean");
		
		private String name;		
		private DataType(String name) {
			this.name = name;
		}
	}
}
