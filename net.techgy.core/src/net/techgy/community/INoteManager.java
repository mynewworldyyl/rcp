package net.techgy.community;

import java.util.List;

import net.techgy.community.models.Note;


public interface INoteManager {

	int NOTE_PAGE_SIZE = 5;
	
	List<Note> queryByTopicId(long topicId,int pageIndex);
	
	NoteInfo totalPageByTopicId(long topicId);
	
	void saveNote(Note note);
	
	void removeNodeById(long id);

}
