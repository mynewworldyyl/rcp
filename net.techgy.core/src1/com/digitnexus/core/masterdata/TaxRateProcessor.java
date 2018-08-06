package com.digitnexus.core.masterdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.core.UserContext;
import com.digitnexus.core.db.AbstractPersistenceListener;
import com.digitnexus.core.vo.masterdata.QualityVo;
import com.digitnexus.core.vo.masterdata.TaxRateVo;

@Component
public class TaxRateProcessor extends AbstractPersistenceListener<CommonValue,TaxRateVo>{

	@Autowired
	private CommonValueDaoImpl cvDao;
	
	@Override
	public String getVoClsName() {
		return TaxRateVo.class.getName();
	}

	@Override
	protected CommonValue voToEntityForSave(TaxRateVo vo) {
		CommonValue d = new CommonValue();
		String id = vo.getId();
		if(id == null || "".equals(id.trim())) {
			 id =  generator.getStringId(CommonValue.class);
		}
		d.setId(id);
		d.setClient(UserContext.getCurrentUser().getLoginClient());
		d.setTypecode(CommonValue.TAXRATE);
		d.setValue(vo.getValue());
		d.setCode(vo.getCode());
		d.setName(vo.getName());
		return d;
	}

	@Override
	protected CommonValue voToEntityForUpdate(TaxRateVo vo) {
		CommonValue d = this.cvDao.find(CommonValue.class, vo.getId());
		d.setValue(vo.getValue());
		d.setCode(vo.getCode());
		d.setName(vo.getName());
		return d;
	}

	@Override
	public Object beforeQuery(Class<?> cls, Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT a FROM CommonValue a WHERE a.typecode='")
		.append(CommonValue.TAXRATE).append("' ");
		return sb.toString();
	}

	@Override
	protected TaxRateVo entityToVo(CommonValue d) {
		TaxRateVo vo = new TaxRateVo();
		vo.setId(d.getId());
		vo.setCode(d.getCode());
		vo.setValue(d.getValue());
		vo.setName(d.getName());
		return vo;
	}
}
