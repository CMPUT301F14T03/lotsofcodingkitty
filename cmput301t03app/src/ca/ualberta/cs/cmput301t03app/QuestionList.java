package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;


public class QuestionList
{
	ArrayList<Question> questionList;

	
	public ArrayList<Question> getQuestionList() {
		
		if (questionList == null) {
			questionList=new ArrayList<Question>();
		}
		return questionList;
	}
	public int listSize(){
		
		return getQuestionList().size();
	}
	
	public void addQuestion(Question question) {
	
		getQuestionList().add(question);
	}
	
	 public ArrayList<Question> upvoteList() {
		 
		 ArrayList<Question> upvoteList= new ArrayList<Question>();
		 for (int i=0; i<listSize(); i++) {}
		return upvoteList;
	 }
}
