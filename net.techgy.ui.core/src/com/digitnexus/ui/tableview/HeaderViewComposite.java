package com.digitnexus.ui.tableview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.uidef.ViewerDef;

import net.cmty.ui.core.editor.AbstractEditorPart;
import net.cmty.ui.core.editor.IEditorInput;
import net.cmty.ui.core.editor.NameValueComposite;
import net.cmty.ui.core.i18n.I18NUtils;

/**
 * 表头内容和表头支持的操作，如查询条件和查询按钮
 * @author ylye
 *
 */
@SuppressWarnings("serial")
public abstract class HeaderViewComposite extends AbstractEditorPart{

	//@Inject
	//private MSelectionService selectionService;
	
	//查询条件定义
	protected List<FieldDef> qeuryDefs;
	//动作定义
	protected List<ActionDef> actionDefs;
	//显示视图定义，如表格，图形，树等显示定义
	protected ViewerDef def = null;
	//查询条件对应的输入控件
	protected List<NameValueComposite> headerInputs = new ArrayList<NameValueComposite>();
	
	//protected IEditorPart editor;
	//头部容器，包括查询条件和操作，但不包括表格的头部
	protected Composite headerContainer;
	
	//内容区
	protected Composite contentViewContent;
	//操作按钮列表，如增加，删除，修改，查询按钮
	protected List<Button> buttons = new ArrayList<Button>();
	//是否修改过，如果修改过此值为true
	protected boolean isDirdy;
	//操作监听器，如用户选新增，删除，修改，查询等操作后会执行此方法
	private SelectionListener actionListener = new SelectionAdapter(){
		@Override
		public void widgetSelected(SelectionEvent e) {
			doAction((Button)e.getSource());
		}
	};
	/**
	 * 
	 * @param parent
	 * @param style  服务器可能个性化定义其样式定义
	 * @param headerOrQeuryDefs 查询条件定义
	 * @param actionDefs 动作定义
	 * @param input 编辑器输入
	 * @param def 视图定义
	 */
	public HeaderViewComposite(Composite parent,int style,List<FieldDef> headerOrQeuryDefs,
			List<ActionDef> actionDefs,IEditorInput input,ViewerDef def) {
		super(parent,style,input);
		this.qeuryDefs = headerOrQeuryDefs;
	    this.actionDefs = actionDefs;
		/*this.editor = editor;*/
		this.def = def;
		createContent();
	}
	/**
	 * 创建编辑器显示的内容。
	 * 此方法默认显示查询条件和查询条件所对应的操作
	 */
	protected void createContent() {
		this.setLayout(new FillLayout(SWT.HORIZONTAL));
	    //parent.setBackground(new Color(display, 230, 230, 230 ));
	    
		Composite composite = new Composite(this, SWT.NONE);
		GridLayout  gripLayout = new GridLayout(1, false);
		gripLayout.numColumns=1;
		composite.setLayout(gripLayout);
		//composite.setBackground(new Color(display, 230, 230, 100 ));
		
		headerContainer = new Composite(composite, SWT.NONE|SWT.BORDER);
		gripLayout = new GridLayout(1, false);
		gripLayout.numColumns=1;
		headerContainer.setLayout(gripLayout);
		
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);			
		//gd.widthHint = 0;
		headerContainer.setLayoutData(gd);
		//controlCom.setBackground(new Color(display, 230, 20, 150 ));
		headerContainer.setBounds(0, 0, 200, 200);

		//query condition bar查询条件栏
		Composite extControlCom = new Composite(headerContainer, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);		
		extControlCom.setLayoutData(gd);
		//创建查询条件输入框
		this.createQueryInputControl(extControlCom);
		
