package in.stackbook.collaboration.dao;

import java.util.List;

import in.stackbook.collaboration.model.Friend;
import in.stackbook.collaboration.model.User;

public interface FriendDAO {
	
	public boolean save(Friend friend);
	
	public boolean update(Friend friend);

	public boolean delete(int friend_id);
	
	public Friend get(int friend_id);
	
	public Friend get(String email_id1, String email_id2);
	
	public List<Friend> listFriends(String email_id);
	
	public List<Friend> listSentFriendRequests(String email_id);
	
	public List<Friend> listReceivedFriendRequests(String email_id);
	
	public List<User> listBlockedUsers(String email_id);
	
	public List<User> listSuggestedUsers(String email_id);
	
	public List<User> listMutualFriends(String email_id1, String email_id2);
		
	//suggested sql (not friends)
	//select * from c_user where email_id not in(select email_id2 from c_friend where email_id1='test') and email_id!='test'

}
