package com.digitnexus.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digitnexus.core.account.Account;
import com.digitnexus.core.account.AccountManager;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.osgiservice.impl.SpringContext;
import com.digitnexus.core.permisson.Group;
import com.digitnexus.core.permisson.Permission;

public class UserContext {

    private final static Logger logger = LoggerFactory.getLogger(UserContext.class);
    private static ThreadLocal<UserSession> entityThreadLocal = new ThreadLocal<UserSession>();
    //private static final String CURRENT_USER_KEY = "_current_user_key";
    public static final String CURRENT_LOCALE_KEY = "_current_locale_key";

    public static boolean DEBUG = true;
    
    public static String getCurrentClientId() {
        return getCurrentUser().getLoginClient().getId();
    }

    public static String getCurrentUserId() {
        return getCurrentUser().getAccount().getId();
    }

    public final static UserInfo getCurrentUser() {
    	UserSession userInfo =  entityThreadLocal.get();
        return userInfo.getUserInfo();
    }
    
    public final static Account getAccount() {
      return UserContext.getCurrentUser().getAccount();
    }
    
    public final static boolean isAdminAccount() {
    	if(UserContext.getCurrentUser() == null 
    			||UserContext.getCurrentUser().getAccount() == null) {
    		return false;
    	}
        return Account.TYPE_ADMIN.equals(UserContext.getCurrentUser().getAccount().getTypeCode());
    }

    public static void releaseContext() {
        entityThreadLocal.remove();
    }

    public static void setLocale(Locale locale) {
        entityThreadLocal.get().setAttribute(CURRENT_LOCALE_KEY, locale);
    }

    public static Locale getLocale() {
        if (entityThreadLocal.get() != null) {
            Locale locale = (Locale)entityThreadLocal.get().getAttribute(CURRENT_LOCALE_KEY);
            if (locale != null) {
                return locale;
            }
        }
        return Locale.getDefault();
    }
    
    public static void initForTestcase(UserInfo ui) {
        UserSession s = new UserSession(ui);
        entityThreadLocal.set(s);
    }

	public static void init(String accountName, String clientId, String localeStr) {
		
		if(accountName == null || "".equals(accountName.trim())) {
			throw new RuntimeException("User not login");
		}
		if(clientId == null || "".equals(clientId.trim())) {
			throw new RuntimeException("User not login");
		}
		if(localeStr == null || "".equals(localeStr.trim())) {
			throw new RuntimeException("User not login");
		}
		String[] cl = localeStr.split(",");
		Locale l = null;
		if(cl.length >=2) {
			l = new Locale(cl[0],cl[1]);
		}else {
			l = new Locale(cl[0]);
		}
		init( accountName,  clientId,  l);
    
	}
	
   public static void init(String accountName, String clientId, Locale l) {
		
    	AccountManager am = SpringContext.getContext().getBean(AccountManager.class);
		Account account = am.getActieAccountByName(accountName,clientId);
		
		List<Group> gs = new ArrayList<Group>();
		for(Group g : account.getGroups()) {
			if(g.getClient().getId().equals(clientId)) {
				gs.add(g);
			}
		}
		account.getGroups().clear();
		account.getGroups().addAll(gs);
		
		if(account.getPermissons() != null) {
			List<Permission> pss = new ArrayList<Permission>();
			for(Permission p : account.getPermissons()) {
				if(p.getClient().getId().equals(clientId)) {
					pss.add(p);
				}
			}
			account.getPermissons().clear();
			account.getPermissons().addAll(pss);
		}
		Client loginClient = null;
		for(Client c : account.getRelatedClients()) {
			if(c.getId().equals(clientId)){
				loginClient = c;
				break;
			}
		}
		
		UserInfo ui = new UserInfo(loginClient,account,l);
        UserSession s = new UserSession(ui);
        entityThreadLocal.set(s);
    
	}
}
