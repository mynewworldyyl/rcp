package net.cmty.ui.core.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.digitnexus.base.excep.CommonException;

import net.cmty.ui.core.i18n.I18NUtils;
import net.cmty.ui.core.report.ReportBo.ColumnHeader;
import net.cmty.ui.core.report.ReportBo.ColumnValue;
import net.cmty.ui.core.report.ReportBo.Row;
import net.techgy.ui.core.utils.FormDataBuilder;
import net.techgy.ui.core.utils.UIUtils;

@SuppressWarnings("serial")
public class CommonReport extends Composite{

   //private RemoteObject remoteObject = null;
	
	private Composite titleBar;
	
	private Label titleLabel ;
	
	private ReportBo report;
	
	private String chartType;
	
	private List<String> selectKeys = new ArrayList<String>();
	
	public CommonReport(Composite parent, int style, String chartType, ReportBo report) {
		super(parent, style);
		UIUtils.getInstance().requireJs(LIB_PATH, INIT_LOAD_RESES, CommonReport.class.getClassLoader());
		UIUtils.getInstance().requireJs("", COMMON_REPORT, CommonReport.class.getClassLoader());
		
		this.chartType = chartType;
		this.report = report;
		Composite container = this;
		
		String[] keys = getDataAdapter().getValueKeys(report);
		if(keys != null && keys.length > 0) {
			this.selectKeys.add(keys[0]);
		}
		
		FormLayout fl = new FormLayout();
		this.setLayout(fl);
		installTitleBar(this);
		container = new Composite(this,SWT.NONE);
		FormDataBuilder.builder()
		.left(0, 100, 0)
		.top(this.titleBar, 0, SWT.BOTTOM)
		.right(100, 100, 0)
		.bottom(100, 100, 0)
		.apply(container);
		
		container.setLayout(new FillLayout());
		
		/*remoteObject = RWT.getUISession().getConnection()
				.createRemoteObject(CommonReport.class.getName());
		
		remoteObject.set( "parent", WidgetUtil.getId( container ) );
		
		remoteObject.setHandler(handler);*/
		
		this.setChartType(this.chartType);
		this.setReport(this.report);
	}
	
	private boolean needToolBar() {
		String[] keys = getDataAdapter().getValueKeys(report);
		return keys != null && keys.length > 1;
	}

