package ca.ualberta.cs.cmput301t03app.models;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


/**
 * A class representing an answer to a question.
 *
 */
public class Answer {
	private String id;
	private ArrayList<Comment> comments;
	private String answer;
	private String author;
	private String parentId;
	private Date date;
	private File picture;
	private int rating;
	private GeoLocation location;
	
	/** Constructs an {@link #Answer() Answer}
	 * @param answer		 The answer that the user has created
	 * @param author		 The author of the answer
	 * @param parentID		ID of the Question being answered
	 */
	
	public Answer(String answer, String author, String parentId){
		this.id = UUID.randomUUID().toString();
		this.date = new Date();
		this.answer = answer;
		this.author = author; // Added author to constructor (Added by Eric)
		this.rating = 0;
		this.comments = new ArrayList<Comment>(); // Need to initialize the list (Added by Eric)
		this.parentId = parentId;
	}
	
	/**
	 * Adds a Comment object to the list of comments
	 * @param the comment object being added
	 */
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}
	

	public void setPicture(File picture){
		this.picture = picture;
	}
	
	/**
	 * Increments the score of the answer.
	 */
	public void upRating() {
		this.rating++; // This should be incremented, not manually set.
	
	}
	
	public void setGeoLocation(GeoLocation location) {
		this.location = location;
	}
	
	/*----------------------Get counts--------------------*/
	
	/**
	 * Returns the number of Comment objects pertaining to the Answer object.
	 * @return The number of comments.
	 */
	public int countAnswerComments() {
		if (comments == null) {
			return 0;
		} else
			return comments.size();
	}
	
	/*-----------------------Getters-------------------------*/
	/**
	 * Returns a list of Comment objects
	 * @return A list of Comment objects
	 */
	
	public GeoLocation getGeoLocation() {
		return location;
	}
	
	public ArrayList<Comment> getComments() {
		return this.comments;
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
	
	public File getPicture(){
		return this.picture;
	}
	public String getId(){
		return this.id;
	}
	public String getParentId() {
		return this.parentId;
	}
}
