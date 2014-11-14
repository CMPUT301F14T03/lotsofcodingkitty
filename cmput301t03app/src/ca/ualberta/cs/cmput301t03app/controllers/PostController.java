package ca.ualberta.cs.cmput301t03app.controllers;

import java.util.ArrayList;
import ca.ualberta.cs.cmput301t03app.datamanagers.LocalDataManager;
import ca.ualberta.cs.cmput301t03app.datamanagers.QuestionFilter;
import ca.ualberta.cs.cmput301t03app.datamanagers.ServerDataManager;
import ca.ualberta.cs.cmput301t03app.interfaces.iDataManager;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.Post;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.models.UserPostCollector;

import android.content.Context;



/**
 	* Responsible for all of the model manipulation in the program. <p>
 	* This class uses a UserPostCollector
	 * which contains all of the local data information (including posted
	 * questions, favorites, to read, read, etc.). It also uses a DataManager which
	 * is used for loading and saving.
	 * 
 *
 */
public class PostController {

	private static ArrayList<Question> subQuestions = null;
	private static ArrayList<Post> pushPosts = null;
	private QuestionFilter qf = new QuestionFilter();
	private static UserPostCollector upc = new UserPostCollector();
	private Context context;

	/**
	 * 
	 * Constructs a {@link #PostController() PostController}
	 * @param context
	 *            The context of the PostController
	 */

	public PostController(Context context) {

		this.context = context;
	}

	public int countAnswers(Question q) {

		return q.countAnswers();
	}

	public int countComments(Question q) {

		return q.countComments();
	}

	public Boolean checkConnectivity() {
		return false;
	}
	
	/**
	 * Returns true if a question is favorited by the user.
	 * @param  questionID the question ID of the question being checked.
	 * 
	 * */
	public Boolean isQuestionInFavByID(String questionID) {

		upc.initFavoriteID(getContext());
		// Log.d("click", "Size of Fav array: "+
		// upc.getFavoriteQuestions().size());
		for (int i = 0; i < upc.getFavoriteQuestions().size(); i++)
		{
			if (upc.getFavoriteQuestions().get(i).equals(questionID))
				return true;
		}
		return false;
	}

	/**
	 * Returns true if a question is in the cache or has already been read by the user.
	 * @param  questionID the question ID of the question being checked.
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
	 * This method replaces the old question with the updated question
	 * in the question bank and saves to local storage.
	 * 
	 * @param qId A String representing a Question ID
	 */
	public void updateQuestionInBank(String qId) {
		
		Question q = getQuestion(qId);
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<Question> questionArray = upc.getQuestionBank();
		
		//Replace the old question with the updated question at the specified index
		for (int i = 0; i<questionArray.size(); i++) {
			if (q.getId().equals(questionArray.get(i).getId())) {
				questionArray.set(i, q);
			}
		}
		
		local.saveToQuestionBank(questionArray);	
	}
	
	/**
	 * Adds a question to the favorite questions list and saves it locally.
	 * 
	 * @param q the Question object to be saved.
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
	 * @param q the previously read question that must be saved.
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
	 * @param q the question object that the user wants to read later.
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
	 * @param q a Question object that the user wants to post
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
	}

	/**
	 * Adds an Answer object to the question object
	 * 
	 * @param answer the answer object that the user has made
	 * @param questionID The ID of the question that the answer will be added to
	 */
	public void addAnswer(Answer answer, String questionID) {

		getQuestion(questionID).addAnswer(answer);
		getPushPostsInstance().add(new Post(answer, questionID, Question.class));
		//TODO: pushNewPosts();
		updateQuestionInBank(questionID);
	}

	/**
	 * Adds a question to the list in the PostController.
	 * 
	 * @param question
	 *            a Question object that the user wants to be added.
	 */
	public void addQuestion(Question question) {

		getQuestionsInstance().add(question);
		getPushPostsInstance().add(new Post(question));
//		upc.addPostedQuestion(question.getId());
		pushNewPosts();
	}

