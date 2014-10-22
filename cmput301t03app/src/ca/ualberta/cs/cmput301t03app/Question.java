package ca.ualberta.cs.cmput301t03app;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class Question{
	private ArrayList<Comment> comments;
	private ArrayList<Answer> answers;
	private String subject;
	private String body;
	private String author;
	private File picture;
	private Date date;
	private int rating;

	/** This constructor should not exist because we do not allow the user to set the date manually.
	public Question(Date date, String subject, String question){
		this.date = date;
		this.subject = subject;
		this.question = question;
		
		this.rating = 0;
	}
	**/
	
	public Question(String subject, String body, String author) {
		this.date = new Date();
		this.subject = subject;
		this.body = body;
		this.author = author; // Added author to constructor (Added by Eric)
		this.rating = 0;
		this.answers = new  ArrayList<Answer>();  // Need to initialize the lists (Added by Eric)
		this.comments = new ArrayList<Comment>(); // Same as above
	}
	
	/** This constructor should NOT exist, as they allow the author to create questions without a subject or body
	public Question(){
		this.date = null;
		this.subject = null;
		this.question = null;
		this.rating = 0;
	}
	**/

	public int countAnswers() {
		return this.answers.size();
	}
	
	public ArrayList<Comment> getComments() {
		return this.comments;
	}
	
	public void addComment(Comment comment) {
		this.getComments().add(comment);
	}
	
	public ArrayList<Answer> getAnswers() {
		return this.answers;
	}
	
	public void addAnswer(Answer answer) {
		this.answers.add(answer);
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	/**  Subject is already set in the constructor
	public void setSubject(String subject) {
		this.subject = subject;
	}
	**/
	
	public String getBody() {
		return this.body;
	}
	
	/** Question body is already set in the constructor
	public void setQuestion(String body) {
		this.body = body;
	}
	**/
	
	public Date getDate() {
		return this.date;
	}
	
	/** Date is already set in the constructor
	public void setDate(Date date) {
		this.date = date;
	}
	**/
	
	public int getRating() {
		return this.rating;
	}
	
	public void upRating() {
		this.rating++; //Rating should be incremented by 1 per upvote press; we do not need to tell the system what score is.
	}
	
	public void setPicture(File picture){
		this.picture = picture;
	}
	
	public File getPicture(){
		return this.picture;
	}

	public String getAuthor() {
		return this.author;
	}
	
}
