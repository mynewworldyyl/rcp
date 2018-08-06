package net.techgy.common.models;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public enum MessageState {

	OK("success", 3),
	FAIL("fail", 0),
	
	LOGIN_ERR_USERNAME("username error", 1),
	LOGIN_ERR_UN_PW("username or password error", 4),
	LOGIN_ERR_PASSWORD("passwork error", 2);

	private String desc = "";
	private int stateCode = 0;

	MessageState(String desc, int stateCode) {
		this.desc = desc;
		this.stateCode = stateCode;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "code:" + this.stateCode + ",Desc: " + this.desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getStateCode() {
		return stateCode;
	}

	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}

	public static class MessageStateAdapter extends TypeAdapter<MessageState> {

		@Override
		public MessageState read(JsonReader reader) throws IOException {
			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return null;
			}
			String str = reader.nextName();
			int stateCode = reader.nextInt();
			for (MessageState ms : MessageState.values()) {
				if (stateCode == ms.getStateCode()) {
					return ms;
				}
			}
			return null;
		}

		@Override
		public void write(JsonWriter writer, MessageState state)
				throws IOException {

			if (state == null) {
				writer.nullValue();
				return;
			}
			StringBuffer sb = new StringBuffer();
			sb.append("{\"desc\":").append("\"" + state.getDesc()+"\"")
			.append(",\"stateCode\":").append(state.getStateCode())
			.append("}");
			writer.value(sb.toString());
		}
	}
}
