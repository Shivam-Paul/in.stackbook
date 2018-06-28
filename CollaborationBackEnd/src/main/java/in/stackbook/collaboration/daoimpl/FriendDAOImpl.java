package in.stackbook.collaboration.daoimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.SynchronizationRegistryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.stackbook.collaboration.dao.FriendDAO;
import in.stackbook.collaboration.model.Friend;
import in.stackbook.collaboration.model.User;

@Repository("friendDAO")
@Transactional
public class FriendDAOImpl implements FriendDAO {
	
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
			if(friend.getFriends() == 0) {
				friend.setFriends(2);
			}
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
	
	public Friend get(String email_id1, String email_id2) {
		String hql = "from Friend where (email_id1 = :email_id1 and email_id2 = :email_id2 and friends = 1)"
				+ " or (email_id1 = :email_id2 and email_id2 = :email_id1 and friends = 1)";
		Query query = getSession().createQuery(hql);
		query.setParameter("email_id1", email_id1);
		query.setParameter("email_id2", email_id2);
		
		return (Friend)query.uniqueResult();
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
		
		String first = "from User where email_id in (select email_id1 as email_id from Friend where"
				+ " (email_id2 = :email_id and (friends = 4 or friends = 6)))";
		String second = "from User where email_id in (select email_id2 as email_id from Friend where"
				+ " (email_id1 = :email_id and (friends = 5 or friends = 6)))";
		Query list1 = getSession().createQuery(first);
		Query list2 = getSession().createQuery(second);
				
		list1.setParameter("email_id", email_id);
		list2.setParameter("email_id", email_id);
		
		List<User> temp1 = (List<User>)list1.list();
		List<User> temp2 = (List<User>)list2.list();
		
		return combineList(temp1, temp2);
		
	}
	
	/*select * from c_user where email_id not in (select email_id1 as email_id from c_friend where email_id2 = '1@2.com'
	union select email_id2 as email_id from c_friend where email_id1 = '1@2.com') and email_id!='1@2.com'*/
	public List<User> listSuggestedUsers(String email_id) {
		
		String first = "from User where email_id not in"
				+ " (select email_id1 as email_id from Friend where email_id2 = :email_id) and email_id != :email_id";
		String second = "from User where email_id not in"
				+ " (select email_id2 as email_id from Friend where email_id1 = :email_id) and email_id != :email_id";
		
		Query list1 = getSession().createQuery(first);
		Query list2 = getSession().createQuery(second);
		
		list1.setParameter("email_id", email_id);
		list2.setParameter("email_id", email_id);
		
		List<User> temp1 = (List<User>)list1.list();
		List<User> temp2 = (List<User>)list2.list();
		
		return combineList(temp1, temp2);
						
	}
	
	private synchronized List<User> combineList(List<User> temp1, List<User> temp2) {
		
		List<User> result = new ArrayList<User>();
		
		for(User tempUser : temp2) {
			if(temp1.contains(tempUser)) {
				result.add(tempUser);
			}
		}
		for(User tempUser : temp1) {
			if(temp2.contains(tempUser) && !result.contains(tempUser)) {
				result.add(tempUser);
			}
		}
		for(User temp : result) {
			System.out.println("result" + temp.getEmail_id());
		}
		return result;
	}
	
	/*select * from c_user where email_id in
	(((select email_id1 as email_id from c_friend where email_id1 = 'abc@gmail.com' and friends = 1) 
	union (select email_id2 as email_id from c_friend where email_id2 = 'abc@gmail.com' and friends = 1)) 
	intersect ((select email_id1 as email_id from c_friend where email_id1 = 'temp@gmail.com' and friends = 1) 
	union (select email_id2 as email_id from c_friend where email_id2 = 'temp@gmail.com' and friends = 1)))*/
	
	public List<User> listMutualFriends(String email_id1, String email_id2) {
		
		String first = "from User where email_id in (select email_id2 as email_id from Friend where"
				+ " (email_id1 = :email_id1 and friends = 1))";
		String second = "from User where email_id in (select email_id1 as email_id from Friend where"
				+ " (email_id2 = :email_id1 and friends = 1))";
		String third = "from User where email_id in (select email_id2 as email_id from Friend where"
				+ " (email_id1 = :email_id2 and friends = 1))";
		String fourth = "from User where email_id in (select email_id1 as email_id from Friend where"
				+ " (email_id2 = :email_id2 and friends = 1))";
		
		Query list1 = getSession().createQuery(first);
		Query list2 = getSession().createQuery(second);
		Query list3 = getSession().createQuery(third);
		Query list4 = getSession().createQuery(fourth);
		
		list1.setParameter("email_id1", email_id1);
		list2.setParameter("email_id1", email_id1);
		list3.setParameter("email_id2", email_id2);
		list4.setParameter("email_id2", email_id2);
		
		List<User> temp1 = (List<User>)list1.list();
		List<User> temp2 = (List<User>)list2.list();
		List<User> temp3 = (List<User>)list3.list();
		List<User> temp4 = (List<User>)list4.list();
		for(User temp : temp1) {
			System.out.println("temp1" + temp.getEmail_id());
		}
		for(User temp : temp2) {
			System.out.println("temp2" + temp.getEmail_id());
		}
		for(User temp : temp3) {
			System.out.println("temp3" + temp.getEmail_id());
		}
		for(User temp : temp4) {
			System.out.println("temp4" + temp.getEmail_id());
		}
		
		List<User> combined1 = mutualList(temp1, temp2);
		List<User> combined2 = mutualList(temp3, temp4);
		
		for(User temp : combined1) {
			System.out.println("combined1" + temp.getEmail_id());
		}
		for(User temp : combined2) {
			System.out.println("combined2" + temp.getEmail_id());
		}
		
		return combineList(combined1, combined2);
		
	}
	
	private synchronized List<User>  mutualList(List<User> list1, List<User> list2) {
		
		List<User> result = new ArrayList<User>();
		
		for(User tempUser : list1) {
			if(!list2.contains(tempUser)) {
				result.add(tempUser);
			}
		}
		for(User tempUser : list2) {
			if(!list1.contains(tempUser) && !result.contains(tempUser)) {
				result.add(tempUser);
			}
		}
		
		return result;
		
	}

}
