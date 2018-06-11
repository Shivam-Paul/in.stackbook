package in.stackbook.collaboration.rest.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.stackbook.collaboration.dao.JobDAO;
import in.stackbook.collaboration.dao.UserDAO;
import in.stackbook.collaboration.model.Job;
import in.stackbook.collaboration.model.JobApplication;
import in.stackbook.collaboration.model.User;

@RestController
@RequestMapping("/job")
public class JobController {
	
	@Autowired JobDAO jobDAO;
	
	@Autowired Job job;
	
	@Autowired JobApplication jobApplication;
	
	@Autowired User user;
	
	@Autowired UserDAO userDAO;
	
	@Autowired HttpSession httpSession;

	
	@GetMapping("/listalljobs")		
	public ResponseEntity<List<Job>> listAllJobs() {
		
		List<Job> jobs = jobDAO.list();
		
		if(jobs.isEmpty()) {
			job = new Job();
			job.setMessage("No jobs are available");
			jobs.add(job);
			return new ResponseEntity<List<Job>>(jobs, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);

		
		/*List<Integer> job_id = jobDAO.listID();
		List<String> job_title = jobDAO.listTitle();
		List<Job> jobs = new ArrayList<Job>();
		if(job_id.isEmpty()) {
			job = new Job();
			job.setMessage("No jobs are available");
			jobs.add(job);
			return new ResponseEntity<List<Job>>(jobs, HttpStatus.NOT_FOUND);
		}
		Iterator<Integer> id = job_id.iterator();
		Iterator<String> title = job_title.iterator();
		while(id.hasNext() && title.hasNext()) {
			Job tempJob = new Job();
			tempJob.setJob_id((Integer)id.next());
			tempJob.setTitle((String)title.next());
			jobs.add(tempJob);
		}
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);*/
		
	}
	
	@PostMapping("/save")
	public ResponseEntity<Job> saveJob(@RequestBody Job job) {
		
		if(jobDAO.save(job)) {
			job.setMessage("Job saved successfully");
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		job.setMessage("Could not save the job .. please try after some time");
		return new ResponseEntity<Job>(job, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@PostMapping("/update")
	public ResponseEntity<Job> updateJob(@RequestBody Job job) {
		
		if(jobDAO.update(job)) {
			job.setMessage("Job updated successfully");
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		job.setMessage("Could not update the job .. please try after some time");
		return new ResponseEntity<Job>(job, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/update/{job_id}/{status}")
	public ResponseEntity<Job> updateJob(@PathVariable int job_id, @PathVariable char status) {
		
		job = jobDAO.getJob(job_id);
		
		if(job==null) {
			job = new Job();
			job.setMessage("No job exists with this job: "+job_id);
			return new ResponseEntity<Job>(job, HttpStatus.NOT_FOUND);
		}
		job.setStatus(status);
		if(jobDAO.update(job)) {
			job.setMessage("Successfully updated the status of the job: "+job_id+"to: "+status);
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		job.setMessage("Could not update the job .. please try after some time");
		return new ResponseEntity<Job>(job, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@DeleteMapping("/delete/{job_id}")
	public ResponseEntity<Job> deleteJob(@PathVariable int job_id) {
		
		if(jobDAO.deleteJob(job_id)) {
			job.setMessage("Job deleted successfully");
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		job.setMessage("Could not delete the job .. please try after some time");
		return new ResponseEntity<Job>(job, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/listByStatus/{status}")
	public ResponseEntity<List<Job>> getJobsByStatus(@PathVariable char status) {
		
		List<Integer> job_id = jobDAO.listByStatus(status);
		List<String> job_title = new ArrayList<String>();
		
		for(int temp : job_id) {
			
			job_title.add(jobDAO.getJobTitle(temp));
			
		}
		List<Job> jobs = new ArrayList<Job>();
		if(job_id.isEmpty()) {
			job = new Job();
			job.setMessage("No jobs are available");
			jobs.add(job);
			return new ResponseEntity<List<Job>>(jobs, HttpStatus.NOT_FOUND);
		}
		Iterator<Integer> id = job_id.iterator();
		Iterator<String> title = job_title.iterator();
		while(id.hasNext() && title.hasNext()) {
			Job tempJob = new Job();
			tempJob.setJob_id((Integer)id.next());
			tempJob.setTitle((String)title.next());
			jobs.add(tempJob);
		}
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	
	@GetMapping("/get/{job_id}")
	public ResponseEntity<Job> getJob(@PathVariable int job_id) {
		
		job = jobDAO.getJob(job_id);
		
		if(job!=null) {
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		return new ResponseEntity<Job>(job, HttpStatus.NOT_FOUND);
	}
	
	//Job Application #############################################
	
	@GetMapping("/apply/{job_id}/{email_id}")
	public ResponseEntity<JobApplication> applyForAJob(@PathVariable int job_id, @PathVariable String email_id) {
		
		job = jobDAO.getJob(job_id);
										
		if(job==null) {
			jobApplication.setMessage("Job does not exist, cannot apply");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NOT_FOUND);
		}
		if(jobDAO.getJobApplication(job_id, email_id)!=null) {
			jobApplication.setMessage("User already applied, cannot apply again");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.CONFLICT);
		}
		if(job.getStatus()!='O') {
			jobApplication.setMessage("Job is now closed, cannot apply");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.FORBIDDEN);
		}
		
		if(jobDAO.save(jobApplication)) {
			jobApplication.setMessage("You have successfully applied for the job");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
		}
		jobApplication.setMessage("Could not apply for the job .. please try after some time");
		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/application/update/{job_id},{email_id},{status}")
	public ResponseEntity<JobApplication> update(@PathVariable int job_id,
			@PathVariable String email_id, @PathVariable char status) {
		
		
		job = jobDAO.getJob(job_id);
		if(job==null) {
			jobApplication.setMessage("Job does not exist, cannot apply");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NOT_FOUND);
		}
		
		user = userDAO.get(email_id);
		if(job==null) {
			jobApplication.setMessage("User does not exist, cannot apply");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NOT_FOUND);
		}
		
		if(jobDAO.getJobApplication(job_id, email_id)!=null) {
			jobApplication.setMessage("User already applied, cannot apply again");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.CONFLICT);
		}
		
		jobApplication = jobDAO.getJobApplication(job_id, email_id);
		
		if(jobApplication.getStatus()==status) {
			jobApplication.setMessage("Status entered is the existing one, enter different status to update");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.CONFLICT);
		}
		
		jobApplication.setStatus(status);
		if(jobDAO.update(jobApplication)) {
			jobApplication.setMessage("Job Application status updated successfully");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
		}
		jobApplication.setMessage("Could not update the status");
		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/application/listApplicants/{job_id}")
	public ResponseEntity<List<JobApplication>> listApplicantsForJob(@PathVariable int job_id) {
		
		job = jobDAO.getJob(job_id);
		
		List<JobApplication> jobApplications = jobDAO.listAllForJobID(job_id);
		
		if(job==null) {
			jobApplication = new JobApplication();
			jobApplication.setMessage("Job does not exist, cannot apply");
			jobApplications.add(jobApplication);
			return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.NOT_FOUND);
		}
		
		if(jobApplications.isEmpty()) {
			jobApplication = new JobApplication();
			jobApplication.setMessage("No one applied for this job");
			jobApplications.add(jobApplication);
			return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.OK);
	}
	
	@GetMapping("/application/myList/{email_id}")
	public ResponseEntity<List<JobApplication>> myApplications(@PathVariable String email_id) {
		
		List<JobApplication> jobApplications = jobDAO.listAllForEmailID(email_id);
		
		if(userDAO.get(email_id)==null) {
			jobApplication = new JobApplication();
			jobApplication.setMessage("User does not exist");
			jobApplications.add(jobApplication);
			return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.NOT_FOUND);
		}
		
		if(jobApplications.isEmpty()) {
			jobApplication = new JobApplication();
			jobApplication.setMessage("You have not applied for any job");
			jobApplications.add(jobApplication);
			return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.OK);
	}
	
	@GetMapping("/application/check/{job_id}/{email_id}")
	public ResponseEntity<JobApplication> checkJobApplication(@PathVariable int job_id, @PathVariable String email_id) {
		
		job = jobDAO.getJob(job_id);
		if(job==null) {
			jobApplication = new JobApplication();
			jobApplication.setMessage("Job does not exist");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NOT_FOUND);
		}
		jobApplication = jobDAO.getJobApplication(job_id, email_id);
		if(jobApplication!=null) {
			jobApplication = new JobApplication();
			jobApplication.setMessage("Job does not exist");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}
}
