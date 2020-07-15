package com.dor.demoSample.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
	
	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;
	
	@Value("${cloud.aws.end-point.uri}")
	private String sqsEndPoint;
	
	@GetMapping("/talk")
	public String sayHello() {
		
		return "Hello you";
	}

	@GetMapping("/put")
	public void sendMessage() {
		System.out.println("enviando mensaje: " + sqsEndPoint);
		queueMessagingTemplate.send(sqsEndPoint, MessageBuilder.withPayload("hello from Spring Boot").build());
	}
	
	
	@SqsListener(value="QUEUE_TEST2", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
	public void getMessage(String message) {
		
		System.out.println(message);
	}
	

}
