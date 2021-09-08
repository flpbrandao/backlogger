package entities;

import java.util.Date;

public class Ticket {

	private String number, assigned_to, assignment_group, status;
	private Date createdOn, updatedOn;
	private Boolean closed;

	public Ticket() {

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

	

}
