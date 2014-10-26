package ca.ualberta.cs.cmput301t03app;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Answer {
	private String id;
	private ArrayList<Comment> comments;
	private String answer;
	private String author;
	private Date date;
	private File picture;
	private int rating;
	
	/** This constructor should not exist.  We should not allow the author to set the timestamp
	public Answer(String answer, Date date){
		this.date = date;
		this.answer = answer;
		this.rating = 0;
	}
	**/
	
	public Answer(String answer, String author){
		this.id = UUID.randomUUID().toString();
		this.date = new Date();
		this.answer = answer;
		this.author = author; // Added author to constructor (Added by Eric)
		this.rating = 0;
		this.comments = new ArrayList<Comment>(); // Need to initialize the list (Added by Eric)
	}
	
	/**  This constructor should not exists.  Answers should not have NULL attributes
	public Answer()
	{
		this.author = null;
		this.answer = null;
		this.rating = 0;
	}
	**/
	
	public ArrayList<Comment> getComments() {
		return this.comments;
	}
	
	public void addComments(Comment comment) {
		this.comments.add(comment);
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	/** Already being set in the constructor
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	**/
	
	public String getAuthor() {
		return this.author;
	}
	
	/** Already being set in the constructor
	public void setAuthor(String author) {
		this.author = author;
	}
	**/
	
	public Date getDate() {
		return this.date;
	}
	
	/** Already being set in the constructor
	public void setDate(Date date) {
		this.date = date;
	}
	**/
	
	public int getRating() {
		return this.rating;
	}
	
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
