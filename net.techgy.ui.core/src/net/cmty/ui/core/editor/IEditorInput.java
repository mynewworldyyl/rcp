package net.cmty.ui.core.editor;

public interface IEditorInput {

	Object getModel();
	
	boolean equals(IEditorInput input);
	
	String getId();
	
	String getTitle();
}
