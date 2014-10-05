package ca.ualberta.cs.cmput301t03app;


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
	
}
