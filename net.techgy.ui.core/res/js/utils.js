techgy = typeof techgy === 'undefine'  ? techgy : {};

techgy.utils = {
    getUrl: function(url) {
        return this.getWebHttpPath(url);
    },
    getWebContextPath : function() {
        var pathname = location.pathname
        pathname = pathname.substring(1,pathname.length);
        pathname = pathname.substring(0,pathname.indexOf('/'));
        return '/'+pathname;
    },
     getWebWSPath : function(subPath) {
         var wp = 'ws://' + location.host + techgy.utils.getWebContextPath()
             + subPath;
        return wp;
    },
    getWebHttpPath : function(subPath) {
         var wp = 'http://' + location.host + techgy.utils.getWebContextPath()
             + subPath;
        return wp;
    },
    getStringWidthAsPix : function(str) {
        var span = document.getElementById("widthTester");
        if(span == null) {
            span = document.createElement('span');
        }
        span.style = "font-size:10pt";
        document.body.appendChild(span);
        var oldWidth = span.offsetWidth;
        span.innerText= str; 
        oldWidth = span.offsetWidth-oldWidth; 
        span.innerHTML='';
        if(null != span) {
            document.body.removeChild(span);  
        }
        return oldWidth;
    },
        
    getTimeAsMills: function() {
        return new Date().getTime();
    },
        
    strByteLength:  function(str)  {  
        var i;  
        var len;  
        len = 0;  
        for (i=0;i<str.length;i++)  {  
            if (str.charCodeAt(i)>255) len+=2; else len++;  
        }  
        return len;  
    },
    isIngeger: function (value)  {      
        if ('/^(\+|-)?\d+$/'.test(value )){  
            return true;  
        }else { 
            return false;  
        }  
    },
    isFloat: function(value){         
        if ('/^(\+|-)?\d+($|\.\d+$)/'.test(value )){  
            return true;  
        }else{  
            return false;  
        }  
    } ,
    checkUrl: function (value){    
        var myReg = '/^((http:[/][/])?\w+([.]\w+|[/]\w*)*)?$/';   
        return myReg.test( value ); 
    },
    checkEmail: function (value){    
        var myReg = '/^([-_A-Za-z0-9\.]+)@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/';   
        return myReg.test(value);  
    },
    checkIP:   function (value)   {   
        var re='/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/';  
        if(re.test( value ))  {
            if( RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) 
                return true;  
        }
        return false;   
    },
    inherits : function(childCtor, parentCtor) {
        function tempCtor() {};
        tempCtor.prototype = parentCtor.prototype;
        childCtor.superClass_ = parentCtor.prototype;
        childCtor.prototype = new tempCtor();
        childCtor.prototype.constructor = childCtor;
    },
    getWebContextPath : function() {
        var pathname = location.pathname
        pathname = pathname.substring(1,pathname.length);
        pathname = pathname.substring(0,pathname.indexOf('/')); 
        return '/'+pathname;
    },
    bind : function(scope, funct){
        return function(){
            return funct.apply(scope, arguments);
        };
    },
    removeAllChildren : function(container) {
        if(container) {
            for(var c = container.firstChild; c != null; c = container.firstChild ) {
                container.removeChild(c);
            }
        }
    },  
    startLoading : function(domHelper) {
        domHelper = domHelper || document;
        var center = domHelper.createElement('center');
        center.style.paddingTop = "300px";
        center.id='loading_'
        var img = domHelper.createElement('img');
        img.src = 'images/loading.gif';
        center.appendChild(img);
        domHelper.body.appendChild(center);
    },
    stopLoading : function(domHelper) {
        domHelper = domHelper || document;
        domHelper.body.removeChild(domHelper.getElementById('loading_'));
    },
    getAsync : function(url,params,onload,onerror,username,password) {
      if(params) {
            url = url + '?';
            for(var p in params) {
                url = url + p + '=' + params[p]+'&';
            }
        }
        this.r_(url, null, 'get', true, onload, onerror, username, password)
    },
    
    getSync : function(url,params,username,password) {
        if(params) {
            url = url + '?';
            for(var p in params) {
                url = url + p + '=' + params[p]+'&';
            }         
        }
        return this.r_(url, null, 'get', false, null, null, username, password);
    },
    
    post : function(url,params,onload,onerror,username,password) {
        this.r_(url, params, 'post', true, onload, onerror, username, password);
    },
  
    postSync : function(url,params,username,password) {
        return this.r_(url, params, 'post', false, null, null, username, password);  
    },
    
    put : function(url,params,onload,onerror,username,password) {
        this.r_(url, params, 'put', true, onload, onerror, username, password);
    },
    
    putSync : function(url,params,username,password) {
        return this.r_(url, params, 'put', false, null, null, username, password);  
    },
    
    del : function(url,params,onload,onerror,username,password) {
        if(params) {
              url = url + '?';
              for(var p in params) {
                  url = url + p + '=' + params[p]+'&';
              }
          }
          this.r_(url, null, 'delete', true, onload, onerror, username, password)
      },
      
     delSync : function(url,params,username,password) {
          if(params) {
              url = url + '?';
              for(var p in params) {
                  url = url + p + '=' + params[p]+'&';
              }         
          }
          return this.r_(url, null, 'delete', false, null, null, username, password);
      },
    
    //params is a map {key:value,key1:value, ... , keyN: vaueN}
    r_: function(url,params,method,async,onload,onerror,username,password){
        //url, params, method, async, username, password
        url = this.getUrl(url);
        var request = new techgy.utils.HttpRequest(url,params,method,
        async,username,password);
        if(async) {
            request.send(onload, onerror);
        }else {
            request.send();
            return request;
        }       
    },
    /**
     * techgy.utils.Constants={
        IE6:'ie6',
        IE7:'ie7',
        IE8:'ie8',
        IE9:'ie9',
        IE10:'ie10',
        chrome:'chrome',
        firefox:'firefox',
        safari:'safari',
        opera:'opera'
       only IE to be support version checking
       }
     */
    isBroswer : function(browser) {
         return techgy.utils.browser[browser] != null;
    },
    version : function() {
        for(var b in techgy.utils.browser) {
            return techgy.utils.browser[b][1].split('.')[0];
        }
    },
    isIe : function() {
         return this.isBroswer(techgy.utils.Constants.IE9) ||
         this.isBroswer(techgy.utils.Constants.IE8) ||
         this.isBroswer(techgy.utils.Constants.IE7) ||
         this.isBroswer(techgy.utils.Constants.IE6)
    },
    toJson : function(obj) {
        if(typeof obj === 'string') {
            return obj;
        } else if(typeof obj === 'object') {
            return JSON.stringify(obj);
        }else {
            throw 'obj cannot transfor to Json'
        }
    },
    fromJson : function(jsonStr) {
        console.log(typeof jsonStr);    
        if(typeof jsonStr === 'string') {
        	var msg = eval('('+jsonStr+')');
        	if(msg.status){
        		msg.status = eval('('+msg.status+')');
        	}
           return new techgy.Message(msg);
        }else if(typeof jsonStr === 'object') {
            return jsonStr;
        } else  {
            throw 'fail from Json: ' + jsonStr;
        }
        
    },
    
    fromJ : function(jsonStr) {
        if(typeof jsonStr === 'string') {
        	var msg = eval('('+jsonStr+')');
        	if(msg.status){
        		msg.status = eval('('+msg.status+')');
        	}
           return msg;
        }else if(typeof jsonStr === 'object') {
            return jsonStr;
        } else  {
            throw 'fail from Json: ' + jsonStr;
        }
        
    },
    
    clone : function(jsObj) {
    	var type = typeof jsObj;
        if(type != 'object') {
        	return jsObj;
        }
        var obj = {};
        for(var i in jsObj) {
        	var o = jsObj[i];
        	obj[i] = techgy.utils.clone(o);
        }
        return obj;
    }
}

 
techgy.utils.Constants={
    // debug level
    INFO:'INFO',
    DEBUG:'DEBUG',
    ERROR:'ERROR',
    FINAL:'FINAL',
    DEFAULT:'DEFAULT',
    IE:'ie',
    IE6:'ie6',
    IE7:'ie7',
    IE8:'ie8',
    IE9:'ie9',
    IE10:'ie10',
    chrome:'chrome',
    firefox:'firefox',
    safari:'safari',
    opera:'opera'
}
 
