package ca.ualberta.cs.cmput301t03app;

import java.util.Date;

public class Comment {
	/*
	 * This is the comment class. It allows users to set the text of a comment*/
		
	private Date date;
	private String comment;
	
	public Comment(String comment) {
		this.date = new Date();
		this.comment = comment;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	/** We should not be allowing anyone to tamper with timestamps!
	public void setDate(Date date) {
		this.date = date;
	}
	**/
	
	public String getComment() {
		return this.comment;
	}
	
	/** This is already set in the constructor!
	public void setComment(String comment) {
		this.comment = comment;
	}
	**/
	
	

			
	
}