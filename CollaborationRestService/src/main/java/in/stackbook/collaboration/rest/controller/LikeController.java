package in.stackbook.collaboration.rest.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.stackbook.collaboration.dao.BlogDAO;
import in.stackbook.collaboration.dao.ForumDAO;
import in.stackbook.collaboration.dao.LikeDAO;
import in.stackbook.collaboration.model.Like;
import in.stackbook.collaboration.model.User;

@RestController
@RequestMapping("/like")
public class LikeController {
	
	@Autowired Like like;
	
	@Autowired LikeDAO likeDAO;
		
	@Autowired BlogDAO blogDAO;
		
	@Autowired ForumDAO forumDAO;
	
	@Autowired User user;
	
	@Autowired HttpSession session;
	
	
	@GetMapping("/blog/add/{blog_id}/{liked}")
	public ResponseEntity<Like> addBlogLike(@PathVariable int blog_id, @PathVariable int liked) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		if(user != null) {
			if(blogDAO.get(blog_id) != null) {
				if(likeDAO.get('B', blog_id, user.getEmail_id()) == null) {
					
					like.setReference_table('B');
					like.setTable_id(blog_id);
					like.setEmail_id(user.getEmail_id());
					like.setLiked(liked);
					
					if(likeDAO.save(like)) {
						like.setMessage("Like added successfully");
						return new ResponseEntity<Like>(like, HttpStatus.OK);
					}
					like.setMessage("Could not add the like .. please try after some time");
					return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				like.setMessage("You have alread liked/disliked this blog");
				return new ResponseEntity<Like>(like, HttpStatus.CONFLICT);
			}
			like.setMessage("Blog does not exist");
			return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
		}
		like.setMessage("You need to login to like/dislike a blog");
		return new ResponseEntity<Like>(like, HttpStatus.UNAUTHORIZED);
		
	}
	
