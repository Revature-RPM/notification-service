package com.revature.rpm.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.rpm.entities.Comment;
import com.revature.rpm.entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer>{

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
	 * 
	 * @return
	 */
	List<Comment> getNotificationsByUserIdAndIsReadFalseOrderByDateCreatedDesc(int userid);


	List<Comment> getTop5NotificationsByUserIdAndIsReadTrueOrderByDateCreatedDesc(int userid);
	

	
}
