package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;
import java.util.Date;

public class Answer {
	ArrayList<Comment> comments;
	String answer;
	String author;
	Date date;
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
	
	
}
