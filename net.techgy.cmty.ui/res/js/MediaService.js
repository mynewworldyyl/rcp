
namespace("net.techgy.cmty.ui.im.core")
namespace("techgy.va")

navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia ||
navigator.mozGetUserMedia || navigator.msGetUserMedia;
	        
net.techgy.cmty.ui.im.core.MediaService = function(properties) {
	techgy.va.mediaService = this;
	this.contexts = {};
}

techgy.va.Constans = {
	    OFFER: 'offer',
	    ANSWER: 'answer',
	    CANDIDATE: 'candidate',
	    GET_MEDIA_STATUS: 'mediaStatus',
	    GET_MEDIA_SUCCESS: 'success',
	    GET_MEDIA_FAILURE: 'failure',
	    USING: 'using',
		CAMERA_MODEL_VIDEO : "video",
	    CAMERA_MODEL_AUDIO : "audio",
	    CAMERA_MODEL_SCREEN : "screen",
	    CAMERA_MODEL : "cameraModel",
};

net.techgy.cmty.ui.im.core.MediaService.getInstance = function(properties) {
	if(!net.techgy.cmty.ui.im.core.MediaService.ins_) {
		net.techgy.cmty.ui.im.core.MediaService.ins_ = 
			new net.techgy.cmty.ui.im.core.MediaService(properties)
	}
	return net.techgy.cmty.ui.im.core.MediaService.ins_;
}

net.techgy.cmty.ui.im.core.MediaService.screenConfiguration = {
	    audio: false,
	    video: {
		    mandatory: {
		        chromeMediaSource: 'screen',
		        maxWidth: 1920,
		        maxHeight: 1080,
		        minAspectRatio: 1.77
		    },
		    optional: []
	}
};

 net.techgy.cmty.ui.im.core.MediaService.videoConstraints = {
        video : true,
        audio : true
 };

net.techgy.cmty.ui.im.core.MediaService.audioConstraints = {
        video : false,
        audio : true
 };

