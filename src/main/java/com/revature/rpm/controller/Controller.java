package com.revature.rpm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rpm.dto.NotificationDTO;
import com.revature.rpm.dto.ReadDTO;
import com.revature.rpm.services.NotificationService;

@RestController
@RequestMapping("")
public class Controller {
	private NotificationService notificationService;
	
	@Autowired
	public Controller(NotificationService notificationService) {
		super();
		this.notificationService = notificationService;
	}
	
	@GetMapping("/history")
	public Page<NotificationDTO> getNotificationsByPage(Pageable page) {
		return notificationService.getNotificationsByPage(page);
	}

	@PatchMapping("/")
<<<<<<< HEAD
	public void updateRead(@RequestBody ReadDTO readDTO) {
=======
	
	public Boolean updateRead(@RequestBody ReadDTO readDTO) {
>>>>>>> 0802e14d802964d0d0bc3b35cbe7f8110282d1b8
		notificationService.updateUnreadToRead(readDTO);
		return true;
	}
}
