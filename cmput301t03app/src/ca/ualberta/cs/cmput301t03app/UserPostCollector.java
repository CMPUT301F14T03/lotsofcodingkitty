package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

import android.content.Context;

public class UserPostCollector {
	private ArrayList<Question> favoriteQuestions;
	private ArrayList<Question> readQuestions;
	private ArrayList<Question> toReadQuestions;
	private ArrayList<Question> postedQuestions;
	private ArrayList<Answer> postedAnswers;
	
	private Context context;
	private LocalDataManager localDataManager;
		
	public UserPostCollector(Context context) {
		this.context = context;
		localDataManager = new LocalDataManager(context);
		favoriteQuestions = new ArrayList<Question>();
		readQuestions = new ArrayList<Question>();
		toReadQuestions = new ArrayList<Question>();
		postedQuestions = new ArrayList<Question>();
		postedAnswers = new ArrayList<Answer>();
	}
	
	
	/*=====================Getters=====================*/
	public ArrayList<Question> getFavoriteQuestions() {
		favoriteQuestions = localDataManager.loadFavorites();	
		return favoriteQuestions;
	}

	public ArrayList<Question> getReadQuestions() {
		readQuestions = localDataManager.loadRead();
		return readQuestions;
	}
	
	public ArrayList<Question> getToReadQuestions() {
		toReadQuestions = localDataManager.loadToRead();
		return toReadQuestions;
	}
	
	public ArrayList<Question> getPostedQuestions() {
		toReadQuestions = localDataManager.loadPostedQuestions();
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
		localDataManager.saveFavorites(favoriteQuestions);		
	}
	
	public void addReadQuestions(Question question) {
		getReadQuestions().add(question);
		localDataManager.saveRead(readQuestions);
	}
	
	public void addtoReadQuestions(Question question) {
		getToReadQuestions().add(question);
		localDataManager.saveToRead(toReadQuestions);
	}
	
	public void addPostedQuestion(Question question) {
		getPostedQuestions().add(question);
		localDataManager.savePostedQuestions(toReadQuestions);
	}
	
	public void addUserAnswer(Answer answer) {
		getPostedAnswers().add(answer);
	}
	
}