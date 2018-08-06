package net.techgy.ui.core.content;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.Event;

import com.digitnexus.base.event.CmtyEventAdmin;
import com.digitnexus.base.excep.CommonException;

import net.cmty.ui.core.workbench.WorkbenchWindow;
import net.techgy.ui.core.CmtyServiceManaer;
import net.techgy.ui.core.CoreUIActivator;
import net.techgy.ui.core.network.WSConn;

 /**
 * 取得当前窗口容器管理器，每个登陆用户都有一个容器管理器，其他所有内容都显示在此管理器中。
 * 每个用户对应一个工作台并对应一个管理器，管理器可以同时存在多个窗口，但同一刻只能显示一
 * 个窗口，叫顶层窗口。
 * 窗口通过堆栈布局（StackLayout）的后进先出方式实现窗口显示隐藏，同一时刻只有栈顶元素可以显示。
 * 此窗口实例保存于用户的UISession中，CmtyWindow的类名为KEY保存在UISession中。
 * @author T440
 *
 */
public class CmtyWindow {

	public static final String WINDOW_ID = CoreUIActivator.PLUGIN_ID + "/"
	                                         + CmtyWindow.class.getName();
	
	public static final String CMTY_PANEL_TOPIC="net/techgy/ui/core/cmty/panel/";
	
	public static final String CMTY_EVENT_TYPE_ACTIVE=CMTY_PANEL_TOPIC+"active";
	
	public static final String CMTY_EVENT_TYPE_DEACTIVE=CMTY_PANEL_TOPIC+"deactive";
	
	public static final String CMTY_PANEL_ID="panelId";
	
	public static final String CMTY_DEFAULT_PANEL_ID="defaultPanelId";
	
	private static CmtyWindow cmtyWindow;
	//默认的窗口
	private String defaultPanelId = WorkbenchWindow.WORKBENCH_WINDOW_ID;
	
	private StackLayout sl = null;
	
	private Map<String,Control> controls = new HashMap<String,Control>();
	
	private Stack<Control> historyContrls = new Stack<Control>();
	
	//所有显示内容的主容器
	private Composite mainContainer = null;
	
	private Composite parent;
	
	private Control defaultPanel = null;
	
	private IEclipseContext context;
	
	@Inject
	private CmtyServiceManaer sm;
	
	public CmtyWindow(Composite parent,IEclipseContext context,MTrimmedWindow window) {
		cmtyWindow = this;
		this.context = context;
		this.parent = parent;
		//RWT.getUISession().setAttribute(CmtyWindow.class.getName(), this);
		this.createContents();
	}

    private void createContents() {
		
		this.mainContainer = new Composite(parent, SWT.BORDER);
		GridData gd = new GridData(SWT.FILL,SWT.FILL,true,true);
		this.mainContainer.setLayoutData(gd);
		sl = new StackLayout();
		this.mainContainer.setLayout(sl);
		//trigger to show the global tool item or main menu
		sendEvent(WINDOW_ID,CMTY_EVENT_TYPE_ACTIVE);
		this.showPanel(defaultPanelId);
	}

    public void destroyComposite() {
    	if(mainContainer != null && !mainContainer.isDisposed()) {
    		mainContainer.dispose();
    	}
    	//RWT.getUISession().removeAttribute(CmtyWindow.class.getName());
	}	
    
    /**
     * 默认窗口
     * @return
     */
    private Control getDefaultComposite() {
    	if(defaultPanelId != null) {
    		IPanelProvider panelProvider = sm.getPanelProvider(defaultPanelId);
    		this.defaultPanel = panelProvider.createControl(mainContainer,this);
    	} else {
    		Composite defaultPanel = new Composite(mainContainer,SWT.NONE);
    		defaultPanel.setLayout(new FillLayout());
    		Label l = new Label(defaultPanel,SWT.NONE);
    		l.setText("No Content");
    		this.defaultPanel = defaultPanel;
    	}
    	defaultPanel.setData(CMTY_PANEL_ID, defaultPanelId);
    	return this.defaultPanel;
    }
	
