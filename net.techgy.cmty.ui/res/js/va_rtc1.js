techgy = techgy || {};

navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia ||
navigator.mozGetUserMedia || navigator.msGetUserMedia;

window.URL = window.URL || window.webkitURL || window.mozURL || window.msURL;

window.RTCPeerConnection = window.webkitRTCPeerConnection 
|| window.mozRTCPeerConnection || window.msRTCPeerConnection

techgy.va = new function() {
    
	this.mediaService = null;
	
	this.video = false;
	  
	//对方账号
	this.reAn = null;
	
	//自身账号
	this.myAn = null;
	
	 //是否是主动方
    this.isCaller = true;
	
	//对方视频UIt the vedio control
	this.remotePanel = null;
	
	//己方视频UI
	this.localPanel = null;
	
	//己方视频流
    this.localStream_ = null;
    
    //对方视频流
    this.remoteStream_ = null;
	
    this.peer = null;
    
    this.isOpenLocalStream = false;
        
   this.mediaConstraints = {
            optional: [],
            mandatory: {
                OfferToReceiveAudio: true,
                OfferToReceiveVideo: true
            }
     };
    
    this.hIncStep = 80;
    
    this.vIncStep = 60;
    
    this.start = function(isCaller) {
    	if(this.mediaService) {
     		this.mediaService.usingChange({'using':true});
     	}
    	this.isCaller= isCaller;
    	if(this.isCaller) {
     		this.createOfferPeer();
     	}
    }
    
    this.gotRemoteStreamEnded = function(remoteMediaStream) {
    	
    }
    
    this.sendMediaState = function() {
    	if(this.mediaService){
    		this.mediaService.sendMessage({
             'type':techgy.va.Constans.GET_MEDIA_STATUS,
             "state": this.localMeidiaState,
             'to': this.reAn,
             'from': this.myAn
         });
    	}
    }
    
    this.openCamera  = function(video, callback) {
    	this.video_ = video;
    	var succeFunc = techgy.utils.bind(this, function(localStream){
            this.localStream_ = localStream;
            if(this.localPanel) {
            	 this.localPanel.src = window.URL.createObjectURL(localStream);
            }
            this.localMeidiaState = techgy.va.Constans.GET_MEDIA_SUCCESS;
            this.sendMediaState();
            if(this.mediaService) {
            	this.mediaService.cameraStatu({
	                        'type':techgy.va.Constans.GET_MEDIA_STATUS,
	                        "statu": this.localMeidiaState,
            	         });
            }
        });
    	var failFunc = techgy.utils.bind(this,function(error) {
        	//本地视频不可用，但还是可以看对方视频
        	this.localStream_ = null;
            console.log(error);
            this.localMeidiaState=techgy.va.Constans.GET_MEDIA_FAILURE;
            this.sendMediaState();
            if(this.mediaService) {
            	this.mediaService.cameraStatu({
	                        'type':techgy.va.Constans.GET_MEDIA_STATUS,
	                        "statu": this.localMeidiaState,
            	         });
            }
        });
    	
    	navigator.getUserMedia(
    		{
                video : this.video_,
                audio : true
            }, 
            succeFunc, 
            failFunc
            );
    };
    
    
/******************************OFFER SIDE BEGIN*********************/

    this.sendicecandidate = function (candidate) {
    	this.mediaService.sendMessage({
            'type':techgy.va.Constans.CANDIDATE,
            "candidate": candidate,
            'to': this.reAn,
            'from': this.myAn
        });
    };
    
    //if(stream) video.src = webkitURL.createObjectURL(stream);
    this.gotremotetream = function (remoteStream) {
    	//与对方连接建立
    	if(!remoteStream) {
    		 console.log('got error stream');
    		 alert('fail to get remote stream');
    		return;
    	}
    	if(!!this.localStream_  && !!this.localPanel) {
    		this.localPanel.src = window.URL.createObjectURL(this.localStream_ );
         }
        this.remoteStream_ = remoteStream;
        if(!!remoteStream && !!this.remotePanel) {
        	this.remotePanel.src = window.URL.createObjectURL(remoteStream);
        }
    };
    
    /**
     * sdp.type === 'offer'
     * sdp.sdp
     */
    this.sendOfferSDP = function(sdp) {
       	//主叫者给Offer对方
       	this.mediaService.sendMessage({
               'type':techgy.va.Constans.OFFER,
               'video': this.video_,
               "sdp": sdp.sdp,
               'to': this.reAn,
               'from': this.myAn
           });
  	 };
    
    this.createOfferPeer = function() {
    	this.peer = new  RTCPeerConnection(this.configuration);
    	this.peer.onicecandidate=techgy.utils.bind(this,this.sendicecandidate);
    	this.peer.onaddstream = techgy.utils.bind(this,this.gotremotetream);
    	if(this.localStream_) {
        	//for 被动方执行
        	this.peer.addStream(this.localStream_);
        }
    	this.peer.createOffer(techgy.utils.bind(this,this.sendOfferSDP));
    	
    	/*this.peer = RTCPeerConnection({
    	    attachStream	: this.localStream_,
    	    onICE     		: techgy.utils.bind(this,this.sendicecandidate),
    	    onRemoteStream  	: techgy.utils.bind(this,this.gotremotetream),
    	    onRemoteStreamEnded: techgy.utils.bind(this,this.gotRemoteStreamEnded),
    	    onOfferSDP    	: techgy.utils.bind(this,this.sendOfferSDP)
    	});*/
    };
    
/******************************OFFER SIDE END*********************/
    
/******************************OFFER SIDE END*********************/  
    /**
     * sdp.type === 'answer'
     * sdp.sdp
     */
    this.sendAnswerSDP = function(sdp){
    	//被叫方发应答
    	this.mediaService.sendMessage({
            'type':techgy.va.Constans.ANSWER,
            'video': this.video_,
            "sdp": sdp,
            'to': this.reAn,
            'from': this.myAn
        });
    };
    
    this.createAnswerPeer = function(msg) {
    	this.peer = RTCPeerConnection({
    	    attachStream	   : this.localStream_,
    	    onICE     		   : techgy.utils.bind(this,this.sendicecandidate),
    	    onRemoteStream     : techgy.utils.bind(this,this.gotremotetream),
    	    onRemoteStreamEnded: techgy.utils.bind(this,this.gotRemoteStreamEnded),
    	    offerSDP    	   : {
    	    	                   type: 'offer',
    	    	                    sdp: msg.sdp
    	                          },
		    onAnswerSDP      : techgy.utils.bind(this,this.sendAnswerSDP)
    	});
    }
    
    
/******************************OFFER SIDE END*********************/   
    
    this.onMessage = function (msg) {       
        if(!msg) {
            alert('error');
            throw 'receive error message';
        }
        switch(msg.type) {
            case techgy.va.Constans.OFFER: {  
            	this.createAnswerPeer(msg);
                break;
            }  
            case techgy.va.Constans.ANSWER: {
               //主动方收到应答   	
                //主动方接受到对方信息，并设置到本地
            	this.peer.addAnswerSDP(msg.sdp);
                break;
            }
            case techgy.va.Constans.CANDIDATE: {
            	//交换彼此IP，端口等等连接信息，以建立一个端对端连接
                try{
                	this.peer.addICE({
                	    sdpMLineIndex: msg.candidate.sdpMLineIndex,
                	    candidate    : msg.candidate.candidate
                	});    
                }catch(e) {
                    console.log(e);
                }   
                break;
            }
            
            case techgy.va.Constans.GET_MEDIA_STATUS : {
            	this.remosteMediaState = msg.state;
            }
        }     
    };
    
    this.close = function(isCaller) {
    	
        if(this.mediaService) {
     		this.mediaService.usingChange({'using':false});
     	}
        
        if(this.peer) {
        	if(this.localStream_) {
        		this.peer.peer.close;
        	}
        	this.peer = null;
        }
        
        if(this.localStream_) {
        	this.localStream_.stop();
        	this.localStream_ = null;
        }
        
        this.localMeidiaState 
    	//this.mediaService = null;
    	this.video = null;
    	//对方账号
    	this.reAn = null;
    	//自身账号
    	this.myAn = null;
    	 //是否是主动方
        this.isCaller = true;
    	//对方视频UIt the vedio control
    	this.remotePanel = null;
    	//己方视频UI
    	this.localPanel = null;
    	//己方视频流
        this.localStream_ = null;
        //对方视频流
        this.remoteStream_ = null;
        this.peer = null;
        this.isOpenLocalStream = false;
       
    }
};



techgy.va.Constans ={
    OFFER: 'offer',
    ANSWER: 'answer',
    CANDIDATE: 'candidate',
    GET_MEDIA_STATUS: 'mediaStatus',
    GET_MEDIA_SUCCESS: 'success',
    GET_MEDIA_FAILURE: 'failure',
    USING: 'using',
};

