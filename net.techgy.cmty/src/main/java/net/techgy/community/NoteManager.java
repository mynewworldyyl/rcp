package net.techgy.community;

import java.util.List;

import net.techgy.community.models.INoteDao;
import net.techgy.community.models.Note;
import net.techgy.community.models.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NoteManager implements INoteManager{

	@Autowired
	private INoteDao noteDao;
	
	@Autowired
	private ITopicManager topicManager;
	
	@Transactional
	@Override
	public List<Note> queryByTopicId(long topicId, int pageIndex) {
		// TODO Auto-generated method stub
		int firstIndex = NOTE_PAGE_SIZE * pageIndex;
		return this.noteDao.queryByTopicId(topicId, firstIndex, NOTE_PAGE_SIZE);
	}
	@Transactional
	@Override
	public NoteInfo totalPageByTopicId(long topicId) {
		int count = this.noteDao.totalNumByTopicId(topicId);
		int pageCount = count/NOTE_PAGE_SIZE;
		if(count % NOTE_PAGE_SIZE != 0) {
			pageCount += 1; 
		}
		NoteInfo ni = new NoteInfo();
		ni.setNotePageNum(pageCount);
		ni.setPageSize(NOTE_PAGE_SIZE);
		ni.setTopicId(topicId);
		return ni;
	}
	
	@Transactional
	@Override
	public void saveNote(Note note) {
        Topic t = this.topicManager.queryTopicById(note.getTid());
        if(t != null) {
        	note.setTopic(t);
        	this.noteDao.save(note);
        }
	}

	@Transactional
	@Override
	public void removeNodeById(long id) {
		// TODO Auto-generated method stub
		this.noteDao.removeNodeById(id);
	}

	
}
