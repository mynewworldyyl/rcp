package net.cmty.ui.core.forum;

import java.util.Map;

import net.techgy.ui.core.CoreUIActivator;
import net.techgy.ui.core.content.CmtyWindow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("serial")
public class ForumWindow extends Composite{

	public static final String FORUM_WINDOW_ID = CoreUIActivator.PLUGIN_ID +"/"+ 
	ForumWindow.class.getName();
	
	private ForumPageContainer pageContainer;
	
	private TopicTypeListView modelList;
	
	public ForumWindow(Composite parent,int style,CmtyWindow cmtyWindow) {
		super(parent,style);
		this.setLayout(new FillLayout());
		createContent();
	}

	private void createContent() {
		SashForm sashForm = new SashForm(this, SWT.HORIZONTAL);
		
		this.modelList = new TopicTypeListView(sashForm,SWT.NONE);
		this.modelList.setLayout(new FillLayout());
		
		this.pageContainer = new ForumPageContainer(sashForm,SWT.NONE);
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
