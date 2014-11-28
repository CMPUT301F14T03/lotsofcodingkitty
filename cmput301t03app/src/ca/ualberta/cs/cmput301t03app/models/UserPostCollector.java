package ca.ualberta.cs.cmput301t03app.models;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.datamanagers.LocalDataManager;

import android.content.Context;

/**
 * A class that contains all user specific Question lists.
 * Including the user favorited questions, the previously read questions, the 
 * questions marked "To-be-Read-Later" and the questions posted by the user.
 * 
 */
public class UserPostCollector {
	private ArrayList<Question> questionBank;
	private ArrayList<String> favoriteQuestions;
	private ArrayList<String> readQuestions;
	private ArrayList<String> toReadQuestions;
	private ArrayList<String> postedQuestions;
	private ArrayList<String> pushQuestions;
	private ArrayList<Tuple> pushAnswersAndComments;
	private LocalDataManager local;
//	private ArrayList<String> postedAnswers;

//	private Context context;

	/** Constructs a {@link #userPostCollector() userPostCollector}.
	 * @param subject			The subject that the question is asking about.
	 * @param body				The body of the question or description.
	 * @param author			The author of the question.
	 */
	public UserPostCollector() {
		questionBank = null;
		favoriteQuestions = null;
		readQuestions = null;
		toReadQuestions = null;
		postedQuestions = null;
		pushAnswersAndComments = null;
	}
	
	/*###########################-----Initializers----##############################*/
	
	/**
	 * Initializes the favorites list
	 * @param context The Activity Context
	 */
	public void initFavoriteID(Context context) {
		if (this.favoriteQuestions == null) {
			local = new LocalDataManager(context);
			this.favoriteQuestions = local.loadFavorites();
		}
	}
	
	/**
	 * Initializes the previously read list
	 * @param context The Activity Context
	 */
	public void initReadID(Context context) {
		if (this.readQuestions == null) {
			local = new LocalDataManager(context);
			this.readQuestions = local.loadRead();
		}
	}	
	
	/**
	 * Initializes the to read id list
	 * @param context The Activity Context
	 */
	public void initToReadID(Context context) {
		if (this.toReadQuestions == null) {
			local = new LocalDataManager(context);
			this.toReadQuestions = local.loadToRead();
		}
	}

	/**
	 * Initializes the posted questions id list
	 * @return context The Activity Context
	 */
	public void initPostedQuestionID(Context context) {
		if (this.postedQuestions == null) {
			local = new LocalDataManager(context);
			this.postedQuestions = local.loadPostedQuestions();
		}
	}
	/**
	 * Initializes the list of questions id's of questions that need to be pushed
	 * @param context The Activity context
	 */
	public void initPushQuestionID(Context context) {
		if (this.pushQuestions == null) {
			local = new LocalDataManager(context);
			this.pushQuestions = local.loadPostedQuestions();
		}
	}
	/**
	 * Initializes the list of tuples that contains the question ID, answer ID and comment object that is 
	 * required for pushing answers and comments made offline to the server
	 * @param context The Activity context
	 */
	public void initPushAnsCommTuple(Context context) {
		if (this.pushAnswersAndComments == null) {
			local = new LocalDataManager(context);
			this.pushAnswersAndComments = local.loadTupleArray();
		}
	}
	
	
	/**
	 * Initializes the question bank
	 * @param context The Activity Context
	 */
	public void initQuestionBank(Context context) {
		if (this.questionBank == null) {
			local = new LocalDataManager(context);
			this.questionBank = local.loadQuestions();
		}
	}
	

	/*############################----GETTERS----################################*/
	
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
	
	public ArrayList<String> getPushQuestions() {
		return this.pushQuestions;
	}
	
	public ArrayList<Tuple> getPushAnswersAndComments() {
		return this.pushAnswersAndComments;
	}

	public ArrayList<String> getPostedQuestions() {
		return this.postedQuestions;
	}


	/*########################----ADDING METHODS----###########################/
	/**
	 * Adds a question object to the question bank
	 * @param A question object to be added
	 */
	public void addQuestionToBank(Question question) {
		questionBank.add(question);
	}
	
	/**
	 * Adds a string which represents an ID to the favorite list
	 * @param A question ID to be added
	 */
	public void addFavoriteQuestion(String qId) {
		favoriteQuestions.add(qId);
	}	

	/**
	 * Adds a string which represents an ID to the previously read list
	 * @param A question ID to be added 
	 */
	public void addReadQuestion(String qId) {
		readQuestions.add(qId);
	}

	/**
	 * Adds a string which represents an ID to the to-read list
	 * @param A question ID to be added 
	 */
	public void addtoReadQuestion(String qId) {
		toReadQuestions.add(qId);
	}
	
	public void addToPushQuestions(String qID) {
		pushQuestions.add(qID);
	}

	/**
	 * Adds a string which represents an ID to the user posted question list
	 * @param A question ID to be added
	 */
	public void addPostedQuestion(String qId) {
		postedQuestions.add(qId);
	}

	//This method is for testing since each test needs to start with a fresh list
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

	public void clearPushQuestions() {
		this.pushQuestions.clear();
		
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