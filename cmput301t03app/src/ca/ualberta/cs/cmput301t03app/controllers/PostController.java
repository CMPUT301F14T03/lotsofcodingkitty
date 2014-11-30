package ca.ualberta.cs.cmput301t03app.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import ca.ualberta.cs.cmput301t03app.datamanagers.LocalDataManager;
import ca.ualberta.cs.cmput301t03app.datamanagers.QuestionFilter;
import ca.ualberta.cs.cmput301t03app.datamanagers.ServerDataManager;
import ca.ualberta.cs.cmput301t03app.interfaces.IDataManager;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.GeoLocation;
//import ca.ualberta.cs.cmput301t03app.models.Post;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.models.UpvoteTuple;
import ca.ualberta.cs.cmput301t03app.models.UserPostCollector;
import ca.ualberta.cs.cmput301t03app.models.Tuple;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Responsible for all of the model manipulation in the program.
 * <p>
 * This class uses a UserPostCollector which contains all of the local data
 * information (including posted questions, favorites, to read, read, etc.). It
 * also uses a DataManager which is used for loading and saving.
 * 
 * 
 */
public class PostController {

	private static ArrayList<Question> subQuestions = null;
//	private static ArrayList<Post> pushPosts = null;
	private static HashMap<String, Integer> questionUpvotes = null;
	private static HashMap<String, UpvoteTuple> answerUpvotes = null;
	private static QuestionFilter qf = new QuestionFilter();
	private static UserPostCollector upc = new UserPostCollector();
	private static ServerDataManager sdm = new ServerDataManager();
	private static LocalDataManager ldm;
	private static int serverListIndex = 10;
	private static ArrayList<Question> serverList = new ArrayList<Question>();
	private Context context;

	/**
	 * 
	 * Constructs a {@link #PostController() PostController}
	 * 
	 * @param context
	 *            The context of the PostController
	 */

	public PostController(Context context) {

		this.context = context;
		this.ldm = new LocalDataManager(context);
	}


/*#############################----START OF SORTING METHODS-----###################################*/

	/**
	 * Sorts the sub-question list by presence of picture, placing the posts with pictures at the top
	 */
	public void sortQuestionsByPic() {
		qf.sortByPic(getQuestionsInstance());
	}

	/**
	 * Sort the sub-question list by rating, placing the most upvoted question at the top.
	 */
	public void sortQuestionsByUpvote() {
		qf.sortByUpvote(getQuestionsInstance());
	}

	/**
	 * Sorts the sub-questions list by date, placing the most recent one on top.
	 */
	public void sortQuestionsByDate() {
		qf.sortByDate(getQuestionsInstance());
	}
	
	/**
	 * Sorts the sub-question list by location, placing the closest location to the user at the top.
	 * @param location
	 */
	public void sortByLocation(GeoLocation location) {
		ArrayList<Question> filtered = qf.sortByLocation(getQuestionsInstance(), location);
		getQuestionsInstance().clear();
		getQuestionsInstance().addAll(filtered);
	}
	/*#############################----END OF SORTING METHODS-----###################################*/

	/**
	 * Upvote question method, pushes upvotes to server
	 * 
	 * @param questionId
	 */

	public void upvoteQuestion(String questionId) {
		getQuestion(questionId).upRating();
//		if (getQuestionUpvotes().containsKey(questionId)) {
//			int count = getQuestionUpvotes().get(questionId);
//			count++;
//			getQuestionUpvotes().put(questionId, count);
//		} else {
//			getQuestionUpvotes().put(questionId, 1);
//		}
//
//		// push all upvotes in question upvote hashtable
//
//		if (checkConnectivity()) {
//			pushQuestionUpvotes();
//		} else {
//			ldm = new LocalDataManager(getContext());
//			ldm.savePushQuestionUpvotes(questionUpvotes);
	}
	
