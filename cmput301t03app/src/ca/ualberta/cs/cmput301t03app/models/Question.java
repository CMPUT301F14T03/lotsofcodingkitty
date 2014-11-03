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
	private ArrayList<Comment> comments;
	private String subject;
	private String body;
	private String author;
	private File picture;
	private Date date;
	private int rating;
	private ArrayList<Answer> answers;

	
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
	
	public int countAnswers() {
		return this.answers.size();
	}
	
	public int countComments() {
		return this.comments.size();
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
	
	public Answer getAnswerByID(String answerID) {
		/* Josh : This might not be needed */
		for (int i = 0; i<answers.size(); i++) {
			if(this.answers.get(i).getId().equals(answerID)) {
				return answers.get(i);
			}
		}
		return null;
	}	
	
	public void addAnswer(Answer answer){
		this.answers.add(answer);
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
	public String getId(){
		return this.id;
	}
}
