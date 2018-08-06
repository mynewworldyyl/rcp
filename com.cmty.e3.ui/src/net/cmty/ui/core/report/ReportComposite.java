package net.cmty.ui.core.report;

import java.util.Random;

import net.cmty.ui.core.editor.IEditorInput;
import net.cmty.ui.core.report.ReportBo.ColumnHeader;
import net.cmty.ui.core.report.ReportBo.ColumnValue;
import net.cmty.ui.core.report.ReportBo.Row;
import net.techgy.ui.core.CoreUIActivator;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.digitnexus.base.uidef.TableViewerEditorDef;
import com.digitnexus.ui.tableview.HeaderViewComposite;


@SuppressWarnings("serial")
public class ReportComposite extends HeaderViewComposite{

	public static final String MAP_ID = 
			CoreUIActivator.PLUGIN_ID + "/" + ReportComposite.class.getName();
	
	
	public ReportComposite(Composite parent,int style, IEditorInput input,TableViewerEditorDef def) {
		super( parent, style, def.getQeuryDefs(),def.getActionDefs(),input, def);		
	}
	

	@Override
	protected void createContent() {
		this.setLayout(new FillLayout());
		
		//this.setBackground(this.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		
		ScrolledComposite sc = new ScrolledComposite(this, SWT.V_SCROLL| SWT.H_SCROLL);
		sc.setLayout(new FillLayout());
		
		Composite child = new Composite(sc, SWT.NONE);
		//child.setBackground(this.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
		sc.setContent(child);
		
		int height = 200;
		
		Composite parentCom = child;
		parentCom.setLayout(new GridLayout(3,true));
		
		GridDataFactory gdf = GridDataFactory.fillDefaults()
				.align(SWT.FILL, SWT.FILL)
				.grab(true, true)
				.minSize(SWT.DEFAULT,height)
				;
		
		CommonReport pieChart = new CommonReport(parentCom,SWT.BORDER,
				ReportBo.ChartType.pieChart.name(),getTestReportBo(2,5));
		pieChart.setTitle("Pie Chart");
		gdf.applyTo(pieChart);
		
		CommonReport barChart = new CommonReport(parentCom,SWT.BORDER,
				ReportBo.ChartType.barChart.name(),getTestReportBo(2,5));
		barChart.setTitle("Bar Chart");
		gdf.applyTo(barChart);
		
		CommonReport lineChart = new CommonReport(parentCom,SWT.BORDER,
				ReportBo.ChartType.lineChart.name(),getTestReportBo(1,5));
		lineChart.setTitle("Line Chart");
		gdf.applyTo(lineChart);
		
		CommonReport barLineChart = new CommonReport(parentCom,SWT.BORDER,
				ReportBo.ChartType.barLineChart.name(),getTestReportBo(1,5));
		barLineChart.setTitle("Bar Line Chart");
		gdf.applyTo(barLineChart);
		
		CommonReport donutChart = new CommonReport(parentCom,SWT.BORDER,
				ReportBo.ChartType.donutChart.name(),getTestReportBo(1,5));
		donutChart.setTitle("Donut Chart");
		gdf.applyTo(donutChart);
		
		CommonReport funnelChart = new CommonReport(parentCom,SWT.BORDER,
				ReportBo.ChartType.funnelChart.name(),getTestReportBo(1,5));
		funnelChart.setTitle("Funnel Chart");
		gdf.applyTo(funnelChart);
		
		CommonReport horizontalBarChart = new CommonReport(parentCom,SWT.BORDER,
				ReportBo.ChartType.horizontalBarChart.name(),getTestReportBo(1,5));
		horizontalBarChart.setTitle("Horizontal Bar Chart");
		gdf.applyTo(horizontalBarChart);
		
		CommonReport horizontalProgressBars = new CommonReport(parentCom,SWT.BORDER,
				ReportBo.ChartType.horizontalProgressBars.name(),getTestReportBo(1,5));
		horizontalProgressBars.setTitle("Horizontal Progress Bars");
		gdf.applyTo(horizontalProgressBars);
		
		CommonReport radarChart = new CommonReport(parentCom,SWT.BORDER,
				ReportBo.ChartType.radarChart.name(),getTestReportBo(1,5));
		radarChart.setTitle("Radar Chart");
		gdf.applyTo(radarChart);
		
		CommonReport roseChart = new CommonReport(parentCom,SWT.BORDER,
				ReportBo.ChartType.roseChart.name(),getTestReportBo(1,5));
		roseChart.setTitle("Rose Chart");
		gdf.applyTo(roseChart);
		
		CommonReport scatterChart = new CommonReport(parentCom,SWT.BORDER,
				ReportBo.ChartType.scatterChart.name(),getTestReportBo(1,3));
		scatterChart.setTitle("Scatter Chart");
		gdf.applyTo(scatterChart);
		
		
		GridLayout gl = (GridLayout) parentCom.getLayout();
		int rows = parentCom.getChildren().length / gl.numColumns;
		if(parentCom.getChildren().length % gl.numColumns != 0) {
			rows++;
		}
		int totalHeight = (rows * height) + (rows+ 2) * (gl.marginHeight + gl.verticalSpacing +
				gl.marginBottom+gl.marginTop + 2*pieChart.getBorderWidth());
		
		sc.setMinSize(parentCom.computeSize(SWT.DEFAULT, totalHeight));
		sc.setExpandVertical(true);
		sc.setExpandHorizontal(true);
	}
	
