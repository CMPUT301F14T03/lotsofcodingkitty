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
<<<<<<< HEAD
	
=======

>>>>>>> upstream/master
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
<<<<<<< HEAD
	// 
	// Unsure about this constructor.....
=======
>>>>>>> upstream/master
	public UserPostCollector(ArrayList<String> favoriteQuestions, ArrayList<String> readQuestions,
			ArrayList<String> toReadQuestions, ArrayList<String> postedQuestions, ArrayList<String> postedAnswers) {
		this.favoriteQuestions = favoriteQuestions;
		this.readQuestions = readQuestions;
		this.toReadQuestions = toReadQuestions;
		this.postedQuestions = postedQuestions;
		this.postedAnswers = postedAnswers;
	}
	
<<<<<<< HEAD
	/**
	 * This method initializes the favorites id list
	 * @param context
	 */
	public void initFavoriteID(Context context) {
		if (favoriteQuestions == null) {
			LocalDataManager local = new LocalDataManager(context);
			favoriteQuestions = local.loadFavorites();
		}
	}
	
	/**
	 * This method initializes the read id list
	 * @param context
	 */
	public void initReadID(Context context) {
		if (readQuestions == null) {
			LocalDataManager local = new LocalDataManager(context);
			readQuestions = local.loadRead();
		}
	}
	
	/**
	 * This method initializes the to read id list
	 * @param context
	 */
	public void initToReadID(Context context) {
		if (toReadQuestions == null) {
			LocalDataManager local = new LocalDataManager(context);
			toReadQuestions = local.loadRead();
		}
	}

	/**
	 * This method initializes the posted questions id list
	 * @return context
	 */
	public void initPostedQuestionID(Context context) {
		if (postedQuestions == null) {
			LocalDataManager local = new LocalDataManager(context);
			postedQuestions = local.loadPostedQuestions();
		}
	}
	// I changed a lot of these because it didn't really seem MVC for the model
	// to be calling the dataManager (which is what our post controller should know about)
	// to populate its own lists.
	//
	// The model is now populated by the PostController calling the LocalDataManager.

=======
>>>>>>> upstream/master
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
<<<<<<< HEAD
	}
=======

	}	
>>>>>>> upstream/master

	public ArrayList<String> getPostedQuestions() {
		return postedQuestions;
<<<<<<< HEAD
	}
=======

	}	
>>>>>>> upstream/master

	public ArrayList<String> getPostedAnswers() {
		return postedAnswers;
<<<<<<< HEAD
	}
=======

	}	
>>>>>>> upstream/master

	/*--------------------------------------------------------*/

	/*=====================Adding=====================*/
	public void addQuestionToBank(Question question) {
		questionBank.add(question);
	}
	
	public void addFavoriteQuestion(String qId) {
		favoriteQuestions.add(qId);
<<<<<<< HEAD
	}
=======

	}	
>>>>>>> upstream/master

	public void addReadQuestion(String qId) {
		readQuestions.add(qId);
	}

	public void addtoReadQuestion(String qId) {
		toReadQuestions.add(qId);
	}

	public void addPostedQuestion(String qId) {
		postedQuestions.add(qId);
<<<<<<< HEAD
	}
=======

	}	
>>>>>>> upstream/master

	public void addUserAnswer(String qId) {
		postedAnswers.add(qId);
	}
}

