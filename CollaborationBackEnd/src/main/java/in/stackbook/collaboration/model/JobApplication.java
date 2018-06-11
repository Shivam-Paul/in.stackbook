package in.stackbook.collaboration.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="c_job_application")
public class JobApplication extends BaseDomain {
	
	@Id
	@GeneratedValue(generator="job_app_gen")
	@SequenceGenerator(name="job_app_gen", sequenceName="c_job_application_seq",allocationSize=1)	
	private int job_application_id;
	
	private int job_id;
	
	private String email_id;
	
	private Timestamp date_applied;
	
	private char status;  //A(Applied)/R(Rejected)/S(Selected)
	
	public int getJob_application_id() {
		return job_application_id;
	}

	public void setJob_application_id(int job_application_id) {
		this.job_application_id = job_application_id;
	}

	public int getJob_id() {
		return job_id;
	}

	public void setJob_id(int job_id) {
		this.job_id = job_id;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public Timestamp getDate_applied() {
		return date_applied;
	}

	public void setDate_applied(Timestamp date_applied) {
		this.date_applied = date_applied;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	
	

}
