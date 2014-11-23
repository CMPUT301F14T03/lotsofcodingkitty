package ca.ualberta.cs.cmput301t03app.models;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.graphics.Bitmap;


/**
 * This class represents a question. <p>
 * It models a real question in life where it has a subject of the question, a 
 * description of the question, who wrote the question, etc.
 */

public class Question{
	private String id;
	private ArrayList<Answer> answers;
	private ArrayList<Comment> comments;
	private String subject;
	private String body;
	private String author;
	private File picture;
	private Bitmap pictureFilePath;
	private Date date;
	private int rating;
	private GeoLocation location;
	
	/** Constructs a {@link #Question() Question}.
	 * @param subject			The subject that the question is asking about.
	 * @param body				The body of the question or description.
	 * @param author			The author of the question.
	 */
	
	public Question(String subject, String body, String author) {
		this.id = UUID.randomUUID().toString();
		this.date = new Date();
		this.subject = subject;
		this.body = body;
		this.author = author; // Added author to constructor (Added by Eric)
		this.rating = 0;
		this.comments = new ArrayList<Comment>(); // Same as above
		this.answers = new ArrayList<Answer>();
		this.pictureFilePath = null;
	}
	
	/**
	 * This method is for testing date comparison
	 * @param subject
	 * @param body
	 * @param author
	 * @param date
	 */
	
	public Question(String subject, String body, String author, Date date) {
		this.id = UUID.randomUUID().toString();
		this.date = date;
		this.subject = subject;
		this.body = body;
		this.author = author;
		this.rating = 0;
		this.comments = new ArrayList<Comment>();
		this.answers = new ArrayList<Answer>();
	}
	

	/**
	 * Adds an Answer object to the list of answers.
	 * @param An Answer object being added to the question
	 */
	public void addAnswer(Answer answer){
		this.answers.add(answer);
	}
	
	/**
	 * Adds a Comment object to the list of comments.
	 * @param A Comment Object being added
	 */
	public void addComment(Comment comment) {
		this.getComments().add(comment);
	}
	
	/**
	 * Increments the score of the question.
	 */
	public void upRating() {
		this.rating++; //Rating should be incremented by 1 per upvote press; we do not need to tell the system what score is.
	}
	
	public void setPicture(Bitmap pictureFilePath){
		//public void setPicture(File picture){
		//this.picture = picture;
		this.pictureFilePath = pictureFilePath;
	}
	
	/**
	 * Used for replacing answer list with newly upvoted answers
	 * @param answers
	 */
	
	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	
	public void setGeoLocation(GeoLocation location) {
		this.location = location;
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
	
	public GeoLocation getGeoLocation() {
		return location;
	}
	
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
	
	public Bitmap getPicture(){
		//return this.picture;
		return this.pictureFilePath;
	}

	public String getAuthor() {
		return this.author;
	}
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
}
