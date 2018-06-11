package in.stackbook.collaboration.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.stackbook.collaboration.dao.LikeDAO;
import in.stackbook.collaboration.model.Like;

@Repository("likeDAO")
@Transactional
public class LikeDAOImpl implements LikeDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public boolean save(Like like) {
		try {		
			getSession().save(like);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Like like) {
		try {		
			getSession().update(like);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(int like_id) {
		try {		
			getSession().delete(get(like_id));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Like get(int like_id) {
		return (Like)getSession().get(Like.class, like_id);
	}
	
	public Like get(char reference_table, int table_id, String email_id) {
		return (Like)getSession().createCriteria(Like.class)
				.add(Restrictions.eq("reference_table", reference_table))
				.add(Restrictions.eq("table_id", table_id))
				.add(Restrictions.eq("email_id", email_id))
				.uniqueResult();
	}

	public List<Like> listLikesForTableID(char reference_table, int table_id) {
		return (List<Like>)getSession().createCriteria(Like.class)
				.add(Restrictions.eq("table_id", table_id))
				.add(Restrictions.eq("reference_table", reference_table))
				.add(Restrictions.eq("liked", 1))
				.list();
	}
	
	public List<Like> listDislikesForTableID(char reference_table, int table_id) {
		return (List<Like>)getSession().createCriteria(Like.class)
				.add(Restrictions.eq("table_id", table_id))
				.add(Restrictions.eq("reference_table", reference_table))
				.add(Restrictions.eq("liked", -1))
				.list();
	}

}
