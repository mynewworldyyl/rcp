package net.techgy.ui.core.actions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.BundleContext;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.utils.I18NManager;
import com.digitnexus.base.utils.JsonUtils;

import net.cmty.ui.core.i18n.I18NUtils;
import net.techgy.cmty.service.DataDto;
import net.techgy.cmty.ui.account.LoginDialog;
import net.techgy.cmty.ui.account.RegisterDialog;
import net.techgy.cmty.ui.preference.GlobalDataUtils;
import net.techgy.cmty.ui.views.MainMenuViewPart;
import net.techgy.ui.core.network.WSConn;
import net.techgy.ui.core.utils.UIUtils;

public class ActionManager {

	private static ActionManager instance = null;
	
	@Inject
	private IEclipseContext context;
	
	/*private ActionManager(){}
	
	public static ActionManager getInstace(){
		return instance;
	}*/
	
	private Map<String,IAction> actions = new HashMap<String,IAction>();
	
	public void registerAction(IAction act) {
		if(this.actions.containsKey(act.getId())) {
			//throw new CommonException("SystemError",act.getId());
			return;
		}
		this.actions.put(act.getId(), act);
	}
	
	public void removeAction(IAction act) {
		this.removeAction(act.getId());
	}
	
	public void removeAction(String id) {
		if(this.actions.containsKey(id)) {
			this.actions.remove(id);
		}
	}
	
	public IAction getAction(String id) {
		if(this.actions.containsKey(id)) {
			return this.actions.get(id);
		}
		return null;
	}
	
	public int executeAction(String id, String url, Map<String,Object> params) {
		if(!this.actions.containsKey(id)) {
			throw new CommonException("SystemError: "+id);
		}
		IAction act = this.getAction(id);
		ActionDef ad = new ActionDef(id,id,url);
		return act.execute(ad,params);
	}
	
	public int executeAction(ActionDef ad,Map<String,Object> params) {
		if(!this.actions.containsKey(ad.getId())) {
			throw new CommonException("SystemError",ad.getId());
		}
		IAction act = this.getAction(ad.getId());
		return act.execute(ad,params);
	}
	
