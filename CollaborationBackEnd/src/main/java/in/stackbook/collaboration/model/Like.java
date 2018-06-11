package in.stackbook.collaboration.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Table(name="c_like")
@Entity
public class Like extends BaseDomain {
	
	@Id
	@GeneratedValue(generator="like_gen")
	@SequenceGenerator(name="like_gen", sequenceName="c_like_seq",allocationSize=1)
	private int like_id;
	
	private char reference_table; //B(Blog)/G(Blog_Comments)/F(Forum)/M(Forum_Comments)
	
	private int table_id;
	
	private String email_id;
	
	private int liked;  //0 or 1
	

	public int getLike_id() {
		return like_id;
	}

	public void setLike_id(int like_id) {
		this.like_id = like_id;
	}

	public char getReference_table() {
		return reference_table;
	}

	public void setReference_table(char reference_table) {
		this.reference_table = reference_table;
	}

	public int getTable_id() {
		return table_id;
	}

	public void setTable_id(int table_id) {
		this.table_id = table_id;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public int getLiked() {
		return liked;
	}

	public void setLiked(int liked) {
		this.liked = liked;
	}
	
	
	

}
