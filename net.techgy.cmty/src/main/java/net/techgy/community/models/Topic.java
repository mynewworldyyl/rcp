package net.techgy.community.models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="t_topic")
public class Topic {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="creator")
	private String creator = "";
	
	@Lob
    @Basic(fetch=FetchType.LAZY)
	@Column(name="descriptor")
	private String desc = "";
	
	@Column(name="title", length=255)
	private String title = "";
	
	@Column(name="createDate")
	private Date createDate;
	

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Topic [id=" + id + ", creator=" + creator + ", desc=" + desc
				+ ", title=" + title + "]";
	}

	@Override
	public Topic clone() throws CloneNotSupportedException {
		Topic t = new Topic();
		t.setCreateDate(this.getCreateDate());
		t.setCreator(this.getCreator());
		t.setDesc(this.getDesc());
		t.setId(this.getId());
		t.setTitle(this.getTitle());
		return t;
	}
	
	
}
