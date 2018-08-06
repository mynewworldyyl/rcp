package net.techgy.cmty.ui.preference.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import net.cmty.ui.core.i18n.I18NUtils;
import net.techgy.cmty.ui.preference.IPreferencePage;
import net.techgy.cmty.ui.preference.PreferencePagePanel;
import net.techgy.cmty.ui.preference.internal.PreferenceWindow.ModelChangedListener;
import net.techgy.ui.core.content.CmtyWindow;

@SuppressWarnings("serial")
public class PreferencePageContainer extends Composite implements ModelChangedListener{

	private Composite mainContainer;
	
	private Composite controlBar ;
	
	private StackLayout sl;
	
	private Map<String,PreferencePagePanel> pages = new HashMap<String,PreferencePagePanel>();
	
	@Inject
	private PreferencePageManager pp;
	
	public PreferencePageContainer(Composite parent,int style) {
		super(parent,style);
		//parent.setLayout(new FillLayout());
		this.setLayout(new FillLayout());
		createContent();
	}
	
	public void createContent() {
		Composite com = new Composite(this,SWT.NONE);
		com.setLayout(new FormLayout());
		
		mainContainer = new Composite(com,SWT.BORDER);
		
		controlBar = new Composite(com,SWT.BORDER);
		
		FormData formData = new FormData();
		formData.top = new FormAttachment(0,100,0);
		formData.left = new FormAttachment(0,100,0);
		formData.right = new FormAttachment(100,100,0);
		formData.bottom =new FormAttachment(controlBar, 0, SWT.TOP);
		mainContainer.setLayoutData(formData);
		
		formData = new FormData();
		formData.height = 40;
		formData.left = new FormAttachment(0,100,0);
		formData.right = new FormAttachment(100,100,0);
		formData.bottom =new FormAttachment(100,100,0);
		controlBar.setLayoutData(formData);
		
		createToolBar(controlBar);
		
		//createContent(contentBar);
		
		sl = new StackLayout();
		this.mainContainer.setLayout(sl);
	}
	
	private PreferencePagePanel currentPage() {
		if(sl.topControl instanceof PreferencePagePanel) {
			return (PreferencePagePanel)sl.topControl;
		}
		return null;
	}

	public void showPanel(String modelId) {
		if(modelId == null) {
			return;
		}
		
		PreferencePagePanel panel = pages.get(modelId);
		
		if(panel == null) {
			IPreferencePage page = pp.getPage(modelId);
			if(page == null) {
				return;
			} 
			panel = page.createPage(this.mainContainer );
			panel.createContent();
			pages.put(modelId, panel);
		}
		
		sl.topControl = panel;
		this.mainContainer.layout(true);
		
	}

	@Override
	public void selectionChange(Entry<String, String> model) {
		showPanel(model.getKey());
	}
	
	
	private void createToolBar(Composite contentBar) {
		contentBar.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Button restoreBtn  = new Button(contentBar, SWT.PUSH);
		restoreBtn.setText(I18NUtils.getInstance().getString("Restore"));
		restoreBtn.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				PreferencePagePanel page = currentPage();
				if(page != null) {
					page.restore();
				}
			}			
		});
		
		Button applyBtn  = new Button(contentBar, SWT.PUSH);
		applyBtn.setText(I18NUtils.getInstance().getString("Apply"));
		applyBtn.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				PreferencePagePanel page = currentPage();
				if(page != null) {
					page.apply();
				}
			}
			
		});
		
		Button cancelBtn  = new Button(contentBar, SWT.PUSH);
		cancelBtn.setText(I18NUtils.getInstance().getString("Cancel"));
		cancelBtn.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				PreferencePagePanel page = currentPage();
				if(page != null) {
					page.cancel();
				}
				CmtyWindow.hidePane(PreferenceWindow.PREFERENCE_WINDOW_ID);
			}
			
		});
		
		Button okBtn  = new Button(contentBar, SWT.PUSH);
		okBtn.setText(I18NUtils.getInstance().getString("OK"));
		okBtn.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				PreferencePagePanel page = currentPage();
				if(page != null) {
					page.ok();
				}
				CmtyWindow.hidePane(PreferenceWindow.PREFERENCE_WINDOW_ID);
			}
		});
	}
}
