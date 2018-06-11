package in.stackbook.collaboration;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import in.stackbook.collaboration.dao.UserDAO;
import in.stackbook.collaboration.model.User;
import in.stackbook.collaboration.model.UserNotification;

//@ComponentScan(basePackageClasses = "in.stackbook")
public class UserDAOTestCase {
	
	@Autowired private static AnnotationConfigApplicationContext context;
	
	@Autowired private static UserDAO userDAO;
	
	@Autowired private static User user;
	
	@Autowired private static UserNotification userNotification;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("in.stackbook");
		context.refresh();
		
		userDAO = (UserDAO)context.getBean("userDAO");
		user = (User)context.getBean("user");
		userNotification = (UserNotification)context.getBean("userNotification");
	
	}
	
	//User Test Cases
	
	@Test
	public void addUserTestCase() {
		
		/*if((user = (User)userDAO.get("123@test.com"))!=null) {
			
			assertEquals("Add User Test Case", "test", user.getName());
			return;
			
		}*/
		
		user.setEmail_id("1@2.com");
		user.setName("a");
		user.setPassword("1");
		user.setMobile("2");
		
		Assert.assertEquals("Add User Test Case", true, userDAO.save(user));
		
	}
	
	@Test
	public void updateUserTestCase() {
		
		user = userDAO.get("test@test.com");
		
		user.setName("new");
		
		Assert.assertEquals("Update User Test Case", true, userDAO.update(user));		
		
	}
	
	@Test
	public void deleteUserTestCase() {
		
		boolean actual = userDAO.delete("test");
		
		Assert.assertEquals(true, actual);
		
	}
	
	@Test
	public void getUserSuccessTestCase() {
		
		user = userDAO.get("1@2.com");
		
		Assert.assertNotNull("Get User Test Case", user);
		
	}
	
	@Test
	public void getUserFailureTestCase() {
		
		user = userDAO.get("fail@fail.com");
		
		Assert.assertNull("Get User Test Case", user);
		
	}
	
	@Test
	public void getAllUsers() {
		
		int actualSize = userDAO.list().size();
		
		Assert.assertEquals(2, actualSize);
		
	}
	
	
	
	@Test
	public void validateUserTestCase() {
		
		Assert.assertNotNull("Validate TestCase", userDAO.validate("1@2.com", "1"));
		
	}
	
	//UserNotification Test Cases
	
	@Test
	public void addUserNotificationTestCase() {
		
		userNotification.setEmail_id("1@2.com");
		userNotification.setNotification("new");
		
		Assert.assertEquals("Add User Notification Test Case", true, userDAO.save(userNotification));
		
	}
	
	@Test
	public void updateUserNotificationTestCase() {
		
		userNotification = userDAO.get(21);
		
		userNotification.setSeen('Y');
		
		Assert.assertEquals("Update", true, userDAO.update(userNotification));
		
	}
	
	@Test
	public void deleteUserNotificationTestCase() {
				
		Assert.assertEquals("Delete User Notification Test Case", true, userDAO.delete(1));
		
	}
	
	@Test
	public void getUserNotificationTestCase() {
		
		userNotification = userDAO.get(21);
		
		Assert.assertNotNull("Get User Notification Test Case", userNotification);

		
	}
	
	@Test
	public void getAllUserNotifications() {
		
		int actual = userDAO.listAllNotificationsFor("1@2.com").size();
		
		Assert.assertEquals(1, actual);

		
	}
	
	@Test
	public void getAllNotViewedUserNotifications() {
		
		int actual = userDAO.listAllNotSeenFor("1@2.com").size();
		
		Assert.assertEquals(0, actual);
		
	}


	
	
	
	
}
