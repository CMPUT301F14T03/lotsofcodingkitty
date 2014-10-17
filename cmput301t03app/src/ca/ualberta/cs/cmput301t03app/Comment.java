package ca.ualberta.cs.cmput301t03app;

import java.util.Date;

public class Comment {
	/*
	 * This is the comment class. It allows users to set the text of a comment*/
		
	public Date date;
	public String comment;
	
	public Comment(String comment) {
		this.date = new Date();
		this.comment = comment;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	

			
	
}