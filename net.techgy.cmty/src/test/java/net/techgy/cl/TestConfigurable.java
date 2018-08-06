package net.techgy.cl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import net.techgy.cl.manager.IAttributeManager;
import net.techgy.cl.manager.ICfgFeedManager;
import net.techgy.cl.manager.IClassManager;
import net.techgy.cl.manager.INamespaceManager;
import net.techgy.cl.services.CfgFeedVO;
import net.techgy.cl.services.ClassVO;
import net.techgy.cl.services.InsClsValueVO;
import net.techgy.ui.UIConstants;
import net.techgy.ui.UITable;
import net.techgy.ui.UIUtils;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/app-*.xml" })
public class TestConfigurable {

	private static Logger logger = Logger.getLogger(TestConfigurable.class);
	
	@Autowired
	private IAttributeManager attrManager;
	
	@Autowired
	private IClassManager clsManager;
	
	@Autowired
	private ICfgFeedManager feedManager;
	
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
		this.nsManager.createNamespace(ns);
		
	}
		
	@Test
	public void testSaveAttr01() {
		Namespace ns = this.nsManager.findNamespace(namespace);
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
        av.setAttr(attr);
		
		av = new AttrValue();
		av.setDesc("Intel 2.4MHz");
		av.setValue("Intel 2.4MHz");
		attr.getAttrValues().add(av);
		av.setAttr(attr);
		
		this.attrManager.createAttribute(attr);
		
		attr = new Attribute();
		attr.setDataType("string");
		attr.setDefaultValue("");
		attr.setDesc("Memory for computer");
		attr.setLength(34);
		attr.setMaxValue(0);
		attr.setMinValue(0);
		attr.setName("Memory"+uniID);
		attr.setRequried(true);
		attr.setNamespace(ns);

		av = new AttrValue();
		av.setDesc("kingston 2.0GHz");
		av.setValue("kingston 2.0GHz");
		attr.getAttrValues().add(av);
		av.setAttr(attr);
		
		av = new AttrValue();
		av.setDesc("sumsung 4.0MHz");
		av.setValue("sumsung 4.0MHz");
		attr.getAttrValues().add(av);
		av.setAttr(attr);
		
		this.attrManager.createAttribute(attr);
	}
	
	@Test
	public void testQueryAttr01() {
		List<Attribute> attrs = this.attrManager.findAttribute(namespace);
		for(Attribute att: attrs) {
			logger.debug(att.getName());
			for(AttrValue v: att.getAttrValues()) {
				logger.debug("          "+ v.getValue());
			}
			logger.debug("==============================");
		}
	}
	
	@Test
	public void testUITable01() {
		List<Attribute> attrs = this.attrManager.findAttribute(namespace);
		UITable table = UIUtils.getInstance().getTable(Attribute.class,attrs);
		System.out.println(table.getTableName());
	}
		
	@Test
	public void testQueryAttrByLike01() {
		List<Attribute> attrs = this.attrManager.findAttributeByLike("techgy");
		for(Attribute att: attrs) {
			logger.debug(att.getName());
			for(AttrValue v: att.getAttrValues()) {
				logger.debug("          "+ v.getValue());
			}
			logger.debug("==============================");
		}
	}
	
	@Test
	public void testUpdateAttr01() {
		List<Attribute> attrs = this.attrManager.findAttribute(namespace);
		for(Attribute att: attrs) {
			att.setDesc("update attrbite name: " + att.getName());
			att.getAttrValues().iterator().next().setDesc("update Value");
			this.attrManager.updateAttribute(att);
		}
	}
	
	
	@Test
	public void testDelAttr01() {
		List<Attribute> attrs = this.attrManager.findAttribute(namespace);
		for(Attribute att: attrs) {
			if(!this.attrManager.delAttribute(att.getId())){
				logger.debug("Attribute is used by other class so cannot be delete!=================");
			}
		}
	}
	
	
	
	@Test
	public void testSaveClass01() {
		ClassVO vc = new ClassVO();
		vc.setDesc("My love computer!");
		vc.setInstanceOf(null);
		vc.setName("Configurable Computer");
		vc.setParentCls(null);
		vc.setType(CLConstants.CLS_CFG_TYPE);
		vc.setNamespace(namespace);
		List<Attribute> attrs = this.attrManager.findAttribute(namespace);
		for(Attribute attr: attrs) {
			vc.getAttrs().add(attr);
		}
		Assert.assertTrue(this.clsManager.createClass(vc));
	}
	
	@Test
	public void testQueryClass01() {
		List<ClassVO> clses = this.clsManager.findClass(namespace);
		for(ClassVO cv : clses) {
			logger.debug(cv.getName());
			for(Attribute avo: cv.getAttrs()) {
				logger.debug(avo.getName());
				for(AttrValue v: avo.getAttrValues()) {
					logger.debug("          "+ v.getValue());
				}
				logger.debug("==============================");
			}
		}
	}

	@Test
	public void testUpdateClass01() {
		List<ClassVO> clses = this.clsManager.findClass(namespace);
		for(ClassVO cls: clses) {
			cls.setDesc("Update Class desc : "+cls.getDesc());
			//cls.setType(CLConstants.CLS_CFG_TYPE);
			this.clsManager.updateClass(cls);
		}
	}
	

	@Test
	public void testDelClass01() {
		List<ClassVO> clses = this.clsManager.findClass(namespace);
		for(ClassVO cls: clses) {
			this.clsManager.delClass(cls.getId());
		}
	}
	
	
	/** ===================================================================== */
	
	
	@Test
	public void testSaveInstanceClass01() {
		List<ClassVO> clses = this.clsManager.findClass(namespace);
		if(clses == null) {
			throw new RuntimeException("class not found");
		}
		ClassVO insCls = new ClassVO();
		insCls.setDesc("My love HP computer!");
		insCls.setName("My Computer");
		insCls.setParentCls(null);
		insCls.setType(CLConstants.CLS_INS_TYPE);
		insCls.setNamespace(namespace);
		
		ClassVO concretClass = clses.get(0);
		insCls.setInstanceOf(concretClass);
		
		List<Attribute> attrs =  concretClass.getAttrs();
		for(Attribute attr: attrs) {
			InsClsValue avo = new InsClsValue();
			if(attr.getAttrValues() != null &&  attr.getAttrValues().size() > 0) {
				AttrValue value = attr.getAttrValues().iterator().next();
				avo.setAttr(attr);
				//avo.setCls(concretClass);
				avo.setDesc(value.getDesc());
				avo.setValue(value.getValue());	
			}
		    //insCls.getValues().add(avo);
		}
		this.clsManager.createInstanceClass(insCls);
	}
	
	@Test
	public void testQueryInstanceClass01() {
		List<ClassVO> clses = this.clsManager.findInsClass(namespace);
		for(ClassVO cv : clses) {
			logger.debug(cv.getName());
			for(InsClsValueVO avo: cv.getValues()) {
				logger.debug(avo.getAttr().getName());
				logger.debug(avo.getAttr().getName() + " = "+ avo.getValue());
				logger.debug("==============================");
			}
		}
	}
    
	@Test
	public void testUpdateInstanceClass01() {
		List<ClassVO> clses = this.clsManager.findInsClass(namespace);
		for(ClassVO cls: clses) {
			cls.setDesc("Update instance Class desc : "+cls.getDesc());
			this.clsManager.updateClass(cls);
		}
	}
	
    
	@Test
	public void testDelInstanceClass01() {
		List<ClassVO> clses = this.clsManager.findInsClass(namespace);
		for(ClassVO cls: clses) {
			this.clsManager.delClass(cls.getId());
		}
	}
	
	@Test
	public void testSaveCfgFeed01() {
		List<ClassVO> clses = this.clsManager.findClass(namespace);
		if(clses == null) {
			throw new RuntimeException("class not found");
		}
		ClassVO insCls = new ClassVO();
		insCls.setDesc("My IBM computer!");
		insCls.setName("My IBM Computer");
		insCls.setParentCls(null);
		insCls.setType(CLConstants.CLS_INS_TYPE);
		insCls.setNamespace(namespace);
		
		ClassVO concretClass = clses.get(0);
		insCls.setInstanceOf(concretClass);
		
		List<Attribute> attrs =  concretClass.getAttrs();
		for(Attribute attr: attrs) {
			InsClsValueVO avo = new InsClsValueVO();
			if(attr.getAttrValues() != null &&  attr.getAttrValues().size() > 0) {
				AttrValue value = attr.getAttrValues().iterator().next();
				avo.setAttr(attr);
				avo.setCls(insCls);
				avo.setDesc(value.getDesc());
				avo.setValue(value.getValue());	
			}
		    insCls.getValues().add(avo);
		}
		CfgFeedVO cfvo = new CfgFeedVO();
		cfvo.setCls(insCls);
		cfvo.setDesc("my IBM computer feed");
		cfvo.setName("my love IBM computer from feed");
		this.feedManager.createCfgFeed(cfvo);
	}
	
	@Test
	public void testQueryCfgFeed01() {
		List<CfgFeedVO> clses = this.feedManager.findCfgFeed("IBM");
		for(CfgFeedVO feed : clses) {
			logger.debug(feed.getName());
			ClassVO cvo = feed.getCls();
			logger.debug(cvo.getName());
			for(InsClsValueVO avo: cvo.getValues()) {
				logger.debug(avo.getAttr().getName());
				logger.debug(avo.getAttr().getName() + " = "+ avo.getValue());
				logger.debug("==============================");
			}
		}
	}
    
	@Test
	public void testUpdateFeed01() {
		List<CfgFeedVO> clses = this.feedManager.findCfgFeed("IBM");
		for(CfgFeedVO cls: clses) {
			cls.setDesc("Update instance Class desc : "+cls.getDesc());
			this.feedManager.updateCfgFeed(cls);
		}
	}
	
    
	@Test
	public void testDelFeed01() {
		List<CfgFeedVO> clses = this.feedManager.findCfgFeed("IBM");
		for(CfgFeedVO cls: clses) {
			this.feedManager.delCfgFeed(cls.getId());
		}
	}
	
	public static void main(String[] args) {
		List l = new ArrayList();
		System.out.println(Collection.class.isAssignableFrom(l.getClass()));
		
		ClassVO insCls = new ClassVO();
		insCls.setDesc("My IBM computer!");
		
		logger.debug("desc: "+UIUtils.getInstance().getELValue("desc", insCls));
		
		logger.debug("UIConstants.UI_TYPE_TEXT: " + UIUtils.getInstance().getELValue("UIConstants.UI_TYPE_TEXT", new UIConstants(){}));
		
	}

}
