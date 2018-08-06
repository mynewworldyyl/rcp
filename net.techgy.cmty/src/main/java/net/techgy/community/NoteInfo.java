package net.techgy.community;

public class NoteInfo {

	 private long topicId;
	 
	 private int pageSize;
	 private int notePageNum;
	 
	public long getTopicId() {
		return topicId;
	}
	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getNotePageNum() {
		return notePageNum;
	}
	public void setNotePageNum(int notePageNum) {
		this.notePageNum = notePageNum;
	}
	
}