	public void initActions(BundleContext cxt) {
		String id = "Login";
		// acs.add(new ActionDef(id,id));
		cxt.registerService(IAction.class, new AbstractAction(id) {
			@Override
			public int execute(ActionDef ad, Map<String, Object> params) {
				if (params == null || params.get("shell") == null) {
					throw new CommonException("SystemError");
				}

				Shell shell = (Shell) params.get("shell");
				
			    LoginDialog dialog = new LoginDialog(shell, "Login");
				int code = dialog.open();
				if (code != Window.OK) {
					return FAIL;
				}
				
				String un = dialog.getUsername();
				String pw = dialog.getPassword();
					
				//登陆账号
				DataDto resp = WSConn.ins().login(un,pw);
				if (!resp.isSuccess()) {
					MessageDialog.openError(shell, I18NUtils.getInstance()
							.getString("Note"), I18NUtils.getInstance()
							.getString("LoginFail", resp.getMsg()));
					return FAIL;
				}
				Map<String, String> clientLists = JsonUtils.getInstance()
						.getStringMap(resp.getData(), false);
				if (clientLists == null || clientLists.isEmpty()) {
					MessageDialog.openError(shell, I18NUtils.getInstance()
							.getString("Note"), I18NUtils.getInstance()
							.getString("NoPermLoginClient", un));
					return FAIL;
				}
				
				//账号登陆成功后，进入指定的租户。
				//正常流程是用户选登陆账号，服务器返回账号所能进入的租户，用户选择租户后进入租户
				String clientId = clientLists.keySet().iterator().next();
				/*RWT.getUISession().setAttribute(UIConstants.LOGIN_CLIENT,
						clientId);
				RWT.getUISession().setAttribute(UIConstants.LOGIN_ACCOUNT, un);*/
				resp.setClientId(clientId);
				resp.setClassType(Map.class.getName());
				resp = WSConn.ins().loginClient(resp);
				if (!resp.isSuccess()) {
					MessageDialog.openError(shell, I18NUtils.getInstance()
							.getString("Note"), I18NUtils.getInstance()
							.getString("LoginFail", resp.getMsg()));
					return FAIL;
				} else {
					try {
						//进入租户成功，保存登陆信息，如记住账号密码等
						//SettingStore ss = RWT.getSettingStore();
						boolean isRem = dialog.isRem();
						if (isRem) {
							GlobalDataUtils.ins().setValue(
									UIConstants.LAST_PASSWD, pw);
							GlobalDataUtils.ins().setValue(
									UIConstants.LAST_UUSER, un);
							GlobalDataUtils.ins().setValue(
									UIConstants.IS_REMENBER_UUSER_PW,
									Boolean.TRUE.toString());
						} else {
							GlobalDataUtils.ins().setValue(
									UIConstants.IS_REMENBER_UUSER_PW,
									Boolean.FALSE.toString());
							GlobalDataUtils.ins().setValue(
									UIConstants.LAST_PASSWD, null);
							GlobalDataUtils.ins().setValue(
									UIConstants.LAST_UUSER, null);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (params.get("accountLabel") != null) {
						Label label = (Label) params.get("accountLabel");
						label.setText(I18NUtils.getInstance().getString(
								"CurrenLoginAccount")
								+ un);
					}
					
					EPartService partService = context.get(EPartService.class);
					//MPart mainMenuPart = partService.createPart(MainMenuViewPart.class.getName());
					MPart p = partService.showPart(MainMenuViewPart.class.getName(), PartState.ACTIVATE);
					p.setVisible(true);
					
					/*UI ui = (UI)params.get("ui");
					if(ui != null) {
						//tabris相关
						ui.getActionOperator().setActionVisible(LogoutAction.class.getName(), true);
						ui.getActionOperator().setActionVisible(LoginAction.class.getName(), false);
					}
					if(UIUtils.getInstance().isPCBrowser()) {
						CmtyWindow.showPane(ProfileManager.getProfile()
								.getString(GeneralPreferencePage.class.getName(),
										CmtyWindow.CMTY_DEFAULT_PANEL_ID, ""));
					}
					*/
					return OK;
				}
			}
		}, null);

		id = "Logout";
		// acs.add(new ActionDef(id,id));
		cxt.registerService(IAction.class, new AbstractAction(id) {
			@Override
			public int execute(ActionDef ad, Map<String, Object> params) {
				Shell shell = (Shell) params.get("shell");
				
				DataDto resp = WSConn.ins().logout();

				if (!resp.isSuccess()) {
					if (params != null && (params.get("shell") != null)) {
						UIUtils.getInstance().showNodeDialog(shell,I18NUtils.getInstance()
										.getString(resp.getMsg()));
					}
					return FAIL;
				}
				if (params != null && (params.get("accountLabel") != null)) {
					Label label = (Label) params.get("accountLabel");
					label.setText(I18NUtils.getInstance().getString("NoLogin"));
				}

				/*RWT.getUISession().removeAttribute(UIConstants.LOGIN_ACCOUNT);
				RWT.getUISession().removeAttribute(UIConstants.LOGIN_CLIENT);
				// RWT.getUISession().getHttpSession().invalidate();
				
				UI ui = (UI)params.get("ui");
				if(ui != null) {
					ui.getActionOperator().setActionVisible(
							LogoutAction.class.getName(), false);
					ui.getActionOperator().setActionVisible(
							LoginAction.class.getName(), true);
				}*/
				
				return OK;
			}
		}, null);

		id = "Register";
		// acs.add(new ActionDef(id,id));
		cxt.registerService(IAction.class, new AbstractAction(id) {
			@Override
			public int execute(ActionDef ad, Map<String, Object> params) {
				if (params == null || params.get("shell") == null) {
					throw new CommonException("SystemError");
				}
				Shell shell = (Shell) params.get("shell");
				RegisterDialog dialog = new RegisterDialog(shell, "Register");
				int code = dialog.open();
				if (code != Window.OK) {
					return FAIL;
				}

				String un = dialog.getUsername();
				String pw = dialog.getPassword();
				//String cpw = dialog.getConformPassword();

				Map<String, String> ps = new HashMap<String, String>();
				ps.put("username", un);
				ps.put("pw", pw);
				
				DataDto resp = WSConn.ins().call("accountService","register", ps);
				if (!resp.isSuccess()) {
					MessageDialog.openError(shell, I18NUtils.getInstance().getString("Note"),
							I18NUtils.getInstance().getString("RegisterFail", resp.getMsg()));
					return FAIL;
				}else {
					StringBuffer url = new StringBuffer("/cmty/fileUpload?");
					
					I18NManager i18nManager = null;//SingletonUtil.getSessionInstance(I18NManager.class);
					Locale l = i18nManager.getLocale();
					String lstr = l.getLanguage()+"," + l.getCountry();
					
					url.append(UIConstants.REQ_USER_ID).append("=").append(un);
					url.append("&").append(UIConstants.REQ_USER_CLIENT_ID).append("=").append("0");
					url.append("&").append(UIConstants.REQ_USER_LOCALE).append("=").append(lstr);
					
					//dialog.getFile().submit(url.toString());
				}
				dialog.close();
				return OK;
			}
		}, null);
	}
}
