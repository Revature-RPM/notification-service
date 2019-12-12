package com.revature.rpm.tests;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.advisor.ExceptionHandlerAdvisor;
import com.revature.rpm.controller.Controller;
import com.revature.rpm.dto.ReadDTO;
import com.revature.rpm.repositories.NotificationRepository;
import com.revature.rpm.services.JWTService;
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
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
@SpringBootTest
public class ControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	NotificationService mockNotificationService;
	@Mock
	JWTService mockjwtserv;
	
	@InjectMocks
	private Controller controller;
	
	@Autowired
	ObjectMapper om;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.setControllerAdvice(new ExceptionHandlerAdvisor())
				.build();
	}
	
	@Test
	public void updateReadHappyPath() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		when(mockNotificationService
			.updateUnreadToRead(anyInt(),Mockito.any(ReadDTO.class)))
			.thenReturn(new Boolean(true));
		when(mockjwtserv.extractUserIdFromJWT(anyString())).thenReturn(1);
		this.mockMvc
			.perform(patch("/")
			.header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJSZXZhdHVyZSIsInN1YiI6IjEiLCJpYXQiOjE1NzU5MTQ4NjMsImV4cCI6MTU3NjUxOTY2MywidXNlcklkIjoxfQ.wgdEB-shuiunIn-ihoDDpag4oxvK8ohNBOtHUJOMUU83qWsOLzC3WV5S_9icgjBF5tNH8t15iXqWrw3SYb_Vzw")
			.contentType(MediaType.APPLICATION_JSON)
			.content(om.writeValueAsString(readDTO)))
			.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void updateReadNotFound() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		when(mockNotificationService
			.updateUnreadToRead(anyInt(), Mockito.any(ReadDTO.class)))
			.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
		when(mockjwtserv.extractUserIdFromJWT(anyString())).thenReturn(1);
		this.mockMvc
			.perform(patch("/")
			.header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJSZXZhdHVyZSIsInN1YiI6IjEiLCJpYXQiOjE1NzU5MTQ4NjMsImV4cCI6MTU3NjUxOTY2MywidXNlcklkIjoxfQ.wgdEB-shuiunIn-ihoDDpag4oxvK8ohNBOtHUJOMUU83qWsOLzC3WV5S_9icgjBF5tNH8t15iXqWrw3SYb_Vzw")
			.contentType(MediaType.APPLICATION_JSON)
			.content(om.writeValueAsString(readDTO)))
			.andDo(print())
			.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void updateReadPermissionDenied() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		when(mockNotificationService
			.updateUnreadToRead(anyInt(),Mockito.any(ReadDTO.class)))
			.thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN));
		when(mockjwtserv.extractUserIdFromJWT(anyString())).thenReturn(1);
		this.mockMvc
			.perform(patch("/")
			.header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJSZXZhdHVyZSIsInN1YiI6IjEiLCJpYXQiOjE1NzU5MTQ4NjMsImV4cCI6MTU3NjUxOTY2MywidXNlcklkIjoxfQ.wgdEB-shuiunIn-ihoDDpag4oxvK8ohNBOtHUJOMUU83qWsOLzC3WV5S_9icgjBF5tNH8t15iXqWrw3SYb_Vzw")
			.contentType(MediaType.APPLICATION_JSON)
			.content(om.writeValueAsString(readDTO)))
			.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
	
	@Test
	public void updateUnreadHappyPath() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		when(mockNotificationService
			.updateReadToUnread(anyInt(),Mockito.any(ReadDTO.class)))
			.thenReturn(true);
		when(mockjwtserv.extractUserIdFromJWT(anyString())).thenReturn(1);
		this.mockMvc
			.perform(patch("/unread/")
			.header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJSZXZhdHVyZSIsInN1YiI6IjEiLCJpYXQiOjE1NzU5MTQ4NjMsImV4cCI6MTU3NjUxOTY2MywidXNlcklkIjoxfQ.wgdEB-shuiunIn-ihoDDpag4oxvK8ohNBOtHUJOMUU83qWsOLzC3WV5S_9icgjBF5tNH8t15iXqWrw3SYb_Vzw")
			.contentType(MediaType.APPLICATION_JSON)
			.content(om.writeValueAsString(readDTO)))
			.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void updateUnreadNotFound() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		when(mockNotificationService
			.updateReadToUnread(anyInt(),Mockito.any(ReadDTO.class)))
			.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
		when(mockjwtserv.extractUserIdFromJWT(anyString())).thenReturn(1);
		this.mockMvc
			.perform(patch("/unread/")
			//JWT valid one week from 12/9/2019
			.header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJSZXZhdHVyZSIsInN1YiI6IjEiLCJpYXQiOjE1NzU5MTQ4NjMsImV4cCI6MTU3NjUxOTY2MywidXNlcklkIjoxfQ.wgdEB-shuiunIn-ihoDDpag4oxvK8ohNBOtHUJOMUU83qWsOLzC3WV5S_9icgjBF5tNH8t15iXqWrw3SYb_Vzw")
			.contentType(MediaType.APPLICATION_JSON)
			.content(om.writeValueAsString(readDTO)))
			.andDo(print())
			.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void updateUnreadPermissionDenied() throws Exception {
		ReadDTO readDTO = new ReadDTO();
		readDTO.setNotification_id(1);
		readDTO.setUser_id(1);
		when(mockNotificationService
			.updateReadToUnread(anyInt(),Mockito.any(ReadDTO.class)))
			.thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN));
		when(mockjwtserv.extractUserIdFromJWT(anyString())).thenReturn(1);
		this.mockMvc
			.perform(patch("/unread/")
			//JWT valid one week from 12/9/2019
			.header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJSZXZhdHVyZSIsInN1YiI6IjEiLCJpYXQiOjE1NzU5MTQ4NjMsImV4cCI6MTU3NjUxOTY2MywidXNlcklkIjoxfQ.wgdEB-shuiunIn-ihoDDpag4oxvK8ohNBOtHUJOMUU83qWsOLzC3WV5S_9icgjBF5tNH8t15iXqWrw3SYb_Vzw")
			.contentType(MediaType.APPLICATION_JSON)
			.content(om.writeValueAsString(readDTO)))
			.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
	
	@Test
	public void getAllByOrderByDate() throws Exception {
		
	}
}
