package com.revature.advisor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
/**
 * 
 * @author James Meadows
 * @author Stefano Georges
 * @author Chong Ting
 * @author Christopher Troll
 * @author Emad Davis
 *
 */
@ControllerAdvice
public class ExceptionHandlerAdvisor {
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> exceptionHandler(HttpClientErrorException e){
		return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
	}
}
