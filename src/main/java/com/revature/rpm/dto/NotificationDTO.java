package com.revature.rpm.dto;

import java.util.Date;

public class NotificationDTO {

	// Notification abstract class parameters
	private int notificationId;
	private int projectId;
	private boolean isRead;
	private Date dateCreated;
	
	// Discriminator Column Name and Value
	private String Content_Type;
	private String Comment;
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
	public String getContent_Type() {
		return Content_Type;
	}
	public void setContent_Type(String content_Type) {
		Content_Type = content_Type;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Comment == null) ? 0 : Comment.hashCode());
		result = prime * result + ((Content_Type == null) ? 0 : Content_Type.hashCode());
		result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + (isRead ? 1231 : 1237);
		result = prime * result + notificationId;
		result = prime * result + projectId;
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
		NotificationDTO other = (NotificationDTO) obj;
		if (Comment == null) {
			if (other.Comment != null)
				return false;
		} else if (!Comment.equals(other.Comment))
			return false;
		if (Content_Type == null) {
			if (other.Content_Type != null)
				return false;
		} else if (!Content_Type.equals(other.Content_Type))
			return false;
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
		return true;
	}
	@Override
	public String toString() {
		return "NotificationDTO [notificationId=" + notificationId + ", projectId=" + projectId + ", isRead=" + isRead
				+ ", dateCreated=" + dateCreated + ", Content_Type=" + Content_Type + ", Comment=" + Comment + "]";
	}
	public NotificationDTO(int notificationId, int projectId, boolean isRead, Date dateCreated, String content_Type,
			String comment) {
		super();
		this.notificationId = notificationId;
		this.projectId = projectId;
		this.isRead = isRead;
		this.dateCreated = dateCreated;
		Content_Type = content_Type;
		Comment = comment;
	}
	public NotificationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
