techgy = techgy || {};

navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia ||
    navigator.mozGetUserMedia || navigator.msGetUserMedia;

window.URL = window.URL || window.webkitURL || window.mozURL || window.msURL;

window.RTCPeerConnection = window.webkitRTCPeerConnection 
    || window.mozRTCPeerConnection || window.msRTCPeerConnection

    
techgy.va = new function() {
    
	this.sigalChannel = null;
	
	this.localMeidiaState=null;
	
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
	
    this.peer_ = null;
        
    this.configuration = {
    	   iceServers: [
    	                 {url: "stun:23.21.150.121"},
    	                 {url: "stun:stun.l.google.com:19302"},
    	                 {url: "turn:numb.viagenie.ca", credential: "webrtcdemo", username: "louis%40mozilla.com"}
    	             ]
    };
    
   this.mediaConstraints = {
            optional: [],
            mandatory: {
                OfferToReceiveAudio: true,
                OfferToReceiveVideo: true
            }
     };
    
    this.hIncStep = 80;
    
    this.vIncStep = 60;
    
    /**
     * 打开两个窗口，
     * localPanel: 本地视频窗口 net.cmty.ui.core.video.Video实例
     * remotePanel: 对方视频窗口 net.cmty.ui.core.video.Video实例
     * isCaller ： 是否是主叫方
     * optDoc Document
     */
    
    this.createPeeer = function() {
    	
    	this.peer_ = new RTCPeerConnection(this.configuration);
    	
    	 // send any ice candidates to the other peer
    	this.peer_.onicecandidate = techgy.utils.bind(this,function (evt) {
        	this.sigalChannel.notify("message", {
                'type':techgy.va.Constans.CANDIDATE,
                "candidate": evt.candidate,
                'to': this.reAn,
                'from': this.myAn
            });
        });
        
        // once remote stream arrives, show it in the remote video element
        this.peer_.onaddstream = techgy.utils.bind(this,function (evt) {
        	//与对方连接建立
            this.remoteStream_ = evt.stream;
            if(!this.remoteStream_) {
            	//对方视频不可用
                alert('fail to get remote stream');
            } else {  
            	this.remotePanel.src = window.URL.createObjectURL(this.remoteStream_);
            }
        });
        if(this.localStream_) {
        	//for 被动方执行
        	this.peer_.addStream(this.localStream_);
        }
    }
        
    this.start = function(params) {
    	//建立己方与对方连接实例
    	this.reAn = params.to;
    	this.myAn = params.from;
    	
        if(this.isCaller) {
        	 //主叫创建连接
        	 this.createPeeer();
        }
        
        if(this.isCaller) {
       	 this.peer_.createOffer(techgy.utils.bind(this, function(desc) {
       		 this.peer_.setLocalDescription(desc);
	        	//主叫者给Offer对方
	        	this.sigalChannel.notify("message", {
	                'type':techgy.va.Constans.OFFER,
	                'video': this.video_,
	                "sdp": desc,
	                'to': this.reAn,
	                'from': this.myAn
	            });
	        
       	 }));
       }
        
        navigator.getUserMedia({
            video : this.video_,
            audio : true
        }, techgy.utils.bind(this, function(stream){
            this.localStream_ = stream;
            if(this.peer_) {
            	//for caller to execute
            	this.peer_.addStream(stream);
            }
            this.localPanel.src = window.URL.createObjectURL(this.localStream_);
        }), function(error) {
            console.log(error);
            this.localMeidiaState="Failure";
        });
        
        
    }
       
    this.onMessage = function (msg) {       
        if(!msg) {
            alert('error');
            throw 'receive error message';
        }
        switch(msg.type) {
            case techgy.va.Constans.OFFER: {
            	 //被叫收到Offer之后，才能创建连接
            	this.createPeeer();
                try{
                	//设置主动方信息到本地
                    this.peer_.setRemoteDescription(new RTCSessionDescription(msg.sdp));//12
                }catch(e){
                    console.log("setRemoteDescription error.",e.message);
                    throw e;
                }
                //给主动方一个应答，并告诉对本地信息
                this.peer_.createAnswer(techgy.utils.bind(this,function(desc){
                	this.peer_.setLocalDescription(desc);
                	//被叫方发应答
                	this.sigalChannel.notify("message", {
                        'type':techgy.va.Constans.ANSWER,
                        'video': this.video_,
                        "sdp": desc,
                        'to': this.reAn,
                        'from': this.myAn
                    });
                }));
                break;
            }  
            case techgy.va.Constans.ANSWER: {
               //主动方收到应答   	
                //主动方接受到对方信息，并设置到本地
                try{
                    this.peer_.setRemoteDescription(new RTCSessionDescription(msg.sdp));//12
                }catch(e){
                    console.log("setRemoteDescription error.",e.message);
                    throw e;
                }
                break;
            }
            case techgy.va.Constans.CANDIDATE: {
            	//交换彼此IP，端口等等连接信息，以建立一个端对端连接
                try{
                    if(msg.candidate) {
                        this.peer_.addIceCandidate(new RTCIceCandidate(msg.candidate));
                    }     
                }catch(e) {
                    console.log(e);
                }   
                break;
            }
        }     
    };
    
   



techgy.va.Constans ={
    INVITE: 'invite',
    OFFER: 'offer',
    ANSWER: 'answer',
    CANDIDATE: 'candidate',
    SDP:'sdp',
    END: 'end',
    GET_MEDIA_STATUS: 'media_status',
    GET_MEDIA_SUCCESS: 'success',
    GET_MEDIA_FAILURE: 'failure'
};

