package ca.ualberta.cs.cmput301t03app;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserPostCollector {
	private ArrayList<Question> questionBank;
	private ArrayList<String> favoriteQuestions;
	private ArrayList<String> readQuestions;
	private ArrayList<String> toReadQuestions;
	private ArrayList<String> postedQuestions;
	private ArrayList<String> postedAnswers;
	
//	private Context context;
//	private LocalDataManager localDataManager;
		
	public UserPostCollector() {
		questionBank = new ArrayList<Question>();
		favoriteQuestions = new ArrayList<String>();
		readQuestions = new ArrayList<String>();
		toReadQuestions = new ArrayList<String>();
		postedQuestions = new ArrayList<String>();
		postedAnswers = new ArrayList<String>();
	}
	
	// This is a constructor that is used when the UPC is created using the LocalDataManagers loading methods
	
	public UserPostCollector(ArrayList<String> favoriteQuestions, ArrayList<String> readQuestions,
				ArrayList<String> toReadQuestions, ArrayList<String> postedQuestions, ArrayList<String> postedAnswers) {
		this.favoriteQuestions = favoriteQuestions;
		this.readQuestions = readQuestions;
		this.toReadQuestions = toReadQuestions;
		this.postedQuestions = postedQuestions;
		this.postedAnswers = postedAnswers;
	}
	
	// I changed a lot of these because it didn't really seem MVC for the model
	// to be calling the dataManager (which is what our post controller should know about)
	// to populate its own lists.
	//
	// The model is now populated by the PostController calling the LocalDataManager.
	
	
	/*=====================Getters=====================*/
	public ArrayList<Question> getQuestionBank() {
		return questionBank;
	}
	public ArrayList<String> getFavoriteQuestions() {
		return favoriteQuestions;
	}

	public ArrayList<String> getReadQuestions() {
		return readQuestions;
	}
	
	public ArrayList<String> getToReadQuestions() {
		return toReadQuestions;
	}
	
	public ArrayList<String> getPostedQuestions() {
		return postedQuestions;
	}
	
	public ArrayList<String> getPostedAnswers() {
		return postedAnswers;
	}
	
	/*--------------------------------------------------------*/
	
	/*=====================Adding=====================*/
	public void addQuestionToBank(Question question) {
		questionBank.add(question);
	}
	public void addFavoriteQuestion(String qId) {
		favoriteQuestions.add(qId);
	}
	
	public void addReadQuestion(String qId) {
		readQuestions.add(qId);
	}
	
	public void addtoReadQuestion(String qId) {
		toReadQuestions.add(qId);
	}
	
	public void addPostedQuestion(String qId) {
		postedQuestions.add(qId);
	}
	
	public void addUserAnswer(String qId) {
		postedAnswers.add(qId);
	}
	
}