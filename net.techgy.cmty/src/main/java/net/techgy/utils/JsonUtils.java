package net.techgy.utils;

import net.techgy.common.models.MessageState;
import net.techgy.common.models.MessageState.MessageStateAdapter;
import net.techgy.common.models.MessageType;
import net.techgy.common.models.MessageType.MessageTypeAdapter;
import net.techgy.common.models.MsgHeader;
import net.techgy.common.models.MsgHeader.MessageHeaderAdapter;

import com.google.gson.GsonBuilder;

public class JsonUtils {

	private static JsonUtils instance = null;
	private static GsonBuilder builder = null;
	
	private JsonUtils() {
		
	}
	public synchronized static JsonUtils getInstance() {
		if(instance == null) {
			instance = new JsonUtils();
		}
		return instance;
	}
	
	private GsonBuilder builder() {
		GsonBuilder builder = new GsonBuilder();
		//builder.registerTypeAdapter(MessageType.class, new MessageTypeAdapter());
		//builder.registerTypeAdapter(MessageState.class, new MessageStateAdapter());
		//builder.registerTypeAdapter(MsgHeader.class, new MessageHeaderAdapter());
		//builder.registerTypeAdapter(MessageState.class, new MessageStateAdapter());
		return builder;
	}
	
	public<T> T fromJson(String json, Class<T> c) {
		return this.builder().create().fromJson(json, c);
	}
	
	public String toJson(Object obj) {
		return this.builder().create().toJson(AOPUtil.getTarget(obj));
	}
	
	public<T> T fromJson(String json, Class<T> c,boolean isModify,boolean recovery) {
		GsonBuilder builder = new GsonBuilder();
		//builder.registerTypeAdapter(MessageType.class, new MessageTypeAdapter());
		//builder.registerTypeAdapter(MessageState.class, new MessageStateAdapter());
		if(recovery) {
			this.processFromJson(json);
		}
		if(isModify) {
			json = this.modifyGjonBug(json);
		}
		return builder.create().fromJson(json, c);
	}
	
	public<T> T fromJson(String json, java.lang.reflect.Type type,boolean isModify,boolean recovery) {
		GsonBuilder builder = new GsonBuilder();
		//builder.registerTypeAdapter(MessageType.class, new MessageTypeAdapter());
		//builder.registerTypeAdapter(MessageState.class, new MessageStateAdapter());
		if(recovery) {
			this.processFromJson(json);
		}
		if(isModify) {
			json = this.modifyGjonBug(json);
		}
		return builder.create().fromJson(json, type);
	}
	
	public String toJson(Object obj,boolean process) {
		GsonBuilder builder = new GsonBuilder();
		//builder.registerTypeAdapter(MessageType.class, new MessageTypeAdapter());
		//builder.registerTypeAdapter(MessageState.class, new MessageStateAdapter());
		String json = builder.create().toJson(obj);
		if(process) {
			json = this.processToJson(json);
		}
		return json;
	}
	
	public String toJson(Object obj,java.lang.reflect.Type type,boolean process) {
		GsonBuilder builder = new GsonBuilder();
		//builder.registerTypeAdapter(MessageType.class, new MessageTypeAdapter());
		//builder.registerTypeAdapter(MessageState.class, new MessageStateAdapter());
		String json = builder.create().toJson(obj,type);
		if(process) {
			json = this.processToJson(json);
		}
		return builder.create().toJson(obj,type);
	}
	
	 public String processToJson(String json) {
	    	json = json.replaceAll("\\{", "(");
	    	json = json.replaceAll("\\}", ")");
	    	json = json.replaceAll("\"", "@@");
	    	return json;
	 }
	 
	 public String processFromJson(String json) {
	    	json = json.replaceAll("(","\\{");
	    	json = json.replaceAll(")","\\}");
	    	json = json.replaceAll( "@@","\"");
	    	return json;
	 }
	 
	 public String modifyGjonBug(String content) {
			content=content.substring(1, content.length()-1);
			content = content.replaceAll("\\\\", "");
			return content;
		}
}
