package com.digitnexus.core.masterdata;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.db.AbstractPersistenceListener;
import com.digitnexus.core.dept.ClientDaoImpl;
import com.digitnexus.core.dept.Department;
import com.digitnexus.core.vo.dept.DepartmentVo;
import com.digitnexus.core.vo.masterdata.MaterialTypeVo;

@Component
public class MaterialTypeProcessor extends AbstractPersistenceListener<MaterialType,MaterialTypeVo> {

	@Autowired
	private MaterialTypeDaoImpl assetTypeDao;
	
	@Autowired
	private ClientDaoImpl clientDao;
	
	@Autowired
	private IMaterialCodeFactory codeFactory;
	
	@Override
	public String getVoClsName() {
		return MaterialTypeVo.class.getName();
	}

	@Override
	protected MaterialType voToEntityForSave(MaterialTypeVo vo) {
		MaterialType at = new MaterialType();
		String id = vo.getId();
		if(id == null || "".equals(id.trim())) {
			 id =  generator.getStringId(MaterialType.class);
		}
		at.setId(id);
		at.setClient(UserContext.getCurrentUser().getLoginClient());
		at.setName(vo.getName());
		at.setDesc(vo.getDesc());
		at.setCode(codeFactory.getCode());
		if(vo.getParentId() != null) {
			MaterialType p = this.assetTypeDao.find(MaterialType.class, vo.getParentId());
			p.getSubTypes().add(at);
			at.setParentType(p);
		}
		return at;
	}

	@Override
	protected MaterialType voToEntityForUpdate(MaterialTypeVo vo) {
		MaterialType d = this.assetTypeDao.find(MaterialType.class, vo.getId());
		d.setName(vo.getName());
		d.setDesc(vo.getDesc());
		d.setCode(vo.getCode());
		return d;
	}

	@Override
	public Object beforeQuery(Class<?> cls, Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT a FROM MaterialType a WHERE 1=1 and a.parentType IS NULL");
		//Map<String,Object> queryParams = new HashMap<String,Object>();
		if(params != null && !params.isEmpty()) {
			for(Map.Entry<String, String> e : params.entrySet()) {
				String fn = e.getKey();
				String fv = e.getValue();
				if(fv == null || fv.trim().equals("")) {
					continue;
				}
				try {
					Field f = cls.getDeclaredField(fn);
					Object value = getOneCondition(fv,f);
					if(value != null) {
						sb.append(" OR a.").append(f.getName()).append(" LIKE '%" ).append(value).append("%' ");
					}
				} catch (NoSuchFieldException e1) {
				} catch (SecurityException e1) {
				}
			}
		}
		return sb.toString();
	}


	@Override
	public String[] beforeDelete(String clsName, String ids) {
		String[] idss = super.beforeDelete(clsName, ids);
		return idss;
	}

	@Override
	protected MaterialTypeVo entityToVo(MaterialType d) {
		MaterialTypeVo vo = new MaterialTypeVo();
		vo.setClientName(d.getClient().getName());
		vo.setId(d.getId());
		vo.setDesc(d.getDesc());
		vo.setCode(d.getCode());
		vo.setName(d.getName());
		if(d.getParentType() != null) {
			vo.setParentId(d.getParentType().getId());
		}
	
		if(!d.getSubTypes().isEmpty()) {
			for(MaterialType sub : d.getSubTypes()) {
				MaterialTypeVo svo = this.entityToVo(sub);
				vo.getSubs().add(svo);
			}
		}
		return vo;
	}
	
}
