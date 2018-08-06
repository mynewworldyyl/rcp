package net.techgy.common.models;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class MsgHeader {

	//all header related constants will be place here
	public static final String USERNAME = "userName";
    public static final String PASSWORD = "password";
    
    public static final String CHAT_MESSAGE_TO ="sendMessageTo";
    public static final String CHAT_MESSAGE_FROM= "sendMessageFrom";
    	
    public static String ADD_FRIEND_ACCOUNT = "addFriendAccount";
    
    public MsgHeader(){};
    
    public MsgHeader(String name,String value) {
    	this.name = name;
    	this.value=value;
    }
    
	private String name = null;
    private String value = null;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.name.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof MsgHeader)) {
			return false;
		}
		MsgHeader mh = (MsgHeader)obj;
		return this.name.equals(mh.getName());
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.name).append("=").append(this.value);
		return sb.toString();
	}
	
    
	public static class MessageHeaderAdapter extends TypeAdapter<MsgHeader> {

		@Override
		public MsgHeader read(JsonReader reader) throws IOException {
			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return null;
			}			
			String name = reader.nextString();
			String value = reader.nextString();			
			return new MsgHeader(name,value);
		}

		@Override
		public void write(JsonWriter writer, MsgHeader h)
				throws IOException {

			if (h == null) {
				writer.nullValue();
				return;
			}
			StringBuffer sb = new StringBuffer();
			sb.append("{\"name\":").append("\"" + h.name+"\"")
			.append(",\"value\":").append("\"" + h.value+"\"")
			.append("}");
			writer.value(sb.toString());
		}
	}
	
}
