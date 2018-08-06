package net.techgy.ui.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.EventAdmin;

import com.digitnexus.base.event.CmtyEventAdmin;
import com.digitnexus.base.uidef.menu.MenuActionDef;

import net.techgy.ui.core.actions.ActionManager;
import net.techgy.ui.core.content.CmtyWindow;
import net.techgy.ui.core.menubar.CmtyMenu;
import net.techgy.ui.core.menubar.IMenuDefProvider;
import net.techgy.ui.core.statubar.IStatuControlProvider;
import net.techgy.ui.core.toolbar.CmtyToolBar;
import net.techgy.ui.core.utils.ImageUtils;

public class CmtyEntryPoint /*extends AbstractEntryPoint*/ {

	private CmtyWindow cmtyWin;
	
	private CmtyToolBar toolBar;
	
	private Composite statuBar;
		
	private Composite logoBar;
	
	private CmtyMenu menuBar;
	
	@Inject
	private EventAdmin eventAdmin;
	   
	@Inject
	private IEclipseContext context;
	
	@Inject
	private MTrimmedWindow window;
	
	@Inject
	private ActionManager am;
	
	//private ServerPushSession pushSession;
	
	@PostConstruct
	protected void createContents(Composite parent) {
		//this.eventAdmin = eventAdmin;
		init();
		CmtyEventAdmin.setEventBus(eventAdmin);
		cmtyWin = new CmtyWindow(parent,context,window);
		this.context.set(CmtyWindow.WINDOW_ID, cmtyWin);
		//this.createFoot(parent);
	}
	
	/**
	 * 在createShell方法中调用此方法
	 */
    private void init() {
    	//this.context.set(WorkbenchSelectionManager.class.getName(), new WorkbenchSelectionManager());
    	
    	//首面加载前首先加载自定义的JS脚本
    	//UIUtils.getInstance().requireJs(LIB_PATH, INIT_LOAD_RESES, CmtyEntryPoint.class.getClassLoader());
    	//主题ID，以此KEY发送全局事件
    	//RWT.getUISession().setAttribute(CmtyEventAdmin.CMTY_TOPIC_KEY, CmtyEventAdmin.getTopicId());
    	//消息推送通道
    	//pushSession = new ServerPushSession();
    	//pushSession.start();
	}

	protected Shell createShell(Display display,CmtyServiceManaer sm) {
		 /*Thread tr  = new Thread(new Runnable(){
		    	public void run() {
		    		try {
						while(true) {
							Thread.sleep(8*1000);
							Map<String,Object> params = new HashMap<String,Object>();
							params.put("msg", "Helloworld");
							Event event = new Event(TabrisPushSessionEntryPoint.topic,params);
							CmtyEventAdmin.getEventBus().postEvent(event);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		
		    	}
		    });
		   
		   tr.start();*/
		   //页面加载前
		init();
		Shell shell = null;//super.createShell(display);
		
		//RWT.getUISession().setAttribute(UIConstants.SHELL, shell);
		//RWT.getUISession().setAttribute(UIConstants.DISPLAY, display);
		
		GridLayout sgl = new GridLayout(1,false);
		sgl.horizontalSpacing=0;
		sgl.marginHeight=0;
		sgl.marginWidth=0;
		sgl.verticalSpacing=0;
		shell.setLayout(sgl);
		
		//this.createShellMenuBar(shell);
		
		Composite com = new Composite(shell,SWT.NONE);
		GridData gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
		com.setLayoutData(gd);
		GridLayout gl = new GridLayout(2,false);
		gl.horizontalSpacing=0;
		gl.marginHeight=0;
		gl.marginWidth=0;
		gl.verticalSpacing=0;
		com.setLayout(gl);
		
	    //创建工具栏
		this.createToolBar(com);
		//创建状态栏
		this.createStatu(com,sm);
		
		shell.addDisposeListener(new DisposeListener() {
    		@Override
    		public void widgetDisposed(DisposeEvent event) {
    			if(cmtyWin != null) {
    				cmtyWin.destroyComposite();
    				/*if(pushSession != null) {
    					pushSession.stop();
        				pushSession = null;
    				}*/
    			}
    		}
    	});
		return shell;
	}
	
	private void createShellMenuBar(Shell shell,CmtyServiceManaer sm) {
		List<IMenuDefProvider> ps = sm.getMenuDefProvider(
				IMenuDefProvider.class.getName());
		if(ps != null) {
			List<MenuActionDef> mds = new ArrayList<MenuActionDef>();
			for(IMenuDefProvider p : ps) {
				mds.addAll(p.getMenuDefs());
			}
			menuBar = new CmtyMenu(am,shell, SWT.BAR, mds);
			shell.setMenuBar(menuBar);
		}
   	}

