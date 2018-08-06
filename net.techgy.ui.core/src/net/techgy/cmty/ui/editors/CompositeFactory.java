package net.techgy.cmty.ui.editors;

import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.uidef.BaseDef;
import com.digitnexus.base.uidef.TableViewerEditorDef;
import com.digitnexus.base.uidef.TreeViewerEditorDef;
import com.digitnexus.core.vo.ReportVo;
import com.digitnexus.core.vo.dept.AccountVo;
import com.digitnexus.core.vo.dept.DepartmentVo;
import com.digitnexus.core.vo.dept.EmployeeVo;
import com.digitnexus.core.vo.map.MapVo;
import com.digitnexus.ui.map.MapComposite;
import com.digitnexus.ui.model.FieldDefComparator;
import com.digitnexus.ui.tableview.HeaderViewComposite;
import com.digitnexus.ui.tableview.UpdatableTableViewComposite;
import com.digitnexus.ui.treeview.UpdatableTreeViewComposite;

import net.cmty.ui.core.editor.CompositeFactoryManager;
import net.cmty.ui.core.editor.ICompositeFactory;
import net.cmty.ui.core.editor.IEditorInput;
import net.cmty.ui.core.report.ReportComposite;

public class CompositeFactory implements ICompositeFactory{

	public CompositeFactory(){}
	//private static final CompositeFactory instance = new CompositeFactory();
	/*public static CompositeFactory getInstance() {
		return instance;
	}*/
	
	public static FieldDefComparator fieldDefComparator = new FieldDefComparator();
	 
	public HeaderViewComposite cretateComposite(Composite parent, BaseDef def, IEditorInput input) {
        
		String clsName = def.getClsName();
		
		if(def instanceof TableViewerEditorDef) {
			//表格式编辑器
			TableViewerEditorDef tvd = (TableViewerEditorDef)def;
			if(tvd.getQeuryDefs() != null && !tvd.getQeuryDefs().isEmpty()) {
				//依据查询条件定义的顺序排列控件
				Collections.sort(tvd.getQeuryDefs(), this.fieldDefComparator);
			}
			if(tvd.getItemDefs() != null && !tvd.getItemDefs().isEmpty()) {
				//排列表头
				Collections.sort(tvd.getItemDefs(), this.fieldDefComparator);
			}
			return new UpdatableTableViewComposite(parent,SWT.NONE,input,tvd);
		}else if(def instanceof TreeViewerEditorDef) {
			if(DepartmentVo.class.getName().equals(clsName)) {
				TreeViewerEditorDef tvd = (TreeViewerEditorDef)def;
				//Composite parent,int style,IEditorPart editor,TreeViewDef def,Object obj
				return new DeptTreeViewComposite(parent,SWT.NONE, input,tvd,null);
			}else if(EmployeeVo.class.getName().equals(clsName)) {
				TreeViewerEditorDef tvd = (TreeViewerEditorDef)def;
				//Composite parent,int style,IEditorPart editor,TreeViewDef def,Object obj
				return new UpdatableTreeViewComposite(parent,SWT.NONE,input,tvd,null);
			} else {
				TreeViewerEditorDef tvd = (TreeViewerEditorDef)def;
				//Composite parent,int style,IEditorPart editor,TreeViewDef def,Object obj
				return new UpdatableTreeViewComposite(parent,SWT.NONE, input,tvd,null);
			}
		}
	return null;
	}
	
}
