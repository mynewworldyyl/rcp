package net.techgy.ui.core.map;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import net.cmty.ui.core.i18n.I18NUtils;
import net.techgy.ui.core.CoreUIActivator;
import net.techgy.ui.core.content.CmtyWindow;
import net.techgy.ui.core.content.IPanelProvider;
import net.techgy.ui.core.map.OLMap.LonLat;
import net.techgy.ui.core.utils.FormDataBuilder;

public class OLMapPanelProvider implements IPanelProvider{

	public static final String MAP_PANEL_ID = 
			CoreUIActivator.PLUGIN_ID + "/" + OLMapPanelProvider.class.getName();
	
	private LonLat shenZhen = new LonLat(114.059319,22.561541);
	
	@Override
	public String getId() {
		return MAP_PANEL_ID;
	}

	/*@Override
	public String getTargetId() {
		return MAP_PANEL_ID;
	}*/

	@Override
	public Control createControl(Composite parent, CmtyWindow window) {
		
		Composite com = new Composite(parent,SWT.NONE);
		com.setLayout(new FillLayout());
		
		SashForm sashForm = new SashForm(com,SWT.VERTICAL);
		OLMap mapCom = new OLMap(sashForm,SWT.NONE);
		Composite cp = new Composite(sashForm,SWT.NONE);
		
		sashForm.setWeights(new int[]{90,20});
		sashForm.setSashWidth(3);
		sashForm.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		
		new MapControl(mapCom,cp);
		
		//mapCom.create(shenZhen.lat, shenZhen.log, 3);
		
		return com;
	}
	
	private class MapControl{
		
		private OLMap mapCom;
		//private Composite cp ;
		private TabFolder folder;
		
		public MapControl(OLMap mapCom, Composite cp) {
			this.mapCom = mapCom;
			//this.cp = cp;
			
			createTab(cp);
			//createAnnimationPanel(cp);
		}
		
