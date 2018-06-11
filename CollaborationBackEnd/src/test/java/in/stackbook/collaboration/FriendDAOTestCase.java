package in.stackbook.collaboration;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import in.stackbook.collaboration.dao.FriendDAO;
import in.stackbook.collaboration.model.Friend;

public class FriendDAOTestCase {
	
private static AnnotationConfigApplicationContext context;
	
	@Autowired private static Friend friend;
	
	@Autowired private static FriendDAO friendDAO;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("in.stackbook");
		context.refresh();
		
		friend = (Friend)context.getBean("friend");
		friendDAO = (FriendDAO)context.getBean("friendDAO");
	
	}
	
	@Test
	public void saveFriendTestCase() {
		
		friend.setEmail_id1("1@2.com");
		friend.setEmail_id2("test");
		
		Assert.assertEquals("Save Friend Test Case", true, friendDAO.save(friend));
		
	}
	
	@Test
	public void updateFriendTestCase() {
		
		friend = friendDAO.get(43);
		
		friend.setFriends(1);
		
		Assert.assertEquals("Update Friend Test Case", true, friendDAO.update(friend));
		
	}
	
	@Test
	public void deleteFriendTestCase() {
		
		boolean actual = friendDAO.delete(1);
		
		Assert.assertEquals(true, actual);
		
	}
	
	@Test
	public void getFriendTestCase() {
		
		friend = friendDAO.get(21);
		
		Assert.assertNotNull("Get Friend Test Case", friend);
		
	}
	
	@Test
	public void getAllUserFriendsTestCase() {
		
		int actual = friendDAO.listFriends("1@2.com").size();
		
		Assert.assertEquals(0, actual);

		
	}
	
	@Test
	public void getAllSentFriendRequestsTestCase() {
		
		int actual = friendDAO.listSentFriendRequests("a@b.com").size();
		
		Assert.assertEquals(1, actual);

		
	}
	
	@Test
	public void getAllReceivedFriendRequestsTestCase() {
		
		int actual = friendDAO.listReceivedFriendRequests("1@2.com").size();
		
		Assert.assertEquals(1, actual);

		
	}
	
	@Test
	public void getAllBlockedUsersTestCase() {
		
		int actual = friendDAO.listBlockedUsers("1@2.com").size();
		
		Assert.assertEquals(0, actual);

		
	}
	

}
