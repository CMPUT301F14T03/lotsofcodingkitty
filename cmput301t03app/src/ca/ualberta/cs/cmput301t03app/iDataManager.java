package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

public interface iDataManager {


	void save(ArrayList<Question> list);
	ArrayList<Question> load();
//	void saveAnswers(ArrayList<Answer> list);
//	ArrayList<Answer> loadAnswers();

}