package com.digitnexus.core.masterdata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.db.AbstractPersistenceListener;
import com.digitnexus.core.dept.ClientDaoImpl;
import com.digitnexus.core.vo.masterdata.MaterialTypeVo;
import com.digitnexus.core.vo.masterdata.MaterialVo;

@Component
public class MaterialProcessor extends AbstractPersistenceListener<Material,MaterialVo> {

	@Autowired
	private MaterialDaoImpl materialDao;
	
	@Autowired
	private MaterialTypeDaoImpl materialTypeDao;
	
	@Autowired
	private ClientDaoImpl clientDao;
	
	@Autowired
	private IMaterialCodeFactory codeFactory;
	
	@Override
	public String getVoClsName() {
		return MaterialVo.class.getName();
	}

	@Override
	protected Material voToEntityForSave(MaterialVo vo) {
		Material at = new Material();
		String id = vo.getId();
		if(id == null || "".equals(id.trim())) {
			 id =  generator.getStringId(MaterialType.class);
		}
		at.setId(id);
		at.setClient(UserContext.getCurrentUser().getLoginClient());
		at.setName(vo.getName());
		at.setDesc(vo.getDesc());
		at.setCode(codeFactory.getCode());
		
		if(vo.getParentId() == null) {
			throw new CommonException("MaterialTypeCanNotBeNull",vo.getName());
		}
		
		MaterialType pm = this.materialTypeDao.find(MaterialType.class, vo.getParentId());
		if(pm == null) {
			throw new CommonException("MaterialTypeCanNotBeNull",vo.getName());
		}
		pm.getMaterials().add(at);
		at.setParentType(pm);
		return at;
	}

	@Override
	protected Material voToEntityForUpdate(MaterialVo vo) {
		Material d = this.materialDao.find(Material.class, vo.getId());
		d.setName(vo.getName());
		d.setDesc(vo.getDesc());
		d.setCode(vo.getCode());
		return d;
	}

	@Override
	public Object beforeQuery(Class<?> cls, Map<String, String> params) {
		meterialMap.clear();
		roots.clear();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT a FROM Material a WHERE 1=1 ");
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
	
	private Map<String,MaterialVo> meterialMap = new HashMap<String,MaterialVo>();
	
	private List<MaterialVo> roots = new ArrayList<MaterialVo>();

	@Override
	protected MaterialVo entityToVo(Material d) {
		MaterialVo vo = new MaterialVo();
		vo.setClientName(d.getClient().getName());
		vo.setId(d.getId());
		vo.setDesc(d.getDesc());
		vo.setCode(d.getCode());
		vo.setName(d.getName());
		vo.setClientName(d.getClient().getName());
		vo.nodeType = MaterialVo.class.getName();
		
		MaterialType mt = d.getParentType();
		MaterialVo pvo = meterialMap.get(mt.getId());
		vo.setParentId(pvo.getId());
		pvo.getSubMaterials().add(vo);
		
		return pvo;
	}

	private void loadMaterialType() {
		Query q = this.materialTypeDao.getEntityManager().createNamedQuery("findRooMaterial");
		List<MaterialType> l = q.getResultList();
		if(l == null || l.isEmpty()) {
			return;
		}
		
		for(MaterialType mt : l) {
			MaterialVo vo = parseMaterialTypeToMaterial(mt);
			roots.add(vo);
		}
	}
	
	private MaterialVo parseMaterialTypeToMaterial(MaterialType mt) {
		MaterialVo vo = new MaterialVo();
		vo.setName(mt.getName());
		vo.setClientName(mt.getClient().getName());
		vo.setCode(mt.getCode());
		vo.setDesc(mt.getDesc());
		vo.setId(mt.getId());
		vo.nodeType = MaterialTypeVo.class.getName();
		meterialMap.put(mt.getId(), vo);
		if(mt.getSubTypes() != null && !mt.getSubTypes().isEmpty()) {
			for(MaterialType subMt : mt.getSubTypes()) {
				MaterialVo subvo = parseMaterialTypeToMaterial(subMt);
				subvo.setParentId(vo.getId());
				vo.getSubMaterials().add(subvo);
			}
		}
		return vo;
	}

	@Override
	public List<MaterialVo> afterQuery(String clsName,Map<String, String> params, List<Material> entityList) {
		this.loadMaterialType();
		if(entityList == null || entityList.isEmpty()) {
			return roots;
		}
		
		for(Material d : entityList) {
			entityToVo(d);
		}
		meterialMap.clear();
		return roots;
	
	}
}
