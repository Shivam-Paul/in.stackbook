package in.stackbook.collaboration.rest.controller;

import java.util.ArrayList;
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
	
	@Autowired HttpSession session;

	
	@GetMapping("/listAllJobs")		
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
	
	@GetMapping("/updateStatus/{job_id}/{status}")
	public ResponseEntity<Job> updateJobStatus(@PathVariable int job_id, @PathVariable char status) {
		
		job = jobDAO.getJob(job_id);
		
		if(job == null) {
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
		
		List<Job> jobs = jobDAO.listByStatus(status);
		
		if(jobs.isEmpty()) {
			job = new Job();
			job.setMessage("No jobs are available");
			jobs.add(job);
			return new ResponseEntity<List<Job>>(jobs, HttpStatus.NO_CONTENT);
		}
		/*Iterator<Integer> id = job_id.iterator();
		Iterator<String> title = job_title.iterator();
		while(id.hasNext() && title.hasNext()) {
			Job tempJob = new Job();
			tempJob.setJob_id((Integer)id.next());
			tempJob.setTitle((String)title.next());
			jobs.add(tempJob);
		}*/
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	
	@GetMapping("/listByCompanyName/{company_name}")
	public ResponseEntity<List<Job>> getJobsByCompanyName(@PathVariable String company_name) {
		
		List<Job> jobs = jobDAO.listByCompanyName(company_name);
		
		if(jobs.isEmpty()) {
			job = new Job();
			job.setMessage("No jobs are available");
			jobs.add(job);
			return new ResponseEntity<List<Job>>(jobs, HttpStatus.NO_CONTENT);
		}
	
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	
	@GetMapping("/listAboveSalary/{salary}")
	public ResponseEntity<List<Job>> getJobsAboveSalary(@PathVariable int salary) {
		
		List<Job> jobs = jobDAO.listAboveSalary(salary);
		
		if(jobs.isEmpty()) {
			job = new Job();
			job.setMessage("No jobs are available");
			jobs.add(job);
			return new ResponseEntity<List<Job>>(jobs, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	
	@GetMapping("/get/{job_id}")
	public ResponseEntity<Job> getJob(@PathVariable int job_id) {
		
		job = jobDAO.getJob(job_id);
		
		if(job != null) {
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		return new ResponseEntity<Job>(job, HttpStatus.NOT_FOUND);
	}
	
	
	//Job Application #############################################
	
	
	@GetMapping("/application/save/{job_id}")
	public ResponseEntity<JobApplication> applyForAJob(@PathVariable int job_id) {
				
		job = jobDAO.getJob(job_id);
		
		user = (User)session.getAttribute("loggedInUser");
		
		String email_id = user.getEmail_id();
		
		jobApplication = new JobApplication();
										
		if(job != null) {
			if(user != null) {
				if(jobDAO.getJobApplication(job_id, email_id) == null) {
					if(job.getStatus() == 'O') {
						jobApplication.setEmail_id(email_id);
						jobApplication.setJob_id(job_id);
						if(jobDAO.save(jobApplication)) {
							jobApplication.setMessage("You have successfully applied for the job");
							return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
						}
						jobApplication.setMessage("Could not apply for the job .. please try after some time");
						return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.INTERNAL_SERVER_ERROR);
					}
					jobApplication.setMessage("Job is now closed, cannot apply");
					return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.FORBIDDEN);
				}
				jobApplication.setMessage("User already applied, cannot apply again");
				return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.CONFLICT);
			}
			jobApplication.setMessage("User does not exist");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NOT_FOUND);
		}
		jobApplication.setMessage("Job does not exist, cannot apply");
		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NOT_FOUND);	
		
	}
	
	@GetMapping("/application/update/{job_id}/{email_id}/{status}")
	public ResponseEntity<JobApplication> updateApplication(@PathVariable int job_id,
			@PathVariable String email_id, @PathVariable char status) {
		
		job = jobDAO.getJob(job_id);
		user = userDAO.get(email_id);
		jobApplication = jobDAO.getJobApplication(job_id, email_id);

		if(job != null) {
			if(user != null) {
				if(jobApplication != null) {
					if(jobApplication.getStatus() != status) {
						jobApplication.setStatus(status);
						if(jobDAO.update(jobApplication)) {
							jobApplication.setMessage("Job Application status updated successfully");
							return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
						}
						jobApplication.setMessage("Could not update the status");
						return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.INTERNAL_SERVER_ERROR);
					}
					jobApplication.setMessage("Status entered is the existing one, enter different status to update");
					return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.CONFLICT);
				}
				jobApplication.setMessage("Job Application does not exist");
				return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.CONFLICT);
			}
			jobApplication.setMessage("User does not exist");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NOT_FOUND);
		}
		jobApplication.setMessage("Job does not exist");
		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping("/application/delete/{job_application_id}")
	public ResponseEntity<JobApplication> deleteApplication(@PathVariable int job_application_id) {
		
		jobApplication = jobDAO.getJobApplication(job_application_id);
		if(jobApplication != null) {
			if(jobDAO.deleteJobApplication(job_application_id)) {
				jobApplication.setMessage("Job Application delete successfully");
				return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
			}
			jobApplication.setMessage("Could not delete the job application .. please try after some time");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		jobApplication.setMessage("Job application does not exist");
		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping("/application/listApplicants/{job_id}")
	public ResponseEntity<List<JobApplication>> listApplicantsForJob(@PathVariable int job_id) {
		
		job = jobDAO.getJob(job_id);
		
		List<JobApplication> applications = new ArrayList<JobApplication>();
		
		if(job==null) {
			jobApplication = new JobApplication();
			jobApplication.setMessage("Job does not exist, cannot apply");
			applications.add(jobApplication);
			return new ResponseEntity<List<JobApplication>>(applications, HttpStatus.NOT_FOUND);
		}
		
		applications = jobDAO.listAllForJobID(job_id);
		
		if(applications.isEmpty()) {
			jobApplication = new JobApplication();
			jobApplication.setMessage("No one applied for this job");
			applications.add(jobApplication);
			return new ResponseEntity<List<JobApplication>>(applications, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<JobApplication>>(applications, HttpStatus.OK);
	}
	
	@GetMapping("/application/myList")
	public ResponseEntity<List<JobApplication>> myApplications() {
		
		user = (User)session.getAttribute("loggedInUser");
		
		List<JobApplication> applications = new ArrayList<JobApplication>();
		
		if(userDAO.get(user.getEmail_id()) == null) {
			jobApplication = new JobApplication();
			jobApplication.setMessage("User does not exist");
			applications.add(jobApplication);
			return new ResponseEntity<List<JobApplication>>(applications, HttpStatus.NOT_FOUND);
		}
		
		applications = jobDAO.listAllForEmailID(user.getEmail_id());
		
		if(applications.isEmpty()) {
			jobApplication = new JobApplication();
			jobApplication.setMessage("You have not applied for any job");
			applications.add(jobApplication);
			return new ResponseEntity<List<JobApplication>>(applications, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<JobApplication>>(applications, HttpStatus.OK);
	}
	
	@GetMapping("/application/check/{job_id}")
	public ResponseEntity<JobApplication> checkJobApplication(@PathVariable int job_id) {
		
		user = (User)session.getAttribute("loggedInUser");
		
		job = jobDAO.getJob(job_id);
		if(job == null) {
			jobApplication = new JobApplication();
			jobApplication.setMessage("Job does not exist");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NOT_FOUND);
		}
		jobApplication = jobDAO.getJobApplication(job_id, user.getEmail_id());
		if(jobApplication != null) {
			jobApplication = new JobApplication();
			jobApplication.setMessage("Job Application does not exist");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}
}
