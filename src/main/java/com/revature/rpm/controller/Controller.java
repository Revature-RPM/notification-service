package com.revature.rpm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rpm.dto.ReadDTO;
import com.revature.rpm.entities.Comment;
import com.revature.rpm.services.JWTService;
import com.revature.rpm.services.NotificationService;

@RestController
@RequestMapping("")
public class Controller {
	
	NotificationService notificationService;
	JWTService jwtserv;
	
	@Autowired
	public Controller(NotificationService notificationService, JWTService jwtserv) {
		super();
		this.notificationService = notificationService;
		this.jwtserv = jwtserv;
	}
	
	@GetMapping("/")
	public List<Comment> getAllNewNotifications(@RequestHeader("Authorization") String jws) {
		int userid = jwtserv.extractUserIdFromJWT(jws);
		System.out.println("Getting notifications for userid: " + userid + "...");
		return notificationService.getAllNewNotifications(userid);
	}
	
	@GetMapping("/history")
	public Page<Comment> getNotificationsByPage(@RequestHeader("Authorization") String jws, Pageable page) {
		int userid = jwtserv.extractUserIdFromJWT(jws);
		System.out.println("Getting pagenated notifications for userid: " + userid + " Page number: " + page + "...");
		return notificationService.getNotificationsByPage(userid,page);
	}

	@PatchMapping("/")
	public Boolean updateRead(@RequestBody ReadDTO readDTO, @RequestHeader("Authorization") String jws) {
		//System.out.println("Updating read status of notificationId: " + readDTO.getNotification_id() + " to read...");
		int userid = jwtserv.extractUserIdFromJWT(jws);
		notificationService.updateUnreadToRead(userid,readDTO);
		return true;
	}
	@PatchMapping("/unread/")
	public Boolean updateUnread(@RequestBody ReadDTO readDTO, @RequestHeader("Authorization") String jws) {
		//System.out.println("Updating read status of notificationId: " + readDTO.getNotification_id() + " to unread...");
		//System.out.println(jwtserv);
		int userid = jwtserv.extractUserIdFromJWT(jws);
		notificationService.updateReadToUnread(userid, readDTO);
		return true;
	}
}
