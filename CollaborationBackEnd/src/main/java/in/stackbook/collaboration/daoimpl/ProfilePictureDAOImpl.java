package in.stackbook.collaboration.daoimpl;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.stackbook.collaboration.dao.ProfilePictureDAO;
import in.stackbook.collaboration.model.ProfilePicture;

@Repository("profilePictureDAO")
@Transactional
public class ProfilePictureDAOImpl implements ProfilePictureDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public boolean save(ProfilePicture profilePicture) {
		try {
			getSession().save(profilePicture);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	

	public ProfilePicture get(String email_id) {
		return (ProfilePicture)getSession().get(ProfilePicture.class, email_id);
	}

	public boolean update(ProfilePicture profilePicture) {
		try {
			getSession().update(profilePicture);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(String email_id) {
		try {
			getSession().delete(get(email_id));
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
