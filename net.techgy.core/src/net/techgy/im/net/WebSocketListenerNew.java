
/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.techgy.im.net;

import java.io.IOException;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.techgy.common.models.IMessage;
import net.techgy.common.models.MessageImpl;
import net.techgy.common.models.MessageState;
import net.techgy.common.models.MessageType;
import net.techgy.common.models.MsgHeader;
import net.techgy.spring.Context;
import net.techgy.usercenter.AccountService;

import org.apache.log4j.Logger;

import com.digitnexus.base.utils.JsonUtils;

@ServerEndpoint(value = "/va")
public class WebSocketListenerNew {

   // private static final Log log = LogFactory.getLog(ChatAnnotation.class);
    private static final long serialVersionUID = -7178893327801338294L;  
    private Logger logger = Logger.getLogger(this.getClass());
    
    private ISessionManager manager;
    
    private ISession isession;

    public WebSocketListenerNew() {
        this.manager = Context.getBean(ISessionManager.class);
    }

    @OnClose
    public void onClose() {
      logger.debug("onClose");
      this.manager.removeSession(this.isession.getSessionId());
      this.manager = null;
      this.isession=null;
    }


    @OnMessage
    public void incoming(String msg) {
    	IMessage message = JsonUtils.getInstance().fromJson(msg, MessageImpl.class,false,false);
		if(message == null){
			logger.warn("receive NULL message");
			return;
		}
		this.manager.pushMessage(message);
    }

    @OnOpen
    public void onOpen(Session wsession) {
    	Map<String,String> params = wsession.getPathParameters();  	 
    	String un = params.get(MsgHeader.USERNAME);
		String pw = params.get(MsgHeader.PASSWORD);
		MessageImpl msg = new MessageImpl();
		msg.addHeader(MsgHeader.USERNAME, un);
		msg.setType(MessageType.RESP_LOGIN);
		
		if(!this.manager.getLoginManager().isLogin(un)) {
			if(this.manager.getAccountService().login(un, pw).equals(AccountService.FAIL)) {
				//fail go login
				msg.setStatus(MessageState.LOGIN_ERR_UN_PW);	
				try {
					wsession.getBasicRemote().sendText(JsonUtils.getInstance().toJson(msg,false));
				} catch (IOException e) {
				   logger.error("Write message ERROR: ", e);
				}
				return;
			}		
		}
		
		isession = this.manager.createSession(null, wsession,un,pw);
		this.manager.addSession(isession);
		msg.setStatus(MessageState.OK);
		isession.writeMessage(msg);	
	}


    @OnError
    public void onError(Throwable t) throws Throwable {
        logger.error("Chat Error: " + t.toString(), t);
    	this.onClose();
    }
	
}
