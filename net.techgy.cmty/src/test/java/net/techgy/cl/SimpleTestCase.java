package net.techgy.cl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.techgy.common.models.MessageImpl;
import net.techgy.common.models.MessageState;
import net.techgy.common.models.MessageType;
import net.techgy.common.models.MsgHeader;
import net.techgy.osig.service.IImService;
import net.techgy.ui.UIUtils;
import net.techgy.ui.VODefinition;
import net.techgy.utils.JsonUtils;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SimpleTestCase {

	private static Logger logger = Logger.getLogger(TestConfigurable.class);
	
	@Test
	public void testAttrFromJson() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
		SimpleTestCase.class.getResourceAsStream("json.txt"))
	   );
		StringBuffer sb = new StringBuffer();
	    String line = null;
	    while((line = br.readLine()) != null) {
	    	sb.append(line);    	
	    }
	    Attribute avo = JsonUtils.getInstance().fromJson(sb.toString(), Attribute.class);
	    System.out.println(sb.toString());
	}
	
	@Test
	public void testGetVODefinition() {
		 List<VODefinition> ls = UIUtils.getInstance().getVODefinition(Attribute.class, null);
		 logger.debug("def: "+JsonUtils.getInstance().toJson(ls));
	}
	
	@Test
	public void testJson() {
            String[] args = new String[2];
            
            TestJson tj1 = new TestJson();
            tj1.setName("test1");
            tj1.setValue(2);
            args[0] = JsonUtils.getInstance().toJson(tj1);
            
            TestJson tj2 = new TestJson();
            tj2.setName("test1");
            tj2.setValue(2);
            args[1] = JsonUtils.getInstance().toJson(tj2);
            
            String str = JsonUtils.getInstance().toJson(args);
            
            List<String> l = JsonUtils.getInstance().fromJson(str, ArrayList.class);
            
            Object[] argss = l.toArray();
            
            TestJson argss1 = JsonUtils.getInstance().fromJson(argss[0].toString(), TestJson.class);
            
            TestJson argss2 = JsonUtils.getInstance().fromJson(argss[1].toString(), TestJson.class);
	}
	
	abstract class A {
		abstract protected void a();
		
		class InnerB{
			 int i=0;
		}
	}
	
	@Test
	public void testJson01() {
		MessageImpl message = new MessageImpl();
		message.setType(MessageType.REQ_CHAT_MESSAGE);
		message.setClientMessage(true);
		message.setStatus(MessageState.OK);
		message.addHeader(MsgHeader.CHAT_MESSAGE_TO, "test");
		message.addHeader(MsgHeader.USERNAME, "test01");
		message.addBody(MessageType.REQ_CHAT_MESSAGE.getType(), "test");	
		String jsonStr = JsonUtils.getInstance().toJson(message);
        System.out.println(jsonStr);
        
        MessageImpl msg = JsonUtils.getInstance().fromJson(jsonStr, MessageImpl.class);
       
        System.out.println(msg.toString());
        
	}
	
}


