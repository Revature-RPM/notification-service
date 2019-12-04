package com.revature.rpm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rpm.dto.ReadDTO;
import com.revature.rpm.entities.Comment;
import com.revature.rpm.services.NotificationService;

@RestController
@RequestMapping("")
public class Controller {
	
	NotificationService notificationService;
	
	@Autowired
	public Controller(NotificationService notificationService) {
		super();
		this.notificationService = notificationService;
	}
	
	@GetMapping("/")
	public List<Comment> getAllNewNotifications() {
		return notificationService.getAllNewNotifications();
	}
	
	@GetMapping("/history")
	public Page<Comment> getNotificationsByPage(Pageable page) {
		return notificationService.getNotificationsByPage(page);
	}

	@PatchMapping("/")
	public Boolean updateRead(@RequestBody ReadDTO readDTO) {
		notificationService.updateUnreadToRead(readDTO);
		return true;
	}
}
