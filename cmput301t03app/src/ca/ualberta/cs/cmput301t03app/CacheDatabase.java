package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class CacheDatabase {
	
	private void saveData(JSONArray list) {
		
	}
	
	private JSONArray loadData() {
		return postJsonArray;
		
	}
	
	public void saveQuestionList(List<Question> questionArray) {
		
		//Convert questionArray to JSONArray		
		saveData(questionJsonArray);
	}
	
	public List<Question> loadQuestionList() {
		
		questionJsonArray = loadData();
		List<Question> questionArray = new ArrayList<Question>();
		//Parse JSONArray into ArrayList
		return questionArray;
	}
	
	public void saveAnswerList(List<Question> answerArray) {
		
		saveData();
	}
	
	public List<Answer> loadAnswerList() {
		
		answerJsonArray = loadData();
		//Parse JSONArray into ArrayList
		return answerJsonArray;
	}
	
	
}
