package com.revature.rpm.tests;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import com.revature.rpm.entities.Notification;
import com.revature.rpm.services.JWTService;
import com.revature.rpm.services.NotificationService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	NotificationService mockNotificationService;
	@Spy
	JWTService jwtserv;
	
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
		this.mockMvc
			.perform(patch("/unread/")
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
		this.mockMvc
			.perform(patch("/unread/")
			.header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJSZXZhdHVyZSIsInN1YiI6IjEiLCJpYXQiOjE1NzU5MTQ4NjMsImV4cCI6MTU3NjUxOTY2MywidXNlcklkIjoxfQ.wgdEB-shuiunIn-ihoDDpag4oxvK8ohNBOtHUJOMUU83qWsOLzC3WV5S_9icgjBF5tNH8t15iXqWrw3SYb_Vzw")
			.contentType(MediaType.APPLICATION_JSON)
			.content(om.writeValueAsString(readDTO)))
			.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
	
	@Test
	public void getAllByOrderByDate() throws Exception {
		
	}
}
