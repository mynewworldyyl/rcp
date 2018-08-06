package net.techgy.common.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.techgy.common.models.IMessage.MT;
import net.techgy.im.handler.MessagePriority;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

@Document(collection="message")
public class MessageImpl implements IMessage {

	private Map<String,MsgHeader> headers = new HashMap<String,MsgHeader>();
	
	private List<MsgBody> bodies = new ArrayList<MsgBody>();
	
	private MessageType type = MessageType.DISPATCH_HANDLER;
	
	@Transient
	private MessageState status = MessageState.OK;
	
	@Transient
	private MT mt = new MT();
	
	private boolean clientMessage = true;
	
	public MessageType getType() {
		return type;
	}

	public MessageState getStatus() {
		return status;
	}


	public void setStatus(MessageState status) {
		this.status = status;
	}


	public void setType(MessageType type) {
		this.type = type;
	}


	@Override
	public String getHeaderByName(String name) {
		// TODO Auto-generated method stub
		if(headers == null) {
			return null;
		}
		MsgHeader h = this.getHeader(name);
		if(null != h) {
		   return h.getValue();
		}
		return null;
	}

	

	@Override
	public MsgHeader getHeader(String name) {
		if(headers == null) {
			return null;
		}
		return this.headers.get(name);
	}

	@Override
	public MsgBody getSingleBody() {
        for(MsgBody b : this.bodies) {
        	if(b.getType() == this.type.getType()) {
        		return b;
        	}
        }
		return null;
	}

	@Override
	public MsgBody getBody(int key) {
        for(MsgBody b : this.bodies) {
        	if(b.getType() == key) {
        		return b;
        	}
        }
		return null;
	}


	@Override
	public void addHeader(String key, String value) {
		MsgHeader mh  = new MsgHeader();
		mh.setName(key);
		mh.setValue(value);
		this.headers.put(key, mh);
	}

	
	@Override
	public void addBody(int key, Object value) {
		MsgBody mb = new MsgBody();
		mb.setContent(value);
		mb.setType(key);
		this.bodies.add(mb);
	}


	@Override
	public String getUserId() {
		MsgHeader mh = headers.get(MsgHeader.USERNAME);
		if(null == mh) {
			return null;
		}
		return mh.getValue();
	}

	@Override
	public MessagePriority getPriority() {
		MsgHeader mh = this.headers.get(PRIORITY);
		if(mh == null){
			return MessagePriority.NORMAL;
		}
		int value = Integer.parseInt(mh.getValue());
		if(0 == value) {
			return MessagePriority.LOW;
		}else if(1 == value) {
			return MessagePriority.NORMAL;
		}else if(2 == value) {
			return MessagePriority.HEIGHT;
		}
		return MessagePriority.LOW;
	}


	public MT getMt() {
		return mt;
	}


	public void setMt(MT mt) {
		this.mt = mt;
	}


	public boolean isClientMessage() {
		return clientMessage;
	}


	public void setClientMessage(boolean clientMessage) {
		this.clientMessage = clientMessage;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("type=").append(this.getType())
		.append(",resend:").append(this.getMt().getResendCount())
		.append(",Header: ").append(this.headers.toString())
		.append("body").append(this.bodies.toString());
		return sb.toString();
	}
		
	public static class MessageAdapter extends TypeAdapter<MessageImpl> {

		@Override
		public MessageImpl read(JsonReader reader) throws IOException {
			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return null;
			}
			MessageImpl m = new MessageImpl();
			
			return null;//new MsgHeader(name,value);
		}

		@Override
		public void write(JsonWriter writer, MessageImpl h)
				throws IOException {

			if (h == null) {
				writer.nullValue();
				return;
			}
			StringBuffer sb = new StringBuffer();
			/*sb.append("{\"name\":").append("\"" + h.name+"\"")
			.append(",\"value\":").append("\"" + h.value+"\"")
			.append("}");*/
			writer.value(sb.toString());
		}
	}
}
