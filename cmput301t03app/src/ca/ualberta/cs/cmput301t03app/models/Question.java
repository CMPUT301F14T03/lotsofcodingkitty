package ca.ualberta.cs.cmput301t03app.models;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


/**
 * Represents a Question the user posts to the app.
 * The app will handle lists of questions.
 */

public class Question{
	private String id;
	private ArrayList<Answer> answers;
	private ArrayList<Comment> comments;
	private String subject;
	private String body;
	private String author;
	private File picture;
	private Date date;
	private int rating;
	

	
	public Question(String subject, String body, String author) {
		this.id = UUID.randomUUID().toString();
		this.date = new Date();
		this.subject = subject;
		this.body = body;
		this.author = author; // Added author to constructor (Added by Eric)
		this.rating = 0;
		this.comments = new ArrayList<Comment>(); // Same as above
		this.answers = new ArrayList<Answer>();
	}
	
	/** This is for testing sorting by dates
	 * 	@author tbrockma
	 */
	
	public Question(String subject, String body, String author, Date date) {
		this.id = UUID.randomUUID().toString();
		this.date = date;
		this.subject = subject;
		this.body = body;
		this.author = author; // Added author to constructor (Added by Eric)
		this.rating = 0;
		this.comments = new ArrayList<Comment>(); // Same as above
		this.answers = new ArrayList<Answer>();
	}
	

	/**
	 * Adds an Answer object to the list of answers.
	 * @param answer An Answer Object
	 */
	public void addAnswer(Answer answer){
		this.answers.add(answer);
	}
	
	/**
	 * Adds a Comment object to the list of comments.
	 * @param comment A Comment Object
	 */
	public void addComment(Comment comment) {
		this.getComments().add(comment);
	}
	
	/**
	 * Increments the score of the answer.
	 */
	public void upRating() {
		this.rating++; //Rating should be incremented by 1 per upvote press; we do not need to tell the system what score is.
	}
	
	/**
	 * Sets the picture to an image provided by the user
	 * @param picture
	 */
	public void setPicture(File picture){
		this.picture = picture;
	}
	
	/*-----------------------Get counts---------------------*/
	
	/**
	 * Returns the number of Answer objects to the question
	 * @return The number of Answer Objects
	 */
	public int countAnswers() {
		return this.answers.size();
	}
	
	/**
	 * Returns the number of Comment objects to the question
	 * @return int The number of Comment objects.
	 */
	public int countComments() {
		return this.comments.size();
	}
	
	/*----------------------Getters--------------------------*/
	
	public ArrayList<Answer> getAnswers() {
		return this.answers;
	}
	
	public ArrayList<Comment> getComments() {
		return this.comments;
	}
	
	public String getSubject() {
		return this.subject;
	}
		
	public String getBody() {
		return this.body;
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

	public String getAuthor() {
		return this.author;
	}
	public String getId(){
		return this.id;
	}
}
