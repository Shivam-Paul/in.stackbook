package in.stackbook.collaboration.rest.controller;

import java.util.ArrayList;
import java.util.Iterator;
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

import in.stackbook.collaboration.dao.ForumDAO;
import in.stackbook.collaboration.model.Forum;
import in.stackbook.collaboration.model.ForumComment;

@RestController
@RequestMapping("/forum")
public class ForumController {
	
	@Autowired Forum forum;
	
	@Autowired ForumDAO forumDAO;
	
	@Autowired ForumComment forumComment;
	
	@GetMapping("/list/{forum_level}")		
	public ResponseEntity<List<Forum>> listForumsAtLevel(@PathVariable int forum_level) {
		
		List<Integer> forum_id = forumDAO.listByLevel(forum_level);
		List<Forum> forums = new ArrayList<Forum>();
		if(forum_id.isEmpty()) {
			forum = new Forum();
			forum.setMessage("No forums are available");
			forums.add(forum);
			return new ResponseEntity<List<Forum>>(forums, HttpStatus.NOT_FOUND);
		}
		Iterator<Integer> id = forum_id.iterator();
		int tempID = 0;
		while(id.hasNext()) {
			Forum tempForum = new Forum();
			tempID = id.next();
			tempForum.setForum_id(tempID);
			tempForum.setTitle(forumDAO.getTitle(tempID));
			forums.add(tempForum);
		}
		return new ResponseEntity<List<Forum>>(forums, HttpStatus.OK);
		
	}
	
	@PostMapping("/save")
	public ResponseEntity<Forum> saveForum(@RequestBody Forum forum) {
		
		if(forumDAO.save(forum)) {
			forum.setMessage("Forum saved successfully");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		forum.setMessage("Could not save the forum .. please try after some time");
		return new ResponseEntity<Forum>(forum, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@PostMapping("/update")
	public ResponseEntity<Forum> updateForum(@RequestBody Forum forum) {
		
		if(forumDAO.update(forum)) {
			forum.setMessage("Forum updated successfully");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		forum.setMessage("Could not update the forum .. please try after some time");
		return new ResponseEntity<Forum>(forum, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/get/{forum_id}")
	public ResponseEntity<Forum> getForum(@PathVariable int forum_id) {
		
		forum = forumDAO.get(forum_id);
		
		if(forum!=null) {
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		return new ResponseEntity<Forum>(forum, HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/delete/{forum_id}")
	public ResponseEntity<Forum> deleteForum(@PathVariable int forum_id) {
		
		if(forumDAO.delete(forum_id)) {
			forum.setMessage("Forum deleted successfully");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		forum.setMessage("Could not delete the forum .. please try after some time");
		return new ResponseEntity<Forum>(forum, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	// Forum Comment #################
	
	@GetMapping("/{forum_id}/level")
	public ResponseEntity<List<ForumComment>> listCommentsOnForum(@PathVariable int forum_id) {
		
		List<ForumComment> comments = forumDAO.list(forum_id);
		if(comments.isEmpty()) {
			forumComment = new ForumComment();
			forumComment.setMessage("No Comments on this forum yet");
			comments.add(forumComment);
			return new ResponseEntity<List<ForumComment>>(comments, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<ForumComment>>(comments, HttpStatus.OK);
		
	}
	
	@PostMapping("/comment/save")
	public ResponseEntity<ForumComment> saveForumComment(@RequestBody ForumComment forumComment) {
		
		if(forumDAO.save(forumComment)) {
			forum.setMessage("Forum Comment saved successfully");
			return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
		}
		forum.setMessage("Could not save the forum Comment .. please try after some time");
		return new ResponseEntity<ForumComment>(forumComment, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@PostMapping("/comment/update")
	public ResponseEntity<ForumComment> updateForumComment(@RequestBody ForumComment forumComment) {
		
		if(forumDAO.update(forumComment)) {
			forum.setMessage("Forum Comment updated successfully");
			return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
		}
		forum.setMessage("Could not update the forum Comment .. please try after some time");
		return new ResponseEntity<ForumComment>(forumComment, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/get/comment/{f_comment_id}")
	public ResponseEntity<ForumComment> getForumComment(@PathVariable int f_comment_id) {
		
		forumComment = forumDAO.getForumComment(f_comment_id);
		
		if(forumComment!=null) {
			return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
		}
		return new ResponseEntity<ForumComment>(forumComment, HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/delete/comment/{f_comment_id}")
	public ResponseEntity<ForumComment> deleteForumComment(@PathVariable int f_comment_id) {
		
		if(forumDAO.delete(f_comment_id)) {
			forum.setMessage("Forum Comment deleted successfully");
			return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
		}
		forum.setMessage("Could not delete the forum Comment .. please try after some time");
		return new ResponseEntity<ForumComment>(forumComment, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	
	
}
