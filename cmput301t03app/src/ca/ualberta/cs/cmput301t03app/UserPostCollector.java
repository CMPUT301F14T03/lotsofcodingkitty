package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

public class UserPostCollector {
	private ArrayList<Question> favoriteQuestions;
	private ArrayList<Question> readQuestions;
	private ArrayList<Question> toReadQuestions;
	private ArrayList<Question> postedQuestions;
	private ArrayList<Answer> postedAnswers;
	
//	private Context context;
//	private LocalDataManager localDataManager;
		
	public UserPostCollector() {
		favoriteQuestions = new ArrayList<Question>();
		readQuestions = new ArrayList<Question>();
		toReadQuestions = new ArrayList<Question>();
		postedQuestions = new ArrayList<Question>();
		postedAnswers = new ArrayList<Answer>();
	}
	
	// This is a constructor that is used when the UPC is created using the LocalDataManagers loading methods
	
	public UserPostCollector(ArrayList<Question> favoriteQuestions, ArrayList<Question> readQuestions,
				ArrayList<Question> toReadQuestions, ArrayList<Question> postedQuestions, ArrayList<Answer> postedAnswers) {
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
	public ArrayList<Question> getFavoriteQuestions() {
		return favoriteQuestions;
	}

	public ArrayList<Question> getReadQuestions() {
		return readQuestions;
	}
	
	public ArrayList<Question> getToReadQuestions() {
		return toReadQuestions;
	}
	
	public ArrayList<Question> getPostedQuestions() {
		return postedQuestions;
	}
	
	public ArrayList<Answer> getPostedAnswers() {
		return postedAnswers;
	}
	
	/*--------------------------------------------------------*/
	/* Not sure if we need adders anymore Carly since we're appending one object at a time.
	/*=====================Adding=====================*/
	public void addFavoriteQuestions(Question question) {
		favoriteQuestions.add(question);
	}
	
	public void addReadQuestions(Question question) {
		readQuestions.add(question);
	}
	
	public void addtoReadQuestions(Question question) {
		toReadQuestions.add(question);
	}
	
	public void addPostedQuestion(Question question) {
		postedQuestions.add(question);
	}
	
	public void addUserAnswer(Answer answer) {
		postedAnswers.add(answer);
	}
	
}