package com.digitnexus.core.masterdata;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.protocol.Response;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.db.AbstractPersistenceListener;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.dept.ClientManager;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.dept.Department;
import com.digitnexus.core.dept.DeptManager;
import com.digitnexus.core.vo.masterdata.ProjectVo;

@Component
public class ProjectProcessor extends AbstractPersistenceListener<Project,ProjectVo>{

	@Autowired
	private ProjectDaoImpl prjDao;
	
	@Autowired
	private CommonValueDaoImpl cvDao;
	
	@Autowired
	private ClientManager clientManager;
	
	@Autowired
	private DeptManager deptManager;
	
	@Override
	public String getVoClsName() {
		return ProjectVo.class.getName();
	}

	@Override
	public Response afterSaveEntity(Project e, ProjectVo vo, Response resp) {
		resp = super.afterSaveEntity(e, vo, resp);
		clientManager.createClient(e.getName(), e.getPrefix(), null, 
				vo.getAdminAccountName(), vo.getPw(), ClientType.Project);
		return resp;
	}

	@Override
	public Response afterUpdateEntity(Project e, ProjectVo vo, Response resp) {
		return super.afterUpdateEntity(e, vo, resp);
	}

	@Override
	protected Project voToEntityForSave(ProjectVo vo) {
		Project p = new Project();
		String id = vo.getId();
		if(id == null || "".equals(id.trim())) {
			 id =  generator.getStringId(Project.class);
		}
		p.setId(id);
		p.setClient(UserContext.getCurrentUser().getLoginClient());
		setBaseField(vo,p);
		return p;
	}

	@Override
	protected Project voToEntityForUpdate(ProjectVo vo) {
		Project d = prjDao.find(Project.class, vo.getId());
		if(d == null) {
			 throw new CommonException("EntityNotFoundForUpdate",vo.getName());
		}
		setBaseField(vo,d);
		return d;
	}
	
	private void setBaseField(ProjectVo vo,Project p) {
		p.setName(vo.getName());
		if(vo.getAreas() != null && !vo.getAreas().isEmpty()) {
			Set<Client> areas = this.clientManager.getClients(vo.getAreas());
			p.setAreas(areas);
		}
	    p.setBidPrice(vo.getBidPrice());
	    p.setBuildAddr(vo.getBuildAddr());
	    p.setCode(vo.getCode());
	    p.setContacts(vo.getContacts());
	    p.setContractMode(vo.getContractMode());
	    p.setContractPrice(vo.getContractPrice());
	    p.setDesc(vo.getDesc());
	    //p.setExt0();
	    //p.setExt1();
	    //p.setExt2();
	    //p.setExt3();
	    //p.setExt4();
	    //p.setExt5();
	    if(vo.getFactoryIds() != null && !vo.getFactoryIds().isEmpty()) {
			Set<Client> factorys = this.clientManager.getClients(vo.getFactoryIds());
			p.setFactorys(factorys);;
		}
	    p.setFrameworkType(vo.getFrameworkType());
	    p.setName(vo.getName());
	    p.setOthers(vo.getOthers());
	    p.setPartyA(vo.getPartyA());
	    p.setPeriod(vo.getPeriod());
	    p.setPrefix(vo.getPrefix());
	    p.setRespPerson(vo.getRespPerson());
	    p.setScale(vo.getScale());
	    p.setSpan(vo.getSpan());
	    p.setStartOn(vo.getStartOn());
	    p.setStatu(vo.getStatu());
	}

	@Override
	public Object beforeQuery(Class<?> cls, Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT a FROM Project a");
		return sb.toString();
	}

	@Override
	protected ProjectVo entityToVo(Project vo) {
		ProjectVo p = new ProjectVo();
		p.setId(vo.getId());
		p.setName(vo.getName());
		if(vo.getAreas() != null && !vo.getAreas().isEmpty()) {
			Set<String> areaIds = new HashSet<String>();
			for(Client c : vo.getAreas()) {
				areaIds.add(c.getId());
			}
			p.setAreas(areaIds);
		}
	    p.setBidPrice(vo.getBidPrice());
	    p.setBuildAddr(vo.getBuildAddr());
	    p.setCode(vo.getCode());
	    p.setContacts(vo.getContacts());
	    p.setContractMode(vo.getContractMode());
	    p.setContractPrice(vo.getContractPrice());
	    //p.setCreateDept(vo.getClient().getName());
	    p.setDesc(vo.getDesc());
	    //p.setExt0();
	    //p.setExt1();
	    //p.setExt2();
	    //p.setExt3();
	    //p.setExt4();
	    //p.setExt5();
	    if(vo.getFactorys() != null && !vo.getFactorys().isEmpty()) {
			Set<String> factoryIds = new HashSet<String>();
			for(Client c : vo.getFactorys()) {
				factoryIds.add(c.getId());
			}
			p.setAreas(factoryIds);
		}
	    p.setFrameworkType(vo.getFrameworkType());
	    p.setName(vo.getName());
	    p.setOthers(vo.getOthers());
	    p.setPartyA(vo.getPartyA());
	    p.setPeriod(vo.getPeriod());
	    p.setPrefix(vo.getPrefix());
	    p.setRespPerson(vo.getRespPerson());
	    p.setScale(vo.getScale());
	    p.setSpan(vo.getSpan());
	    p.setStartOn(vo.getStartOn());
	    p.setStatu(vo.getStatu());
		return p;
	}
}
