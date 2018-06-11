package in.stackbook.collaboration.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Table(name="c_bulletin")
@Entity
public class Bulletin extends BaseDomain {
	
	@Id
	@GeneratedValue(generator="bulletin_gen")
	@SequenceGenerator(name="bulletin_gen", sequenceName="c_bulletin_seq",allocationSize=1)
	private int bulletin_id;
	
	private String title;
		
	private Timestamp event_date;
	
	private String description;


	public int getBulletin_id() {
		return bulletin_id;
	}

	public void setBulletin_id(int bulletin_id) {
		this.bulletin_id = bulletin_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getEvent_date() {
		return event_date;
	}

	public void setEvent_date(Timestamp event_date) {
		this.event_date = event_date;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
