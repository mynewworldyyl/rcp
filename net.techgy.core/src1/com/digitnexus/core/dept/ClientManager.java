package com.digitnexus.core.dept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.protocol.Response;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.account.Account;
import com.digitnexus.core.account.AccountDaoImpl;
import com.digitnexus.core.i18n.I18NUtils;
import com.digitnexus.core.idgenerator.ICacheIDGenerator;
import com.digitnexus.core.permisson.Group;
import com.digitnexus.core.permisson.GroupDaoImpl;
import com.digitnexus.core.permisson.PermManager;

@Component
public class ClientManager {

	public static final String [] ZONG_BU_CREATED_LIST={ClientType.Region};
	
	public static final String [] DAQU_CREATED_LIST={ClientType.Factory,ClientType.Project};
	
	//public static final String [] ZONG_BU_CREATED_LIST={ClientType.DaQu};
	
	private static final Map<String,Map<String,String>> CREATED_CLIENT_LIST = new HashMap<String,Map<String,String>>();
	
	@Autowired
	private ClientDaoImpl clientDao;
	
	@Autowired
	private ICacheIDGenerator generator;
	
	@Autowired
	private ClientTypeDaoImpl clientTypeDao;
	
	@Autowired
	private AccountDaoImpl accountDao;
	
	@Autowired
	private EmployeeDaoImpl empDao;
	
	@Autowired
	private DeptDaoImpl deptDao;
	
	@Autowired
	private PermManager permManager;
	
	@Autowired
	private GroupDaoImpl groupDao;
	
	public List<Client> getSubClients(String rootParentClientId) {
		List<Client> subClients = new ArrayList<Client>();
		Client parent = this.clientDao.getClient(rootParentClientId);
		getAllSubClient(parent,subClients);
		subClients.add(parent);
		return subClients;
	}
	
	public List<String> getSubClientIds(String rootParentClientId) {
		List<String> subClients = new ArrayList<String>();
		Client parent = this.clientDao.getClient(rootParentClientId);
		getAllSubClientIds(parent,subClients);
		subClients.add(rootParentClientId);
		return subClients;
	}
	
	public Set<Client> getClients(Set<String> ids) {
		Set<Client> cs = this.clientDao.getClients(ids);
		return cs;
	}
	
	public Set<Client> getRegions() {
		return this.getClientByType(ClientType.Region);
	}
	
	public Set<Client> getFactorys() {
		return this.getClientByType(ClientType.Factory);
	}
	
	public Set<Client> getProjects() {
		return this.getClientByType(ClientType.Project);
	}
	
	public Set<Client> getVendors() {
		return this.getClientByType(ClientType.Vendor);
	}
	
	@SuppressWarnings("unchecked")
	private Set<Client> getClientByType(String typecode) {
		List<Client> cs = this.clientDao.getEntityManager()
				.createNamedQuery("getClientByTypecode")
				.setParameter("typecode", typecode)
				.getResultList();
		Set<Client> set = new HashSet<Client>();
		set.addAll(cs);
		return set;
	}
	
	private void getAllSubClientIds(Client parent, List<String> subClients) {
		for(Client c : parent.getSubClients()) {
			subClients.add(c.getId());
			this.getAllSubClientIds(c, subClients);
		}
	}

	private void getAllSubClient(Client parent, List<Client> subClients) {
		for(Client c : parent.getSubClients()) {
			subClients.add(c);
			this.getAllSubClient(c, subClients);
		}
	}
	
	public Response createClient(String name,String desc,String deptId,
			String accountName,String password,String clientType) {
		Response resp = null;
		if(clientType == null || clientType.trim().equals("")) {
			resp = new Response(false);
			resp.setMsg(I18NUtils.getInstance().getString("ClientTypeCanNotBeNull"));
			return resp;
		}
		
		Client ce = new Client();
		ce.setId(generator.getStringId(Client.class));
		
		Client lc = UserContext.getCurrentUser().getLoginClient();
		if(lc != null) {
			Client client = clientDao.getClientByName(lc.getName());
			ce.setParent(client);
		}
		
		ClientType ct = clientTypeDao.getClientTypeByTypeCode(clientType);
		ce.setTypecode(ct);
		
		ce.setDescription(desc);
		ce.setName(name);
		ce.setRemark("");
		clientDao.save(ce);
		this.permManager.createClientPermision(ce);
		
		Department d = null;
		if(deptId != null && !"".equals(deptId.trim())) {
			d = deptDao.find(Department.class, deptId);
			d.setRelatedClient(ce);
			deptDao.update(d);
		}
		
		createAdmin(accountName,password,ce);
		resp = new Response(true);
		return resp;
	}
	
	public Map<String,String> getCreatedList() {
		Client lc = UserContext.getCurrentUser().getLoginClient();
		if(ClientType.Headquarter.equals(lc.getTypecode().getTypeCode())) {
			Map<String,String> m = CREATED_CLIENT_LIST.get(ClientType.Headquarter);
			if(m == null) {
				m = new HashMap<String,String>();
				CREATED_CLIENT_LIST.put(ClientType.Headquarter, m);
			}
			if(m.isEmpty()) {
				for(String key : ZONG_BU_CREATED_LIST) {
					m.put(key, I18NUtils.getInstance().getString(key));
				}
			}
			return m;
		}else if(ClientType.Region.equals(lc.getTypecode().getTypeCode())) {
			Map<String,String> m = CREATED_CLIENT_LIST.get(ClientType.Region);
			if(m == null) {
				m = new HashMap<String,String>();
				CREATED_CLIENT_LIST.put(ClientType.Region, m);
			}
			if(m.isEmpty()) {
				for(String key : DAQU_CREATED_LIST) {
					m.put(key, I18NUtils.getInstance().getString(key));
				}
			}
			return m;
		}
		return Collections.emptyMap();
	}
	
	private void createAdmin(String accountName,String pw,Client client) {
		
		/*Employee emp = new Employee();
		String id = generator.getStringId(Employee.class);
		emp.setId(id);
		emp.setClient(client);
		emp.setEmpCode("0");
		emp.setName(accountName);
		emp.setOnBoardTime(new Date(System.currentTimeMillis()));
		emp.setStatu(Employee.STATU_ONBOARD);
		emp.setTypecode(Employee.TYPE_ADMIN);
		emp.setBelongDept(dept);
		empDao.save(emp);*/
		
		//Client srcClient = clientDao.getClientByName("超级租户");
		String id =  generator.getStringId(Account.class);
		Account a = new Account();
		a.setAccountName(accountName);
		a.setClient(client);
		//a.setEmployee(emp);
		a.setId(id);
		a.setPassword(pw);
		a.setStatu(Account.STATU_ACTIVE);
		a.getRelatedClients().add(client);
		a.setTypeCode(Account.TYPE_ADMIN);
		accountDao.save(a);
		
		Group group = new Group();
		group.setClient(client);
		group.setDescription(client.getDescription());
		group.setId(generator.getStringId(Group.class));
		//Name as the client type code
		group.setName(client.getName());
		group.setTypecode(Group.GROUP_TYPE_ADMIN);
		this.permManager.setGroupPermission(group);
		group.getAccounts().add(a);
		a.getGroups().add(group);
		groupDao.save(group);	
		
	}
	
}
