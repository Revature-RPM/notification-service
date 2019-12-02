package com.revature.rpm.services;

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

	public Page<NotificationDTO> getNotificationsByPage(Pageable page) {
		return notificationRepository.findAllByOrderByDate(page);
	}
}
