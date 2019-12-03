package com.revature.rpm.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name = "Content_Type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Notification {

	@Id
	@Column(name = "notification_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int notificationId;
	
	@Column(name = "project_id")
	private int projectId;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "is_read", columnDefinition = "boolean default false")
	private boolean isRead;
	
	// Does not currently auto-generate date as a default. Need to look into if adtl time. 
	@Column(name = "date_created")
	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + (isRead ? 1231 : 1237);
		result = prime * result + notificationId;
		result = prime * result + projectId;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notification other = (Notification) obj;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (isRead != other.isRead)
			return false;
		if (notificationId != other.notificationId)
			return false;
		if (projectId != other.projectId)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Notification [notificationId=" + notificationId + ", projectId=" + projectId + ", title=" + title
				+ ", isRead=" + isRead + ", dateCreated=" + dateCreated + "]";
	}

	public Notification(int notificationId, int projectId, String title, boolean isRead, Date dateCreated) {
		super();
		this.notificationId = notificationId;
		this.projectId = projectId;
		this.title = title;
		this.isRead = isRead;
		this.dateCreated = dateCreated;
	}

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}


	
}
