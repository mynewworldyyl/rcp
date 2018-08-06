package net.cmty.ui.core.view;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.digitnexus.base.excep.CommonException;

import net.cmty.ui.core.i18n.I18NUtils;
import net.techgy.ui.core.CmtyServiceManaer;
import net.techgy.ui.core.content.CmtyWindow;

@SuppressWarnings("serial")
public class ViewPartContainer extends Composite{

	private static final String VIEW_ID="__View_ID";
	
	//private CmtyWindow cmtyWindow;
	
	private CTabFolder folder;
	
	private CmtyServiceManaer sm;
	
	private Map<String,Control> views = new HashMap<String,Control>();
	
	public ViewPartContainer(Composite parent,int style,CmtyWindow cmtyWindow) {
		super(parent,style);
		//this.cmtyWindow = cmtyWindow;
		createContent();
	}
	
	public void showView(String viewId) {
		Control view = this.views.get(viewId);
		if(view == null) {
			IViewProvider p = sm.getViewProvider(viewId);
			if(p == null) {
				throw new CommonException("ViewNotFound", viewId);
			}
			view = p.createControl(folder,SWT.NONE);
			this.views.put(viewId, view);
			createViewItem(viewId,p.getTitle());
		}
		selectView(viewId);
	}

	private void selectView(String viewId) {
		CTabItem[] items = folder.getItems();
		if(items == null || items.length == 0) {
			throw new CommonException("ViewNotFound", viewId);
		}
		for(CTabItem i : items) {
			String vid = (String)i.getData(VIEW_ID);
			if(vid.equals(viewId)) {
				folder.setSelection(i);
				break;
			}
		}
	}

	private void createContent() {
		this.setLayout(new FillLayout());
		folder = new CTabFolder( this, getStyle() );
	    //folder.setLayoutData( new GridData( 300, 300 ) );
	    folder.setSelection( 0 );
	    folder.addSelectionListener( new SelectionAdapter() {
	      @Override
	      public void widgetSelected(SelectionEvent event ) {
	        CTabItem item = ( CTabItem )event.item;
	        
	      }
	    });
	}
	
	private void createViewItem(String viewId,String title) {
		Control view = this.views.get(viewId);
		title = I18NUtils.getInstance().getString(title);
		CTabItem item = new CTabItem(folder, SWT.CLOSE);
		item.setText(title);
		item.setControl(view);
		item.setData(VIEW_ID, viewId);
		/*item.addListener(SWT.CLOSE, new Listener(){
			@Override
			public void handleEvent(Event event) {
				views.remove(viewId);
			}
		});*/
		item.addDisposeListener(new DisposeListener(){
			@Override
			public void widgetDisposed(DisposeEvent event) {
				views.remove(viewId);
			}
		});
	}
	
}
