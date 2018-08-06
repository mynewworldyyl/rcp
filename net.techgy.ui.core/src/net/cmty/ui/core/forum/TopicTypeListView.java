package net.cmty.ui.core.forum;

import java.util.Map;

import net.techgy.cmty.ui.preference.internal.PreferenceWindow.ModelChangedListener;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("serial")
public class TopicTypeListView  extends Composite{

	private TreeViewer viewer;
	
	private ModelChangedListener listener;
	
	public TopicTypeListView(Composite parent,int style){
		super(parent,style);
		this.setLayout(new FillLayout());
		createContent();
	}

	private void createContent() {
		 viewer = new TreeViewer( this, SWT.MULTI | SWT.H_SCROLL 
				 | SWT.V_SCROLL | SWT.FULL_SELECTION );
		 viewer.getTree().setLinesVisible(true);		 
		 
		 viewer.addSelectionChangedListener(new ISelectionChangedListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				selectionChange((Map.Entry<String, String>)((IStructuredSelection)
						event.getSelection()).getFirstElement());
			}
			 
		 });
	}
	
	public void setListener(ModelChangedListener listener) {
		this.listener = listener;
	}

	private void selectionChange(Map.Entry<String, String> entry) {
		if(this.listener == null) {
			return;
		}
		this.listener.selectionChange(entry);
	}
	
		private class ModelListLabelProvider extends LabelProvider  implements ITableLabelProvider{

			public ModelListLabelProvider() {
				
			}
			
			@Override
			public Image getImage(Object element) {
				return null;
			}

			@Override
			public String getText(Object element) {
				return ((Map.Entry<String, String>)element).getValue();
			}

			@Override
			public Image getColumnImage(Object element, int columnIndex) {
				return getImage(element);
			}

			@Override
			public String getColumnText(Object element, int columnIndex) {
				return this.getText(element);
			}
		}

		
		private class ModelListContentProvider implements IStructuredContentProvider, ITreeContentProvider{

			@Override
			public void dispose() {
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}

			@Override
			public Object[] getElements(Object inputElement) {
			   if(inputElement instanceof Map) {
					return ((Map)inputElement).entrySet().toArray();
				}else {
					return new Object[0];
				}	
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				return new Object[0];
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public boolean hasChildren(Object element) {
				return false;
			}

			
		}
}

