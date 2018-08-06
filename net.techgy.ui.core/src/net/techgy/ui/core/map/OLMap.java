package net.techgy.ui.core.map;

import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("serial")
public class OLMap extends Composite{

	public OLMap(Composite parent, int style) {
		super(parent, style);
	}
//private RemoteObject remoteObject = null;
	
   /* public static final String MAP_ID = CoreUIActivator.PLUGIN_ID + "/" + OLMap.class.getName();

    private boolean rendered = false;
    
	public OLMap(Composite parent, int style) {
		super(parent, style);
		UIUtils.getInstance().requireJs(LIB_PATH, INIT_LOAD_RESES, OLMap.class.getClassLoader());
		//UIUtils.getInstance().registerRes("", CSS, OLMap.class.getClassLoader());
		
		Composite container = this;
		container.setLayout(new FillLayout());
		
		remoteObject = RWT.getUISession().getConnection()
				.createRemoteObject(OLMap.class.getName());
		
		remoteObject.set( "parent", WidgetUtil.getId( container ) );
		
		remoteObject.setHandler(handler);
		
		this.rendered = false;
	}
	
	public void create(double lat, double log, int zoom) {
		JsonObject createParams = new JsonObject();
		JsonArray ja = this.getLogLatJsonArray(log, lat);
		createParams.add("loglat", ja);
		createParams.add("zoom", zoom);
		this.remoteObject.call("create", createParams);
	}
	
	public void create() {
		JsonObject createParams = new JsonObject();
		this.remoteObject.call("create", createParams);
	}
	
	public void setLatlog(double lat, double log) {
		this.remoteObject.set("center", this.getLogLatJsonArray(log, lat));
	}
	
	private JsonArray getLogLatJsonArray(LonLat ll) {
		return this.getLogLatJsonArray(ll.log, ll.lat);
	}
	
	*//**
	    * loglat is an array with length=2, and the [0] is latitude(纬度) 
	    * [1] is longitude(经度)
	    *//*
	private JsonArray getLogLatJsonArray(double log, double lat) {
		JsonArray latlog = new JsonArray();
		latlog.add(log);
		latlog.add(lat);
		return latlog;
	}
	
	public void setlogLat(LonLat ll) {
		this.setLatlog(ll.lat, ll.log);
	}
	
	*//**
	 * 设置到指定级别，zoom必须在允许范围内，默认为0-28
	 * @param zoom
	 *//*
	public void setZoom(int zoom) {
		this.remoteObject.set("zoom", zoom);
	}
	
	*//**
	 * 放大一个级别
	 *//*
	public void incZoom() {
		this.remoteObject.call("incZoom", null);
	}
	
	*//**
	 * 缩小一个级别
	 *//*
	public void decZoom() {
		this.remoteObject.call("decZoom", null);
	}
	
	public void rotate(int duration, double rotation) {
		JsonObject params = new JsonObject();
		params.add("duration", duration);
		params.add("rotation", rotation);
		this.remoteObject.call("rotate", params);
	}
	
	public void panTo(LonLat to, double duration, Map<String,String> ps) {
		JsonObject params = new JsonObject();
		params.add("duration", duration);
		params.add("to", this.getLogLatJsonArray(to));
		if(ps != null && !ps.isEmpty()) {
			for(Map.Entry<String, String> e : ps.entrySet()) {
				params.add(e.getKey(), e.getValue());
			}
		}
		this.remoteObject.call("panTo", params);
	}
	
	public void exportAsPng() {
		this.remoteObject.call("exportAsPng", null);
	}
	
	
	private OperationHandler handler = new AbstractOperationHandler() {

		@Override
		public void handleNotify(String event, JsonObject properties) {
			if(event.equals("renderFinished")) {
				rendered = true;
				renderFinished(properties);
			}
		}
		
	};
	
	protected void renderFinished(JsonObject properties) {
		
	}*/
	
	public static class LonLat {
		public double lat, log;
		
		public LonLat() {
		}
		
		public LonLat( double log,double lat) {
			this.lat = lat;
			this.log = log;
		}
		
	}
	
	public static enum BingMapType {
		Road,
		Aerial,
		AerialWithLabels,
		collinsBart,
		ordnanceSurvey
	}

/**********************load media relatived libs********************************/
	
	public static final String LIB_PATH = "/js/";
	public static final String[] CSS = {"/css/ol.css"};

	private static final String[] INIT_LOAD_RESES = {
		"ol-debug.js",
		"olmap.js"
	};
	
}