		private void createTab(Composite com) {
			
			com.setLayout(new FillLayout());
			
			folder = new TabFolder(com, SWT.NONE );
			folder.setLayout(new FillLayout());

			TabItem baseItem = new TabItem(folder, SWT.NONE);
			baseItem.setText(I18NUtils.getInstance().getString("Annimation"));
			Composite annimation = new Composite(folder,SWT.BORDER);
			baseItem.setControl(annimation);
			createAnnimationPanel(annimation);
			
			TabItem bindHtmlInputItem = new TabItem(folder, SWT.NONE);
			bindHtmlInputItem.setText(I18NUtils.getInstance().getString("Bind Html Input"));		
			Composite bindHtmlInputCom = new Composite(folder,SWT.BORDER);
			bindHtmlInputItem.setControl(bindHtmlInputCom);
			createBindHtmlInputComPanel(bindHtmlInputCom);
			
			TabItem bingMapItem = new TabItem(folder, SWT.NONE);
			bingMapItem.setText(I18NUtils.getInstance().getString("Bind Map"));		
			Composite bingMapCom = new Composite(folder,SWT.BORDER);
			bingMapItem.setControl(bingMapCom);
			createBingMapPanel(bingMapCom);
		}
		
		
		private void createAnnimationPanel(Composite annimation) {
			
		
			Composite parent = annimation;
			parent.setLayout(new FormLayout());
			
			Button incZoom = new Button(parent,SWT.PUSH);
			incZoom.setText("Inc");
			
			FormDataBuilder.builder().top(0, 100, 10).left(0, 100, 10)
			.apply(incZoom);
			incZoom.addSelectionListener(new SelectionAdapter(){
				@Override
				public void widgetSelected(SelectionEvent e) {
					//mapCom.incZoom();
				}
				
			});
			
			Button decZoom = new Button(parent,SWT.PUSH);
			decZoom.setText("Dec");
			FormDataBuilder.builder().top(0, 100, 10).left(incZoom,10,SWT.RIGHT)
			.apply(decZoom);
			decZoom.addSelectionListener(new SelectionAdapter(){
				@Override
				public void widgetSelected(SelectionEvent e) {
					//mapCom.decZoom();
				}
			});
			
			
			Button rotate = new Button(parent,SWT.PUSH);
			rotate.setText("Rotate");
			FormDataBuilder.builder().top(0, 100, 10).left(decZoom,10,SWT.RIGHT)
			.apply(rotate);
			rotate.addSelectionListener(new SelectionAdapter(){
				@Override
				public void widgetSelected(SelectionEvent e) {
					//mapCom.rotate(1000, 4 * Math.PI);;
				}
			});
			
			
			Button panTo = new Button(parent,SWT.PUSH);
			panTo.setText("PanTo");
			FormDataBuilder.builder().top(0, 100, 10).left(rotate,10,SWT.RIGHT)
			.apply(panTo);
			panTo.addSelectionListener(new SelectionAdapter(){
				@Override
				public void widgetSelected(SelectionEvent e) {
					//mapCom.panTo(shenZhen, 2000,null);
				}
			});
			
			Button elasticPanTo = new Button(parent,SWT.PUSH);
			elasticPanTo.setText("Elastic Pan To");
			FormDataBuilder.builder().top(0, 100, 10).left(panTo,10,SWT.RIGHT)
			.apply(elasticPanTo);
			elasticPanTo.addSelectionListener(new SelectionAdapter(){
				@Override
				public void widgetSelected(SelectionEvent e) {
					Map<String,String> params = new HashMap<String,String>();
					params.put("easing", "elastic");
					//mapCom.panTo(shenZhen, 2000,params);
				}
			});
			
			Button bouncePanTo = new Button(parent,SWT.PUSH);
			bouncePanTo.setText("Bounce Pan To");
			FormDataBuilder.builder().top(0, 100, 10).left(elasticPanTo,10,SWT.RIGHT)
			.apply(bouncePanTo);
			bouncePanTo.addSelectionListener(new SelectionAdapter(){
				@Override
				public void widgetSelected(SelectionEvent e) {
					Map<String,String> params = new HashMap<String,String>();
					params.put("easing", "bounce");
					//mapCom.panTo(shenZhen, 2000,params);
				}
			});
			
			Button flyPanTo = new Button(parent,SWT.PUSH);
			flyPanTo.setText("Fly Pan To");
			FormDataBuilder.builder().top(0, 100, 10).left(bouncePanTo,10,SWT.RIGHT)
			.apply(flyPanTo);
			flyPanTo.addSelectionListener(new SelectionAdapter(){
				@Override
				public void widgetSelected(SelectionEvent e) {
					Map<String,String> params = new HashMap<String,String>();
					params.put("fly", "fly");
					//mapCom.panTo(shenZhen, 2000,params);
				}
			});
			
		}
		
		private void createBindHtmlInputComPanel(Composite parent) {
			parent.setLayout(new FormLayout());
			
		}

		private void createBingMapPanel(Composite parent) {
			parent.setLayout(new FormLayout());
			
			Button incZoom = new Button(parent,SWT.PUSH);
			incZoom.setText("exportAsPng");
			
			FormDataBuilder.builder().top(0, 100, 10).left(0, 100, 10)
			.apply(incZoom);
			incZoom.addSelectionListener(new SelectionAdapter(){
				@Override
				public void widgetSelected(SelectionEvent e) {
					//mapCom.exportAsPng();
				}
			});
			
			Button decZoom = new Button(parent,SWT.PUSH);
			decZoom.setText("Dec");
			FormDataBuilder.builder().top(0, 100, 10).left(incZoom,10,SWT.RIGHT)
			.apply(decZoom);
			decZoom.addSelectionListener(new SelectionAdapter(){
				@Override
				public void widgetSelected(SelectionEvent e) {
					
				}
			});
			
		}
	}

	

	
}