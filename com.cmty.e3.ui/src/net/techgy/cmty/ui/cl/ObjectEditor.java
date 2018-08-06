package net.techgy.cmty.ui.cl;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import net.techgy.cmty.vo.NamespaceVo;
import net.techgy.ui.core.utils.ImageUtils;

public abstract class ObjectEditor extends Composite{

	protected Object obj;
	
	private boolean isOpen = false;
	
	private boolean isActive = false;
	
	protected Label title = null;
	
	private ToolBar toolBar = null;
	
	protected boolean isChange = false;
	
	private Composite editorContainer = null;
	
	protected ConfigComposite configComposite;
	
	public ObjectEditor(Composite parent,int style, Object obj,
			boolean showToolBar,ConfigComposite configComposite) {
		super(parent,style);
		this.obj = obj;
		this.configComposite = configComposite;
		FillLayout fl = new FillLayout(SWT.HORIZONTAL);
		fl.marginHeight=0;
		fl.marginWidth=0;
		fl.spacing=0;
		this.setLayout(fl);
		
		Composite mainContent = new Composite(this,SWT.BORDER);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		mainContent.setLayout(gridLayout);
		
		int minimumWidth = 500;
		Composite titleContainer = new Composite(mainContent,SWT.BORDER);
		GridData titleFlGD = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		titleFlGD.minimumWidth = minimumWidth;
		titleFlGD.minimumHeight = 25;
		titleContainer.setLayoutData(titleFlGD);
		
		RowLayout rl = new RowLayout(SWT.HORIZONTAL);
		rl.fill=false;
		rl.marginLeft =5;
		rl.pack = true;
		rl.wrap = false;
		titleContainer.setLayout(rl);
		this.title = new Label(titleContainer,SWT.NONE);
		RowData rd = new RowData(SWT.DEFAULT,titleFlGD.minimumHeight);
		this.title.setLayoutData(rd);
		if(showToolBar) {
			Composite toolBarContent = new Composite(mainContent,SWT.BORDER);
			GridData toolBarGD = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
			toolBarGD.minimumWidth = minimumWidth;
			toolBarGD.minimumHeight = 25;
			toolBarContent.setLayoutData(toolBarGD);
			toolBarContent.setLayout(new FillLayout(SWT.HORIZONTAL));
			this.toolBar = new ToolBar(toolBarContent, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
		}
		 
		int minimumHeight = 500;
		editorContainer = new Composite(mainContent,SWT.BORDER);
		GridData editorFlGD = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		editorFlGD.minimumWidth = minimumWidth;
		editorFlGD.minimumHeight = minimumHeight;
		editorContainer.setLayoutData(editorFlGD);
		editorContainer.setLayout(new FillLayout());
	}
	
	public void open() {
		if(this.toolBar != null) {
			createDefaultToolItems();
			this.createToolBar(this.toolBar);
		}
		String t = this.getTitle();
		if(t != null) {
			this.title.setText(t);
		}
		this.createEditorContent(editorContainer);
		this.isOpen = true;
		this.reset();
	}
	
	private void createDefaultToolItems() {
		ToolItem addItem = new ToolItem(this.toolBar, SWT.PUSH);
		//addItem.setText("Add");
		addItem.setToolTipText("New Namespace");
		Image icon = ImageUtils.getInstance().getImage(ObjectEditor.class.getResourceAsStream("/img/ti04.png"), 20, 20);
		addItem.setImage(icon); 
		addItem.addSelectionListener(new SelectionAdapter(){
		public void widgetSelected(SelectionEvent e) {
			if(configComposite.newNSEditor == null) {
				configComposite.newNSEditor = 
					new NamespaceEditor(ObjectEditor.this.getParent(),SWT.NONE,new NamespaceVo(),ObjectEditor.this.configComposite);
			}
			configComposite.selectEditor(configComposite.newNSEditor, true);					
		   }
	    });
	}

	protected void doSave() {
		if(this.isChange) {
			this.save();
		}
		this.isChange=false;
	}

	public void active() {
		this.isActive = true;
	}
	
	public void deactive() {
		this.isActive = false;
	}
	
	public boolean isChange() {
		return this.isChange;
	}
	
	public void close() {
		if(this.isChange) {
			boolean flag = MessageDialog.openConfirm(this.getShell(), "Notice",
					"You have do change to  " + this.getKey() +", are you want do save it?");
			if(flag) {
				this.doSave();
			}else {
				this.isChange = false;
			}
		}
		this.deactive();
		//this.obj= null;
		//this.editorContainer=null;
		//this.title = null;
		//this.toolBar = null;
		this.isOpen = false;
	}
	
	public boolean isActive1() {
		return this.isActive;
	}
	
	public boolean isOpen() {
		return this.isOpen;
	}
	
	public void reset() {
		
	}
	
	public abstract void save();
	
	public abstract String getKey();
	
	public abstract void createToolBar(ToolBar toolbar);
	
	public abstract String getTitle();
	
	public abstract void createEditorContent(Composite container);
}
