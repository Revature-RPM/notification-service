package com.revature.rpm.dto;

import java.util.Date;

/**
 * DTO for mapping a message queue object to a notification.
 * The AdapterService will convert this object into a notification of the appropriate type.
 * 
 * @author Christopher Troll
 *
 */

public class SQSDTO {
  private String contentType;
  private int notificationId;
  private Date dateCreated;
  private boolean read;
  private String projectId;
  private String title;
  private int userId;
  private String fullDescription;
  private String shortDescription;

  public SQSDTO() {
    super();
  }

  public SQSDTO(
      String contentType,
      int notificationId,
      Date dateCreated,
      boolean read,
      String projectId,
      String title,
      int userId,
      String fullDescription,
      String shortDescription) {
    super();
    this.contentType = contentType;
    this.notificationId = notificationId;
    this.dateCreated = dateCreated;
    this.read = read;
    this.projectId = projectId;
    this.title = title;
    this.userId = userId;
    this.fullDescription = fullDescription;
    this.shortDescription = shortDescription;
  }

  @Override
  public String toString() {
    return "SQSDTO [contentType="
        + contentType
        + ", notificationId="
        + notificationId
        + ", dateCreated="
        + dateCreated
        + ", read="
        + read
        + ", projectId="
        + projectId
        + ", title="
        + title
        + ", userId="
        + userId
        + ", fullDescription="
        + fullDescription
        + ", shortDescription="
        + shortDescription
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
    result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
    result = prime * result + ((fullDescription == null) ? 0 : fullDescription.hashCode());
    result = prime * result + notificationId;
    result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
    result = prime * result + (read ? 1231 : 1237);
    result = prime * result + ((shortDescription == null) ? 0 : shortDescription.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + userId;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SQSDTO other = (SQSDTO) obj;
    if (contentType == null) {
      if (other.contentType != null) return false;
    } else if (!contentType.equals(other.contentType)) return false;
    if (dateCreated == null) {
      if (other.dateCreated != null) return false;
    } else if (!dateCreated.equals(other.dateCreated)) return false;
    if (fullDescription == null) {
      if (other.fullDescription != null) return false;
    } else if (!fullDescription.equals(other.fullDescription)) return false;
    if (notificationId != other.notificationId) return false;
    if (projectId == null) {
      if (other.projectId != null) return false;
    } else if (!projectId.equals(other.projectId)) return false;
    if (read != other.read) return false;
    if (shortDescription == null) {
      if (other.shortDescription != null) return false;
    } else if (!shortDescription.equals(other.shortDescription)) return false;
    if (title == null) {
      if (other.title != null) return false;
    } else if (!title.equals(other.title)) return false;
    if (userId != other.userId) return false;
    return true;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public int getNotificationId() {
    return notificationId;
  }

  public void setNotificationId(int notificationId) {
    this.notificationId = notificationId;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  public boolean isRead() {
    return read;
  }

  public void setRead(boolean read) {
    this.read = read;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getFullDescription() {
    return fullDescription;
  }

  public void setFullDescription(String fullDescription) {
    this.fullDescription = fullDescription;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }
}
