package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

/**
 * An interface for DataManagers. All DataManagers should be 
 * able to save and load the whole Question list.
 * @since 2014-10-28
 */
public interface iDataManager {
	
	public void saveToQuestionBank(ArrayList<Question> list);
	public ArrayList<Question> loadQuestions();
}