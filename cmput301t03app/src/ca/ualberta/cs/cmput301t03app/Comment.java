package ca.ualberta.cs.cmput301t03app;

import java.util.Date;

public class Comment {
	/*
	 * This is the comment class. It allows users to set the text of a comment*/
		
	private Date date;
	private String comment;
	private String author;
	
	public Comment(String comment, String author) {
		this.date = new Date();
		this.comment = comment;
		this.author = author;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	/** We should not be allowing anyone to tamper with timestamps!
	public void setDate(Date date) {
		this.date = date;
	}
	**/
	
	public String getCommentBody() {
		return this.comment;
	}
	
	/** This is already set in the constructor!
	public void setComment(String comment) {
		this.comment = comment;
	}
	**/
	
	public String getAuthor() {
		return this.author;
	}
	

			
	
}