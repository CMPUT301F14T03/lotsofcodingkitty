package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

public interface iDataManager {


	void saveQuestions(ArrayList<Question> list);
	ArrayList<Question> loadQuestions();
//	void saveAnswers(ArrayList<Answer> list);
//	ArrayList<Answer> loadAnswers();

}