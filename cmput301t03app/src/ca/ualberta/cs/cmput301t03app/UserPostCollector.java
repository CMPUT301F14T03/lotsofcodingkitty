package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

import android.content.Context;

public class UserPostCollector {
	private static ArrayList<Question> favoriteQuestions = new ArrayList<Question>();
	private static ArrayList<Question> readQuestions;
	private static ArrayList<Question> toReadQuestions;
	private static ArrayList<Question> postedQuestions;
	private static ArrayList<Answer> postedAnswers;
	
	private Context context;
	private LocalDataManager localDataManager = new LocalDataManager(context);
		
	public UserPostCollector(Context context) {
		this.context = context;
	}
	
	
	/*=====================Getters=====================*/
	public ArrayList<Question> getFavoriteQuestions() {
		populateList(favoriteQuestions, LocalDataManager.FAVORITE);	
		return favoriteQuestions;
	}

	public ArrayList<Question> getReadQuestions() {
		populateList(readQuestions, LocalDataManager.READ);
		return readQuestions;
	}
	
	public ArrayList<Question> getToReadQuestions() {
		populateList(toReadQuestions, LocalDataManager.TO_READ);
		return toReadQuestions;
	}
	
	public ArrayList<Question> getPostedQuestions() {
		return postedQuestions;
	}
	
	public ArrayList<Answer> getPostedAnswers() {
		return postedAnswers;
	}
	
	/*--------------------------------------------------------*/
	
	/*=====================Adding=====================*/
	public void addFavoriteQuestions(Question question) {
//		getFavoriteQuestions().add(question);
		favoriteQuestions.add(question); //For testing
		localDataManager.save(favoriteQuestions);		
	}
	
	public void addReadQuestions(Question question) {
		getReadQuestions().add(question);
		localDataManager.save(readQuestions);
	}
	
	public void addtoReadQuestions(Question question) {
		getToReadQuestions().add(question);
		localDataManager.save(toReadQuestions);
	}
	
	public void addPostedQuestion(Question question) {
		getPostedQuestions().add(question);
	}
	
	public void addUserAnswer(Answer answer) {
		getPostedAnswers().add(answer);
	}
	
	//Method used by Getters to populate a given list
	private void populateList(ArrayList<Question> cacheList, int mode) {
		ArrayList<Question> tempList = new ArrayList<Question>();
		localDataManager.setMode(mode);
		tempList = localDataManager.load();
		cacheList.clear();
		
		for (int i = 0; i < tempList.size(); i++) {
			cacheList.add(tempList.get(i));
		}
	}
}