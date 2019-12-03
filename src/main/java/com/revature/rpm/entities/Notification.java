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
	private int notification_id;
	
	@Column(name = "project_id")
	private int project_id;
	
	@Column(name = "is_read", columnDefinition = "boolean default false")
	private boolean isRead;
	
	@Column(name = "date_created")
	@Temporal(TemporalType.DATE)
	private Date date_created;

	public int getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(int notification_id) {
		this.notification_id = notification_id;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date_created == null) ? 0 : date_created.hashCode());
		result = prime * result + (isRead ? 1231 : 1237);
		result = prime * result + notification_id;
		result = prime * result + project_id;
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
		if (date_created == null) {
			if (other.date_created != null)
				return false;
		} else if (!date_created.equals(other.date_created))
			return false;
		if (isRead != other.isRead)
			return false;
		if (notification_id != other.notification_id)
			return false;
		if (project_id != other.project_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Notification [notification_id=" + notification_id + ", project_id=" + project_id + ", isRead=" + isRead
				+ ", date_created=" + date_created + "]";
	}

	public Notification(int notification_id, int project_id, boolean isRead, Date date_created) {
		super();
		this.notification_id = notification_id;
		this.project_id = project_id;
		this.isRead = isRead;
		this.date_created = date_created;
	}

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
