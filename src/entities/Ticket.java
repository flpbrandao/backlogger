package entities;


import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.scene.control.CheckBox;

public class Ticket {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	private String number, assigned_to, assignment_group, status;
	private Date createdOn, updatedOn;
	private Boolean closed;
	private CheckBox completed;

	public Ticket() {

	}
	
	

	public Ticket(String number, Date createdOn, Date updatedOn) {
		this.number = number;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}



	public Ticket(String number, String assigned_to, String assignment_group, String status, Date createdOn,
			Date updatedOn) {
		this.number = number;
		this.assigned_to = assigned_to;
		this.assignment_group = assignment_group;
		this.status = status;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}



	public Ticket(String number) {
		this.number = number;
	
	}
	
	public Ticket(String number, String assigned_to, String assignment_group, String status, Date createdOn,
			Date updatedOn, CheckBox completed) {
		this.number = number;
		this.assigned_to = assigned_to;
		this.assignment_group = assignment_group;
		this.status = status;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.completed = completed;
	}

	public CheckBox getCompleted() {
		return completed;
	}

	public void setCompleted(CheckBox completed) {
		this.completed = completed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNumber() {
		return number;
	}



	public void setNumber(String number) {
		this.number = number;
	}

	public String getAssigned_to() {
		return assigned_to;
	}

	public void setAssigned_to(String assigned_to) {
		this.assigned_to = assigned_to;
	}

	public String getAssignment_group() {
		return assignment_group;
	}

	public void setAssignment_group(String assignment_group) {
		this.assignment_group = assignment_group;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	@Override
	public String toString() {
		return "Ticket [number=" + number + ", assigned_to=" + assigned_to + ", assignment_group=" + assignment_group
				+ ", status=" + status + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", closed=" + closed
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}


}
