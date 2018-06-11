package in.stackbook.collaboration.rest.controller;

import java.sql.Timestamp;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import in.stackbook.collaboration.model.Message;
import in.stackbook.collaboration.model.OutputMessage;

@RestController
public class ChatController {
	
	@MessageMapping("/chat") //org.springframework.messaging.handler.annotation package. both
	@SendTo("/topic/message") 
	public OutputMessage sendMessage(Message message) {
		return new OutputMessage(message, new Timestamp(System.currentTimeMillis()));
	}

}
