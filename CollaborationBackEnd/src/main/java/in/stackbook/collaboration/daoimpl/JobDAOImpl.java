package in.stackbook.collaboration.daoimpl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.stackbook.collaboration.dao.JobDAO;
import in.stackbook.collaboration.model.Job;
import in.stackbook.collaboration.model.JobApplication;


@Repository("jobDAO")
@Transactional
public class JobDAOImpl implements JobDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/* private int getMaxValue() {
	 *  int maxValue = 100;
	 *  
	 * try{ 
	 * 
	 * maxValue = (Integer)getSession().createQuery("max(id) from job").uniqueResult();
	 * return maxValue;
	 * }
	 * catch(Exception e) {
	 * e.printStackTrace();
	 * return 100;
	 * }
	 * 
	 * 
	 * */

	public boolean save(Job job) {		
		try {			
			//job.setID(getMaxValue()+1);
			job.setDate_created(new Timestamp(System.currentTimeMillis()));
			if(job.getStatus()=='\0') {
				job.setStatus('O');
			}
			if(job.getOpenings()==0) {
				job.setOpenings(1);
			}
			getSession().save(job);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Job job) {
		try {			
			getSession().update(job);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteJob(int job_id) {
		try {			
			getSession().delete(getJob(job_id));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	

	public Job getJob(int job_id) {
		
		return (Job)getSession().get(Job.class, job_id);
		
	}
	
	public List<Job> list() {
		
		return (List<Job>)getSession().createQuery("from Job").list();
		
	}

	public List<Job> listByStatus(char status) {
		
		return (List<Job>)getSession().createCriteria(Job.class)
				.add(Restrictions.eq("status", status))
				.list();

	}

	public List<Job> listByCompanyName(String company_name) {
		
		return (List<Job>)getSession().createCriteria(Job.class)
				.add(Restrictions.eq("company_name", company_name))
				.list();
		
	}

	public List<Job> listAboveSalary(int salary) {
		
		String hql = "from Job where salary >= ':salary'";
		Query query = getSession().createQuery(hql);
		query.setParameter("salary", salary);
		
		return query.list();
		
	}
	
	
	//Job Application ##########################################################


	public boolean save(JobApplication jobApplication) {
		try {			
			jobApplication.setDate_applied(new Timestamp(System.currentTimeMillis()));
			jobApplication.setStatus('A');
			getSession().save(jobApplication);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(JobApplication jobApplication) {
		try {			
			getSession().update(jobApplication);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteJobApplication(int job_application_id) {
		try {			
			getSession().update(getJobApplication(job_application_id));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public JobApplication getJobApplication(int job_application_id) {
		
		return (JobApplication)getSession().get(JobApplication.class, job_application_id);

	}
	
	public JobApplication getJobApplication(int job_id, String email_id) {
		
		return (JobApplication)getSession().createCriteria(JobApplication.class)
				.add(Restrictions.eq("job_id", job_id))
				.add(Restrictions.eq("email_id", email_id))
				.uniqueResult();

	}

	public List<JobApplication> listAllForJobID(int job_id) {
		return (List<JobApplication>)getSession().createCriteria(JobApplication.class)
				.add(Restrictions.eq("job_id", job_id))
				.list();
	}

	public List<JobApplication> listAllForEmailID(String email_id) {
		return (List<JobApplication>)getSession().createCriteria(JobApplication.class)
				.add(Restrictions.eq("email_id", email_id))
				.list();
	}

	public List<JobApplication> listAllForJobByStatus(int job_id, char status) {
		return (List<JobApplication>)getSession().createCriteria(JobApplication.class)
				.add(Restrictions.eq("job_id", job_id))
				.add(Restrictions.eq("status", status))
				.list();
	}

}
