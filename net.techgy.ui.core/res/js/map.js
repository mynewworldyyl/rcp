
rwt.qx.Class.define( "com.digitnexus.base.remoteobject.VoyagerMap", {

  extend : Object,

  statics : {
    getInstance : function() {
      return rwt.runtime.Singletons.get( com.digitnexus.base.remoteobject.VoyagerMap );
    }
  },

  /**
   * @signature function()
   */
  construct : function() {
	  
  },

  events: {
    "request" : "rwt.event.DataEvent"
  },

  properties :
  {
    timeoutInterval :
    {
      check: "Number",
      init : 100,
      apply : "_applyTimeoutInterval"
    }
  },

  members :
  {
    addToHistory : function( state, newTitle ) {
        
    }
  },

  destruct : function()
  {
	  
  }
  
});


rwt.remote.HandlerRegistry.add( "com.digitnexus.base.remoteobject.VoyagerMap", {
	
	  factory : function( properties ) {
	    return com.digitnexus.base.remoteobject.VoyagerMap.getInstance();
	  },

	  listeners : [
	    
	  ],

	  methods : [
	    "showMap"
	  ],

	  methodHandler : {
	    "showMap" : function( object,value ) {
	      alert(value.title);
              var remoteObject = rwt.remote.RemoteObjectFactory.getRemoteObject(object);
              if(remoteObject) {
                  remoteObject.call('clientCall',{'msg':'Hello Server'});
              }
	    }
	  }

} );