if(!techgy.utils.sys) {
    var ua = navigator.userAgent.toLowerCase();
    var s = null;
    var key = null;
    var bv = null;
    if(s = ua.match(/msie ([\d.]+)/)) {
        key = techgy.utils.Constants.IE;
        key = key + s[1].split('.')[0];
    }else if(s = ua.match(/firefox\/([\d.]+)/)) {
        key = techgy.utils.Constants.firefox
    }else if(s = ua.match(/chrome\/([\d.]+)/)) {
        key = techgy.utils.Constants.chrome;
    }else if(s = ua.match(/opera.([\d.]+)/)) {
        key = techgy.utils.Constants.opera;
    }else if(s = ua.match(/version\/([\d.]+).*safari/)) {
        key = techgy.utils.Constants.safari;
    }
    techgy.utils.browser = {};
    if(s != null) {
       techgy.utils.browser[key] = [];
       techgy.utils.browser[key][0] = s[0].trim();
       techgy.utils.browser[key][1] = s[1].trim();
    }
}


techgy.utils.Dialog = function(elt, docHelper) {
    if(!elt){
        console.log('sub element cannot be null')
        return;
    }
    docHelper = docHelper | documnet;
}

techgy.asynformupload = {
    isIE: function () {
        return ! -[1, ];
    },
    isFF: function () {
        return window.navigator.userAgent.indexOf("Firefox") !== -1;
    },
    isArray: function (obj) {
        return this.isType(obj, "Array");
    },
    isType: function (obj, type) {
        return Object.prototype.toString.call(obj) === "[object " + type + "]";
    },
    isNodeList: function (obj) {
        return this.isType(obj, "NodeList");
    },
    parseDom : function (arg) {
        var objE = document.createElement("div");
        objE.innerHTML = arg;
        return objE.childNodes;
    },
    hiddenForm_ : 'hiddenForm_',
    hiddenIframe_ :'hiddenIframe_',
    formSubmit: function (args, action, func) {
        this.clearContext();
        this.callBack = null;
        this.loadHack = true;
        var subArr = [];
        var subArrT = [];
        if (techgy.asynformupload.isArray(args)) {
            subArr = args;
        } else if(techgy.asynformupload.isNodeList(args)) {
            for(var index = 0 ; index < args.length; index++) {
                subArr.push(args[index]);
            }
        }else {
            var tag = args.tagName.toLowerCase();
            if (tag == "form") { 
                for (var i = 0, num = args.childNodes.length; i < num; i++) {
                    subArr.push(args.childNodes[i]); 
                }
            }
            else {
                subArr = [args];
            }
        }
        //create asynformupload form and ifroma
        var objForm = document.createElement("form");
        objForm.action = action;
        objForm.target = this.hiddenIframe_;
        objForm.encoding = "multipart/form-data";
        objForm.method = "post";
        objForm.id = this.hiddenForm_;
        objForm.style.display = "none";
        var objIframe = techgy.asynformupload.parseDom('<iframe id="hiddenIframe_" name="hiddenIframe_" src="about:blank" style="display:none;" onload="techgy.asynformupload.complete()"></iframe>')[0];
        //add submit value in form
        for (var i = 0, num = subArr.length; i < num; i++) {
            if (!subArr[i].name && subArr[i].nodeType == 1 && subArr[i].tagName.toLowerCase() == "input") subArr[i].name = "inputid_" + i;
            var input = subArr[i].cloneNode(true);
            subArrT.push(input);
            subArr[i].parentNode.replaceChild(input, subArr[i]);
            objForm.appendChild(subArr[i]);
        }
        //submit
        document.body.appendChild(objIframe);
        document.body.appendChild(objForm);
        objForm.submit();
        //dispose
        for (var i = 0, num = subArrT.length; i < num; i++) {
            subArrT[i].parentNode.replaceChild(subArr[i], subArrT[i]);
        }
        if (func) this.callBack = func;
    },
    complete: function () {
        //check load hack, opera & chrome & safari will load twice for onece add body other is loaded
        if (this.loadHack && !techgy.asynformupload.isIE()) {
            this.loadHack = false;
            if (techgy.asynformupload.isFF()) setTimeout('techgy.asynformupload.complete()', 100);
            return;
        }

        var responseText = "";
        try {
            responseText = fn(this.hiddenIframe_).contentHtml.document.body.innerHTML;
        } catch (err) { }

        this.clearContext();
        if (this.callBack) this.callBack(responseText);
    },
        
    clearContext: function () {
        var node = document.getElementById( this.hiddenForm_)
        if(node) {
            var pE = node.parentNode; 
            if (pE) pE.removeChild(node); 
        }
        node = document.getElementById(this.hiddenIframe_)
        if(node) {
            pE = node.parentNode; 
            if (pE) pE.removeChild(node); 
        }
    },
    callBack: null,
    loadHack: true
};

