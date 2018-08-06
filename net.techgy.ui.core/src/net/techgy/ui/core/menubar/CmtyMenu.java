package net.techgy.ui.core.menubar;

import java.util.ArrayList;
import java.util.List;

import net.cmty.ui.core.i18n.I18NUtils;
import net.techgy.ui.core.actions.ActionManager;
import net.techgy.ui.core.utils.UIUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.menu.MenuActionDef;

public class CmtyMenu extends Menu{

	private static final String ACT_KEY = "ActionDef";
	
	private List<MenuActionDef> acts = new ArrayList<MenuActionDef>();
	
	private ActionManager am;
	 /**
	  * SWT.DROP_DOWN Menu
	  * @param parent
	  */
	 public CmtyMenu(ActionManager am, Menu menu, List<ActionDef> acts ) {
		 super(menu);
		 this.am = am;
	 }
	 
	 /**
	  * SWT.DROP_DOWN Menu
	  * @param parent
	  */
	 public CmtyMenu(ActionManager am, MenuItem parent , List<ActionDef> acts ) {
		 super(parent);
		 this.am = am;
	 }
	 
	 /**
	  * SWT.POP_UP Menu
	  * @param parent
	  */
	 public CmtyMenu(ActionManager am,  Control parent , List<ActionDef> acts ) {
		 super(parent);
		 this.am = am;
	 }
	 
	 /**
	   * @see SWT#BAR
	   * @see SWT#DROP_DOWN
	   * @see SWT#POP_UP
	   * @see SWT#NO_RADIO_GROUP
	   * @see Widget#checkSubclass
	   * @see Widget#getStyle
	   */
	 public CmtyMenu(ActionManager am, Decorations parent, int style , List<MenuActionDef> acts ) {
		 super(parent,style | SWT.BAR);
		 this.acts = acts;
		 this.am = am;
		 //Menu  menuBar = new Menu(parent, SWT.BAR);
		 if(acts == null || acts.isEmpty()) {
		     return;
		 }
		 
		 for(MenuActionDef ad : acts) {
			    MenuItem topMenuItem = new MenuItem(this, SWT.CASCADE);
			    topMenuItem.setText(ad.getName());
			    initMenu(ad,topMenuItem);
			}
	 }
	 
	 private void initMenu(MenuActionDef ad, MenuItem topMenuItem) {
		 if(ad.getSubActions() == null || ad.getSubActions().isEmpty()) {
		    	topMenuItem.setData(ACT_KEY,ad);
		    	topMenuItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						try {
							ActionDef itemDef = (ActionDef)((MenuItem)e.getSource()).getData(ACT_KEY);
							am.executeAction(itemDef,null);
						} catch (CommonException e1) {
							UIUtils.getInstance().showNodeDialog(CmtyMenu.this.getShell(),
									I18NUtils.getInstance().getString(e1.getMessage()));
						}
						
					}
		         });
		    return;
		 }

    	 Menu subMenu = new Menu(topMenuItem);
		 topMenuItem.setMenu(subMenu);
		    
		 for(MenuActionDef subAct : ad.getSubActions()) {
			 int style = SWT.PUSH;
			 if(subAct.getSubActions() != null && !subAct.getSubActions().isEmpty()) {
				 style = SWT.CASCADE;
			 }
			 MenuItem mi = new MenuItem(subMenu, style);
			 mi.setText(subAct.getName());
			 initMenu(subAct,mi);
		 }
    
	 }
}