	private void installTitleBar(Composite tc) {
		//this.titleBar = new ToolBar(tc, SWT.FLAT|SWT.WRAP|SWT.RIGHT |SWT.BORDER);
		
		this.titleBar = new Composite(tc,SWT.NONE);
		titleBar.setLayout(new FormLayout());
		//titleBar.setBackground(this.getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND));
		this.titleBar.setBackgroundMode(SWT.INHERIT_FORCE);
		 FormDataBuilder.builder(SWT.DEFAULT,30)
		.left(0, 100, 0)
		.top(0,100,0)
		.right(100, 100, 0)
		.apply(titleBar);
		
		 titleLabel = new Label(this.titleBar, SWT.NONE);
		 //titleLabel.setForeground(this.getDisplay().getSystemColor(SWT.COLOR_TITLE_FOREGROUND));
		  FormDataBuilder.builder()
			.left(0, 100, 5)
			.top(0,100,5)
			.bottom(100, 100, 0)
			.apply(titleLabel);
		 
		//this.getDisplay().getSystemImage()
		
		//final ToolItem ddi = new ToolItem(toolBar, SWT.DROP_DOWN);
		 
		  if(this.needToolBar()) {
			    Menu menu = new Menu(this.getShell(), SWT.POP_UP );
			  
				//Button ddi = new Button(titleBar,SWT.PUSH);
				Link ddi = new Link(titleBar,SWT.NONE);
			    //ddi.setForeground(this.getDisplay().getSystemColor(SWT.COLOR_TITLE_FOREGROUND));
			    //ddi.setData(RWT.MARKUP_ENABLED,true);
				//style='font-style:italic; font-size:20px' 
			    ddi.setText("<a>" + I18NUtils.getInstance().getString("Menu")+"</a>");
			    ddi.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						Rectangle bounds = ddi.getBounds();  
						Point point = CommonReport.this.toDisplay(bounds.x, bounds.y + bounds.height);  
						//Menu m = (Menu) ddi.getData(DROP_DOWN_MENU_KEY);
						menu.setLocation(point); 
						menu.setVisible(true);
					}
				});
			    FormDataBuilder.builder()
				.right(100, 100, -5)
				.top(0,100,5)
				.bottom(100, 100, 0)
				.apply(ddi);
		        
			   String[] keys = getDataAdapter().getValueKeys(report);
			   
			   boolean isMulti = this.isMultipleDim();
			   int style = isMulti ? SWT.CHECK : SWT.RADIO;
			   for(String key : keys) {
				    MenuItem mi = new MenuItem(menu, style);
				    mi.setData("valueKey", key);
					mi.setText(I18NUtils.getInstance().getString(key));
					if(this.selectKeys.contains(key)) {
						mi.setSelection(true);
					}
					mi.addSelectionListener(new SelectionAdapter(){
						@Override
						public void widgetSelected(SelectionEvent e) {
							MenuItem item = (MenuItem)e.widget;
							String k = (String)item.getData("valueKey");
							if(item.getSelection()) {
								if(!isMulti) {
									selectKeys.clear();
									MenuItem[] mis = menu.getItems();
									for(MenuItem mi : mis) {
										String kk = (String)mi.getData("valueKey");
										if(mi.getSelection() && !kk.equals(k)) {
											mi.setSelection(false);
										}
									}
								}
								selectKeys.add(k);
							} else {
								selectKeys.remove(k);
							}
							refleshData();
						}
			         });
			   }
			}
		
	}

	private boolean isMultipleDim() {
		String ct = this.chartType;
		if(ReportBo.ChartType.barChart.name().equals(ct) 
		   ||ReportBo.ChartType.barLineChart.name().equals(ct) 
		   ||ReportBo.ChartType.lineChart.name().equals(ct)
		   ||ReportBo.ChartType.scatterChart.name().equals(ct)
		   ) {
			return true;
		}else {
			return false;
		}
	}

	public void addParameters(Set<Parameter> params) {
		if(params == null || params.isEmpty()) {
			return;
		}
		
		/*JsonArray ja = new JsonArray();
		for(Parameter p : params) {
			JsonObject jo = new JsonObject();
			jo.add("type", p.type);
			jo.add("key", p.key);
			jo.add("value", p.value);
			ja.add(jo);
		}
		
		JsonObject p = new JsonObject();
		p.add("params", ja);
		remoteObject.call("addParameters", p);*/
	}
	
	private IReportDataAdapter getDataAdapter() {
		IReportDataAdapter adapter = dataAdapter.get(this.chartType);
		if(adapter == null) {
			throw new CommonException("DataAdapterNotExist", this.chartType);
		}
		return adapter;
	}
	
	private void setChartType(String chartType) {
		this.chartType = chartType;
		//remoteObject.set("chartType", chartType);;
	}
	
	private void setReport(ReportBo report) {
		this.report = report;
		this.refleshData();
	}
	
	private void refleshData() {
		if(this.selectKeys.isEmpty()) {
			return;
		}
		
		String[] labels = getDataAdapter().getLabels(report);
		
		List<float[]> values = getDataAdapter().getValues(this.report,this.selectKeys);
		if(values == null || values.isEmpty()) {
			return;
		}
		
        /*JsonObject jo = new JsonObject();
		
		JsonArray ls = new JsonArray();
		for(String p : labels) {
			ls.add(p);
		}
		
		JsonArray allValues = new JsonArray();
		if(this.isMultipleDim()) {
			for(float[] vs : values) {
				JsonArray lv = new JsonArray();
				allValues.add(lv);
				for(float v : vs) {
					lv.add(v);
				}
			}
		} else {
			for(float v : values.get(0)) {
				allValues.add(v);
			}
		}
		
		jo.add("labels", ls);
		jo.add("datas", allValues);
		remoteObject.call("draw", jo);*/
	}

	public void setTitle(String title) {
		this.titleLabel.setText(title);
	}
	
	/*private OperationHandler handler = new AbstractOperationHandler() {

		@Override
		public void handleNotify(String event, JsonObject properties) {
			Event e = new Event();
			e.data=properties;
			e.widget=CommonReport.this;
		}
		
	};*/
	