techgy.utils.DebugMessage = function() {
    //info
    this.level = techgy.utils.Constants.INFO;
    this.msg = null;
    this.timeStamp = new Date();
    this.nodeId = -1;
};
techgy.utils.DebugMessage.Constants ={
    INFO:'INFO',
    DEBUG:'DEBUG',
    ERROR:'ERROR',
    FINAL:'FINAL',
    DEFAULT:'DEFAULT'
}

    
techgy.utils.DebugMessage.prototype.toString = function() {
    var timeStr= ''+ this.timeStamp.getFullYear()+',' + this.timeStamp.getMonth()+ ',' + this.timeStamp.getDate()
    + ' ' + this.timeStamp.getHours() +':'+ this.timeStamp.getMinutes()+':' +this.timeStamp.getSeconds();
    return timeStr +" [" + this.level+ "] Node ID[" + this.nodeId +'] '+ this.msg;
}
techgy.utils.List = function() {
    this.data = [];
}
techgy.utils.List.prototype.size = function() {
    if (null == this.data) {
        return 0;
    }
    return this.data.length;
};
techgy.utils.List.prototype.removeAll = function() {
    this.data.splice(0,this.data.length);
} 
techgy.utils.List.prototype.add = function (element) {
    if (null == this.data) {
        return this.data = Array();
    }
    for(var i = 0; i< this.data.length; i++) {
        if(this.data[i] == null) {
            this.data[i] = element;
            return;
        }
    }
    this.data.push(element);
};

techgy.utils.List.prototype.get = function (index) {
    if (null == this.data) {
        return null;
    }
    if (index >= this.size()) {
        return null;
    }
    return this.data[index];
};

techgy.utils.List.prototype.remove = function (element) {
    if (null == this.data) {
        return;
    }
    var index = this.getIndex_(element);
    if (index == -1) {
        return;
    }
    this.data.splice(index,1)
};

techgy.utils.List.prototype.contains = function (element) {
    if (null == this.data) {
        return false;
    }
    var index = this.getIndex_(element);
    if (index == -1) {
        return false;
    }
    return true;
};
techgy.utils.List.prototype.removeAll = function () {
    if (null == this.data) {
        return;
    }
    this.data.splice(0,this.data.length);
};
	
techgy.utils.List.prototype.addAll = function (elements) {
    if (null == elements || elements.size() < 0) {
        return;
    }
    if(null == this.data) {
        this.data = new Array();
    }
    for ( var index = 0; index < this.elements.length; index++) {
        if (null == this.elements[index]) {
            continue;
        }
        this.data.push(this.elements[index]);
    }
};
techgy.utils.List.prototype.indexOf = function (elements) {
    return this.getIndex_(elements);
}
/**
* .Hide
*/
techgy.utils.List.prototype.getIndex_ = function(element) {
    if (null == this.data) {
        return -1;
    }
    var result = -1;
    for ( var index = 0; index < this.data.length; index++) {
        if (null == this.data[index]) {
            continue;
        }
        if(!this.data[index].equals) {
            throw 'Must implement equals method for list element';
        }
        if (this.data[index].equals(element)) {
            result = index;
            break;
        }
    }
    return result;
};
       
/**
* This implementation is very strange that set inherit from List. just work
* well for me! will be modified if any problem occurs.
*/
techgy.utils.Set = function() {
    techgy.utils.List.apply(this, arguments);
};
techgy.utils.inherits(techgy.utils.Set, techgy.utils.List);
//techgy.utils.Set.prototype = new  techgy.utils.List();
//techgy.utils.Set.prototype.constructor = techgy.utils.Set;
       
