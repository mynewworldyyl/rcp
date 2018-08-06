
rwt.qx.Class.define( "net.techgy.ui.core.map.OLMap", {

  extend : rwt.widgets.Composite,

  construct : function(properties) {
    this.base( arguments );
    var parent = rap.getObject( properties.parent );
    this.body = document.createElement( "div" );
    this.body.id = properties.parent;
	parent.append(this.body);
	
	this.map = null;
	
	this.rendered = false;
	this.onrender_ = techgy.utils.bind(this,this.onRender_);
	rap.on( "render", techgy.utils.bind(this,this.onrender_));
	
  },

  destruct : function() {
   
  },

  members : {
	  
	  create : function(params) {
		  if(this.rendered) {
			  this.createMap_(params);
		    } else {
		    	setTimeout(techgy.utils.bind(this,function() {
		    		this.create(params);
		    	}),1000);
		    }
	  },
	  
	  createMap_ : function(params) {
		  this.map = new ol.Map({
		        target: this.body,
		        layers: this.createLayers_(params),
		        view: this.createView_(params),
		        controls: this.createControls_(params),
		        interactions: ol.interaction.defaults()
		        .extend([
		             new ol.interaction.Select({
			             style: new ol.style.Style({
				             image: new ol.style.Circle({
					             radius: 5,
					             fill: new ol.style.Fill({
					                      color: '#FF0000'
					                      }),
					             stroke: new ol.style.Stroke({
					                      color: '#000000'
					                      })
				                })
			              })
		            })
		       ]),
		   });
	  },
	  
	  createControls_ : function(params) {
		  var control = ol.control.defaults({
	            attributionOptions: ({
	              collapsible: false
	            })
	       });
		  
		  control.extend([
                      new ol.control.ScaleLine(),
                      new net.techgy.ui.core.map.RotateNorthControl()
                     ]);
		  
		  control.extend([
		              new ol.control.FullScreen()
		  ]);
          return control;
	  },
	  
	  createLayers_ : function(params) {
	    var layers = [];
	    //layers.push(new ol.layer.Tile({source: new ol.source.OSM()}));
	    
	   /* layers.push(new ol.layer.Tile({
	        source: new ol.source.TileWMS({
	            url: 'http://maps.opengeo.org/geowebcache/service/wms',
	            params: {LAYERS: 'openstreetmap', VERSION: '1.1.1'}
	          })
	        }));*/
	    
	   /* layers.push(new ol.layer.Tile({
            title: "Global Imagery",
            source: new ol.source.TileWMS({
              url: 'http://maps.opengeo.org/geowebcache/service/wms',
              params: {LAYERS: 'bluemarble', VERSION: '1.1.1'}
            })
          }));*/
	    
	    /*layers.push(new ol.layer.Vector({
	    	  title: 'Earthquakes',
	    	  source: new ol.source.GeoJSON({
	    	    url: 'data/layers/7day-M2.5.json'
	    	  }),
	    	  style: new ol.style.Style({
	    	    image: new ol.style.Circle({
	    	      radius: 3,
	    	      fill: new ol.style.Fill({color: 'white'})
	    	    })
	    	  })
	    	}));*/
	    
	    layers.push(new ol.layer.Tile({
	        source: new ol.source.BingMaps({
	        	  imagerySet: 'Road',
	        	  key: 'Ak-dzM4wZjSqTlzveKz5u0d4IQ4bRzVI309GxmkgSVr1ewS6iPSrOvOKhA-CJlm3'
	        	})
	       }));
	    
	    layers.push( new ol.layer.Tile({
             source: new ol.source.TileDebug({
             projection: 'EPSG:3857',
             tileGrid: new ol.tilegrid.XYZ({
             maxZoom: 22
            })
          })
        }))
        
        var source = new ol.source.GeoJSON({
		  projection: 'EPSG:3857',
		  url: techgy.utils.getWebHttpPath(
				  '/cmty/rwt-resources/resource/js/ol/data/geojson/switzerland.geojson')
		});
		var style = new ol.style.Style({
		  fill: new ol.style.Fill({
		    color: 'rgba(255, 255, 255, 0.6)'
		  }),
		  stroke: new ol.style.Stroke({
		    color: '#319FD3',
		    width: 1
		  }),
		  image: new ol.style.Circle({
		    radius: 10,
		    fill: new ol.style.Fill({
		      color: 'rgba(255, 255, 255, 0.6)'
		    }),
		    stroke: new ol.style.Stroke({
		      color: '#319FD3',
		      width: 1
		    })
		  })
		});
		var vectorLayer = new ol.layer.Vector({
		  source: source,
		  style: style
		});
		
		layers.push(vectorLayer);

		return layers;
	  },
	  
	  createView_ : function(params) {
		  
		  var zoom = params.zoom;
		  if(!zoom) {
			  zoom = 3;
		  }
		  
		  var loglat = params.loglat;
		  if(!loglat) {
			  loglat=[37.41, 8.82];
		  }
		  var view = new ol.View({
	          center: this.transferLogLat(loglat),
	          zoom: zoom,
	          rotation: 0
	          //projection: 'EPSG:4326',
	          //maxResolution: 0.703125
	       });
		  
		  return view;
	  },
	  
	  onRender_ : function() {
		   if( this.body.parentNode ) {
		       rap.off( "render", this.onrender_ );
		       this.rendered = true;
		       rap.getRemoteObject(this).notify('renderFinished',{
		    	   
		       });
		   }
	   },
	   
	   check_ : function() {
		   if(!this.map) {
			   throw 'Map not init';
		   }
	   },
	   
	   animationRotate_ : function(params) {
		   var duration = params.duration;
			  if(!duration) {
				  duration = 2000;
			  }
			  
			  var rotation = params.rotation;
			  if(!rotation) {
				  rotation = 4 * Math.PI;
			  }
		    var rotateLeft = ol.animation.rotate({
		       duration: duration,
		       rotation: rotation
		     });
		    return rotateLeft;
	   },
	   
	   /**
	    * loglat is an array with length=2, and the [0] is latitude(绾害) 
	    * [1] is longitude(缁忓害)
	    */
	   transferLogLat : function(loglat) {
		   return ol.proj.transform(loglat, 'EPSG:4326', 'EPSG:3857')
	   },
	   
	   elastic_ : function (t) {
		   return Math.pow(2, -10 * t) * Math.sin((t - 0.075) * (2 * Math.PI) / 0.3) + 1;
	   },
	   
	   bounce_ : function (t) {
		   var s = 7.5625, p = 2.75, l;
		   if (t < (1 / p)) {
		     l = s * t * t;
		   } else {
		     if (t < (2 / p)) {
		       t -= (1.5 / p);
		       l = s * t * t + 0.75;
		     } else {
		       if (t < (2.5 / p)) {
		         t -= (2.25 / p);
		         l = s * t * t + 0.9375;
		       } else {
		         t -= (2.625 / p);
		         l = s * t * t + 0.984375;
		       }
		     }
		   }
		   return l;
		 },
	   
	   animationPan_ : function(params) {
		   
		      var cxt = {};
		      
		      cxt.duration = params.duration;
			  if(!cxt.duration) {
				  cxt.duration = 2000;
			  }
			  
			  if(!params.source) {
				  cxt.source = this.map.getView().getCenter();
			  } else {
				  cxt.source = this.transferLogLat(params.source);
			  }
			  
			  if(params.easing == 'elastic') {
				  cxt.easing = this.elastic_
			  }else if(params.easing == 'bounce') {
				  cxt.easing = this.bounce_
			  }

			  return  ol.animation.pan(cxt);;
	   },
	   
	   incZoom : function() {
		   this.check_();
		   var curZoom = this.map.getView().getZoom();
		   if(curZoom == 28) {
			   return;
		   }
		   this.setZoom(curZoom + 1);
	   },
	   
	   decZoom : function() {
		   this.check_();
		   var curZoom = this.map.getView().getZoom() - 1;
		   if(curZoom == 0) {
			   return;
		   }
		   this.setZoom(curZoom - 1);
		   
	   },
	   
	   setZoom : function(zoom) {
		   this.check_();
		   this.map.getView().setZoom(zoom);
	   },
	   
	   setCenter : function(loglat) {
		  this.check_();
		  this.map.getView().setCenter(loglat);
	   },
	   
	   rotate : function(params) {
		   var rotation = this.animationRotate_(params);
		   this.map.beforeRender(rotation);
	   },
	   
	   panTo : function(params) {
		   if(!params.to) {
			   return;
		   }
		   var to = this.transferLogLat(params.to);
		   var pan = this.animationPan_(params);
		   
		   if(params.fly) {
			   
			   var start = +new Date();
			   pan.start = start;
			   
			   var bounce = ol.animation.bounce({
			     duration: pan.duration,
			     resolution: 4 * this.map.getView().getResolution(),
			     start: start
			   });
			   
			   this.map.beforeRender(pan, bounce);
		   }else {
			   this.map.beforeRender(pan);
		   }
		   
		   this.map.getView().setCenter(to);
	   },
	   
	   exportAsPng : function() {
		   this.map.once('postcompose', function(event) {
			      var canvas = event.context.canvas;
			      var url = canvas.toDataURL('image/png');
			      var link = document.createElement('a');
				  link.href = url;// window.URL.createObjectURL(blob);
				  link.download = 'map';
				  link.click();
	       });
		   this.map.renderSync();
	   },
	   
  }
  
});


