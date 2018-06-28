package in.stackbook.collaboration.dao;

import java.util.List;

import in.stackbook.collaboration.model.Job;
import in.stackbook.collaboration.model.JobApplication;

public interface JobDAO {
	
	//Job methods
	
	public boolean save(Job job);
	
	public boolean update(Job job);
	
	public boolean deleteJob(int job_id);
	
	public Job getJob(int job_id);
		
	public List<Job> list();
		
	public List<Job> listByStatus(char status);
	
	public List<Job> listByCompanyName(String company_name);
	
	public List<Job> listAboveSalary(int salary);
	
	
	//JobApplication methods
	
	public boolean save(JobApplication jobApplication);
	
	public boolean update(JobApplication jobApplication);

	public boolean deleteJobApplication(int job_application_id);
	
	public JobApplication getJobApplication(int job_application_id);
	
	public JobApplication getJobApplication(int job_id, String email_id);
	
	public List<JobApplication> listAllForJobID(int job_id);
	
	public List<JobApplication> listAllForEmailID(String email_id);
	
	public List<JobApplication> listAllForJobByStatus(int job_id, char status);
	
	
	

}
