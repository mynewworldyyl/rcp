
rwt.qx.Class.define( "net.cmty.ui.core.html.form.HtmlInput", {

  extend : rwt.widgets.Composite,

  construct : function(properties) {
    this.base( arguments );
    var parent = rap.getObject( properties.parent );
    if(!properties.type) {
    	throw 'input type cannot be null';
    }
    
    this.formFactory = net.cmty.ui.core.html.form.formFactory;
    
    this.input = document.createElement( "input" );
    
    this.input.type = properties.type;
	
    if(properties.id) {
    	this.input.id = properties.id;
    }
    
    if(properties.name) {
    	this.input.name = properties.name;
    }
    
    parent.append(this.input);
	
	this.rendered = false;
	this.onrender_ = techgy.utils.bind(this,this.onRender_);
	rap.on( "render", this.onrender_);
	
  },

  destruct : function() {
   
  },

  members : {
	  
	  onRender_ : function() {
		  this.rendered = true;
		  rap.getRemoteObject(this).notify('renderFinished',{
	  			'rendered' : true
			});
	  },
	  
	  onValueChanged_ : function(event) {
		  rap.getRemoteObject(this).notify('valueChanged',{
  			'value' : this.getValue_()
			});
	  },
	  
	  getValue_ : function() {
		  return this.input.value;
	  },
	  
	  setValue : function(value) {
		  this.input.value = value;
	  },
	   
  }
  
});


rwt.remote.HandlerRegistry.add( 'net.cmty.ui.core.html.form.HtmlInput', {
	
	  factory : function( properties ) {
	    return new net.cmty.ui.core.html.form.HtmlInput(properties);
	  },

	  listeners : [
	    
	  ],
	  
	  properties: [
	       'value',
	  ],
	  
	  methods : [
	    'create',
	  ],
      
	  events : [
	    'renderFinished',
	    'valueChanged'
	  ],
	   
	  methodHandler : {
	    
	  }
} );