/**
* Overwrite this method to guarantee every element is unique in the set. If
* the added element is exist, delete the old element before adding. Must
* overwrite the equals() method of the element if need
*/
techgy.utils.Set.prototype.add = function(element) {
    if (null == this.data) {
        return this.data = Array();
    }
    if (this.contains(element)) {
        this.remove(element);
    }
    this.data.push(element);
};

techgy.utils.Set.prototype.contains = function (element) {
    if (null == this.data) {
        return false;
    }
    var index = this.getIndex_(element);
    if (index == -1) {
        return false;
    }
    return true;
};

techgy.utils.Set.prototype.remove = function (element) {
    if (null == this.data) {
        return;
    }
    var index = this.getIndex_(element);
    if (index == -1) {
        return;
    }
    this.data.splice(index,1)
};

techgy.utils.Set.prototype.addAll = function (elements) {
    if (null == elements || elements.size() < 0) {
        return;
    }
    if(null == this.data) {
        this.data = new Array();
    }
    for ( var index = 0; index < elements.size(); index++) {
        var e = elements.get(index);
        if (null == e) {
            continue;
        }
        this.add(e);
    }
};
        
techgy.utils.Set.prototype.getIndex_ = function (element) {
    if (null == this.data) {
        return -1;
    }
    var result = -1;
    for ( var index = 0; index < this.data.length; index++) {
        if (null == this.data[index]) {
            continue;
        }
        if((this.data[index].equals != null) && (this.data[index].equals(element))) {
            result = index;
        }else if (this.data[index] === element) {
            result = index;
                    
        }
        if(result != -1) {
            break;
        }
    }
    return result;
};

techgy.utils.Set.prototype.size = function () {
    if (null == this.data) {
        return 0;
    }
    return this.data.length;
};

techgy.utils.Set.prototype.get = function (index) {
    if (null == this.data) {
        return null;
    }
    if (index >= this.size()) {
        return null;
    }
    return this.data[index];
};
        
techgy.utils.MapEntity = function(key, value) {
    this.key = key;
    this.value = value;
};
techgy.utils.HashMap = function() {
    this.data = new Array();
};

techgy.utils.HashMap.prototype.put = function(key, value) {
    if (null == this.data) {
        this.data = new Array();
        this.data.push(new techgy.utils.MapEntity(key, value));
        return;
    }
    if (null == key) {
        throw "key can't be null";
    }
    if (this.contains(key)) {
        this.remove(key);
    }
    this.data.push(new techgy.utils.MapEntity(key, value));
};

techgy.utils.HashMap.prototype.keySet = function() {
    if (null == this.data || this.data.length <= 0) {
        return null;
    }
    var ks = new techgy.utils.Set();
    for ( var index = 0; index < this.data.length; index++) {
        if (null == this.data[index]) {
            continue;
        }
        ks.add(this.data[index].key);
    }
    return ks;
};

techgy.utils.HashMap.prototype.addAll = function(parameters) {
    var size = parameters.size();
    if (null == parameters || size <= 0) {
        return;
    }
    if (null == this.data) {
        this.data = new Array();
    }
    size = -1;
    var ks = parameters.keySet();
    size = ks.size();
    for ( var index = 0; index < size; index++) {
        var key = ks.get(index);
        if (null == key) {
            continue;
        }
        var value = parameters.get(key);
        this.put(key, value);
    }
};

techgy.utils.HashMap.prototype.get = function(key) {
    if (null == this.data || null == key) {
        return null;
    }
    var index = this.getIndex_(key);
    if (-1 == index) {
        return null;
    }
    return this.data[index].value;
};

techgy.utils.HashMap.prototype.contains = function(key) {
    if (null == this.data || null == key) {
        return false;
    }
    var index = this.getIndex_(key);
    if (-1 == index) {
        return false;
    }
    return true;
};

techgy.utils.HashMap.prototype.remove = function(key) {
    if (null == this.data || null == key) {
        return;
    }
    var index = -1;
    index = this.getIndex_(key);
    if (-1 != index) {
        this.data.splice(index,1)
    }
};

techgy.utils.HashMap.prototype.removeAll = function() {
    if (null == this.data) {
        return;
    }
    for ( var index = 0; index < this.data.length; index++) {
        if (null == this.data[index]) {
            continue;
        }
        this.data.splice(index,1)
    }
};

techgy.utils.HashMap.prototype.size = function() {
    if (null == this.data) {
        return 0;
    }
    var count = 0;
    for ( var index = 0; index < this.data.length; index++) {
        if (null == this.data[index]) {
            continue;
        }
        count++;
    }
    return count;
};

/**
* .Hide
*/
techgy.utils.HashMap.prototype.getIndex_ = function(key) {
    if (null == this.data || null == key) {
        return -1;
    }
    var result = -1;
    for ( var index = 0; index < this.data.length; index++) {
        if (null == this.data[index]) {
            continue;
        }
        if (this.data[index].key == key) {
            result = index;
            break;
        }
    }
    return result;
}
        
/******************************HTML table********************************/
techgy.utils.Table = function(style) {
    this.init(style);
};
        
techgy.utils.Table.prototype.init = function(style) {
    this.table = document.createElement('table');
    if(style != null) {
        this.table.className = style;
    }
    this.table.style.border = 2;
    this.table.style.width = '100%';
    this.table.style.height = '100%';
    this._tbody = document.createElement('tbody')
    this.table.appendChild(this._tbody);
    this.inputlist = new techgy.utils.HashMap();
}
        
