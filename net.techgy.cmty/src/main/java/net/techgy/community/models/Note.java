package net.techgy.community.models;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="t_note")
public class Note {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="creator")
	private String creator = "";
	
	@Column(name="description")
	private String text = "";
	
	@Column(name="createDate")
	private Date createDate;

	@ManyToOne  
    @JoinColumn(name="topicId")
	private Topic topic;
	
	@Transient
	private long tid;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", creator=" + creator + ", text=" + text
				+ ", topic=" + topic + "]";
	}

	public long getTid() {
		return tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}
    
	
}
