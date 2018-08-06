package net.techgy.cmty.ui.preference;

import net.techgy.ui.core.content.IProvider;

import org.eclipse.swt.widgets.Composite;

public interface IPreferencePage extends IProvider{

	String title();
	
	PreferencePagePanel createPage(Composite parent);
	
	
}
