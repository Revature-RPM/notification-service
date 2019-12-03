package com.revature.rpm.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.rpm.dto.NotificationDTO;
import com.revature.rpm.dto.ReadDTO;
import com.revature.rpm.entities.Notification;
import com.revature.rpm.repositories.NotificationRepository;

@Service
public class NotificationService {
	
	NotificationRepository notificationRepository;
	
	@Transactional
	public Boolean updateUnreadToRead(ReadDTO readDTO) {
		int notification_id = readDTO.getNotification_id();
		
		int user_id = readDTO.getUser_id();
		
		Notification notification = notificationRepository.findById(notification_id)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		notification.setRead(true);
		notificationRepository.save(notification);
		return true;
		
	}
	
	@Autowired
	public NotificationService(NotificationRepository notificationRepository) {
		super();
		this.notificationRepository = notificationRepository;
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
		return notificationRepository.findAllByOrderByDate(page);
	}
}
