package net.techgy.cmty.ui.cl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;

import com.digitnexus.base.utils.JsonUtils;

import net.techgy.cl.IConfigableManager;
import net.techgy.cmty.vo.AttributeVo;
import net.techgy.ui.UIConstants;

public class AttributeEditor extends ObjectEditor {
	
	private AttributeVo attr = null;
	
	private Text nameText = null;
	private Button comfirmButton = null;
	private Button cancelButton  = null;
	private Button editButton = null;
	private Text descText = null;
	private Combo dataTypeCombo = null;
	private Text defValueText = null;
	private Text minValueText = null;
	private Text maxValueText = null;
	private Button requiredbutton = null;
	private Text lengthText = null;
	
	public AttributeEditor(Composite parent,int style,AttributeVo attr,ConfigComposite configComposite) {
		super(parent,style,attr,true,configComposite);
		this.attr = attr;
	}
	
	@Override
	public String getKey() {
		return this.attr.getFullName();
	}

	@Override
	public void createToolBar(ToolBar toolbar) {
		  /*
		   ToolItem addItem = new ToolItem(toolbar, SWT.PUSH);
		   //addItem.setText("Add");
		    addItem.setToolTipText("Save AttributeVo");
		   Image icon = ImageUtils.getInstance().getImage(CmtyEntryPoint.class.getResourceAsStream("/img/ti01.png"), 20, 20);
		   addItem.setImage(icon);   
		    addItem.addSelectionListener(new SelectionAdapter(){
				@Override
				public void widgetSelected(SelectionEvent e) {
					
				}
		    });
		    */
	}

	@Override
	public String getTitle() {
		return attr.getNamespace()+"."+attr.getName();
	}

	@Override
	public void createEditorContent(Composite container) {
		Composite mainContent = new Composite(container,SWT.BORDER);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		mainContent.setLayout(gridLayout);
		
		int minimumWidth = 500;
		Composite AttributeVoContainer = new Composite(mainContent,SWT.BORDER);
		GridData headerFlGD = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		headerFlGD.minimumWidth = minimumWidth;
		headerFlGD.minimumHeight = 200;
		AttributeVoContainer.setLayoutData(headerFlGD);
		createAttributeVoHeader(AttributeVoContainer);
		
		Composite AttributeVoValueContainer = new Composite(mainContent,SWT.BORDER);
		headerFlGD = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		headerFlGD.minimumWidth = minimumWidth;
		headerFlGD.minimumHeight = 200;
		AttributeVoValueContainer.setLayoutData(headerFlGD);
		createAttributeVoValues(AttributeVoValueContainer);		
	}

	private void createAttributeVoValues(Composite AttributeVoValueContainer) {

	    FillLayout layout = new FillLayout();
	    layout.marginHeight = 5;
	    layout.marginWidth = 5;
	    AttributeVoValueContainer.setLayout( layout );
	    int style = getStyle();
	    Table table = new Table( AttributeVoValueContainer, style );
	    //table.setData( RWT.MARKUP_ENABLED, new Boolean( false ) );
	    table.addSelectionListener( new SelectionAdapter() {
	      @Override
	      public void widgetSelected( SelectionEvent event ) {
	        //log( "click: " + event.item );
	      }
	      @Override
	      public void widgetDefaultSelected( SelectionEvent event ) {
	        //log( "double-click: " + event.item );
	      }
	    } );
	    if( ( style & SWT.VIRTUAL ) != 0 ) {
	      table.addListener( SWT.SetData, new Listener() {
	        public void handleEvent( Event event ) {
	          final TableItem item = ( TableItem )event.item;
	         
	        }
	      } );
	    }
	    
	    for( int i = 0; i < 3; i++ ) {
	      final TableColumn column = new TableColumn( table, SWT.NONE );
	      column.setText( "Col " + i );
	      column.setToolTipText( "Col " + i );
	     /* if( columnImages ) {
	        column.setImage( columnImage );
	      }*/
	      column.setWidth( i == 0 ? 50 : 100 );
	      column.setMoveable( true );
	      column.addSelectionListener( new SelectionAdapter() {
	        @Override
	        public void widgetSelected( SelectionEvent event ) {
	          Table table = column.getParent();
	          if( table.getSortColumn() == column ) {
	            if( table.getSortDirection() == SWT.UP ) {
	              table.setSortDirection( SWT.DOWN );
	            } else {
	              table.setSortDirection( SWT.UP );
	            }
	          } else {
	            table.setSortDirection( SWT.UP );
	            table.setSortColumn( column );
	          }
	        }
	      } );
	    }
	    for( int i = 0; i < 5; i++ ) {
	    	  TableItem item = new TableItem( table, SWT.NONE );
	    	  item.setText( i, "Item" + i + "-" + i );
			  //updateItem( result );
	    }
	    table.setSelection( 0 );
	    table.setHeaderVisible( true );
	    table.setLinesVisible( true );
	    Menu menu = new Menu( table );
	    MenuItem menuItem = new MenuItem( menu, SWT.NONE );
	    menuItem.setText( "Menu for Table" );
	    table.setMenu( menu );
	}

