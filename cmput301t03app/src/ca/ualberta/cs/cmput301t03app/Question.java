package ca.ualberta.cs.cmput301t03app;


<<<<<<< HEAD
public class Question
{
	public String body=null;
	public int rating=0;
	public void Question(String string,int rating){
		setBody(string);
		setRating(rating);
	}
	
	public String getBody()
	{
	
		return body;
	}
	
	public void setBody(String body)
	{
	
		this.body = body;
	}
	
	public int getRating()
	{
	
		return rating;
	}
	
	public void setRating(int rating)
	{
	
		this.rating = rating;
	}
	
=======
import java.sql.Date;
import java.util.ArrayList;

public class Question {
	

	private String topicTitle;
	private String textbody;
	private  Date dateCreated;
	private ArrayList<Answer> ListofAnswers;
	private ArrayList<Comment> ListofComments;
	private int Rating;
	private int picture;
	
	public Question(Date date, String title, String question){
		topicTitle = title;
		dateCreated = date;
		textbody = question;
		ListofAnswers = new ArrayList<Answer>();
		ListofComments = new ArrayList<Comment>();
	}
	
	public String getTitle(){
		return topicTitle;
	}
	
	public Date getDate(){
		return dateCreated;
	}
	public void setgetTitle(String string){
		topicTitle = string;
	}
	
	public String getTextbody() {
		return textbody;
	}

	public void setTextbody(String textbody) {
		this.textbody = textbody;
	}

	public void setgetDate(Date date){
		dateCreated = date;
	}
	
	public ArrayList<Answer> getListofAnswers() {
		return ListofAnswers;
	}

	public void setListofAnswers(ArrayList<Answer> listofAnswers) {
		ListofAnswers = listofAnswers;
	}

	public ArrayList<Comment> getListofComments() {
		return ListofComments;
	}

	public void setListofComments(ArrayList<Comment> listofComments) {
		ListofComments = listofComments;
	}

	public int getRating() {
		return Rating;
	}

	public void setRating(int rating) {
		Rating = rating;
	}

	public int getPicture() {
		return picture;
	}

	public void setPicture(int picture) {
		this.picture = picture;
	}
	
	
	

>>>>>>> c878cfaf0dac4cc9adca39222fb534fb23f1d4cb
}
