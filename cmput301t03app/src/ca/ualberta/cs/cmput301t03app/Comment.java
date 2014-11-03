package ca.ualberta.cs.cmput301t03app;

import java.util.Date;

/**
 * Represents a comment or reply that is added to
 * Answer or Question objects. Answer and Question objects 
 * can have multiple comments.
 * 
 * @since 2014-10-28
 */

public class Comment {
		
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
	
	public String getCommentBody() {
		return this.comment;
	}
	
	public String getAuthor() {
		return this.author;
	}
}