
rwt.remote.HandlerRegistry.add( 'net.techgy.cmty.ui.storage.LocalStorage', {
	  factory : function( properties ) {
		
	    return new function() {
	    	 var storage = techgy.utils.localStorage;
	    	this.isSupport = function() {
    			rap.getRemoteObject(this).notify('isSupport',{
    				'isSupport' : storage.isSupport()
    			});
	    	};
	    	
	    	this.setValue = function(key, value) {
	    		techgy.utils.localStorage.set(key,value);
	    		rap.getRemoteObject(this).notify('value',{
	    			'key' : key,
	    			'value' : value
    			});
	    	};
	    	
	    	this.getValue = function(key) {
	    		var value = storage.get(key);
	    		rap.getRemoteObject(this).notify('value',{
	    			'key' : key,
	    			'value' : value
    			});
	    	};
	    	
	    	this.remove = function(key) {
	    		
	    		var value = storage.get(key);
	    		techgy.utils.localStorage.remove(key);
	    		rap.getRemoteObject(this).notify('remove',{
	    			'statu' : true,
	    			'key' : key,
	    			'value' : value
    			});
	    	};
	    	    
	    	this.clear = function() {
	    		storage.clear();
	    		rap.getRemoteObject(this).notify('clear',{
	    			'statu' : true
    			});
	    	};
	    	
	    };
	    
	    
	  },
      
	  statics : {
		  LOCAL_STORAGE:'localStorage',
		  SESSION_STORAGE: 'sessionStorage',
		  SUPPORT: 'support',
	  },
	  
	  listeners : [
	    
	  ],
	  
	  properties: [
	     
	  ],
	  
	  methods : [
	    'isSupport',
	    'setValue',
	    'getValue',
	    'remove',
	    'clear',
	  ],
      
	  events : [
	     'value',
	     'isSupport',
	     'remove',
	     'clear'
	  ],
	   
	  methodHandler : {
	   
	  }
} );


