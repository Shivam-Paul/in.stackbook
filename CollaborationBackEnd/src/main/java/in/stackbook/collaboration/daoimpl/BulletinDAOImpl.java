package in.stackbook.collaboration.daoimpl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.stackbook.collaboration.dao.BulletinDAO;
import in.stackbook.collaboration.model.Bulletin;

@Repository("bulletinDAO")
@Transactional
public class BulletinDAOImpl implements BulletinDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public boolean save(Bulletin bulletin) {
		try {			
			if(bulletin.getEvent_date()==null)
				bulletin.setEvent_date(new Timestamp(System.currentTimeMillis()));
			getSession().save(bulletin);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Bulletin bulletin) {
		try {			
			getSession().update(bulletin);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(int bulletin_id) {
		try {			
			getSession().delete(get(bulletin_id));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Bulletin get(int bulletin_id) {
		return (Bulletin)getSession().get(Bulletin.class, bulletin_id);
	}
	
	public List<Bulletin> list() {
		
		return (List<Bulletin>)getSession().createQuery("from Bulletin").list();
		
	}

	/*public List<Integer> listAllID() {
		return (List<Integer>)getSession().createCriteria(Bulletin.class)
		.setProjection(Projections.projectionList()
		.add(Projections.property("bulletin_id"), "bulletin_id"))
		.list();
	}*/
	
	/*public List<String> listAllTitle() {
		return (List<String>)getSession().createCriteria(Bulletin.class)
		.setProjection(Projections.projectionList()
		.add(Projections.property("title"), "title"))
		.list();
	}*/


}