	private void createAttributeVoHeader(Composite AttributeVoContainer) {
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		AttributeVoContainer.setLayout(gridLayout);
		
		editButton = new Button(AttributeVoContainer,SWT.NONE);
		editButton.setText("Edit");
		GridData gd = new GridData(SWT.CENTER,SWT.CENTER,false,false,1,1);
		editButton.setLayoutData(gd);
		
		comfirmButton  = new Button(AttributeVoContainer,SWT.NONE);
		comfirmButton.setText("Comfirm");
		gd = new GridData(SWT.CENTER,SWT.CENTER,false,false,1,1);
		comfirmButton.setLayoutData(gd);
		comfirmButton.setEnabled(false);
		
		cancelButton  = new Button(AttributeVoContainer,SWT.NONE);
		cancelButton.setText("Cancel");
		gd = new GridData(SWT.CENTER,SWT.CENTER,false,false,1,1);
		cancelButton.setLayoutData(gd);
		cancelButton.setEnabled(false);
		
		//new Label(headerContainer,SWT.NONE);	
		Label nsLabel = new Label(AttributeVoContainer,SWT.NONE);
		nsLabel.setText("Name Space: ");		
		Text nsText = new Text(AttributeVoContainer,SWT.BORDER);
		nsText.setText(attr.getNamespace());
		nsText.setEditable(false);
		gd = new GridData(SWT.FILL,SWT.FILL,false,false,1,1);
		gd.minimumWidth=50;
		nsText.setLayoutData(gd);
		Label nsMsgLabel = new Label(AttributeVoContainer,SWT.NONE);
		nsMsgLabel.setText("");
		nsMsgLabel.setVisible(false);
		
		Label nameLabel = new Label(AttributeVoContainer,SWT.NONE);
		nameLabel.setText("Name: ");		
		nameText = new Text(AttributeVoContainer,SWT.BORDER);
		gd = new GridData(SWT.FILL,SWT.FILL,false,false,1,1);
		nameText.setLayoutData(gd);
		Label nameMsgLabel = new Label(AttributeVoContainer,SWT.NONE);
		nameMsgLabel.setText("");
		nameMsgLabel.setVisible(false);
		
		Label descLabel = new Label(AttributeVoContainer,SWT.NONE);
		descLabel.setText("Desc: ");		
		descText = new Text(AttributeVoContainer,SWT.BORDER);
		gd = new GridData(SWT.FILL,SWT.FILL,false,false,1,1);
		descText.setLayoutData(gd);
		Label descMsgLabel = new Label(AttributeVoContainer,SWT.NONE);
		descMsgLabel.setText("");
		descMsgLabel.setVisible(false);
		
		Label datatypeLabel = new Label(AttributeVoContainer,SWT.NONE);
		datatypeLabel.setText("Data type: ");		
		dataTypeCombo = new Combo( AttributeVoContainer, SWT.NONE);
		gd = new GridData(SWT.FILL,SWT.FILL,false,false,1,1);
		dataTypeCombo.setLayoutData(gd);
		Label dataTypeMsgLabel = new Label(AttributeVoContainer,SWT.NONE);
		dataTypeMsgLabel.setText("");
		dataTypeMsgLabel.setVisible(false);
		dataTypeCombo.setItems(UIConstants.DATA_TYPES);
		
		Label defValueLabel = new Label(AttributeVoContainer,SWT.NONE);
		defValueLabel.setText("Def Value: ");		
		defValueText = new Text(AttributeVoContainer,SWT.BORDER);
		defValueText.setEditable(false);
		gd = new GridData(SWT.FILL,SWT.FILL,false,false,1,1);
		defValueText.setLayoutData(gd);
		Label defValueMsgLabel = new Label(AttributeVoContainer,SWT.NONE);
		defValueMsgLabel.setText("");
		defValueMsgLabel.setVisible(false);
		
		Label minValueLabel = new Label(AttributeVoContainer,SWT.NONE);
		minValueLabel.setText("Min Value: ");		
		minValueText = new Text(AttributeVoContainer,SWT.BORDER);
		gd = new GridData(SWT.FILL,SWT.FILL,false,false,1,1);
		minValueText.setLayoutData(gd);
		Label minValueMsgLabel = new Label(AttributeVoContainer,SWT.NONE);
		minValueMsgLabel.setText("");
		minValueMsgLabel.setVisible(false);
		
		Label maxValueLabel = new Label(AttributeVoContainer,SWT.NONE);
		maxValueLabel.setText("Max Value: ");		
		maxValueText = new Text(AttributeVoContainer,SWT.BORDER);
		gd = new GridData(SWT.FILL,SWT.FILL,false,false,1,1);
		maxValueText.setLayoutData(gd);
		Label maxValueMsgLabel = new Label(AttributeVoContainer,SWT.NONE);
		maxValueMsgLabel.setText("");
		maxValueMsgLabel.setVisible(false);
		
		Label lengthLabel = new Label(AttributeVoContainer,SWT.NONE);
		lengthLabel.setText("Length: ");		
		lengthText = new Text(AttributeVoContainer,SWT.BORDER);
		gd = new GridData(SWT.FILL,SWT.FILL,false,false,1,1);
		lengthText.setLayoutData(gd);
		Label lengthMsgLabel = new Label(AttributeVoContainer,SWT.NONE);
		lengthMsgLabel.setText("");
		lengthMsgLabel.setVisible(false);
		
		Label requiredLabel = new Label(AttributeVoContainer,SWT.NONE);
		requiredLabel.setText("Require: ");		
		requiredbutton = new Button(AttributeVoContainer,SWT.CHECK);
		Label requiredMsgLabel = new Label(AttributeVoContainer,SWT.NONE);
		requiredMsgLabel.setText("");
		requiredMsgLabel.setVisible(false);
		
		editButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				setEditState(true);
			}		
		});
		comfirmButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				setEditState(false);
				setValue();
				save();
				if(attr.getId() == null) {
					configComposite.back();
				}
			}		
		});
		cancelButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				reset();
				if(attr.getId() == null) {
					configComposite.back();
				}
			}	
		});
		reset();		
	}
	
	protected void setValue() {
		attr.setDesc(descText.getText());
		attr.setName(nameText.getText());
		attr.setDataType(UIConstants.DATA_TYPES[dataTypeCombo.getSelectionIndex()]);
		attr.setDefaultValue(this.defValueText.getText());
		attr.setDesc(this.descText.getText());
		attr.setLength(Integer.valueOf(this.lengthText.getText()));
		attr.setMaxValue(Double.valueOf(this.maxValueText.getText()));
		attr.setMinValue(Double.valueOf(this.minValueText.getText()));
		attr.setRequried(this.requiredbutton.getSelection());
	}

	private void setEditState(boolean flag) {
		nameText.setEditable(flag);
		descText.setEditable(flag);
		editButton.setEnabled(!flag);
		cancelButton.setEnabled(flag);
		comfirmButton.setEnabled(flag);	
		dataTypeCombo.setEnabled(flag);
		defValueText.setEditable(flag);
		minValueText.setEditable(flag);
		maxValueText.setEditable(flag);
		requiredbutton.setEnabled(flag);
		lengthText.setEditable(flag);
	}


	@Override
	public void reset() {
		super.reset();
		this.setEditState(attr.getId() == null);
		this.nameText.setText(attr.getNamespace());
		this.descText.setText(attr.getDesc());
        if(attr.getDataType() != null) {
        	int index = 0;
        	for(String v : UIConstants.DATA_TYPES) {
        		if(v.equals(attr.getDataType())) {
        			break;
        		}
        		index++;
        	}
        	this.dataTypeCombo.select(index);
        }
		this.defValueText.setText(attr.getDefaultValue());
		this.minValueText.setText(attr.getMinValue()+"");
		this.maxValueText.setText(attr.getMaxValue()+"");
		this.requiredbutton.setSelection(attr.getRequried());
		this.lengthText.setText(attr.getLength()+"");
	}
	
	@Override
	public void save() {
		/*AttributeVo at = null;
		try {
			at = attr.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		String attrStr = JsonUtils.getInstance().toJson(at,false);
		Map<String,String> params = new HashMap<String,String>();
		params.put("attr", attrStr);
		IConfigableManager cfgService = null;//UIActivator.ins().getCS().getService(IConfigableManager.class);	
		if(attr.getId()<=0) {
			cfgService.create("AttributeVoManager","createAttributeVo",params);
		} else {
			cfgService.update("AttributeVoManager","updateAttributeVo",params);
		}
		this.isChange = false;*/
	}

}
