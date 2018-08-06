package net.techgy.common.models;


public class MsgBody {
     private int type = 0;
     private Object content;
     
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.type;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.content.toString();
	}
     
	
     
}