	/**
	 * Adds a comment object to a question object.
	 * 
	 * @param comment
	 *            A comment object that the user wants to be added to a question.
	 * @param parentId The ID of the question that the comment will be added to
	 */
	public void addCommentToQuestion(Comment comment, String parentId) {
		for (int i = 0; i < subQuestions.size(); i++) {
			if (subQuestions.get(i).getId().equals(parentId)) {
				subQuestions.get(i).addComment(comment);
				getPushPostsInstance().add(new Post(comment, parentId, Question.class));
			}
		}
		//TODO: pushNewPosts();
		updateQuestionInBank(parentId);
		
	}

	/**
	 * Adds a comment object to an answer object.
	 * 
	 * @param comment
	 *            A comment object that will be added to the answer
	 * @param answerID The ID of the answer that the comment will be added to
	 * @param questionID the question that the answer is pertaining to.
	 */
	public void addCommentToAnswer(Comment comment, String questionID,
			String answerID) {

		Question q = getQuestion(questionID);
		ArrayList<Answer> a = q.getAnswers();
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i).getId().equals(answerID)) {
				a.get(i).addComment(comment);
				getPushPostsInstance().add(new Post(comment, answerID, questionID, Answer.class));
			}
		}
		//TODO: pushNewPosts();
		updateQuestionInBank(questionID);
	}


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
	
	// new
	
	public ArrayList<Post> getPushPostsInstance() {
		if (pushPosts == null) {
			pushPosts = new ArrayList<Post>();
		}
		return pushPosts;
	}
	
	/**
	 * Returns the list of comments to the specified question object
	 * 
	 * @param questionID the question that the comments pertain to.
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
	 * @param answerId The ID of the answer object being returned.
	 * @param questionId The ID of the question that the answer pertains to.
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
	 * Returns the question object of a given question id.
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
	 * Returns the list of favorited questions. <p>This method only pulls from the
	 * question bank of favorite IDs list .
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
	 * Returns the list of read questions. <p> This method only pulls from the
	 * question bank of read IDs list .
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
	 * Returns the list of "To-read" questions. <p>This method only pulls from the
	 * question bank of "to-read" IDs list .
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
	 * question bank of posted questions IDs list
	 * .
	 * 
	 * @return A list of questions that the user has posted.
	 */
	public ArrayList<Question> getUserPostedQuestions() {

		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idArray = local.loadPostedQuestions();
		ArrayList<Question> postedArray = getQuestionsFromID(idArray, local);
		return postedArray;
	}

	/**
	 * 
	 * Returns an ArrayList of Question objects from local memory using a list of Question IDs.
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
	 * 
	 * Saves a question to local memory given that, that question does not exist in local memory.
	 * 
	 * @param q The question to be saved.
	 * @param local The datamanager that saves to local memory
	 */

	private void checkExistanceOfQuestion(Question q, LocalDataManager local) {

		boolean found = false;
		for (int i = 0; i < upc.getQuestionBank().size(); i++) {
			if (upc.getQuestionBank().get(i).getId().equals(q.getId()))	{
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
	
	// Pushes new posts to server, returns true if connectivity attained and
	// pushed // returns false otherwise // This makes testing easier

	public Boolean pushNewPosts() {
		Boolean connectivity = true; // placeholder until i make a
										// checkconnectivity function
		if (connectivity) {
			ServerDataManager sdm = new ServerDataManager(); // dm.save(pushQuestions);
			sdm.pushPosts(getPushPostsInstance());

			// Wait questions to be pushed before clearing

			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pushPosts.clear();
			return true;
		}
		return false;
	}
	
	public ArrayList<Question> executeSearch(String searchString) {
		ServerDataManager sdm = new ServerDataManager();
		ArrayList<Question> qList = sdm.searchQuestions(searchString, null);
//		try {
//			Thread.currentThread().sleep(250);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		getQuestionsInstance().clear();
		getQuestionsInstance().addAll(qList);
		return qList;
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
}
