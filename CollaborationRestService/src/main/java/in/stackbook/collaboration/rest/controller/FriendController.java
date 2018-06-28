package in.stackbook.collaboration.rest.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.stackbook.collaboration.dao.FriendDAO;
import in.stackbook.collaboration.dao.UserDAO;
import in.stackbook.collaboration.model.Friend;
import in.stackbook.collaboration.model.User;

@RestController
@RequestMapping("/friend")
public class FriendController {
	
	@Autowired Friend friend;
	
	@Autowired FriendDAO friendDAO;
	
	@Autowired User user;
	
	@Autowired UserDAO userDAO;
	
	@Autowired HttpSession session;
	
	@GetMapping("/list/friends")		
	public ResponseEntity<List<Friend>> listAllFriends() {
		
		user = (User)session.getAttribute("loggedInUser");
		
		List<Friend> friends = friendDAO.listFriends(user.getEmail_id());
		
		if(friends.isEmpty()) {
			friend = new Friend();
			friend.setMessage("You do not have any friends");
			friends.add(friend);
			return new ResponseEntity<List<Friend>>(friends, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
		
	}
	
	@GetMapping("/list/requests")		
	public ResponseEntity<List<Friend>> listFriendRequests() {
		
		user = (User)session.getAttribute("loggedInUser");
		
		List<Friend> friends = friendDAO.listReceivedFriendRequests(user.getEmail_id());
		
		if(friends.isEmpty()) {
			friend = new Friend();
			friend.setMessage("You have not received any friend requests");
			friends.add(friend);
			return new ResponseEntity<List<Friend>>(friends, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
		
	}	
	
	@GetMapping("/list/sentRequests")		
	public ResponseEntity<List<Friend>> listSentRequests() {
		
		user = (User)session.getAttribute("loggedInUser");
		
		List<Friend> friends = friendDAO.listSentFriendRequests(user.getEmail_id());
		
		if(friends.isEmpty()) {
			friend = new Friend();
			friend.setMessage("You do not have any outstanding friend requests");
			friends.add(friend);
			return new ResponseEntity<List<Friend>>(friends, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
		
	}
	
	@GetMapping("/list/blocked")		
	public ResponseEntity<List<User>> listBlocked() {
		
		user = (User)session.getAttribute("loggedInUser");
		
		List<User> users = friendDAO.listBlockedUsers(user.getEmail_id());
		
		if(users.isEmpty()) {
			user = new User();
			user.setMessage("You do not have any blocked users");
			users.add(user);
			return new ResponseEntity<List<User>>(users, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		
	}
	
	@GetMapping("/save/{email_id2}/{friends}")
	public ResponseEntity<Friend> saveFriend(@PathVariable String email_id2, @PathVariable int friends) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		friend = friendDAO.get(user.getEmail_id(), email_id2);
		
		if(friend == null) {
			Friend friend = new Friend();
			friend.setEmail_id1(user.getEmail_id());
			friend.setEmail_id2(email_id2);
			friend.setFriends(friends);
			if(friendDAO.save(friend)) {
				friend.setMessage("Friend request sent");
				return new ResponseEntity<Friend>(friend, HttpStatus.OK);
			}
			friend.setMessage("Could not send friend request .. please try after some time");
			return new ResponseEntity<Friend>(friend, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		friend.setMessage("User already added");
		return new ResponseEntity<Friend>(friend, HttpStatus.CONFLICT);
		
	}
	
	@GetMapping("/update/{friend_id}/{friends}")
	public ResponseEntity<Friend> updateFriend(@PathVariable int friend_id, @PathVariable int friends) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		friend = friendDAO.get(friend_id);
		
		if(friend.getFriends() != friends) {
			friend.setFriends(friends);
			if(friendDAO.update(friend)) {
				friend.setMessage("Friend updated successfully");
				return new ResponseEntity<Friend>(friend, HttpStatus.OK);
			}
			friend.setMessage("Could not update the friend .. please try after some time");
			return new ResponseEntity<Friend>(friend, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		friend.setMessage("User already has the same status");
		return new ResponseEntity<Friend>(friend, HttpStatus.CONFLICT);		
	}
	
	@GetMapping("/get/{friend_id}")
	public ResponseEntity<Friend> getFriend(@PathVariable int friend_id) {
		
		friend = friendDAO.get(friend_id);
		
		if(friend != null) {
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		friend = new Friend();
		friend.setMessage("User is not your friend");
		return new ResponseEntity<Friend>(friend, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/get/usingEmail/{email_id1}/{email_id2}")
	public ResponseEntity<Friend> getFriendUsingEmail(@PathVariable String email_id1, @PathVariable String email_id2) {
		
		friend = friendDAO.get(email_id1, email_id2);
		
		if(friend != null) {
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		friend = new Friend();
		friend.setMessage("User is not your friend");
		return new ResponseEntity<Friend>(friend, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/get/name/{email_id}")
	public ResponseEntity<String> getUserName(@PathVariable String email_id) {
		
		user = userDAO.get(email_id);
		String name = null;
		if(user != null) {
			name = user.getName();
			return new ResponseEntity<String>(name, HttpStatus.OK);
		}
		return new ResponseEntity<String>(name, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/delete/{friend_id}")
	public ResponseEntity<Friend> deleteFriend(@PathVariable int friend_id) {
		
		if(friendDAO.delete(friend_id)) {
			friend.setMessage("User Unfriended successfully");
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		friend.setMessage("Could not unfriend the user .. please try after some time");
		return new ResponseEntity<Friend>(friend, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	

}
