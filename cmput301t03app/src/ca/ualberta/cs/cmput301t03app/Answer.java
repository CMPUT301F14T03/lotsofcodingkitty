package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;
import java.util.Date;

public class Answer {
	ArrayList<Comment> comments;
	String answer;
	String author;
	Date date;
	int rating;
	
	public Answer(String answer, Date date){
		this.date = date;
		this.answer = answer;
		this.rating = 0;
	}
	
	public Answer()
	{
		this.author = null;
		this.answer = null;
		this.rating = 0;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}
	public void addComments(Comment comment) {
		getComments().add(comment);
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
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
}
