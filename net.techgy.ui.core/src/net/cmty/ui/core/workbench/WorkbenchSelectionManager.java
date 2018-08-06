package net.cmty.ui.core.workbench;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;

public class WorkbenchSelectionManager  implements ISelectionProvider,ISelectionChangedListener{

	private List<ISelectionChangedListener> listeners = new ArrayList<ISelectionChangedListener>();
	
	private ISelection selection = null;
	
	public WorkbenchSelectionManager() {
		
	}
	
	/*private static final WorkbenchSelectionManager instance = new WorkbenchSelectionManager();
	
	public static WorkbenchSelectionManager getInstance() {
		return instance;
	}*/
	
	
	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if(!this.listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public ISelection getSelection() {
		return selection;
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		if(this.listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	@Override
	public void setSelection(ISelection selection) {
		this.selection = selection;
	}
	
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection s = event.getSelection();
		if(s == null || this.selection == s || s.isEmpty() 
				|| s.equals(this.selection)) {
			return;
		}
		
		this.setSelection(s);
		
		if(listeners.isEmpty()) {
			return;
		}
		
		for(ISelectionChangedListener l : this.listeners) {
			l.selectionChanged(event);
		}
	}
	
}
