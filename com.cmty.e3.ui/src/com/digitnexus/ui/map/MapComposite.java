package com.digitnexus.ui.map;

import net.cmty.ui.core.editor.IEditorInput;
import net.techgy.ui.core.CoreUIActivator;

import org.eclipse.rap.demo.gmaps.internal.GMapsExamplePage;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.uidef.TableViewerEditorDef;
import com.digitnexus.ui.tableview.HeaderViewComposite;


public class MapComposite extends HeaderViewComposite{

	public static final String MAP_ID = 
			CoreUIActivator.PLUGIN_ID + "/" + MapComposite.class.getName();
	
	private GMapsExamplePage map;
	
	public MapComposite(Composite parent,int style, IEditorInput input,TableViewerEditorDef def) {
		super( parent, style, def.getQeuryDefs(),def.getActionDefs(),input, def);	
	}
	

	@Override
	protected void createContent() {
		this.map = new GMapsExamplePage();
		this.setLayout(new FillLayout());
		this.map.createControl(this);
	}
	
	@Override
	public String getPanelId() {
		return MapComposite.class.getName();
	}
	
}
