/**
 * 代表UI的视频显示界面组件
 * 引组件继承自Composite
 */
rwt.qx.Class.define( "net.techgy.cmty.ui.im.video.Video", {

  extend : rwt.widgets.Composite,

  construct : function(properties) {
    this.base( arguments );
    
    if(!properties.url) {
    	throw 'stream url cannot be null';
    }
    
    var id = properties.parent;
    var parent = rap.getObject(id);
    
    this.videoWin = document.createElement( "video" );
    this.videoWin.controls=true;
    this.videoWin.autoplay = true;
    
    this.setUrl(properties.url);
    
	parent.append(this.videoWin);
  },

  destruct : function() {
   
  },

  members : {
	  
	  setUrl : function(url) {
		  this.videoWin.src=url;
	  },
	  
	  setHeight: function(height) {
		  this.videoWin.style.height=height;
	  },
	  
	  setWidth: function(width) {
		  this.videoWin.style.width=width;
	  }
  }
  
} );


rwt.remote.HandlerRegistry.add( 'net.techgy.cmty.ui.im.video.Video', {
	
	  factory : function( properties ) {
		//创建代表UI的视频显示界面组件
	    return new net.techgy.cmty.ui.im.video.Video(properties);
	  },

	  listeners : [
	    
	  ],
	  
	  properties: [
	      'url',
	      'width',
	      'height'
	  ],
	  
	  methods : [
	  ],
      
	  events : [
	    
	  ],
	   
	  methodHandler : {
	    
	  }
} );
