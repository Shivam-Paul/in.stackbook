package in.stackbook.collaboration.rest.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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
import in.stackbook.collaboration.model.User;

@RestController
@RequestMapping("/forum")
public class ForumController {
	
	@Autowired Forum forum;
	
	@Autowired ForumDAO forumDAO;
	
	@Autowired ForumComment forumComment;
	
	@Autowired HttpSession session;
	
	@Autowired User user;
	
	/*@GetMapping("/list/{forum_level}")		
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
		
	}*/
	
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
	
	@GetMapping("/approveForum/{forum_id}")
	public ResponseEntity<Forum> approveForum(@PathVariable int forum_id) {
		
		forum = forumDAO.get(forum_id);
		
		forum.setApproved('A');
		if(forumDAO.update(forum)) {
			forum.setMessage("Approved Forum");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		forum.setMessage("Approved Forum");
		return new ResponseEntity<Forum>(forum, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/get/{forum_id}")
	public ResponseEntity<Forum> getForum(@PathVariable int forum_id) {
		
		forum = forumDAO.get(forum_id);
		
		if(forum!=null) {
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		forum = new Forum();
		forum.setMessage("Forum does not exist");
		return new ResponseEntity<Forum>(forum, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/delete/{forum_id}")
	public ResponseEntity<Forum> deleteForum(@PathVariable int forum_id) {
		
		if(forumDAO.delete(forum_id)) {
			forum.setMessage("Forum deleted successfully");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		forum.setMessage("Could not delete the forum .. please try after some time");
		return new ResponseEntity<Forum>(forum, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<Forum>> list() {
		
		List<Forum> forums = forumDAO.list();
		
		if(forums.isEmpty()) {
			forum = new Forum();
			forum.setMessage("There are no forums");
			forums.add(forum);
			return new ResponseEntity<List<Forum>>(forums, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Forum>>(forums, HttpStatus.OK);
	}
	
	@GetMapping("/list/approved/{approved}")
	public ResponseEntity<List<Forum>> listByApproval(@PathVariable char approved) {
		
		List<Forum> forums = forumDAO.listByApproval(approved);
		
		if(forums.isEmpty()) {
			forum = new Forum();
			forum.setMessage("There are no forums");
			forums.add(forum);
			return new ResponseEntity<List<Forum>>(forums, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Forum>>(forums, HttpStatus.OK);
	}
	
	@GetMapping("/list/archived/{archived}")
	public ResponseEntity<List<Forum>> listByArchive(@PathVariable char archived) {
		
		List<Forum> forums = forumDAO.listByArchive(archived);
		
		if(forums.isEmpty()) {
			forum = new Forum();
			forum.setMessage("There are no forums");
			forums.add(forum);
			return new ResponseEntity<List<Forum>>(forums, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Forum>>(forums, HttpStatus.OK);
	}
	
	@GetMapping("/list/level/{forum_level}")
	public ResponseEntity<List<Forum>> listByLevel(@PathVariable int forum_level) {
		
		List<Forum> forums = forumDAO.listByLevel(forum_level);
		
		if(forums.isEmpty()) {
			forum = new Forum();
			forum.setMessage("There are no forums");
			forums.add(forum);
			return new ResponseEntity<List<Forum>>(forums, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Forum>>(forums, HttpStatus.OK);
	}
	
	@GetMapping("/list/parentID/{parent_id}")
	public ResponseEntity<List<Forum>> listByParentID(@PathVariable int parent_id) {
		
		List<Forum> forums = forumDAO.listAllForParentID(parent_id);
		
		if(forums.isEmpty()) {
			forum = new Forum();
			forum.setMessage("There are no forums");
			forums.add(forum);
			return new ResponseEntity<List<Forum>>(forums, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Forum>>(forums, HttpStatus.OK);
	}
	
	@GetMapping("/list/newPostAccess/{new_post_access}")
	public ResponseEntity<List<Forum>> listByNewPostAccess(@PathVariable int new_post_access) {
		
		List<Forum> forums = forumDAO.listByNewPostAccess(new_post_access);
		
		if(forums.isEmpty()) {
			forum = new Forum();
			forum.setMessage("There are no forums");
			forums.add(forum);
			return new ResponseEntity<List<Forum>>(forums, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Forum>>(forums, HttpStatus.OK);
	}
	
	@GetMapping("/list/viewAccess/{view_access}")
	public ResponseEntity<List<Forum>> listByViewAccess(@PathVariable int view_access) {
		
		List<Forum> forums = forumDAO.listByViewAccess(view_access);
		
		if(forums.isEmpty()) {
			forum = new Forum();
			forum.setMessage("There are no forums");
			forums.add(forum);
			return new ResponseEntity<List<Forum>>(forums, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Forum>>(forums, HttpStatus.OK);
	}
	
	@GetMapping("/list/commentAccess/{comment_access}")
	public ResponseEntity<List<Forum>> listByCommentAccess(@PathVariable int comment_access) {
		
		List<Forum> forums = forumDAO.listByCommentAccess(comment_access);
		
		if(forums.isEmpty()) {
			forum = new Forum();
			forum.setMessage("There are no forums");
			forums.add(forum);
			return new ResponseEntity<List<Forum>>(forums, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Forum>>(forums, HttpStatus.OK);
	}
	
	@GetMapping("/list/pendingApproval")
	public ResponseEntity<List<Forum>> listPendingApproval() {
		
		List<Forum> forums = forumDAO.listPendingApprovalForums();
		
		if(forums.isEmpty()) {
			forum = new Forum();
			forum.setMessage("There are no forums");
			forums.add(forum);
			return new ResponseEntity<List<Forum>>(forums, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Forum>>(forums, HttpStatus.OK);
	}
	
	@GetMapping("/list/approvedForumsIn/{forum_level}/{parent_id}")
	public ResponseEntity<List<Forum>> listApprovedForumInCurrentLevelByParentID(@PathVariable int forum_level,
			@PathVariable int parent_id) {
		
		List<Forum> forums = forumDAO.listApprovedForumsInCurrentLevelByParentID(forum_level, parent_id);
		
		if(forums.isEmpty()) {
			forum = new Forum();
			forum.setMessage("There are no forums");
			forums.add(forum);
			return new ResponseEntity<List<Forum>>(forums, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Forum>>(forums, HttpStatus.OK);
		
	}
	
	

	// Forum Comment #################
	
	
	@GetMapping("/comment/listCommentsOn/{forum_id}")
	public ResponseEntity<List<ForumComment>> listCommentsOn(@PathVariable int forum_id) {
		
		List<ForumComment> comments = forumDAO.list(forum_id);
		if(comments.isEmpty()) {
			forumComment = new ForumComment();
			forumComment.setMessage("No Comments on this forum yet");
			comments.add(forumComment);
			return new ResponseEntity<List<ForumComment>>(comments, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<ForumComment>>(comments, HttpStatus.OK);
		
	}
	
	@GetMapping("/comment/listCommentsOnBy/{forum_id}")
	public ResponseEntity<List<ForumComment>> listCommentsOnBy(@PathVariable int forum_id) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		List<ForumComment> comments = forumDAO.list(forum_id, user.getEmail_id());
		if(comments.isEmpty()) {
			forumComment = new ForumComment();
			forumComment.setMessage("No Comments on this forum yet");
			comments.add(forumComment);
			return new ResponseEntity<List<ForumComment>>(comments, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<ForumComment>>(comments, HttpStatus.OK);
		
	}
	
	@GetMapping("/comment/listCommentsFrom")
	public ResponseEntity<List<ForumComment>> listCommentsFrom() {
		
		user = (User)session.getAttribute("loggedInUser");
		
		List<ForumComment> comments = forumDAO.list(user.getEmail_id());
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
	
	@GetMapping("/comment/get/{f_comment_id}")
	public ResponseEntity<ForumComment> getForumComment(@PathVariable int f_comment_id) {
		
		forumComment = forumDAO.getForumComment(f_comment_id);
		
		if(forumComment!=null) {
			return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
		}
		return new ResponseEntity<ForumComment>(forumComment, HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/comment/delete/{f_comment_id}")
	public ResponseEntity<ForumComment> deleteForumComment(@PathVariable int f_comment_id) {
		
		if(forumDAO.delete(f_comment_id)) {
			forum.setMessage("Forum Comment deleted successfully");
			return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
		}
		forum.setMessage("Could not delete the forum Comment .. please try after some time");
		return new ResponseEntity<ForumComment>(forumComment, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	
	
}
