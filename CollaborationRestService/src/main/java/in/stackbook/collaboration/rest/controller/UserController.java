package in.stackbook.collaboration.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import in.stackbook.collaboration.dao.FriendDAO;
import in.stackbook.collaboration.dao.UserDAO;
import in.stackbook.collaboration.model.User;
import in.stackbook.collaboration.model.UserNotification;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired UserDAO userDAO;
	
	@Autowired User user;
	
	@Autowired UserNotification userNotification;
	
	@Autowired FriendDAO friendDAO;
	
	@Autowired HttpSession session;
		
	//http://localhost:8081/CollaborationRestService
	
	@GetMapping("/")
	public String serverTest() {
		return "home";
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		if(userDAO.get(user.getEmail_id()) == null) {
			//System.out.println(image.getBytes().length);
			//user.setImage(image.getBytes());
			if(userDAO.save(user)) {
				user.setMessage("User registered successfully");
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}
			user.setMessage("Please contact admin");
			return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		user.setMessage("User already exists with this email id");
		return new ResponseEntity<User>(user, HttpStatus.CONFLICT);
		
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userDAO.list();
		if(users.size() == 0) {
			user.setMessage("No users registered");
			return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/list/suggestedUsers")
	public ResponseEntity<List<User>> listSuggestedUsers() {
		
		user = (User)session.getAttribute("loggedInUser");
		
		List<User> users = friendDAO.listSuggestedUsers(user.getEmail_id());
		
		if(users.isEmpty()) {
			user = new User();
			user.setMessage("No other users available to add as friend");
			users.add(user);
			return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		
	}
	
	@GetMapping("/list/mutualFriends/{email_id}")
	public ResponseEntity<List<User>> listMutualFriends(@PathVariable String email_id) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		List<User> users = friendDAO.listMutualFriends(user.getEmail_id(), email_id);
		
		if(users.isEmpty()) {
			user = new User();
			user.setMessage("No other users available to add as friend");
			users.add(user);
			return new ResponseEntity<List<User>>(users, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		
	}
	
	@GetMapping("/list/blocked")		
	public ResponseEntity<List<User>> listBlockedUsers() {
		
		user = (User)session.getAttribute("loggedInUser");
		
		List<User> users = friendDAO.listBlockedUsers(user.getEmail_id());
		
		if(users.isEmpty()) {
			user = new User();
			user.setMessage("You have not blocked any users");
			users.add(user);
			return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		
	}
	
	@GetMapping("/get/{email_id}")
	public ResponseEntity<User> getUser(@PathVariable String email_id) {
		user = userDAO.get(email_id);
		if(user!=null) {
			user.setMessage("User retrieved successfully");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		user = new User();
		user.setMessage("No user with this email id exists");
		return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/get/image/{email_id}")
	public @ResponseBody byte[] getImage(@PathVariable String email_id) {
		user = userDAO.get(email_id);
		if(user != null) {
			byte[] image = user.getImage();
			if(image != null) {
				return image;
			}
			return null;
		}
		return null;
	}
	
	@PostMapping("/validate")
	public ResponseEntity<User> validateCredentials(@RequestBody User user) {
		user = userDAO.validate(user.getEmail_id(), user.getPassword());
		if(user != null) {
			session.setAttribute("loggedInUser", user);
			user.setMessage("Logged in successfully");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		user = new User();
		user.setMessage("Invalid email id or password");
		return new ResponseEntity<User>(user, HttpStatus.UNAUTHORIZED);	
	}
	
	@PutMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		if(userDAO.get(user.getEmail_id()) != null) {
			user.setImage(user.getImage());
			if(userDAO.update(user)) {
				user.setMessage("Successfully updated the user");
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}
			user.setMessage("Could not update the user, please try after some time");
			return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		user.setMessage("No user with this email id exists");
		return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		
	}
	
	@PostMapping("/upload")
	public ResponseEntity<User> uploadImage(@RequestParam(value="image") CommonsMultipartFile image) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		if(user != null) {
			if(image != null) {
				user.setImage(image.getBytes());
				if(userDAO.update(user)) {
					user.setMessage("Image uploaded successfully");
					return new ResponseEntity<User>(user, HttpStatus.OK);
				}
			}
			user.setMessage("Image is empty");
			return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
		}
		user.setMessage("No user with this email id exists");
		return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		
	}
	
	@DeleteMapping("/delete/{email_id}")
	public ResponseEntity<User> deleteUser(@PathVariable String email_id) {
		if(userDAO.get(email_id)==null) {
			user.setMessage("No user with this email id exists");
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
		if(userDAO.delete(email_id)) {
			user.setMessage("User deleted successfully");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		user.setMessage("User could not be deleted, please try after some time");
		return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	//User Notification 
	
	
	@PostMapping("/notification/save")
	public ResponseEntity<UserNotification> saveUserNotification(@RequestBody UserNotification userNotification) {
		if(userDAO.save(userNotification)) {
			userNotification.setMessage("User Notification added successfully");
			return new ResponseEntity<UserNotification>(userNotification, HttpStatus.OK);
		}
		userNotification.setMessage("Could not save the notification");
		return new ResponseEntity<UserNotification>(userNotification, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/notification/list")
	public ResponseEntity<List<UserNotification>> listAllNotifications() {
		user = (User)session.getAttribute("loggedInUser");
		if(user == null) {
			userNotification = new UserNotification();
			userNotification.setMessage("Please login to view Notifications");
			List<UserNotification> notifications = new ArrayList<UserNotification>();
			notifications.add(userNotification);
			return new ResponseEntity<List<UserNotification>>(notifications, HttpStatus.UNAUTHORIZED);
		}
		List<UserNotification> notifications = userDAO.listAllNotificationsFor(user.getEmail_id());
		if(notifications.size() != 0) {			
			return new ResponseEntity<List<UserNotification>>(notifications, HttpStatus.OK);
		}
		userNotification = new UserNotification();
		userNotification.setMessage("No notifications received");
		notifications.add(userNotification);
		return new ResponseEntity<List<UserNotification>>(notifications, HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/notification/update/seen/{notification_id}/{seen}")
	public ResponseEntity<UserNotification> updateNotificationViewed(@PathVariable int notification_id,
			@PathVariable char seen) {
		userNotification = userDAO.get(notification_id);
		if(userNotification!=null) {
			if(seen != userNotification.getSeen()) {
				if(userDAO.update(userNotification)) {
					userNotification.setMessage("Notification updated successfully");
					return new ResponseEntity<UserNotification>(userNotification, HttpStatus.OK);
				}
				userNotification.setMessage("Could not update the notification");
				return new ResponseEntity<UserNotification>(userNotification, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			userNotification.setMessage("Cannot update seen to be same as before");
			return new ResponseEntity<UserNotification>(userNotification, HttpStatus.CONFLICT);
		}
		userNotification = new UserNotification();
		userNotification.setMessage("Notification does not exist .. cannot update");
		return new ResponseEntity<UserNotification>(userNotification, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/notification/update/setAllAsSeen")
	public ResponseEntity<UserNotification> updateNotificationSetAllAsSeen() {
		userNotification = new UserNotification();
		user = (User)session.getAttribute("loggedInUser");
		if(userDAO.listAllNotificationsFor(user.getEmail_id()).size() == 0) {
			userNotification.setMessage("You have no notifications");
			return new ResponseEntity<UserNotification>(userNotification, HttpStatus.NO_CONTENT);
		}
		int update;
		update = userDAO.updateSetAllAsSeen(user.getEmail_id());
		if(update > 0) {
			userNotification.setMessage("Successfully updated all notifications to seen");
			return new ResponseEntity<UserNotification>(userNotification, HttpStatus.OK);
		}
		else if(update == 0) {
			userNotification.setMessage("All notification are already set as seen");
			return new ResponseEntity<UserNotification>(userNotification, HttpStatus.METHOD_NOT_ALLOWED);
		}
		userNotification.setMessage("Could not update the notifications");
		return new ResponseEntity<UserNotification>(userNotification, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/notification/sendToAll/{role}")
	public ResponseEntity<UserNotification> sendNotificationToAll(@RequestBody UserNotification userNotification,
			@PathVariable int role) {
		if(role == 5) {
			if(userDAO.sendNotificationToAllUsers(userNotification)) {
				userNotification.setMessage("Notification sent to all Users");
				return new ResponseEntity<UserNotification>(userNotification, HttpStatus.OK);
			}
			userNotification.setMessage("Could not send the notifications");
			return new ResponseEntity<UserNotification>(userNotification, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		userNotification.setMessage("You do not have the authority for this action");
		return new ResponseEntity<UserNotification>(userNotification, HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/notification/sendToUsersByRole/{role}/{userRole}")
	public ResponseEntity<UserNotification> sendNotificationToUsersByRole(@RequestBody UserNotification userNotification, 
			@PathVariable int role, @PathVariable int userRole) {
		if(userRole >= role) {
			if(userDAO.sendNotificationToUsersBasedOnRole(userNotification, role)) {
				userNotification.setMessage("Notification sent to Users");
				return new ResponseEntity<UserNotification>(userNotification, HttpStatus.OK);
			}
			userNotification.setMessage("Could not send the notifications");
			return new ResponseEntity<UserNotification>(userNotification, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		userNotification.setMessage("You do not have the authority for this action");
		return new ResponseEntity<UserNotification>(userNotification, HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/notification/sendToUsersByMinMaxRole/{minRole}/{maxRole}/{userRole}")
	public ResponseEntity<UserNotification> sendNotificationToUsersByMinMaxRole(@RequestBody UserNotification userNotification, 
			@PathVariable int minRole, @PathVariable int maxRole, @PathVariable int userRole) {
		if(userRole >= maxRole) {
				if(userDAO.sendNotificationToUsersBasedOnMinMaxRole(userNotification, minRole, maxRole)) {
					userNotification.setMessage("Notification sent to Users");
					return new ResponseEntity<UserNotification>(userNotification, HttpStatus.OK);
				}
				userNotification.setMessage("Could not send the notifications");
				return new ResponseEntity<UserNotification>(userNotification, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		userNotification.setMessage("You do not have the authority for this action");
		return new ResponseEntity<UserNotification>(userNotification, HttpStatus.FORBIDDEN);
	}

}
