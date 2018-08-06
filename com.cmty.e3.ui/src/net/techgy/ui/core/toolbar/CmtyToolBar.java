package net.techgy.ui.core.toolbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.osgi.service.event.EventHandler;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.ActionDef;

import net.cmty.ui.core.i18n.I18NUtils;
import net.techgy.ui.core.CmtyServiceManaer;
import net.techgy.ui.core.actions.ActionManager;
import net.techgy.ui.core.content.CmtyWindow;
import net.techgy.ui.core.utils.UIUtils;


/**
 * 显示于窗口上部的工具栏，工具栏按钮随视图（VIEW）及编辑器（EDITOR）的变化需变化。
 * 工具栏监听指定的topic，如果有新的Panel激活，则依PanelID查找工具项定义，并添加到
 * 工具栏中；如果Panel关闭，则删除与其相关的工具按钮。
 * 工具栏中可显示的数量有限，如果超过最大值，则在最后一个按钮显示为下拉菜单。
 * @author T440
 *
 */
@SuppressWarnings("serial")
public class CmtyToolBar extends ToolBar{

	private static final String ACT_KEY = "ActionDef";
	private static final String DROP_DOWN_MENU_KEY = "MenuKey";
	
	//private List<ActionDef> acts = new ArrayList<ActionDef>();
	//Action执行上下文，目前没有值
	private Map<String,Object> context = new HashMap<String,Object>();
	
	//最多显示6个顶级菜单项目，多余的下拉列表显示
	private int dispItemNum = 6;
	
	@Inject
	private CmtyServiceManaer sm;
	
	@Inject
	private IEventBroker eventBroker;
	
	@Inject
	private ActionManager am;
	
	//private String topic = null;
	
	//private ServiceRegistration<EventHandler> activeReg;
	//private ServiceRegistration<EventHandler> deactiveReg;
	
	//监听PanelId事件
	private EventHandler activeEventListener = new EventHandler(){
		@Override
		public void handleEvent(org.osgi.service.event.Event event) {
			getDisplay().syncExec(new Runnable(){
				public void run() {
					handlerActivePanelEvent(event);
				}
			});
		}		
	};
	
	
	private EventHandler deactiveEventListener = new EventHandler(){
		@Override
		public void handleEvent(org.osgi.service.event.Event event) {
			getDisplay().syncExec(new Runnable(){
				public void run() {
					handlerDeactivePanelEvent(event);
				}
			});
		}		
	};
	
	public CmtyToolBar(Composite parent,int style,Map<String,Object> context) {
		super(parent,style);
		//this.topic = topic;
		//doActive(CmtyWindow.WINDOW_ID);
		eventBroker.subscribe(CmtyWindow.CMTY_EVENT_TYPE_ACTIVE, activeEventListener);
		eventBroker.subscribe(CmtyWindow.CMTY_EVENT_TYPE_DEACTIVE, deactiveEventListener);
		//activeReg = CmtyEventAdmin.registerEventHandler(CmtyWindow.CMTY_EVENT_TYPE_ACTIVE, activeEventListener);
		//deactiveReg = CmtyEventAdmin.registerEventHandler(CmtyWindow.CMTY_EVENT_TYPE_DEACTIVE, deactiveEventListener);
	}
	
	
	private void handlerActivePanelEvent(org.osgi.service.event.Event event) {
		if(this.isDisposed()) {
			return;
		}
		
		if(!event.containsProperty(CmtyWindow.CMTY_PANEL_ID)) {
			return;
		}
		
		String panelId = (String)event.getProperty(CmtyWindow.CMTY_PANEL_ID);
		doActive(panelId);	
	}

	private void handlerDeactivePanelEvent(org.osgi.service.event.Event event) {
		if(this.isDisposed()) {
			return;
		}
		
		if(!event.containsProperty(CmtyWindow.CMTY_PANEL_ID)) {
			return;
		}
		
		String panelId = (String)event.getProperty(CmtyWindow.CMTY_PANEL_ID);
		doDeactive(panelId);	
	}
	
	private void doDeactive(String panelId) {
		ToolItem[] tis = this.getItems();
		if(tis == null || tis.length == 0) {
			return;
		}
		
		for(ToolItem ti : tis) {
			ActionDef itemDef = (ActionDef)ti.getData(ACT_KEY);
			if(itemDef !=null && itemDef.getUrl().equals(panelId)) {
				ti.dispose();
			}else if(itemDef == null  && (ti.getStyle() & SWT.DROP_DOWN) != 0) {
				Menu m = (Menu) ti.getData(DROP_DOWN_MENU_KEY);
				if(m == null) {
					continue;
				}
				MenuItem[] mis = m.getItems();
				if(mis == null || mis.length == 0) {
					continue;
				}
				for(MenuItem mi : mis) {
					ActionDef idef = (ActionDef)mi.getData(ACT_KEY);
					if(idef.getUrl().equals(panelId)) {
						mi.dispose();
					}
				}
			}
		}
		this.layout(true);
	}

