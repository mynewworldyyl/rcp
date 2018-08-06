package net.techgy.cmty.ui.preference.internal;

import java.util.Map;

import net.techgy.ui.core.CoreUIActivator;
import net.techgy.ui.core.content.CmtyWindow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("serial")
public class PreferenceWindow extends Composite{

	public static final String PREFERENCE_WINDOW_ID = CoreUIActivator.PLUGIN_ID +"/"+ 
	PreferenceWindow.class.getName();
	
	private PreferencePageContainer pageContainer;
	
	private ModelListView modelList;
	
	public PreferenceWindow(Composite parent,int style,CmtyWindow cmtyWindow) {
		super(parent,style);
		this.setLayout(new FillLayout());
		createContent();
	}

	private void createContent() {
		SashForm sashForm = new SashForm(this, SWT.HORIZONTAL);
		
		this.modelList = new ModelListView(sashForm,SWT.NONE);
		this.modelList.setLayout(new FillLayout());
		
		this.pageContainer = new PreferencePageContainer(sashForm,SWT.NONE);
		this.pageContainer.setLayout(new FillLayout());
		
		this.modelList.setListener(pageContainer);
		
		sashForm.setWeights(new int[]{25,75});
		sashForm.setSashWidth(3);
		sashForm.setBackground(this.getDisplay().getSystemColor(SWT.COLOR_GRAY));
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	
	public static interface ModelChangedListener {
		void selectionChange(Map.Entry<String, String> model);
	}
	
}
