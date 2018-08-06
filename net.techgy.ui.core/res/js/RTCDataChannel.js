namespace('techgy');
namespace('techgy.va');
techgy.va.DataChannel = function(messageSender, channelLabel) {
    
	if(!messageSender) {
		throw 'messageSender cannot be NULL';
	}
	
	if(!messageSender.notifyMessage) {
		throw 'messageSender.notifyMessage is NULL defined';
	}
	
	if(!messageSender.channelReady) {
		throw 'messageSender.channelReady is NULL defined';
	}
	
	if(!messageSender.onData) {
		throw 'messageSender.onData is NULL defined';
	}
	
	this.messageSender = messageSender;
	
	this.channelLabel = channelLabel;
	
    this.peer = null;
    
    this.channel = null;   
};


techgy.va.DataChannel.prototype.send = function(msg) {
	if(this.channel) {
		this.channel.send(msg);
	}
};

techgy.va.DataChannel.prototype.close = function() {
    if(this.peer) {
    	this.peer = null;
    }
    if(this.isOfferer) {
    	 this.channel.close();
 	}
    this.channel = null;
};

techgy.va.DataChannel.prototype.sendicecandidate = function (candidate) {
	this.messageSender.notifyMessage({
        "candidate": candidate,
    },this.channelLabel);
};

/**
 * sdp.type === 'offer'
 * sdp.sdp
 */
techgy.va.DataChannel.prototype.sendSDP = function(sdp) {
   	//主叫者给Offer对方
   	this.messageSender.notifyMessage({
           "sdp": sdp.sdp,
       },this.channelLabel);
};


techgy.va.DataChannel.prototype.onChannelMessage = function (event) {
	 //console.log(event);
	 this.messageSender.onData(event.data);
};

techgy.va.DataChannel.prototype.onChannelOpened = function (channel) {
    	this.channel = channel;
    	this.messageSender.channelReady(this);
    	console.log(channel);
 };
 
 techgy.va.DataChannel.prototype.onChannelClosed = function (event) {
 	console.log(event);
 };
 
 techgy.va.DataChannel.prototype.onChannelError = function (event) {
	 console.log(event);
 };


techgy.va.DataChannel.prototype.onMessage = function (msg) {      
    if(msg.candidate) {
    	//交换彼此IP，端口等等连接信息，以建立一个端对端连接
        try{
        	this.peer.addICE({
        	    sdpMLineIndex: msg.candidate.sdpMLineIndex,
        	    candidate    : msg.candidate.candidate
        	});    
        }catch(e) {
            console.log(e);
        }   
    }
};


/******************************DataChannel END*********************/  


/*********************************Offer Begin***************************/

techgy.va.OfferDataChannel = function(messageSender, channelLabel) {
	techgy.va.DataChannel.apply(this, arguments);
	//是否是主动方
    this.isOfferer = true;
	
}
techgy.utils.inherits(techgy.va.OfferDataChannel, techgy.va.DataChannel);

techgy.va.OfferDataChannel.prototype.start = function() {
	this.createOfferPeer();
};

techgy.va.OfferDataChannel.prototype.createOfferPeer = function() {
	this.peer = RTCPeerConnection({
		channel : this.channelLabel,
	    onICE     		    : techgy.utils.bind(this,this.sendicecandidate),
	    onOfferSDP    	    : techgy.utils.bind(this,this.sendSDP),
	    onChannelMessage   	: techgy.utils.bind(this,this.onChannelMessage ),
	    onChannelOpened     : techgy.utils.bind(this,this.onChannelOpened ),
	    onChannelClosed     : techgy.utils.bind(this,this.onChannelClosed ),
	    onChannelError     : techgy.utils.bind(this,this.onChannelError ),
	});
};

techgy.va.OfferDataChannel.prototype.onMessage = function (msg) {      
    if(!msg) {
        alert('error');
        throw 'receive error message';
    }
    techgy.va.OfferDataChannel.superClass_.onMessage.call(this,msg)
    if(msg.sdp) {
		this.peer.addAnswerSDP({'sdp': msg.sdp, 'type' : 'answer'});
    }
};


/*********************************Offer End******************************/


/*********************************Answer Begin***************************/

techgy.va.AnswerDataChannel = function(messageSender, channelLabel) {
	techgy.va.DataChannel.apply(this, arguments);
	//是否是主动方
    this.isOfferer = false;
	
}
techgy.utils.inherits(techgy.va.AnswerDataChannel, techgy.va.DataChannel);

techgy.va.AnswerDataChannel.prototype.onMessage = function (msg) {      
    if(!msg) {
        alert('error');
        throw 'receive error message';
    }
    techgy.va.AnswerDataChannel.superClass_.onMessage.call(this,msg)
    if(msg.sdp) {
    	this.createAnswerPeer(msg);
    }
};

techgy.va.AnswerDataChannel.prototype.createAnswerPeer = function(msg) {
	this.peer = RTCPeerConnection({
	    onICE     		   : techgy.utils.bind(this,this.sendicecandidate),
	    offerSDP    	   : { type: 'offer',sdp: msg.sdp},
	    onAnswerSDP      :    techgy.utils.bind(this,this.sendSDP),
	    onChannelMessage   	: techgy.utils.bind(this,this.onChannelMessage ),
	    onChannelOpened     : techgy.utils.bind(this,this.onChannelOpened ),
	    onChannelClosed     : techgy.utils.bind(this,this.onChannelClosed ),
	    onChannelError     :  techgy.utils.bind(this,this.onChannelError ),
	});
};

/*********************************Answer End******************************/
