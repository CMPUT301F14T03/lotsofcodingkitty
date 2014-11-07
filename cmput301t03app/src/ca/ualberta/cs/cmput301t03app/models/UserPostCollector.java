package ca.ualberta.cs.cmput301t03app.models;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.datamanagers.LocalDataManager;

import android.content.Context;

/**
 * A model that contains all user specific lists containing
 * ID's and a question bank containing a list of Question objects.
 * All lists are populated by loading from LocalDataManager.
 * 
 */
public class UserPostCollector {
	private ArrayList<Question> questionBank;
	private ArrayList<String> favoriteQuestions;
	private ArrayList<String> readQuestions;
	private ArrayList<String> toReadQuestions;
	private ArrayList<String> postedQuestions;
	private LocalDataManager local;
//	private ArrayList<String> postedAnswers;

//	private Context context;


	public UserPostCollector() {
		questionBank = null;
		favoriteQuestions = null;
		readQuestions = null;
		toReadQuestions = null;
		postedQuestions = null;
//		postedAnswers = null;
	}
	
	/*---------------------------Initializers-----------------------------*/
	
	/**
	 * This method initializes the favorites id list
	 * @param context Activity Context
	 */
	public void initFavoriteID(Context context) {
		if (this.favoriteQuestions == null) {
			local = new LocalDataManager(context);
			this.favoriteQuestions = local.loadFavorites();
		}
	}
	
	/**
	 * This method initializes the read id list
	 * @param context Activity Context
	 */
	public void initReadID(Context context) {
		if (this.readQuestions == null) {
			local = new LocalDataManager(context);
			this.readQuestions = local.loadRead();
		}
	}
	
	/**
	 * This method initializes the to read id list
	 * @param context Activity Context
	 */
	public void initToReadID(Context context) {
		if (this.toReadQuestions == null) {
			local = new LocalDataManager(context);
			this.toReadQuestions = local.loadRead();
		}
	}

	/**
	 * This method initializes the posted questions id list
	 * @return context Activity Context
	 */
	public void initPostedQuestionID(Context context) {
		if (this.postedQuestions == null) {
			local = new LocalDataManager(context);
			this.postedQuestions = local.loadPostedQuestions();
		}
	}
	
	/**
	 * This method initializes the question bank
	 * @param context Activity Context
	 */
	public void initQuestionBank(Context context) {
		if (this.questionBank == null) {
			local = new LocalDataManager(context);
			this.questionBank = local.loadQuestions();
		}
	}
	

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

	/*--------------------------------------------------------*/

	/*=====================Adding=====================*/
	/**
	 * Adds a question object to the question bank
	 * @param question A question object
	 */
	public void addQuestionToBank(Question question) {
		questionBank.add(question);
	}
	
	/**
	 * Adds a string which represents an ID to the list
	 * @param qId A question ID
	 */
	public void addFavoriteQuestion(String qId) {
		favoriteQuestions.add(qId);
	}	

	/**
	 * Adds a string which represents an ID to the list
	 * @param qId A question ID
	 */
	public void addReadQuestion(String qId) {
		readQuestions.add(qId);
	}

	/**
	 * Adds a string which represents an ID to the list
	 * @param qId A question ID
	 */
	public void addtoReadQuestion(String qId) {
		toReadQuestions.add(qId);
	}

	/**
	 * Adds a string which represents an ID to the list
	 * @param qId A question ID
	 */
	public void addPostedQuestion(String qId) {
		postedQuestions.add(qId);
	}


	//This method is for testing since each test needs to start with a fresh list
	//TODO: Delete this later
	public void clearLists() {
		if (favoriteQuestions != null){
		favoriteQuestions.clear();
		}
		
		if (readQuestions != null) {
			readQuestions.clear();
		}
		if (toReadQuestions != null) {
			toReadQuestions.clear();
		}
		if (postedQuestions != null) {
			postedQuestions.clear();
		}
		if (questionBank != null) {
			questionBank.clear();
		}
	}
}

// This is a constructor that is used when the UPC is created using the LocalDataManagers loading methods
// Unsure about this constructor.....

//public UserPostCollector(ArrayList<String> favoriteQuestions, ArrayList<String> readQuestions,
//		ArrayList<String> toReadQuestions, ArrayList<String> postedQuestions, ArrayList<String> postedAnswers) {
//	this.favoriteQuestions = favoriteQuestions;
//	this.readQuestions = readQuestions;
//	this.toReadQuestions = toReadQuestions;
//	this.postedQuestions = postedQuestions;
//	this.postedAnswers = postedAnswers;
//}

/**
 * Adds a string which represents an ID to the list
 * @param qId A question ID
 */
//public void addUserAnswer(String qId) {
//	postedAnswers.add(qId);
//}

//public ArrayList<String> getPostedAnswers() {
//return this.postedAnswers;
//}