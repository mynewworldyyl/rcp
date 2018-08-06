package net.techgy.cl;

import java.util.List;

import net.techgy.cl.dao.IAttributeDao;
import net.techgy.cl.services.CfgFeedVO;
import net.techgy.cl.services.ClassVO;
import net.techgy.cl.services.InsClsValueVO;


public class ClUtils {

	private static ClUtils instance = new ClUtils();
	
	private ClUtils() {};
	
	public static ClUtils getInstance() {
		return instance;
	}
	
	/*public Attribute attrVOToAttr(AttributeVO avo,IAttributeDao attrDao) {
		Attribute attr = null;
		if(avo.getId() > 0) {
			 attr = attrDao.findAttribute(avo.getId());
		}
		
		if(null == attr) {
			attr = new Attribute();
			attr.setDataType(avo.getDataType());
			attr.setDefaultValue(avo.getDefaultValue());
			attr.setDesc(avo.getDesc());
			attr.setId(avo.getId());
			attr.setLength(avo.getLength());
			attr.setMaxValue(avo.getMaxValue());
			attr.setMinValue(avo.getMinValue());
			attr.setName(avo.getName());
			attr.setNamespace(avo.getNamespace());
			attr.setRequried(avo.getRequried());
			List<AttributeValueVO> avvos = avo.getAvvos();
			for(AttributeValueVO avvo: avvos) {
				AttributeValue av = attrValueVOToAttrValue(avvo);
				av.setAttr(attr);
				attr.getAttrValues().add(av);
			}
		}
		
		return attr;
	}
	
	public AttributeValue attrValueVOToAttrValue(AttributeValueVO vo) {
		AttributeValue av = new AttributeValue();
		av.setDesc(vo.getDesc());
		av.setId(vo.getId());
		av.setValue(vo.getValue());
		return av;
	}
	
	public AttributeValueVO attrValueToAttrValueVO(AttributeValue vo) {
		AttributeValueVO av = new AttributeValueVO();
		av.setDesc(vo.getDesc());
		av.setId(vo.getId());
		av.setValue(vo.getValue());
		return av;
	}
	
	public AttributeVO attrToAttrVO(Attribute avo) {
		AttributeVO attr = new AttributeVO();
		attr.setDataType(avo.getDataType());
		attr.setDefaultValue(avo.getDefaultValue());
		attr.setDesc(avo.getDesc());
		attr.setId(avo.getId());
		attr.setLength(avo.getLength());
		attr.setMaxValue(avo.getMaxValue());
		attr.setMinValue(avo.getMinValue());
		attr.setName(avo.getName());
		attr.setNamespace(avo.getNamespace());
		attr.setRequried(avo.isRequried());
		List<AttributeValue> avvos = avo.getAttrValues();
		for(AttributeValue avvo: avvos) {
			AttributeValueVO av = attrValueToAttrValueVO(avvo);
			attr.getAvvos().add(av);
		}
		return attr;
	}*/
	
	
	/*public VariantClass clsVOToCls(ClassVO vo,IAttributeDao attrDao) {
		VariantClass vc = new VariantClass();
		vc.setDesc(vo.getDesc());
		vc.setId(vo.getId());
		vc.setName(vo.getName());
		vc.setNamespace(vo.getNamespace());
		vc.setType(vo.getType());
		List<InsClsValueVO> attres = vo.getValues();
		if(CLConstants.CLS_INS_TYPE == vo.getType() && null != attres) {
			for(InsClsValueVO ivc: attres) {
				InsClsValue attr = this.insClsValueVOToInsClsValue(ivc,attrDao) ;
				attr.setCls(vc);
				if(null != attr) {
					vc.getValues().add(attr);
				}
			}
		}
		List<Attribute> avos = vo.getAttrs();
		vc.setAttrs(avos);
		
		VariantClass cls = null;
		if(vo.getParentCls() != null ) {
			cls = this.clsVOToCls(vo.getParentCls(),attrDao);
			if(null == cls) {
				throw new RuntimeException("Parnet Class not found: " + vo.getParentCls());
			}
			vc.setParentCls(cls);
			cls = null;
		}
		if(vo.getInstanceOf() != null) {
			cls = this.clsVOToCls(vo.getInstanceOf(),attrDao);
			if(null == cls) {
				throw new RuntimeException("Instance Class not found: " + vo.getInstanceOf());
			}
			vc.setInstanceOf(cls);
			cls = null;
		}
		return vc;
	}
	
	public ClassVO clsToClsVO(VariantClass vo) {
		ClassVO vc = new ClassVO();
		vc.setDesc(vo.getDesc());
		vc.setId(vo.getId());
		vc.setName(vo.getName());
		vc.setNamespace(vo.getNamespace());
		vc.setType(vo.getType());
		List<Attribute> attres = vo.getAttrs();
		for(Attribute attr: attres) {
			vc.getAttrs().add(ClUtils.getInstance().attrToAttrVO(attr));
		}
		if(vo.getParentCls() != null) {
			vc.setParentCls(this.clsToClsVO(vo.getParentCls()));
		}
		if(vo.getInstanceOf() != null) {
			vc.setInstanceOf(this.clsToClsVO(vo.getInstanceOf()));
		}
		if(vo.getValues() != null && !vo.getValues().isEmpty()) {
			for(InsClsValue iv : vo.getValues()) {
				InsClsValueVO ins =this.insClsValueToInsClsValueVo(iv);
				ins.setCls(vc);
				vc.getValues().add(ins);
			}
		}
		return vc;
	}*/
	
	/*public InsClsValue insClsValueVOToInsClsValue(InsClsValueVO vo,IAttributeDao attrDao) {
		InsClsValue ins = new InsClsValue();
		ins.setAttr(this.attrVOToAttr(vo.getAttr(),attrDao));
		ins.setDesc(vo.getDesc());
		ins.setId(vo.getId());
		ins.setValue(vo.getValue());
		//ins.setCls(this.clsVOToCls(vo.getCls()));
		return ins;
	}
	
	
	
	public InsClsValueVO insClsValueToInsClsValueVo(InsClsValue vo) {
		InsClsValueVO ins = new InsClsValueVO();
		ins.setAttr(this.attrToAttrVO(vo.getAttr()));
		//ins.setCls(this.clsToClsVO(vo.getCls()));
		ins.setDesc(vo.getDesc());
		ins.setId(vo.getId());
		ins.setValue(vo.getValue());
		return ins;
	}
	
	public CfgFeed cfgFeedVOToCfgFeed(CfgFeedVO vo,IAttributeDao attrDao){
		CfgFeed feed = new CfgFeed();
		feed.setCls(this.clsVOToCls(vo.getCls(), attrDao));
		feed.setDesc(vo.getDesc());
		feed.setId(vo.getId());
		feed.setName(vo.getName());
		feed.setNamespace(vo.getNamespace());
		return feed;
	}
	
	public CfgFeedVO cfgFeedToCfgFeedVO(CfgFeed vo,IAttributeDao attrDao){
		CfgFeedVO feed = new CfgFeedVO();
		feed.setCls(this.clsToClsVO(vo.getCls()));
		feed.setDesc(vo.getDesc());
		feed.setId(vo.getId());
		feed.setName(vo.getName());
		feed.setNamespace(vo.getNamespace());
		return feed;
	}*/
}
