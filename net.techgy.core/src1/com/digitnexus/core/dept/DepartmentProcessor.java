package com.digitnexus.core.dept;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.db.AbstractPersistenceListener;
import com.digitnexus.core.idgenerator.ICacheIDGenerator;
import com.digitnexus.core.vo.dept.DepartmentVo;
import com.google.gson.reflect.TypeToken;

@Component
public class DepartmentProcessor extends AbstractPersistenceListener<Department,DepartmentVo> {

	@Autowired
	private DeptDaoImpl deptDao;
	
	@Autowired
	private ClientDaoImpl clientDao;
	
	@Override
	public String getVoClsName() {
		return DepartmentVo.class.getName();
	}

	@Override
	protected Department voToEntityForSave(DepartmentVo obj) {
		DepartmentVo vo = (DepartmentVo)obj;
		Department d = new Department();
		String id = obj.getId();
		if(id == null || "".equals(id.trim())) {
			 id =  generator.getStringId(Department.class);
		}
		d.setId(id);
		d.setClient(UserContext.getCurrentUser().getLoginClient());
		d.setDeptName(vo.getName());
		d.setDescription(vo.getDesc());
		if(vo.getParentId() != null) {
			Department p = this.deptDao.find(Department.class, vo.getParentId());
			p.getSubDepts().add(d);
			d.setParent(p);
		}
		return d;
	}

	@Override
	protected Department voToEntityForUpdate(DepartmentVo vo) {
		Department d = this.deptDao.find(Department.class, vo.getId());
		d.setDeptName(vo.getName());
		d.setDescription(vo.getDesc());
		return d;
	}

	@Override
	public Object beforeQuery(Class<?> cls, Map<String, String> params) {
        String clientId = UserContext.getCurrentClientId();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT a FROM Department a WHERE a.relatedClient.id='")
		.append(clientId).append("' ");
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
		for(String id: idss) {
			Department d = this.deptDao.find(Department.class, id);
			if(d.getParent() == null) {
				throw new CommonException("CannotDelRootDepartment",d.getDeptName());
			}
		}
		return idss;
	}

	@Override
	protected DepartmentVo entityToVo(Department d) {
		DepartmentVo vo = new DepartmentVo();
		if(d.getRelatedClient() != null) {
			vo.setRelatedClientId(d.getRelatedClient().getId());
			vo.setRelatedClientName(d.getRelatedClient().getName());
		}
		vo.setId(d.getId());
		vo.setDesc(d.getDescription());
		vo.setName(d.getDeptName());
		if(d.getParent() != null) {
			vo.setParentId(d.getParent().getId());
		}
		if(!d.getSubDepts().isEmpty()) {
			for(Department sub : d.getSubDepts()) {
				DepartmentVo svo = this.entityToVo(sub);
				vo.getSubs().add(svo);
			}
		}
		return vo;
	}
	
}
