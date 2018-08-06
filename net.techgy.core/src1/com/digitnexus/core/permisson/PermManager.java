package com.digitnexus.core.permisson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.dept.ClientDaoImpl;
import com.digitnexus.core.dept.ClientType;
import com.digitnexus.core.idgenerator.ICacheIDGenerator;
import com.digitnexus.core.idgenerator.IDUtils;
import com.digitnexus.core.uidef.annotation.Action;
import com.digitnexus.core.uidef.annotation.ItemField;
import com.digitnexus.core.uidef.annotation.TableViewEditor;
import com.digitnexus.core.uidef.annotation.TreeViewEditor;
import com.digitnexus.core.vo.dept.AccountVo;
import com.digitnexus.core.vo.dept.DepartmentVo;
import com.digitnexus.core.vo.dept.EmployeeVo;
import com.digitnexus.core.vo.dept.GroupVo;

@Component
public class PermManager {

	@Value("#{configProperties['entity.package']}")
	private String  scanPackage;
	
	@Value("#{configProperties['isMainServer']}")
	private boolean isMainServer;
	
	@Autowired
	private PermDaoImpl permDao;
	
	@Autowired
	private ClientDaoImpl clientDao;
	
	@Autowired
	private GroupManager groupManager;
	
	@Autowired
	private ICacheIDGenerator generator;
	
	private Map<String,List<Permission>> clientTypeToPermissions = new HashMap<String,List<Permission>>();
	
	private Map<String,List<Permission>> clientToPermissions = new HashMap<String,List<Permission>>();
	
	private boolean initPerm = true;
	
	private void initPermissionConfig() {
		if(!this.initPerm) {
			return;
		}
		this.initPerm = false;
		if(!isMainServer) {
			return;
		}
		clientTypeToPermissions.clear();
		
		if(scanPackage == null || this.scanPackage.trim().equals("")) {
			this.scanPackage="com.digitnexus";
		}
		
		Set<Class<?>> classes = IDUtils.getInstance().getClasses(this.scanPackage.split(","));
		
		for(Class<?> cls : classes) {
			if(!cls.isAnnotationPresent(TableViewEditor.class) && !cls.isAnnotationPresent(TreeViewEditor.class)) {
	    		continue;
	    	}
			if(cls.isAnnotationPresent(TableViewEditor.class)) {
				TableViewEditor reqAnno = cls.getAnnotation(TableViewEditor.class);
				if(reqAnno.notNeedPerm()) {
	    			continue;
	    		}
				
	    	}else if(cls.isAnnotationPresent(TreeViewEditor.class)) {
	    		TreeViewEditor reqAnno = cls.getAnnotation(TreeViewEditor.class);
	    		if(reqAnno.notNeedPerm()) {
	    			continue;
	    		}
	    	}
			
			parseViewPerm(cls);
	    	Action[] actions = null;
			if(cls.isAnnotationPresent(TableViewEditor.class)) {
				TableViewEditor reqAnno = cls.getAnnotation(TableViewEditor.class);
				actions =  reqAnno.actions();
				
	    	}else if(cls.isAnnotationPresent(TreeViewEditor.class)) {
	    		TreeViewEditor reqAnno = cls.getAnnotation(TreeViewEditor.class);
	    		actions = reqAnno.actions();
	    	}
			if(actions != null) {
				for(Action a :actions) {
					String act = a.actionType().name();
	    			if(ActionType.Ext.name().equals(act)) {
	    				act = a.name();
	    			} 
					parseOnePermDef(a.permClientTypes(),cls.getName(),act,Permission.PERM_TYPE_ACTION);
		    	}
			}
		}
	}
	
	private void parseViewPerm(Class cls) {
		if(cls == Object.class) {
			return;
		}
		Field[] fs = cls.getDeclaredFields();
    	for(Field f : fs) {
    		if(!f.isAnnotationPresent(ItemField.class)) {
    			continue;
    		}
    		ItemField rf = f.getAnnotation(ItemField.class);
    		if(rf.hide() || rf.isIdable()) {
    			continue;
    		}
    		parseOnePermDef(rf.permClientTypes(),cls.getName(),f.getName(),Permission.PERM_TYPE_VIEW);
    	}
    	parseViewPerm(cls.getSuperclass());
	}

	public List<Permission> permKeyValues() {
		String tc = UserContext.getCurrentUser().getLoginClient().getTypecode().getTypeCode();
		@SuppressWarnings("unchecked")
		List<Permission> ps = this.permDao.getEntityManager().createNamedQuery("permKeyValues")
				.setParameter("typeCode", tc)
				.getResultList();
		return ps;
	}
    
	/**
	 * Init permission for new created client.
	 * @param client
	 */
    public void createClientPermision(Client client) {
    	if(!isMainServer) {
			return;
		}
    	this.initPermissionConfig();
    	List<Permission> perms = this.clientTypeToPermissions.get(client.getTypecode().getTypeCode());
    	if(perms == null || perms.isEmpty()) {
    		throw new CommonException("PermissionNotFoundForClient",client.getTypecode().getTypeCode());
    	}
    	for(Permission p : perms) {
    		p.setClient(client);
    		p.setId(this.generator.getStringId(Permission.class));
    		this.permDao.save(p);
		}
    	this.loadPermission();
    	updateAdminGroupPermission(client,perms);
	}
    