		//content bar request item list
		contentViewContent = new Composite(composite, SWT.NONE|SWT.BORDER);			
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);			
		//gd.widthHint = 0;
		contentViewContent.setLayoutData(gd);
		//contentCom.setBackground(new Color(display, 230, 20, 150 ));
		
		//操作栏或按钮栏
        Composite baseControlCom = new Composite(headerContainer, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		baseControlCom.setLayoutData(gd);
		//创建操作栏，如查询，增加，删除，修改，导出等
		this.createOperationButton(baseControlCom);
	}
	
	/**
	 * 每一个字段定义一个查询条件
	 * @param parent
	 */
	protected void createQueryInputControl(Composite parent) {
		//查询条件使用行布局
		RowLayout l = new RowLayout();
		l.center=false;
		l.spacing=8;
		l.wrap=true;
		l.fill=false;
		l.pack=true;
		l.type=SWT.HORIZONTAL;
		l.justify=false;
		l.marginTop=0;
		l.marginBottom=8;
		parent.setLayout(l);
		
		//根据列定义创建头部
		List<FieldDef> qds = this.qeuryDefs;
		if (qds == null) {
			//没有查询条件
			return;
		}
		for (FieldDef d : qds) {
			//一个定义一个查询条件
			NameValueComposite nvc = new NameValueComposite(parent,SWT.NONE,d,null);
			this.headerInputs.add(nvc);
		}
	}
   /**
    * 支持的操作，如增删改查
    * @param parent
    */
	protected void createOperationButton(Composite parent) {
		    RowLayout rowLayout = new RowLayout();
		    //rowLayout.marginLeft=20;
		    rowLayout.center=true;
		    rowLayout.spacing=20;
		    rowLayout.wrap=true;
			parent.setLayout(rowLayout);
		    if(this.actionDefs == null || this.actionDefs.isEmpty()) {
		    	return;
		    }
		    RowData cgd = null;
		    
		    //由当前VO对当前用户所支持的操作添加按钮
		    for(ActionDef ad : this.actionDefs) {
		    	if(!this.isDisplayButton(ad)) {
		    		//不显示按钮，也许没权限
		    		continue;
		    	}
		    	cgd = new RowData();
		    	final Button action = new Button(parent, SWT.NONE);
		    	action.setLayoutData(cgd);
		    	//操作的定义放在data中，用户选择后可以使用
		    	action.setData(ad);
		    	//操作名称
		    	action.setText(I18NUtils.getInstance().getString(ad.getName()));
		    	//操作监听器
		    	action.addSelectionListener(actionListener);
		    	buttons.add(action);
		    }
	}
	
	protected boolean isDisplayButton(ActionDef ad) {
		return true;
	}

	protected String getQueryDefaultValue(FieldDef fd){
		return "";
	}
	
	/**
	 * 用户选择相应按钮后，做相应按钮定议的操作，一般情况是请求服务器做相应的行为，如增加，删除，修改，查询，导出等.
	 * 由子类实现此方法
	 * @param action
	 * @return
	 */
	protected boolean doAction(Button action) {
		throw new UnsupportedOperationException( "doAction" + this.def.getClsName() );
	}
	
	/**
	 * 根据类型取得当前VO所支付的操作，对同一个VO，类型的操作定义只能存在一个。
	 * 如果不支付此类型，返回NULL。
	 * @param type
	 * @return
	 */
	protected ActionDef getActionDef(ActionType type) {
		for(ActionDef def : this.actionDefs) {
			if(def.getActionType().equals(type)) {
				return def;
			}
		}
		return null;
	}
	  
	protected final int getPreferredWidth(int numOfa, Font font) {
		// Compute width from the column itself
		int aLen = 10;//TextSizeUtil.stringExtent(font, "a").x;
		int result = numOfa * aLen + 8;
		return result;
	}
	
	/**
	 * 取得查询条件
	 * @return
	 */
	protected Map<String,String> getValueAsMap() {
		  if(headerInputs == null || headerInputs.isEmpty()) {
			  return null;
		  }
		  Map<String,String> nvs = new HashMap<String,String>();
		  for(NameValueComposite nvc: headerInputs) {
			  nvs.put(nvc.getName(), nvc.getValue());
		  }
		  return nvs;
	  }
	
	public boolean isDirty() {
		return false;
	}
	
	public boolean save() {
		return false;
	}
	
}
