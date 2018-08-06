                      
rwt.qx.Class.define( "net.techgy.cmty.ui.im.friend.DataChannelPanel", {

  extend : rwt.widgets.Composite,

  construct : function(properties) {
    this.base( arguments );
    var parent = rap.getObject( properties.parent );
    
    this.ID = 1;
    
    this.panel = document.createElement( "div" );
    
    this.isOfferer = properties.offerer;
    this.chunkLength = properties.chunkLength;
    this.timeout = properties.timeout;
	  
    this.panel.style.border='3px dotted #FFF';
    this.panel.style.height='100%';
    this.panel.style.width='100%';
    
    this.files = [];
    
    if(this.isOfferer) {
    	this.dragFunc_ = techgy.utils.bind(this,this.handleDragOver_);
        this.dropFunc_ = techgy.utils.bind(this,this.handleFileSelect_);
        
        this.panel.addEventListener('dragover', this.dragFunc_, false);
    	this.panel.addEventListener('drop', this.dropFunc_, false);
    }
    
    this.fileToChannels = [];
    
    this.receiveFiles = [];
    
	parent.append(this.panel);
  },

  destruct : function() {
	  this.destroy();
  },

  members : {
	  
	  destroy : function(id) {
		  if(this.isOfferer) {
			  this.panel.removeEventListener('dragover',this.dragFunc_);
			  this.panel.removeEventListener('drop', this.dropFunc_);
		  }		  
	  },
	  
	  //offer停止共享某个文件
	  //传输结束或中途出错，消毁数据
	  stop : function(params) {
		  
		var id = params.id;
		//var lm = params.lastModified;
		if(this.isOfferer) {
			
			var delIndex = -1;
			for (var i = 0, f; f = this.files[i]; i++) {
				if(f.id === id) {
					delIndex = i;
				}				
			}
			
			if(delIndex != -1 && delIndex < this.files.length) {
				this.files.splice(delIndex,1);
			}
		}
		
		var i = -1;
		
		if(this.isOfferer) {
			for(var index = 0; index < this.receiveFiles.length; index++ ) {
				  if(this.receiveFiles[index].id === id) {
					  i = index;
					  break;
				  }
			  }
			  
			  if(i != -1) {
		    	  //删除传输文件队列
		    	  this.receiveFiles.slice(i,1);
		      }
		}
		  i = -1;
		  for(var index = 0; index < this.fileToChannels.length; index++) {
			  var m = this.fileToChannels[index];
			  if(id === m.file.id) {
				  i = index;
				  break;
			  }
		  }
		  
		  if(i != -1) {
			  this.fileToChannels[i].channel.close();
	    	  this.fileToChannels.slice(i,1);
	      }
		
	  },
	  
	  handleFileSelect_ : function (evt) {
		    evt.stopPropagation();
		    evt.preventDefault();
            
		    var fs = evt.dataTransfer.files; 
		    
		    var output = [];
		    for (var i = 0, f; f = fs[i]; i++) {
		    	f.id = this.ID++;
		    	this.files.push(f);
		    	var fdata = {
		    		name : f.name,
		    		type: f.type,
		    		size:  f.size,
		    		relativePath: f.webkitRelativePath,
		    		lastModified : f.lastModifiedDate.toLocaleTimeString(),
		    		'id' : f.id
		    	}
		    	output.push(fdata);
		    }
		    
		    if(fs.length > 0) {
		    	rap.getRemoteObject(this).notify("select", {
					  'files' : output
				});
		    }
	  },
	  
	  handleDragOver_ : function (evt) {
		    evt.stopPropagation();
		    evt.preventDefault();
		    evt.dataTransfer.dropEffect = 'copy';
	  },
	  
	  notityError_ : function(msg) {
		  rap.getRemoteObject(this).notify("error", {
			  'msg' : msg
		  });
	  },
	  
	  notifyMessage : function(msg,id) {
		  msg.id = id;
		  rap.getRemoteObject(this).notify("message", msg);
	  },
	  
	  
	  channelReady : function(channel) {
		  // transfer ArrayBuffer
		  channel.binaryType='ArrayBuffer';
		  
		  var cf = null;
		  for(var index = 0; index < this.fileToChannels.length; index++) {
			  if(channel == this.fileToChannels[index].channel) {
				  cf = this.fileToChannels[index];
				  break;
			  }
		  }
		  if(cf == null) {
			  channel.close();
			  return;
		  }
		  
		  if(!this.isOfferer) {
			  //接受方无需读取文件
			  return;
		  }
		  
		  var reader = new FileReader();
	      // Closure to capture the file information.
	      
	      reader.onabort = techgy.utils.bind(this,function(e){
	    	  if(e) {
	    		  console.log(e);
	    	  }
	      });
	      
		  reader.onerror = techgy.utils.bind(this,function(e){
			   if(e) {
		    		  console.log(e);
		    	  }    	  
		    });
			
		  reader.onloadend = techgy.utils.bind(this,function(e){
			   if(e) {
		    		  console.log(e);
		    	} 
		    });
		   
		  reader.onloadstart = techgy.utils.bind(this,function(e){
			   if(e) {
		    		  console.log(e);
		       } 
		    });
			
		  reader.onprogress = techgy.utils.bind(this,function(e){
			   if(e) {
		    		  console.log(e);
		    	} 
		   });
		  
		  var chunkLength = this.chunkLength;
		  var timeout = this.timeout;
		  
		  /*
		  var onReadAsDataURL = techgy.utils.bind(this,function(event, text) {
			    // data object to transmit over data channel
			    var data = {'id': cf.file.id}; 
			    // on first invocation
			    if (event) text = event.target.result; 
			    if (text.length > chunkLength) {
			    	// getting chunk using predefined chunk length
			        data.message = text.slice(0, chunkLength); 
			    } else {
			        data.message = text;
			        data.last = true;
			    }
			    // use JSON.stringify for chrome!
			    //channel.send(data); 
			    channel.send(JSON.stringify(data)); 

			    var remainingDataURL = text.slice(chunkLength);
			    
			    if (remainingDataURL.length){
			    	setTimeout(function () {
				    	// continue transmitting
				    	onReadAsDataURL(null, remainingDataURL); 
				    }, timeout);
			    }
		  });
		  */
		  var onReadAsDataBuffer = techgy.utils.bind(this,function(event, text) {
			    // data object to transmit over data channel
			    var data = {'id': cf.file.id , 'chunkSize' : chunkLength}; 
			    // on first invocation
			    if (event) text = event.target.result; 
			    
			    var arrayBuffer = null;
			    if (text.byteLength > chunkLength) {
			    	// getting chunk using predefined chunk length
			    	arrayBuffer = text.slice(0, chunkLength);
			    } else {
			    	arrayBuffer =  text;
			        data.last = true;
			    }
			    
			    var base64Data = techgy.utils.base64.encode(arrayBuffer);
			    
			    data.message = base64Data;
			    // use JSON.stringify for chrome!
			    //channel.send(data); 
			    channel.send(JSON.stringify(data)); 

			    var remainingDataURL = text.slice(chunkLength);
			    
			    if (remainingDataURL.byteLength>0){
			    	setTimeout(function () {
				    	// continue transmitting
			    		onReadAsDataBuffer(null, remainingDataURL); 
				    }, timeout);
			    }
		  });
		  
		  //reader.onload = onReadAsDataURL;
		  //reader.readAsDataURL(cf.file);
		  
		  reader.onload = onReadAsDataBuffer;
		  reader.readAsArrayBuffer(cf.file);
	  },
	  
	  getChannel_ : function(id) {
		  var cf = null;
		  for(var index = 0; index < this.fileToChannels.length; index++) {
			  var m = this.fileToChannels[index];
			  if(id === m.file.id) {
				  cf = m;
				  break;
			  }
		  }
		  return cf;
	  },
	  
	  getReceiveFile_ : function(id) {
		  var i = -1;
		  for(var index = 0; index < this.receiveFiles.length; index++ ) {
			  var oid = this.receiveFiles[index].id;
			  if( oid == id) {
				  i = index;
				  break;
			  }
		  }
		  if(i != -1) {
			  return this.receiveFiles[index]
		  } else {
			  return null;
		  }
	  },
	  
	  onData1 : function(d) {
		  
		  var data = JSON.parse(d);
		  
		  if(this.isOfferer) {
			  //发送方无需接收文件
			  if(data.progress) {
				  console.log('receive: ' + data.progress);
				  rap.getRemoteObject(this).notify("progress", data);
			  }
			  return;
		  }
		  
		  //var data = d;
		  //this.receiveFiles = [{'id': id, 'datas': [data] }];
		  
		  var cd = this.getReceiveFile_(data.id);
		  if(!cd) {
			  this.notityError_({'msg' : "FileTransferError",'id': data.id,
				  'name': data.name});
			  return;
		  } 
		  
		  cd.progress = cd.progress + data.message.length;
		 
		  //pushing chunks in array
		  cd.datas.push(data.message); 
          var end = false;
		  if (data.last) {
		      this.saveToDisk_( cd.datas.join(''), cd.name);
		      end = true;
		  }
		  var channel = this.getChannel_(cd.id);
		  
		  var pro = {'progress' : cd.progress, 'id' : cd.id,
				  'end' : end, 'size':cd.size};
		  
		  rap.getRemoteObject(this).notify("progress", pro);
		 
		  console.log('send: '+pro.progress);
		  
		  if(channel) {
			 channel.channel.send(JSON.stringify(pro)); 
		  };
		  
		  if(end) {
			  this.stop({'id' : cd.id}); 
		  }
	  },
	  
      onData : function(d) {
		  
		  var data = JSON.parse(d);
		  
		  if(this.isOfferer) {
			  //发送方无需接收文件
			  if(data.progress) {
				  //console.log('Send : ' + data.progress);
				  rap.getRemoteObject(this).notify("progress", data);
			  }
			  if(data.end) {
				  rap.getRemoteObject(this).notify("complete", {
					  'id' : data.id
				  });
				  this.stop(data);
			  }
			  return;
		  }
		  
		  //var data = d;
		  //this.receiveFiles = [{'id': id, 'datas': [data] }];
		  
		  var cd = this.getReceiveFile_(data.id);
		  if(!cd) {
			  this.notityError_({'msg' : "FileTransferError",'id': data.id,
				  'name': data.name});
			  return;
		  }
		  
		  
		  var uint8Data = techgy.utils.base64.decode(data.message);
		  cd.progress = cd.progress + uint8Data.byteLength;
		 
		  //pushing chunks in array
		  cd.datas.push(uint8Data); 
          var end = false;
          
		  if (data.last) {
		      end = true;
		  }
		  
		  var pro = {'progress' : cd.progress, 'id' : cd.id,
				  'end' : end, 'size' : cd.size , 'chunkSize' : data.chunkSize };
		  rap.getRemoteObject(this).notify("progress", pro);
		  //console.log('received: ' + 100*(pro.progress/cd.size) );
		  
		  var channel = this.getChannel_(cd.id);
		  if(channel) {
			 channel.channel.send(JSON.stringify(pro)); 
		  };

		  if(end) {
			  
			  var blob = new Blob(cd.datas, {type: 'application/octet-binary'});
			  var url = URL.createObjectURL(blob);
			  
			  cd.url = url;
			  cd.blob = blob;
			  
			  rap.getRemoteObject(this).notify("complete", {
				  'id' : cd.id,
				  'url' : cd.url,
				  'name' : cd.name,
				  'type' : cd.type
			  });
			  
			  this.stop(cd);
		  }
		 
	  },
	  
	  saveToDisk_ : function(dataUrl,fileName) {
		  
		  var link = document.createElement('a');
		  link.href =dataUrl;// window.URL.createObjectURL(blob);
		  link.download = fileName;
		  link.click();
		  
		  /*
		    var save = document.createElement('a');
		    save.href = dataUrl;
		    save.target = '_blank';
		    save.download = fileName || dataUrl;

		    var event = document.createEvent('Event');
		    event.initEvent('click', true, true);

		    save.dispatchEvent(event);
		    (window.URL || window.webkitURL).revokeObjectURL(save.href);*/
		  
		  /*
		  var save = document.createElement('a');
		    save.href = dataUrl;
		    save.target = '_blank';
		    save.download = fileName || dataUrl;

		    var evt = document.createEvent('MouseEvents');
		    evt.initMouseEvent('click', true, true, window, 1, 0, 0, 0, 
		    		0, false, false, false, false, 0, null);

		    save.dispatchEvent(evt);

		    (window.URL || window.webkitURL).revokeObjectURL(save.href);
		    */
	  },
	  
	  onMessage : function(params) {

		  var cf = this.getChannel_(params.id)
		  
		  if(cf) {
			  cf.channel.onMessage(params)
		  }else {
			  this.notityError_({'msg' : "FileChannelNotInit", 'name': params.name});
		  }
	  },
	  
	  setOfferer : function(value) {
		  this.isOfferer = value;
	  },
	  
	  saveAsFile : function(params) {
		  
		  var cd = this.getReceiveFile_(params.id);
		  var link = document.createElement('a');
		  link.href =cd.url;
		  link.download = cd.name;
		  link.click();
          //this.saveToDisk_(cd.url, cd.name);
	  },
	  
	  setChunkLength : function(value) {
		  this.chunkLength = value;
	  },
	  
	  setTimeout : function(value) {
		  this.timeout = value;
	  },
	  
	  //offerer调用，开始传文件
	  transferFile : function(params) {
		  var name = params.name;
		  var lm = params.lastModified;
		  var size = params.size;
		  var type = params.type;
		  var id = params.id;
		  
		  var file = null;
		  for(var index = 0; index < this.files.length; index++) {
			  var f = this.files[index];
			  if(f && f.id === id) {
				  file = f;
				  break;
			  }
		  }
		  
		  if(!file) {
			  this.notityError_({'msg' : "File Not Fount: " + name});
			  return;
		  }
		  
		  var channel = new techgy.va.OfferDataChannel(this,file.id);
		  
		  this.fileToChannels.push({
			  'channel': channel,
			  'file': file
		  });
		  
		  channel.start();		  
	  },
	  
	  //answer表示开始接收文件
	  startAcceptFile : function(params) {
		  var name = params.name;
		  var lm = params.lastModified;
		  var size = params.size;
		  var type = params.type;
		  var id = params.id;
		  var file = null;
		  var channel = this.getChannel_(id);
		  
		  if(channel) {
			  this.notityError_({'msg' : "FileIsTransfering " + name});
			  return;
		  }
		  
		  var cd = {
				  'name' : name, 
				  'datas' : [], 
				  'file' : params,
				  'progress': 0,
				  'size': size,
				  'id': id,
				  'type': type,
				  };
		  
		  
		  this.receiveFiles.push(cd);
		  
		  var channel = new techgy.va.AnswerDataChannel(this,id);
		  
		  this.fileToChannels.push({
			  'channel': channel,
			  'file': params
		  });		  
		  //channel.start();		  
	  }
	  
  }
  
} );

rwt.remote.HandlerRegistry.add( 'net.techgy.cmty.ui.im.friend.DataChannelPanel', {
	  factory : function( properties ) {
		return new net.techgy.cmty.ui.im.friend.DataChannelPanel(properties);
	  },
      
	  statics : {
		  
	  },
	  
	  listeners : [
	    
	  ],
	  
	  properties: [
	     'offerer',
	     'chunkLength',
	     'timeout'
	  ],
	  
	  methods : [
	    'onMessage',
	    'destroy',
	    'stop',
	    'setOfferer',
	    'setChunkLength',
	    'setTimeout',
	    'transferFile',
	    'startAcceptFile',
        'saveAsFile'
	  ],
      
	  events : [
	     'select',
	     'error',
	     "message",
	     'progress',
	     'complete'
	  ],
	   
	  methodHandler : {
	   
	  }
} );


