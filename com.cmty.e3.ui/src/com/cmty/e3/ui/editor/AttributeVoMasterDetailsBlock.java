package com.cmty.e3.ui.editor;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class AttributeVoMasterDetailsBlock extends MasterDetailsBlock implements Updatable{

	protected void createContextMenu(Control control,FormToolkit toolkit) {
		MenuManager popupMenuManager = new MenuManager();
		final FormToolkit tk = toolkit;
		IMenuListener listener = new IMenuListener() {
			public void menuAboutToShow(IMenuManager mng) {
				fillContextMenu(mng,tk);
			}
		};
		popupMenuManager.addMenuListener(listener);
		popupMenuManager.setRemoveAllWhenShown(true);
		Menu menu = popupMenuManager.createContextMenu(control);
		control.setMenu(menu);
	}

	private Text configFile;
	private Button replaceAll; 
	private Tree tree;
	private TreeViewer treeViewer;
	private SectionPart sectionPart;
	private AttributeVoEditorPage page;
	//private TemplateBasicXMLEditor ruleSourceEditor;
	//private DeleteRuleNodeAction deleteRuleAction;
	//private IManagedForm managedForm;
	/**
	 * Create the master details block
	 */
	public AttributeVoMasterDetailsBlock(AttributeVoEditorPage page) {
		// Create the master details block
		this.page = page;
		//this.ruleSourceEditor = ruleSourceEditor;
	}

	protected void fillContextMenu(IMenuManager mng, FormToolkit toolkit) {
		// TODO Auto-generated method stub
		/*XMLNode element = this.getCurrentSelectElement(treeViewer);
		if(element == null) return;
		String tagName = element.getTagName();
		if(tagName == null) return ;
		tagName = tagName.trim();
		if(ModelConstants.RL_ELE_RULE.equals(tagName)) {
			mng.add(this.deleteRuleAction);
		}*/
	}

	/**
	 * Create contents of the master details block
	 * @param managedForm
	 * @param parent
	 */
	@Override
	protected void createMasterPart(final IManagedForm managedForm,final Composite parent) {
		//this.managedForm = managedForm;
		FormToolkit toolkit = managedForm.getToolkit();
		//sashForm.setOrientation(SWT.VERTICAL);
		final Composite composite = toolkit.createComposite(parent, SWT.NONE);
		composite.setLayout(new TableWrapLayout());
		composite.setRedraw(true);
		
		toolkit.adapt(composite);
		RuleNodeContentProvider provider = new RuleNodeContentProvider(this);		
	    this.page.getModel().addModelListener(provider);
	    
	    //change config file position
	    final Section section_1 = toolkit.createSection(composite, Section.TITLE_BAR | Section.FOCUS_TITLE | 
	    		Section.TWISTIE| Section.EXPANDED);
		final TableWrapData twd_section_1 = new TableWrapData(TableWrapData.FILL, TableWrapData.BOTTOM);
		twd_section_1.grabVertical = true;
		twd_section_1.heightHint = 107;
		twd_section_1.maxWidth = 356;
		section_1.setLayoutData(twd_section_1);
		section_1.setText("规则属性列表");	    
	    
		final Section section = toolkit.createSection(composite, Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		final TableWrapData twd_section = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
		twd_section.grabVertical = true;
		section.setLayoutData(twd_section);
		toolkit.adapt(section);
		section.setText("规则列表");

		final Composite composite_1 = toolkit.createComposite(section, SWT.NONE);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		composite_1.setLayout(gridLayout_1);
		toolkit.paintBordersFor(composite_1);
		section.setClient(composite_1);
		
		tree = toolkit.createTree(composite_1, SWT.NONE);
		final GridData gd_tree = new GridData(SWT.LEFT, SWT.FILL, true, true);
		gd_tree.heightHint = 283;
		gd_tree.widthHint = 240;
		//gd_tree.verticalSpan = 2;
		tree.setLayoutData(gd_tree);
		toolkit.adapt(tree, true, true);
		
		final Composite composite_22 = toolkit.createComposite(composite_1, SWT.NONE);
		final GridLayout gridLayout_2 = new GridLayout();
		gridLayout_2.numColumns = 1;
		composite_22.setLayout(gridLayout_2);
		toolkit.paintBordersFor(composite_22);
		
		final Button addRule = toolkit.createButton(composite_22, "增加", SWT.NONE);
		addRule.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				XMLNode rulesNode = NodeOpHelper.getInstance().findNode(
						page.getModel().getTree(),ModelConstants.RL_ELE_RULES );			
				if(rulesNode == null) return;
				String type = TemplateHelper.getInstance().getReplaceContentForAddRuleNode(rulesNode);
				if(null == type) {
					MessageDialog.openError(addRule.getShell(), "错误", "模板类型未定义，请检查 responxefilter.xml文件中对应声明");
					return;
				}
				NodeOpHelper.getInstance().addSubNode(rulesNode,type );			
			}			
		});
		final Button deleteRule = toolkit.createButton(composite_22, "删除", SWT.NONE);
		deleteRule.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if(MessageDialog.openConfirm(deleteRule.getShell(), "提示", "你确定要删除结点吗？")) {
					deleteRule();
				}				
			}			
		});		
		treeViewer = new TreeViewer(this.tree);
		treeViewer.setAutoExpandLevel(1);
		treeViewer.setContentProvider(provider);
		treeViewer.setLabelProvider(new RuleNodeLabelProvider());
		
		this.treeViewer.setInput(NodeOpHelper.getInstance()
				.findNode(this.page.getModel().getTree(),ModelConstants.RL_ELE_RULES));
		this.page.getSite().setSelectionProvider(treeViewer);
		
		sectionPart = new SectionPart(section);
		managedForm.addPart(this.sectionPart);

		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				// TODO Auto-generated method stub
				AbstractElement am =  getModelAdapter(event);
				if(null == am) return;
				managedForm.fireSelectionChanged(sectionPart, new StructuredSelection(am));
			}
		});
		
		final Composite composite_2 = toolkit.createComposite(section_1, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.horizontalSpacing = 10;
		gridLayout.marginBottom = 20;
		composite_2.setLayout(gridLayout);
		toolkit.paintBordersFor(composite_2);
		section_1.setClient(composite_2);

		final Label configFileLabel = toolkit.createLabel(composite_2, "配置文件", SWT.NONE);

		configFile = toolkit.createText(composite_2, null, SWT.NONE);
		final GridData gd_configFile = new GridData(SWT.FILL, SWT.CENTER, true, false);
		configFile.setLayoutData(gd_configFile);

		final Button selectConfigFile = toolkit.createButton(composite_2, "浏览...", SWT.NONE);
		new Label(composite_2, SWT.NONE);
        selectConfigFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				FileDialog dialog = new FileDialog(parent.getShell());
				XMLNode root = ruleSourceEditor.getModel().getTree();
				if(root == null) return;
				String input = root.getIFilePath();    
			    IProject project = FileSystemUtil.getInstance().getProject(input);   
				dialog.setFilterPath(FileSystemUtil.getInstance()
						.coverterFullPath(project.getFullPath().toString()));
                //dialog.setFilterNames(new String[] { "prop" });
                dialog.setFilterExtensions(new String[] {"*.lay","*.htm","*.html" });
                dialog.setFilterIndex(0);
                
				String selectedDirectory = dialog.open();
				if (selectedDirectory != null) {
					selectedDirectory = FileSystemUtil.getInstance()
								.getRelativelyInProject(selectedDirectory);	
					selectedDirectory = selectedDirectory.substring(selectedDirectory.lastIndexOf("/")+1);
			    	configFile.setText(selectedDirectory);
					configFile.forceFocus();
					cnChange = true;
					commit();
				}
			}
		});       
		replaceAll = toolkit.createButton(composite_2, "全部替换", SWT.CHECK);
		this.replaceAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				arChange = true;
				commit();
			}
			
		});
		new Label(composite_2, SWT.NONE);
		
		this.createContextMenu(tree, toolkit);	
		createContextMenuAction();
		new Label(composite_1, SWT.NONE);
		this.update(null);
	}

	protected void deleteRule() {
		// TODO Auto-generated method stub
		ISelection selection = treeViewer.getSelection();
		if(selection == null) {
			MessageDialog.openError(treeViewer.getControl().getShell(),
					"错误", "你没有选择要删除的规则");
			return;
		}
	    if(!(selection instanceof IStructuredSelection)) {
	    	MessageDialog.openError(treeViewer.getControl().getShell(),
					"错误", "你选择的结点不能删除");
	    }
	    XMLNode currentNode = (XMLNode) ((IStructuredSelection)selection).getFirstElement();
        
		if(currentNode == null) {
			MessageDialog.openError(treeViewer.getControl().getShell(),
					"错误", "你选择的结点不能删除");
			return;
		}
		String name = currentNode.getFullTagName();
		if(name == null || !ModelConstants.RL_ELE_RULE.equalsIgnoreCase(name.trim())){
			MessageDialog.openError(treeViewer.getControl().getShell(),
					"错误", "选择rule结点才能删除");
			return;
		}
		IDocument document =  currentNode.getDocument();		
		try {
			
			int offset = currentNode.offset;
			int length = currentNode.length;
			if(currentNode.getCorrespondingNode() != null) {
				length = currentNode.getCorrespondingNode().offset - offset + currentNode.getCorrespondingNode().length;
			}
			document.replace(offset, length, "");
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected AbstractElement getModelAdapter(SelectionChangedEvent event) {
		// TODO Auto-generated method stub
		XMLNode node = this.getXMLNode(event);
		if(node == null) return null;
		
		String name = node.getTagName();
		if(null == name || "".equals(name.trim())) {
			return null;
		}
		AbstractElement am = null;
		if(ModelConstants.RL_ELE_RULE.equals(name.trim())) {
			am = new Rule();
		}else if(ModelConstants.RL_ELE_PATTERN.equals(name.trim())) {
			am = new Pattern();
		}else if(ModelConstants.RL_ELE_RESPLACE_CONTENT.equals(name.trim())) {
			am = new ReplaceContent();
		}else if(ModelConstants.RL_ELE_CHARSET.equals(name.trim())) {
			am = new Charset();
		}else if(ModelConstants.RL_ELE_EXPRESSION.equals(name.trim())) {
			am = new Expression();
		}else if(ModelConstants.RL_ELE_METHOD.equals(name.trim())) {
			am = new Method();
		}else if(ModelConstants.RL_ELE_NAME_VALUE_PAIR.equals(name.trim())) {
			am = new NameValuePair();
		}else if(ModelConstants.RL_ELE_OPERATE_TYPE.equals(name.trim())) {
			am = new OperateType();
		}else if(ModelConstants.RL_ELE_PAIT.equals(name.trim())) {
			am = new Pair();
		}else if(ModelConstants.RL_ELE_PARAM.equals(name.trim())) {
			am = new Param();
		}else if(ModelConstants.RL_ELE_PROXY.equals(name.trim())) {
			am = new Proxy();
		}/*else if(ModelConstants.RL_ELE_RULES.equals(name.trim())) {
			//am = new Rules();
		}*/else if(ModelConstants.RL_ELE_SCOPE_TAG.equals(name.trim())) {
			am = new ScopeTag();
		}else if(ModelConstants.RL_ELE_RULE_SITE.equals(name.trim())) {
			am = new RuleSite();
		}	
	
		if(null != am) {
			am.setAdapter(node);
		}
		return am;
	}
	
	 private XMLNode getXMLNode(SelectionChangedEvent event) {
			ISelection selection = event.getSelection();
			if(null == selection || !(selection instanceof StructuredSelection)) {
				return null;
			}
			Object obj = ((StructuredSelection)selection).getFirstElement();
			if(null == obj || !(obj instanceof XMLNode)) {
				return null;
			}
			return (XMLNode)obj;
	 }
	 
	private void createContextMenuAction() {
		// TODO Auto-generated method stub
		/*this.addSiteAction = new AddSiteNodeAction(treeViewer);
		this.deleteSiteAction =  new DeleteSiteNodeAction(treeViewer);
		this.addFilterAction =  new AddFilterNodeAction(treeViewer);*/
		this.deleteRuleAction =  new DeleteRuleNodeAction(treeViewer);
	}
	
	/**
	 * Register the pages
	 * @param part
	 */
	@Override
	protected void registerPages(DetailsPart part) {
		// Register the pages
		part.registerPage(com.sitech.vasd.editor.template.rule.model.Rule.class, 
				new RulePage()); 
		part.registerPage(com.sitech.vasd.editor.template.rule.model.Pattern.class, 
				new PatternPage()); 
		part.registerPage(com.sitech.vasd.editor.template.rule.model.ReplaceContent.class, 
				new ReplaceContentPage()); 
		part.registerPage(com.sitech.vasd.editor.template.rule.model.Charset.class, 
				new CharsetPage());
		part.registerPage(com.sitech.vasd.editor.template.rule.model.Expression.class, 
				new ExpressionExtPage());		
		part.registerPage(com.sitech.vasd.editor.template.rule.model.Method.class, 
				new MethodPage());
		part.registerPage(com.sitech.vasd.editor.template.rule.model.NameValuePair.class,
				new NameValuePairPage());
		part.registerPage(com.sitech.vasd.editor.template.rule.model.OperateType.class,
				new OperateTypePage());
		part.registerPage(com.sitech.vasd.editor.template.rule.model.Pair.class,
				new PairPage());
		part.registerPage(com.sitech.vasd.editor.template.rule.model.Param.class,
				new ParamPage());
		part.registerPage(com.sitech.vasd.editor.template.rule.model.Proxy.class,
				new ProxyPage());
		part.registerPage(com.sitech.vasd.editor.template.rule.model.Rules.class,
				new RulesPage());
		part.registerPage(com.sitech.vasd.editor.template.rule.model.ScopeTag.class,
				new ScopeTagPage());
		part.registerPage(com.sitech.vasd.editor.template.rule.model.RuleSite.class,
				new RuleSitePage());
	}

	/**
	 * Create the toolbar actions
	 * @param managedForm
	 */
	@Override
	protected void createToolBarActions(IManagedForm managedForm) {
		// Create the toolbar actions
	}

	private boolean cnChange = false;
	private boolean arChange = false;
	
	public void commit() {
	   XMLNode rules = this.getRulesNode();
	   if(rules == null) return;
	   if(cnChange) {
		   String cn = this.configFile.getText();
		   NodeOpHelper.getInstance().addAttributeForNode(rules,
				   ModelConstants.RL_PROP_RULES_CONFIG_FILL, cn);
		   cnChange = false;
	   }
	   if(arChange) {
		   boolean ar = this.replaceAll.getSelection();   
		   NodeOpHelper.getInstance().addAttributeForNode(rules,
				   ModelConstants.RL_PROP_RULES_ALL_REPLACE, ar?"true":"false");
		   arChange = false;
	   }   
	}
	
	private XMLNode getRulesNode() {
	   XMLNode root = this.page.getModel().getTree();
	   if(root == null) return null;
	   XMLNode rules = NodeOpHelper.getInstance().findNode(root, ModelConstants.RL_ELE_RULES);
	   return rules;
	}
	
	@Override
	public void update(Object obj) {
		// TODO Auto-generated method stub
	   XMLNode rules = this.getRulesNode();
	   if(rules == null) return;
	   String cf = NodeOpHelper.getInstance().getAttribute(rules, ModelConstants.RL_PROP_RULES_CONFIG_FILL);
	   String allReplace = NodeOpHelper.getInstance().getAttribute(rules, ModelConstants.RL_PROP_RULES_ALL_REPLACE);
	   this.configFile.setText(cf);
	   boolean af = allReplace == null || allReplace.equalsIgnoreCase("false")?false:true;
	   this.replaceAll.setSelection(af);	   
	}


	
	
	
}
