package net.cmty.ui.core.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.digitnexus.base.excep.CommonException;
/**
 * COLUMN Report Data:
 * 
 *      Column1    Column2 Column3 Column4
 *      Vendor       10       11        9
 *      Contract      9       20       11
 *      Project       8        6        9
 *      Factory      13        9       13
 *      Area         10       15       19
 * 
 * 
 * ROW Report Data:
 * 
 *        Column1    Vendor Contract Project Factory Area
 *        Vendor       10       11        9    3      12
 *        Contract      9       20       11     8     9
 *        Project       8        6        9    23     11
 *        Factory      13        9       13    12     9
 *        Area         10       15       19    16     10
 * 
 * @author ylye
 *
 */
public class ReportBo{
	
	private String labelType =LABEL_TYPE_COLUMN; //LABEL_TYPE_ROW; //;
	
	private Set<ColumnHeader> headers = new TreeSet<ReportBo.ColumnHeader>();
	
	private List<Row> rows = new ArrayList<Row>();
	
	public void addHeader(ColumnHeader h) {
		this.headers.add(h);
	}
	
	public Row getRow(String rowId) {
		for(Row r : this.rows) {
			if(rowId.equals(r.getRowId())) {
				return r;
			}
		}
		return null;
	}
	
	public void addRow(Row row) {
		Row r = this.getRow(row.getRowId());
		if(r != null) {
			throw new CommonException("RowExist", row.getRowId());
		}
        this.rows.add(row);
	}
	
	
	public Iterator<Row> rowIterator() {
		return rows.iterator();
	}
	
	public Iterator<ColumnHeader> columnIterator() {
		return this.headers.iterator();
	}
	
	public String[] getColumnIds() {
		String[] labels = new String[this.headers.size()];
		Iterator<ColumnHeader> ite = this.columnIterator();
		int index = 0;
		while(ite.hasNext()) {
			labels[index] = ite.next().getHeaderId();
			index++;
		}
		return labels;
	}

    public String[] getRowIds() {
    	String[] labels = new String[this.rows.size()];
		Iterator<Row> ite = this.rowIterator();
		int index = 0;
		while(ite.hasNext()) {
			labels[index] = ite.next().getRowId();
			index++;
		}
		return labels;
	}
    
    public float[] getRowValues(String rowId) {
    	Row row = this.getRow(rowId);
    	List<ColumnValue> columnValues = row.getColumnValues();
    	
    	float[] values = new float[columnValues.size()];
    	for(int index = 0; index < columnValues.size(); index++) {
    		values[index] = columnValues.get(index).columnValue;
    	}
		return values;
	}
    
    public float[] getColumnValues(String columnId) {
    	
    	float[] values = new float[this.rows.size()];
    	Iterator<Row> ite = this.rowIterator();
		int index = 0;
		while(ite.hasNext()) {
			ColumnValue cv = ite.next().getColumnValue(columnId);
			if(cv != null) {
				values[index] = cv.columnValue;
			}
			index++;
		}
		return values;
	}
    
	public String getLabelType() {
		return labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	/****************************************************************************/
	public static final String LABEL_TYPE_COLUMN = "column";
	//use header as label
	public static final String LABEL_TYPE_ROW = "header";
	
    public static class ColumnHeader implements Comparable<ColumnHeader>{
    	
    	private Integer order = 0;
    	
		private String headerId;

		public ColumnHeader(String id, int order) {
			this.order = order;
			this.headerId = id;
		}
		
		@Override
		public int hashCode() {
			return headerId.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return headerId.equals(obj);
		}

		@Override
		public String toString() {
			return headerId;
		}

		public Integer getOrder() {
			return order;
		}

		public String getHeaderId() {
			return headerId;
		}
		
		@Override
		public int compareTo(ColumnHeader o) {
			return order.compareTo(o.order);
		}
	}

	public static class ColumnValue implements Comparable<ColumnValue>{
		
		private ColumnHeader header;
		private float columnValue;
		
		public ColumnValue(ColumnHeader header, float columnValue) {
			this.header = header;
			this.columnValue = columnValue;
		}
		
		@Override
		public int compareTo(ColumnValue o) {
			return header.order.compareTo(o.header.order);
		}
		
		public float getColumnValue() {
			return this.columnValue;
		}
		
	}
	
	public static class Row {
		
		private String rowId;
		
		private List<ColumnValue> columnValues = new ArrayList<ColumnValue>();
		
		public Row(String rowId) {
			 this.rowId = rowId;
		}
		
		public void addColumnValue(ColumnValue value) {
			this.columnValues.add(value);
		}
		
		public String getRowId() {
			return this.rowId;
		}

		public List<ColumnValue> getColumnValues() {
			return columnValues;
		}
		
		public ColumnValue getColumnValue(String columnId) {
			for(ColumnValue cv : this.columnValues) {
				if(columnId.equals(cv.header.getHeaderId())) {
					return cv;
				}
			}
			return null;
		}
	}
	
	public static enum ChartType{
		pieChart,
		barChart,
		lineChart,
		barLineChart,
		donutChart,
		funnelChart,
		horizontalBarChart,
		horizontalProgressBars,
		radarChart,
		roseChart,
		scatterChart,
	}
	/****************************************************************************/
}
