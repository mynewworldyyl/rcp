package net.techgy.cmty.ui.cl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.digitnexus.base.utils.JsonUtils;

import net.techgy.cl.IConfigableManager;
import net.techgy.cmty.vo.AttributeVo;
import net.techgy.cmty.vo.NamespaceVo;
import net.techgy.ui.core.utils.ImageUtils;

public class NamespaceEditor extends ObjectEditor {
	
	private NamespaceVo ns = null;
	public NamespaceEditor(Composite parent,int style,NamespaceVo ns,ConfigComposite configComposite) {
		super(parent,style,ns,true,configComposite);
		this.ns = ns;
	}
	
	@Override
	public String getKey() {
		return ns.getNamespace();
	}

	@Override
	public void createToolBar(ToolBar toolbar) {
		
		ToolItem addAttrItem = new ToolItem(toolbar, SWT.PUSH);
		//addItem.setText("Add");
		addAttrItem.setToolTipText("New Attribute");
		Image addAttrItemicon = ImageUtils.getInstance().getImage(NamespaceEditor.class.getResourceAsStream("/img/ti04.png"), 20, 20);
		addAttrItem.setImage(addAttrItemicon); 
		addAttrItem.addSelectionListener(new SelectionAdapter(){
		public void widgetSelected(SelectionEvent e) {
			AttributeVo attr = new AttributeVo();
			attr.setNamespace(ns.getNamespace());
			if(configComposite.newAtrEditor == null) {
				configComposite.newAtrEditor = 
					new AttributeEditor(NamespaceEditor.this.getParent(),SWT.NONE,attr,NamespaceEditor.this.configComposite);
			}
			configComposite.selectEditor(configComposite.newAtrEditor, true);					
		}
	    });
	}

	@Override
	public String getTitle() {
		return this.getKey();
	}

	@Override
	public void createEditorContent(Composite container) {
		
		Composite mainContent = new Composite(container,SWT.BORDER);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		mainContent.setLayout(gridLayout);
		
		int minimumWidth = 500;
		Composite headerContainer = new Composite(mainContent,SWT.BORDER);
		GridData headerFlGD = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		headerFlGD.minimumWidth = minimumWidth;
		headerFlGD.minimumHeight = 50;
		headerContainer.setLayoutData(headerFlGD);
		createNamespaceHeader(headerContainer);
		
		Composite attributeListContainer = new Composite(mainContent,SWT.BORDER);
		GridData attributeListGD = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		attributeListGD.minimumWidth = minimumWidth;
		attributeListGD.minimumHeight = 500;
		attributeListContainer.setLayoutData(attributeListGD);
		createAttributeList(attributeListContainer);
	}
	
	private void createAttributeList(Composite attributeListContainer) {
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		attributeListContainer.setLayout(gridLayout);		
	}

	private Text nameText = null;
	private Button comfirmButton = null;
	private Button cancelButton  = null;
	private Button editButton = null;
	private Text descText = null;
	
	private void createNamespaceHeader(Composite headerContainer) {
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		headerContainer.setLayout(gridLayout);
		
		editButton = new Button(headerContainer,SWT.NONE);
		editButton.setText("Edit");
		GridData gd = new GridData(SWT.CENTER,SWT.CENTER,false,false,1,1);
		editButton.setLayoutData(gd);
		
		comfirmButton  = new Button(headerContainer,SWT.NONE);
		comfirmButton.setText("Comfirm");
		gd = new GridData(SWT.CENTER,SWT.CENTER,false,false,1,1);
		comfirmButton.setLayoutData(gd);
		comfirmButton.setEnabled(false);
		
		cancelButton  = new Button(headerContainer,SWT.NONE);
		cancelButton.setText("Cancel");
		gd = new GridData(SWT.CENTER,SWT.CENTER,false,false,1,1);
		cancelButton.setLayoutData(gd);
		cancelButton.setEnabled(false);
		
		//new Label(headerContainer,SWT.NONE);	
		
		Label nameLabel = new Label(headerContainer,SWT.NONE);
		nameLabel.setText("Name Space: ");		
		nameText = new Text(headerContainer,SWT.BORDER);
		nameText.setText(ns.getNamespace());
		nameText.setEditable(false);
		gd = new GridData(SWT.FILL,SWT.FILL,false,false,1,1);
		gd.minimumWidth=50;
		nameText.setLayoutData(gd);
		Label nameMsgLabel = new Label(headerContainer,SWT.NONE);
		nameMsgLabel.setText("");
		nameMsgLabel.setVisible(false);
		
		Label descLabel = new Label(headerContainer,SWT.NONE);
		descLabel.setText("Desc: ");		
		descText = new Text(headerContainer,SWT.BORDER);
		gd = new GridData(SWT.FILL,SWT.FILL,false,false,1,1);
		descText.setLayoutData(gd);
		descText.setText(ns.getDesc());	
		descText.setEditable(false);
		
		Label descMsgLabel = new Label(headerContainer,SWT.NONE);
		descMsgLabel.setText("");
		descMsgLabel.setVisible(false);
		
		editButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				nameText.setEditable(true);
				descText.setEditable(true);
				editButton.setEnabled(false);
				comfirmButton.setEnabled(true);
				cancelButton.setEnabled(true);
			}		
		});
		comfirmButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				nameText.setEditable(false);
				descText.setEditable(false);
				editButton.setEnabled(true);
				cancelButton.setEnabled(false);
				comfirmButton.setEnabled(false);
				ns.setDesc(descText.getText());
				ns.setNamespace(nameText.getText());
				save();
				if(ns.getId() == null) {
					configComposite.back();
				}
			}		
		});
		cancelButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				reset();
				if(ns.getId() == null) {
					configComposite.back();
				}
			}	
		});
		reset();
	}
	
	@Override
	public void reset() {
		super.reset();
		if(ns.getId() == null) {
			nameText.setEditable(true);
			descText.setEditable(true);
			editButton.setEnabled(false);
			cancelButton.setEnabled(true);
			comfirmButton.setEnabled(true);			
		}else {
			nameText.setEditable(false);
			descText.setEditable(false);
			editButton.setEnabled(true);
			cancelButton.setEnabled(false);
			comfirmButton.setEnabled(false);	
		}
		nameText.setText(ns.getNamespace());
		descText.setText(ns.getDesc());
	}

	@Override
	public void save() {
		NamespaceVo n = null;
		try {
			n = this.ns.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String attrStr = JsonUtils.getInstance().toJson(n,false);
		Map<String,String> params = new HashMap<String,String>();
		params.put("ns", attrStr);
		IConfigableManager cfgService = null;//Conn.ins().getService(IConfigableManager.class);	
		if(ns.getId() == null) {
			cfgService.create("namespaceManager","createNS",params);
		} else {
			cfgService.update("namespaceManager","updateNS",params);
		}
		this.isChange = false;
	}
	
}
