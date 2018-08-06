package com.digitnexus.ui.treeview;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.techgy.ui.core.utils.UIUtils;

import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeColumn;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.TreeViewerEditorDef;
import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.ui.model.PropertySourceModel;

public class TreeLabelProvider  extends LabelProvider implements ITableLabelProvider,ITableFontProvider{

	private TreeViewerEditorDef def = null;
	
	private TreeViewer viewer = null;
	
	private Map<String,Font> nodeTypeToFont = new HashMap<String,Font>();
	
	public TreeLabelProvider(TreeViewerEditorDef def,TreeViewer viewer) {
		this.def = def;
		this.viewer = viewer;
	}
	
	@Override
	public boolean isLabelProperty(Object element, String property) {
		for(FieldDef d : def.getItemDefs()) {
			if(d.getFieldName().equals(property)) {
				return d.isHide();
			}
		}
		return true;
	}
	
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		TreeColumn tc = viewer.getTree().getColumn(columnIndex);
		String fieldName = (String)tc.getData("fieldName");
		//tc.get
		/*FieldDef def = this.def.getItemDefs().get(columnIndex);
		if(def == null) {
			return null;
		}*/
		if(!(element instanceof PropertySourceModel)) {
			return null;
		}
		Object elt = ((PropertySourceModel)element).getModel();
		//String fieldName = def.getFieldName();
		try {
			Field f = elt.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			Object vo = f.get(elt);
			if(vo != null) {
				String value = vo.toString();
				return value;
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	   return null;
	}

	@Override
	public Font getFont(Object element, int columnIndex) {
		Font defFont = viewer.getTree().getFont();
		if(!(element instanceof PropertySourceModel)) {
			return defFont;
		}
		Object elt = ((PropertySourceModel)element).getModel();
        String nodeType = (String)UIUtils.getInstance().getFieldValue(elt,UIConstants.ModelProperty.NodeType.getFieldName());
        if(nodeType == null) {
        	return defFont;
        }
        Font font = this.nodeTypeToFont.get(nodeType);
        if(font == null) {
        	FontData[] data = defFont.getFontData();
        	String fn = data[0].getName();
        	int fh = data[0].getHeight();
        	int fs = data[0].getStyle();
        	String fontDef = this.def.getNameParams().get(nodeType+"-Font");
        	if(fontDef != null) {
        		String[] defs = fontDef.split(",");
        		if(defs == null || defs.length != 3) {
        			throw new CommonException("FontDefInvalide");
        		}
        		if( defs[0] != null && !"".equals( defs[0])) {
        			fn = defs[0];
        		}
        		if( defs[1] != null && !"".equals( defs[1])) {
        			fh = Integer.parseInt(defs[1]);
        		}
        		if( defs[2] != null && !"".equals( defs[2])) {
        			fs = Integer.parseInt(defs[2]);
        		}
        		FontData titleData = new FontData(fn,fh , fs);
            	font = new Font(viewer.getTree().getDisplay(), titleData);
            	this.nodeTypeToFont.put(nodeType, font);
        	}
        }
		return font;
	}
	
	
}