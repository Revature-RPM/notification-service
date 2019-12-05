package com.revature.rpm.components;

import java.io.IOException;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rpm.entities.Notification;
import com.revature.rpm.services.NotificationService;

@Component
public class Listener implements InitializingBean {

	// Injecting environment variable data into strings
	@Value("${MESSAGING_ACCESS_KEY}")
	private String accessKey;
	
	@Value("${MESSAGING_SECRET_ACCESS_KEY}")
	private String secretKey;
	
	@Value("${MESSAGING_REGION}")
	private String region;
	
	@Value("${MESSAGING_QUEUE_URL}")
	private String queueUrl;
	
	// ObjectMapper can map between Java/JSON
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	NotificationService notificationService;
	
	Logger logger = Logger.getLogger(Listener.class);

	private BasicAWSCredentials credentials;
	private AmazonSQS sqsClient;
	
	// afterPropertiesSet is the appropriate place to initialize them
	@Override
	public void afterPropertiesSet() throws Exception {
		credentials = new BasicAWSCredentials(accessKey, secretKey);
		sqsClient = AmazonSQSClient.builder()
						.withCredentials(new AWSStaticCredentialsProvider(credentials))
						.withRegion(region)
						.build();
	}
	
	
	@Scheduled(fixedRate = 15000)
	private void scheduledPolling() {
		logger.info("Polling queue ...");
		ReceiveMessageResult pollResult = getMessages();
		List<Message> messages = pollResult.getMessages();
		
		messages.forEach(message -> {
			String body = message.getBody();
			Notification notification = null;
			
			try {
				notification = objectMapper.readValue(body, Notification.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			//logger method to log incoming Notification
			logger.info("Received notification request: " + notification.getTitle() + " with project ID: " + notification.getProjectId());
			
			//Method to insert Notification
			notificationService.save(notification);
			deleteMessage(message.getReceiptHandle());
			
		});
	}
	
	public ReceiveMessageResult getMessages() {
		ReceiveMessageRequest messageRequest = new ReceiveMessageRequest(queueUrl);
		messageRequest.setVisibilityTimeout(15);
		ReceiveMessageResult result = sqsClient.receiveMessage(messageRequest);
		return result;
	}
	
	public void deleteMessage(String receiptHandle) {
		DeleteMessageRequest deleteRequest = new DeleteMessageRequest(queueUrl, receiptHandle);
		sqsClient.deleteMessage(deleteRequest);
	}
	
}