package net.techgy.cl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.techgy.cl.manager.INamespaceManager;
import net.techgy.osig.service.impl.ConfigurationService;
import net.techgy.utils.JsonUtils;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/app-*.xml" })
public class TestConfigurableService {

	private static Logger logger = Logger.getLogger(TestConfigurableService.class);
	
	@Autowired
	private IConfigableManager manager;
	
	@Autowired
	private ConfigurationService service;

	@Autowired
	private INamespaceManager nsManager;
	
	private static String[] attrNames = {"CPU","Memory","Sound Card","Monitor "};
	
	private static String namespace="techgy.test.computer";
	
	private static String namespace_ins="techgy.test.computer.ins";
	
	@Test
	public void testSaveNamespace01() {
		Namespace ns = new Namespace();
		Date d = new Date(System.currentTimeMillis());
		ns.setCreatedDate(d);
		ns.setDesc("test name space");
		ns.setNamespace(namespace);
		ns.setUpdateDate(d);
		
		String attrStr = JsonUtils.getInstance().toJson(ns);
		Map<String,String> params = new HashMap<String,String>();
		params.put("ns", attrStr);
		
		this.manager.create("namespaceManager","createNS",params);
		
	}
	
	@Test
	public void testQueryNamespaceForAttr01() {
		Map<String,String> params = new HashMap<String,String>();
		params.put("queryStr", "");
		List<Namespace> nss = this.manager.query("namespaceManager", "findNSAttrByLike", params);
		Assert.notNull(nss);
		Assert.notEmpty(nss);
	}
	
	@Test
	public void testFindNamespace01() {
		Map<String,String> params = new HashMap<String,String>();
		params.put("ns", "ns01");
		Namespace nss = this.manager.queryUniqueResult("namespaceManager", "findNSByName", params);
		Assert.notNull(nss);
	}
	
	@Test
	public void testFindNamespaceByLkie01() {
		Map<String,String> params = new HashMap<String,String>();
		params.put("queryStr", "ns");
		List<Namespace> nss = this.manager.query("namespaceManager", "findNSByLike", params);
		Assert.notNull(nss);
	}
	
	
	@Test
	public void testSaveAttr01() {
		Namespace ns = nsManager.findNamespace(namespace);
		
		String uniID = String.valueOf(System.currentTimeMillis());
		Attribute attr = new Attribute();
		attr.setDataType("string");
		attr.setDefaultValue("");
		attr.setDesc("CPU for computer");
		attr.setLength(34);
		attr.setMaxValue(0);
		attr.setMinValue(0);
		attr.setName("CPU"+uniID);
		attr.setRequried(true);
		attr.setNamespace(ns);

		AttrValue av = new AttrValue();
		av.setDesc("AMD 2.0MHz");
		av.setValue("AMD 2.0MHz");
        attr.getAttrValues().add(av);
       // av.setAttr(attr);
		
		av = new AttrValue();
		av.setDesc("Intel 2.4MHz");
		av.setValue("Intel 2.4MHz");
		attr.getAttrValues().add(av);
		//av.setAttr(attr);
		 
		String attrStr = JsonUtils.getInstance().toJson(attr);
		Map<String,String> params = new HashMap<String,String>();
		params.put("attr", attrStr);
		
		this.manager.create("attributeManager","createAttribute",params);
	}
	
	
	@Test
	public void testQueryAttrByLike02() {
		Map<String,String> params = new HashMap<String,String>();
		params.put("queryStr", "namespace like '%te%'");
		
		String attrs = this.service.executeGet("attributeManager","findAttributeByConditionStr",
				JsonUtils.getInstance().toJson(params));
		logger.debug(attrs);
	}
	
	@Test
	public void testUpdateAttr01() {
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("queryStr", "namespace like '%te%'");
		
		List<Attribute> attrList = this.manager.execute("attributeManager", "findAttributeByConditionStr", params);
		
		/*String attrs = this.service.executeGet("attributeManager","findAttributeByConditionStr",
				JsonUtils.getInstance().toJson(params));	*/
		//Type listOfTestObject = new TypeToken<List<Attribute>>(){}.getType();
		//Gson gson = new Gson();
		
		//List<Attribute> attrList = (ArrayList<Attribute>)gson.fromJson(attrs, listOfTestObject);
		if(attrList != null && !attrList.isEmpty()){
			Attribute attr = attrList.get(0);
			Set<AttrValue> l = attr.getAttrValues();
			for(AttrValue av: l) {
				av.setAttr(null);
			}
			attr.setDesc("update attrbite name: " + attr.getName());
			attr.getAttrValues().iterator().next().setDesc("update Value");
			Map<String,String> ps = new HashMap<String,String>();
			ps.put("attr", JsonUtils.getInstance().toJson(attr.getId()));
			this.service.executePut(JsonUtils.getInstance().toJson(ps),"attributeManager","updateAttribute");
		}
	}
	
	
	@Test
	public void testDelAttr01() {
		Map<String,String> params = new HashMap<String,String>();
		params.put("queryStr", "namespace like '%te%'");		
		List<Attribute> attrList = this.manager.execute("attributeManager", "findAttributeByConditionStr", params);
		if(attrList != null && !attrList.isEmpty()){
			Attribute attr = attrList.get(0);
			Map<String,String> ps = new HashMap<String,String>();
			ps.put("id", attr.getId()+"");
			this.service.executeDelete(JsonUtils.getInstance().toJson(ps), "attributeManager", "delAttribute");
		}		
	}
	
	@Test
	public void testSayhello() {
		Map<String,String> params = new HashMap<String,String>();
		params.put("something", "Yeyulei");		
		String value = (String)this.service.executePost(JsonUtils.getInstance().toJson(params), "attributeManager", "sayHelloWorld");
	    logger.debug(value);
	    
	}
	
}
