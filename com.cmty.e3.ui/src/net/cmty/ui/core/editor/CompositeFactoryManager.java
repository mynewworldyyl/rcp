package net.cmty.ui.core.editor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.uidef.BaseDef;
import com.digitnexus.base.uidef.TableViewerEditorDef;
import com.digitnexus.core.vo.ReportVo;
import com.digitnexus.core.vo.dept.AccountVo;
import com.digitnexus.core.vo.map.MapVo;
import com.digitnexus.ui.map.MapComposite;
import com.digitnexus.ui.tableview.HeaderViewComposite;

import net.cmty.ui.core.report.ReportComposite;
import net.techgy.cmty.ui.editors.AccountComposite;
import net.techgy.cmty.ui.editors.CompositeFactory;

public class CompositeFactoryManager implements ICompositeFactory{

	/*private static final CompositeFactoryManager instance = new CompositeFactoryManager();
	public static CompositeFactoryManager getInstance() {
		return instance;
	}*/
	
	private boolean isInit = false;
	
	private ICompositeFactory defaultFactory = null;
	//以VO的类名为KEY，相应的VO编辑容器为创建工厂
	private Map<String,ICompositeFactory> voToComposite  = new HashMap<String,ICompositeFactory>();
	
	public void registFactory(String voClassName,ICompositeFactory factory) {
		if(voToComposite.containsKey(voClassName)) {
			//VO已经注册
			throw new CommonException("VOExist");
		}
		voToComposite.put(voClassName, factory);
	}
	
	/**
	 * 根据VO类型名创建编辑器的Composite
	 */
	@Override
	public HeaderViewComposite cretateComposite(Composite parent, BaseDef def,IEditorInput input) {
		if(this.voToComposite.isEmpty()) {
			throw new CommonException("SystemError");
		}
		String className = def.getClsName();
		ICompositeFactory factory = voToComposite.get(className);
		if(factory == null) {
			if(defaultFactory == null) {
				throw new CommonException("SystemError");
			}
			factory = defaultFactory;
		}
		return factory.cretateComposite(parent, def,input);
	}

	public void setDefaultFactory(ICompositeFactory defaultFactory) {
		this.defaultFactory = defaultFactory;
	}
	
	
	
	public void init() {
		if(this.isInit) {
			return;
		}
		
		this.setDefaultFactory(new CompositeFactory());
		
		this.isInit = true;
		this.registFactory(AccountVo.class.getName(),
			new ICompositeFactory(){
				@Override
				public HeaderViewComposite cretateComposite(Composite parent,
						BaseDef def, IEditorInput input) {
					TableViewerEditorDef tvd = (TableViewerEditorDef)def;
					if(tvd.getQeuryDefs() != null && !tvd.getQeuryDefs().isEmpty()) {
						Collections.sort(tvd.getQeuryDefs(), CompositeFactory.fieldDefComparator);
					}
					if(tvd.getItemDefs() != null && !tvd.getItemDefs().isEmpty()) {
						Collections.sort(tvd.getItemDefs(), CompositeFactory.fieldDefComparator);
					}
					return new AccountComposite(parent,SWT.NONE,input, tvd);
				}
		});
		
		this.registFactory(MapVo.class.getName(),
			new ICompositeFactory(){
				@Override
				public HeaderViewComposite cretateComposite(Composite parent,BaseDef def, IEditorInput input) {
					TableViewerEditorDef tvd = (TableViewerEditorDef)def;
					if(tvd.getQeuryDefs() != null && !tvd.getQeuryDefs().isEmpty()) {
						Collections.sort(tvd.getQeuryDefs(), CompositeFactory.fieldDefComparator);
					}
					return new MapComposite(parent,SWT.NONE,input,tvd);
				}
		});
		
		this.registFactory(ReportVo.class.getName(),
			new ICompositeFactory(){
				@Override
				public HeaderViewComposite cretateComposite(Composite parent,BaseDef def, IEditorInput input) {
					TableViewerEditorDef tvd = (TableViewerEditorDef)def;
					if(tvd.getQeuryDefs() != null && !tvd.getQeuryDefs().isEmpty()) {
						Collections.sort(tvd.getQeuryDefs(), CompositeFactory.fieldDefComparator);
					}
					return new ReportComposite(parent,SWT.V_SCROLL,input,tvd);
				}
		});
	}
}
