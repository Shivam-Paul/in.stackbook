package in.stackbook.collaboration.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Table(name="c_user_notification")
@Entity
public class UserNotification extends BaseDomain {
	
	@Id
	@GeneratedValue(generator="user_notification_gen")
	@SequenceGenerator(name="user_notification_gen", sequenceName="c_user_notification_seq",allocationSize=1)
	private int notification_id;
	
	private String email_id;
	
	private String notification;
	
	private char seen;  //Y(Yes)/N(No)
	
	private Timestamp received_date;
	
	private char reference_table;
	
	private int reference_id;
	

	public int getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(int notification_id) {
		this.notification_id = notification_id;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public char getSeen() {
		return seen;
	}

	public void setSeen(char seen) {
		this.seen = seen;
	}

	public Timestamp getReceived_date() {
		return received_date;
	}

	public void setReceived_date(Timestamp received_date) {
		this.received_date = received_date;
	}

	public char getReference_table() {
		return reference_table;
	}

	public void setReference_table(char reference_table) {
		this.reference_table = reference_table;
	}

	public int getReference_id() {
		return reference_id;
	}

	public void setReference_id(int reference_id) {
		this.reference_id = reference_id;
	}
	
	
	
	
	

}
