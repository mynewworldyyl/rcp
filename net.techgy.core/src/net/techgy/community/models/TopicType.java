package net.techgy.community.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.digitnexus.core.account.Account;

@Entity
@Table(name="t_topic_type")
public class TopicType {

	@Id
	@Column(length = 64)
	private long id;

	@ManyToOne
	@JoinColumn(name = "creator")
	private Account creator;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "descriptor")
	private String desc = "";

	@Column(name = "title", length = 255)
	private String title = "";

	@Column(name = "created_on", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = true)
	private Account createdBy;

	@Column(name = "updated_on", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	@ManyToOne
	@JoinColumn(name = "updated_by", nullable = true)
	private Account updatedBy;

}