	@GetMapping("/blog/update/{blog_id}/{liked}")
	public ResponseEntity<Like> updateBlogLike(@PathVariable int blog_id, @PathVariable int liked) {
		
		user = (User)session.getAttribute("loggedInUser");
		like = likeDAO.get('B', blog_id, user.getEmail_id());
		
		if(user != null) {
			if(blogDAO.get(blog_id) != null) {
				if(like != null) {
					if(like.getLiked() == 1) {
						like.setLiked(-1);
					}
					else if(like.getLiked() == -1) {
						like.setLiked(1);
					}
					else {
						like.setMessage("Invalid like status .. please contact admin");
						return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
					}
					if(likeDAO.update(like)) {
						like.setMessage("Like updated successfully");
						return new ResponseEntity<Like>(like, HttpStatus.OK);
					}
					like.setMessage("Could not update the like .. please try after some time");
					return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				like.setMessage("You have not liked/disliked this blog");
				return new ResponseEntity<Like>(like, HttpStatus.CONFLICT);
			}
			like.setMessage("Blog does not exist");
			return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
		}
		like.setMessage("You need to login to like/dislike a blog");
		return new ResponseEntity<Like>(like, HttpStatus.UNAUTHORIZED);
		
	}
	
	@DeleteMapping("/blog/delete/{like_id}")
	public ResponseEntity<Like> deleteBlogLike(@PathVariable int like_id) {
				
		if(likeDAO.delete(like_id)) {
			like = new Like();
			like.setMessage("Like deleted successfully");
			return new ResponseEntity<Like>(like, HttpStatus.OK);
		}
		like.setMessage("Could not delete the like .. please try after some time");
		return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/blog/get/{like_id}")
	public ResponseEntity<Like> getBlogLike(@PathVariable int like_id) {
		
		if(likeDAO.get(like_id) != null) {
			return new ResponseEntity<Like>(like, HttpStatus.OK);
		}
		like.setMessage("Like does not exist");
		return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/blog/get/{blog_id}")
	public ResponseEntity<Like> getBlogLikeByDetails(@PathVariable int blog_id) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		if(likeDAO.get('B', blog_id, user.getEmail_id()) != null) {
			return new ResponseEntity<Like>(like, HttpStatus.OK);
		}
		like.setMessage("Like does not exist");
		return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
		
	}
	
	
	//Blog Comment
	
	
	@GetMapping("/blog/comment/add/{b_comment_id}/{liked}")
	public ResponseEntity<Like> addBlogCommentLike(@PathVariable int b_comment_id, @PathVariable int liked) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		if(user != null) {
			if(blogDAO.getBlogComment(b_comment_id) != null) {
				if(likeDAO.get('G', b_comment_id, user.getEmail_id()) == null) {
					
					like.setReference_table('G');
					like.setTable_id(b_comment_id);
					like.setEmail_id(user.getEmail_id());
					like.setLiked(liked);
					
					if(likeDAO.save(like)) {
						like.setMessage("Like added successfully");
						return new ResponseEntity<Like>(like, HttpStatus.OK);
					}
					like.setMessage("Could not add the like .. please try after some time");
					return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				like.setMessage("You have alread liked/disliked this blog comment");
				return new ResponseEntity<Like>(like, HttpStatus.CONFLICT);
			}
			like.setMessage("Blog comment does not exist");
			return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
		}
		like.setMessage("You need to login to like/dislike a blog comment");
		return new ResponseEntity<Like>(like, HttpStatus.UNAUTHORIZED);
		
	}
	
	@GetMapping("/blog/comment/update/{b_comment_id}/{liked}")
	public ResponseEntity<Like> updateBlogCommentLike(@PathVariable int b_comment_id, @PathVariable int liked) {
		
		user = (User)session.getAttribute("loggedInUser");
		like = likeDAO.get('G', b_comment_id, user.getEmail_id());
		
		if(user != null) {
			if(blogDAO.getBlogComment(b_comment_id) != null) {
				if(like != null) {
					if(like.getLiked() == 1) {
						like.setLiked(-1);
					}
					else if(like.getLiked() == -1) {
						like.setLiked(1);
					}
					else {
						like.setMessage("Invalid like status .. please contact admin");
						return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
					}
					if(likeDAO.update(like)) {
						like.setMessage("Like updated successfully");
						return new ResponseEntity<Like>(like, HttpStatus.OK);
					}
					like.setMessage("Could not update the like .. please try after some time");
					return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				like.setMessage("You have not liked/disliked this blog comment");
				return new ResponseEntity<Like>(like, HttpStatus.CONFLICT);
			}
			like.setMessage("Blog comment does not exist");
			return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
		}
		like.setMessage("You need to login to like/dislike a blog comment");
		return new ResponseEntity<Like>(like, HttpStatus.UNAUTHORIZED);
		
	}
	
	@DeleteMapping("/blog/comment/delete/{like_id}")
	public ResponseEntity<Like> deleteBlogCommentLike(@PathVariable int like_id) {
				
		if(likeDAO.delete(like_id)) {
			like = new Like();
			like.setMessage("Like deleted successfully");
			return new ResponseEntity<Like>(like, HttpStatus.OK);
		}
		like.setMessage("Could not delete the like .. please try after some time");
		return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/blog/comment/get/{like_id}")
	public ResponseEntity<Like> getBlogCommentLike(@PathVariable int like_id) {
		
		if(likeDAO.get(like_id) != null) {
			return new ResponseEntity<Like>(like, HttpStatus.OK);
		}
		like.setMessage("Like does not exist");
		return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/blog/comment/get/{b_comment_id}")
	public ResponseEntity<Like> getBlogCommentLikeByDetails(@PathVariable int b_comment_id) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		if(likeDAO.get('G', b_comment_id, user.getEmail_id()) != null) {
			return new ResponseEntity<Like>(like, HttpStatus.OK);
		}
		like.setMessage("Like does not exist");
		return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
		
	}
	
	
	//Forum
	
	
	@GetMapping("/forum/add/{forum_id}/{liked}")
	public ResponseEntity<Like> addForumLike(@PathVariable int forum_id, @PathVariable int liked) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		if(user != null) {
			if(forumDAO.get(forum_id) != null) {
				if(likeDAO.get('F', forum_id, user.getEmail_id()) == null) {
					
					like.setReference_table('F');
					like.setTable_id(forum_id);
					like.setEmail_id(user.getEmail_id());
					like.setLiked(liked);
					
					if(likeDAO.save(like)) {
						like.setMessage("Like added successfully");
						return new ResponseEntity<Like>(like, HttpStatus.OK);
					}
					like.setMessage("Could not add the like .. please try after some time");
					return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				like.setMessage("You have alread liked/disliked this forum");
				return new ResponseEntity<Like>(like, HttpStatus.CONFLICT);
			}
			like.setMessage("Forum does not exist");
			return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
		}
		like.setMessage("You need to login to like/dislike a forum");
		return new ResponseEntity<Like>(like, HttpStatus.UNAUTHORIZED);
		
	}
	
	@GetMapping("/forum/update/{forum_id}/{liked}")
	public ResponseEntity<Like> updateForumLike(@PathVariable int forum_id, @PathVariable int liked) {
		
		user = (User)session.getAttribute("loggedInUser");
		like = likeDAO.get('F', forum_id, user.getEmail_id());
		
		if(user != null) {
			if(forumDAO.get(forum_id) != null) {
				if(like != null) {
					if(like.getLiked() == 1) {
						like.setLiked(-1);
					}
					else if(like.getLiked() == -1) {
						like.setLiked(1);
					}
					else {
						like.setMessage("Invalid like status .. please contact admin");
						return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
					}
					if(likeDAO.update(like)) {
						like.setMessage("Like updated successfully");
						return new ResponseEntity<Like>(like, HttpStatus.OK);
					}
					like.setMessage("Could not update the like .. please try after some time");
					return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				like.setMessage("You have not liked/disliked this forum");
				return new ResponseEntity<Like>(like, HttpStatus.CONFLICT);
			}
			like.setMessage("Forum does not exist");
			return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
		}
		like.setMessage("You need to login to like/dislike a forum");
		return new ResponseEntity<Like>(like, HttpStatus.UNAUTHORIZED);
		
	}
	
	@DeleteMapping("/forum/delete/{like_id}")
	public ResponseEntity<Like> deleteForumLike(@PathVariable int like_id) {
				
		if(likeDAO.delete(like_id)) {
			like = new Like();
			like.setMessage("Like deleted successfully");
			return new ResponseEntity<Like>(like, HttpStatus.OK);
		}
		like.setMessage("Could not delete the like .. please try after some time");
		return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/forum/get/{like_id}")
	public ResponseEntity<Like> getForumLike(@PathVariable int like_id) {
		
		if(likeDAO.get(like_id) != null) {
			return new ResponseEntity<Like>(like, HttpStatus.OK);
		}
		like.setMessage("Like does not exist");
		return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/forum/get/{forum_id}")
	public ResponseEntity<Like> getForumLikeByDetails(@PathVariable int forum_id) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		if(likeDAO.get('F', forum_id, user.getEmail_id()) != null) {
			return new ResponseEntity<Like>(like, HttpStatus.OK);
		}
		like.setMessage("Like does not exist");
		return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
		
	}
	
	
	//Forum Comment
	
	
	@GetMapping("/forum/comment/add/{f_comment_id}/{liked}")
	public ResponseEntity<Like> addForumCommentLike(@PathVariable int f_comment_id, @PathVariable int liked) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		if(user != null) {
			if(forumDAO.getForumComment(f_comment_id) != null) {
				if(likeDAO.get('M', f_comment_id, user.getEmail_id()) == null) {
					
					like.setReference_table('M');
					like.setTable_id(f_comment_id);
					like.setEmail_id(user.getEmail_id());
					like.setLiked(liked);
					
					if(likeDAO.save(like)) {
						like.setMessage("Like added successfully");
						return new ResponseEntity<Like>(like, HttpStatus.OK);
					}
					like.setMessage("Could not add the like .. please try after some time");
					return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				like.setMessage("You have alread liked/disliked this forum comment");
				return new ResponseEntity<Like>(like, HttpStatus.CONFLICT);
			}
			like.setMessage("Forum comment does not exist");
			return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
		}
		like.setMessage("You need to login to like/dislike a forum comment");
		return new ResponseEntity<Like>(like, HttpStatus.UNAUTHORIZED);
		
	}
	
	@GetMapping("/forum/comment/update/{f_comment_id}/{liked}")
	public ResponseEntity<Like> updateForumCommentLike(@PathVariable int f_comment_id, @PathVariable int liked) {
		
		user = (User)session.getAttribute("loggedInUser");
		like = likeDAO.get('M', f_comment_id, user.getEmail_id());
		
		if(user != null) {
			if(forumDAO.getForumComment(f_comment_id) != null) {
				if(like != null) {
					if(like.getLiked() == 1) {
						like.setLiked(-1);
					}
					else if(like.getLiked() == -1) {
						like.setLiked(1);
					}
					else {
						like.setMessage("Invalid like status .. please contact admin");
						return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
					}
					if(likeDAO.update(like)) {
						like.setMessage("Like updated successfully");
						return new ResponseEntity<Like>(like, HttpStatus.OK);
					}
					like.setMessage("Could not update the like .. please try after some time");
					return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				like.setMessage("You have not liked/disliked this forum comment");
				return new ResponseEntity<Like>(like, HttpStatus.CONFLICT);
			}
			like.setMessage("Forum comment does not exist");
			return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
		}
		like.setMessage("You need to login to like/dislike a forum comment");
		return new ResponseEntity<Like>(like, HttpStatus.UNAUTHORIZED);
		
	}
	
	@DeleteMapping("/forum/comment/delete/{like_id}")
	public ResponseEntity<Like> deleteForumCommentLike(@PathVariable int like_id) {
				
		if(likeDAO.delete(like_id)) {
			like = new Like();
			like.setMessage("Like deleted successfully");
			return new ResponseEntity<Like>(like, HttpStatus.OK);
		}
		like.setMessage("Could not delete the like .. please try after some time");
		return new ResponseEntity<Like>(like, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/forum/comment/get/{like_id}")
	public ResponseEntity<Like> getForumCommentLike(@PathVariable int like_id) {
		
		if(likeDAO.get(like_id) != null) {
			return new ResponseEntity<Like>(like, HttpStatus.OK);
		}
		like.setMessage("Like does not exist");
		return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/forum/comment/get/{f_comment_id}")
	public ResponseEntity<Like> getForumCommentLikeByDetails(@PathVariable int f_comment_id) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		if(likeDAO.get('M', f_comment_id, user.getEmail_id()) != null) {
			return new ResponseEntity<Like>(like, HttpStatus.OK);
		}
		like.setMessage("Like does not exist");
		return new ResponseEntity<Like>(like, HttpStatus.NOT_FOUND);
		
	}

}
