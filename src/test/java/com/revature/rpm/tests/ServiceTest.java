package com.revature.rpm.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.rpm.dto.ReadDTO;
import com.revature.rpm.entities.Comment;
import com.revature.rpm.repositories.NotificationRepository;
import com.revature.rpm.services.NotificationService;
/**
 * 
 * @author James Meadows
 * @author Stefano Georges
 * @author Chong Ting
 * @author Christopher Troll
 * @author Emad Davis
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
	@Mock
	NotificationRepository mockNotificationRepository;

	Page<Comment> pagedResponse;
	
	@InjectMocks
	private NotificationService notificationService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public void initPaganation() {
		List<Comment> comments = new ArrayList<>();
		
	}

	@Test
	public void readHappyPath() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		Comment comment = new Comment();
		comment.setUserId(1);
		when(mockNotificationRepository.findById(1)).thenReturn(Optional.of(comment));
		Boolean returnValue = notificationService.updateUnreadToRead(1, readDTO);
		assertSame("The Notification Repository returns true on success with setReadToTrue", true, returnValue);
		assertSame("The value of isRead on comment should be true",true,comment.isRead());
		verify(mockNotificationRepository).findById(1);
	}

	@Test(expected = HttpClientErrorException.class)
	public void readNotFoundPath() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		when(mockNotificationRepository.findById(1)).thenReturn(Optional.empty());
		Boolean returnValue = notificationService.updateUnreadToRead(1, readDTO);
		fail("Exception should have been thrown due to empty optional");
	}
	@Test(expected = HttpClientErrorException.class)
	public void readFirstForbiddenPath() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		Comment comment = new Comment();
		comment.setUserId(2);
		when(mockNotificationRepository.findById(1)).thenReturn(Optional.of(comment));
		Boolean returnValue = notificationService.updateUnreadToRead(1,readDTO);
		fail("Exception should have been thrown due to readDTO user not matching user listed on returned notification");
	}
	@Test(expected = HttpClientErrorException.class)
	public void readSecondForbiddenPath() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		Comment comment = new Comment();
		comment.setUserId(1);
		when(mockNotificationRepository.findById(1)).thenReturn(Optional.of(comment));
		Boolean returnValue = notificationService.updateUnreadToRead(2,readDTO);
		fail("Exception should have been thrown due to readDTO user not matching user listed on returned notification");
	}
	@Test
	public void unreadHappyPath() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		Comment comment = new Comment();
		comment.setUserId(1);
		when(mockNotificationRepository.findById(1)).thenReturn(Optional.of(comment));
		Boolean returnValue = notificationService.updateReadToUnread(1,readDTO);
		assertSame("The Notification Repository returns true on success with setReadToTrue", true, returnValue);
		assertSame("The value of isRead on comment should be false",false,comment.isRead());
		verify(mockNotificationRepository).findById(1);
	}

	@Test(expected = HttpClientErrorException.class)
	public void unreadNotFoundPath() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		when(mockNotificationRepository.findById(1)).thenReturn(Optional.empty());
		Boolean returnValue = notificationService.updateReadToUnread(1,readDTO);
		fail("Exception should have been thrown due to empty optional");
	}
	@Test(expected = HttpClientErrorException.class)
	public void unreadFirstForbiddenPath() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		Comment comment = new Comment();
		comment.setUserId(2);
		when(mockNotificationRepository.findById(1)).thenReturn(Optional.of(comment));
		Boolean returnValue = notificationService.updateReadToUnread(1,readDTO);
		fail("Exception should have been thrown due to readDTO user not matching user listed on returned notification");
	}
	@Test(expected = HttpClientErrorException.class)
	public void unreadSecondForbiddenPath() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		Comment comment = new Comment();
		comment.setUserId(1);
		when(mockNotificationRepository.findById(1)).thenReturn(Optional.of(comment));
		Boolean returnValue = notificationService.updateReadToUnread(2,readDTO);
		fail("Exception should have been thrown due to readDTO user not matching user listed on returned notification");
	}
	//Create tests for the Get: "/" methods
	//Test #1 Returns 5 unread notifications
	@Test
	public void getAllIsReadFalse(){
		List<Comment> mockList = new ArrayList<>();
		
		//set up generate random dates
		long offset = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("1990-01-01 00:00:00").getTime();
		long diff = end - offset + 1;
		
		//Create a mock database
		for(int i = 0; i < 5; i++) {
			Comment note = new Comment();
			note.setProjectId("irqewjfmifoadji"); // Random string inserted for project_id. 
			Timestamp rand = new Timestamp(offset +  (long)(Math.random() * diff));
			note.setDateCreated(rand);
			note.setFullDescription("mock fullDescription");
			note.setNotificationId(i);
			note.setRead(false);
			note.setShortDescription("mock shotDescription");
			note.setTitle("mock Title");
			note.setUserId(i);
			mockList.add(note);
		}
		//Stubbing the getNotificationsByIsReadFalseOrderByDateCreatedDesc() method
		when(mockNotificationRepository.getNotificationsByUserIdAndIsReadFalseOrderByDateCreatedDesc(1)).thenReturn(mockList);
		//Test the getAllNewNotifications() method
		List<Comment> returnedList=notificationService.getAllNewNotifications(1);
		//Verify the results
		for(int i = 0; i < returnedList.size(); i++) {
			//verify the is_read = false
			assertSame("At index "+ i + "the is_read should be false, result is ", returnedList.get(i).isRead(), false);
			//verify the order is corrected
			if(i != 0) {
				if(returnedList.get(i-1).getDateCreated().compareTo(returnedList.get(i).getDateCreated()) < 0) {
					fail("Date are not in order");
				}
			}
		}
	}
	
	//Test #2 Returns 5 read notifications
	@Test
	public void getAllIsReadTrue(){
		List<Comment> mockList = new ArrayList<>();
		
		//set up generate random dates
		long offset = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("1990-01-01 00:00:00").getTime();
		long diff = end - offset + 1;
		
		//Create a mock database
		for(int i = 0; i < 5; i++) {
			Comment note = new Comment();
			note.setProjectId("irqewjfmifoadji"); // Random string inserted for project_id. 
			Timestamp rand = new Timestamp(offset +  (long)(Math.random() * diff));
			note.setDateCreated(rand);
			note.setFullDescription("mock fullDescription");
			note.setNotificationId(i);
			note.setRead(true);
			note.setShortDescription("mock shotDescription");
			note.setTitle("mock Title");
			note.setUserId(i);
			mockList.add(note);
		}
		//Stubbing the getNotificationsByIsReadFalseOrderByDateCreatedDesc() method
		when(mockNotificationRepository.getNotificationsByUserIdAndIsReadFalseOrderByDateCreatedDesc(1)).thenReturn(mockList);
		//Test the getAllNewNotifications() method
		List<Comment> returnedList=notificationService.getAllNewNotifications(1);
		//Verify the results
		for(int i = 0; i < returnedList.size(); i++) {
			//verify the is_read = false
			assertSame("At index "+ i + "the is_read should be false, result is ", returnedList.get(i).isRead(), true);
			//verify the order is corrected
			if(i != 0) {
				if(returnedList.get(i-1).getDateCreated().compareTo(returnedList.get(i).getDateCreated()) < 0) {
					fail("Date are not in order");
				}
			}
		}
	}

	//Test #3 Returns 1 unread and 4 read notifications
	@Test
	public void getFourTrueAndOneFalse(){
		List<Comment> mockList = new ArrayList<>();
		
		//set up generate random dates
		long offset = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("1990-01-01 00:00:00").getTime();
		long diff = end - offset + 1;
		
		//Create a mock database
		for(int i = 0; i < 5; i++) {
			Comment note = new Comment();
			note.setProjectId("irqewjfmifoadji"); // Random string inserted for project_id. 
			Timestamp rand = new Timestamp(offset +  (long)(Math.random() * diff));
			note.setDateCreated(rand);
			note.setFullDescription("mock fullDescription");
			note.setNotificationId(i);
			if(i > 0) {
				note.setRead(false);
			}else {
				note.setRead(true);
			}
			note.setShortDescription("mock shotDescription");
			note.setTitle("mock Title");
			note.setUserId(i);
			mockList.add(note);
		}
		//Stubbing the getNotificationsByIsReadFalseOrderByDateCreatedDesc() method
		when(mockNotificationRepository.getNotificationsByUserIdAndIsReadFalseOrderByDateCreatedDesc(1)).thenReturn(mockList);
		//Test the getAllNewNotifications() method
		List<Comment> returnedList=notificationService.getAllNewNotifications(1);
		//Verify the results
		int falseCount = 0;
		int trueCount = 0;
		for(int i = 0; i < returnedList.size(); i++) {
			//verify the count of is_read = true and is_read = false
			if(returnedList.get(i).isRead() == false) {
				falseCount++;
			} else {
				trueCount++;
			}
			//verify the order is corrected
			if(i != 0) {
				if(returnedList.get(i-1).getDateCreated().compareTo(returnedList.get(i).getDateCreated()) < 0) {
					fail("Date are not in order");
				}
			}
		}
		assertSame("There should be 4 is_read = false", falseCount, 4);
		assertSame("There should be 1 is_read = true", trueCount, 1);
	}

	//Test #4 Returns 2 unread and 3 read notifications
	@Test
	public void getThreeTrueAndTwoFalse(){
		List<Comment> mockList = new ArrayList<>();
		
		//set up generate random dates
		long offset = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("1990-01-01 00:00:00").getTime();
		long diff = end - offset + 1;
		
		//Create a mock database
		for(int i = 0; i < 5; i++) {
			Comment note = new Comment();
			note.setProjectId("irqewjfmifoadji"); // Random string inserted for project_id. 
			Timestamp rand = new Timestamp(offset +  (long)(Math.random() * diff));
			note.setDateCreated(rand);
			note.setFullDescription("mock fullDescription");
			note.setNotificationId(i);
			if(i > 1) {
				note.setRead(false);
			}else {
				note.setRead(true);
			}
			note.setShortDescription("mock shotDescription");
			note.setTitle("mock Title");
			note.setUserId(i);
			mockList.add(note);
		}
		//Stubbing the getNotificationsByIsReadFalseOrderByDateCreatedDesc() method
		when(mockNotificationRepository.getNotificationsByUserIdAndIsReadFalseOrderByDateCreatedDesc(1)).thenReturn(mockList);
		//Test the getAllNewNotifications() method
		List<Comment> returnedList=notificationService.getAllNewNotifications(1);
		//Verify the results
		int falseCount = 0;
		int trueCount = 0;
		for(int i = 0; i < returnedList.size(); i++) {
			//verify the count of is_read = true and is_read = false
			if(returnedList.get(i).isRead() == false) {
				falseCount++;
			} else {
				trueCount++;
			}
			//verify the order is corrected
			if(i != 0) {
				if(returnedList.get(i-1).getDateCreated().compareTo(returnedList.get(i).getDateCreated()) < 0) {
					fail("Date are not in order");
				}
			}
		}
		assertSame("There should be 3 is_read = false", falseCount, 3);
		assertSame("There should be 2 is_read = true", trueCount, 2);
	}
	//Test #5 Returns 3 unread and 2 read notifications
	@Test
	public void getTwoTrueAndThreeFalse(){
		List<Comment> mockList = new ArrayList<>();
		
		//set up generate random dates
		long offset = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("1990-01-01 00:00:00").getTime();
		long diff = end - offset + 1;
		
		//Create a mock database
		for(int i = 0; i < 5; i++) {
			Comment note = new Comment();
			note.setProjectId("irqewjfmifoadji"); // Random string inserted for project_id. 
			Timestamp rand = new Timestamp(offset +  (long)(Math.random() * diff));
			note.setDateCreated(rand);
			note.setFullDescription("mock fullDescription");
			note.setNotificationId(i);
			if(i > 2) {
				note.setRead(false);
			}else {
				note.setRead(true);
			}
			note.setShortDescription("mock shotDescription");
			note.setTitle("mock Title");
			note.setUserId(i);
			mockList.add(note);
		}
		//Stubbing the getNotificationsByIsReadFalseOrderByDateCreatedDesc() method
		when(mockNotificationRepository.getNotificationsByUserIdAndIsReadFalseOrderByDateCreatedDesc(1)).thenReturn(mockList);
		//Test the getAllNewNotifications() method
		List<Comment> returnedList=notificationService.getAllNewNotifications(1);
		//Verify the results
		int falseCount = 0;
		int trueCount = 0;
		for(int i = 0; i < returnedList.size(); i++) {
			//verify the count of is_read = true and is_read = false
			if(returnedList.get(i).isRead() == false) {
				falseCount++;
			} else {
				trueCount++;
			}
			//verify the order is corrected
			if(i != 0) {
				if(returnedList.get(i-1).getDateCreated().compareTo(returnedList.get(i).getDateCreated()) < 0) {
					fail("Date are not in order");
				}
			}
		}
		assertSame("There should be 2 is_read = false", falseCount, 2);
		assertSame("There should be 3 is_read = true", trueCount, 3);
	}
	//Test #6 Returns 4 unread and 1 read notifications
	@Test
	public void getOneTrueAndFourFalse(){
		List<Comment> mockList = new ArrayList<>();
		
		//set up generate random dates
		long offset = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("1990-01-01 00:00:00").getTime();
		long diff = end - offset + 1;
		
		//Create a mock database
		for(int i = 0; i < 5; i++) {
			Comment note = new Comment();
			note.setProjectId("irqewjfmifoadji"); // Random string inserted for project_id. 
			Timestamp rand = new Timestamp(offset +  (long)(Math.random() * diff));
			note.setDateCreated(rand);
			note.setFullDescription("mock fullDescription");
			note.setNotificationId(i);
			if(i > 3) {
				note.setRead(false);
			}else {
				note.setRead(true);
			}
			note.setShortDescription("mock shotDescription");
			note.setTitle("mock Title");
			note.setUserId(i);
			mockList.add(note);
		}
		//Stubbing the getNotificationsByIsReadFalseOrderByDateCreatedDesc() method
		when(mockNotificationRepository.getNotificationsByUserIdAndIsReadFalseOrderByDateCreatedDesc(1)).thenReturn(mockList);
		//Test the getAllNewNotifications() method
		List<Comment> returnedList=notificationService.getAllNewNotifications(1);
		//Verify the results
		int falseCount = 0;
		int trueCount = 0;
		for(int i = 0; i < returnedList.size(); i++) {
			//verify the count of is_read = true and is_read = false
			if(returnedList.get(i).isRead() == false) {
				falseCount++;
			} else {
				trueCount++;
			}
			//verify the order is corrected
			if(i != 0) {
				if(returnedList.get(i-1).getDateCreated().compareTo(returnedList.get(i).getDateCreated()) < 0) {
					fail("Date are not in order");
				}
			}
		}
		assertSame("There should be 1 is_read = false", falseCount, 1);
		assertSame("There should be 4 is_read = true", trueCount, 4);
	}
	//Testing Pagination Method
		//Test #1: Verify pagination is return full page
		@Test
		public void testPaginationFullPage() {
			List<Comment> mockList = new ArrayList<>();
			
			//This line dictate the outcome of the mock method
			Pageable pageable = PageRequest.of(1, 20);
			
			Page<Comment> mockNote = new PageImpl(mockList, pageable, 400L);
			Mockito.when(mockNotificationRepository.findByUserIdOrderByDateCreatedDesc(1, mockNote.getPageable())).thenReturn(mockNote);
			assertThat(mockNotificationRepository.findByUserIdOrderByDateCreatedDesc(1, mockNote.getPageable()).getSize()).isGreaterThan(0);
			assertThat(mockNotificationRepository.findByUserIdOrderByDateCreatedDesc(1, mockNote.getPageable()).getSize()).isEqualTo(20);
		}

}