  private void createToolBar(Composite com) {
		
		Composite toolContainer = new Composite(com,SWT.NONE);
		GridData gd = new GridData(SWT.BEGINNING,SWT.FILL,false,true);
		toolContainer.setLayoutData(gd);
		toolContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		//toolContainer.setBackground(toolContainer.getDisplay().getSystemColor(SWT.COLOR_BLUE));
		//String topic = UIUtils.getInstance().getAccountTopic(CmtyWindow.CMTY_PANEL_TOPIC);
		
		Map<String,Object> context = new HashMap<String,Object>();
		context.put(CmtyWindow.WINDOW_ID, cmtyWin);
		toolBar = new CmtyToolBar(toolContainer, SWT.FLAT | SWT.WRAP | SWT.RIGHT,context);
	}

	private void createStatu(Composite com,CmtyServiceManaer sm) {
		
		statuBar = new Composite(com,SWT.NONE);
		GridData gd = new GridData(SWT.FILL,SWT.FILL,true,true);
		statuBar.setLayoutData(gd);
		//statuBar.setBackground(statuBar.getDisplay().getSystemColor(SWT.COLOR_DARK_YELLOW));
		
		statuBar.setLayout(new FormLayout());
		
		Collection<IStatuControlProvider> stauProviders = sm.getStatuProvider();
		if(stauProviders != null) {
			for(IStatuControlProvider p : stauProviders) {
				p.createControl(statuBar);
			}
		}
	}

	/**
	 * 创建底部状态栏
	 * @param parent
	 */
	private void createFoot(Composite parent) {
		GridData gd = new GridData(SWT.FILL,SWT.BOTTOM,true,false);
		Composite footContainer = new Composite(parent,SWT.BORDER);
		footContainer.setLayoutData(gd);
		
		footContainer.setLayout(new FillLayout());
		Label cr = new Label(footContainer,SWT.NONE);
		cr.setText("Copyright 2014 My New World. All Rights Reserved.");
		cr.setFont( new Font(footContainer.getDisplay(), "Verdana", 15, SWT.BOLD ));
		cr.setAlignment(SWT.CENTER);
		//logoTitle.pack();		
		footContainer.setBackgroundMode(SWT.INHERIT_DEFAULT);
		footContainer.setBackground(footContainer.getDisplay().getSystemColor( SWT.COLOR_TITLE_BACKGROUND_GRADIENT ));
	
	}
	
	 
	
	
   private int createLogoBar(Composite parent,int width,int height) {
		
		logoBar = new Composite(parent,SWT.BORDER);
		
		Image bg = ImageUtils.getInstance().getImage(
	    		CmtyEntryPoint.class.getResourceAsStream("/img/banner01.png"), 24, 40);
		logoBar.setBackgroundImage(bg);
	    height = bg.getBounds().height;
	    RowData logoRowData = new RowData(width,height);
	    logoBar.setLayoutData(logoRowData);
		
	    logoBar.setLayout( new FormLayout() );
		 
		Label logoTitle = new Label(logoBar, SWT.NONE );
		//logoTitle.setText( "Technoligy Community" );
		logoTitle.setForeground(logoBar.getDisplay().getSystemColor( SWT.COLOR_GRAY ));
		logoTitle.setFont( new Font(logoBar.getDisplay(), "Verdana", 38, SWT.BOLD ));
		//logoTitle.pack();
		logoTitle.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
		logoTitle.setText( "<i><b>Technoligy Community</b></i>" );
		
		
		logoBar.setBackgroundMode(SWT.INHERIT_DEFAULT);
		//logoTitle.setBackground(Display.getCurrent().getSystemColor(SWT.TRANSPARENCY_ALPHA));
	    FormData fdLabel = new FormData();
	    logoTitle.setLayoutData( fdLabel );
	    fdLabel.top = new FormAttachment( 0, 5 );
	    fdLabel.left = new FormAttachment( 0, 10 );
	   
		Composite statusBar = new Composite(logoBar,SWT.NONE);
		RowLayout rl = new RowLayout(SWT.HORIZONTAL);
		statusBar.setLayout(rl);
		//statusBar.setBackgroundMode(SWT.INHERIT_DEFAULT);	
		
		FormData fdStatuBar = new FormData();
		statusBar.setLayoutData( fdStatuBar );
		//fdStatuBar.bottom = new FormAttachment(0,);
		fdStatuBar.left = new FormAttachment(6, 50 );
		fdStatuBar.top = new FormAttachment(70, 10 );
		
	    return height;
	}
   
   ///在项目net.techgy.ui下的RES目录
	public static final String LIB_PATH = "/js/";
   
    private static final String[] INIT_LOAD_RESES = {
		"RTCPeerConnection-v1.6.js",
		"utils.js",
		"File.js",
		"RTCDataChannel.js",
		"base64.js",
		"jquery.min.js",
		"LocalStorage.js",
		"SessionStorage.js"
   };
  
}
