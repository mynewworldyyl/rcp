package net.cmty.ui.core.editor;

import java.util.Map;

import net.cmty.ui.core.i18n.I18NUtils;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;

import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.UIConstants.UIType;
import com.digitnexus.ui.dialogs.TreeSelectDialog;
/**
 * 左边一个名字，右边一个输入框，输入框可以是文本，下拉框，日期，时间等等类型
 * 基于FieldDef生成输入框
 * @author T440
 *
 */
public class NameValueComposite extends Composite {

	private FieldDef def = null;
	
	private Control inputControl = null;
	private String value;
	
	public NameValueComposite(Composite parent,int style,FieldDef def,String initValue) {
		super(parent,style);
		RowLayout l = new RowLayout();
		l.spacing = 0;
		l.center=true;
		l.fill=true;
		l.pack=true;
		l.wrap=true;
		l.type=SWT.HORIZONTAL;
		l.justify=true;
		l.marginTop=0;
		l.marginBottom=1;
		this.setLayout(l);
		this.def =def;
		if(initValue == null) {
			//没有指定初始值，由定义生成默认值
			this.value = this.getDefaultValue();
		}else {
			this.value = initValue;
		}
		if(this.value == null) {
			this.value = "";
		}
		createContent();
	}
	private void createContent() {
		RowData cgd = new RowData();
		//左边的标签
		CLabel label = new CLabel(this, SWT.CENTER);
		label.setAlignment(SWT.CENTER);
		label.setLayoutData(cgd);
		//通过资源文件取得与本地语源相关的标签显示文字
		label.setText(I18NUtils.getInstance().getString(this.def.getLabel()));
		//右边的输入框
		this.createInput(this);
	}
	
