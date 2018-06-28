package in.stackbook.collaboration.dao;

import java.util.List;

import in.stackbook.collaboration.model.User;
import in.stackbook.collaboration.model.UserNotification;

public interface UserDAO {
	
	public boolean save(User user);
		
	public boolean update(User user);

	public boolean delete(String email_id);

	public User get(String email_id);
	
	public List<User> list();
	
	public List<User> listByRole(int role);
	
	public User validate(String email_id, String password);
	
	
	//UserNotification
	
	
	public boolean save(UserNotification userNotification);
	
	public boolean sendNotificationToAllUsers(UserNotification userNotification);
	
	public boolean sendNotificationToUsersBasedOnRole(UserNotification userNotification, int role);
	
	public boolean sendNotificationToUsersBasedOnMinMaxRole(UserNotification userNotification, int min, int max);
	
	public boolean update(UserNotification userNotification);
	
	public int updateSetAllAsSeen(String email_id);
	
	public boolean delete(int notification_id);
	
	public UserNotification get(int notification_id);
	
	public List<UserNotification> listAllNotificationsFor(String email_id);
	
	public List<UserNotification> listAllNotSeenFor(String email_id);
	
	//public List<UserNotification> listAllAfterDate(String emailID, Timestamp timestamp);
	
	
}
