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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.stackbook.collaboration.dao.BlogDAO;
import in.stackbook.collaboration.model.Blog;
import in.stackbook.collaboration.model.BlogComment;
import in.stackbook.collaboration.model.User;

@RestController
@RequestMapping("/blog")
public class BlogController {
	
	@Autowired Blog blog;
	
	@Autowired BlogDAO blogDAO;
	
	@Autowired BlogComment blogComment;
	
	@Autowired HttpSession session;
	
	@Autowired User user;
	
	
	@PostMapping("/save")
	public ResponseEntity<Blog> saveBlog(@RequestBody Blog blog) {
		
		if(blogDAO.save(blog)) {
			blog.setMessage("Blog saved successfully");
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		blog.setMessage("Could not save the blog .. please try after some time");
		return new ResponseEntity<Blog>(blog, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@PostMapping("/update")
	public ResponseEntity<Blog> updateBlog(@RequestBody Blog blog) {
		
		if(blogDAO.update(blog)) {
			blog.setMessage("Blog updated successfully");
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		blog.setMessage("Could not save the blog .. please try after some time");
		return new ResponseEntity<Blog>(blog, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/get/{blog_id}")
	public ResponseEntity<Blog> getBlog(@PathVariable int blog_id) {
		
		blog = blogDAO.get(blog_id);
		
		if(blog != null) {
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		blog = new Blog();
		blog.setMessage("Blog does not exist");
		return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/list")		
	public ResponseEntity<List<Blog>> listAllBlogs() {
		
		List<Blog> blogs = blogDAO.list();
		
		if(blogs.isEmpty()) {
			blog = new Blog();
			blog.setMessage("No blogs are available");
			blogs.add(blog);
			return new ResponseEntity<List<Blog>>(blogs, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);

		
		/*List<Integer> blog_id = blogDAO.listID();
		List<String> blog_title = blogDAO.listTitle();
		List<Blog> blogs = new ArrayList<Blog>();
		if(blog_id.isEmpty()) {
			blog = new Blog();
			blog.setMessage("No blogs are available");
			blogs.add(blog);
			return new ResponseEntity<List<Blog>>(blogs, HttpStatus.NOT_FOUND);
		}
		Iterator<Integer> id = blog_id.iterator();
		Iterator<String> title = blog_title.iterator();
		while(id.hasNext() && title.hasNext()) {
			Blog tempBlog = new Blog();
			tempBlog.setBlog_id((Integer)id.next());
			tempBlog.setTitle((String)title.next());
			blogs.add(tempBlog);
		}
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);*/	
	}
	
	@GetMapping("/listByApproval/{approved}")		
	public ResponseEntity<List<Blog>> listByApproval(@PathVariable char approved) {
		
		List<Blog> blogs = blogDAO.list(approved);
		
		if(blogs.isEmpty()) {
			blog = new Blog();
			blog.setMessage("No blogs are available");
			blogs.add(blog);
			return new ResponseEntity<List<Blog>>(blogs, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);

	}
	
	@GetMapping("/listUserBlogs")		
	public ResponseEntity<List<Blog>> listAllUserBlogs() {
		
		user = (User)session.getAttribute("loggedInUser");
		
		List<Blog> blogs = new ArrayList<Blog>();
		
		if(user == null) {
			blog.setMessage("Please login to view your blogs");
			blogs.add(blog);
			return new ResponseEntity<List<Blog>>(blogs, HttpStatus.UNAUTHORIZED);
		}
		
		blogs = blogDAO.list(user.getEmail_id());
		
		if(blogs.isEmpty()) {
			blog = new Blog();
			blog.setMessage("No blogs are available");
			blogs.add(blog);
			return new ResponseEntity<List<Blog>>(blogs, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);

	}
	
		
	@GetMapping("/addView/{blog_id}")
	public ResponseEntity<Blog> addView(@PathVariable int blog_id) {
		
		blog = blogDAO.get(blog_id);
		
		if(blog != null) {
			blog.setViews(blog.getViews()+1);
			if(blogDAO.update(blog)) {
				blog.setMessage("Added view on blog successfully");
				return new ResponseEntity<Blog>(blog, HttpStatus.OK);
			}
			blog.setMessage("Could not add view on the blog");
			return new ResponseEntity<Blog>(blog, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		blog = new Blog();
		blog.setMessage("No blog exists with this blog_id: "+blog_id);
		return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/update/{blog_id}/{approved}")
	public ResponseEntity<Blog> updateBlogApproval(@PathVariable int blog_id, @PathVariable char approved) {
		
		blog = blogDAO.get(blog_id);
		
		if(blog != null) {
			blog.setApproved(approved);
			if(blogDAO.update(blog)) {
				blog.setMessage("Successfully updated the approval of the blog: "+blog_id+"to: "+approved);
				return new ResponseEntity<Blog>(blog, HttpStatus.OK);
			}
			blog.setMessage("Could not update the blog .. please try after some time");
			return new ResponseEntity<Blog>(blog, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		blog = new Blog();
		blog.setMessage("No blog exists with this blog: "+blog_id);
		return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping("/delete/{blog_id}")
	public ResponseEntity<Blog> deleteBlog(@PathVariable int blog_id) {
		
		if(blogDAO.delete(blog_id)) {
			blog.setMessage("Blog deleted successfully");
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		blog.setMessage("Could not delete the blog .. please try after some time");
		return new ResponseEntity<Blog>(blog, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	//  Blog Comments #######################
	
	
	@PostMapping("/comment/save")
	public ResponseEntity<BlogComment> saveBlogComment(@RequestBody BlogComment blogComment) {
		
		if(blogDAO.save(blogComment)) {
			blogComment.setMessage("Blog Comment saved successfully");
			return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
		}
		blogComment.setMessage("Could not save the blog Comment .. please try after some time");
		return new ResponseEntity<BlogComment>(blogComment, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@PostMapping("/comment/update")
	public ResponseEntity<BlogComment> updateBlogComment(@RequestBody BlogComment blogComment) {
		
		if(blogDAO.update(blogComment)) {
			blogComment.setMessage("Blog Comment updated successfully");
			return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
		}
		blog.setMessage("Could not update the blog Comment .. please try after some time");
		return new ResponseEntity<BlogComment>(blogComment, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/comment/delete/{b_comment_id}")
	public ResponseEntity<BlogComment> deleteBlogComment(@PathVariable int b_comment_id) {
		
		if(blogDAO.deleteBlogComment(b_comment_id)) {
			blogComment.setMessage("Blog Comment deleted successfully");
			return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
		}
		blog.setMessage("Could not delete the blog Comment .. please try after some time");
		return new ResponseEntity<BlogComment>(blogComment, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/comment/get/{b_comment_id}")
	public ResponseEntity<Blog> getBlogComment(@PathVariable int b_comment_id) {
		
		blogComment = blogDAO.getBlogComment(b_comment_id);
		
		if(blogComment != null) {
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		blogComment = new BlogComment();
		blogComment.setMessage("Blog Comment does not exist");
		return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/comment/list/on/{blog_id}")		
	public ResponseEntity<List<BlogComment>> listAllCommentsOn(@PathVariable int blog_id) {
		
		List<BlogComment> blogComments = blogDAO.list(blog_id);
		if(blogComments.isEmpty()) {
			blogComment = new BlogComment();
			blogComment.setMessage("No one has commented on this blog yet");
			blogComments.add(blogComment);
			return new ResponseEntity<List<BlogComment>>(blogComments, HttpStatus.OK);
		}
		
		return new ResponseEntity<List<BlogComment>>(blogComments, HttpStatus.OK);
	}
	
	@GetMapping("/comment/listBy/on/{blog_id}")		
	public ResponseEntity<List<BlogComment>> listAllCommentsOnBy(@PathVariable int blog_id) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		List<BlogComment> blogComments = blogDAO.list(blog_id, user.getEmail_id());
		if(blogComments.isEmpty()) {
			blogComment = new BlogComment();
			blogComment.setMessage("No one has commented on this blog yet");
			blogComments.add(blogComment);
			return new ResponseEntity<List<BlogComment>>(blogComments, HttpStatus.OK);
		}
		
		return new ResponseEntity<List<BlogComment>>(blogComments, HttpStatus.OK);
	}
	
	@GetMapping("/comment/listFrom")		
	public ResponseEntity<List<BlogComment>> listAllCommentsFrom() {
		
		user = (User)session.getAttribute("loggedInUser");
		
		List<BlogComment> blogComments = blogDAO.listAllCommentsFrom(user.getEmail_id());
		if(blogComments.isEmpty()) {
			blogComment = new BlogComment();
			blogComment.setMessage("No one has commented on this blog yet");
			blogComments.add(blogComment);
			return new ResponseEntity<List<BlogComment>>(blogComments, HttpStatus.OK);
		}
		
		return new ResponseEntity<List<BlogComment>>(blogComments, HttpStatus.OK);
	}
	

}