net.techgy.cmty.ui.im.core.MediaService.prototype = {
		
		createContext : function(params) {
			if(!params.id) {
    			throw new "id cannot be null";
    		}
    		
    		var id = params.id;
    		
    		if(this.contexts[id]) {
    			throw "context exist: " + id;
    		}
    		
    		this.contexts[id] = {'id' : id};
    		return this.contexts[id];
		},

    	sendMessage : function(msg) {
    	    if(!this.remoteObject) {
    	    	this.remoteObject = rap.getRemoteObject(this);
    	    }
    	    this.remoteObject.notify( "message", msg );
    	 },
    	    
    	 onMessage : function(msg) {
    		if(!msg.id) {
    			throw new "id cannot be null";
    		}
    		var id = msg.id;
    		
    		var ctx = this.contexts[id];
    		if(!ctx.channel) {
    			throw new "Channel is not open";
    		}
    		ctx.channel.onMessage(msg);
    	},
    	
    	createMediaChannel : function(params) {
    		var cxt = this.contexts[params.id];
    		params.localStream = cxt.localStream;
    		params.singnalChannel = this;
    		params.gotStream = techgy.utils.bind(this,function(remoteStream) {
    			cxt.remoteStream = remoteStream;
    			cxt.remoteUrl = window.URL.createObjectURL(remoteStream);
    			
    			if(!this.remoteObject) {
    	    	    this.remoteObject = rap.getRemoteObject(this);
    	    	}
    	    	this.remoteObject.notify( "channelReady", {
    				'id': cxt.id, 
    				'remoteUrl':cxt.remoteUrl,
    				'localUrl': cxt.localUrl,
    			});
    		});
    		
    		cxt.channel = new techgy.va.MediaChannel(params);
    		cxt.channel.start();
    	},
    	
    	destroyContext : function(params) {
    		if(!params.id) {
    			throw new "id cannot be null";
    		}
    		
    		var id = params.id;
    		var cxt = this.contexts[id];
    		if(!cxt) {
    			return;
    		}
    		
    		var localStream = cxt.localStream;
    		if(localStream) {
    			localStream.stop();
    		}
    		
    		var channel = cxt.channel;
    		if(channel) {
    			channel.close();
    		}
    		
    		this.contexts[id] = undefined;
    		
    	},
    	
    	getContext_ : function(id) {
            var cxt = this.contexts[id];
    		if(!cxt) {
    			throw "Context is not init: " + id;
    		}
    		return cxt;
    	},
    	
    	openCamera : function(params) {
        	
    		if(!params.id) {
    			throw new "id cannot be null";
    		}
    		
    		var cxt = this.getContext_(params.id);
    		if(!cxt) {
    			cxt = this.createContext(params);
    		}
    		
    		if(!params.cameraModel){
    			throw "CameraModel cannot be null";
    		}
    		
    		cxt.cameraModel = params.cameraModel;
    		var id = params.id;
        	var cameraModel = params.cameraModel;
        	
        	var constraint = null;
        	if(cameraModel ===  techgy.va.Constans.CAMERA_MODEL_VIDEO) {
        		constraint = net.techgy.cmty.ui.im.core.MediaService.videoConstraints;
        	} else if(cameraModel ===  techgy.va.Constans.CAMERA_MODEL_SCREEN) {
        		constraint = net.techgy.cmty.ui.im.core.MediaService.screenConfiguration;
        	} else if(cameraModel ===  techgy.va.Constans.CAMERA_MODEL_AUDIO) {
        		constraint = net.techgy.cmty.ui.im.core.MediaService.audioConstraints;
        	} else {
        		throw "camera model not support : " + cameraModel;
        	}
        	
        	var succeFunc = techgy.utils.bind(this, function(localStream){
        		var mediaStatu = null;
        		if(!localStream) {
        			mediaStatu = techgy.va.Constans.GET_MEDIA_FAILURE;
        		}else {
        			mediaStatu = techgy.va.Constans.GET_MEDIA_SUCCESS;
        		}
        		
        		cxt.localStream = localStream;
        		cxt.localUrl = window.URL.createObjectURL(localStream);;
        		
                var remoteObject = rap.getRemoteObject(this);
        		remoteObject.notify('mediaStatus',{
        			'statu' : mediaStatu,
        			'id' : id,
        			'localUrl' : cxt.localUrl
        		});
            });
        	
        	var failFunc = techgy.utils.bind(this,function(error) {
            	//本地视频不可用，但还是可以看对方视频
                var mediaStatu = techgy.va.Constans.GET_MEDIA_FAILURE;
                var remoteObject = rap.getRemoteObject(this);
        		remoteObject.notify('mediaStatus',{
        			'statu' : mediaStatu,
        			'id' : id,
        		});
            });
        	
        	navigator.getUserMedia(
        		constraint, 
                succeFunc, 
                failFunc
            );
        
 	    },
 	    
 	    closeCamera : function(params) {
 	    	if(!params.id) {
    			throw new "id cannot be null";
    		}
    		
    		var cxt = this.getContext_(params.id);
    		if(!cxt) {
    			return;
    		}
    		
    		if(cxt.localStream) {
    			cxt.localStream.stop();
        		cxt.localStream = null;
        		cxt.localUrl = null;
    		}
 	    },
    
};


rwt.remote.HandlerRegistry.add( 'net.techgy.cmty.ui.im.core.MediaService', {
	  factory : function( properties ) {
		//创建代表UI的视频显示界面组件
	    return new net.techgy.cmty.ui.im.core.MediaService.getInstance(properties);
	  },

	  listeners : [
	    
	  ],
	  
	  properties: [
	     
	  ],
	  
	  methods : [
	    'createContext',
	    'destroyContext',
	    'openCamera',
	    'closeCamera',
	    'onMessage',
	    'createMediaChannel'
	  ],
      
	  events : [
	     'mediaStatus',
	     'message',
	     'channelReady'
	  ],
	   
	  methodHandler : {
	   
	  }
} );


