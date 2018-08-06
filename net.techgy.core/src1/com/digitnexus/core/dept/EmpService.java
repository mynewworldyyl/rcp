package com.digitnexus.core.dept;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.protocol.Response;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.db.PersistenceManager;
import com.digitnexus.core.vo.dept.EmployeeVo;
import com.google.gson.reflect.TypeToken;

@Component("empService")
@Path("/empService")
@Scope("singleton")
public class EmpService {

	@Autowired
	private EmpManager empManager;

	@POST
	@Path("/query")
	@Transactional
	public String query(@FormParam("clsName") String cls,@FormParam("body") Map<String,String> params) {
		EmployeeVo emp = this.empManager.queryEmpAsDeptTree(UserContext.getCurrentClientId(), params);
		List<EmployeeVo> l = new ArrayList<EmployeeVo>();
		l.add(emp);
		Response resp = new Response(true);
		String json = JsonUtils.getInstance().toJson(l, true);
		resp.setData(json);
		resp.setClassType(EmployeeVo.class.getName());
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}

	@POST
	@Path("/update")
	@Transactional
	public String update(@FormParam("clsName") String clsName,@FormParam("vos") List<EmployeeVo> emplist) {
		//List<EmployeeVo> emplist = (List<EmployeeVo>) this.getFromJson(vos,clsName);
		this.empManager.update(emplist);
		Response resp = new Response(true);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}

	@POST
	@Path("/save")
	@Transactional
	public String save(@FormParam("clsName") String clsName,@FormParam("vos") List<EmployeeVo> emplist) {
		//List<EmployeeVo> emplist = (List<EmployeeVo>) this.getFromJson(vos,clsName);
		this.empManager.save(emplist);
		Response resp = new Response(true);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}

	@POST
	@Path("/delete")
	@Transactional
	public String delete(@FormParam("clsName") String clsName,@FormParam("ids") List<String> ids) {
		Response resp = empManager.delete(clsName, ids);
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}

	private Class loadCls(String clsName) {
		Class<?> clas = null;
		try {
			clas = PersistenceManager.class.getClassLoader().loadClass(clsName);
		} catch (ClassNotFoundException e) {
			throw new CommonException("ResourceNoFound", clsName);
		}
		return clas;
	}

	@POST
	@Path("/query1")
	@Transactional
	public String queryList(@FormParam("clsName") String cls,
			@FormParam("body") Map<String, String> ps) {
		List<EmployeeVo> ql = new ArrayList<EmployeeVo>();
		if (ps == null) {
			ql.addAll(reqList);
		} else {
			String name = ps.get("reqNum");
			if (name == null || "".equalsIgnoreCase(name.trim())) {
				ql.addAll(reqList);
			} else {
				for (EmployeeVo rd : this.reqList) {
					if (rd.getName().indexOf(name) != -1) {
						ql.add(rd);
					}
				}
			}
		}
		Response resp = new Response(true);
		String json = JsonUtils.getInstance().toJson(ql, true);
		resp.setData(json);
		// resp.setClassType(def.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp, false);
		return respStr;
	}

	private List<EmployeeVo> reqList = new ArrayList<EmployeeVo>();
	{
		EmployeeVo dept = new EmployeeVo();
		dept.setCode("");
		dept.setId("1");
		dept.setName("总部");
		dept.setParentDeptId(null);

		EmployeeVo emp01 = new EmployeeVo();
		emp01.setCode("EMP01");
		emp01.setId("2");
		emp01.setName("ZangSan");
		emp01.setParentDeptId(null);
		emp01.setStatu(Employee.STATU_ONBOARD);
		dept.getSubDeptOrEmp().add(emp01);

		EmployeeVo emp02 = new EmployeeVo();
		emp02.setCode("EMP02");
		emp02.setId("3");
		emp02.setName("LiSi");
		emp02.setParentDeptId(null);
		emp01.setStatu(Employee.STATU_ONBOARD);
		dept.getSubDeptOrEmp().add(emp02);

		/*
		 * DepartmentVo subdept1 = new DepartmentVo();
		 * subdept1.setCode("Code2"); subdept1.setDesc("desc 2");
		 * subdept1.setId("2"); subdept1.setName("工厂");
		 * subdept1.setParent(null); dept.getSubs().add(subdept1);
		 */

		reqList.add(dept);
	}
}
