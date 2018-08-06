package com.digitnexus.ui.treeview;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.TreeViewerEditorDef;
import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.ui.model.PropertySourceModel;
import com.digitnexus.ui.tableview.ItemsViewComposite;

import net.cmty.ui.core.editor.IEditorInput;
import net.cmty.ui.core.i18n.I18NUtils;
import net.techgy.cmty.service.DataDto;
import net.techgy.ui.core.network.WSConn;
import net.techgy.ui.core.utils.UIUtils;

@SuppressWarnings("serial")
public class ReadOnlyTreeViewComposite extends ItemsViewComposite{

	protected Comparator<PropertySourceModel> comparator = new Comparator<PropertySourceModel>() {
		@Override
		public int compare(PropertySourceModel o1, PropertySourceModel o2) {
            if((o1.getModel() instanceof Comparable) && (o2.getModel() instanceof Comparable)) {
            	return ((Comparable)o1.getModel()).compareTo((Comparable)o2.getModel());
            } else {
            	MessageDialog.openError(ReadOnlyTreeViewComposite.this.getShell(), I18NUtils.getInstance().getString("Note"),
   					 I18NUtils.getInstance().getString("ClassNotComparable",o1.getModel().getClass().getName()));
            	throw new CommonException("ClassNotComparable: ", o1.getModel().getClass().getName());
            }
		}
	};
	
	public ReadOnlyTreeViewComposite(Composite parent,int style, IEditorInput input,TreeViewerEditorDef def,Object obj) {
		super( parent, style, def.getQeuryDefs(),def.getActionDefs(),
				def.getItemDefs(), input, def);
		doQuery(this.getActionDef(ActionType.Query));
	}

	protected ColumnViewer createContentViewer() {
		    contentViewContent.setLayout(new FillLayout(SWT.VERTICAL));
		    TreeViewer treeViewer = new TreeViewer(contentViewContent, SWT.MULTI|SWT.V_SCROLL|SWT.H_SCROLL | SWT.FULL_SELECTION);
		    final Tree tree = treeViewer.getTree();
		    //TableLayout layout = new TableLayout(); // ר���ڱ��Ĳ���  
	        //table.setLayout(layout);  
		    tree.setHeaderVisible(true);
		    tree.setLinesVisible(true);
		    treeViewer.setColumnProperties(initColumnProperties( tree ) );
		    treeViewer.setContentProvider(new TreeContentProvider() );
		    TreeLabelProvider labelProvider = new TreeLabelProvider(getTreeViewerDef(),treeViewer);
		    treeViewer.setLabelProvider(labelProvider);
			//table.setBackground(new Color(display, 130, 200, 50 ));
			/*if(editor != null) {
				IWorkbenchPartSite site = this.editor.getSite();
				site.setSelectionProvider( treeViewer );
			}*/
			treeViewer.setInput(this.psms);
			return treeViewer;
	 }
	  
	@Override
	protected Composite getViewerControl() {
		return this.getTreeViewer().getTree();
	}

	protected TreeViewerEditorDef getTreeViewerDef() {
		return (TreeViewerEditorDef) this.def;
	}
	
	private TreeViewer getTreeViewer() {
		return (TreeViewer) this.viewer;
	}
	
	protected boolean doDetail(ActionDef ad) {
		return false;
	}
	
	protected boolean doAction(Button action) {
		boolean f = super.doAction(action);
		if(f) {
			return f;
		}
		ActionDef ad = (ActionDef)action.getData();
		if(ad == null) {
			return doNoDefAciton(action);
		} else {
			if(ad.getActionType().equals(ActionType.Detail)) {
				return this.doDetail(ad);
	    	}else if(ad.getActionType().equals(ActionType.Export)) {
	    		return this.doExport(ad);
	    	}else if(ad.getActionType().equals(ActionType.Query)) {
	    		return this.doQuery(ad);
	    	}
		}
		return false;
	}

