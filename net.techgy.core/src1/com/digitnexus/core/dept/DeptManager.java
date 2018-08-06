package com.digitnexus.core.dept;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeptManager {

	@Autowired
	private DeptDaoImpl deptDao;
	
	@Autowired
	private EmployeeDaoImpl emptDao;
	
	@Autowired
	private ClientDaoImpl clientDao;
	
	private Department root = null;
	
	public Department getDepartment(String rootParentClientId) {
		/*if(root == null) {
			root = deptDao.getRoot();
		}*/
		root = deptDao.getRoot();
		return getDepartmentByClient(rootParentClientId,root);
	}
	
	public Department getDepartment(String rootParentClientId,String deptId) {
		Department r = this.getDepartment(rootParentClientId);
		if(r == null) {
			return null;
		}else {
			return getDepartmentByDeptId(deptId,r);
		}
	}
	
	public List<String> getAllDepartmentIds(String rootParentClientId,String deptId) {
		Department rootDept = this.getDepartment(rootParentClientId);
		if(rootDept == null) {
			return null;
		} else {
			if(deptId != null) {
				rootDept = getDepartmentByDeptId(deptId,rootDept);
			}
			 List<String> ids = new ArrayList<String>();
			 getAllId(rootDept,ids);
			 return ids;
		}
	}
	
	private void getAllId(Department rootDept, List<String> ids) {
		ids.add(rootDept.getId());
		if(rootDept.getSubDepts() != null) {
			for(Department d : rootDept.getSubDepts()) {
				this.getAllId(d, ids);
			}
		}
	}

	public Department getDepartmentById(String deptId) {
		/*if(root == null) {
			root = deptDao.getRoot();
		}*/
		root = deptDao.getRoot();
		return getDepartment(root.getClient().getId(),deptId);
	}
	
	public Department cloneDepartment(String rootParentClientId) {
		if(root == null) {
			root = deptDao.getRoot();
		}
		return cloneDept(getDepartmentByClient(rootParentClientId,root));
	}
	
	private Department cloneDept(Department d) {
		try {
			return d.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Department cloneDepartment(String rootParentClientId,String deptId) {
		Department r = this.getDepartment(rootParentClientId);
		if(r == null) {
			return null;
		}else {
			return cloneDept(getDepartmentByDeptId(deptId,r));
		}
	}
	
	public Department getDepartmentByDeptId(String deptId,Department dept) {
		if(dept.getId().equals(deptId)) {
			return dept;
		}
		 Set<Department> subs = dept.getSubDepts();
		 for(Department d : subs) {
			 Department p = getDepartmentByDeptId(deptId,d);
			 if(p != null) {
				 return p;
			 }
		 }
		 return null;
	}
	
	private Department getDepartmentByClient(String rootParentClientId,Department dept) {
		if(dept.getRelatedClient() != null && dept.getRelatedClient().getId().equals(rootParentClientId)) {
			return dept;
		}
		 Set<Department> subs = dept.getSubDepts();
		 for(Department d : subs) {
			 Department p = getDepartmentByClient(rootParentClientId,d);
			 if(p != null) {
				 return p;
			 }
		 }
		 return null;
	}
	
	public void clearCache() {
		this.root = null;
	}
	
	
	
}
