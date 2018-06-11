package in.stackbook.collaboration;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import in.stackbook.collaboration.dao.LikeDAO;
import in.stackbook.collaboration.model.Like;

public class LikeDAOTestCase {
	
	private static AnnotationConfigApplicationContext context;
	
	@Autowired private static LikeDAO likeDAO;
	
	@Autowired private static Like like;
		
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("in.stackbook");
		context.refresh();
		
		likeDAO = (LikeDAO)context.getBean("likeDAO");
		like = (Like)context.getBean("like");
	
	}
	
	@Test
	public void saveLikeTestCase() {
		
		like.setReference_table('G');
		like.setTable_id(2);
		like.setEmail_id("1@2.com");
		like.setLiked(1);
		
		Assert.assertEquals("Save Like Test Case", true, likeDAO.save(like));
		
	}
	
	@Test
	public void updateLikeTestCase() {
		
		like = likeDAO.get(2);
		
		like.setLiked(0);
		
		Assert.assertEquals("Update Like Test Case", true, likeDAO.update(like));
		
	}
	
	@Test
	public void deleteLikeTestCase() {
		
		Assert.assertEquals("Delete Like Test Case", true, likeDAO.delete(1));
		
	}
	
	@Test
	public void getLikeTestCase() {
		
		Assert.assertNotNull("Get Like Test Case", likeDAO.get(2));
		
	}
	
	@Test
	public void getAllLikesTestCase() {
		
		int actual = likeDAO.listLikesForTableID('G', 2).size();
		
		Assert.assertEquals(1, actual);
		
	}

}
