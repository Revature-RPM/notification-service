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
/**
 * 
 * Notifications controller class which receives the mapping "" and perform 
 * either our GET "/", GET "/history?page=n", or PATCH "/" requests.
 * 
 * @author James Meadows
 * @author Stefano Georges
 * @author Chong Ting
 * @author Christopher Troll
 * @author Emad Davis
 */
@RestController
@RequestMapping("")
public class Controller {
	
	NotificationService notificationService;
	JWTService jwtserv;
	
	/**
	 * Constructor to take in the parameters of our notification service and our 
	 * JWT service. 
	 * 
	 * @param notificationService
	 * @param jwtserv
	 */
	@Autowired
	public Controller(NotificationService notificationService, JWTService jwtserv) {
		super();
		this.notificationService = notificationService;
		this.jwtserv = jwtserv;
	}
	
	/** 
	 * Controller level method to get all of the new notifications matching the userid (stored within jwt). 
	 * The userid is extracted from the jwt. The notification service class method getAllNewNotifications is
	 * called passing the userid as a parameter. This method should return a List<Comments> from the database.
	 * 
	 * This method returns the List<Comments> from the notifcation service to the in the ResponseBody.
	 * 
	 * @param jws
	 * @return
	 */
	@GetMapping("/")
	public List<Comment> getAllNewNotifications(@RequestHeader("Authorization") String jws) {
		int userid = jwtserv.extractUserIdFromJWT(jws);
		return notificationService.getAllNewNotifications(userid);
	}
	
	/**
	 * 
	 * @param jws
	 * @param page
	 * @return
	 */
	@GetMapping("/history")
	public Page<Comment> getNotificationsByPage(@RequestHeader("Authorization") String jws, Pageable page) {
		int userid = jwtserv.extractUserIdFromJWT(jws);
		return notificationService.getNotificationsByPage(userid,page);
	}

	@PatchMapping("/")
	public Boolean updateRead(@RequestBody ReadDTO readDTO, @RequestHeader("Authorization") String jws) {
		int userid = jwtserv.extractUserIdFromJWT(jws);
		notificationService.updateUnreadToRead(userid,readDTO);
		return true;
	}
	@PatchMapping("/unread/")
	public Boolean updateUnread(@RequestBody ReadDTO readDTO, @RequestHeader("Authorization") String jws) {
		int userid = jwtserv.extractUserIdFromJWT(jws);
		notificationService.updateReadToUnread(userid, readDTO);
		return true;
	}
}
