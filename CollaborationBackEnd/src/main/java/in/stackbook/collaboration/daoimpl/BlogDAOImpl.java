package in.stackbook.collaboration.daoimpl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.stackbook.collaboration.dao.BlogDAO;
import in.stackbook.collaboration.model.Blog;
import in.stackbook.collaboration.model.BlogComment;

@Repository("blogDAO")
@Transactional
public class BlogDAOImpl implements BlogDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public boolean save(Blog blog) {
		try {			
			blog.setViews(0);
			blog.setApproved('P');
			blog.setDate_created(new Timestamp(System.currentTimeMillis()));
			getSession().save(blog);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Blog blog) {
		try {			
			getSession().update(blog);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(int blog_id) {
		try {			
			getSession().delete(get(blog_id));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Blog get(int blog_id) {
		return (Blog)getSession().get(Blog.class, blog_id);
	}
	
	/*public String getBlogTitle(int blog_id) {
		return (String)getSession().createCriteria(Blog.class)
				.setProjection(Projections.projectionList()
				.add(Projections.property("title"), "title"))
				.add(Restrictions.eq("blog_id", blog_id))
				.uniqueResult();
	}*/
	
	public List<Blog> list() {
		
		return (List<Blog>)getSession().createQuery("from Blog").list();
		
	}
	
	public List<Blog> list(char approved) {
		return (List<Blog>)getSession().createCriteria(Blog.class)
				.add(Restrictions.eq("approved", approved))
				.list();
	}
	
	public List<Blog> list(String email_id) {
		return (List<Blog>)getSession().createCriteria(Blog.class)
				.add(Restrictions.eq("email_id", email_id))
				.list();
	}

	/*public List<Integer> listID() {
		return (List<Integer>)getSession().createCriteria(Blog.class)
				.setProjection(Projections.projectionList()
				.add(Projections.property("blog_id"), "blog_id"))
				.list();
	}*/
	
	/*public List<String> listTitle() {
		return (List<String>)getSession().createCriteria(Blog.class)
				.setProjection(Projections.projectionList()
				.add(Projections.property("title"), "title"))
				.list();
	}*/

	
	//Blog Comments ###################################################

	public boolean save(BlogComment blogComment) {
		try {			
			blogComment.setComment_date(new Timestamp(System.currentTimeMillis()));
			getSession().save(blogComment);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(BlogComment blogComment) {
		try {			
			getSession().update(blogComment);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteBlogComment(int b_comment_id) {
		try {			
			getSession().delete(getBlogComment(b_comment_id));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public BlogComment getBlogComment(int b_comment_id) {
		return (BlogComment)getSession().get(BlogComment.class, b_comment_id);
	}

	public List<BlogComment> list(int blog_id) {
		return (List<BlogComment>)getSession().createCriteria(BlogComment.class)
				.add(Restrictions.eq("blog_id",blog_id))
				.list();
	}
	
	public List<BlogComment> list(int blog_id, String email_id) {
		return (List<BlogComment>)getSession().createCriteria(BlogComment.class)
				.add(Restrictions.eq("blog_id", blog_id))
				.add(Restrictions.eq("email_id", email_id))
				.list();
	}
	
	public List<BlogComment> listAllCommentsFrom(String email_id) {
		return (List<BlogComment>)getSession().createCriteria(BlogComment.class)
				.add(Restrictions.eq("email_id", email_id))
				.list();
	}
	

}
