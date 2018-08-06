package com.digitnexus.ui.tableview;

import java.util.Collection;
import java.util.Iterator;

import net.cmty.ui.core.editor.IEditorInput;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.TableViewerEditorDef;
import com.digitnexus.ui.model.PropertySourceModel;

/**
 * for one request panel
 * @author ylye
 *
 */
public class RequestComposite extends UpdatableTableViewComposite{

	public RequestComposite(Composite parent,int style,TableViewerEditorDef def,
			 IEditorInput input,Object obj) {
		super(parent,style,input,def/*,obj*/);		
	}

	@Override
	protected boolean doQuery(ActionDef ad) {
		//String itemConllectionType = this.def.getItemCollectionType();
		//String itemCls = this.def.getItemFieldCls();
		Collection<?> l = this.getItemsCollection();
		if(l == null) {
			return true;
		}
		Object[] itemsObjs = l.toArray();
		psms.clear();
		for(Object rd : itemsObjs) {
			this.addModel(rd);
		}
		this.viewer.refresh();
		return true;
	}

	private Collection<?> getItemsCollection() {
		// TODO Auto-generated method stub
		return null;
	}

	protected boolean doModify(ActionDef ad) {
		IStructuredSelection selection = (IStructuredSelection)this.viewer.getSelection();
		if(selection == null) {
			return true;
		}
		Object obj = selection.getFirstElement();
		this.startCellEditor(obj);
		return true;
	}
	
	protected boolean doDelete(ActionDef ad) {
		Collection<?> l = this.getItemsCollection();
		if(l == null || l.isEmpty()) {
			return true;
		}
		IStructuredSelection selection = (IStructuredSelection)this.viewer.getSelection();
		if(selection != null && !selection.isEmpty()) {
			Iterator<?> ite = selection.iterator();
			while(ite.hasNext()) {
				Object o = ite.next();
				Object elt = ((PropertySourceModel)o).getModel();
				if(l.contains(elt)) {
					l.remove(elt);
					this.psms.remove(o);
				}
			}
			this.viewer.refresh();
		}
		return true;
	}
	
}
