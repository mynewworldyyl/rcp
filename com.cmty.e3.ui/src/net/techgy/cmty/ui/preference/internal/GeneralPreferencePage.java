package net.techgy.cmty.ui.preference.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.digitnexus.ui.map.MapComposite;

import net.cmty.ui.core.i18n.I18NUtils;
import net.cmty.ui.core.workbench.WorkbenchWindow;
import net.techgy.cmty.ui.preference.IPreferencePage;
import net.techgy.cmty.ui.preference.PreferencePagePanel;
import net.techgy.cmty.ui.preference.ProfileManager;
import net.techgy.ui.core.content.CmtyWindow;

public class GeneralPreferencePage implements IPreferencePage {

	@Override
	public String getId() {
		return GeneralPreferencePage.class.getName();
	}

	/*@Override
	public String getTargetId() {
		return GeneralPreferencePage.class.getName();
	}*/

	@Override
	public String title() {
		// TODO Auto-generated method stub
		return I18NUtils.getInstance().getString("General");
	}

	@Override
	public PreferencePagePanel createPage(Composite parent) {
		if(panelItemLabels == null) {
			panelItemLabels = new String[3];
			panelItemLabels[0] = I18NUtils.getInstance().getString(WorkbenchWindow.WORKBENCH_WINDOW_ID);
			panelItemLabels[1] = I18NUtils.getInstance().getString(PreferenceWindow.PREFERENCE_WINDOW_ID);
			panelItemLabels[2] = I18NUtils.getInstance().getString(MapComposite.MAP_ID);
		}
		PreferencePagePanel pp = new GeneralPreferencePagePanel(parent,SWT.NONE,getId());
		return pp;
	};
	
	private Combo defaultPanel = null;
	
	private String[] panelItems = {WorkbenchWindow.WORKBENCH_WINDOW_ID,
			PreferenceWindow.PREFERENCE_WINDOW_ID,MapComposite.MAP_ID};
	
	public String[] panelItemLabels = null;
	
	private class GeneralPreferencePagePanel extends PreferencePagePanel{
		
		public GeneralPreferencePagePanel(Composite parent,int style,String modelId) {
			super(parent,style,modelId);
		}
		
		@Override
		public void restore() {
			
		}

		@Override
		public Map<String, String> getValues() {
			// TODO Auto-generated method stub
			Map<String, String> params = new HashMap<String,String>(0);
			params.put(CmtyWindow.CMTY_DEFAULT_PANEL_ID, panelItems[defaultPanel.getSelectionIndex()]);
			return params;
		}

		@Override
		public void createContent() {
			Composite parent = this;
			ProfileManager p = ProfileManager.getProfile();
			parent.setLayout(new FillLayout(SWT.VERTICAL));
			
			Group group = new Group(parent,SWT.NONE);
			group.setText(title());
			group.setLayout(new GridLayout(2,false));
			
			Label l = new Label(group,SWT.NONE);
			l.setText(I18NUtils.getInstance().getString("DefaultPanel"));
			
			String id = p.getString(getId(), CmtyWindow.CMTY_DEFAULT_PANEL_ID, "");
			
			defaultPanel = new Combo(group, SWT.NONE);
			defaultPanel.setItems(panelItemLabels);
			if(!"".equals(id)) {
				int i = 0;
				for(int index = 0; index < panelItems.length ; index++) {
					if(panelItems[index].equals(id)) {
						i = index;
					}
				}
				defaultPanel.select(i);
			}
			
			Label fileSelect = new Label(group,SWT.NONE);
			fileSelect.setText(I18NUtils.getInstance().getString("FileSelect"));
			fileSelect.setLayoutData(new GridData(300,50));
			
			/*final FileButton fileButton = new FileButton(group, SWT.NONE, new FileListener(){
				@Override
				public void onFile(FileButton fb, FileInfo[] fis) {
					fb.readAsText(fis[0].getName(), new FileDataAdapter(){
						@Override
						public void onData(String fileName, String dataType, String data) {
							fileSelect.setText(data);
						}
					});
					
				}
			});
			
			fileButton.setLayoutData(new GridData(100,50));*/
			
		}
		
	};

}
