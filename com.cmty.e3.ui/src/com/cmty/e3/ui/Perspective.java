package com.cmty.e3.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.cmty.e3.ui.view.MainMenuView;

import net.techgy.cmty.ui.views.MainMenuViewPart;

public class Perspective implements IPerspectiveFactory {

	public static final String ID = Perspective.class.getName();
	
	public void createInitialLayout(IPageLayout layout) {
		//layout.setEditorAreaVisible(true);
		//layout.setFixed(true);
		
		defineActions(layout);
        defineLayout(layout);
        
	}

	public void defineActions(IPageLayout layout) {
        // Add "new wizards".
       /* layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");//$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");//$NON-NLS-1$
       */
       
		
		// Add "show views".
       layout.addShowViewShortcut(IPageLayout.ID_PROJECT_EXPLORER);
        layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
        layout.addShowViewShortcut(IPageLayout.ID_BOOKMARKS);
        layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
        layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
        layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
        layout.addShowViewShortcut(IPageLayout.ID_PROGRESS_VIEW);
        layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);

        layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);

		/*layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
		layout.addActionSet(JavaUI.ID_ACTION_SET);
		layout.addActionSet(JavaUI.ID_ELEMENT_CREATION_ACTION_SET);
		layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);*/
    }
	
	 public void defineLayout(IPageLayout layout) {
		 
	        String editorArea = layout.getEditorArea();
	        
	        layout.setEditorAreaVisible(true);
	        
			IFolderLayout topLeft = layout.createFolder(
	                "topLeft", IPageLayout.LEFT, 0.2f,//$NON-NLS-1$
	                editorArea);//$NON-NLS-1$
			
			//topLeft.addView(MainMenuViewPart.ID);		
			topLeft.addPlaceholder(MainMenuViewPart.ID);
			
	        // Top left.
	        /* IFolderLayout right = layout.createFolder(
	                "right", IPageLayout.RIGHT, 0.2f, editorArea);//$NON-NLS-1$
	        */
			//topLeft.addView(IPageLayout.ID_PROJECT_EXPLORER);
		    //layout.getViewLayout(IPageLayout.ID_PROJECT_EXPLORER).setCloseable(false);	    
		    //layout.getViewLayout(IPageLayout.ID_PROJECT_EXPLORER).setMoveable(true);
			
			
			/*IFolderLayout bottomRight = layout.createFolder(
	                "bottomRight", IPageLayout.BOTTOM, 0.72f,//$NON-NLS-1$
	                editorArea);
		
			bottomRight.addView("com.sitech.vasd.editor.server.view.ServersView2");		
			layout.getViewLayout("com.sitech.vasd.editor.server.view.ServersView2").setCloseable(true);
			layout.getViewLayout("com.sitech.vasd.editor.server.view.ServersView2").setMoveable(true);

		    IFolderLayout topRight = layout.createFolder(
	                "topRight", IPageLayout.RIGHT, 0.8f,//$NON-NLS-1$
	                editorArea);//$NON-NLS-1$
*/		    
		    /*topRight.addView(HtmlTreeViewExt.ID);				
			layout.getViewLayout(HtmlTreeViewExt.ID).setCloseable(false);
			layout.getViewLayout(HtmlTreeViewExt.ID).setMoveable(true);*/
			
			/*topRight.addView(HttpHeadersView.ID);				
		    layout.getViewLayout(HttpHeadersView.ID).setCloseable(true);
		    layout.getViewLayout(HttpHeadersView.ID).setMoveable(true);

		    topRight.addView(IPageLayout.ID_OUTLINE);*/
		    
			   /* layout.getViewLayout(IPageLayout.ID_OUTLINE).setCloseable(false);
		    layout.getViewLayout(IPageLayout.ID_OUTLINE).setMoveable(true);*/
			
	    }
	
}
