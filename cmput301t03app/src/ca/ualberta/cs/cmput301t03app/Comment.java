package ca.ualberta.cs.cmput301t03app;

import java.sql.Date;

public class Comment {
	/*
	 * This is the comment class. It allows users to set the text of a comment*/
		
	public Date dateCreated;
	public String textBody;
	
	
	public void setTextBody(String comment) {
		textBody = comment;
			
	}
}