rwt.remote.HandlerRegistry.add( 'net.techgy.ui.core.map.OLMap', {
	
	  factory : function( properties ) {
	    return new net.techgy.ui.core.map.OLMap(properties);
	  },

	  listeners : [
	    
	  ],
	  
	  properties: [
	       'zoom',
	       'center'
	  ],
	  
	  methods : [
	    'create',
        'incZoom',
        'decZoom',
        'rotate',
        'panTo',
        'exportAsPng',
        
	  ],
      
	  events : [
	    'renderFinished'        
	  ],
	   
	  methodHandler : {
	  }
} );

net.techgy.ui.core.map.RotateNorthControl = function(opt_options) {

	  var options = opt_options || {};

	  var anchor = document.createElement('a');
	  anchor.href = '#rotate-north';
	  anchor.innerHTML = 'N';

	  var this_ = this;
	  var handleRotateNorth = function(e) {
	    // prevent #rotate-north anchor from getting appended to the url
	    e.preventDefault();
	    this_.getMap().getView().setRotation(0);
	  };

	  anchor.addEventListener('click', handleRotateNorth, false);
	  anchor.addEventListener('touchstart', handleRotateNorth, false);

	  var element = document.createElement('div');
	  element.className = 'rotate-north ol-unselectable';
	  element.appendChild(anchor);

	  ol.control.Control.call(this, {
	    element: element,
	    target: options.target
	  });

};
ol.inherits(net.techgy.ui.core.map.RotateNorthControl, ol.control.Control);