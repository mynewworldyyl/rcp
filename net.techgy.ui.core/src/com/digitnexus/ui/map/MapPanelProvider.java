package com.digitnexus.ui.map;

import net.techgy.ui.core.CoreUIActivator;
import net.techgy.ui.core.content.CmtyWindow;
import net.techgy.ui.core.content.IPanelProvider;

import org.eclipse.rap.demo.gmaps.internal.GMapsExamplePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class MapPanelProvider implements IPanelProvider{

	public static final String MAP_PANEL_ID = 
			CoreUIActivator.PLUGIN_ID + "/" + MapPanelProvider.class.getName();
	
	@Override
	public String getId() {
		return MAP_PANEL_ID;
	}

	@Override
	public String getTargetId() {
		return MapComposite.MAP_ID;
	}

	@Override
	public Control createControl(Composite parent, CmtyWindow window) {
		
		Composite mapCom = new Composite(parent,SWT.NONE);
		
		GMapsExamplePage mapPage = new GMapsExamplePage();
		mapCom.setLayout(new FillLayout());
		mapPage.createControl(mapCom);
		
		return mapCom;
	}

	
}
