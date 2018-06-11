package in.stackbook.collaboration.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Table(name="c_blog_comment")
@Entity
public class BlogComment extends BaseDomain {
	
	@Id
	@GeneratedValue(generator="blog_comment_gen")
	@SequenceGenerator(name="blog_comment_gen", sequenceName="c_blog_comment_seq",allocationSize=1)
	private int b_comment_id;
	
	private int blog_id;
	
	private String blog_comment;
	
	private Timestamp comment_date;
	
	private String email_id;
	
	private int score;
	
	
	

	public int getB_comment_id() {
		return b_comment_id;
	}

	public void setB_comment_id(int b_comment_id) {
		this.b_comment_id = b_comment_id;
	}

	public int getBlog_id() {
		return blog_id;
	}

	public void setBlog_id(int blog_id) {
		this.blog_id = blog_id;
	}

	public String getBlog_comment() {
		return blog_comment;
	}

	public void setBlog_comment(String blog_comment) {
		this.blog_comment = blog_comment;
	}

	public Timestamp getComment_date() {
		return comment_date;
	}

	public void setComment_date(Timestamp comment_date) {
		this.comment_date = comment_date;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	

}
