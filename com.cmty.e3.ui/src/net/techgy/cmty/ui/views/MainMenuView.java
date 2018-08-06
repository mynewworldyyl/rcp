package net.techgy.cmty.ui.views;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.cmty.e3.ui.editor.CommonVoEditorInput;
import com.digitnexus.base.uidef.TableViewerEditorDef;
import com.digitnexus.base.uidef.menu.Menu;
import com.digitnexus.base.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import net.cmty.ui.core.editor.BaseDefEditorProvier;
import net.cmty.ui.core.workbench.WorkbenchSelectionManager;
import net.techgy.cmty.service.DataDto;
import net.techgy.cmty.ui.i18n.I18NUtils;
import net.techgy.ui.core.network.WSConn;
import net.techgy.ui.core.utils.UIUtils;

public class MainMenuView extends Composite implements IDoubleClickListener{
	
	private TreeViewer viewer;
	
	@Inject
	private IEclipseContext context;
	
	@Inject
	private WorkbenchSelectionManager selectionManager;
	
	@Inject
	private EModelService modelService;
	
	@Inject
	private EPartService partService;
	
	public MainMenuView(Composite parent,int style,IEclipseContext context){
		super(parent,style);
		this.setLayout(new FillLayout());
		this.context = context;
		this.partService = context.get(EPartService.class);
		this.selectionManager = context.get(WorkbenchSelectionManager.class);
		this.modelService = context.get(EModelService.class);
		createContent();
	}

	@PostConstruct
	private void createContent() {
		
		viewer = new TreeViewer( this, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION );
	    viewer.setContentProvider( new TreeViewerContentProvider() );
	    //ILabelDecorator labelDecorator = PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator();
	    ILabelProvider labelProvider =  new ViewLabelProvider() ;
	    viewer.setLabelProvider( labelProvider );
	    viewer.addDoubleClickListener( this );
	   
	    //getSite().setSelectionProvider( viewer );
	   /* WorkbenchSelectionManager selectionManager = 
				SingletonUtil.getSessionInstance(WorkbenchSelectionManager.class);*/
	    //WorkbenchSelectionManager selectionManager = WorkbenchSelectionManager.getInstance();
	    viewer.addSelectionChangedListener(selectionManager);
	    viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				if(selection != null && selection.getFirstElement() instanceof Menu) {
					selectionChange((Menu)selection.getFirstElement());
				}
			}
	    });
	    initData();
		
	}
	
	private void selectionChange(Menu menu) {
		  if(menu.getSubMenus() == null || menu.getSubMenus().isEmpty()) {
			 //WorkbenchWindow workbench = (WorkbenchWindow)RWT.getUISession().getAttribute(WorkbenchWindow.WORKBENCH_WINDOW_ID);
			 //String editorId = menu.getEditorId();
			 if(menu.getEditorId() == null) {
				 menu.setEditorId(BaseDefEditorProvier.COMMON_EDITOR_ID);
			 }
			 //EPartService partService = context.get(EPartService.class);
			 //MApplication appService = context.get(MApplication.class);
			 //EModelService modelService = context.get(EModelService.class);
			 
			 //createPart(menu);
			 
			 openOrActiveEditor(menu);
			 			 
			//EPartService partService = context.get(EPartService.class);
			//MPart mainMenuPart = partService.createPart(MainMenuViewPart.class.getName());
			//MPart p = partService.showPart(CmtyServiceManaer.class.getName(), PartState.CREATE);
			//p.setVisible(true);
				
			/* WorkbenchWindow workbench = (WorkbenchWindow)CmtyWindow.getCmtyWindow()
			    		.getContext().get(WorkbenchWindow.WORKBENCH_WINDOW_ID);
			 if(null != workbench) {
				workbench.getEditorSite().openEditor(editorId,new BaseDefEditorInput(menu));
			}*/
		  }
	}
	
	private void openOrActiveEditor(Menu menu) {
		CommonVoEditorInput input = new CommonVoEditorInput(menu);
		IWorkbenchPage page =  PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage();
		IEditorPart editor = page.findEditor(input);
		if(editor == null) {
			try {
				editor = page.openEditor(input, menu.getEditorId());
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		} else {
			page.activate(editor);
		}
	}

	public MPart createPart(Menu menu) {
		
		MPart part = null;
		for(MPart p : partService.getParts()) {
			if(p.getElementId().equals(menu.getObjType())) {
				part = p;
			}
		}
		
		if(part == null) {
			part = MBasicFactory.INSTANCE.createPart();
			part.setLabel(menu.getName());
			part.setContributionURI("platform:/plugin/net.techgy.ui/net.techgy.cmty.ui.editors.CommonEditorPart");
			part.setElementId(menu.getObjType());
			part.setVisible(true);
			part.setCloseable(true);
			part.setToBeRendered(true);
			MPartStack ps = (MPartStack)modelService.find("net.techgy.ui.editor", this.context.get(MApplication.class));
			ps.getChildren().add(part);
		}
		
		 //List<MPartStack> stacks = modelService.findElements(app, null,MPartStack.class, null);
		 //MPartStack ps = modelService.find(app,"", app, MPartStack.class,null,0);
	
		 partService.showPart(part, PartState.ACTIVATE);
		 return part;
	}
	
	 public void doubleClick( DoubleClickEvent event ) {
		    String msg = "You doubleclicked on " + event.getSelection().toString();
		    Shell shell = viewer.getTree().getShell();
		    MessageDialog.openInformation( shell, "Treeviewer", msg );
	}
	 
	 public void initData() {
		    DataDto resp = WSConn.ins().call("menuManager", "mainMenus");
		    List<Menu> lmenus  = null;
		    if(resp.isSuccess()) {
				TableViewerEditorDef def = null;
				if(resp.getObj() != null) {
					lmenus = (List<Menu>)resp.getObj();
				}else {
					Type type = new TypeToken<List<Menu>>(){}.getType();
					lmenus = JsonUtils.getInstance().fromJson(resp.getData(), type, false, true);
				}
			}else {
				UIUtils.getInstance().showNodeDialog(viewer.getTree().getShell(),
						I18NUtils.getInstance().getString(resp.getMsg()));
				return;
			}
			for(Menu menu : lmenus) {
				for(Menu m : menu.getSubMenus()) {
					
					m.setParent(menu);
				}
			}
		    viewer.setInput( lmenus );
	  }
}