public static class Parameter {
		
		public String type, key, value;
		
		public Parameter(String type, String key, String value) {
			this.type = type;
			this.key = key;
			this.value = value;
		}
}

private static final Map<String,IReportDataAdapter> dataAdapter = new HashMap<String,IReportDataAdapter>();

private static interface IReportDataAdapter {
	
	String[] getLabels(ReportBo bo);
	
	String[] getValueKeys(ReportBo bo);
	
	List<float[]> getValues(ReportBo bo, List<String> keys);
}

private static abstract class AbstractReportDataAdapter implements IReportDataAdapter{
	public String[] getLabels(ReportBo bo) {
    	if(bo.getLabelType().equals(ReportBo.LABEL_TYPE_ROW)) {
    		return bo.getRowIds();
    	}else if(bo.getLabelType().equals(ReportBo.LABEL_TYPE_COLUMN)) {
    		return bo.getColumnIds();
    	}else {
    		throw new CommonException("NotSupportLabelType",bo.getLabelType());
    	}
	}
    
    //选择条件,作为工具栏下拉列表值
    public String[] getValueKeys(ReportBo bo) {
    	
    	String[] keys = null;
    	//如果是Column为label,则是Row为选择条件,反之则以Column为选择条件
    	if(bo.getLabelType().equals(ReportBo.LABEL_TYPE_ROW)) {
    		keys = bo.getColumnIds() ;
    	}else if(bo.getLabelType().equals(ReportBo.LABEL_TYPE_COLUMN)) {
    		keys = bo.getRowIds();
    	}else {
    		throw new CommonException("NotSupportLabelType",bo.getLabelType());
    	}
	    return keys;
	}
}

static {
	
	IReportDataAdapter LineDimDataAdapter = new AbstractReportDataAdapter(){

		@Override
		public List<float[]> getValues(ReportBo bo, List<String> keys) {
			if(keys == null || keys.isEmpty()) {
				return Collections.emptyList();
			}
			List<float[]> values = new ArrayList<float[]>();
	    	if(bo.getLabelType().equals(ReportBo.LABEL_TYPE_ROW)) {
	    		for(String colId : keys) {
	    			values.add(bo.getColumnValues(colId));
	    		}
	    	}else if(bo.getLabelType().equals(ReportBo.LABEL_TYPE_COLUMN)) {
	    		for(String rowId : keys) {
	    			values.add(bo.getRowValues(rowId));
	    		}
	    	}
	    	return values;
		}
		
	};
	
	dataAdapter.put(ReportBo.ChartType.lineChart.name(), LineDimDataAdapter);
	
	IReportDataAdapter singleDimDataAdapter = new AbstractReportDataAdapter(){

		@Override
		public List<float[]> getValues(ReportBo bo, List<String> keys) {
			if(keys == null || keys.isEmpty()) {
				return Collections.emptyList();
			}
			List<float[]> values = new ArrayList<float[]>();
	    	
	    	if(bo.getLabelType().equals(ReportBo.LABEL_TYPE_ROW)) {
	    		values.add(bo.getColumnValues(keys.get(0)));
	    	}else if(bo.getLabelType().equals(ReportBo.LABEL_TYPE_COLUMN)) {
	    		values.add(bo.getRowValues(keys.get(0)));
	    	}
	    	return values;
		}
		
	};
	
	dataAdapter.put(ReportBo.ChartType.donutChart.name(), singleDimDataAdapter);
	dataAdapter.put(ReportBo.ChartType.funnelChart.name(), singleDimDataAdapter);
	dataAdapter.put(ReportBo.ChartType.horizontalBarChart.name(), singleDimDataAdapter);
	dataAdapter.put(ReportBo.ChartType.horizontalProgressBars.name(), singleDimDataAdapter);
	dataAdapter.put(ReportBo.ChartType.pieChart.name(), singleDimDataAdapter);
	dataAdapter.put(ReportBo.ChartType.radarChart.name(), singleDimDataAdapter);
	dataAdapter.put(ReportBo.ChartType.roseChart.name(), singleDimDataAdapter);
	
	IReportDataAdapter multipleDimDataAdapter = new AbstractReportDataAdapter(){
		
		    /**
		     *依据选择条件，选择值
		     */
		    public List<float[]> getValues(ReportBo bo, List<String> keys) {
		    	
		    	List<float[]> values = new ArrayList<float[]>();
		    	
		    	if(bo.getLabelType().equals(ReportBo.LABEL_TYPE_ROW)) {
		    		
		    		Iterator<Row> ite = bo.rowIterator();
		    		int columnSize = keys.size();
		    		while(ite.hasNext()) {
		    			Row row = ite.next();
		    			float[] vs = new float[columnSize];
		    			int index = 0;
		    			for(String colId : keys) {
		    				ColumnValue cv = row.getColumnValue(colId);
		        			if(cv != null) {
		        				vs[index] = cv.getColumnValue();
		        			}
		        			index++;
		        		}
		    			values.add(vs);
		    		}
		    		
		    	}else if(bo.getLabelType().equals(ReportBo.LABEL_TYPE_COLUMN)) {
		    		
		    		Iterator<ColumnHeader> ite = bo.columnIterator();
		    		int rowSize = keys.size();
		    		while(ite.hasNext()) {
		    			ColumnHeader col = ite.next();
		    			float[] vs = new float[rowSize];
		    			int index = 0;
		    			for(String rowId : keys) {
		    				Row row = bo.getRow(rowId);
		    				ColumnValue cv = row.getColumnValue(col.getHeaderId());
		        			if(cv != null) {
		        				vs[index] = cv.getColumnValue();
		        			}
		        			index++;
		        		}
		    			values.add(vs);
		    		}
		    		
		    	}else {
		    		throw new CommonException("NotSupportLabelType",bo.getLabelType());
		    	}
			    return values;
			}
		
	};

	dataAdapter.put(ReportBo.ChartType.barChart.name(), multipleDimDataAdapter);
	dataAdapter.put(ReportBo.ChartType.barLineChart.name(), multipleDimDataAdapter);
	dataAdapter.put(ReportBo.ChartType.scatterChart.name(), multipleDimDataAdapter);
}


