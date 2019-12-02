package com.revature.rpm.services;

import org.springframework.stereotype.Service;

import com.revature.rpm.dto.ReadDTO;

@Service
public class NotificationService {
	public Boolean updateUnreadToRead(ReadDTO readDTO) {
		int notification_id = readDTO.getNotification_id();
		int user_id = readDTO.getUser_id();
		
		return true;
		
	}
}