    /**
     * 根据窗口ID显示窗口
     * @param comId
     */
	public void showPanel(String comId) {
		if(comId == null) {
			return;
		}
		
		Control control = controls.get(comId);
		
		if(control == null) {
			IPanelProvider panelProvider =sm.getPanelProvider(comId);
			if(panelProvider != null) {
				control = panelProvider.createControl(mainContainer,this);
				control.setData(CMTY_PANEL_ID, comId);
				controls.put(comId, control);
			}
			
			if(control == null) {
				return;
			} 
		}
		
		if(sl.topControl != null) {
			String preid = (String)sl.topControl.getData(CMTY_PANEL_ID);
			sendEvent(preid,CMTY_EVENT_TYPE_DEACTIVE);
			if(historyContrls.contains(sl.topControl)) {
				historyContrls.remove(sl.topControl);
			}
			historyContrls.push(sl.topControl);
		}
		
		sl.topControl = control;
		this.mainContainer.layout(true);
		//发送Panel激活事件
		sendEvent(comId,CMTY_EVENT_TYPE_ACTIVE);
		
	}
	
	/**
	 * 发送异步事件，宣布指定的窗口已经显示，激活相关的菜单或工具条等
	 */
	private void postEvent(String panelId,String topic){
		Map<String,Object> params = new HashMap<String,Object>();
		//params.put(CmtyEventAdmin.CMTY_EVENT_TYPE, eventType);
		params.put(CMTY_PANEL_ID, panelId);
		//String topic = UIUtils.getInstance().getAccountTopic(CMTY_PANEL_TOPIC);
		Event event = new Event(topic,params);
		CmtyEventAdmin.getEventBus().postEvent(event);
	}
	
	/**
	 * 发送同步事件，宣布指定的窗口已经显示，激活相关的菜单或工具条等
	 */
	private void sendEvent(String panelId,String topic){
		Map<String,Object> params = new HashMap<String,Object>();
		//params.put(CmtyEventAdmin.CMTY_EVENT_TYPE, eventType);
		params.put(CMTY_PANEL_ID, panelId);
		//String topic = UIUtils.getInstance().getAccountTopic(CMTY_PANEL_TOPIC);
		Event event = new Event(topic,params);
		CmtyEventAdmin.getEventBus().sendEvent(event);
	}
	
	/**
	 * 钝化指定的窗口，也就是窗口从顶层显示窗口移到底层，不再在顶层显示。
	 * 显示当前栈顶的下一个窗口，如没不存在，则显示默认窗口。
	 * @param comId 要钝化的窗口ID
	 * @param remove是否从历史记录中删除此窗口实例
	 */
	public void deactive(String comId,boolean remove) {
		if(comId == null) {
			return;
		}
		Control control = controls.get(comId);
		if(control == null) {
			return;
		}
		
		Control preControl = null;
		
		if(this.historyContrls.empty()) {
			//不再有历史窗口，显示默认窗口
			preControl = this.getDefaultComposite();
		}else {
			//从栈顶取一个窗口显示
			preControl = this.historyContrls.pop();
		}
		
		String pid = (String)preControl.getData(CMTY_PANEL_ID);
		this.showPanel(pid);
		
		if(remove) {
			this.controls.remove(comId);
			this.historyContrls.remove(control);
			control.dispose();
		}
	}
	
	public void deactive(Control control,boolean remove) {
		String id = null;//WidgetUtil.getId(control);
		if(id == null) {
			return;
		}
		Control existControl = controls.get(id);
		if(existControl == null) {
			controls.put(id, control);
		}
		deactive(id,remove);
	}
	
	public boolean isShowPanel(String panelId) {
		Control panel = controls.get(panelId);
		return sl.topControl != null && panel == sl.topControl;
	}
	
