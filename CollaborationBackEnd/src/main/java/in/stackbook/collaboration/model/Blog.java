package in.stackbook.collaboration.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Table(name="c_blog")
@Entity
public class Blog extends BaseDomain {
	
	@Id
	@GeneratedValue(generator="blog_gen")
	@SequenceGenerator(name="blog_gen", sequenceName="c_blog_seq",allocationSize=1)
	private int blog_id;
	
	private String title;
	
	private String content;
	
	private String email_id;
	
	private char approved;  //Y(Yes)/P(Pending)/S(Suspended)
	
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy hh:mm:ss")
	private Timestamp date_created;
	
	private int views;
	

	public int getBlog_id() {
		return blog_id;
	}

	public void setBlog_id(int blog_id) {
		this.blog_id = blog_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public char getApproved() {
		return approved;
	}

	public void setApproved(char approved) {
		this.approved = approved;
	}

	public Timestamp getDate_created() {
		return date_created;
	}

	public void setDate_created(Timestamp date_created) {
		this.date_created = date_created;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}
	
	

}
