package com.revature.rpm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * 
 * @author James Meadows
 * @author Stefano Georges
 * @author Chong Ting
 * @author Christopher Troll
 * @author Emad Davis
 *
 */

@EnableScheduling
@SpringBootApplication
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
