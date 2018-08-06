package net.cmty.ui.core.editor;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.excep.CommonException;

import net.cmty.ui.core.i18n.I18NUtils;
import net.techgy.ui.core.CmtyServiceManaer;
import net.techgy.ui.core.CoreUIActivator;
/**
 * 所有编辑器（AbstractEditorPart的子类）的基础容器
 * 提供编辑器提供者ID及编辑器输入，可以打开一个新的编辑器。
 * @author T440
 *
 */
@SuppressWarnings("serial")
public class EditorPartContainer extends Composite{

	public static final String EDITOR_ID=CoreUIActivator.PLUGIN_ID + "/" + EditorPartContainer.class.getName();
	
	private static final String ITEM_ID="__ITEM_ID";
	
	private CTabFolder folder;
	
	@Inject
	private CmtyServiceManaer sm;
	
	//当前打开的编辑器实例列表，KEY为编辑器的ID，Value为编辑器
	private Map<String,AbstractEditorPart> editors = new HashMap<String,AbstractEditorPart>();
	
	public EditorPartContainer(Composite parent,int style) {
		super(parent,style);
		createContent();
	}
	
	/**
	 * 通过编辑器提供者打开编辑器
	 * @param editorProviderId
	 * @param input
	 */
	public void openEditor(String editorProviderId,IEditorInput input) {
		//input的ID为打开的VO的类名
		AbstractEditorPart editor = this.editors.get(input.getId());
		if(editor == null) {
			//编辑器没有打开，通过编辑器提供者创建
			IEditorProvider p = sm.getEditorProvider(editorProviderId);
			if(p == null) {
				throw new CommonException("EditorNotFound", editorProviderId);
			}
			editor = (AbstractEditorPart)p.createEditor(folder, input,null);
			if(editor == null) {
				//创建失败
				throw new CommonException("EditorNotFound", input.getId());
			}
			this.editors.put(input.getId(), editor);
			//创建激活按钮
			createViewItem(input.getId(),editor.getInput().getTitle());
		}
		selectEditor(input.getId());
	}

	private void selectEditor(String editorId) {
		CTabItem[] items = folder.getItems();
		if(items == null || items.length == 0) {
			throw new CommonException("ViewNotFound", editorId);
		}
		for(CTabItem i : items) {
			String vid = (String)i.getData(ITEM_ID);
			if(vid.equals(editorId)) {
				folder.setSelection(i);
				break;
			}
		}
	}

	private void createContent() {
		this.setLayout(new FillLayout());
		folder = new CTabFolder( this, getStyle() );
	    //folder.setLayoutData( new GridData( 300, 300 ) );
	    folder.setSelection( 0 );
	    folder.addSelectionListener( new SelectionAdapter() {
	      @Override
	      public void widgetSelected(SelectionEvent event ) {
	        CTabItem item = ( CTabItem )event.item;	        
	      }
	    });
	}
	
	private void createViewItem(String editorId,String title) {
		AbstractEditorPart editor = this.editors.get(editorId);
		title = I18NUtils.getInstance().getString(title);
		CTabItem item = new CTabItem(folder, SWT.CLOSE);
		item.setText(title);
		item.setControl(editor);
		item.setData(ITEM_ID, editorId);
		/*
		 item.addListener(SWT.CLOSE, new Listener(){
			@Override
			public void handleEvent(Event event) {
				views.remove(viewId);
			}
		 });
		*/
		item.addDisposeListener(new DisposeListener(){
			@Override
			public void widgetDisposed(DisposeEvent event) {
				//关闭编辑器时删除编辑器实例
				editors.remove(editorId);
			}
		});
	}
	

	
}