techgy.utils.Table.prototype.addRow = function(style) {
    this.__currentRow = document.createElement('tr');
    if(style != null) {
        this.__currentRow.className = style;
    }
    this._tbody.appendChild(this.__currentRow);
    return this.__currentRow;
}
techgy.utils.Table.prototype.addTd = function(elt,style) {
    var td = document.createElement('td');
    if(null!= style) {
        td.className = style;
    }
    if(null != this.__currentRow) {
        this.__currentRow.appendChild(td);
    }
    if(elt != null) {
        if(typeof(elt) == 'string') {
            mxUtils.write(td, elt);
        }else if(typeof(elt) == 'object'){
            td.appendChild(elt);
        }
        if(elt.nodeName == 'INPUT' || elt.nodeName == 'DIV' || elt.nodeName == 'P' || elt.nodeName == 'TEXTAREA') {
            this.inputlist.put(elt.name,elt);
        }
    }
    return td;
}
// utils for form
techgy.utils.Table.prototype.addTextRow = function(label,name,value) {
    this.addRow();
    this.addTd(mxResources.get(label,null,label)); 
    var input = techgy.utils.formFactory.createTextbox(name,value);
    this.inputlist.put(name,input);
    this.addTd(input);
}
        
techgy.utils.Table.prototype.addTextarea = function(label,name,value,rows,cols) {
    this.addRow();
    this.addTd(label); 
    var input = techgy.utils.formFactory.createTextarea(name,value,rows,cols)
    this.inputlist.put(name,input);
    this.addTd(input);
}
        
techgy.utils.Table.prototype.addLabel = function(label) {
    this.addTd(label);
}
techgy.utils.Table.prototype.addRadioRow = function(label,name,value,defaultValue) {
    this.addRow();
    this.addTd(label); 
    var input = techgy.utils.formFactory.createRadiobox(name,value,defaultValue);
    this.inputlist.put(name,input);
    this.addTd(input);
}
        
techgy.utils.Table.prototype.addCheckboxRow= function(label,name,values,checkValues) {
    this.addRow();
    this.addTd(label); 
    this.addTd(label); 
    var input = techgy.utils.formFactory.createCheckbox(name,values,checkValues);
    this.inputlist.put(name,input);
    this.addTd(input);
}
techgy.utils.Table.prototype.addCheckboxRow= function(label,name,values,checkValues) {
    this.addRow();
    this.addTd(label); 
    var input = techgy.utils.formFactory.createCheckbox(name,values,checkValues);
    this.inputlist.put(name,input);
    this.addTd(input);   
}
//name, isMultiSelect, size, labels, values, selectValues
techgy.utils.Table.prototype.addComboboxRow= function(label,name,isMultiSelect, size, labels, values, selectValues) {
    this.addRow();
    this.addTd(label); 
    var input = techgy.utils.formFactory.createCombobox(name, isMultiSelect, size, labels, values, selectValues);
    this.inputlist.put(name,input);
    this.addTd(input);   
}
techgy.utils.Table.prototype.addButton= function() {
             
    }
        
techgy.utils.Table.prototype.removeRow = function(row) {
    if(row != null) {
        this._tbody.removeChild(row);
        return true;
    }
    return false;
}
        
techgy.utils.Table.prototype.getAllRows = function() {
    return this._tbody.childNodes;
}
techgy.utils.Table.prototype.removeAllRows = function() {
    if(this._tbody.childNodes == null) {
        return;
    }
    var children = this._tbody.childNodes;
    for(var index = 0; index < children.length;) {     
        if(!this.removeRow(children[index])) {
            index++
        }
    }
}
        
techgy.utils.Table.prototype.hiddenAllRows = function(rows) {
    this.setInputDisplay_(rows,'none');
}
        
techgy.utils.Table.prototype.showAllRows = function(rows) {
    this.setInputDisplay_(rows,'');
}
techgy.utils.Table.prototype.setInputDisplay_ = function(rows,display) {
    if(rows == null) {
        return;
    }
    var size = rows.size();
    for(var index = 0; index < size; index++) {
        var input = rows.get(index);
        if(input == null) {
            continue;
        }
        input.style.display= display; 
    }
}
/******************************End HTML table****************************/
    
/*********************************Form Factory*******************************/

