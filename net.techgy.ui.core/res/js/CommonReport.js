/**
 * 代表UI的视频显示界面组件
 * 引组件继承自Composite
 */

rwt.qx.Class.define( "net.cmty.ui.core.report.CommonReport", {

  extend : rwt.widgets.Composite,

  construct : function(properties) {
    this.base( arguments );
    var parent = rap.getObject( properties.parent );
    this.body = document.createElement( "canvas" );
    this.body.id = properties.parent;
	parent.append(this.body);
	this.onrender_ = techgy.utils.bind(this,this.onRender_);
	rap.on( "render", techgy.utils.bind(this,this.onrender_));
	
	this.chartType = null;
	this.bo = null;
	this.parameters = [];
	
	this.rendered = false;
	
  },

  destruct : function() {
   
  },

  members : {
	  draw : function(bo) {
		    this.check_();
		    
		    if(!bo) {
		    	throw "bo is NULL";
		    }
		    
		    var labels = bo.labels;
		    if(labels == null) {
		        throw "fail to labels from bo";
		    }
		    var datas = bo.datas;
		    if(datas == null) {
		        throw "fail to get data form bo";
		    }
		    this.bo = bo;
		    var context = this.body.getContext('2d');
		    context.clearRect(0,0,this.body.width,this.body.height);
		    this.doDraw_();
		},
		
		doDraw_ : function() {
			if(this.rendered) {
				
    	    	var graph = this.createGraph_();
		        if(graph == null) {
		            throw "fail to create graph";
		        }
		        //this.setChartProperties_(graph);
		        //graph.Set('chart.labels', this.bo.labels);
		        //this.drawChart_(graph);
		    } else {
		    	setTimeout(techgy.utils.bind(this,this.doDraw_),1000);
		    }
	   },
		
		onRender_ : function() {
		     if( this.body.parentNode ) {
		       rap.off( "render", this.onrender_ );
		       this.rendered = true;
		     }
	   },
		
		check_ : function() {
			//this.setBo(this.bo);
			this.setChartType(this.chartType);
		},
		
		createGraph_ : function() {
			this.check_()
			var container = this.body.id;
			var data = this.bo.datas;
			var labels = this.bo.labels;
		    var chartType = this.chartType;          
		    var chart = null;
		    switch(chartType) {
		     case  'pieChart' : 
		    	 chart = new RGraph.Pie({
		                id: container,
		                data: data,
		                options: {
		                    labels: labels
		                }
		            });
		    	 this.setChartProperties_(chart);
		    	 chart.draw();
		       break;
		      case 'lineChart': 
		    	  chart = new RGraph.Line({
		                id: container,
		                data: data,
		                options: {
		                    labels: labels
		                }
		            });
		    	  chart.draw();
		        break;
		      case 'barChart': 
		    	  chart = new RGraph.Bar({
		                id: container,
		                data: data,
		                options: {
		                    labels: labels,
		                }
		            });
		    	  this.setChartProperties_(chart);
		    	  chart.draw();
		       break;
		     case  'barLineChart': 
		    	    var bar = new RGraph.Bar({
		                id: container,
		                data: data,
		                options: {
		                    labels: labels,
		                }
		            });
		    	    var line = new RGraph.Line({
		                id: container,
		                data: data,
		                options: {
		                    colors: ['#052'],
		                    xaxispos: 'center',
		                    ymax: 8,
		                    linewidth: 10,
		                    tickmarks: null
		                }
		            })
		    	 chart = new RGraph.CombinedChart(bar, line);
		    	    this.setChartProperties_(chart);
		    	 chart.draw();
		      break;
		      case 'stackChart': 
		          
		       break;
		      case 'donutChart': 
		       chart = new RGraph.Pie({
	                id: container,
	                data: data,
	                options: {
	                    variant: 'donut',
	                    linewidth: 5,
	                    strokestyle: 'white',
	                    tooltips: labels,
	                    labels: labels,
	                    key: {
	                        self: labels,
	                        shadow: {
	                            self: true,
	                            offsetx: 10,
	                            offsety: 5,
	                            blur: 15,
	                            color: '#ccc'
	                        },
	                        align: 'right'
	                    },
	                    align: 'left',
	                    shadow: {
	                        offsetx: 0,
	                        offsety: 0,
	                        blur: 25
	                    },
	                    centerx: 185
	                }
	            });
			       this.setChartProperties_(chart);
			       chart.draw();
			       break;
		       case 'funnelChart': 
		          chart = new RGraph.Funnel({
		                id: container,
		                data: data,
		                options: {
		                    labels: {
		                        self: labels,
		                        sticks: true,
		                        x: 30
		                    },
		                    gutter: {
		                        left: 10
		                    },
		                    strokestyle: 'rgba(0,0,0,0)',
		                    text: {
		                        boxed: false
		                    },
		                    shadow: {
		                        self: true,
		                        offsetx: 0,
		                        offsety: 0,
		                        blur: 15,
		                        color: 'gray'
		                    }
		                }
		            });
		          this.setChartProperties_(chart);
		          chart.draw();
		       break;
		       case 'horizontalBarChart': 
		          chart = new RGraph.HBar({
		                id: container,
		                data: data,
		                options: {
		                    gutter: {
		                        left: {
		                            autosize: true
		                        }
		                    },
		                    labels: labels
		                }
		            });
		          this.setChartProperties_(chart);
		          chart.draw();
		       break;
		       case 'horizontalProgressBars': 
		    	   chart = new RGraph.HProgress({
		                id: container,
		                min: 10,
		                max: 100,
		                value: 80
		            });
		          this.setChartProperties_(chart);
		          chart.draw();
		       break;
		       case 'radarChart': 
		          chart = new RGraph.Radar({
		                id: container,
		                data: data,
		                options: {
		                    labels: labels
		                }
		            });
		          this.setChartProperties_(chart);
		          chart.draw();
		       break;
		        case 'roseChart': 
		          chart =  new RGraph.Rose({
		                id: container,
		                data: data
		            });
		          this.setChartProperties_(chart);
		          chart.draw();
		       break;
		       case 'scatterChart': 
		         chart =  new RGraph.Rscatter({
		                id: container,
		                data: [[45,36],[156,32],[142,23],[256,59],[123,48]],
		                options: {
		                    xmax: 365,
		                    ymax: 60,
		                    labels: ['Q1','Q2','Q3','Q4'],
		                    ylabels: {
		                        count: 6
		                    },
		                    numyticks: 12,
		                    background: {
		                        hbars: [
		                                [0,45,'rgba(0,255,0,0.2)'],
		                                [45,10,'rgba(255,255,0,0.2)'],
		                                [55,5,'rgba(255,0,0,0.2)']
		                               ]
		                    }
		                }
		            });
		         this.setChartProperties_(chart);
		         chart.draw();
		       break;
		    }
		    return chart;
		},
		
		setChartProperties_  : function(graph) {
		    var parameters = this.parameters;
		    if(!parameters || parameters.length <= 0) {
		        return;
		    }
		    for(var index = 0; index < parameters.length; index++) {
		        var param = parameters[index];
		        if(null == param) {
		            continue;
		        }
		        var key = param.key;
		        var value = param.value;		
		        if(key == null || key == ''||  value == '' ||  value == null ||
		        key.indexOf('chart.') == -1) {
		            continue;
		        }
		        var value = value.toString().trim();
		        var type = param.type;
		        if(type == null || type == "") {
		            continue;
		        }
		        switch(type){
		            case "int":
		            case "integer":
		            case "byte":
		                value = parseInt(value);
		                break;
		            case "double":
		            case "float":
		                value = parseFloat(value);
		                break;
		            case "string":
		                
		                break;
		            case "boolean":
		                value = 'true' === value;
		                break;
		            case "color":
		                value = value;
		                break;
		            case 'array': {
		                value =  value.split(",");
		                 break;
		            }
		             case 'colors': {
		                value =  value.split(",");
		                break;
		            }
		            default: 
		                			
		        }
		        graph.Set(key,value);
		    }	
		},
		
	  addParameters: function(params) {
		  if(!params || !params.params || params.params.length <= 0) {
		     return;
		  }
		  var ps = params.params;
		  for(var index = 0; index < ps.length; index++) {
			  this.parameters.push(ps[index]);
		  }
	  },
	  
      setChartType: function(chartType) {
    	if(!chartType) {
  	        throw "chart type can't be null, fail to create graph!";
  	    }
  	    chartType = chartType.trim();
  	    if('' === chartType) {
  	        throw "chart type can't be null, fail to create graph!";
  	    }
  	    this.chartType = chartType;
	  }
  }
  
} );


rwt.remote.HandlerRegistry.add( 'net.cmty.ui.core.report.CommonReport', {
	
	  factory : function( properties ) {
	    return new net.cmty.ui.core.report.CommonReport(properties);
	  },

	  listeners : [
	    
	  ],
	  
	  properties: [
	    'chartType',
	  ],
	  
	  methods : [
	    'draw',
	    'addParameters'
	  ],
      
	  events : [
	  ],
	   
	  methodHandler : {
	    
	  }
} );
