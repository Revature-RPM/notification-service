package com.revature.rpm.components;

import java.io.IOException;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
import com.revature.rpm.dto.SQSDTO;
import com.revature.rpm.entities.Comment;
import com.revature.rpm.services.AdapterService;
import com.revature.rpm.services.NotificationService;

@Component
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = false)
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
	
	@Autowired
	AdapterService adapterService;
	
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
		ReceiveMessageResult pollResult = getMessages();
		List<Message> messages = pollResult.getMessages();
		
		messages.forEach(message -> {
			String body = message.getBody();
			SQSDTO dto = null;
//			Notification notification = null;
			
			try {
				dto = objectMapper.readValue(body, SQSDTO.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			//logger method to log incoming Notification
			logger.info("Received notification request: " + dto.getTitle() + " with project ID: " + dto.getProjectId());
			
			//Cast DTO into appropriate entity, then save it
			/*
			 * AdapterService should be updated to accept a SQSDTO and output the appropriate object type in the future
			 * At the moment, the notificiation type is determined here.
			*/
			if(dto.getContentType().equals("Comment")) {
				Comment comment = adapterService.parseComment(dto);
				notificationService.save(comment);
				deleteMessage(message.getReceiptHandle());
			} 

			
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