	/**
	 * 根据输入值的类型，创建相应的输入框
	 * @param parent
	 */
	protected void createInput(Composite parent){
		RowData cgd = new RowData();		
		UIType uiType = this.def.getUiType();
		if(UIType.Text.equals(uiType)) {
			//文本类型输入框
			Text text = new Text(parent, SWT.BORDER);
			text.setLayoutData(cgd);
			if(def.getLength() > 0) {
				text.setTextLimit(def.getLength());
			}
			text.setText(this.value);
			text.setData(def);
			inputControl = text;
		}else if(UIType.Checkbox.equals(uiType)) {
			//多选框
			Button b = new Button(parent,SWT.CHECK);
			if(this.value != null) {
				b.setSelection(this.value.equals(Boolean.TRUE.toString()));
			}
			inputControl = b;
		}else if(UIType.Email.equals(uiType)) {
			//邮件地址输入框
			Text text = new Text(parent, SWT.BORDER);
			text.setLayoutData(cgd);
			if(def.getLength() > 0) {
				text.setTextLimit(def.getLength());
			}
			text.setText(this.getDefaultValue());
			text.setData(def);
			inputControl = text;
		}else if(UIType.List.equals(uiType)) {
			//代表框
			Combo combo = new Combo( parent, SWT.NONE );
			String[] items = null;
			if(this.def.getAvailables()!= null && this.def.getAvailables().length >0) {
				items = this.def.getAvailables();
			}else if(this.def.getKeyValues() != null && !this.def.getKeyValues().isEmpty()) {
				Object[] arrayVs = this.def.getKeyValues().values().toArray();
				items = new String[arrayVs.length];
				for(int index =0; index < arrayVs.length; index++) {
					items[index] = arrayVs[index].toString();
				}
				combo.setData(this.def.getKeyValues());
			}
			combo.setItems(items);
			//combo.select(0);
			inputControl = combo;
		}else if(UIType.Combo.equals(uiType)) {
			//下拉列表框
			Combo combo = new Combo( parent, SWT.NONE );
			String[] items = null;
			if(this.def.getAvailables()!= null && this.def.getAvailables().length >0) {
				//定义中指定了可用的下拉选值，也就是静态值
				items = this.def.getAvailables();
				combo.setData(items);
				if(this.value != null) {
					//当前选择默认值，Combo以索引选值
					for(int index=0; index < items.length; index++) {
						if(this.value.equals(items[index])) {
							combo.select(index);
							break;
						}
					}
				}
				
			}else if(this.def.getKeyValues() != null && !this.def.getKeyValues().isEmpty()) {
				//键值对下拉风格，显示值和提交值成对出现。
				Object[] arrayVs = this.def.getKeyValues().values().toArray();
				//显示的值列表
				items = new String[arrayVs.length];
				for(int index =0; index < arrayVs.length; index++) {
					items[index] = arrayVs[index].toString();
				}
				//设置显示的值列表，也就是用户可读的文字列表
				combo.setData(this.def.getKeyValues());
				if(this.value != null) {
					//有默认值，值是用户选择的显示值对应的KEY
					String sv = null;
					//查找对应的KEY对应的值，再根据值查找对应的索引
					for(String key: this.def.getKeyValues().keySet()) {
						if(this.value.equals(key)) {
							sv = this.def.getKeyValues().get(key);
							break;
						}
					}
					if(sv != null) {
						//根据值查找对应的索引,设置下拉框对应的索引
						for(int index=0; index < items.length; index++) {
							if(sv.equals(items[index])) {
								combo.select(index);
								break;
							}
						}
					}
				}
			}
			combo.setItems(items);
			//combo.select(0);
			inputControl = combo;
		}else if(UIType.Radiobox.equals(uiType)) {	
			//单选按钮组
			Composite radioGroup = new Composite(parent,SWT.BORDER);
			radioGroup.setLayout(new RowLayout());
			String[] items = null;
			if(this.def.getAvailables()!= null && this.def.getAvailables().length >0) {
				items = this.def.getAvailables();
				for(String i: items) {
					Button b = new Button( radioGroup, SWT.RADIO );
					b.setText(i);
					b.setData(i);
					if(this.value != null) {
						b.setSelection(this.value.equals(Boolean.TRUE.toString()));
					}
				}
			}else if(this.def.getKeyValues() != null && !this.def.getKeyValues().isEmpty()) {
				Map<String, String>  keyValues = this.def.getKeyValues();
				for(Map.Entry<String, String> e: keyValues.entrySet()) {
					Button b = new Button( radioGroup, SWT.RADIO );
					b.setText(e.getValue());
					b.setData(e.getKey());
					if(this.value != null) {
						b.setSelection(this.value.equals(Boolean.TRUE.toString()));
					}
				}
			}
			inputControl = radioGroup;
			
		}else if(UIType.Table.equals(uiType)) {
			//表格显示
			Text text = new Text(parent, SWT.BORDER);
			text.setLayoutData(cgd);
			if(def.getLength() > 0) {
				text.setTextLimit(def.getLength());
			}
			text.setText(this.getDefaultValue());
			text.setData(def);
			inputControl = text;
		}else if(UIType.Tree.equals(uiType)) {
			//树形显示，职部门，职员与部门等数据
			//用户选择后，弹出显示树列表的对话框
			final Button treeButton = new Button(parent,SWT.TOGGLE);			
			treeButton.addSelectionListener(new SelectionListener(){
				@Override
				public void widgetSelected(SelectionEvent e) {
					TreeSelectDialog treeDialog = null;
					if(treeButton.getData() == null) {
						treeDialog = new TreeSelectDialog(treeButton.getShell(), def.getLabel(),def,value);
						treeButton.setData(treeDialog);
					}else {
						treeDialog = (TreeSelectDialog)treeButton.getData();
					}
					int returnCode = treeDialog.open();	    
					if( returnCode == Window.OK ) {
					    	
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			treeButton.setText("Select");
			inputControl = treeButton;
		}else if(UIType.Date.equals(uiType)) {
			//日期控件
			DateTime dateTime = new DateTime(parent, SWT.DATE | SWT.DROP_DOWN |SWT.BORDER);
			inputControl = dateTime;
			/*DateTime dateTime = new DateTime(parent, SWT.CALENDAR|SWT.DROP_DOWN);
			inputControl = dateTime;*/
		}else if(UIType.Time.equals(uiType)) {
			//时间控件
			DateTime dateTime = new DateTime(parent, SWT.TIME | SWT.DROP_DOWN | SWT.BORDER);
			inputControl = dateTime;
		}/*else if(UIType.DateTime.equals(uiType)) {
			DateTime dateTime = new DateTime(parent, SWT.DATE | SWT.DROP_DOWN);
			inputControl = dateTime;
		}*/ else {
			//不支持的控件
			throw new IllegalArgumentException(uiType.name() + " is not support");
		}
	}
	
	/**
	 * 取得当前编辑的结果值
	 * @return
	 */
	public String getValue() {
		UIType uiType = this.def.getUiType();
		if(UIType.Text.equals(uiType)) {
			Text text = (Text)this.inputControl;
			return text.getText();
		}else if(UIType.Checkbox.equals(uiType)) {
			//选择框返回true或false
			Button b = (Button)this.inputControl;
			return b.getSelection()?"true":"false";
		}else if(UIType.Email.equals(uiType)) {
			Text text = (Text)this.inputControl;
			return text.getText();
		}else if(UIType.List.equals(uiType)) {
			Combo combo = (Combo)this.inputControl;
			int selectionIndex = combo.getSelectionIndex();
			if(selectionIndex < 0) {
				return "";
			}
			String value = combo.getItem(selectionIndex);
			Object kv = combo.getData();
			if(kv instanceof Map) {
				Map<String,String> ps = (Map<String,String>)kv;
				for(Map.Entry<String,String> e : ps.entrySet()) {
					if(e.getValue().equals(value)) {
						value = e.getKey();
						break;
					}
				}
			}
			return value;
		}else if(UIType.Combo.equals(uiType)) {
			Combo combo = (Combo)this.inputControl;
			int selectionIndex = combo.getSelectionIndex();
			if(selectionIndex < 0) {
				return "";
			}
			String value = combo.getItem(selectionIndex);
			Object kv = combo.getData();
			if(kv instanceof Map) {
				Map<String,String> ps = (Map<String,String>)kv;
				for(Map.Entry<String,String> e : ps.entrySet()) {
					if(e.getValue().equals(value)) {
						value = e.getKey();
						break;
					}
				}
			}
			return value;
		}else if(UIType.Radiobox.equals(uiType)) {
			Composite radioGroup = (Composite) this.inputControl;
			Control[] controls = radioGroup.getChildren();
			for(Control b : controls) {
				if(((Button)b).getSelection()) {
					return ((Button)b).getData().toString();
				}
			}			
			return "";
		}else if(UIType.Table.equals(uiType)) {
			Text text = (Text)this.inputControl;
			return text.getText();
		}else if(UIType.Tree.equals(uiType)) {
			Button b = (Button) this.inputControl;
			if(b.getData() == null) {
				return "";
			}else {
				TreeSelectDialog td = (TreeSelectDialog)b.getData();
				return td.getValue();
			}
		} else if(UIType.Date.equals(uiType)) {
			DateTime dateTime = (DateTime)inputControl;
			return dateTime.toString();
		}else if(UIType.Time.equals(uiType)) {
			DateTime dateTime = (DateTime)inputControl;
			return dateTime.toString();
		}else {
			throw new IllegalArgumentException(uiType.name() + " is not support");
		}
	}
	
	
	public String getName() {
		return this.def.getFieldName();
	}
	
	/**
	 * 字段名称
	 * @param flag
	 */
	public void setEditable(boolean flag) {
		UIType uiType = this.def.getUiType();
		if(UIType.Radiobox.equals(uiType)) {
			Composite radioGroup = (Composite) this.inputControl;
			Control[] controls = radioGroup.getChildren();
			for(Control b : controls) {
				b.setEnabled(flag);
			}	
		}else if(UIType.Text.equals(uiType)) {
			inputControl.setEnabled(flag);
		}else if(UIType.Checkbox.equals(uiType)) {
			inputControl.setEnabled(flag);
		}else if(UIType.Email.equals(uiType)) {
			inputControl.setEnabled(flag);
		}else if(UIType.List.equals(uiType)) {
			inputControl.setEnabled(flag);
		}else if(UIType.Combo.equals(uiType)) {
			inputControl.setEnabled(flag);	 		
		}else if(UIType.Table.equals(uiType)) {
			inputControl.setEnabled(flag);
		}else if(UIType.Tree.equals(uiType)) {
			inputControl.setEnabled(flag);
		} else if(UIType.Date.equals(uiType)) {
			inputControl.setEnabled(flag);
		}else if(UIType.Time.equals(uiType)) {
			inputControl.setEnabled(flag);
		}else {
			throw new IllegalArgumentException(uiType.name() + " is not support");
		}
	
	}
	
	/**
	 * 默认值
	 * @return
	 */
	private String getDefaultValue() {
		if(def.getDefaultValue() != null && !def.getDefaultValue().isEmpty()) {
			return def.getDefaultValue().values().iterator().next().toString();
		}else {
			return null;
		}
	}
}
