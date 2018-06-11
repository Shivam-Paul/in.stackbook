package in.stackbook.collaboration.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Table(name="c_user")
@Entity
public class User extends BaseDomain implements Serializable {
	
	//@Email
	@Id
	//@NotNull
	private String email_id;
	
	private String name;
	
	private String password;
	
	private int role;  //1(New)/2(Student)/3(Moderator)/4(Teacher)/5(Admin)
	
	private Character active;  //Y(Yes)/N(No)
	
	private Timestamp registered_date;
	
	private String mobile;


	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public Character getActive() {
		return active;
	}

	public void setActive(Character active) {
		this.active = active;
	}

	public Timestamp getRegistered_date() {
		return registered_date;
	}

	public void setRegistered_date(Timestamp registered_date) {
		this.registered_date = registered_date;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
	
}
