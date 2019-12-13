package com.revature.rpm.dto;
/**
 * @author James Meadows
 * @author Stefano Georges
 * @author Chong Ting
 * @author Christopher Troll
 * @author Emad Davis
 */
public class ReadDTO {

  private int notification_id;
  private int user_id;

  public int getNotification_id() {
    return notification_id;
  }

  public void setNotification_id(int notification_id) {
    this.notification_id = notification_id;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }
}
