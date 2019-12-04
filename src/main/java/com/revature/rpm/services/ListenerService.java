package com.revature.rpm.services;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

@Service
public class ListenerService {
	public SQSConnectionFactory connectionFactory = new SQSConnectionFactory(
	        new ProviderConfiguration(),
	        AmazonSQSClientBuilder.defaultClient()
	        );
	// Create the connection.
	public SQSConnection connection = null;
	
	@Autowired
	public ListenerService() {
		try {
			connection = connectionFactory.createConnection();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
