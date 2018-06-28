package in.stackbook.collaboration.model;

import java.sql.Date;

public class OutputMessage extends Message {
	
	private Date date;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public OutputMessage(Message original, Date date) {
		super(original.getId(), original.getMessage());
		this.date = date;
	}

}
