package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

public interface iDataManager {

	public void saveQuestion(Question q);
	public ArrayList<Question> loadQuestions();
	public void saveAnswers(ArrayList<Answer> list);
	public void saveAnswer(Answer a);
//	void saveAnswers(ArrayList<Answer> list);
//	ArrayList<Answer> loadAnswers();
	void save(ArrayList<Question> list);
	ArrayList<Question> load();
	void saveAnswerList(ArrayList<Answer> list);

}