
rwt.qx.Class.define( "net.cmty.ui.core.file.FileButton", {

  extend : rwt.widgets.Composite,

  construct : function(properties) {
    this.base( arguments );
    var parent = rap.getObject( properties.parent );
    
    this.fileInput = document.createElement( "input" );
    this.fileInput.type = "file";
    this.fileInput.id = "files";
    this.fileInput.name = "files[]";
    //this.fileInput.accept="image/png, image/jpeg"
    	
    this.fileInput.addEventListener('change', 
    		techgy.utils.bind(this,this.handleFileSelect));
    //this.fileInput.addEventListener('play',techgy.utils.bind(this,this.onStart));
    //this.fileInput.addEventListener('pause',techgy.utils.bind(this,this.onStop));
    
    this.files = null;
    
	parent.append(this.fileInput);
  },

  destruct : function() {
   
  },

  members : {
	  
	  readAsText : function(params) {
		  this.readData_('string', params.fileName);
	  },
	  
	  readAsBinaryString : function(params) {
		  this.readData_('byte', params.fileName);
	  },
	  
	  readAsDataURL : function(params) {
		  this.readData_('url', params.fileName);
	  },
	  
	  readAsArrayBuffer : function(params) {
		  this.readDataAndUpload_(params.fileName,params.submitUrl);
	  },
	  
	  readDataAndUpload_ : function(fileName,url) {
		  if(!fileName) {
			  this.notityError_("FileNameCannotBeNull")
			  return;
		  }
		  
		  if(!this.files || this.files.length <= 0) {
		    	this.notityError_("NoFileSelected")
		    	return;
		  }

		    for (var i = 0, f; f = this.files[i]; i++) {

		      if (!(f.name == fileName)) {
		           continue;
		      }
              url = techgy.utils.getWebHttpPath(url);
              var xhr = new XMLHttpRequest();
    		  xhr.open("POST", url, true);
              //xhr.setRequestHeader("Content-Type", "multipart/form-data; boundary=AaB03x"); 
              xhr.overrideMimeType("apolication/octet-stream");
              xhr.setRequestHeader("Content-Type", f.type); 
    		  xhr.setRequestHeader("fileName", f.name);
    		  xhr.setRequestHeader("dataType", f.type);
    		  xhr.send(f);
    		 
		      /*var reader = new FileReader();
              var loadDataFunc = function(e) {
            	  var data = e.target.result;
            	  var xhr = new XMLHttpRequest();
        		  xhr.open("POST", url, true);
                  //xhr.setRequestHeader("Content-Type", "multipart/form-data; boundary=AaB03x"); 
                  xhr.overrideMimeType("apolication/octet-stream");
                  xhr.setRequestHeader("Content-Type", f.type); 
        		  xhr.setRequestHeader("fileName", f.name);
        		  xhr.setRequestHeader("dataType", f.type);
        		  xhr.send(data);
		        };
		      // Closure to capture the file information.
		      reader.onload =techgy.utils.bind(this,loadDataFunc);
		      reader.readAsArrayBuffer(f);*/
		      break;
		    }   
		 
	  },
	  
	  handleFileSelect:  function (evt) {
		    // FileList object
		    this.files = evt.target.files; 
		    
		    var fs = evt.target.files;
		    
		    var output = [];
		    for (var i = 0, f; f = fs[i]; i++) {
		    	this.files.push(f);
		    	var fdata = {
		    		fileName : escape(f.name),
		    		type: f.type,
		    		size:  f.size,
		    		relativePath: f.webkitRelativePath,
		    		lastModifiedDate : f.lastModifiedDate.toLocaleDateString()
		    	}
		    	output.push(fdata);
		    }
		    
		    if(fs.length > 0) {
		    	rap.getRemoteObject(this).notify("select", {
					  'files' : output
				});
		    }
		    // files is a FileList of File objects. List some properties.
		    /*
		    var output = [];
		    for (var i = 0, f; f = files[i]; i++) {
		    	fileInfos.push(f);
		    	
		      output.push('<li><strong>', escape(f.name), '</strong> (', f.type || 'n/a', ') - ',
		                  f.size, ' bytes, last modified: ',
		                  f.lastModifiedDate ? f.lastModifiedDate.toLocaleDateString() : 'n/a',
		                  '</li>');
		    }
		    document.getElementById('list').innerHTML = '<ul>' + output.join('') + '</ul>';
		    */
	  },
	  
	  notityError_ : function(msg) {
		  rap.getRemoteObject(this).notify("error", {
			  'msg' : msg
		  });
	  },
	  
	  readData_ : function (readDataType, fileName) {
		  
		  if(!fileName) {
			  this.notityError_("FileNameCannotBeNull")
			  return;
		  }
		  
		  if(!this.files || this.files.length <= 0) {
		    	this.notityError_("NoFileSelected")
		    	return;
		  }

		    for (var i = 0, f; f = this.files[i]; i++) {

		      if (!(f.name == fileName)) {
		           continue;
		      }

		      var reader = new FileReader();
              var loadDataFunc = function(e) {
            	  var data = e.target.result;
            	 /* if('buff' == readDataType) {
            		  data = data.slice(0,data.byteLength);
            	  }*/
		          rap.getRemoteObject(this).notify("data", {
		  			  'fileName' : fileName,
		  			  'dataType' : readDataType,
		  			  'data' : data
		  		  })
		        };
		      // Closure to capture the file information.
		      reader.onload =techgy.utils.bind(this,loadDataFunc);

		      switch(readDataType) {
			      case 'string':
			    	  reader.readAsText(f);
			    	  break;
			      case 'byte':
			    	  reader.readAsBinaryString(f);
			    	  break;
			      case 'url':
			    	  reader.readAsDataURL(f);
			    	  break;
			     /* case 'buff':
			    	  reader.readAsArrayBuffer(f);
			    	  break;*/
		      }
		      
		    }
		  },
  }
  
} );

rwt.remote.HandlerRegistry.add( 'net.cmty.ui.core.file.FileButton', {
	  factory : function( properties ) {
		return new net.cmty.ui.core.file.FileButton(properties);
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
	    'readAsText',
	    'readAsBinaryString',
	    'readAsDataURL',
	    'readAsArrayBuffer'
	  ],
      
	  events : [
	     'select',
	     'data',
	     'error'
	  ],
	   
	  methodHandler : {
	   
	  }
} );


