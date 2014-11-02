package ca.ualberta.cs.cmput301t03app;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.content.Context;

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
	//
	// Unsure about this constructor.....

	public UserPostCollector(ArrayList<String> favoriteQuestions, ArrayList<String> readQuestions,
			ArrayList<String> toReadQuestions, ArrayList<String> postedQuestions, ArrayList<String> postedAnswers) {
		this.favoriteQuestions = favoriteQuestions;
		this.readQuestions = readQuestions;
		this.toReadQuestions = toReadQuestions;
		this.postedQuestions = postedQuestions;
		this.postedAnswers = postedAnswers;
	}
	

	/**
	 * This method initializes the favorites id list
	 * @param context
	 */
	public void initFavoriteID(Context context) {
		if (this.favoriteQuestions == null) {
			LocalDataManager local = new LocalDataManager(context);
			this.favoriteQuestions = local.loadFavorites();
		}
	}
	
	/**
	 * This method initializes the read id list
	 * @param context
	 */
	public void initReadID(Context context) {
		if (this.readQuestions == null) {
			LocalDataManager local = new LocalDataManager(context);
			this.readQuestions = local.loadRead();
		}
	}
	
	/**
	 * This method initializes the to read id list
	 * @param context
	 */
	public void initToReadID(Context context) {
		if (this.toReadQuestions == null) {
			LocalDataManager local = new LocalDataManager(context);
			this.toReadQuestions = local.loadRead();
		}
	}

	/**
	 * This method initializes the posted questions id list
	 * @return context
	 */
	public void initPostedQuestionID(Context context) {
		if (this.postedQuestions == null) {
			LocalDataManager local = new LocalDataManager(context);
			this.postedQuestions = local.loadPostedQuestions();
		}
	}
	
	/**
	 * This method initializes the question bank
	 * @param context
	 */
	public void initQuestionBank(Context context) {
		if (this.questionBank == null) {
			LocalDataManager local = new LocalDataManager(context);
			this.questionBank = local.loadQuestions();
		}
	}
	
	// I changed a lot of these because it didn't really seem MVC for the model
	// to be calling the dataManager (which is what our post controller should know about)
	// to populate its own lists.
	//
	// The model is now populated by the PostController calling the LocalDataManager.

	/*=====================Getters=====================*/
	public ArrayList<Question> getQuestionBank() {
		return this.questionBank;
	}

	public ArrayList<String> getFavoriteQuestions() {
		return this.favoriteQuestions;
	}

	public ArrayList<String> getReadQuestions() {
		return this.readQuestions;
	}

	public ArrayList<String> getToReadQuestions() {
		return this.toReadQuestions;
	}

	public ArrayList<String> getPostedQuestions() {
		return this.postedQuestions;
	}

	public ArrayList<String> getPostedAnswers() {
		return this.postedAnswers;
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

	
	
	
	
	//This method is for testing since each test needs to start with a fresh list
	//TODO: Delete this later
	public void clearLists() {
		favoriteQuestions.clear();
		readQuestions.clear();
		toReadQuestions.clear();
		postedQuestions.clear();
		questionBank.clear();
	}
}

