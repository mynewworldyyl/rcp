
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

import org.apache.log4j.Logger;

@ServerEndpoint(value = "/chatting")
public class VedioAutioSocketListenerNew {

    private static final long serialVersionUID = -7178893327801338294L;  
    private Logger logger = Logger.getLogger(this.getClass());
    
    private Session session;

    public VedioAutioSocketListenerNew() {
    }

    private static Session client;
    
    private static Session server;

    @OnOpen
    public void start(Session wsession) {
        this.session = wsession;
        Map<String,String> params = wsession.getPathParameters();  	 
        String who = params.get("who");
		 if("client".equals(who)) {
			  client = wsession;
		 } else {
			  server = wsession;
		 }
    }


    @OnClose
    public void end() {
    	 client = null;
    	 server = null;
    }


    @OnMessage
    public void incoming(Session session,String message) {
      try {
		if(client == session) {
			  server.getBasicRemote().sendText(message);
		  }else {
			  client.getBasicRemote().sendText(message);
		  }
	} catch (IOException e) {
		logger.error("", e);
	}
    }


    @OnError
    public void onError(Throwable t) throws Throwable {
        logger.error("Chat Error: " + t.toString(), t);
    }
}