if(net.cmty.ui.core.html.form.formFactory == null 
		|| net.cmty.ui.core.html.form.formFactory == 'undefined') {
    net.cmty.ui.core.html.form.FormFactory_ = function() {
         
    }
    net.cmty.ui.core.html.form.FormFactory_.prototype.createTextbox =  function(name,value) {
        return this.createInput(name,value,'text');  
    }
    net.cmty.ui.core.html.form.FormFactory_.prototype.createPassword = function(name,value) {     
        return this.createInput(name,value,'password');  
    }
    net.cmty.ui.core.html.form.FormFactory_.prototype.createHidden = function(name,value) {     
        return this.createInput(name,value,'hidden');  
    }
    net.cmty.ui.core.html.form.FormFactory_.prototype.createFile = function(name,value) {     
        return this.createInput(name,value,'file');  
    }
          
    net.cmty.ui.core.html.form.FormFactory_.prototype.createSubmit= function(name,value) {     
        return this.createInput(name,value,'submit');  
    }
    net.cmty.ui.core.html.form.FormFactory_.prototype.createReset= function(name,value) {     
        return this.createInput(name,value,'reset');  
    }
    net.cmty.ui.core.html.form.FormFactory_.prototype.createImage= function(name,imageFile) {
        var image = this.createInput(name,value,'image'); 
        image.src = imageFile;
        return image;  
    }
    net.cmty.ui.core.html.form.FormFactory_.prototype.createRadiobox =  function(name,values,defaultValue) {
        var radios = document.createElement('div');
        radios.name = name;
        //default is array
        var valuesArray = values;
        if(typeof(values) == 'string') {
            valuesArray =  this.parseAvaiableValue_(values,',');
        }
        if(null != valuesArray && valuesArray.length > 0) {
            for(var i = 0; i< valuesArray.length; i++) {
                var elt = this.createInput(name,valuesArray[i],'radio');
                if(defaultValue != null && defaultValue == valuesArray[i]) {
                    elt.checked='checked';
                }
                this.writeln_(radios, valuesArray[i]);
                radios.appendChild(elt);
            }
        }
        return radios;
    }
         
    net.cmty.ui.core.html.form.FormFactory_.prototype.createCheckbox =  function(name,values,checkedValues) {
        var radios = document.createElement('div');
        radios.name = name;
        var valuesArray = values;
        if(typeof(values) == 'string') {
            valuesArray =  this.parseAvaiableValue_(values,',');
        }
        var defaultValues = checkedValues;
        if(typeof(checkedValues) == 'string') {
            defaultValues =  this.parseAvaiableValue_(checkedValues,',');
        }
        if(null != valuesArray && valuesArray.length > 0) {
            for(var i = 0; i< valuesArray.length; i++) {
                var elt = this.createInput(name,valuesArray[i],'checkbox');
                if(defaultValues != null && defaultValues != 'undefined') {
                    for(var ii = 0; ii< defaultValues.length; ii++) {
                        if(valuesArray[i] == defaultValues[ii]) {
                            elt.checked='checked';
                        }
                    }
                }
                this.writeln_(radios, valuesArray[i]);
                radios.appendChild(elt);
            }
        }
        return radios; 
    }
         
    net.cmty.ui.core.html.form.FormFactory_.prototype.createTextarea = function(name, value, rows, cols){
        var input = document.createElement('textarea');
       /* if (mxClient.IS_NS){
            rows--;
        }*/
        input.setAttribute('rows', rows || 5);
        input.setAttribute('cols', cols || 40);
        input.value = value;
        input.name = name;
        return input;
    };
         
    net.cmty.ui.core.html.form.FormFactory_.prototype.createCombobox =  function(name, isMultiSelect, size, labels, values, selectValues) {
        var select = document.createElement('select');
        select.style.width = 150+'px';
        select.name = name;
        if (size != null){
            select.setAttribute('size', size);
        }
        if (isMultiSelect){
            select.setAttribute('multiple', 'true');
        }
        if(null != labels && typeof(labels) == 'string') {
            labels =  this.parseAvaiableValue_(labels,',');
        }
        if(null != values && typeof(values) == 'string') {
            values =  this.parseAvaiableValue_(values,',');
        }
        var selectValues = selectValues;
        if(null != selectValues && typeof(selectValues) == 'string') {
            selectValues =  this.parseAvaiableValue_(selectValues,',');
        }
        if(null != values && values.length > 0) {
            for(var i = 0; i< values.length; i++) {
                var option = document.createElement('option');
                this.writeln_(option, labels[i]);
                option.setAttribute('value', values[i]);
                if(selectValues != null && selectValues != 'undefined') {
                    for(var ii = 0; ii< selectValues.length; ii++) {
                        if(values[i] == selectValues[ii]) {
                            option.setAttribute('selected', true);
                        }
                    }
                }
                select.appendChild(option);
            }
        }
        return select;
    }
    
    net.cmty.ui.core.html.form.FormFactory_.prototype.writeln_ = function(parent, text) { 
    	doc = parent.ownerDocument;
		var node = doc.createTextNode(text);
		
		if (parent != null)
		{
			parent.appendChild(node);
			parent.appendChild(document.createElement('br'));
		}
		
		return node;
    }
        
    net.cmty.ui.core.html.form.FormFactory_.prototype.createInput = function(name,value,type) {     
        var input = document.createElement('input');
        input.value = value == null ? '': value;
        input.name = name == null? '': name;
        input.type = type == null? '': type;
        return input;
    }
    
    net.cmty.ui.core.html.form.FormFactory_.prototype.parseAvaiableValue_ = function(availableValue,seperator){
        if(availableValue == null ||  availableValue == '') {
            return;
        }
        if(seperator == null) {
            seperator = ',';
        }
        var values =  availableValue.split(seperator);
        if(null == values || values.length < 1) {
            return;
        }
        return values;
    }
         
    net.cmty.ui.core.html.form.FormFactory_.prototype.getNodeValue = function(node){
        var nodeName = node.nodeName;
        if(node == null) {
            return null;
        }
        if(nodeName=="INPUT") {
            if(node.type =='radio' || node.type =='checkbox') {
                if( node.checked ) {
                    return node.value;
                }else {
                    return null;
                }  
            }
            return node.value;
        } else if(nodeName=="OPTION") {
            if(node.selected){
                return node.value;
            }
        } else if(nodeName == "TEXTAREA") {
            return node.value;
        }
        if(nodeName!="DIV" && nodeName != 'P') {
            return null;
        } 
        var subNodes = node.childNodes;
        if(null == subNodes || subNodes.length < 1) {
            return null;
        }
        var value = null;
        for(var index = 0 ; index < subNodes.length; index++) {
            var subNode = subNodes[index];
            if(subNode == null) {
                continue;
            }
                 
            var v = this.getNodeValue(subNode);
            if(v != null && value != null) {
                value = value +','+ v;
            }else if(v != null) {
                value = v;
            }
        }
        return value;
    }
    net.cmty.ui.core.html.form.formFactory = new net.cmty.ui.core.html.form.FormFactory_();
};
/******************************End HTML Form Factory****************************/