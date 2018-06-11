package in.stackbook.collaboration.daoimpl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.stackbook.collaboration.dao.ForumDAO;
import in.stackbook.collaboration.model.Forum;
import in.stackbook.collaboration.model.ForumComment;

@Repository("forumDAO")
@Transactional
public class ForumDAOImpl implements ForumDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public boolean save(Forum forum) {
		try {			
			if(forum.getNew_post_access()==0)
				forum.setNew_post_access(1);
			if(forum.getView_access()==0)
				forum.setView_access(1);
			if(forum.getComment_access()==0)
				forum.setComment_access(1);
			forum.setScore(0);
			forum.setApproved('P');
			forum.setArchived('N');
			forum.setDate_created(new Timestamp(System.currentTimeMillis()));
			getSession().save(forum);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Forum forum) {
		try {			
			getSession().update(forum);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(int forum_id) {
		try {			
			
			getSession().delete(get(forum_id));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Forum get(int forum_id) {
		return (Forum)getSession().get(Forum.class, forum_id);
	}
	
	public String getTitle(int forum_id) {
		return (String)getSession().createCriteria(Forum.class)
				.add(Restrictions.eq("forum_id", forum_id))
				.uniqueResult();
	}
	
	public List<Forum> list() {
		
		return (List<Forum>)getSession().createQuery("from Forum").list();
		
	}

	public List<Integer> listByLevel(int forum_level) {
		return (List<Integer>)getSession().createCriteria(Forum.class)
				.setProjection(Projections.projectionList()
				.add(Projections.property("forum_id"), "forum_id"))
				.add(Restrictions.eq("this.forum_level", forum_level))
				.list();
	}

	/*public List<Integer> listByViewAccess(int view_access) {
		
		return (List<Integer>)getSession().createNativeQuery("select forum_id from c_forum where "+
				"view_access>="+view_access).list(); 
	}

	public List<Integer> listByPostAccess(int new_post_access) {
		return (List<Integer>)getSession().createNativeQuery("select forum_id from c_forum where "+
				"new_post_access>="+new_post_access).list();
	}*/

	public List<Integer> listPendingApprovalForums() {
		return (List<Integer>)getSession().createCriteria(Forum.class)
				.setProjection(Projections.projectionList()
				.add(Projections.property("forum_id"), "forum_id"))
				.add(Restrictions.eq("this.approved", 'P'))
				.list();
	}
	
	//Forum Comments #####################################################

	public boolean save(ForumComment forumComment) {
		try {			
			forumComment.setScore(0);
			forumComment.setComment_date(new Timestamp(System.currentTimeMillis()));
			getSession().save(forumComment);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(ForumComment forumComment) {
		try {			
			getSession().update(forumComment);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteForumComment(int f_comment_id) {
		try {			
			getSession().delete(get(f_comment_id));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public ForumComment getForumComment(int f_comment_id) {
		return (ForumComment)getSession().get(ForumComment.class, f_comment_id);
	}

	public List<ForumComment> list(int forum_id) {
		return (List<ForumComment>)getSession().createCriteria(ForumComment.class)
				.add(Restrictions.eq("forum_id", forum_id))
				.list();
	}

}
