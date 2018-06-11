package in.stackbook.collaboration.model;

import java.sql.Timestamp;

public class OutputMessage extends Message {
	
	private Timestamp time;

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	public OutputMessage(Message original, Timestamp time) {
		super(original.getId(), original.getMessage());
		this.time = time;
	}

}
