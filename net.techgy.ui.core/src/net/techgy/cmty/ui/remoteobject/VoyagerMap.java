package net.techgy.cmty.ui.remoteobject;

public class VoyagerMap {

	public static final String MAP_REMOTE_OBJECT_TYPE = "com.digitnexus.base.remoteobject.VoyagerMap";
	
	/*private RemoteObject remoteOpbject = null;

	public VoyagerMap() {
		this.init();
	}
	
	public void init() {
		Connection conn = RWT.getUISession().getConnection();
		this.remoteOpbject = conn.createRemoteObject(MAP_REMOTE_OBJECT_TYPE);
		this.remoteOpbject.setHandler(new MapOperatorHandler(this));
		JavaScriptModuleLoader jsLoader = RWT.getClient().getService(JavaScriptModuleLoader.class);
		jsLoader.ensureModule(VoyagerJSModule.class);
	}
	
	public void showMap(String title) {
		JsonObject params = new JsonObject();
		params.add("title", title);
		this.remoteOpbject.call("showMap", params);
	}
	
	public void clientCall(String msg) {
		System.out.println("=====================Message from client: " + msg);
	}*/
}
