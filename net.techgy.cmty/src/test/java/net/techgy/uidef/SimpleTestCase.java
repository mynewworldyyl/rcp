package net.techgy.uidef;

import java.io.IOException;

import net.techgy.uidef.demo.RequestDemo;

import org.junit.Test;

public class SimpleTestCase {

	
	@Test
	public void testUIDef01() throws IOException {
		ReqDef def = UIDefUtils.getInstance().parseReqDef(RequestDemo.class);
		System.out.println(def);
	}
	
	
}


