package com.revature.rpm.dto;

import java.util.Date;

public class NotificationDTO {

	// Notification abstract class parameters
	private int notification_id;
	private int project_id;
	private boolean is_read;
	private Date date_created;
	
	// Discriminator Column Name and Value
	private String Content_Type;
	private String Comment;
	
	// GETTERS/SETTERS
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
	public boolean isIs_read() {
		return is_read;
	}
	public void setIs_read(boolean is_read) {
		this.is_read = is_read;
	}
	public Date getDate_created() {
		return date_created;
	}
	public void setDate_created(Date date_created) {
		this.date_created = date_created;
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
	
	//HASHCODE EQUALS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Comment == null) ? 0 : Comment.hashCode());
		result = prime * result + ((Content_Type == null) ? 0 : Content_Type.hashCode());
		result = prime * result + ((date_created == null) ? 0 : date_created.hashCode());
		result = prime * result + (is_read ? 1231 : 1237);
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
		if (date_created == null) {
			if (other.date_created != null)
				return false;
		} else if (!date_created.equals(other.date_created))
			return false;
		if (is_read != other.is_read)
			return false;
		if (notification_id != other.notification_id)
			return false;
		if (project_id != other.project_id)
			return false;
		return true;
	}
	
	// TOSTRING
	@Override
	public String toString() {
		return "NotificationDTO [notification_id=" + notification_id + ", project_id=" + project_id + ", is_read="
				+ is_read + ", date_created=" + date_created + ", Content_Type=" + Content_Type + ", Comment=" + Comment
				+ "]";
	}
	
	// CONSTRUCTORS
	public NotificationDTO(int notification_id, int project_id, boolean is_read, Date date_created, String content_Type,
			String comment) {
		super();
		this.notification_id = notification_id;
		this.project_id = project_id;
		this.is_read = is_read;
		this.date_created = date_created;
		Content_Type = content_Type;
		Comment = comment;
	}
	
	public NotificationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
