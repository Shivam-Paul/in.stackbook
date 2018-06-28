package in.stackbook.collaboration.daoimpl;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.stackbook.collaboration.dao.UserDAO;
import in.stackbook.collaboration.model.User;
import in.stackbook.collaboration.model.UserNotification;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {
	
	@Autowired private SessionFactory sessionFactory;
	
	//@Autowired private UserDAO userDAO;
	
	@Autowired private User user;
	
	@Autowired private UserNotification userNotification;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public boolean save(User user) {
		
		try {
			if(user.getRole()==0) {
				user.setRole(1);
			}
			if(user.getActive()==null) {
				user.setActive('Y');
			}
			user.setRegistered_date(new Timestamp(System.currentTimeMillis()));
			
			getSession().save(user);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(User user) {
		try {
			getSession().update(user);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public int updateSetAllAsSeen(String email_id) {
		
		String hql = "update UserNotification set seen=:seen where email_id=:email_id";
		Query query = getSession().createQuery(hql);
		query.setParameter("seen", 'Y');
		query.setParameter("email_id", email_id);
		
		return query.executeUpdate();
		
	}

	public boolean delete(String email_id) {
		try {
			user = get(email_id);
			if(user==null) {
				return false;
			}
			getSession().delete(user);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	public User get(String email_id) {
		return (User)getSession().get(User.class, email_id);
	}

	public List<User> list() {
		return (List<User>)getSession().createQuery("from User").list();
	}
	
	public List<User> listByRole(int role) {
		return (List<User>)getSession().createCriteria(User.class)
				.add(Restrictions.eq("role", role))
				.list();
	}

	public User validate(String email_id, String password) {
		return (User)getSession().createCriteria(User.class)
				.add(Restrictions.eq("email_id",email_id))
				.add(Restrictions.eq("password", password))
				.uniqueResult();
	}
	
	//User Notification

	public boolean save(UserNotification userNotification) {
		try {
			userNotification.setSeen('N');
			userNotification.setReceived_date(new Timestamp(System.currentTimeMillis()));
			getSession().save(userNotification);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sendNotificationToAllUsers(UserNotification userNotification) {
		try {
			userNotification.setSeen('N');
			userNotification.setReceived_date(new Timestamp(System.currentTimeMillis()));
			List<User> users = list();
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			int i = 0;
			for(User user : users) {
				userNotification.setEmail_id(user.getEmail_id());
				session.save(userNotification);
				if(i % 20 == 0) {
					session.flush();
					session.clear();
				}
				i++;
			}
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sendNotificationToUsersBasedOnRole(UserNotification userNotification, int role) {
		try {
			userNotification.setSeen('N');
			userNotification.setReceived_date(new Timestamp(System.currentTimeMillis()));
			List<User> users = list();
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			int i = 0;
			int tempRole = 0;
			for(User user : users) {
				tempRole = user.getRole();
				if(role != tempRole) {
					continue;
				}
				userNotification.setEmail_id(user.getEmail_id());
				session.save(userNotification);
				if(i % 20 == 0) {
					session.flush();
					session.clear();
				}
				i++;
			}
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sendNotificationToUsersBasedOnMinMaxRole(UserNotification userNotification, int min, int max) {
		boolean b = false;
		for(int i = min ; i < max ; i++) {
			b = sendNotificationToUsersBasedOnRole(userNotification, i);
		}
		return b;
	}

	public boolean update(UserNotification userNotification) {
		try {
			getSession().update(userNotification);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean delete(int notification_id) {
		try {
			userNotification = get(notification_id);
			if(userNotification==null) {
				return false;
			}
			getSession().delete(userNotification);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	public UserNotification get(int notification_id) {
		
		return (UserNotification)getSession().get(UserNotification.class, notification_id);

	}

	public List<UserNotification> listAllNotificationsFor(String email_id) {
		
		return (List<UserNotification>)getSession().createCriteria(UserNotification.class)
				.add(Restrictions.eq("email_id", email_id))
				.list();

		
	}

	public List<UserNotification> listAllNotSeenFor(String email_id) {

		return (List<UserNotification>)getSession().createCriteria(UserNotification.class)
				.add(Restrictions.eq("email_id", email_id))
				.add(Restrictions.eq("seen", 'N'))
				.list();
		
	}

}
