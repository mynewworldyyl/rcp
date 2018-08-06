package net.techgy.community;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.techgy.community.models.Note;
import net.techgy.community.models.Topic;
import net.techgy.usercenter.AccountManager;
import net.techgy.usercenter.LoginManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.digitnexus.base.utils.JsonUtils;


@Path("/cmty")
@Component
@Qualifier("communityService")
public class CommunityServiceImpl{

	@Autowired
	private LoginManager longinManager;
	
	private AccountManager accountManager;
	
	@Autowired
	private INoteManager noteManager;
	
	@Autowired
	private ITopicManager topicManager;
	
	@GET
	@Path("/topiclist")
	@Produces(MediaType.APPLICATION_JSON)
	public String topicList(@QueryParam("qStr") String qStr, @QueryParam("qType") String qType,
			@QueryParam("pageIndex") int pageIndex) {
		List<Topic> result = topicManager.queryTopic(qType, qStr, pageIndex);
		return JsonUtils.getInstance().toJson(result,false);
	}
	
	@POST
	@Path("/ct")
	@Produces(MediaType.APPLICATION_JSON)
	public String createTopic(@QueryParam("userName") String accountName,String json) {
		String result = this.checkUser(accountName);
		if( result != null) {
			return result;
		}
		Topic topic = JsonUtils.getInstance().fromJson(json, Topic.class);
		if(topic == null) {
			return "dataFormatError";
		}
		
		this.topicManager.saveTopic(topic);
		return "success";
	}
	
	@POST
	@Path("/cn")
	@Produces(MediaType.APPLICATION_JSON)
	public String createNote(@QueryParam("userName") String accountName,String json) {
		String result = this.checkUser(accountName);
		if( result != null) {
			return result;
		}
		Note note = JsonUtils.getInstance().fromJson(json, Note.class);
		if(note == null) {
			return "dataFormatError";
		}
		this.noteManager.saveNote(note);
		return "success";
	}
	
	
	@GET
	@Path("/notelist")
	@Produces(MediaType.APPLICATION_JSON)
	public String noteList(@QueryParam("topicId") long topicId, @QueryParam("pageIndex") int pageIndex) {
		List<Note> result = this.noteManager.queryByTopicId(topicId, pageIndex);
		if(result == null) {
			return null;
		}
		return JsonUtils.getInstance().toJson(result,false);
	}
	
	@GET
	@Path("/ti")
	@Produces(MediaType.APPLICATION_JSON)
	public String topicInfo(@QueryParam("qStr") String qStr, @QueryParam("qType") String qType) {
		TopicInfo ti = this.topicManager.topicInfo(qStr, qType);
		if(ti == null) {
			return "";
		}
		return JsonUtils.getInstance().toJson(ti,false);
	}
	
	@GET
	@Path("/ni")
	@Produces(MediaType.APPLICATION_JSON)
	public String noteInfo(@QueryParam("topicId") int topicId) {
		NoteInfo ti = this.noteManager.totalPageByTopicId(topicId);
		if(ti == null) {
			return "";
		}
		return JsonUtils.getInstance().toJson(ti,false);
	}
	
	private String checkUser(String accountName) {
		if(accountName == null || "".equals(accountName.trim())) {
			return "nonLogin";
		}
		if(!longinManager.isLogin(accountName)) {
			return "nonLogin";
		}
		return null;
	}
}
