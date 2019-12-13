package com.revature.rpm.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.rpm.entities.Comment;
import com.revature.rpm.entities.Notification;
/**
 * This is the repository layer that directly calls the database. The method names are long because
 * they use the Spring Data Fluent API.
 *
 * @author James Meadows
 * @author Stefano Georges
 * @author Chong Ting
 * @author Christopher Troll
 * @author Emad Davis
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

  /**
   * Chilly winter-time 
   * For notification find 
   * A pagination
   *
   * @param page
   * @return
   */
  Page<Comment> findByUserIdOrderByDateCreatedDesc(int userid, Pageable page);

  /**
   * Accepts a userId and returns unread notification in date descending order.
   * 
   * @param userid
   * @return
   */
  List<Comment> getNotificationsByUserIdAndIsReadFalseOrderByDateCreatedDesc(int userid);

  /**
   * Accepts a userId and returns read notifications in date descending order.
   * 
   * @param userid
   * @return
   */
  List<Comment> getTop5NotificationsByUserIdAndIsReadTrueOrderByDateCreatedDesc(int userid);
}
