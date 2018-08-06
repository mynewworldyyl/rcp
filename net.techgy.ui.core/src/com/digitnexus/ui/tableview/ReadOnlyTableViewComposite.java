package com.digitnexus.ui.tableview;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.TableViewerEditorDef;
import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.base.utils.ReflectUtils;
import com.digitnexus.ui.model.ItemContentProvider;
import com.digitnexus.ui.model.ItemLabelProvider;
import com.digitnexus.ui.model.PropertySourceModel;
import com.google.gson.reflect.TypeToken;

import net.cmty.ui.core.editor.IEditorInput;
import net.cmty.ui.core.i18n.I18NUtils;
import net.techgy.cmty.service.DataDto;
import net.techgy.ui.core.network.WSConn;
import net.techgy.ui.core.utils.UIUtils;

@SuppressWarnings("serial")
public class ReadOnlyTableViewComposite extends ItemsViewComposite{

	public ReadOnlyTableViewComposite(Composite parent,int style, IEditorInput input,TableViewerEditorDef def) {
		super( parent, style, def.getQeuryDefs(),def.getActionDefs(),def.getItemDefs(), input, def);
		doQuery(this.getActionDef(ActionType.Query));
	}
	
	protected ColumnViewer createContentViewer() {
		    contentViewContent.setLayout(new FillLayout(SWT.VERTICAL));
		    //加表格视图，多行水平垂直可滚动
		    TableViewer v = new TableViewer(contentViewContent, SWT.MULTI|SWT.V_SCROLL|SWT.H_SCROLL | SWT.FULL_SELECTION);
		    final Table table = v.getTable();
		    //可以针对元素做CSS或标签定义
		    //table.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
		    TableLayout layout = new TableLayout(); 
	        table.setLayout(layout);
	        //头部可见
		    table.setHeaderVisible(true);
		    //行可见
			table.setLinesVisible(true);
			//
		    v.setColumnProperties(initColumnProperties( table ) );
		    v.setContentProvider(new ItemContentProvider() );
		    ItemLabelProvider labelProvider = new ItemLabelProvider(this.itemDefs,v);
		    v.setLabelProvider(labelProvider);
			//table.setBackground(new Color(display, 130, 200, 50 ));
			/*if(editor != null) {
				IWorkbenchPartSite site = this.editor.getSite();
				site.setSelectionProvider( v );
			}*/
			v.setInput(this.psms);
			return v;
	 }
	  
	@Override
	protected Composite getViewerControl() {
		return getTableViewer().getTable();
	}
	
	protected TableViewer getTableViewer() {
		return (TableViewer)viewer;
	}
	
	protected TableViewerEditorDef getTableViewDef() {
		return (TableViewerEditorDef)this.def;
	}
	
	/**
	 * 实现导出和查询操作，返回false表示未处理，需要子类处理，
	 * 返回true表示已处理
	 */
	protected boolean doAction(Button action) {
		ActionDef ad = (ActionDef)action.getData();
		if(ad.getActionType().equals(ActionType.Detail)) {
    		//return this.doDetail(ad);
    	}else if(ad.getActionType().equals(ActionType.Query)) {
    		//查询
    		return this.doQuery(ad);
    	}
		
		return false;
	}

	/**
	 * 实元素的ID
	 * @param elt
	 * @return
	 */
	protected String getSelectReqId(Object elt) {
		String idName = null;
		for(FieldDef fd: this.itemDefs) {
			if(fd.isIdable()) {
				idName = fd.getFieldName();
			}
		}
		String id = ReflectUtils.getInstance().getFieldValue(elt, idName);
		return id;
	}

	protected String[] initColumnProperties(Table table) {
		    List<FieldDef> fs = this.itemDefs;
		    if(fs == null || fs.isEmpty()) {
		    	return new String[0];
		    }
		   List<String> l = new ArrayList<String>();
		    for(int index = 0; index < fs.size(); index++) {
		    	FieldDef fd = fs.get(index);
		    	if(fd.isHide() || fd.isHideInRow()) {
		    		//隐藏列
		    		continue;
		    	}
		    	TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		    	//保存列对应的实体字段名称
		    	tableColumn.setData("fieldName", fd.getFieldName());
		    	//列标题
				tableColumn.setText(fd.getName());
				int columnWidth = fd.getName().length();
				if(fd.getLengthByChar() > 0) {
					columnWidth=fd.getLengthByChar();
				}
				//根据字符数量算列宽
				int width = this.getPreferredWidth(columnWidth, table.getFont());
				tableColumn.setWidth(width);
				//返回增加的字段列表，也就是真实显示给用户看的列表
				l.add(fd.getFieldName());
		    }
		    String[] rs = new String[l.size()];
		    rs = l.toArray(rs);
		    return rs;
		}
	  
		
		public boolean isView() {
			return false;
		}
		
