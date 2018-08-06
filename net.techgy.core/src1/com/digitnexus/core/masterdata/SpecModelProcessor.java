package com.digitnexus.core.masterdata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.db.AbstractPersistenceListener;
import com.digitnexus.core.vo.masterdata.MaterialTypeVo;
import com.digitnexus.core.vo.masterdata.MaterialVo;
import com.digitnexus.core.vo.masterdata.SpecModelVo;

@Component
public class SpecModelProcessor extends AbstractPersistenceListener<CommonValue,SpecModelVo> {

	@Override
	public String getVoClsName() {
		return SpecModelVo.class.getName();
	}

	@Override
	protected CommonValue voToEntityForSave(SpecModelVo vo) {
		CommonValue at = new CommonValue();
		String id = vo.getId();
		if(id == null || "".equals(id.trim())) {
			 id =  generator.getStringId(CommonValue.class);
		}
		at.setId(id);
		at.setClient(UserContext.getCurrentUser().getLoginClient());
		at.setName(vo.getName());
		at.setValue(vo.getValue());
		at.setDesc(vo.getDesc());
		at.setTypecode(CommonValue.MODELTYPE);
		
		if(vo.getParentId() == null) {
			throw new CommonException("MaterialTypeCanNotBeNull",vo.getName());
		}
		
		MaterialType pm = this.baseDao.getEntityManager().find(MaterialType.class, vo.getParentId());
		if(pm == null) {
			throw new CommonException("MaterialTypeCanNotBeNull",vo.getName());
		}
		at.setExt0(vo.getParentId());
		return at;
	}

	@Override
	protected CommonValue voToEntityForUpdate(SpecModelVo vo) {
		CommonValue at = this.baseDao.getEntityManager().find(CommonValue.class, vo.getId());
		at.setName(vo.getName());
		at.setValue(vo.getValue());
		at.setDesc(vo.getDesc());
		return at;
	}

	@Override
	public Object beforeQuery(Class<?> cls, Map<String, String> params) {
		meterialMap.clear();
		roots.clear();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT a FROM CommonValue a WHERE a.typecode='" + CommonValue.MODELTYPE + "'");
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


	private Map<String,SpecModelVo> meterialMap = new HashMap<String,SpecModelVo>();
	
	private List<SpecModelVo> roots = new ArrayList<SpecModelVo>();

	@Override
	protected SpecModelVo entityToVo(CommonValue d) {
		SpecModelVo vo = new SpecModelVo();
		vo.setClientName(d.getClient().getName());
		vo.setId(d.getId());
		vo.setDesc(d.getDesc());
		vo.setName(d.getName());
		vo.setValue(d.getValue());
		vo.nodeType = SpecModelVo.class.getName();
		
		SpecModelVo pvo = meterialMap.get(d.getExt0());
		vo.setParentId(pvo.getId());
		pvo.getSubMaterials().add(vo);
		return pvo;
	}

	private void loadMaterialType() {
		Query q = this.baseDao.getEntityManager().createNamedQuery("findRooMaterial");
		List<MaterialType> l = q.getResultList();
		if(l == null || l.isEmpty()) {
			return;
		}
		
		for(MaterialType mt : l) {
			SpecModelVo vo = parseMaterialTypeToMaterial(mt);
			roots.add(vo);
		}
	}
	
	private SpecModelVo parseMaterialTypeToMaterial(MaterialType mt) {
		SpecModelVo vo = new SpecModelVo();
		vo.setName(mt.getName());
		vo.setClientName(mt.getClient().getName());
		vo.setDesc(mt.getDesc());
		vo.setId(mt.getId());
		vo.nodeType = MaterialTypeVo.class.getName();
		meterialMap.put(mt.getId(), vo);
		if(mt.getSubTypes() != null && !mt.getSubTypes().isEmpty()) {
			for(MaterialType subMt : mt.getSubTypes()) {
				SpecModelVo subvo = parseMaterialTypeToMaterial(subMt);
				subvo.setParentId(vo.getId());
				vo.getSubMaterials().add(subvo);
			}
		}
		return vo;
	}

	@Override
	public List<SpecModelVo> afterQuery(String clsName,Map<String, String> params, List<CommonValue> entityList) {
		this.loadMaterialType();
		if(entityList == null || entityList.isEmpty()) {
			return roots;
		}
		
		for(CommonValue d : entityList) {
			entityToVo(d);
		}
		meterialMap.clear();
		return roots;
	
	}
}
