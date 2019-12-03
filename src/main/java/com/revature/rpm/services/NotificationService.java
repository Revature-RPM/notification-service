package com.revature.rpm.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.revature.rpm.dto.NotificationDTO;
import com.revature.rpm.dto.ReadDTO;
import com.revature.rpm.entities.Comment;
import com.revature.rpm.repositories.NotificationRepository;

@Service
public class NotificationService {
	
	NotificationRepository notificationRepository;
	
	@Autowired
	public NotificationService(NotificationRepository notificationRepository) {
		super();
		this.notificationRepository = notificationRepository;
	}
	
	public Boolean updateUnreadToRead(ReadDTO readDTO) {
		int notification_id = readDTO.getNotification_id();
		int user_id = readDTO.getUser_id();
		
		return true;
		
	}
	/**
	 * Service to find all
	 * Page is given from Control
	 * Return page by date
	 * 
	 * @param page
	 * @return
	 */
	public Page<NotificationDTO> getNotificationsByPage(Pageable page) {
		return notificationRepository.findAllByOrderByDateCreated(page);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Comment> getAllNewNotifications() {
		
		System.out.println("getAllNewNotifications is running.");
		List<Comment> newNotifications =  notificationRepository.getNotificationsByIsReadFalse();
		return newNotifications;
		
//		if (newNotifications.size() <= 5) {
//			final int remainder = (5 - newNotifications.size());
//			
//			List<NotificationDTO> recentNotifications = notificationRepository.getNotifications
//		}
	}
}