if(techgy.utils.formFactory == null || techgy.utils.formFactory == 'undefined') {
    techgy.utils.FormFactory_ = function() {
         
        }
    techgy.utils.FormFactory_.prototype.createTextbox =  function(name,value) {
        return this.createInput(name,value,'text');  
    }
    techgy.utils.FormFactory_.prototype.createPassword = function(name,value) {     
        return this.createInput(name,value,'password');  
    }
    techgy.utils.FormFactory_.prototype.createHidden = function(name,value) {     
        return this.createInput(name,value,'hidden');  
    }
    techgy.utils.FormFactory_.prototype.createFile = function(name,value) {     
        return this.createInput(name,value,'file');  
    }
          
    techgy.utils.FormFactory_.prototype.createSubmit= function(name,value) {     
        return this.createInput(name,value,'submit');  
    }
    techgy.utils.FormFactory_.prototype.createReset= function(name,value) {     
        return this.createInput(name,value,'reset');  
    }
    techgy.utils.FormFactory_.prototype.createImage= function(name,imageFile) {
        var image = this.createInput(name,value,'image'); 
        image.src = imageFile;
        return image;  
    }
    techgy.utils.FormFactory_.prototype.createRadiobox =  function(name,values,defaultValue) {
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
                mxUtils.write(radios, valuesArray[i]);
                radios.appendChild(elt);
            }
        }
        return radios;
    }
         
    techgy.utils.FormFactory_.prototype.createCheckbox =  function(name,values,checkedValues) {
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
                mxUtils.write(radios, valuesArray[i]);
                radios.appendChild(elt);
            }
        }
        return radios; 
    }
         
    techgy.utils.FormFactory_.prototype.createTextarea = function(name, value, rows, cols){
        var input = document.createElement('textarea');
        if (mxClient.IS_NS){
            rows--;
        }
        input.setAttribute('rows', rows || 5);
        input.setAttribute('cols', cols || 40);
        input.value = value;
        input.name = name;
        return input;
    };
         
    techgy.utils.FormFactory_.prototype.createCombobox =  function(name, isMultiSelect, size, labels, values, selectValues) {
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
                mxUtils.writeln(option, labels[i]);
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
        
    techgy.utils.FormFactory_.prototype.createInput = function(name,value,type) {     
        var input = document.createElement('input');
        input.value = value == null ? '': value;
        input.name = name == null? '': name;
        input.type = type == null? '': type;
        return input;
    }
    techgy.utils.FormFactory_.prototype.parseAvaiableValue_ = function(availableValue,seperator){
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
         
    techgy.utils.FormFactory_.prototype.getNodeValue = function(node){
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
    techgy.utils.formFactory = new techgy.utils.FormFactory_();
};
techgy.utils.HttpRequest = function(url, params, method, async, username, password){
    this.username = username;
    this.password = password;
    this.url = url;
    this.params = params;
    this.method = method || 'post';
    this.async = (async != null) ? async : true;
};


techgy.utils.HttpRequest.prototype.isBinary = function(){
    return this.binary;
};


techgy.utils.HttpRequest.prototype.setBinary = function(value){
    this.binary = value;
};


techgy.utils.HttpRequest.prototype.getText = function(){
    return this.request.responseText;
};


techgy.utils.HttpRequest.prototype.isReady = function(){
    return this.request.readyState == 4;
};


techgy.utils.HttpRequest.prototype.getXml = function(){
    return this.request.responseXML;
};


techgy.utils.HttpRequest.prototype.getText = function(){
    return this.request.responseText;
};


techgy.utils.HttpRequest.prototype.getStatus = function(){
    return this.request.status;
};


techgy.utils.HttpRequest.prototype.toString = function(){
    return 'Status: ' + this.getStatus() + 'Text: ' + this.getText();
};


techgy.utils.HttpRequest.prototype.createRequest_ = function(){
    if (window.XMLHttpRequest){
        return new XMLHttpRequest();	
    }else if (typeof(ActiveXObject) != "undefined"){
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
};


techgy.utils.HttpRequest.prototype.send = function(onload, onerror){
    this.request = this.createRequest_();
    var handlerResponse = techgy.utils.bind(this, function() {
        if (this.isReady()){
            var status = this.getStatus();
            if(status == 200) {
                onload(this);
            } else{
                if(onerror) {
                    onerror(this);
                }
                console.log(this.getResponseText());
            }
            this.request.onreadystatechange = null;
        }
    });
        //                             /resources/i18n_zh-cn.properties
        //http://techgy.oicp.net:8899/web/resources/i18n_zh-cn.properties
    if (this.request){
        if (onload && this.async){
            this.request.onreadystatechange = handlerResponse;
        }
        this.request.open(this.method, this.url, this.async,
            this.username, this.password);
        this.setRequestHeaders(this.request, this.params);
        this.request.send(this.params);
        if( !this.async && this.getStatus() != 200) {
             console.log(this.getText());
        }  
    }
};


techgy.utils.HttpRequest.prototype.setRequestHeaders = function(request, params){
    if (params){
        request.setRequestHeader('Content-Type',
            'application/x-www-form-urlencoded');
    }
};

/**
 * take the i18n.propertis as the default language
 */
techgy.utils.i18n = new function() {
    //resource name is : i18n_zh.properties
    this.resources_ = new techgy.utils.HashMap();
    //take the i18n.propertis as the default language
    this.defaultLanguage_ = '';
    this.defaultResDir_ = '/resources';
    this.resPrefix_ = 'i18n';
    this.supportLangs = [];
    
    this.get = function(key,params,defaultValue) {
        if(!defaultValue) {
            defaultValue = '';
        }
        if(key == null ) {
            return defaultValue;
        }
        var v = this.resources_.get(key.trim());
        if(!v) {
            return defaultValue
        }
        if(!params) {
            return v;
        }
        var result = [];
        var index = null;		
        for (var i = 0; i < v.length; i++){
            var ch = v.charAt(i);
            if (ch == '{') {
                index = '';
            } else if (index != null && ch == '}') {
                index = parseInt(index);				
                if (index >= 0 && index < params.length){
                    result.push(params[index]);
                }			
                index = null;
            } else if (index != null) {
                index += ch;
            } else  {
                result.push(ch);
            }
        }		
        v = result.join('');
        return v;
    }
    
    
    this.set = function(key,value) {
        if(!key) {
            return;
        }
        this.resources_.put(key.trim(),value);
    }
    
    this.getFromServer_ = function(url) {
        var request = new techgy.utils.HttpRequest(
            url, null, 'get', false, null, null);
        request.send();
        if(request.getStatus() == 200) {
            return request.getText();
        } else {
            console.log( 'fail to get: '+url);
            return null;
        }
    }
    
    this.parse_ = function(text) {
        if(!text) {
            return;
        }
        var lines = text.split('\n');		
        for (var i = 0; i < lines.length; i++){
            var index = lines[i].indexOf('=');	
            if(index < 0) {
                continue;
            }
            var key = lines[i].substring(0, index);
            var idx = lines[i].length;			
            if (lines[i].charCodeAt(idx - 1) == 13){
                idx--;
            }
            var value = lines[i].substring(index + 1, idx);
            //strip the invalid utf8 chart
            value = value.replace(/\\(?=u[a-fA-F\d]{4})/g,"%");
            this.set(key,unescape(value));
        }
    }
    
    
    this.load_ = function(lan){
        var path = this.defaultResDir_ + '/' +this.resPrefix_;
        if(lan != null && lan.trim() != '') {
            path =  path + '_' + lan;
        }
        path = path + '.properties';
        var url = techgy.utils.getWebHttpPath(path);
        var text = this.getFromServer_(url);
        if(!text) {
            return false
            };
        this.parse_(text);
        return true;
    }

    this.getLocal_ = function() {
        var lang = techgy.utils.localStorage.get('language');
        if(lang == null) {
            lang = navigator.browserLanguage ? navigator.browserLanguage : navigator.language;
        }
        return lang;
    }

    this.init = function() {
        var url = '/rest/ac/sl';
        // getAsync : function(url,params,onload,onerror,username,password) 
        techgy.utils.getAsync(url,null,function(req){
            var value = req.getText();
            if(value == null || '' == value.trim()) {
                console.log('fail to get support language');     
            }else {
            	techgy.utils.i18n.supportLangs = techgy.utils.fromJ(value);
            //alert('save note result: ' + req)
            }
        },function(req){
             console.log('fail to get support language: '+ req.getSatus());
        });
        this.load_('');
        var specailLanguage = this.getLan_(this.getLocal_());  
        if(specailLanguage && specailLanguage != '') {
            this.load_(specailLanguage);
        }
    }
    
    this.getLan_ = function(lan) {
        if(!lan) {
            return '';
        }
        return lan.toLowerCase();
    }
     
    this.setDefaultLang = function(lan) {
        this.defaultLanguage_ = lan;
    }
    
    this.setDefaultResDir = function(dir) {
        this.defaultResDir_ = dir;
    }
    
    this.setResPrefix = function(prefix) {
        this.resPrefix_ = prefix;
    }
    
    this.getDefaultLang = function() {
        return this.defaultLanguage_;
    }
    
    this.getDefaultResDir = function() {
        return this.defaultResDir_;
    }
    
    this.getResPrefix = function() {
        return this.resPrefix_;
    }
}

techgy.utils.localStorage = new function() {
    this.updateBrowser_ = techgy.utils.i18n.get('update_your_browser');
    this.set = function(key,value) {
        if(!this.isSupport()) {
            alert(this.updateBrowser_);
            return;
        }
        localStorage.setItem(key,value);
    }
   
    this.remove = function(key) {
         if(!this.isSupport()) {
            alert(this.updateBrowser_);
            return;
        }
        localStorage.removeItem(key);
    }
   
    this.get = function(key) {
         if(!this.isSupport()) {
            alert(this.updateBrowser_);
            return;
        }
        return localStorage.getItem(key);
    }
   
    this.clear = function() {
         if(!this.isSupport()) {
            alert(this.updateBrowser_);
            return;
        }
        if(confirm('this operation will clear all local storage data?')) {
            localStorage.clear();
        }
    }
   
    this.supportLocalstorage = function() {
        try {
            return window.localStorage !== null;
        } catch (e) {
            return false;
        }
    }
   
    this.isSupport = function() {
        if(techgy.utils.isIe() && techgy.utils.version() < 8) {
           return false;
        }else {
            return true;
        }      
    }
}
 
techgy.utils.sessionStorage = new function() {
	
    this.updateBrowser_ = techgy.utils.i18n.get('update_your_browser');
    
    this.set = function(key,value) {
        if(!this.supportLocalstorage()) {
            alert(this.updateBrowser_);
            return;
        }
        sessionStorage.setItem(key,value);
    }
   
    this.remove = function(key) {
         if(!this.supportLocalstorage()) {
            alert(this.updateBrowser_);
            return;
        }
        sessionStorage.removeItem(key);
    }
   
    this.get = function(key) {
         if(!this.supportLocalstorage()) {
            alert(this.updateBrowser_);
            return;
        }
        return sessionStorage.getItem(key);
    }
   
    this.clear = function() {
         if(!this.supportLocalstorage()) {
            alert(this.updateBrowser_);
            return;
        }
        if(confirm('this operation will clear all local storage data?')) {
            sessionStorage.clear();
        }
    }
   
    this.supportLocalstorage = function() {
        try {
            return window.sessionStorage !== null;
        } catch (e) {
            return false;
        }
    }
}
 
 
 techgy.utils.idGenerator = new function() {
     this.id_ = 0;
   
     this.getId = function(){
         return this.id_++;
     }
 }
 
 techgy.utils.UICell =  function() {
	 this.value = '';
     this.columnSeq = '';
 }
 
 techgy.utils.UIRow =  function() {
	 this.cells = [];
 }
 
 techgy.utils.CreatedDef=  function() {	 
	 this.notCreated = false;
	 this.uiType = 'text';
	 this.values = [];
	 this.defValue='';
 }
 
 techgy.utils.QueryDef=  function() {
	 this.displayName = null;
	 this.seq = -1;
	 this.unvisible;
     this.resKey = "";
     this.uiValueFields=[];
	 this.valueSeperator=',';
	 this.elementSeperator=';';
 }
 
 techgy.utils.UpdateDef=  function() {
	 
 }
 
 techgy.utils.RemoveDef=  function() {
	 
 }
 
 techgy.utils.VODefinition=  function() {
     this.fieldName = '';
	 this.fieldType = '';
	 this.defValue='';
	 this.complexIns=false;
	 this.fieldValueClsName=null;
	 this.createdDef= {};
	 this.queryDef= {};
	 this.removeDef= {};
	 this.updateDef= {};
 }
 
 techgy.utils.UITable =  function() {
	 this.tableName = null;
     this.vodef = [];
	 this.rows = [];
	 this.vos = [];
 }
 
 techgy.utils.uiManager = new function() {	 
	 this.parseVOTable = function(tableStr) {
		 if(!tableStr || tableStr.trim() === '') {
			 alert('no content');
		 }
		 var tj = eval('('+tableStr+')');
		 var t = new techgy.utils.UITable();
		 t.tableName = tj.tableName;	
		 t.vodef = this.parseVODefinition(tj.vodef);
		 if(tj.rows && tj.rows.length > 0) {
			 for(var ri in tj.rows) {
				 var ur = new techgy.utils.UIRow();
				 var r = tj.rows[ri];
				 for(var c in r.cells) {
					 var uc = new techgy.utils.UICell();
					 var c = r.cells[c];
					 uc.value = c.value;
					 uc.columnSeq = c.columnSeq;
					 ur.cells.push(uc);
				 }
				 t.rows.push(ur);
			 }
		 }
		t.vos = tj.vos;
		return t;
	 }
	 
	 this.createUITable = function(t,optDoc) {
		 var doc = optDoc || document;
		 var table = techgy.dom.createElt('table',null,doc);
		 var thead = techgy.dom.createElt('thead',null,doc);
		 var tbody = techgy.dom.createElt('tbody',null,doc);
		 table.appendChild(thead);
		 table.appendChild(tbody);
		 if(!t || t.vodef.length <= 0) {
			 return table;
		 }
		 var hrowui = techgy.dom.createElt('tr',null,doc);
		 
		 var sa = techgy.dom.createElt('th',null,doc);
		 var selectInput = techgy.dom.createElt('input',null,doc);
		 selectInput.type='checkbox';
		 techgy.dom.writeText(selectInput,'selectAll',doc);
		 sa.appendChild(selectInput);
		 hrowui.appendChild(sa);
		 var iddef = null;
		 for(var hi in t.vodef) {
			 var h = t.vodef[hi];
			 if(!iddef && h.ableId) {
				 iddef = h;
			 }
			 if(!h || !h.queryDef || h.queryDef.unvisible) {
				 continue;
			 }
			 var hcell = techgy.dom.createElt('th',null,doc);
			 techgy.dom.writeText(hcell,h.queryDef.displayName,doc);
			 hrowui.appendChild(hcell);
		 }
		 if(!iddef) {
			 throw 'no id def found for :' + t.tableName;
		 }
		 thead.appendChild(hrowui);
		 
		 if(t.rows && t.rows.length > 0) {

                 for(var ri in t.rows) {
                	 var idcell = null;
                	 var r = t.rows[ri];
                	 for(var c in r.cells) {
    					 var c = r.cells[c];
    					  if(c.columnSeq == iddef.queryDef.seq) {
    						  idcell = c;
    						  break;
    					  }
    				 }
                	 if(!idcell) {
                		 throw 'no id def found for :' + t.tableName;
                	 }
                	 
                	 var rowui = techgy.dom.createElt('tr',null,doc);
					 var sd = techgy.dom.createElt('td',null,doc);
					 var selectInput = techgy.dom.createElt('input',null,doc);
					 selectInput['vo'] = r; 
					 selectInput['void'] = idcell;
					 selectInput.type='checkbox';
					 techgy.dom.writeText(selectInput,'select',doc);		
					 sd.appendChild(selectInput);
					 rowui.appendChild(sd);
                	 
                	 for(var hi in t.vodef) {
        				 var h = t.vodef[hi]; 
        				 if(!h || !h.queryDef || h.queryDef.unvisible) {
        					 continue;
        				 }
        				 var cell = null;
        				 var r = t.rows[ri];
                    	 for(var c in r.cells) {
        					 var c = r.cells[c];
        					  if(c.columnSeq == h.queryDef.seq) {
        						  cell = c;
        						  break;
        					  }
        				 }
                    	 if(!cell) {
                    		 throw 'cell not found for :' + t.tableName; 
                    	 }
                    	 var cellUi = techgy.dom.createElt('td',null,doc);
						 techgy.dom.writeText(cellUi,cell.value,doc);
						 rowui.appendChild(cellUi);
        			 }
                	 tbody.appendChild(rowui);
			 }
            
		 }
		 return table;
	 }
	 
	 this.getSelectVO = function(uiTable) {
		 var vos = [];
		 var rows = uiTable.childNodes[1].childNodes;
		 for(var i in rows) {
			 var row = rows[i];
			 if(row.firstChild && row.firstChild.firstChild) {
				 var input = row.firstChild.firstChild;
				 if(input.checked) {
					 vos.push(input['void']);
				 }
			 }
		 }
		 return vos;
	 }
	 
	 this.parseVODefinition = function(defs) {
		 if(typeof defs ==='string') {
			 defs = eval('('+defs+')');
		 }
		 var vos = [];
		 for(var hi in defs) {
			 var obj = defs[hi];
			 var uth = techgy.utils.clone(techgy.utils.clone(obj));
			 vos.push(uth);
		 }
		 return vos;
	 },
	 
	 this.parseQueryCondition = function(uis,defs) {
		 
	 };
	 
 }
 
 
