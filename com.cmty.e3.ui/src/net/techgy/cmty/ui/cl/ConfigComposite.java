package net.techgy.cmty.ui.cl;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.digitnexus.ui.treeview.TreeNode;

import net.techgy.cmty.vo.AttributeVo;
import net.techgy.cmty.vo.NamespaceVo;


public class ConfigComposite extends Composite {

    private Composite contentContainer =null;
	
	private StackLayout sl = null;
	
	private TreeViewer attrViewer = null;
	private TreeViewer classViewer = null;
	private TreeViewer instanceViewer = null;
	
	private Map<String,ObjectEditor> editors = new HashMap<String,ObjectEditor>();
	
	private Stack<ObjectEditor> editorStack = new Stack<ObjectEditor>();
	
	//private Object activeItem = null;
	
	private boolean isOpen = false;
	
	private boolean isActive = false;
	
	private ObjectEditor noSelectionEditor = null;
	
	public NamespaceEditor newNSEditor = null;
	
	public AttributeEditor newAtrEditor = null;
	
	public ConfigComposite(Composite parent1, int style) {
		super(parent1,style);

		FillLayout fl = new FillLayout(SWT.HORIZONTAL);
		fl.marginHeight=0;
		fl.marginWidth=0;
		fl.spacing=0;
		this.setLayout(fl);
		//main window container
		int contentMiniHeight=500;
		
		Composite mainContent = new Composite(this,SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		mainContent.setLayout(gridLayout);
		
		//the left content
		int minimumWidth = 250;
		Composite configLisgContainer = new Composite(mainContent,SWT.NONE);
		GridData menuFlGD = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1);
		menuFlGD.minimumWidth = minimumWidth;
		menuFlGD.minimumHeight=contentMiniHeight;
		configLisgContainer.setLayoutData(menuFlGD);	
		GridLayout menuFlLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;		
		configLisgContainer.setLayout(menuFlLayout);
		
		Composite configListContainer = new Composite(configLisgContainer,SWT.NONE);
		GridData flGD = new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1);
		flGD.minimumWidth = minimumWidth;
		flGD.minimumHeight=330;
		configListContainer.setLayoutData(flGD);
		createConfigListContent(configListContainer);
		
		//the right content
		contentContainer = new Composite(mainContent,SWT.BORDER);
		GridData contentGD = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		contentGD.minimumWidth=400;
		contentGD.minimumHeight=contentMiniHeight;	
		contentContainer.setLayoutData(contentGD);
		
