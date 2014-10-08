package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

/*This class represents the server in which questions and answers will be saved to*/
public class ServerDatabase {

	public ServerDatabase() {	
	}
	
	public void saveQuestionList() {
		
	}
	
	public ArrayList<Question> loadQuestionList() {
		ArrayList<Question> questionArray = new ArrayList<Question>();
		//Load from server
		return questionArray;
	}
	
	//TODO:Search through list of answers
	public ArrayList<Question> search(String searchWord) {
		
		ArrayList<Question> questionArray = new ArrayList<Question>();
		ArrayList<Question> searchResultsArray = new ArrayList<Question>();
		questionArray = loadQuestionList(); //Load list of questions from server
		
		//Add all questions that contain searchWord in the question title.
		for (Question question : questionArray) {
			
			String questionTitle = question.getTitle().toLowerCase();
			if (questionTitle.contains(searchWord.toLowerCase())) {
				
				searchResultsArray.add(question);
			}
		}
		return searchResultsArray;
	}
}
