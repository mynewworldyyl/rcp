package net.techgy.uidef;

public class UIConstants {

	public enum UIType {
		
		Text("textinput"),
		Radiobox("Radiobox"),
		Checkbox("Checkbox"),
		Email("Email"),
		List("List"),
		Tree("Tree"),
		Table("table");
		
		private String name;		
		private UIType(String name) {
			this.name = name;
		}
	}
	
    public enum DataType {
		
		String("String"),
		Integer("Snteger"),
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
