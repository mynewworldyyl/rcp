package net.cmty.ui.core.editor;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.widgets.Composite;

import net.techgy.ui.core.CoreUIActivator;
import net.techgy.ui.core.content.IProvider;

public interface IEditorProvider extends IProvider {

	public static final String EDITOR_PROVIDER_ID = CoreUIActivator.PLUGIN_ID +"/"+IEditorProvider.class.getName();
	
	AbstractEditorPart createEditor(Composite parent, IEditorInput input,IEclipseContext context);
}