	@Override
	public String getPanelId() {
		return ReportComposite.class.getName();
	}
	
	/**       Column1    Vendor Contract Project Factory Area
     *        AssetType    10       11        9    3      12
     *        Quality      9       20       11     8     9
     *        Model        8        6        9    23     11
     *        Size         13        9       13    12     9
     *        Length       10       15       19    16     10  
	 * @param rowNum
	 * @param columnNum
	 * @return
	 */
	private static final float TEST_DATA[][] = {
			{10, 11, 9, 3, 12},
			{9, 20, 11, 8, 9},
			{8, 6, 9, 23, 11},
			{13, 9, 13, 12, 9},
			{10, 15, 19, 16, 10}
	};
	
	private ReportBo getTestReportBo(int rowNum, int columnNum) {
		ReportBo r = new ReportBo();
		for(int columnId = 0; columnId < TEST_DATA[0].length; columnId++) {
			String hid = null;
			if(columnId == 0) {
				hid = "Vendor";
			}else if(columnId == 1) {
				hid = "Contract";
			}else if(columnId == 2) {
				hid = "Project";
			}else if(columnId == 3) {
				hid = "Factory";
			}else if(columnId == 4) {
				hid = "Area";
			}
			ColumnHeader h = new ColumnHeader(hid, columnId);
			r.addHeader(h);
			for(int rowId = 0; rowId < TEST_DATA.length-1; rowId++) {
				String rh = null;
				if(rowId == 0) {
					rh = "AssetType";
				}else if(rowId == 1) {
					rh = "Quality";
				}else if(rowId == 2) {
					rh = "Model";
				}else if(rowId == 3) {
					rh = "Size";
				}
				Row row = r.getRow(rh);
				if(row == null) {
					row = new Row(rh);
					r.addRow(row);
				}
				
				ColumnValue cv = new ColumnValue(h, TEST_DATA[rowId][columnId]);
				row.addColumnValue(cv);
			}
		}
		return r;
	}
	
	private ReportBo getTestReportBo1(int rowNum, int columnNum) {
		ReportBo r = new ReportBo();
		for(int columnId = 0; columnId < columnNum; columnId++) {
			ColumnHeader h = new ColumnHeader(columnId+"", columnId);
			r.addHeader(h);
			Random ran = new Random(System.currentTimeMillis());
			for(int rowId = 0; rowId < rowNum; rowId++) {
				Row row = r.getRow(rowId+"");
				if(row == null) {
					row = new Row(rowId+"");
					r.addRow(row);
				}
				int v = ran.nextInt();
				if(v < 0) {
					v = -v;
				}
				ColumnValue cv = new ColumnValue(h, v%100);
				row.addColumnValue(cv);
			}
		}
		return r;
	}
	
}
