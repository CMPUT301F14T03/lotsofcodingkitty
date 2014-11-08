package ca.ualberta.cs.cmput301t03app.models;

import java.util.Date;

/**
 * This class represents a comment or reply to an answer or question.
 * 
 */

public class Comment {
		
	private Date date;
	private String comment;
	private String author;
	
	/**Constructs a {@link #Comment() Comment}
	 * @param comment		 The comment or reply text
	 * @param author		 The author of the comment
	 */
	
	public Comment(String comment, String author) {
		this.date = new Date();
		this.comment = comment;
		this.author = author;
	}
	
	/*--------------------Getters-------------------*/
	
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