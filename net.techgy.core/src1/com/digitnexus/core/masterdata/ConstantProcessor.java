package com.digitnexus.core.masterdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.core.UserContext;
import com.digitnexus.core.db.AbstractPersistenceListener;
import com.digitnexus.core.vo.masterdata.ConstantVo;

@Component
public class ConstantProcessor extends AbstractPersistenceListener<CommonValue,ConstantVo>{

	@Autowired
	private CommonValueDaoImpl cvDao;
	
	@Override
	public String getVoClsName() {
		return ConstantVo.class.getName();
	}

	@Override
	protected CommonValue voToEntityForSave(ConstantVo vo) {
		CommonValue d = new CommonValue();
		String id = vo.getId();
		if(id == null || "".equals(id.trim())) {
			 id =  generator.getStringId(CommonValue.class);
		}
		d.setId(id);
		d.setClient(UserContext.getCurrentUser().getLoginClient());
		d.setName(vo.getName());
		d.setTypecode(CommonValue.CONSTANT);
		d.setValue(vo.getValue());
		d.setUnit(vo.getUnit());
		d.setCode(vo.getCode());
		return d;
	}

	@Override
	protected CommonValue voToEntityForUpdate(ConstantVo vo) {
		CommonValue d = this.cvDao.find(CommonValue.class, vo.getId());
		d.setName(vo.getName());
		d.setValue(vo.getValue());
		d.setUnit(vo.getUnit());
		d.setCode(vo.getCode());
		return d;
	}

	@Override
	public Object beforeQuery(Class<?> cls, Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT a FROM CommonValue a WHERE a.typecode='")
		.append(CommonValue.CONSTANT).append("' ");
		return sb.toString();
	}

	@Override
	protected ConstantVo entityToVo(CommonValue d) {
		ConstantVo vo = new ConstantVo();
		vo.setId(d.getId());
		vo.setCode(d.getCode());
		vo.setName(d.getName());
		vo.setUnit(d.getUnit());
		vo.setValue(d.getValue());
		return vo;
	}
}
