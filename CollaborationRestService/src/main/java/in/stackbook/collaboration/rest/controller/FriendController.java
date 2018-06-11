package in.stackbook.collaboration.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.stackbook.collaboration.dao.FriendDAO;
import in.stackbook.collaboration.model.Friend;
import in.stackbook.collaboration.model.User;

@RestController
@RequestMapping("/friend")
public class FriendController {
	
	@Autowired Friend friend;
	
	@Autowired FriendDAO friendDAO;
	
	@Autowired User user;
	
	@GetMapping("/list/{email_id}")		
	public ResponseEntity<List<Friend>> listAllFriends(@PathVariable String email_id) {
		
		List<Friend> friends = friendDAO.listFriends(email_id);
		
		if(friends.isEmpty()) {
			friend = new Friend();
			friend.setMessage("You do not have any friends");
			friends.add(friend);
			return new ResponseEntity<List<Friend>>(friends, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
		
	}
	
	@GetMapping("/requests/{email_id}")		
	public ResponseEntity<List<Friend>> listFriendRequests(@PathVariable String email_id) {
		
		List<Friend> friends = friendDAO.listReceivedFriendRequests(email_id);
		
		if(friends.isEmpty()) {
			friend = new Friend();
			friend.setMessage("You have not received any friend requests");
			friends.add(friend);
			return new ResponseEntity<List<Friend>>(friends, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
		
	}
	
	@GetMapping("/listSuggestedUsers/{email_id}")
	public ResponseEntity<List<User>> listSuggestedUsers(@PathVariable String email_id) {
		
		List<User> users = friendDAO.listSuggestedUsers(email_id);
		
		if(users.isEmpty()) {
			user = new User();
			user.setMessage("No other users available to add as friend");
			users.add(user);
			return new ResponseEntity<List<User>>(users, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		
	}
	
	@GetMapping("/sentRequests/{email_id}")		
	public ResponseEntity<List<Friend>> listSentRequests(@PathVariable String email_id) {
		
		List<Friend> friends = friendDAO.listSentFriendRequests(email_id);
		
		if(friends.isEmpty()) {
			friend = new Friend();
			friend.setMessage("You do not have any outstanding friend requests");
			friends.add(friend);
			return new ResponseEntity<List<Friend>>(friends, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
		
	}
	
	@PostMapping("/save")
	public ResponseEntity<Friend> saveFriend(@RequestBody Friend friend) {
		
		if(friendDAO.save(friend)) {
			friend.setMessage("Friend request sent");
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		friend.setMessage("Could not send friend request .. please try after some time");
		return new ResponseEntity<Friend>(friend, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@PostMapping("/update")
	public ResponseEntity<Friend> updateFriend(@RequestBody Friend friend) {
		
		if(friendDAO.update(friend)) {
			friend.setMessage("Friend updated successfully");
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		friend.setMessage("Could not update the friend .. please try after some time");
		return new ResponseEntity<Friend>(friend, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/get/{friend_id}")
	public ResponseEntity<Friend> getFriend(@PathVariable int friend_id) {
		
		friend = friendDAO.get(friend_id);
		
		if(friend!=null) {
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		return new ResponseEntity<Friend>(friend, HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/delete/{friend_id}")
	public ResponseEntity<Friend> deleteFriend(@PathVariable int friend_id) {
		
		if(friendDAO.delete(friend_id)) {
			friend.setMessage("User Unfriended successfully");
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		friend.setMessage("Could not unfriend the user .. please try after some time");
		return new ResponseEntity<Friend>(friend, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	

}
