package ca.ualberta.cs.cmput301t03app;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import android.media.*;


public class Question{
	ArrayList<Comment> comments;
	ArrayList<Answer> answers;
	String subject;
	String question;
	String author;
	File picture;
	Date date;
	int rating;
	
	public Question(Date date, String subject, String question){
		this.date = date;
		this.subject = subject;
		this.question = question;
		this.rating = 0;
	}
	
	public Question(String subject, String question) {
		this.date = new Date();
		this.subject = subject;
		this.question = question;
		this.rating = 0;
	}
	
	public Question(){
		this.date = null;
		this.subject = null;
		this.question = null;
		this.rating = 0;
	}
	
	public ArrayList<Comment> getComments() {
		return comments;
	}
	public void addComment(Comment comment) {
		getComments().add(comment);
	}
	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	public void addAnswer(Answer answer) {
		getAnswers().add(answer);
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
//	public Image getPicture() {
//		return picture;
//	}
//	public void setPicture(Image picture) {
//		this.picture = picture;
//	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public void setPicture(File picture){
		this.picture = picture;
	}
	
	public File getPicture(){
		return this.picture;
	}
	
}
