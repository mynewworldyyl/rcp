package net.cmty.ui.core.editor;

import org.eclipse.swt.widgets.Composite;

/**
 * 编辑器实现，每个编辑器都有一个输入和编辑器所在添加到的目标栏位。
 * 目标栏位通过panelId指定
 * @author ylye
 *
 */
public abstract class AbstractEditorPart extends Composite{

	private IEditorInput input;
	
	public AbstractEditorPart(Composite parent,int style,IEditorInput input) {
		super(parent,style);
		this.input = input;
	}
	
	public IEditorInput getInput(){
		return input;
	}
	
	public abstract String getPanelId();
}
