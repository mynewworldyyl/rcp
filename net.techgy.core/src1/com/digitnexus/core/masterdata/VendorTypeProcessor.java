package com.digitnexus.core.masterdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.core.UserContext;
import com.digitnexus.core.db.AbstractPersistenceListener;
import com.digitnexus.core.vo.masterdata.ConstantVo;
import com.digitnexus.core.vo.masterdata.VendorTypeVo;

@Component
public class VendorTypeProcessor extends AbstractPersistenceListener<CommonValue,VendorTypeVo>{

	@Autowired
	private CommonValueDaoImpl cvDao;
	
	@Override
	public String getVoClsName() {
		return VendorTypeVo.class.getName();
	}

	@Override
	protected CommonValue voToEntityForSave(VendorTypeVo vo) {
		CommonValue d = new CommonValue();
		String id = vo.getId();
		if(id == null || "".equals(id.trim())) {
			 id =  generator.getStringId(CommonValue.class);
		}
		d.setId(id);
		d.setClient(UserContext.getCurrentUser().getLoginClient());
		d.setName(vo.getName());
		d.setTypecode(CommonValue.VENDOR_TYPE);
		d.setRemark(vo.getRemark());
		d.setCode(vo.getCode());
		return d;
	}

	@Override
	protected CommonValue voToEntityForUpdate(VendorTypeVo vo) {
		CommonValue d = this.cvDao.find(CommonValue.class, vo.getId());
		d.setName(vo.getName());
		d.setRemark(vo.getRemark());
		d.setCode(vo.getCode());
		return d;
	}

	@Override
	public Object beforeQuery(Class<?> cls, Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT a FROM CommonValue a WHERE a.typecode='")
		.append(CommonValue.VENDOR_TYPE).append("' ");
		return sb.toString();
	}

	@Override
	protected VendorTypeVo entityToVo(CommonValue d) {
		VendorTypeVo vo = new VendorTypeVo();
		vo.setId(d.getId());
		vo.setCode(d.getCode());
		vo.setName(d.getName());
		vo.setRemark(d.getRemark());
		return vo;
	}
}
