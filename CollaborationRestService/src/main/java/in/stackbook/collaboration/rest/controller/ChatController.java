package in.stackbook.collaboration.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import in.stackbook.collaboration.model.Chat;

/*@RestController
public class ChatController {
	
	@MessageMapping("/chat") //org.springframework.messaging.handler.annotation package. both
	@SendTo("/topic/message") 
	public OutputMessage sendMessage(Message message) {
		return new OutputMessage(message, new Date(System.currentTimeMillis()));
	}

}*/

@Controller
public class ChatController {
	
	private SimpMessagingTemplate messagingTemplate;

	private List<String> users = new ArrayList<String>();


	@Autowired

	public void SockController(SimpMessagingTemplate messagingTemplate) {

		this.messagingTemplate = messagingTemplate;

	}

	@SubscribeMapping("/join/{username}")

	public List<String> join(@DestinationVariable("username") String username) {
        

		 System.out.println("username in sockcontroller" + username);
		 
		 if(!users.contains(username)) {
				users.add(username);
			}


		System.out.println("====JOIN==== " + username);

		// notify all subscribers of new user

		messagingTemplate.convertAndSend("/topic/join", username);

		return users;

	}

	@MessageMapping(value = "/chat")

	public void chatReveived(Chat chat) {


		if ("all".equals(chat.getTo())) {

			System.out.println("IN CHAT REVEIVED " + chat.getMessage() + " " + chat.getFrom() + " to " + chat.getTo());

			messagingTemplate.convertAndSend("/queue/chats", chat);

		}

		else {

			System.out.println("CHAT TO " + chat.getTo() + " From " + chat.getFrom() + " Message " + chat.getMessage());

			messagingTemplate.convertAndSend("/queue/chats/" + chat.getTo(), chat);

			messagingTemplate.convertAndSend("/queue/chats/" + chat.getFrom(), chat);

		}

	}
}