	private String[] initColumnProperties(Tree tree) {
		List<FieldDef> fs = this.itemDefs;
		if (fs == null || fs.isEmpty()) {
			return new String[0];
		}
		List<String> rs = new ArrayList<String>();
		for (int index = 0; index < fs.size(); index++) {
			FieldDef fd = fs.get(index);
			if (fd.isHide()) {
				continue;
			}
			// TableColumn tableColumn = new TableColumn(table, SWT.NONE);
			TreeColumn treeColumn = new TreeColumn(tree, SWT.RIGHT);
			treeColumn.setAlignment(SWT.LEFT);
			String label = I18NUtils.getInstance().getString(fd.getName());
			treeColumn.setText(label);
			treeColumn.setData("fieldName", fd.getFieldName());
			int columnWidth = fd.getName().length();
			if (fd.getLengthByChar() > 0) {
				columnWidth = fd.getLengthByChar();
			}
			int width = this.getPreferredWidth(columnWidth, tree.getFont());
			treeColumn.setWidth(width);
			rs.add(fd.getFieldName());
		}
		String[] strs = new String[rs.size()];
		rs.toArray(strs);
		return strs;
	}
	  
	protected boolean doQuery(ActionDef ad) {
		if(ad == null) {
			return true;
		}
		Map<String,String> params = UIUtils.getInstance().getValueAsMap(this.headerInputs);
		params.put("clsName", this.def.getClsName());
		DataDto resp = WSConn.ins().call(ad, params);
		if(!resp.isSuccess()) {
			UIUtils.getInstance().showNodeDialog(this.getShell(), resp.getMsg());
			return true;
		}
		
		String fn = getTreeViewerDef().getParentFieldName();
		String setMehtod = "set"+ fn.substring(0, 1).toUpperCase() + fn.substring(1);
		fn = getTreeViewerDef().getSubFieldName();
		String getMehtod = "get"+ fn.substring(0, 1).toUpperCase() + fn.substring(1);
		 
		Method get = null;
		Method set = null;
		Class reqCls = null;
		try {
			//Class chileCollectionType = CommonTreeViewComposite.class.getClassLoader().loadClass(this.def.getSubCollectionType());
			reqCls = UIUtils.getInstance().loadClass(this.def.getClsName());
			set = reqCls.getMethod(setMehtod, String.class);
			get = reqCls.getMethod(getMehtod, new Class[0]);
		}catch (SecurityException e1) {
			throw new RuntimeException("",e1);
		}catch (NoSuchMethodException e) {
			throw new RuntimeException("",e);
		}
		
		psms.clear();
		
		List<Object> list = UIUtils.getInstance().commonObjectList(resp.getData(), reqCls);
		
		for(Object o : list) {
			TreeNode tn = getModel(o,get,set);
			if(tn != null) {
				psms.add(tn);
			}
		}
		mergeLocal();
		psms.sort(comparator);
		this.getTreeViewer().refresh();
		this.getTreeViewer().expandAll();
		return true;
	}
	
	
	private TreeNode getModel(Object o,Method get,Method set) {
		boolean modifiable = hasPermission(ActionType.Modify);
		TreeNode tn = new TreeNode(o,getTreeViewerDef().getItemDefs(),modifiable,this.def.getClsName());
		if(modifiable) {
			tn.addPropertyChangeListener(this.pcl);
		}
		try {
			Object vo = get.invoke(o, new Object[0]);
			if(vo == null) {
				return tn;
			}
			Object[] array = null;
			if(vo instanceof Object[]) {
				 array = (Object[])vo;
			}else if(vo instanceof Collection) {
				array = ((Collection)vo).toArray();
			}else {
				throw new RuntimeException("children collection type not support: " + getTreeViewerDef().getSubCollectionType());
			}
			if(array == null || array.length <0) {
				return tn;
			}
			for(Object c : array) {
				//set.invoke(c, o);
				if(c == null) {
					continue;
				}
				TreeNode p =  getModel(c, get, set);
				if(p != null) {
					tn.getChildren().add(p);
				}
				tn.getChildren().sort(comparator);
			}
			return tn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
		 
	 protected boolean isDisplayButton(ActionDef ad) {
		 return true;
	 }
		
	protected boolean doExport(ActionDef ad) {
		throw new UnsupportedOperationException( "not support Export Action for: " + getTreeViewerDef().getName() );
	 }

	@Override
	public String getPanelId() {
		return ReadOnlyTreeViewComposite.class.getName();
	}
}
