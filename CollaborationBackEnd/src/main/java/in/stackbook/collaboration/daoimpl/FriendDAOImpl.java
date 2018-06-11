package in.stackbook.collaboration.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.stackbook.collaboration.dao.FriendDAO;
import in.stackbook.collaboration.model.Friend;
import in.stackbook.collaboration.model.User;

@Repository("friendDAO")
@Transactional
public class FriendDAOImpl implements FriendDAO {
	
	//TODO use persist for chat stuff because 
	/*
	 * Fourth difference between save and persist method in Hibernate:
	 * persist method is called outside of transaction boundaries,
	 * it is useful in long-running conversations with an extended Session context.
	 * On the other hand save method is not good in a long-running conversation with an extended Session context.
	 */
	
	@Autowired private SessionFactory sessionFactory;
		
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public boolean save(Friend friend) {
		try {
			friend.setFriends(2);
			getSession().save(friend);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public boolean update(Friend friend) {
		try {
			getSession().update(friend);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(int friend_id) {
		try {
			getSession().delete(get(friend_id));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Friend get(int friend_id) {
		return (Friend)getSession().get(Friend.class, friend_id);
	}

	public List<Friend> listFriends(String email_id) {
				
		String hql = "from Friend where (email_id1 = :email_id and friends = 1)"
				+ " or (email_id2 = :email_id and friends = 1)";
		Query query = getSession().createQuery(hql);
		query.setParameter("email_id", email_id);
		
		return (List<Friend>)query.list();
		
		/*return (List<Friend>)getSession().createNativeQuery("select * from c_friend where " + 
				"(email_id1='"+email_id+"' and friends=1) or " + 
				"(email_id2='"+email_id+"' and friends=1)").list();*/
		
	}
	
	public List<Friend> listSentFriendRequests(String email_id) {
		
		return (List<Friend>)getSession().createCriteria(Friend.class)
				.add(Restrictions.eq("email_id1", email_id))
				.add(Restrictions.eq("friends", 2))
				.list();
		
	}

	public List<Friend> listReceivedFriendRequests(String email_id) {
		
		return (List<Friend>)getSession().createCriteria(Friend.class)
				.add(Restrictions.eq("email_id2", email_id))
				.add(Restrictions.eq("friends", 2))
				.list();
		
	}

	public List<User> listBlockedUsers(String email_id) {
				
		String hql = "from User where :email_id in ((select email_id1 as email_id from friend where"
				+ " (email_id2 = :email_id and (friends = 4 or friends = 6)))"
				+ " union (select email_id2 as email_id from friend where"
				+ " (email_id1 = :email_id and (friends = 5 or friends = 6))))";
		
		Query query = getSession().createQuery(hql);
		
		query.setParameter("email_id", email_id);
		
		return (List<User>)query.list();
	
	}
	
	/*select * from c_user where email_id not in (select email_id1 as email_id from c_friend where email_id2 = '1@2.com'
	union select email_id2 as email_id from c_friend where email_id1 = '1@2.com') and email_id!='1@2.com'*/
	public List<User> listSuggestedUsers(String email_id) {
		
		String hql = "from User where :email_id not in (select email_id1 as email_id from Friend where email_id2 = :email_id"
				+ " union select email_id2 as email_id from Friend where email_id1 = :email_id) and email_id != :email_id";
		Query query = getSession().createQuery(hql);
		
		query.setParameter("email_id", email_id);
		
		return (List<User>)query.list();
	}
	
	public List<User> listMutualFriends(String email_id1, String email_id2) {
		
		String hql = "from User where email_id in"
				+ " (((select email_id1 as email_id from c_friend where email_id2 = :email_id1 and friends = 1) union"
				+ " (select email_id2 as email_id from c_friend where email_id1 = :email_id1 and friends = 1)) intersect"
				+ " ((select email_id1 as email_id from c_friend where email_id2 = :email_id2 and friends = 1) union"
				+ " (select email_id2 as email_id from c_friend where email_id1 = :email_id2 and friends = 1)))";
		Query query = getSession().createQuery(hql);
		
		query.setParameter("email_id1", email_id1);
		query.setParameter("email_id2", email_id2);
		
		return (List<User>)query.list();
		
	}

}
