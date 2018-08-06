package com.digitnexus.core.dept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.protocol.Response;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.idgenerator.ICacheIDGenerator;
import com.digitnexus.core.vo.dept.DepartmentVo;
import com.digitnexus.core.vo.dept.EmployeeVo;

@Component
public class EmpManager {

	@Autowired
	private DeptManager deptManager ;
	
	@Autowired
	private ClientManager clientManager ;
	
	@Autowired
	private EmployeeDaoImpl empDao;
	
	@Autowired
	private ICacheIDGenerator generator;
	
	public EmployeeVo queryEmpAsDeptTree(String rootParentClientId, Map<String,String> params) {
		if(rootParentClientId == null || rootParentClientId.trim().equals("")) {
			rootParentClientId = UserContext.getCurrentClientId();
		}
		Department root = this.deptManager.getDepartment(rootParentClientId);
		
		List<EmployeeVo> lvo = new ArrayList<EmployeeVo>();
		Map<String,EmployeeVo> depts = new HashMap<String,EmployeeVo>();
		
		this.addDeptAsEmpVo(lvo,depts,root,null);
		
		/*List<String> clients = this.clientManager.getSubClientIds(rootParentClientId);
		List<String> clientids = new ArrayList<String>();
		//clientids.add(rootParentClientId);
		for(String cid : clients) {
			clientids.add(cid);
		}*/
		
		List<String> deptIds = deptManager.getAllDepartmentIds(rootParentClientId, root.getId());
		
		Map<String,Object> ps = new HashMap<String,Object>();
		ps.put("deptIds", deptIds);
		ps.put("isAdmin", false);
		if(params != null && !params.isEmpty()) {
			ps.putAll(params);
		}
		
		List<Employee> l = empDao.queryEmp(ps);
		
		for(Employee e :  l) {
			EmployeeVo vo = this.getOnEmployeeVO(e);
			EmployeeVo pvo = depts.get(vo.getParentDeptId());
			pvo.getSubDeptOrEmp().add(vo);
		}
		
		return depts.get(root.getId());
	}

	private void addDeptAsEmpVo(List<EmployeeVo> lvo,Map<String,EmployeeVo> depts, Department root,EmployeeVo pvo) {
		if(root == null) {
			return;
		}
		EmployeeVo vo = new EmployeeVo();
		vo.setId(root.getId());
        vo.setName(root.getDeptName());
        vo.nodeType = DepartmentVo.class.getName();
       
        lvo.add(vo);
        depts.put(vo.getId(), vo);
        if(pvo != null) {
        	vo.setParentDeptId(pvo.getId());
        	pvo.getSubDeptOrEmp().add(vo);
        }
        for(Department d : root.getSubDepts()) {
        	this.addDeptAsEmpVo(lvo, depts, d,vo);
        }
	}

	private EmployeeVo getOnEmployeeVO(Employee e) {
		EmployeeVo vo = new EmployeeVo();
		vo.setCode(e.getEmpCode());
		vo.setId(e.getId());
		vo.setName(e.getName());
		vo.setOnBoardTime(e.getOnBoardTime());
		vo.setParentDeptId(e.getBelongDept().getId());
		vo.setStatu(e.getStatu());
		vo.setTypecode(e.getTypecode());
		vo.nodeType = EmployeeVo.class.getName();
		return vo;
	}

	public void save(List<EmployeeVo> emplist) {
        for(EmployeeVo vo : emplist) {
        	Employee e = this.getFromVo(vo);
        	this.empDao.save(e);
        }
	}
	
	public Response delete(String clsName, List<String> ids) {
		Response resp = new Response(true);
		/*Type type = new TypeToken<List<String>>(){}.getType();
		List<String> idls = JsonUtils.getInstance().fromJson(ids, type, false, false);*/
		for(String id : ids) {
			this.empDao.remove(Employee.class, id);
		}
		return resp;
	}
	
	
	public void update(List<EmployeeVo> emplist) {
    	for(EmployeeVo vo : emplist) {
    		Employee e = getFromVoForUpdate(vo);
    		this.empDao.update(e);
		}
	}
	
	private Employee getFromVoForUpdate(EmployeeVo vo) {
		Employee e = this.empDao.find(Employee.class, vo.getId());
		e.setEmpCode(vo.getCode());
		e.setName(vo.getName());
		e.setOnBoardTime(vo.getOnBoardTime());
		e.setStatu(vo.getStatu());
		e.setTypecode(vo.getTypecode());
		return e;
	}
	
	private Employee getFromVo(EmployeeVo vo) {
		Employee e = new Employee();
		Department d = this.deptManager.getDepartmentById(vo.getParentDeptId());
		e.setBelongDept(d);
		e.setClient(UserContext.getCurrentUser().getLoginClient());
		e.setEmpCode(vo.getCode());
		String id = vo.getId();
		if(id == null) {
			id = this.generator.getStringId(Employee.class);
		}
		e.setId(id);
		e.setName(vo.getName());
		e.setOnBoardTime(vo.getOnBoardTime());
		e.setStatu(vo.getStatu());
		e.setTypecode(vo.getTypecode());
		return e;
	}
}