		/**
		 * 根据ActionDef取提查询的URL及查询参数请求服务器做查询操作；
		 * 查询结果是JSON格式的列表，元素的类型是MAP的KEY-VALUE的JSON格式，还
		 * 不是对像，需要根据元素Class实例再重新做JSON返序列化
		 */
		@SuppressWarnings("unchecked")
		protected boolean doQuery(ActionDef ad) {
			if(ad == null) {
				UIUtils.getInstance().showNodeDialog(this.getShell(), 
						I18NUtils.getInstance().getString("NotSupportOperation")
						,I18NUtils.getInstance().getString(ActionType.Query.name()));
				return true;
			}
			Map<String,String> params = this.getValueAsMap();
			//查询的实体类全名称
			params.put("clsName", this.getTableViewDef().getClsName());
			DataDto resp = WSConn.ins().call(ad, params);
			if(!resp.isSuccess()) {
				UIUtils.getInstance().showNodeDialog(this.getShell(), resp.getMsg());
				return true;
			}
			List<Object> list = null;
			if(resp.getObj() != null ) {
				//查询结果都是List类型
				list =(List)resp.getObj();
			}else {
				//将json转化为List实例
				Type type = new TypeToken<List>(){}.getType();
				list = JsonUtils.getInstance().fromJson(resp.getData(), type, false, true);
			}
			Class<?> reqCls = null;
			try {
				//实体类Class实例
				reqCls = ReadOnlyTableViewComposite.class.getClassLoader().loadClass(this.def.getClsName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(reqCls == null) {
				//返回的实体类不存在，出错
				UIUtils.getInstance().showNodeDialog(this.getShell(), I18NUtils.getInstance().getString("SystemError"));
				return true;
			}
			this.psms.clear();
			for(Object rd: list) {
				//重新将对象序列化为JSON,然后能过JSON再反序列化为类的实例
				String objJson = JsonUtils.getInstance().toJson(rd, false);
				Object obj = JsonUtils.getInstance().fromJson(objJson, reqCls, false, false);
				//加一行
				this.addModel(obj);
			}
			mergeLocal();
			//重新从psms中提取数据更新界面
			this.viewer.refresh();
			return true;
		}
		
		 protected boolean doExport(ActionDef ad) {
			 return true;
		 }
		 
		 /**
		  * 由单号取单据数据
		  * 
		  * @param reqDef
		  * @param selectObj
		  * @return
		  */
		 public Object getRequest(TableViewerEditorDef reqDef,Object selectObj) {
				//Object selectObj = this.getSelectElement();
				if(selectObj == null) {
					return null;
				}
				//单号
				String reqNum = this.getSelectReqId(selectObj);
				if(reqNum == null) {
					return null;
				}
				Map<String,String> ps = new HashMap<String,String>();
				ps.put("cls", reqDef.getClsName());
				ps.put("reqNum", reqNum);
				String url = null;
				String method = "GET";
				ActionDef ad = this.getActionDef(ActionType.Detail);
				if(ad == null) {
					String compoentnPath = reqDef.getNameParams().get(UIConstants.REQ_GET_COMPONENT_PATH);
					String methodPath = reqDef.getNameParams().get(UIConstants.REQ_GET_METHOD_PATH);
					method = reqDef.getNameParams().get(UIConstants.REQ_GET_METHOD);
					url = compoentnPath + methodPath;
				}else {
					url = ad.getUrl();
					method = ad.getMethod();
				}
				if(null == method || "".equals(method.trim())) {
					method ="GET";
				}
				DataDto resp = WSConn.ins().call(url,method,ps);
				if(!resp.isSuccess()) {
					return null;
				} 
				String itemJson = resp.getData();
				//Type type = new TypeToken<List<RequestDemo>>(){}.getType();
				Object request = JsonUtils.getInstance().fromJson(itemJson,  selectObj.getClass(), false, true);
				return request;
			}
			
		    /**
		     * 实体VO实例封装成PropertySourceModel实例
		     * @param model
		     * @return
		     */
			protected PropertySourceModel addModel(Object model) {
				boolean modifialbe = hasPermission(ActionType.Modify) 
						|| hasPermission(ActionType.Add);
				PropertySourceModel o = new PropertySourceModel(model,this.itemDefs,modifialbe,this.def.getClsName());
				if(modifialbe) {
					//如果有修改或新增权限，则加入修改或新增的监听器
					o.addPropertyChangeListener(this.pcl);
				}
				this.psms.add(o);
				return o;
			}

			@Override
			public String getPanelId() {
				return ReadOnlyTableViewComposite.class.getName();
			}
}
