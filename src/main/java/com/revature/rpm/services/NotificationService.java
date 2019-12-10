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

@Service
public class NotificationService {
	
	NotificationRepository notificationRepository;
	
	@Autowired
	public NotificationService(NotificationRepository notificationRepository) {
		super();
		this.notificationRepository = notificationRepository;
	}

	@Transactional
	public Boolean updateUnreadToRead(ReadDTO readDTO) {
		int notificationId = readDTO.getNotification_id();
		
		int userId = readDTO.getUser_id();
		
		Notification notification = notificationRepository.findById(notificationId)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		if(notification.getUserId()!=userId)throw new HttpClientErrorException(HttpStatus.FORBIDDEN); 
		notification.setRead(true);
		notificationRepository.save(notification);
		return true;
	}

	@Transactional
	public Boolean updateReadToUnread(ReadDTO readDTO) {
		int notificationId = readDTO.getNotification_id();
		
		int userId = readDTO.getUser_id();
		
		Notification notification = notificationRepository.findById(notificationId)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		if(notification.getUserId()!=userId)throw new HttpClientErrorException(HttpStatus.FORBIDDEN); 
		notification.setRead(false);
		notificationRepository.save(notification);
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
	public Page<Comment> getNotificationsByPage(Pageable page, int userid) {
		return notificationRepository.findByUserIdOrderByDateCreatedDesc(page, userid);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Comment> getAllNewNotifications(int userid) {
		//Creating a list with notifications that are not read
		List<Comment> newNotifications =  notificationRepository.getNotificationsByUserIdAndIsReadFalse(userid);
		
		if (newNotifications.size() < 5) {
			final int numNeeded = 5 - newNotifications.size();
			System.out.println(numNeeded);
			//Creating a list with notification that are read
			List<Comment> fillerNotifications = notificationRepository.getTop5NotifcationsByUserIdAndIsReadTrue(userid);
			System.out.println(fillerNotifications);
			
			for(int i = 0; i < numNeeded; i++) {
				newNotifications.add(i, fillerNotifications.get(i));
			}
		}
		
		//sort the whole list by date
		Collections.sort(newNotifications, (a,b) -> {
			return a.getDateCreated().compareTo(b.getDateCreated());
		});
		//change the order where the latest is the top
		Collections.reverse(newNotifications);
		return newNotifications;
	}

	public void save(Notification notification) {
		notificationRepository.save(notification);	
	}
}