		createContent(contentContainer);		
	}
	
	private void createContent(Composite contentContainer) {
		sl = new StackLayout();
	    contentContainer.setLayout(sl);
	    this.noSelectionEditor = new NullSelectionEditor(contentContainer,SWT.NONE,new String("No selection"),this);
	    this.selectEditor(this.noSelectionEditor,true);
	   
	}

	
	private void createConfigListContent(Composite configContainer) {
		FillLayout fl = new FillLayout();
		fl.marginHeight = 3;
		fl.marginWidth = 3;
		configContainer.setLayout(fl);
		
		TabFolder tabFolder = new TabFolder(configContainer, SWT.NONE);
		
		final TabItem attrItem = new TabItem(tabFolder, SWT.NONE);
		createAttrComposite(tabFolder,attrItem);
		
		final TabItem classItem = new TabItem(tabFolder, SWT.NONE);
		createClassComposite(tabFolder,classItem);
		
		final TabItem instanceItem = new TabItem(tabFolder, SWT.NULL);
		createInstanceItem(tabFolder,instanceItem);
		
	}
	
	private void createInstanceItem(TabFolder tabFolder, TabItem instanceItem) {
		instanceItem.setText("Instance");
		Composite composite = new Composite(tabFolder, SWT.BORDER);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		Text text = new Text(composite, SWT.BORDER);
		text.setText("This is page " + 3);
		//text.setBounds(10, 10, 100, 20);
		Label lt = new Label(composite, SWT.NONE);
		lt.setText("Groups List");
		instanceItem.setControl(composite);
	}

	private void createClassComposite(TabFolder tabFolder, TabItem classItem) {
		classItem.setText("Class");
		Composite glComposite = new Composite(tabFolder,SWT.BORDER | SWT.V_SCROLL| SWT.H_SCROLL);
		glComposite.setLayout(new FillLayout(SWT.VERTICAL));
		Label l1 = new Label(glComposite, SWT.NONE);
		l1.setText("Group");
		classItem.setControl(glComposite);
		
	}

	private void createAttrComposite(TabFolder tabFolder,TabItem attrItem) {
		attrItem.setText("Attribute");
		
		Composite flComposite = new Composite(tabFolder,SWT.NONE);
		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		flComposite.setLayout(gridLayout);
		
		int minimumWidth = tabFolder.getBorderWidth();
		
	    final Composite searchComposite = new Composite(flComposite,SWT.NONE);
		GridData searchGD = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		searchGD.minimumWidth = minimumWidth;
		searchGD.minimumHeight=20;
		searchComposite.setLayoutData(searchGD);		
		searchComposite.setLayout(new FillLayout());
		final Text searchText = new Text(searchComposite,SWT.SEARCH| SWT.ICON_SEARCH);
		searchText.addTraverseListener(new TraverseListener() {  
		      public void keyTraversed(TraverseEvent e) {  
		        if (e.keyCode == 13) {
		          // e.detail = SWT.TRAVERSE_TAB_NEXT;  
		          e.doit = true;  
			      doSearchAttr(searchText.getText());
		        }  
		      }  
		 }); 

		Composite treeViewComposite = new Composite(flComposite,SWT.NONE);
		GridData treeGD = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		treeGD.minimumWidth = minimumWidth;
		treeGD.minimumHeight = 300;
		treeViewComposite.setLayoutData(treeGD);
		
		treeViewComposite.setLayout(new FillLayout());
		
		this.attrViewer = new TreeViewer(treeViewComposite,SWT.NONE);
		this.attrViewer.setContentProvider(new AttrContentProvider(attrViewer));
		this.attrViewer.setLabelProvider(new AttrLabelProvider());
		attrItem.setControl(flComposite);
		//this.activeItem = this.attrViewer;
		this.attrViewer.addSelectionChangedListener(new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				Object e = ((IStructuredSelection)event.getSelection()).getFirstElement();
				doChangeAttrSelection(e);
			}
			
		});
		
		doSearchAttr("");
		
	}
	
	protected void doChangeAttrSelection(Object e) {	
		if(e instanceof TreeNode){
			ObjectEditor editor = null;
			Object selectObj = ((TreeNode)e).getModel();
			
			if(selectObj instanceof NamespaceVo) {
				NamespaceVo ns = (NamespaceVo) selectObj;
				editor = this.editors.get(ns.getNamespace());
				if(editor == null) {
					editor = new NamespaceEditor(contentContainer,SWT.NONE,ns,this);
					this.editors.put(editor.getKey(), editor);
				}
			}else if(selectObj instanceof AttributeVo) {
				AttributeVo attr = (AttributeVo)selectObj;
				editor = this.editors.get(attr);
				if(editor == null) {
					editor = new AttributeEditor(contentContainer,SWT.NONE,attr,this);
					this.editors.put(editor.getKey(), editor);
				}
			}
			this.selectEditor(editor,false);
		} else {
			this.selectEditor(this.noSelectionEditor,false);
		}
	}

	public void selectEditor(ObjectEditor editor,boolean backEditor) {		
		if(editor == null) {
			return;
		}
		ObjectEditor currentEditor = (ObjectEditor)sl.topControl;
		if(editor == currentEditor) {
			return;
		}
		
		if(currentEditor != null) {
			currentEditor.deactive();
		}
		if(backEditor) {
			this.editorStack.push(currentEditor);
		}
		
		if(!editor.isOpen()) {
			editor.open();
		}
		if(!editor.isActive1()) {
			editor.active();
		}
		this.sl.topControl = editor;
		this.contentContainer.layout(true);
	}

	private void doSearchInstance(String text) {
		// TODO Auto-generated method stub
		
	}

	private void doSearchClass(String text) {
		// TODO Auto-generated method stub
		
	}

	private void doSearchAttr(String text) {/*
		IConfigableManager cfgService = Conn.ins().getService(IConfigableManager.class);
		Map<String,String> params = new HashMap<String,String>();
		if(text== null || "".equals(text)) {
			text = "";
		}
		params.put("queryStr", text);
		List<Namespace> nss = cfgService.query("namespaceManager", "findNSAttrByLike", params);
		List<TreeNode> tns = new ArrayList<TreeNode>();
		for(Namespace ns : nss) {
			TreeNode nst = new TreeNode();
			nst.setLabel(ns.getNamespace());
			nst.setImg(AttrLabelProvider.DEFAULT_IMAGE_01);
			nst.setType(CLConstants.TYPE_NAMESPACE);
			nst.setSrcObj(ns);
			if(ns.getAttrs() != null && ns.getAttrs().size() > 0) {
				for(Attribute attr : ns.getAttrs()) {
					TreeNode at = new TreeNode();
					at.setLabel(attr.getName());
					at.setImg(AttrLabelProvider.DEFAULT_IMAGE_02);
					at.setType(CLConstants.TYPE_ATTR);
					at.setSrcObj(attr);
					nst.getChildren().add(at);
				}
			}	
			tns.add(nst);
		}
		
		this.attrViewer.setInput(tns.toArray());
		if(this.attrViewer.getTree().getItems().length > 0) {
			TreeItem ti = this.attrViewer.getTree().getItem(0);
			if(ti != null) {
				ti.setExpanded(true);
			}
		}
	*/}

	public void back() {
		ObjectEditor oe = this.editorStack.pop();
		if(oe != null) {
			this.selectEditor(oe, false);
		}
	}
	
	public void open() {
		this.isOpen = true;
	}
	
	public void active() {
		this.isActive = true;
	}
	
	public void deactive() {
		this.isActive = false;
	}
	
	public void close() {
		this.isOpen = false;
	}
	
	public boolean isActive1() {
		return this.isActive;
	}
	
	public boolean isOpen() {
		return this.isOpen;
	}
	
}
