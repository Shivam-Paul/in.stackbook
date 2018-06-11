package in.stackbook.collaboration;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import in.stackbook.collaboration.dao.BlogDAO;
import in.stackbook.collaboration.model.Blog;
import in.stackbook.collaboration.model.BlogComment;

public class BlogDAOTestCase {
	
	private static AnnotationConfigApplicationContext context;
	
	@Autowired private static BlogDAO blogDAO;
	
	@Autowired private static Blog blog;
	
	@Autowired private static BlogComment blogComment;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("in.stackbook");
		context.refresh();
		
		blogDAO = (BlogDAO)context.getBean("blogDAO");
		blog = (Blog)context.getBean("blog");
		blogComment = (BlogComment)context.getBean("blogComment");
	
	}
	
	@Test
	public void saveBlogTestCase() {
		
		blog.setTitle("blog 1");
		blog.setContent("this is the first blog");
		blog.setEmail_id("1@2.com");
		
		Assert.assertEquals("Save Blog Test Case", true, blogDAO.save(blog));
		
	}
	
	@Test
	public void updateBlogTestCase() {
		
		blog = blogDAO.get(1);
		
		blog.setApproved('Y');
		
		Assert.assertEquals("Update Blog Test Case", true, blogDAO.update(blog));
		
	}
	
	@Test
	public void deleteBlogTestCase() {
				
		Assert.assertEquals("Delete Blog Test Case", true, blogDAO.delete(1));
		
	}
	
	@Test
	public void getBlogTestCase() {
		
		Assert.assertNotNull("Get Blog Test Case", blogDAO.get(1));
		
	}
	
	@Test
	public void getAllBlogsTestCase() {
		
		int actual = blogDAO.list().size();
		
		Assert.assertEquals(1, actual);
		
	}
	
	@Test 
	public void getAllBlogsByTestCase() {
		
		int actual = blogDAO.listAllFrom("1@2.com").size();
		
		Assert.assertEquals("Get all Blogs From Test Case", 1, actual);
		
	}
	
	//Blog Comments Test Case ###################
	
	@Test
	public void saveBlogCommentTestCase() {
		
		blogComment.setBlog_id(1);
		blogComment.setBlog_comment("blog comment");
		blogComment.setEmail_id("a@b.com");
		
		Assert.assertEquals("Save Blog Comment Test Case", true, blogDAO.save(blogComment));
		
	}
	
	@Test
	public void updateBlogCommentTestCase() {
		
		blogComment = blogDAO.getBlogComment(2);
		
		blogComment.setBlog_comment("new comment");
		
		Assert.assertEquals("Update Blog Comment Test Case", true, blogDAO.update(blogComment));
		
	}
	
	@Test
	public void deleteBlogCommentTestCase() {
				
		Assert.assertEquals("Delete Blog Comment Test Case", true, blogDAO.deleteBlogComment(1));
		
	}
	
	@Test
	public void getBlogCommentTestCase() {
		
		Assert.assertNotNull("Get Blog Comment Test Case", blogDAO.getBlogComment(2));
		
	}
	
	@Test
	public void getAllBlogCommentsOnBlog() {
		
		int actual = blogDAO.list(1).size();
		
		Assert.assertEquals(1, actual);
		
	}
	
	@Test 
	public void getAllBlogCommentsFrom() {
		
		int actual = blogDAO.listAllFrom("1@2.com").size();
		
		Assert.assertEquals("Get all Blog Comments From Test Case", 1, actual);
		
	}
	
	
	

}
