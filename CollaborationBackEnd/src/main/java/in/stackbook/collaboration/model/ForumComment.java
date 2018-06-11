package in.stackbook.collaboration.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Table(name="c_forum_comment")
@Entity
public class ForumComment extends BaseDomain {
	
	@Id
	@GeneratedValue(generator="forum_comment_gen")
	@SequenceGenerator(name="forum_comment_gen", sequenceName="c_forum_comment_seq",allocationSize=1)
	private int f_comment_id;
	
	private int forum_id;
	
	private String forum_comment;
	
	private String email_id;
	
	private Timestamp comment_date;
	
	private int score;	
	

	public int getF_comment_id() {
		return f_comment_id;
	}

	public void setF_comment_id(int f_comment_id) {
		this.f_comment_id = f_comment_id;
	}

	public int getForum_id() {
		return forum_id;
	}

	public void setForum_id(int forum_id) {
		this.forum_id = forum_id;
	}

	public String getForum_comment() {
		return forum_comment;
	}

	public void setForum_comment(String forum_comment) {
		this.forum_comment = forum_comment;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public Timestamp getComment_date() {
		return comment_date;
	}

	public void setComment_date(Timestamp comment_date) {
		this.comment_date = comment_date;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
	

}
