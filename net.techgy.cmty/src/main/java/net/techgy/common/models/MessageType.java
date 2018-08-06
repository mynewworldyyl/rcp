package net.techgy.common.models;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public enum MessageType {
	
	  DISPATCH_HANDLER(-1),
	  
	  TEST_MESSAGE_KE_REQ(1),
	  TEST_MESSAGE_KE_RESP(2),
	  
	  REQ_LOGIN(3),
	  RESP_LOGIN(4),
	  
	  REQ_ADD_FRIEND(5),
	  RESP_ADD_FRIEND(6),
	  
	  REQ_GET_FRIEND_LIST(7),
	  RESP_GET_FRIEND_LIST(8),
	  
	  REQ_SEARCH_USER_LIST(9),
	  RESP_SEARCH_USER(10),
	  
	  REQ_CHAT_MESSAGE(11),
	  RESP_CHAT_MESSAGE(12),
	  
	  REQ_LOGOUT(13),
	  RESP_LOGOUT(14),
	  
	  REQ_REGISTER(15),
	  RESP_REGISTER(16),
	    
	  REQ_UNREGISTER(17),
	  RESP_UNREGISTER(18),
	  
	  REQ_VA_MESSAGE(19),
	  RESP_VA_MESSAGE(20);
	    
    private int type = -1;
	MessageType(int type){
		this.type = type;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public static class MessageTypeAdapter extends TypeAdapter<MessageType> {

		@Override
		public MessageType read(JsonReader reader) throws IOException {
			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return null;
			}
			int type = reader.nextInt();
			for (MessageType ms : MessageType.values()) {
				if (type == ms.getType()) {
					return ms;
				}
			}
			return null;
		}

		@Override
		public void write(JsonWriter writer, MessageType type)
				throws IOException {
			if (type == null) {
				writer.nullValue();
				return;
			}
			writer.value(type.getType());
		}
	}
}
