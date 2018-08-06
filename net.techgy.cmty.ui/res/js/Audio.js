/**
 * 代表UI的视频显示界面组件
 * 引组件继承自Composite
 */

rwt.qx.Class.define( "net.techgy.cmty.ui.im.audio.Audio", {

  extend : rwt.widgets.Composite,

  construct : function(properties) {
    this.base( arguments );
    //this.initAudio_();
    var parent = rap.getObject( properties.parent );
    this.videoWin = document.createElement( "audio" );
    this.videoWin.controls = "controls"
    this.videoWin.autoplay = true;
    this.videoWin.preload = "auto" 
    	
    //this.videoWin.style.height=30;
    //this.videoWin.style.width=200;
    
    this.videoWin.addEventListener('play',techgy.utils.bind(this,this.onStart_));
    this.videoWin.addEventListener('pause',techgy.utils.bind(this,this.onStop_));
    
    //techgy.va.remotePanel = this.videoWin;
    
	parent.append(this.videoWin);
  },

  destruct : function() {
   
  },

  members : {
	  
	  initAudio_ : function() {
		  if(this.isInit) {
			  return;
		  }
		  this.isInit = true;
		  audiojs.events.ready(function() {
		 	    var as = audiojs.createAll();
		  });
	  },
	  
	  onStop_ : function() {
		  var remoteObject = rap.getRemoteObject(this).notify("onStop");
	  },
	  
      onStart_ : function() {
    	  var remoteObject = rap.getRemoteObject(this).notify("onStart");
	  },
	  
	  setUrl : function(params) {
		  this.videoWin.src=params.url;
	  },
	  
	  setHeight: function(params) {
		  this.videoWin.style.height=params.height;
	  },
	  
	  setWidth: function(params) {
		  this.videoWin.style.width=params.width;
	  }
  }
  
} );


rwt.remote.HandlerRegistry.add( 'net.techgy.cmty.ui.im.audio.Audio', {
	
	  factory : function( properties ) {
		//创建代表UI的视频显示界面组件
	    return new net.techgy.cmty.ui.im.audio.Audio(properties);
	  },

	  listeners : [
	    
	  ],
	  
	  properties: [
	    
	  ],
	  
	  methods : [
         'setUrl',
         'setWidth',
         'setHeight'
	  ],
      
	  events : [
	    'onStop',
	    'onStart',
	  ],
	   
	  methodHandler : {
	    
	  }
} );