    private void updateAdminGroupPermission(Client client,List<Permission> perms) {
    	
    	if(perms == null || perms.isEmpty()) {
    		return;
    	}
    	
    	Map<String,String> params = new HashMap<String,String>();
		params.put(Client.CLIENT_ID_KEY, client.getId());
		params.put("groupType",Group.GROUP_TYPE_ADMIN);
    	List<Group> groups = this.groupManager.queryList(params);
    	
    	if(groups== null || groups.isEmpty()) {
    		return;
    	}
    	
    	for(Permission p : perms) {
    		if(ActionType.Query.name().equals(p.getAction())) {
    			for(Group g : groups) {
    				g.getPermissions().add(p);
    			}
    		}else if(Permission.PERM_TYPE_VIEW.equals(p.getPermType())) {
    			for(Group g : groups) {
    				g.getPermissions().add(p);
    			}
    		}
    	}
    }
    
    /**
	 * Init permission for new created client.
	 * @param client
	 */
    public void updatePermision() {
    	if(!isMainServer) {
			return;
		}
    	this.initPermissionConfig();
    	this.loadPermission();
    	
    	Client adminClient = clientDao.getClientByName(ClientType.Admin);
    	this.updatePermision(adminClient);
    	
    	Client zongbuClient = clientDao.getClientByName("总部");	
    	this.updatePermision(zongbuClient);
    	this.loadPermission();
	}
    
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void updatePermision(Client client) {
    	List<Permission> newPerms = this.clientTypeToPermissions.get(client.getTypecode().getTypeCode());
    	if(newPerms == null || newPerms.isEmpty()) {
    		throw new CommonException("PermissionNotFoundForClient",client.getTypecode().getTypeCode());
    	}
    	
    	List<Permission> existPerms = this.clientToPermissions.get(client.getId());
    	if(existPerms == null) {
    		existPerms = new ArrayList<Permission>();
    	}
    	
    	for(Permission p : newPerms) {
    		if(existPerms.contains(p)) {
    			continue;
    		}
    		p.setClient(client);
    		p.setId(this.generator.getStringId(Permission.class));
    		this.permDao.save(p);
    		existPerms.add(p);
		}
    	
    	updateAdminGroupPermission(client,existPerms);
    	
    	if(client.getSubClients() != null && !client.getSubClients().isEmpty()) {
    		for(Client c : client.getSubClients()) {
    			this.updatePermision(c);
    		}
    	}
    	
    }

    public void deletePermission(Permission p) {
		this.permDao.getEntityManager().createNamedQuery("removeById")
		.setParameter("id", p.getId())
		.executeUpdate();
	}

    private void loadPermission() {
		this.clientToPermissions.clear();
		@SuppressWarnings("unchecked")
		List<Permission> permList = this.permDao.getEntityManager()
				.createNamedQuery("loadAll").getResultList();
		for(Permission p: permList) {
			List<Permission> l = this.clientToPermissions.get(p.getClient().getId());
			if(l == null) {
				l = new ArrayList<Permission>();
				this.clientToPermissions.put(p.getClient().getId(), l);
			}
			l.add(p);
		}
	}

	private void parseOnePermDef(String[] clientTypeCodes,
			String entityType,String action,String type) {
		if(clientTypeCodes == null || clientTypeCodes.length == 0) {
			clientTypeCodes = ClientType.ALL_TYPES;
		}
    	for(String ct : clientTypeCodes) {
    		List<Permission> l = clientTypeToPermissions.get(ct);
			if(l == null) {
				l = new ArrayList<Permission>();
				clientTypeToPermissions.put(ct, l);
			}
			Permission p = new Permission(ct,entityType,action,type);
			p.setDescription(action);
			l.add(p);
    	}
	}
	
	public List<Permission> getClientPermissions(String clientId) {
		if(this.clientToPermissions.isEmpty()) {
			this.loadPermission();
		}
		return this.clientToPermissions.get(clientId);
	}
	
	public List<Permission> getClientPermissions(String clientId,String entityType) {
		List<Permission> ps = this.getClientPermissions(clientId);
		if(ps == null || ps.isEmpty()) {
			return null;
		}
		List<Permission> eps = new ArrayList<Permission>();
		for(Permission p : ps) {
			if(p.getEntityType().equals(entityType)) {
				eps.add(p);
			}
		}
		return eps;
	}
    
	
	public void setGroupPermission(Group group) {
		String clientId = group.getClient().getId();
		List<Permission> ps = this.getClientPermissions(clientId);
		if(ps == null || ps.isEmpty()) {
			return;
		}
		boolean isAdmin = Group.GROUP_TYPE_ADMIN.equals(group.getTypecode());
		for(Permission p : ps) {
			if(ActionType.Query.name().equals(p.getAction())) {
				group.getPermissions().add(p);
			}else if(Permission.PERM_TYPE_VIEW.equals(p.getPermType()) && isAdmin) {
				group.getPermissions().add(p);
    		}
		}
		
		List<Permission> deptPerms = this.getClientPermissions(clientId, DepartmentVo.class.getName());
		if(deptPerms != null && !deptPerms.isEmpty()) {
			for(Permission p : deptPerms) {
				group.getPermissions().add(p);
			}
		}
		
		List<Permission> empPerms = this.getClientPermissions(clientId, EmployeeVo.class.getName());
		if(empPerms != null && !empPerms.isEmpty()) {
			for(Permission p : empPerms) {
				group.getPermissions().add(p);
			}
		}
		
		List<Permission> accountPerms = this.getClientPermissions(clientId, AccountVo.class.getName());
		if(accountPerms != null && !accountPerms.isEmpty()) {
			for(Permission p : accountPerms) {
				group.getPermissions().add(p);
			}
		}
		
		List<Permission> groupPerms = this.getClientPermissions(clientId, GroupVo.class.getName());
		if(groupPerms != null && !groupPerms.isEmpty()) {
			for(Permission p : groupPerms) {
				group.getPermissions().add(p);
			}
		}
	}
}
