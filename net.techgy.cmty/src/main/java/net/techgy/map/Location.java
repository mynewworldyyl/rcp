package net.techgy.map;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.techgy.im.models.UserImpl;

@Entity
@Table(name="t_location")
public class Location {

	@Id
	@GeneratedValue
	private long id;
	
	//经度
	@Column(name="lng")
	private Double longitude;
	
	//纬度
	@Column(name="lat")
	private Double latitude;
	
	@Column(name="name")
	private String name;
	
	@Column(name="descr")
	private String desc;
	
	@ManyToOne
    @JoinColumn(name="created_by")
	private UserImpl createdBy;
	
	@Column(name="created_on")
	private Timestamp createdOn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public UserImpl getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserImpl createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
}
