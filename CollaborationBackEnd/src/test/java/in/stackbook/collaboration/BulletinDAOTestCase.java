package in.stackbook.collaboration;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import in.stackbook.collaboration.dao.BulletinDAO;
import in.stackbook.collaboration.model.Bulletin;

public class BulletinDAOTestCase {
	
	private static AnnotationConfigApplicationContext context;
	
	@Autowired private static BulletinDAO bulletinDAO;
	
	@Autowired private static Bulletin bulletin;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("in.stackbook");
		context.refresh();
		
		bulletinDAO = (BulletinDAO)context.getBean("bulletinDAO");
		bulletin = (Bulletin)context.getBean("bulletin");
	
	}
	
	@Test
	public void saveBulletinTestCase() {
		
		bulletin.setTitle("bulletin title");
		bulletin.setDescription("bulletin desc");
		
		Assert.assertEquals("Save Bulletin Test Case", true, bulletinDAO.save(bulletin));
		
	}
	
	@Test
	public void updateBulletinTestCase() {
		
		bulletin = bulletinDAO.get(1);
		
		bulletin.setTitle("new bulletin title");
		
		Assert.assertEquals("Update Bulletin Test Case", true, bulletinDAO.update(bulletin));
		
	}
	
	@Test
	public void deleteBulletinTestCase() {
		
		Assert.assertEquals("Delete Bulletin Test Case", true, bulletinDAO.delete(1));
		
	}
	
	@Test
	public void getBulletinTestCase() {
		
		Assert.assertNotNull("Get Bulletin Test Case", bulletinDAO.get(1));
		
	}
	
	@Test
	public void getAllBulletinID() {
		
		int actual = bulletinDAO.listAllID().size();
		
		Assert.assertEquals(1, actual);
		
	}
	
	@Test
	public void getAllBulletinTitle() {
		
		int actual = bulletinDAO.listAllTitle().size();
		
		Assert.assertEquals(1, actual);
		
	}
	
	

}
