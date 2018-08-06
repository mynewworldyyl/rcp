package com.digitnexus.ui.model;

import java.util.Collection;
import java.util.List;

import net.techgy.ui.core.utils.UIUtils;

import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TableColumn;

import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.base.utils.Utils;

@SuppressWarnings("serial")
public class ItemLabelProvider extends LabelProvider implements ITableLabelProvider/*,ITableColorProvider*/,ITableFontProvider{
	
	private List<FieldDef> fieldDefs = null;
	
	private Color evenBg = null;
	private Color oddBg = null;
	private TableViewer viewer = null;
	
	public ItemLabelProvider(List<FieldDef> itemFields,TableViewer viewer) {
		this.viewer = viewer;
		this.fieldDefs = itemFields;
		//奇偶列的背景色
		evenBg = viewer.getTable().getDisplay().getSystemColor(SWT.COLOR_CYAN);
		oddBg = viewer.getTable().getDisplay().getSystemColor(SWT.COLOR_GRAY);
	}
	
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	/**
	 * 取指定列要显示的内容
	 */
	public String getColumnText(Object element, int columnIndex) {
		//只支持PropertySourceModel实例
		if(!(element instanceof PropertySourceModel)) {
			return null;
		}
		
		//取得列标头对应的字段名称，此名称在列标头初始化时设定
		TableColumn column = viewer.getTable().getColumn(columnIndex);
		Object elt = ((PropertySourceModel)element).getModel();
		String fieldName = (String)column.getData("fieldName");

		//通过反射取得指定对象上的字段值
		Object vo = UIUtils.getInstance().getModelProperty(elt, fieldName);
		if(vo == null) {
			return "";
		}
		//取得字段定义
		FieldDef fd = Utils.getInstance().getFieldDef(this.fieldDefs, fieldName);
		if(fd == null) {
			throw new IllegalStateException();
		}
		String value = "";
		if(fd != null && fd.getKeyValues() != null && !fd.getKeyValues().isEmpty() ) {
			//字段是多值取成的列表，首先取得当前KEY列表
			String[] vs = Utils.getInstance().getCollectionValues(vo);
			//由KEY取得值列表
			vs = Utils.getInstance().getMapValues(fd.getKeyValues(), vs);
			value =  getCollectionValue(vs);
		} else if(fd != null && fd.getTreeRoots() != null && !fd.getTreeRoots().isEmpty() ) {
			//树结构控件显示方式和KEY-VALUE方式相同
			String[] vs = Utils.getInstance().getCollectionValues(vo);
			vs = Utils.getInstance().getTreeLabels(fd, vs);
			value =  getCollectionValue(vs);
		} else {
			value =  vo.toString();
		}
		if(fd.getUiType().equals(UIType.Password)) {
			//密码不能直接显示出来
			value = "******";
		}
		return value;
	}
	
	private String getCollectionValue(String[] vs) {
		if( vs == null ) {
			//空值，由空字符串组成
			return "";
		}else if(vs.length == 1) {
			//单值
			return vs[0];
		} else {
			//多个值组成列表，只在方格内显示第一个值，其他值通过Tooltip的方式显示
			StringBuffer sb = new StringBuffer(vs[0]);
			for(int index=1; index < vs.length; index++) {
				sb.append("\n").append(vs[index]);
			}
			//由title标签实现Tooltip显示方式，当鼠标停留上面时，会弹出提未框
			String code = "<abbr title='" + sb.toString() + "'>" + vs[0] + "...</abbr>";
			return code;
		}
	}

	//@Override
	public Color getForeground(Object element, int columnIndex) {
		
		return null;
	}

	//@Override
	public Color getBackground(Object element, int columnIndex) {
		Object inputElement = this.viewer.getInput();
		Object[] los = null;
		if(inputElement instanceof Object[]) {
			 los =(Object[])inputElement;
		}else if(inputElement instanceof Collection) {
			los = ((Collection)inputElement).toArray();
		}else {
			los = new Object[0];
		}
		int index = 0; 
		for(;index < los.length; index++) {
			if(los[index].equals(element)) {
				break;
			}
		}
		if(index%2 == 0) {
			return this.evenBg;
		}else {
			return this.oddBg;
		}
	}

	@Override
	public Font getFont(Object element, int columnIndex) {
		
		return null;
	}
	
	
}