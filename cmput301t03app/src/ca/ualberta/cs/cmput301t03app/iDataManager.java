package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

public interface iDataManager {

	public void appendToQuestionBank(Question q);
	public void saveToQuestionBank(ArrayList<Question> list);
	public ArrayList<Question> loadQuestions();
	
	public void saveAnswer(Answer a);
	public void saveAnswers(ArrayList<Answer> list);
	
//	void saveAnswers(ArrayList<Answer> list);
//	ArrayList<Answer> loadAnswers();

}