package com.revature.rpm.entities;

import java.util.Date;

import javax.persistence.*;
/**
 * This entity maps to a single table in the database.
 * We use a single-table inheritance strategy with a discriminatory column. 
 * This means we can store different sub-types of notifications inside a single table.
 * The discriminator column tells us what type of notification each record is.
 * 
 * @author James Meadows
 * @author Stefano Georges
 * @author Chong Ting
 * @author Christopher Troll
 * @author Emad Davis
 */
@Entity
@DiscriminatorColumn(name = "Content_Type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Notification {

  @Id
  @Column(name = "notification_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int notificationId;

  @Column(name = "project_id")
  private String projectId;

  @Column(name = "user_id")
  private int userId;

  @Column(name = "title")
  private String title;

  @Column(name = "is_read", columnDefinition = "boolean default false")
  private boolean isRead;

  // Does not currently auto-generate date as a default. Need to look into if adtl time.
  @Column(name = "date_created")
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateCreated;

  public int getNotificationId() {
    return notificationId;
  }

  public void setNotificationId(int notificationId) {
    this.notificationId = notificationId;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
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
    result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + userId;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Notification other = (Notification) obj;
    if (dateCreated == null) {
      if (other.dateCreated != null) return false;
    } else if (!dateCreated.equals(other.dateCreated)) return false;
    if (isRead != other.isRead) return false;
    if (notificationId != other.notificationId) return false;
    if (projectId == null) {
      if (other.projectId != null) return false;
    } else if (!projectId.equals(other.projectId)) return false;
    if (title == null) {
      if (other.title != null) return false;
    } else if (!title.equals(other.title)) return false;
    if (userId != other.userId) return false;
    return true;
  }

  @Override
  public String toString() {
    return "Notification [notificationId="
        + notificationId
        + ", projectId="
        + projectId
        + ", userId="
        + userId
        + ", title="
        + title
        + ", isRead="
        + isRead
        + ", dateCreated="
        + dateCreated
        + "]";
  }

  public Notification(
      int notificationId,
      String projectId,
      int userId,
      String title,
      boolean isRead,
      Date dateCreated) {
    super();
    this.notificationId = notificationId;
    this.projectId = projectId;
    this.userId = userId;
    this.title = title;
    this.isRead = isRead;
    this.dateCreated = dateCreated;
  }

  public Notification() {
    super();
    // TODO Auto-generated constructor stub
  }
}
