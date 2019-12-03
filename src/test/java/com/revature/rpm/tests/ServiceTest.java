package com.revature.rpm.tests;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