	public void upvoteAnswer(String answerId, String questionId) {
		getAnswer(answerId, questionId).upRating();
	}

//	public void pushQuestionUpvotes() {
//
//		for (HashMap.Entry<String, Integer> entry : getQuestionUpvotes()
//				.entrySet()) {
//			sdm.pushQuestionUpvote(entry.getKey(), entry.getValue());
//		}
//		try {
//			Thread.currentThread().sleep(250);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		questionUpvotes.clear();
//		ldm.savePushQuestionUpvotes(questionUpvotes);
//	}

	public void loadToBePushed() {
		ldm = new LocalDataManager(getContext());
		questionUpvotes = ldm.loadQuestionUpvotes();
		answerUpvotes = ldm.loadAnswerUpvotes();
		// pushPosts = ldm.loadPosts();
	}

	
/*############################----START OF GEOLOCATION METHODS----##################################*/
	/**
	 * Takes in a geolocation and returns a city name if available
	 * 
	 * @param Geolocation
	 *            location
	 * @return a string with the city name
	 */
	public String getCity(GeoLocation location) {
		return location.getCityFromLoc(context);
	}

	public GeoLocation turnFromCity(String cityName) {

		Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
		List<Address> addresses;
		GeoLocation location = new GeoLocation();
		location.setCityName(cityName);
		try {
			addresses = gcd.getFromLocationName(cityName, 1);
			if (addresses.size() > 0) {
				location.setLatitude(addresses.get(0).getLatitude());
				location.setLongitude(addresses.get(0).getLongitude());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
	}

/*#################################------END OF GEOLOCATION METHODS----################################*/
	
	
/*################################-----START OF CHECKING METHODS----###################################*/
	/**
	 * Returns true if the application is connected to the internet
	 * 
	 * @return A boolean stating if connected to internet
	 */
	public Boolean checkConnectivity() {
		ConnectivityManager connMgr = (ConnectivityManager) getContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns true if a question is favorited by the user.
	 * 
	 * @param questionID
	 *            the question ID of the question being checked.
	 * 
	 * */
	public Boolean isQuestionInFavByID(String questionID) {

		upc.initFavoriteID(getContext());
		// Log.d("click", "Size of Fav array: "+
		// upc.getFavoriteQuestions().size());
		for (int i = 0; i < upc.getFavoriteQuestions().size(); i++) {
			if (upc.getFavoriteQuestions().get(i).equals(questionID))
				return true;
		}
		return false;
	}

	/**
	 * Returns true if a question is in the cache or has already been read by
	 * the user.
	 * 
	 * @param questionID
	 *            the question ID of the question being checked.
	 * 
	 * */
	public Boolean isQuestionInReadByID(String questionID) {

		for (int i = 0; i < upc.getFavoriteQuestions().size(); i++) {
			if (upc.getFavoriteQuestions().get(i).equals(questionID))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * Saves a question to local memory given that, that question does not exist
	 * in local memory.
	 * 
	 * @param q
	 *            The question to be saved.
	 * @param local
	 *            The datamanager that saves to local memory
	 */

	private void checkExistanceOfQuestion(Question q, LocalDataManager local) {

		boolean found = false;
		for (int i = 0; i < upc.getQuestionBank().size(); i++) {
			if (upc.getQuestionBank().get(i).getId().equals(q.getId())) {
				found = true;
				break;
			}
		}
		if (!found) {
			ArrayList<Question> questionList = upc.getQuestionBank();
			questionList.add(q);
			local.saveToQuestionBank(questionList);
		}
	}
	
/*#############################-------END OF CHECKING METHODS----##################################*/
	
	
/*############################-------START OF LOCAL DB METHODS-----####################################*/

	/**
	 * This method replaces the old question with the updated question in the
	 * question bank and saves to local storage.
	 * 
	 * @param qId
	 *            A String representing a Question ID
	 */
	public void updateQuestionInBank(String qId) {

		Question q = getQuestion(qId);
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<Question> questionArray = upc.getQuestionBank();

		// Replace the old question with the updated question at the specified
		// index
		for (int i = 0; i < questionArray.size(); i++) {
			if (q.getId().equals(questionArray.get(i).getId())) {
				questionArray.set(i, q);
			}
		}

		local.saveToQuestionBank(questionArray);
	}

	/**
	 * Adds a question to the favorite questions list and saves it locally.
	 * 
	 * @param q
	 *            the Question object to be saved.
	 */
	public void addFavoriteQuestion(Question q) {

		upc.initFavoriteID(getContext());
		upc.initQuestionBank(getContext());
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idList = upc.getFavoriteQuestions();
		String id = q.getId();

		if (!idList.contains(id)) {
			idList.add(id);
			local.saveFavoritesID(idList);
			checkExistanceOfQuestion(q, local);
		}
	}

	/**
	 * Adds a question to the read questions list and saves it locally
	 * 
	 * @param q
	 *            the previously read question that must be saved.
	 */
	public void addReadQuestion(Question q) {

		upc.initReadID(getContext()); // I need to load the lists if they
										// haven't been loaded yet.
		upc.initQuestionBank(getContext());
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idList = upc.getReadQuestions();
		String id = q.getId();

		if (!idList.contains(id)) {
			idList.add(id);
			local.saveReadID(idList);
			checkExistanceOfQuestion(q, local);
		}
	}

	/**
	 * Adds a question to the "to read" list and saves it locally
	 * 
	 * @param q
	 *            the question object that the user wants to read later.
	 */
	public void addToRead(Question q) {

		upc.initToReadID(getContext()); // I need to load the lists if they
										// haven't been loaded yet.
		upc.initQuestionBank(getContext());
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idList = upc.getToReadQuestions();
		String id = q.getId();

		if (!idList.contains(id)) {
			idList.add(id);
			local.saveToReadID(idList);
			checkExistanceOfQuestion(q, local);
		}
	}

	/**
	 * Adds a question to the posted questions list and saves it locally
	 * 
	 * @param q
	 *            a Question object that the user wants to post
	 */
	public void addUserPost(Question q) {

		upc.initPostedQuestionID(getContext()); // I need to load the lists if
												// they haven't been loaded yet.
		upc.initQuestionBank(getContext());
		LocalDataManager local = new LocalDataManager(getContext());

		ArrayList<String> idList = upc.getPostedQuestions();
		ArrayList<Question> qList = upc.getQuestionBank();

		String id = q.getId();
		idList.add(id);
		qList.add(q);
		local.savePostedQuestionsID(idList);
		local.saveToQuestionBank(qList);
		upc.initQuestionBank(getContext()); //Need to refresh questionBank list in upc
	}
	
	/**
	 * Adds a question to the list of questions made while offline.
	 * @param q A question made offline.
	 */
	public void addPushQuestion(Question q) {
		upc.initPushQuestionID(getContext());
		upc.initQuestionBank(getContext());
		LocalDataManager local = new LocalDataManager(getContext());
		Toast.makeText(
				this.getContext(),
				"You are offline. Push your posts to the server when you regain connectivity with the 'sync' button",
				Toast.LENGTH_LONG).show();
		Log.d("Debug", "Question was asked offline");
		ArrayList<String> idList = upc.getPushQuestions();
		ArrayList<Question> qList = upc.getQuestionBank();

		String id = q.getId();
		idList.add(id);
		qList.add(q);
		local.savePushQuestionsID(idList);
		local.saveToQuestionBank(qList);
	}
	
	/**
	 * Adds an Answer object to the question object
	 * 
	 * @param answer
	 *            the answer object that the user has made
	 * @param questionID
	 *            The ID of the question that the answer will be added to
	 */
	public void addAnswer(Answer answer, String questionID) {

		getQuestion(questionID).addAnswer(answer);
//		getPushPostsInstance()
//				.add(new Post(answer, questionID, Question.class));
//		pushNewPosts();
		updateQuestionInBank(questionID);
	}

	/**
	 * Adds a comment object to a question object.
	 * 
	 * @param comment
	 *            A comment object that the user wants to be added to a
	 *            question.
	 * @param parentId
	 *            The ID of the question that the comment will be added to
	 */
	public void addCommentToQuestion(Comment comment, String questionId) {
		for (int i = 0; i < subQuestions.size(); i++) {
			if (subQuestions.get(i).getId().equals(questionId)) {
				subQuestions.get(i).addComment(comment);
//				getPushPostsInstance().add(
//						new Post(comment, parentId, Question.class));
			}
		}
		// TODO: pushNewPosts();
		updateQuestionInBank(questionId);

	}
	

	/**
	 * Adds a comment object to an answer object.
	 * 
	 * @param comment
	 *            A comment object that will be added to the answer
	 * @param answerID
	 *            The ID of the answer that the comment will be added to
	 * @param questionID
	 *            the question that the answer is pertaining to.
	 */
	public void addCommentToAnswer(Comment comment, String questionID,
			String answerID) {

		Question q = getQuestion(questionID);
		ArrayList<Answer> a = q.getAnswers();
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i).getId().equals(answerID)) {
				a.get(i).addComment(comment);
//				getPushPostsInstance().add(
//						new Post(comment, answerID, questionID, Answer.class));
			}
		}
		// TODO: pushNewPosts();
		updateQuestionInBank(questionID);
	}
	
	/**
	 * Returns the list of favorited questions.
	 * <p>
	 * This method only pulls from the question bank of favorite IDs list .
	 * 
	 * @return A list of the users favorited questions.
	 */
	public ArrayList<Question> getFavoriteQuestions() {

		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idArray = local.loadFavorites();
		ArrayList<Question> favoriteArray = getQuestionsFromID(idArray, local);
		return favoriteArray;
	}

	/**
	 * Returns the list of read questions.
	 * <p>
	 * This method only pulls from the question bank of read IDs list .
	 * 
	 * @return A list of the user's previously read Questions.
	 */
	public ArrayList<Question> getReadQuestions() {

		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idArray = local.loadRead();
		ArrayList<Question> readArray = getQuestionsFromID(idArray, local);
		return readArray;
	}

	/**
	 * Returns the list of "To-read" questions.
	 * <p>
	 * This method only pulls from the question bank of "to-read" IDs list .
	 * 
	 * @return A list of questions that the user has marked "To-Read".
	 */
	public ArrayList<Question> getToReadQuestions() {

		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idArray = local.loadToRead();
		ArrayList<Question> toReadArray = getQuestionsFromID(idArray, local);
		return toReadArray;
	}

	/**
	 * Returns the list of posted questions. This method only pulls from the
	 * question bank of posted questions IDs list .
	 * 
	 * @return A list of questions that the user has posted.
	 */
	public ArrayList<Question> getUserPostedQuestions() {

		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idArray = local.loadPostedQuestions();
		ArrayList<Question> postedArray = getQuestionsFromID(idArray, local);
		return postedArray;
	}
	
	public Question getQuestionFromLocalSave(String qID) {
		
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<Question> questionBank = local.loadQuestions();
		for (int i = 0; i < questionBank.size(); i++) {
			if (questionBank.get(i).getId().equals(qID)) {
				return questionBank.get(i);
			}
		}
		return null;
	}
/*##################################------END OF LOCAL DB METHODS------#####################################*/	
	
	/**
	 * Adds and saves the question ID, answer ID and comment object needed to push offline posts to the server
	 * when the user re-syncs
	 * @param qID The ID of the question corresponding to the question object
	 * @param aID The ID of the answer corresponding to the answer object
	 * @param comment The comment object
	 */
	public void addPushAnsAndComm(String qID, String aID, Comment comment) {
		upc.initPushAnsCommTuple(getContext());

		LocalDataManager local = new LocalDataManager(getContext());
		Toast.makeText(
				this.getContext(),
				"You are offline. Push your posts to the server when you regain connectivity with the 'sync' button",
				Toast.LENGTH_LONG).show();
		
		ArrayList<Tuple> tupleList = upc.getPushAnswersAndComments();
		Tuple tuple = new Tuple(qID, aID, comment);
		tupleList.add(tuple);
		local.savePushAnsAndComm(tupleList);
	}

	
	/**
	 * Parses the sub-question list of the PC to load the next 10 questions in the list
	 * 
	 * @param list The sub-question list to be parsed.
	 */
	public void loadServerQuestions() {
		//Log.d("Debug", "passed size:"+serverList.size());
		//Log.d("Debug", "serverListIndex is at "+serverListIndex);
		int checkListSize = serverList.size();
		int increment = 10;
		if (checkListSize - serverListIndex < 10) {
			increment = checkListSize - serverListIndex;
			//Log.d("Debug", "Increment "+increment+" times");
		}
		if (checkListSize > 10) { 
			for (int i = serverListIndex; i < (serverListIndex + increment); i++) {
				//Log.d("Debug", "Increment "+increment+" times");
				//Log.d("Debug", "i is:"+i);
				subQuestions.add(serverList.get(i));
			}
		}
		serverListIndex = serverListIndex + increment;
	}
	
	// Pushes new posts to server, returns true if connectivity attained and
	// pushed // returns false otherwise // This makes testing easier

	

	
/*###############################-----START OF GETTER METHODS----################################*/
	public Context getContext() {

		return this.context;
	}

	public UserPostCollector getUPC() {

		return upc;
	}

	/**
	 * Returns the questions that the post controller contains.
	 * 
	 * @return A list of Question objects
	 */
	public ArrayList<Question> getQuestionsInstance() {

		if (subQuestions == null) {
			subQuestions = new ArrayList<Question>();
		}
		return subQuestions;
	}

	/**
	 * Retrieves questions from the server and places the 10 most recent questions into the sub-questions list to display
	 * @return The sub-question list containing 10 questions from the server.
	 */
	public ArrayList<Question> getQuestionsFromServer() {
		serverList = sdm.searchQuestions("", null);
		serverList = qf.sortByDate(serverList);
		subQuestions.clear();
		// Log.d("size", "size:"+serverList.size());
		for (int i=0; i<serverList.size(); i++) {
			if (i > 9) {
				return subQuestions;
			} else {
				subQuestions.add(serverList.get(i));
			}
		}
		return subQuestions;
	}
	
//	public ArrayList<Post> getPushPostsInstance() {
//		if (pushPosts == null) {
//			pushPosts = new ArrayList<Post>();
//		}
//		return pushPosts;
//	}
	
	/**
	 * Returns the list of comments to the specified question object
	 * 
	 * @param questionID
	 *            the question that the comments pertain to.
	 * @return A list of Comment objects
	 */
	public ArrayList<Comment> getCommentsToQuestion(String questionID) {

		for (int i = 0; i < subQuestions.size(); i++) {
			if (subQuestions.get(i).getId().equals(questionID)) {
				return subQuestions.get(i).getComments();
			}
		}
		return null;
	}

	/**
	 * Returns the list of comments to the specified answer object
	 * 
	 * @param answerID
	 * @return ArrayList<Comment> A list of Comment objects
	 */
	public ArrayList<Comment> getCommentsToAnswer(String questionID,
			String answerID) {

		Question q = getQuestion(questionID);
		ArrayList<Answer> a = q.getAnswers();
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i).getId().equals(answerID)) {
				return a.get(i).getComments();
			}
		}
		return null;
	}

	/**
	 * Returns an answer object of a given answer id.
	 * 
	 * @param answerId
	 *            The ID of the answer object being returned.
	 * @param questionId
	 *            The ID of the question that the answer pertains to.
	 * @return An Answer object
	 */
	public Answer getAnswer(String answerId, String questionId) {

		Question q = getQuestion(questionId);
		ArrayList<Answer> a = q.getAnswers();
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i).getId().equals(answerId))
				return a.get(i);
		}
		return null;
	}

	/**
	 * Returns the question object of a given question id from the sub-question list.
	 * 
	 * @param qID
	 *            The ID of the question object being returned.
	 * @return A Question Object
	 */
	public Question getQuestion(String qID) {

		for (int i = 0; i < getQuestionsInstance().size(); i++) {
			if (subQuestions.get(i).getId().equals(qID)) {
				return subQuestions.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Returns an array Tuple object that contains the information needed to push Questions, Answers and Comments made while offline
	 * 
	 * @return Array of Tuple objects
	 */
	public ArrayList<Tuple> getTupleForPush() {
		
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<Tuple> tupleArray = local.loadTupleArray();
		return tupleArray;
	}
	
	/**
	 * 
	 * Returns an ArrayList of Question objects from local memory using a list
	 * of Question IDs.
	 * 
	 * @param idArray
	 *            An array of Question IDs
	 * @param local
	 *            the LocalDataManager used to load questions
	 * @return returns an ArrayList of Questions
	 */

	private ArrayList<Question> getQuestionsFromID(ArrayList<String> idArray,
			LocalDataManager local) {

		ArrayList<Question> returnedArray = new ArrayList<Question>();
		ArrayList<Question> questionArray = local.loadQuestions();

		for (int i = 0; i < idArray.size(); i++) {
			for (int j = 0; j < questionArray.size(); j++) {
				if (idArray.get(i).equals(questionArray.get(j).getId())) {
					returnedArray.add(questionArray.get(j));
				}
			}
		}
		return returnedArray;
	}
	
	/**
	 * Gets answer object corresponding to the answerID from the question object corresponding
	 * to the question ID
	 * 
	 * @param questionID ID of the question object to find
	 * @param answerID ID of the answer object within the question object
	 * @return Answer object
	 */
	public Answer getAnswerToPush(String questionID, String answerID) {
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idArray = new ArrayList<String>();
		idArray.add(questionID);
		ArrayList<Question> associatedQuestion = getQuestionsFromID(idArray, local);
		Question unpackQuestion = associatedQuestion.get(0);
		ArrayList<Answer> answerArray = unpackQuestion.getAnswers();
		
		for (int i = 0; i < answerArray.size(); i++) {
			if (answerArray.get(i).getId().equals(answerID)) {
				return answerArray.get(i);
			}
		}
		return null;
	}
	

	
/*##############################----END OF GETTER METHODS----################################*/



	/**
	 * Resets the counter for indexing the position of the list of questions
	 */
	public void resetServerListIndex() {
		serverListIndex = 0;
	}


	public ArrayList<Question> executeSearch(String searchString) {
		ServerDataManager sdm = new ServerDataManager();
		ArrayList<Question> qList = sdm.searchQuestions(searchString, null);
		// try {
		// Thread.currentThread().sleep(250);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		getQuestionsInstance().clear();
		getQuestionsInstance().addAll(qList);
		return qList;
	}

	/**
	 * Error message to display when there is no connection and user attempts to upvote.
	 */
	public void cantUpvoteMsg() {
		Toast.makeText(getContext(), "Please re-connect to upvote", Toast.LENGTH_SHORT).show();
	}


	public int countAnswers(Question q) {

		return q.countAnswers();
	}

	public int countComments(Question q) {

		return q.countComments();
	}
	
	/*
	 * These methods are currently not being used and are commented out We may
	 * want to re-implement them later so they are saved down here. Not revised
	 * for style or correctness.
	 */

	/*
	 * public void setUsername(String name){ this.username = name; }
	 */

	/*
	 * public String getUsername(){ return username; }
	 */

	/*
	*/

	/*
	 * // Creates a LocalDataManager and then calls all the saving methods // on
	 * each of the UPC's arrays. // I don't understand why this exists --- Eric
	 * //This is for updating the lists in the data manager --Carly public void
	 * saveUserPosts(){ dm = new LocalDataManager(context); ((LocalDataManager)
	 * dm).saveFavoritesID(upc.getFavoriteQuestions()); ((LocalDataManager)
	 * dm).savePostedQuestionsID(upc.getPostedQuestions()); ((LocalDataManager)
	 * dm).saveReadID(upc.getReadQuestions()); ((LocalDataManager)
	 * dm).saveToReadID(upc.getToReadQuestions()); }
	 */

	/*
	 * // when we load server posts, it should be based on a query // add code
	 * to reflect this later // Returns false if no questions loaded // Returns
	 * true otherwise // This makes testing easier public Boolean
	 * loadServerPosts(){ if (!checkConnectivity()) return false; dm = new
	 * ServerDataManager(); //subQuestions = dm.load(); // subAnswers =
	 * dm.loadAnswers() // this answers will be all of the children of the //
	 * questions we just loaded if (subQuestions == null) return false; return
	 * true;
	 * 
	 * }
	 */

	/*
	 * This method is not needed, but if we decide to need to save user posted
	 * answers then just uncomment.
	 * 
	 * public void addUserPost(Answer a, Context context) { LocalDataManager
	 * local = new LocalDataManager(context); String id = a.getId();
	 * local.savePostedAnswersID(id); local.saveAnswer(a); }
	 */

	// This should not be needed anymore. Unless we're saving answers
	// independent of questions.
	// public void addFavoriteAnswer(Answer a) {
	// LocalDataManager local = new LocalDataManager(getContext());
	// String id = a.getId();
	// // local.saveFavoriteAnswerID(id);
	// // local.saveAnswer(a);
	// }
	
	/**
	 * Sort the subQuestions list based on the specified comparison
	 * 
	 * @param type
	 */
//	private static final int SORT_BY_DATE = 0;
//	private static final int SORT_BY_UPVOTE = 1;
//	private static final int SORT_BY_PICTURE = 2;
//	public void sortQuestions(int type) {
//		switch (type) {
//		case SORT_BY_DATE:
//			sortQuestionsByDate();
//			break;
//		case SORT_BY_UPVOTE:
//			sortQuestionsByUpvote();
//			break;
//		case SORT_BY_PICTURE:
//			sortQuestionsByPic();
//			break;
//		}
//	}
	
	/**
	 * Upvote answer method, pushes upvotes to server
	 * 
	 * @param answerId
	 * @param questionId
	 */


//		if (getAnswerUpvotes().containsKey(answerId)) {
//			UpvoteTuple tuple = getAnswerUpvotes().get(answerId);
//			tuple.setUpvoteCount(tuple.getUpvoteCount() + 1);
//			getAnswerUpvotes().put(answerId, tuple);
//		} else {
//			UpvoteTuple tuple = new UpvoteTuple(questionId, 1);
//			getAnswerUpvotes().put(answerId, tuple);
//		}
//		// push all upvotes in answer upvote hashtable
//
//		if (checkConnectivity()) {
//			pushAnswerUpvotes();
//		} else {
//			ldm = new LocalDataManager(getContext());
//			ldm.savePushAnswerUpvotes(answerUpvotes);
//		}
//	}

//	public void pushAnswerUpvotes() {
//
//		for (HashMap.Entry<String, UpvoteTuple> entry : getAnswerUpvotes()
//				.entrySet()) {
//			Integer upvoteCount = entry.getValue().getUpvoteCount();
//			String qId = entry.getValue().getQuestionId();
//			sdm.pushAnswerUpvote(entry.getKey(), qId, upvoteCount);
//		}
//		try {
//			Thread.currentThread().sleep(250);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		answerUpvotes.clear();
//		ldm.savePushAnswerUpvotes(answerUpvotes);
//	}
	
//	public static HashMap<String, Integer> getQuestionUpvotes() {
//	if (questionUpvotes == null) {
//		questionUpvotes = new HashMap<String, Integer>();
//	}
//	return questionUpvotes;
//}

//public static HashMap<String, UpvoteTuple> getAnswerUpvotes() {
//	if (answerUpvotes == null) {
//		answerUpvotes = new HashMap<String, UpvoteTuple>();
//	}
//	return answerUpvotes;
//}
}