	/**
	 * 显示指定的窗口，如地图，视图和编辑器的容器窗口，首选项窗口等
	 * @param panelId
	 * @return
	 */
	public static boolean showPane(String panelId) {
		CmtyWindow window = getCmtyWindow();
		if(null != window) {
			window.showPanel(panelId);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 隐藏窗口
	 * @param panelId
	 * @return
	 */
	public static boolean hidePane(String panelId) {
		CmtyWindow window = getCmtyWindow();
		if(null != window) {
			window.deactive(panelId, false);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断窗口是否是当前可显示的窗口
	 * @param panelId
	 * @return
	 */
	public static boolean isShow(String panelId) {
		CmtyWindow window = getCmtyWindow();
		return window.isShowPanel(panelId);
	}
	
	/**
	 *切换窗口的显示状态，如果是当前显示的窗口，则隐藏，否则显示
	 * @param panelId
	 * @return
	 */
	public static boolean exchange(String panelId) {
		boolean isShow = CmtyWindow.isShow(panelId);
		if(isShow) {
		     return CmtyWindow.hidePane(panelId);
		}else {
			return CmtyWindow.showPane(panelId);
		}
	}
	
	/**
	 * @return
	 */
	public static CmtyWindow getCmtyWindow() {
		return cmtyWindow;
	}
	
	public static boolean checkLogin() {
	   return WSConn.ins().isLoginClient();
	}
	
	public static Shell getStaticShell() {
		return cmtyWindow.getShell();
	}
	
	 
	 /*ToolItem openUrl = new ToolItem(toolBar, SWT.PUSH);
	    openUrl.setText("Open URL");
	    Image icon = ImageUtils.getInstance().getImage(
	    		CmtyEntryPoint.class.getResourceAsStream("/img/ti01.png"), 24, 24);
	    //Image icon = new Image(shell.getDisplay(), "icons/new.gif");
	    openUrl.setImage(icon);
	    openUrl.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				UrlLauncher urlLauncher = RWT.getClient().getService(UrlLauncher.class);
				urlLauncher.openURL("http://www.google.com");
			}
      });
	    
	    ToolItem exejS = new ToolItem(toolBar, SWT.PUSH);
	    exejS.setText("ExeJS");
	    Image js = ImageUtils.getInstance().getImage(
	    		CmtyEntryPoint.class.getResourceAsStream("/img/ti02.png"), 24, 24);
	    exejS.setImage(js);
	    exejS.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				JavaScriptExecutor jse = RWT.getClient().getService(JavaScriptExecutor.class);
				jse.execute("alert('Hello User')");
			}
      });
	    
	    ToolItem browserNavigator = new ToolItem(toolBar, SWT.PUSH);
	    browserNavigator.setText("Navigation");
	    Image browserNavigatorImage = ImageUtils.getInstance().getImage(
	    		CmtyEntryPoint.class.getResourceAsStream("/img/ti03.png"), 24, 24);
	    browserNavigator.setImage(browserNavigatorImage);
	    browserNavigator.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				BrowserNavigation service = RWT.getClient().getService(BrowserNavigation.class);
				service.addBrowserNavigationListener(new BrowserNavigationListener(){
					@Override
					public void navigated(BrowserNavigationEvent event) {
						MessageDialog.openInformation(shell, "Notice",
								"You navigate to : " + event.getSource());
					}
					
				});
				service.pushState("Test", "Test State Service");
			}
      });  
	    
	    ToolItem clientInfoItem = new ToolItem(toolBar, SWT.PUSH);
	    clientInfoItem.setText("Client Info");
	    Image clientInfoItemImage = ImageUtils.getInstance().getImage(
	    		CmtyEntryPoint.class.getResourceAsStream("/img/ti04.png"), 24, 24);
	    clientInfoItem.setImage(clientInfoItemImage);
	    clientInfoItem.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				ClientInfo service = RWT.getClient().getService(ClientInfo.class);
				service.getLocale();
				MessageDialog.openInformation(shell, "Notice",
						"Your current browser locale is : " + service.getLocale());
			}
      });
	    
	    final ToolItem itemDropDown = new ToolItem(toolBar, SWT.PUSH);
	    itemDropDown.setText("Test");
	    itemDropDown.setToolTipText("Click here to test");
	    Image itemDropDownImage = ImageUtils.getInstance().getImage(
	    		CmtyEntryPoint.class.getResourceAsStream("/img/ti05.png"), 24, 24);
	    itemDropDown.setImage(itemDropDownImage);
	  
	    itemDropDown.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				ServerAlert service = RWT.getClient().getService(ServerAlert.class);
				service.alert("Hello Client Service Test");
				
				
			}
      });*/
	
	public IEclipseContext getContext(){
		return this.context;
	}
	
	public Shell getShell() {
		if(this.parent == null) {
			throw new CommonException("SystemError");
		}
		return this.parent.getShell();
	}
	
	public Rectangle getBound() {
		return this.mainContainer.getBounds();
	}
	
}
