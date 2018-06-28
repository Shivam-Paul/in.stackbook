package in.stackbook.collaboration.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Table(name="c_forum")
@Entity
public class Forum extends BaseDomain {
	
	@Id
	@GeneratedValue(generator="forum_gen")
	@SequenceGenerator(name="forum_gen", sequenceName="c_forum_seq",allocationSize=1)
	private int forum_id;
	
	private int parent_id;
	
	private char approved;  //Y(Yes)/P(Pending)/R(Rejected)
	
	private String email_id;
	
	private String content;
	
	private int forum_level;
	
	private int new_post_access;  //-1(Anyone)/ 1-5 same as User
	
	private int view_access;  //		^
	
	private char archived;  //Y(Yes)/N(No)
	
	private Timestamp date_created;
	
	private int comment_access;  //		^
	
	private String title;
	

	public int getForum_id() {
		return forum_id;
	}

	public void setForum_id(int forum_id) {
		this.forum_id = forum_id;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public char getApproved() {
		return approved;
	}

	public void setApproved(char approved) {
		this.approved = approved;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getForum_level() {
		return forum_level;
	}

	public void setForum_level(int forum_level) {
		this.forum_level = forum_level;
	}

	public int getNew_post_access() {
		return new_post_access;
	}

	public void setNew_post_access(int new_post_access) {
		this.new_post_access = new_post_access;
	}

	public int getView_access() {
		return view_access;
	}

	public void setView_access(int view_access) {
		this.view_access = view_access;
	}

	public char getArchived() {
		return archived;
	}

	public void setArchived(char archived) {
		this.archived = archived;
	}

	public Timestamp getDate_created() {
		return date_created;
	}

	public void setDate_created(Timestamp date_created) {
		this.date_created = date_created;
	}

	public int getComment_access() {
		return comment_access;
	}

	public void setComment_access(int comment_access) {
		this.comment_access = comment_access;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	
	
	
	

}
