package net.techgy.cmty.ui.preference;

import javax.servlet.http.Cookie;

public class GlobalDataUtils {

	private static final GlobalDataUtils insance =new GlobalDataUtils();
	
	private GlobalDataUtils(){}
	
	public static GlobalDataUtils ins(){
		return insance;
	}
	
	public String getValue(String key) {
		Cookie[] cs = null;//RWT.getRequest().getCookies();
		if(cs == null){
			return null;
		}
		for(Cookie c : cs) {
			if(c.getName().equals(key)) {
				return c.getValue();
			}
		}
		return null;
	}
	
	public void setValue(String key,String value) {
		//RWT.getResponse().addCookie(new Cookie(key,value));;
		return;
	}
}
