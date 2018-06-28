package in.stackbook.collaboration.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Table(name="c_job")
@Entity
public class Job extends BaseDomain {
	
	@Id
	@GeneratedValue(generator="job_gen")
	@SequenceGenerator(name="job_gen", sequenceName="c_job_seq",allocationSize=1)
	private int job_id;
	
	private String title;
	
	private int salary;
	
	private String description;
	
	private String company_name;
	
	private String qualification;
	
	private Timestamp date_created;
	
	private Timestamp apply_before;
	
	private char status;  //O(Open)/C(Closed)
	
	private int openings;

	public int getJob_id() {
		return job_id;
	}

	public void setJob_id(int job_id) {
		this.job_id = job_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Timestamp getDate_created() {
		return date_created;
	}

	public void setDate_created(Timestamp date_created) {
		this.date_created = date_created;
	}

	public Timestamp getApply_before() {
		return apply_before;
	}

	public void setApply_before(Timestamp apply_before) {
		this.apply_before = apply_before;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public int getOpenings() {
		return openings;
	}

	public void setOpenings(int openings) {
		this.openings = openings;
	}
	
	

}
