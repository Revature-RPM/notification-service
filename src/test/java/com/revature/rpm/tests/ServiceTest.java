package com.revature.rpm.tests;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.rpm.dto.ReadDTO;
import com.revature.rpm.entities.Comment;
import com.revature.rpm.repositories.NotificationRepository;
import com.revature.rpm.services.NotificationService;

import antlr.collections.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
	@Mock
	NotificationRepository mockNotificationRepository;


	@InjectMocks
	private NotificationService notificationService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void happyPath() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		Comment comment = new Comment();
		comment.setUserId(1);
		when(mockNotificationRepository.findById(1)).thenReturn(Optional.of(comment));
		Boolean returnValue = notificationService.updateUnreadToRead(readDTO);
		assertSame("The Notification Repository returns true on success with setReadToTrue", true, returnValue);
		verify(mockNotificationRepository).findById(1);
	}

	@Test(expected = HttpClientErrorException.class)
	public void notFoundPath() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		when(mockNotificationRepository.findById(1)).thenReturn(Optional.empty());
		Boolean returnValue = notificationService.updateUnreadToRead(readDTO);
		fail("Exception should have been thrown due to empty optional");
	}
	@Test(expected = HttpClientErrorException.class)
	public void forbiddenPath() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		Comment comment = new Comment();
		comment.setUserId(2);
		when(mockNotificationRepository.findById(1)).thenReturn(Optional.of(comment));
		Boolean returnValue = notificationService.updateUnreadToRead(readDTO);
		fail("Exception should have been thrown due to readDTO user not matching user listed on returned notification");
	}
	//Create tests for the Get: "/" methods
	//Test #1 Returns 5 unread notifications
	/*
	@Test
	public void getAllIsReadFalse(){
		List<Comment> testList = new ArrayList<>();
		

		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		Comment comment = new Comment();
		when(mockNotificationRepository.findById(1)).thenReturn(Optional.of(comment));
		Boolean returnValue = notificationService.updateUnreadToRead(readDTO);
		assertSame("The Notification Repository returns true on success with setReadToTrue", true, returnValue);
		verify(mockNotificationRepository).findById(1);
	}
	*/
	//Test #2 Returns 5 read notifications

	//Test #3 Returns 4 unread and 1 read notifications

	//Test #4 Returns 3 unread and 2 read notifications

	//Test #5 Returns 2 unread and 3 read notifications

	//Test #5 Returns 1 unread and 4 read notifications

}