/**********************load media relatived libs********************************/
	
	public static final String LIB_PATH = "/js/rgraph/";

	public static final String COMMON_REPORT = "/js/CommonReport.js";
	
	private static final String[] INIT_LOAD_RESES = {
		"financial-data.js",
		"RGraph.bar.js",
		"RGraph.bipolar.js",
		"RGraph.common.annotate.js",
		"RGraph.common.context.js",
		"RGraph.common.core.js",
		"RGraph.common.csv.js",
		"RGraph.common.deprecated.js",
		"RGraph.common.dynamic.js",
		"RGraph.common.effects.js",
		"RGraph.common.key.js",
		"RGraph.common.resizing.js",
		"RGraph.common.tooltips.js",
		"RGraph.common.zoom.js",
		"RGraph.cornergauge.js",
		"RGraph.drawing.background.js",
		"RGraph.drawing.circle.js",
		"RGraph.drawing.image.js",
		"RGraph.drawing.marker1.js",
		"RGraph.drawing.marker2.js",
		"RGraph.drawing.poly.js",
		"RGraph.drawing.rect.js",
		"RGraph.drawing.text.js",
		"RGraph.drawing.xaxis.js",
		"RGraph.drawing.yaxis.js",
		"RGraph.fuel.js",
		"RGraph.funnel.js",
		"RGraph.gantt.js",
		"RGraph.gauge.js",
		"RGraph.hbar.js",
		"RGraph.hprogress.js",
		"RGraph.line.js",
		"RGraph.meter.js",
		"RGraph.modaldialog.js",
		"RGraph.odo.js",
		"RGraph.pie.js",
		"RGraph.radar.js",
		"RGraph.rose.js",
		"RGraph.rscatter.js",
		"RGraph.scatter.js",
		"RGraph.thermometer.js",
		"RGraph.vprogress.js",
		"RGraph.waterfall.js",
	};
	
}