	private void doActive(String panelId) {
		
		//激活的panel对应的菜单定义
		List<ActionDef> acts = sm.getToolActionDefs(panelId);
		
		if(acts == null || acts.isEmpty()) {
			//没有菜单定义
			return;
		}
		
		//用于显示下拉菜单的按钮，菜单显示一行，一行最多显示6个菜单，超过6个菜单显示为下拉菜单
		ToolItem dropDownItem = null;
		int count = 0;
		if(this.getItemCount() < this.dispItemNum) {
			//当前显示的总菜单项数小于可显示的最大数量
			//还可以显示的菜单项数量
			count = this.dispItemNum - this.getItemCount()-1;
			if(count >= acts.size()) {
				//可以显示的数量大于当前需要显示的数量，直接显示为菜单
				int c = acts.size() > count? count: acts.size();
				for(int index = 0; index < c ; index++) {
					ActionDef ad = acts.get(index);
					oneToolItem(ad);
				}
				return;
			} else {
				//需要显示的菜单数量大于可显示的数量，那么最后一个菜单项用于激活下拉菜单
				if(count > 0) {
					for(int index = 0; index < count ; index++) {
						ActionDef ad = acts.get(index);
						oneToolItem(ad);
					}
				} 
				//下拉菜单激活按钮
			    final ToolItem ddi = new ToolItem(this, SWT.DROP_DOWN);  
			    ddi.setText(I18NUtils.getInstance().getString("Others"));
		        Menu menu = new Menu(this.getShell(), SWT.POP_UP);
		        ddi.setData(DROP_DOWN_MENU_KEY, menu);
		        ddi.addListener(SWT.Selection, new Listener() {  
		            public void handleEvent(Event event) {  
		                if (event.detail == SWT.ARROW) {  
		                	//先了下拉箭头，弹出下拉菜单
		                    Rectangle bounds = ddi.getBounds();  
		                    Point point = CmtyToolBar.this.toDisplay(bounds.x, bounds.y + bounds.height);  
		                    Menu m = (Menu) ddi.getData(DROP_DOWN_MENU_KEY);
		                    m.setLocation(point);  
		                    m.setVisible(true);  
		                }  
		            }  
		        });
		        dropDownItem = ddi;
			}
		} else {
			//直接显示为下拉菜单
			dropDownItem = this.getItem(this.getItemCount()-1);
		}
		
		 Menu menu = (Menu) dropDownItem.getData(DROP_DOWN_MENU_KEY);
		 
		 for(int index = count; index < acts.size() ; index++) {
	        	ActionDef ad = acts.get(index);
	        	oneDropdownMenuItem(ad,menu);
		}
		 this.layout(true);
	}
	
	private void oneDropdownMenuItem(ActionDef ad,Menu menu) {
		MenuItem mi = new MenuItem(menu, SWT.PUSH);
		mi.setText(ad.getName());
		//item.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
		//item.setText("<a style='cursor:hand'>" + ad.getName()+"</a>");
	  /*  Image icon = ImageUtils.getInstance().getImage(
	    		CmtyEntryPoint.class.getResourceAsStream("/img/ti01.png"), 24, 24);
	    		 openUrl.setImage(icon);
	    		*/
	    //Image icon = new Image(shell.getDisplay(), "icons/new.gif");
		mi.setData(ACT_KEY, ad);
		//mi.setData(CmtyWindow.CMTY_PANEL_ID, panelId);
		mi.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				ActionDef itemDef = (ActionDef)((Item)e.getSource()).getData(ACT_KEY);
				try {
					am.executeAction(itemDef,context);
				} catch (CommonException e1) {
					UIUtils.getInstance().showNodeDialog(menu.getShell(),
							I18NUtils.getInstance().getString(e1.getMessage()));
				}
			}
         });
	}
	

	private ToolItem oneToolItem(ActionDef ad) {
		ToolItem item = new ToolItem(this, SWT.PUSH);
		//item.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
		//item.setText("<a style='cursor:hand'>" + ad.getName()+"</a>");
		item.setText(I18NUtils.getInstance().getString(ad.getName()) );
	  /*  Image icon = ImageUtils.getInstance().getImage(
	    		CmtyEntryPoint.class.getResourceAsStream("/img/ti01.png"), 24, 24);
	    		 openUrl.setImage(icon);
	    		*/
	    //Image icon = new Image(shell.getDisplay(), "icons/new.gif");
		item.setData(ACT_KEY, ad);
		//item.setData(CmtyWindow.CMTY_PANEL_ID, panelId);
		item.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					ActionDef itemDef = (ActionDef)((Item)e.getSource()).getData(ACT_KEY);
					am.executeAction(itemDef,context);
				} catch (CommonException e1) {
					UIUtils.getInstance().showNodeDialog(CmtyToolBar.this.getShell(),
							I18NUtils.getInstance().getString(e1.getMessage()));
				}
			}
         });
		return item;
	}

	@Override
	public void dispose() {
		this.eventBroker.unsubscribe(this.activeEventListener);
		this.eventBroker.unsubscribe(this.deactiveEventListener);
		super.dispose();
	}
}
