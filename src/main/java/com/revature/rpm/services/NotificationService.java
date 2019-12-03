package com.revature.rpm.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.revature.rpm.dto.NotificationDTO;
import com.revature.rpm.dto.ReadDTO;
import com.revature.rpm.repositories.NotificationRepository;

@Service
public class NotificationService {
	
	NotificationRepository notificationRepository;
	
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
	public List<NotificationDTO> getAllNewNotifications() {

			List<NotificationDTO> newNotifications =  notificationRepository.getNotificationByIsReadFalse();
			return newNotifications;
		
//		if (newNotifications.size() <= 5) {
//			final int remainder = (5 - newNotifications.size());
//			
//			List<NotificationDTO> recentNotifications = notificationRepository.getNotifications
//		}
	}
}
