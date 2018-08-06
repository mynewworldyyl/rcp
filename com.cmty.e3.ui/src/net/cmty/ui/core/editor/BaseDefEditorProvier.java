package net.cmty.ui.core.editor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.uidef.BaseDef;
import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.uidef.menu.Menu;

import net.techgy.cmty.service.DataDto;
import net.techgy.ui.core.CoreUIActivator;
import net.techgy.ui.core.network.WSConn;
import net.techgy.ui.core.utils.UIUtils;
 /**
  * 以Menu编辑器输入的编辑器，Menu为左边视图的菜单列表。
  * Menu指定要打开的VO定义
  * @author T440
  *
  */
public class BaseDefEditorProvier implements IEditorProvider{

	public static final String COMMON_EDITOR_ID = CoreUIActivator.PLUGIN_ID+"/" + BaseDefEditorProvier.class.getName();
	
	@Override
	public String getId() {
		return COMMON_EDITOR_ID;
	}

	/*@Override
	public String getTargetId() {
		return EditorPartContainer.EDITOR_ID;
	}*/

	@Override
	public AbstractEditorPart createEditor(Composite parent, IEditorInput input,IEclipseContext context) {
		if(input.getModel() == null || !(input.getModel() instanceof Menu)){
			return null;
		}
        
		Map<String,String> params = new HashMap<String,String>();
		//input的ID为打开的VO的类名
		params.put("clsName",input.getId());
		//由类型名请求服务器找到VO的定义
		DataDto resp = WSConn.ins().call(UIConstants.DEF_URL,params);
		if(resp.isSuccess()) {
			if(resp.getObj() == null) {
				throw new NullPointerException("object def is null");
			}
			BaseDef def =(BaseDef) resp.getObj();
			//以VO的定义为基础创建相应的VO编辑器
			CompositeFactoryManager cfm = context.get(CompositeFactoryManager.class);
			return cfm.cretateComposite(parent,def,input);
		} else {
			//请求VO定义失败
			UIUtils.getInstance().showNodeDialog(parent.getShell(),resp.getMsg());
		}
		return null;
	}
}
