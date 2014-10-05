package ca.ualberta.cs.cmput301t03app;

import java.util.List;

import org.json.JSONArray;

public class CacheDatabase {
	
	private void saveData(JSONArray list) {
		
	}
	
	private JSONArray loadData() {
		
	}
	
	public void saveQuestionList(List<Question>) {
		
		saveData();
	}
	
	public List<Question> loadQuestionList() {
		
		questionJsonArray = loadData();
		//Parse JSONArray into ArrayList
		return questionJsonArray;
	}
	
	public void saveAnswerList(List<Question>) {
		
		saveData();
	}
	
	public List<Question> loadAnswerList() {
		
		questionJsonArray = loadData();
		//Parse JSONArray into ArrayList
		return questionJsonArray;
	}
	
	
}
