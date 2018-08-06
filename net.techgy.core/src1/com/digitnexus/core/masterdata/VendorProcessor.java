package com.digitnexus.core.masterdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.protocol.Response;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.db.AbstractPersistenceListener;
import com.digitnexus.core.dept.ClientManager;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.vo.masterdata.VendorVo;

@Component
public class VendorProcessor extends AbstractPersistenceListener<Vendor,VendorVo>{

	@Autowired
	private VendorDaoImpl vendorDao;
	
	@Autowired
	private CommonValueDaoImpl cvDao;
	
	@Autowired
	private ClientManager clientManager;
	
	@Override
	public String getVoClsName() {
		return VendorVo.class.getName();
	}
	
	
	@Override
	public Response afterSaveEntity(Vendor e, VendorVo vo, Response resp) {
		resp = super.afterSaveEntity(e, vo, resp);
		clientManager.createClient(e.getName(), e.getName(), null, 
				e.getName(), e.getName(), ClientType.Vendor);
		return resp;
	}

	@Override
	public Response afterUpdateEntity(Vendor e, VendorVo vo, Response resp) {
		// TODO Auto-generated method stub
		return super.afterUpdateEntity(e, vo, resp);
	}



	@Override
	protected Vendor voToEntityForSave(VendorVo vo) {
		Vendor d = new Vendor();
		String id = vo.getId();
		if(id == null || "".equals(id.trim())) {
			 id =  generator.getStringId(Vendor.class);
		}
		d.setId(id);
		d.setClient(UserContext.getCurrentUser().getLoginClient());
		setBaseField(vo,d);
		return d;
	}

	@Override
	protected Vendor voToEntityForUpdate(VendorVo vo) {

		Vendor d = vendorDao.find(Vendor.class, vo.getId());
		if(d == null) {
			 throw new CommonException("EntityNotFoundForUpdate",vo.getName());
		}
		setBaseField(vo,d);
		return d;
	}
	
	private void setBaseField(VendorVo vo,Vendor d) {
		d.setName(vo.getName());
		CommonValue cv = this.cvDao.getVendorType(vo.getVendorTypeId());
		if(cv == null) {
			throw new CommonException("VendorTypeMustSpecify");
		}
		d.setVendorType(cv);
		d.setBphone(vo.getBphone());
		d.setBusineessType(vo.getBusineessType());
		d.setContact(vo.getContact());
		d.setDesc(vo.getDesc());
		d.setEntLegalPerson(vo.getEntLegalPerson());
		d.setEntNature(vo.getEntNature());
		d.setFax(vo.getFax());
		d.setLevel(vo.getLevel());
		d.setLicense(vo.getLicense());
		d.setModile(vo.getMobile());
		d.setIsQualified(vo.getIsQualified());
        d.setRegAddress(vo.getRegAddress());
        d.setRegistedOn(vo.getRegistedOn());
        d.setRegistedPhone(vo.getRegistedPhone());
        d.setRegMoney(vo.getRegMoney());
        d.setRemark(vo.getRemark());
	}

	@Override
	public Object beforeQuery(Class<?> cls, Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT a FROM Vendor a");
		return sb.toString();
	}

	@Override
	protected VendorVo entityToVo(Vendor vo) {
		VendorVo d = new VendorVo();
		d.setId(vo.getId());
		d.setName(vo.getName());
		d.setVendorTypeId(vo.getVendorType().getId());
		d.setBphone(vo.getBphone());
		d.setBusineessType(vo.getBusineessType());
		d.setCode(vo.getId());
		d.setContact(vo.getContact());
		d.setDesc(vo.getDesc());
		d.setEntLegalPerson(vo.getEntLegalPerson());
		d.setEntNature(vo.getEntNature());
		d.setFax(vo.getFax());
		d.setLevel(vo.getLevel());
		d.setLicense(vo.getLicense());
		d.setMobile(vo.getModile());
		d.setIsQualified(vo.getIsQualified());
        d.setRegAddress(vo.getRegAddress());
        d.setRegistedOn(vo.getRegistedOn());
        d.setRegistedPhone(vo.getRegistedPhone());
        d.setRegMoney(vo.getRegMoney());
        d.setRemark(vo.getRemark());
		return d;
	}
}
