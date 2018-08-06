package com.digitnexus.ui.tableview;

import java.lang.reflect.Field;

import net.cmty.ui.core.editor.IEditorInput;
import net.cmty.ui.core.editor.NameValueComposite;

import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.uidef.ActionDef;
import com.digitnexus.base.uidef.TableViewerEditorDef;

/**
 * for one request panel
 * @author ylye
 *
 */
public class DetailRequestComposite extends UpdatableTableViewComposite{

	public DetailRequestComposite(Composite parent,int style,TableViewerEditorDef def
			, IEditorInput input,Object obj) {
		super(parent,style,input,def/*,obj*/);		
	}

	@Override
	protected boolean doQuery(ActionDef ad) {
		/*if(this.model == null) {
			return true;
		}*/
		//String itemConllectionType = this.def.getItemCollectionType();
		//String itemCls = this.def.getItemFieldCls();
		String itemFileName = null;//getTableViewDef().getItemFieldName();
		Object[] itemsObjs = null;
		
		Field f;
		/*try {
			f = this.model.getClass().getDeclaredField(itemFileName);
			f.setAccessible(true);
			Object itemsObj = f.get(this.model);
			Class cls = itemsObj.getClass();
			if(itemsObj instanceof Collection) {
				itemsObjs = ((Collection)itemsObj).toArray();
			}else if(itemsObj instanceof Object[]) {
				itemsObjs = (Object[])itemsObj;
			}
			psms.clear();
			for(Object rd : itemsObjs) {
				this.addModel(rd);
			}
			this.viewer.refresh();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return true;
	}

	@Override
	public void createContent() {
		// TODO Auto-generated method stub
		super.createContent();
		for(NameValueComposite nvc: this.headerInputs) {
			nvc.setEditable(false);
		}
	}
	
	public boolean isView() {
		return true;
	}
}
