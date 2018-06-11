package in.stackbook.collaboration.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Table(name="c_friend")
@Entity
public class Friend extends BaseDomain {
	
	@Id
	@GeneratedValue(generator="friend_gen")
	@SequenceGenerator(name="friend_gen", sequenceName="c_friend_seq",allocationSize=1)
	private int friend_id;
	
	private String email_id1;  // Person who sent the request
	
	private String email_id2;  // Receives request
	
	private int friends;  
	//  1(Yes)/2(1 requested 2)/3(1 requested 2 but ignored)/4(1 blocked 2)/5(2 blocked 1)/6(Both blocked each other)

	public int getFriend_id() {
		return friend_id;
	}

	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}

	public String getEmail_id1() {
		return email_id1;
	}

	public void setEmail_id1(String email_id1) {
		this.email_id1 = email_id1;
	}

	public String getEmail_id2() {
		return email_id2;
	}

	public void setEmail_id2(String email_id2) {
		this.email_id2 = email_id2;
	}

	public int getFriends() {
		return friends;
	}

	public void setFriends(int friends) {
		this.friends = friends;
	}

	
	

}
