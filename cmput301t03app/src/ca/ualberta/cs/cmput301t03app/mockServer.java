/**
 * THIS IS FOR TESTING PURPOSES ONLY!!!
 * 
 * This class is used to mimic the server until we get the server up and running.  The purpose is to
 * generate a list of questions, answers and comments so that we can test loading, saving, getters, etc.
 * 
 * For now, please test populating the lists into the views with the data that is created within this
 * class.
 * 
 */
package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

public class mockServer {

	private static ArrayList<Question> mockQuestionList;
	
	public mockServer() {
		
	}
	
	public void initServer() {
		
	}
	
	public void populateQuestions() {
		Question q1 = new Question("Is this a mock server?", "This server isn't real", "Dan");
		Question q2 = new Question("What is this?", "I don't understand the purpose of this class", "Guss");
		Question q3 = new Question("Is this a free app?", "Do I need to pay to use this app or is it for free?", "Peter");
		
	}
}
