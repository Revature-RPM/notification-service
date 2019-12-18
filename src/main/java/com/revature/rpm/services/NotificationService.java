package com.revature.rpm.services;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.rpm.dto.ReadDTO;
import com.revature.rpm.entities.Comment;
import com.revature.rpm.entities.Notification;
import com.revature.rpm.repositories.NotificationRepository;

/**
 * This service implements the business logic for each controller request.
 * 
 * @author James Meadows
 * @author Stefano Georges
 * @author Chong Ting
 * @author Christopher Troll
 * @author Emad Davis
 */
@Service
public class NotificationService {

  NotificationRepository notificationRepository;

  @Autowired
  public NotificationService(NotificationRepository notificationRepository) {
    super();
    this.notificationRepository = notificationRepository;
  }

  /**
   * Accepts a userId and a readDTO and updates a notifications state from unread to read (read = true).
   * 
   * @param jwtUserId
   * @param readDTO
   * @return
   */
  
  @Transactional
  public Boolean updateUnreadToRead(int jwtUserId, ReadDTO readDTO) {
    int notificationId = readDTO.getNotification_id();
    int userId = readDTO.getUser_id();
    Notification notification =
        notificationRepository
            .findById(notificationId)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    // Checks to see if the user is authorized to make changes, errors are thrown if the user is not
    if (notification.getUserId() != userId)
      throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
    if (notification.getUserId() != jwtUserId)
      throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
    notification.setRead(true);
    notificationRepository.save(notification);
    return true;
  }

  /**
   * Accepts a userId and a readDTO and updates a notifications state from read to unread (read = false).
   * 
   * @param jwtUserId
   * @param readDTO
   * @return
   */
  
  @Transactional
  public Boolean updateReadToUnread(int jwtUserId, ReadDTO readDTO) {
    int notificationId = readDTO.getNotification_id();
    int userId = readDTO.getUser_id();
    Notification notification =
        notificationRepository
            .findById(notificationId)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    // Checks to see if the user is authorized to make changes, errors are thrown if the user is not
    if (notification.getUserId() != userId)
      throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
    if (notification.getUserId() != jwtUserId)
      throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
    notification.setRead(false);
    notificationRepository.save(notification);
    return true;
  }

  /**
   * Accepts a userId and page number, returns a users notifications that map to the page number.
   * 
   * @param userid
   * @param page
   * @return
   */
  public Page<Comment> getNotificationsByPage(int userid, Pageable page) {
    return notificationRepository.findByUserIdOrderByDateCreatedDesc(userid, page);
  }

  /**
   * Accepts a userId and returns all new notifications.
   * If there are fewer than 5 notifications, add read notifications.
   * The notifications are sorted by date with the most recent ones first.
   * 
   * @param userid
   * @return
   */
  public List<Comment> getAllNewNotifications(int userid) {
    // Creating a list with notifications that are not read
    List<Comment> newNotifications =
        notificationRepository.getNotificationsByUserIdAndIsReadFalseOrderByDateCreatedDesc(userid);

    if (newNotifications.size() < 5) {
      final int numNeeded = 5 - newNotifications.size();
      // Creating a list with notification that are read
      List<Comment> fillerNotifications =
          notificationRepository.getTop5NotificationsByUserIdAndIsReadTrueOrderByDateCreatedDesc(
              userid);
      for (int i = 0; i < numNeeded; i++) {
        newNotifications.add(i, fillerNotifications.get(i));
      }
    }

    // sort the whole list by date
    Collections.sort(
        newNotifications,
        (a, b) -> {
          return a.getDateCreated().compareTo(b.getDateCreated());
        });
    // change the order where the latest is the top
    Collections.reverse(newNotifications);
    return newNotifications;
  }

  /**
   * Saves a notification to the database.
   * 
   * @param notification
   */
  public void save(Notification notification) {
    notificationRepository.save(notification);
  }
}