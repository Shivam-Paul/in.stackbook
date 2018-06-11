package in.stackbook.collaboration.rest.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import in.stackbook.collaboration.dao.ProfilePictureDAO;
import in.stackbook.collaboration.dao.UserDAO;
import in.stackbook.collaboration.model.ProfilePicture;
import in.stackbook.collaboration.model.User;

@RestController
@RequestMapping("/picture")
public class ProfilePictureController {
	
	@Autowired ProfilePicture profilePicture;
	
	@Autowired ProfilePictureDAO profilePictureDAO;
	
	@Autowired UserDAO userDAO;
	
	@Autowired User user;
	
	@Autowired HttpSession session;
	
	@PostMapping("/save/{email_id}")
	public ResponseEntity<ProfilePicture> uploadProfilePicture(@RequestParam(value="file") CommonsMultipartFile picture,
			HttpSession session, @PathVariable String email_id) {
		
		user = userDAO.get(email_id);
		
		if(user != null) {
			profilePicture.setEmail_id(email_id);
			profilePicture.setImage(picture.getBytes());
			if(profilePictureDAO.save(profilePicture)) {
				profilePicture.setMessage("Image uploaded successfully");
				return new ResponseEntity<ProfilePicture>(profilePicture, HttpStatus.OK);
			}
			profilePicture.setMessage("Could not upload the image");
			return new ResponseEntity<ProfilePicture>(profilePicture, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			profilePicture.setMessage("User does not exist");
			return new ResponseEntity<ProfilePicture>(profilePicture, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/get/{email_id}")
	public ResponseEntity<ProfilePicture> getProfilePicture(@PathVariable String email_id) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		if(user != null) {
			profilePicture = profilePictureDAO.get(email_id);
			if(profilePicture.getImage() != null) {
				profilePicture.setMessage("Retrived Image successfully");
			}
			else {
				profilePicture.setMessage("No image uploaded");
			}
			return new ResponseEntity<ProfilePicture>(profilePicture, HttpStatus.OK);
		}
		profilePicture.setMessage("User does not exist");
		return new ResponseEntity<ProfilePicture>(profilePicture, HttpStatus.NOT_FOUND);
		
	}
	
	
	

}
