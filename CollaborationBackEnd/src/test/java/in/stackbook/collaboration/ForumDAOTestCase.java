package in.stackbook.collaboration;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import in.stackbook.collaboration.dao.ForumDAO;
import in.stackbook.collaboration.model.Forum;
import in.stackbook.collaboration.model.ForumComment;

public class ForumDAOTestCase {
	
	@Autowired private static AnnotationConfigApplicationContext context;
	
	@Autowired private static ForumDAO forumDAO;
	
	@Autowired private static Forum forum;
	
	@Autowired private static ForumComment forumComment;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("in.stackbook");
		context.refresh();
		
		forumDAO = (ForumDAO)context.getBean("forumDAO");
		forum = (Forum)context.getBean("forum");
		forumComment = (ForumComment)context.getBean("forumComment");
	
	}
	
	@Test
	public void saveForumTestCase() {
		
		forum.setEmail_id("a@b.com");
		forum.setContent("forum content");
		forum.setForum_level(0);
		forum.setTitle("Home");
		
		Assert.assertEquals("Save Forum Test Case", true, forumDAO.save(forum));
		
	}
	
	@Test
	public void updateForumTestCase() {
		
		forum = forumDAO.get(4);
		
		forum.setNew_post_access(1);
		forum.setView_access(1);
		forum.setComment_access(1);
		
		Assert.assertEquals("Update Forum Test Case", true, forumDAO.update(forum));
		
	}
	
	@Test
	public void deleteForumTestCase() {
		
		Assert.assertEquals("Delete Forum Test Case", true, forumDAO.delete(1));
		
	}
	
	@Test
	public void getForumTestCase() {
		
		Assert.assertNotNull("Get Forum Test Case", forumDAO.get(4));
		
	}
	
	@Test
	public void getAllForumsAtLevelTestCase() {
		
		int actual = forumDAO.listByLevel(0).size();
		
		Assert.assertEquals("Get all Forums at level Test Case", 1, actual);
		
	}
	
	/*@Test
	public void getAllForumsByViewAccessTestCase() {
		
		int actual = forumDAO.listByViewAccess(1).size();
		
		Assert.assertEquals("Get all Forums by view access Test Case", 1, actual);
		
	}
	
	@Test
	public void getAllForumsByPostAccessTestCase() {
		
		int actual = forumDAO.listByPostAccess(1).size();
		
		Assert.assertEquals("Get all Forums by post access Test Case", 1, actual);
		
	}*/
	
	@Test
	public void getAllForumsPendingApprovalTestCase() {
		
		int actual = forumDAO.listPendingApprovalForums().size();
		
		Assert.assertEquals("Get all Forums pending approval Test Case", 1, actual);
		
	}
	
	//Forum Comment Test Case ############################
	
	@Test
	public void saveForumCommentTestCase() {
		
		forumComment.setForum_id(4);
		forumComment.setForum_comment("Forum comment");
		forumComment.setEmail_id("1@2.com");
		
		Assert.assertEquals("Save Forum Comment Test Case", true, forumDAO.save(forumComment));
		
	}
	
	@Test
	public void updateForumCommentTestCase() {
		
		forumComment = forumDAO.getForumComment(2);
		
		forumComment.setForum_comment("new comment");
		
		Assert.assertEquals("Update Forum Comment Test Case", true, forumDAO.update(forumComment));
		
	}
	
	@Test
	public void deleteForumCommentTestCase() {
		
		Assert.assertEquals("Delete Forum Comment Test Case", true, forumDAO.delete(1));
		
	}
	
	@Test
	public void getForumCommentTestCase() {
		
		Assert.assertNotNull("Get Forum Comment Test Case", forumDAO.getForumComment(2));
		
	}
	
	@Test
	public void listAllCommentsOnForumTestCase() {
		
		int actual = forumDAO.list(4).size();
		
		Assert.assertEquals("List Forum Comments Test Case", 1, actual);
		
	}

}
