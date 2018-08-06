package net.techgy.cmty.core.im.vo;

import java.util.HashMap;
import java.util.Map;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.core.idgenerator.IDStrategy;

@IDStrategy(cacheSize=3)
public class MessageVo {

	public static final String MODEL_FRIEND="friend";
	
	public static final String MODEL_ROOM="room";
	
	public static final String MODEL_MEDIA_CHAT="room";
	
	public static final String RESP_FOR_MSG_ID="respMessageId";
	
	//端对端聊天信息
	//public static final String TYPE_P2P ="p2p";
	
	//广播信息
	public static final String TYPE_BROACAST ="broacast";
	
	//系统信息
	public static final String TYPE_SYS ="sys";
	
	//事件中代码MessgeVo的键会值
	public static final String MSG_KEY ="messageKey";
	
	//更新显示列表
	public static final String TYPE_REFLESH_LIST ="refleshList";
	
	//更新成员
	public static final String TYPE_REFLESH_MEMBER ="refleshMember";
	
	//被另外的账户增加
	public static final String TYPE_ADDED_AS_MENBER ="addedAsMember";
	
	//普通聊天信息
	public static final String TYPE_FRIEND_VIDEO ="video";
		
	//普通聊天信息
	public static final String TYPE_CHAT ="chat";
	
	//文件共享
	public static final String TYPE_SHARE_FILE ="fileShare";
	public static final String TYPE_SHARE_MUSIC ="musicShare";
	public static final String TYPE_SHARE_VIDEO ="videoShare";
	
	public static final String TYPE_FILE_REQ ="reqFile";
	
	//普通聊天信息
	public static final String TYPE_MEDIA_ACCEPT ="accept";
	
	//IM信息主题前缀
	public static final String CMTY_FRIEND_TOPIC="net/techgy/cmty/core/im/friend/";
	
	//IM信息主题前缀
	public static final String CMTY_GROUP_TOPIC="net/techgy/cmty/core/im/group/";
	
	public static final String CMTY_VIDEO_TOPIC="net/techgy/cmty/core/im/video/";
	
	private String id;
	
	private String seqId = "";
	
	private String from = "" ;
	
	private String to = "" ;
	
	private String content = "" ;
	
	private String msgType = TYPE_CHAT;
	
	private String modelId="";
	
	private String topic;
	
	private byte[] data;
	
	private boolean handled = false;
	
	
	
	private Map<String,Object> params = new HashMap<String,Object>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void putString(String key, String value) {
		if(this.params.containsKey(key)) {
			throw new CommonException("KeyIsExist",key);
		}
		this.params.put(key, value);
	}
	
	public void putByte(String key, Byte value) {
		if(value == null) {
			this.putString(key, null);
		}
		this.putString(key, value.toString());
	}
	
	public void putShort(String key, Short value) {
		if(value == null) {
			this.putString(key, null);
		}
		this.putString(key, value.toString());
	}
	
	public void putInteger(String key, Integer value) {
		if(value == null) {
			this.putString(key, null);
		}
		this.putString(key, value.toString());
	}
	
	public void putLong(String key, Long value) {
		if(value == null) {
			this.putString(key, null);
		}
		this.putString(key, value.toString());
	}
	
	public void putFloat(String key, Float value) {
		if(value == null) {
			this.putString(key, null);
		}
		this.putString(key, value.toString());
	}
	
	public void putDouble(String key, Double value) {
		if(value == null) {
			this.putString(key, null);
		}
		this.putString(key, value.toString());
	}
	
	public void putBoolean(String key, Boolean value) {
		if(value == null) {
			this.putString(key, null);
		}
		this.putString(key, value.toString());
	}
	
	public String getString(String key) {
		if(this.params.get(key) != null) {
			return this.params.get(key).toString();
		}
		return null;
	}
	
	public Object getObject(String key) {
		return this.params.get(key);
	}
	
	public void putObject(String key,Object obj) {
		this.params.put(key,obj);
	}
	
	public Byte getByte(String key) {
		return Byte.valueOf(this.getString(key));
	}
	
	public Short getShort(String key) {
		return Short.valueOf(this.getString(key));
	}
	
	public Integer getInteger(String key) {
		return Integer.valueOf(this.getString(key));
	}
	
	public Long getLong(String key) {
		return Long.valueOf(this.getString(key));
	}
	
	public Float getFloat(String key) {
		return Float.valueOf(this.getString(key));
	}
	
	public Double getDouble(String key) {
		return Double.valueOf(this.getString(key));
	}
	
	public Boolean getBoolean(String key) {
		return Boolean.valueOf(this.getString(key));
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MessageVo)) {
			return false;
		}
		MessageVo m = (MessageVo)obj;
		if(this.seqId != null) {
			if(!this.seqId.equals(m.getSeqId())) {
				return false;
			}
		}
		if(!m.msgType.equals(this.msgType)) {
			return false;
		}
		if(!m.from.equals(this.from)) {
			return false;
		}
		if(!m.to.equals(this.to)) {
			return false;
		}
		if(!m.modelId.equals(this.modelId)) {
			return false;
		}
		if(!this.content.equals(m.getContent())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.from).append("-->").append(this.to)
		.append(" Content: ").append(this.content)
		.append("modelId: ").append(this.modelId);
		return sb.toString();
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public boolean isHandled() {
		return handled;
	}

	public void setHandled(boolean handled) {
		this.handled = handled;
	}
	
	
}
