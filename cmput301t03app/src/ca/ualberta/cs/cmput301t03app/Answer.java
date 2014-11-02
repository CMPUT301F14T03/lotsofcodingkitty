package ca.ualberta.cs.cmput301t03app;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Represents an Answer to a Question.
 * A Question object can have multiple Answers.
 * 
 * @since 2013-11-2
 */
public class Answer {
	private String id;
	private ArrayList<Comment> comments;
	private String answer;
	private String author;
	private Date date;
	private File picture;
	private int rating;
	
	
	public Answer(String answer, String author,String parentID){
		this.id = UUID.randomUUID().toString();
		this.date = new Date();
		this.answer = answer;
		this.author = author; // Added author to constructor (Added by Eric)
		this.rating = 0;
		this.comments = new ArrayList<Comment>(); // Need to initialize the list (Added by Eric)
	}
	
	/**
	 * Returns a list of Comment objects
	 * @return comments A list of Comment objects
	 */
	public ArrayList<Comment> getComments() {
		return this.comments;
	}
	
	/**
	 * Returns the number of Comment objects to the answer
	 * @return int The count of Comment objects.
	 */
	public int countAnswerComments() {
		if (comments == null) {
			return 0;
		} else
			return comments.size();
	}
	
	/**
	 * Adds a Comment object to the list of comments
	 * @param comment A Comment object
	 */
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	
	public String getAuthor() {
		return this.author;
	}
	
	
	public Date getDate() {
		return this.date;
	}
	
	public int getRating() {
		return this.rating;
	}
	
	/**
	 * Increments the score of the answer.
	 */
	public void upRating() {
		this.rating++; // This should be incremented, not manually set.
	
	}
	
	public void setPicture(File picture){
		this.picture = picture;
	}
	
	public File getPicture(){
		return this.picture;
	}
	public String getId(){
		return this.id;
	}
}
