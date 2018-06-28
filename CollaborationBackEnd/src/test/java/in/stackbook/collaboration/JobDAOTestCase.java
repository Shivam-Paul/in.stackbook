package in.stackbook.collaboration;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import in.stackbook.collaboration.dao.JobDAO;
import in.stackbook.collaboration.model.Job;
import in.stackbook.collaboration.model.JobApplication;

public class JobDAOTestCase {
	
	private static AnnotationConfigApplicationContext context;
	
	@Autowired private static JobDAO jobDAO;
	
	@Autowired private static Job job;
	
	@Autowired private static JobApplication jobApplication;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("in.stackbook");
		context.refresh();
		
		jobDAO = (JobDAO)context.getBean("jobDAO");
		job = (Job)context.getBean("job");
		jobApplication = (JobApplication)context.getBean("jobApplication");
	
	}
	
	@Test
	public void saveJobTestCase() {
		
		
		
		//job.setJob_id(100);
		job.setTitle("Software");
		job.setSalary(20000);
		job.setCompany_name("NIIT");
		job.setQualification("12+");
		job.setStatus('O');
		job.setOpenings(3);
		
		Timestamp dt_date = new Timestamp(2018, 07, 02, 06, 14, 00, 742000000);
		
		job.setApply_before(dt_date);
		
		job.setDescription("Software");

		
		Assert.assertEquals("Save Job Test Case", true, jobDAO.save(job));
	}
	
	@Test
	public void updateJobTestCase() {
		
		job = jobDAO.getJob(6);
		
		//job.setOpenings(1);
		job.setStatus('C');
		
		Assert.assertEquals("Update Job Test Case", true, jobDAO.update(job));
	}
	
	@Test
	public void deleteJobTestCase() {
		
		Assert.assertEquals("Delete Job Test Case", true, jobDAO.deleteJob(1));
		
	}
	
	@Test
	public void getJobTestCase() {
		
		Assert.assertNotNull("Get Job Test Case", jobDAO.getJob(21));
		
	}
	
	@Test
	public void getAllJobs() {
		
		int actualSize = jobDAO.list().size();
		
		Assert.assertEquals(1, actualSize);
		
	}
	
	@Test
	public void listAllJobsAboveSalary() {
		
		Assert.assertEquals("List", 1, jobDAO.listAboveSalary(5000).size());
		
	}
	
	//Job Application Test Cases
	
	@Test
	public void saveJobApplicationTestCase() {
		
		jobApplication.setJob_id(21);
		jobApplication.setEmail_id("a@b.com");
		
		Assert.assertEquals("Save Job Application Test Case", true, jobDAO.save(jobApplication));
		
	}
	
	@Test
	public void updateJobApplicationTestCase() {
		
		jobApplication = jobDAO.getJobApplication(21);
		
		jobApplication.setStatus('S');
		
		Assert.assertEquals("Update Job Application Test Case", true, jobDAO.update(jobApplication));
		
	}
	
	@Test
	public void deleteJobApplicationTestCase() {
		
		Assert.assertEquals("Delete Job Application Test Case", true, jobDAO.deleteJobApplication(1));
		
	}
	
	@Test
	public void getJobApplicationTestCase() {
		
		Assert.assertNotNull("Get Job Test Case", jobDAO.getJobApplication(21));
		
	}
	
	@Test
	public void getAllApplicationsForJobID() {
	
		int actualSize = jobDAO.listAllForJobID(21).size();
		
		Assert.assertEquals(1, actualSize);
		
	}
	
	@Test
	public void getAllApplicationsForEmailID() {
	
		int actualSize = jobDAO.listAllForEmailID("a@b.com").size();
		
		Assert.assertEquals(1, actualSize);
		
	}
	
	@Test
	public void getAllApplicationsForJobByStatus() {
	
		int actualSize = jobDAO.listAllForJobByStatus(21,'S').size();
		
		Assert.assertEquals(1, actualSize);
		
	}
	
	
	
	

}
