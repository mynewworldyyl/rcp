package com.digitnexus.core.masterdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.core.UserContext;
import com.digitnexus.core.db.AbstractPersistenceListener;
import com.digitnexus.core.vo.masterdata.QualityVo;

@Component
public class QualityProcessor extends AbstractPersistenceListener<CommonValue,QualityVo>{

	@Autowired
	private CommonValueDaoImpl cvDao;
	
	@Override
	public String getVoClsName() {
		return QualityVo.class.getName();
	}

	@Override
	protected CommonValue voToEntityForSave(QualityVo vo) {
		CommonValue d = new CommonValue();
		String id = vo.getId();
		if(id == null || "".equals(id.trim())) {
			 id =  generator.getStringId(CommonValue.class);
		}
		d.setId(id);
		d.setClient(UserContext.getCurrentUser().getLoginClient());
		d.setTypecode(CommonValue.QUALITY);
		d.setValue(vo.getValue());
		d.setCode(vo.getCode());
		d.setName(vo.getCode());
		return d;
	}

	@Override
	protected CommonValue voToEntityForUpdate(QualityVo vo) {
		CommonValue d = this.cvDao.find(CommonValue.class, vo.getId());
		d.setValue(vo.getValue());
		d.setCode(vo.getCode());
		d.setName(vo.getCode());
		return d;
	}

	@Override
	public Object beforeQuery(Class<?> cls, Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT a FROM CommonValue a WHERE a.typecode='")
		.append(CommonValue.QUALITY).append("' ");
		return sb.toString();
	}

	@Override
	protected QualityVo entityToVo(CommonValue d) {
		QualityVo vo = new QualityVo();
		vo.setId(d.getId());
		vo.setCode(d.getCode());
		vo.setValue(d.getValue());
		return vo;
	}
}
