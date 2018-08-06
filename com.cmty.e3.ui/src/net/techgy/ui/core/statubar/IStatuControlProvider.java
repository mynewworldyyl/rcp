package net.techgy.ui.core.statubar;

import java.util.List;

import net.techgy.ui.core.content.IProvider;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public interface IStatuControlProvider extends IProvider {

	public static final String STATU_BAR_ID = IStatuControlProvider.class.getName();
	
	List<Control> createControl(Composite statuBar);
	
}
