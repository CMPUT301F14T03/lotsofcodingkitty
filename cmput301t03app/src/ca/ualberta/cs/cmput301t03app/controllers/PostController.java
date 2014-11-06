package ca.ualberta.cs.cmput301t03app.controllers;

import java.util.ArrayList;
import ca.ualberta.cs.cmput301t03app.datamanagers.LocalDataManager;
import ca.ualberta.cs.cmput301t03app.incomplete.QuestionFilter;
import ca.ualberta.cs.cmput301t03app.incomplete.ServerDataManager;
import ca.ualberta.cs.cmput301t03app.interfaces.iDataManager;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.Question;

import android.content.Context;
import android.util.Log;

public class PostController{

	private static ArrayList<Question> subQuestions = null;
	private ArrayList<Question> pushQuestions = new ArrayList<Question>();
	private static UserPostCollector upc = new UserPostCollector();
	private iDataManager dm;
	private QuestionFilter qf = new QuestionFilter();
	private Context context;

	public PostController(Context context) {
		this.context = context;
	}

	public int countAnswers(Question q){
		return q.countAnswers();
	}
	
	public int countComments(Question q) {
		return q.countComments();
	}
	
	
	/****************************************Check methods******************************************/
	
	
	public Boolean checkConnectivity(){
		return false;
	}
	
	/**
	 * This method checks if a question is in favorites
	 * @author joshnguyen
	 * */
	public Boolean isQuestionInFavByID(String questionID) {
		upc.initFavoriteID(getContext());
//		Log.d("click",  "Size of Fav array: "+ upc.getFavoriteQuestions().size());
		for (int i = 0; i<upc.getFavoriteQuestions().size();i++) {
			if (upc.getFavoriteQuestions().get(i).equals(questionID))
				return true;
		}
		return false;
	}
	
	/**
	 * This method checks if a question is in Read/Cached
	 * @author joshnguyen
	 * */
	public Boolean isQuestionInReadByID(String questionID) {
		for (int i = 0; i<upc.getFavoriteQuestions().size();i++) {
			if (upc.getFavoriteQuestions().get(i).equals(questionID))
				return true;
		}
		return false;
	}
	
	/*************************************************Adding*********************************************/
	
	
	/**
	 * Adds a question to the favorite questions list and saves it locally
	 * @param q A Question Object
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
	 * @param q A Question Object
	 */
	public void addReadQuestion(Question q) {
		upc.initReadID(getContext());  // I need to load the lists if they haven't been loaded yet.
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
	 * @param q A Question Object
	 */
	public void addToRead(Question q) {
		upc.initToReadID(getContext());  // I need to load the lists if they haven't been loaded yet.
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
	 * @param q A Question Object
	 */
	public void addUserPost(Question q) {
		upc.initPostedQuestionID(getContext());  // I need to load the lists if they haven't been loaded yet.
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
	 * @param answer An Answer Object
	 * @param questionID A string representing an ID
	 */
	public void addAnswer(Answer answer,String questionID){
		getQuestion(questionID).addAnswer(answer);
	}

	/**
	 * When an question is added it is added to the subQuestions list,
	 * the pushQuestions list, and then the UPC.
	 * @param question A Question Object
	 */
	public void addQuestion(Question question){
		getQuestionsInstance().add(question);
	//	pushQuestions.add(question);
	//	upc.addPostedQuestion(question);
	//	pushNewPosts();
	}

	
	/**
	 * Adds a comment object to a question object.
	 * @param comment A comment object
	 * @param parentId A string representing an ID
	 */
	public void addCommentToQuestion(Comment comment, String parentId){
		for (int i = 0; i < subQuestions.size(); i++){
			if (subQuestions.get(i).getId().equals(parentId)){
				subQuestions.get(i).addComment(comment);
			}
		}
	}
	
	/**
	 * Adds a comment object to a answer object.
	 * @param comment A comment object
	 * @param parentId A string representing an ID
	 */
	public void addCommentToAnswer(Comment comment, String questionID,String answerID){
		Question q = getQuestion(questionID);
		ArrayList<Answer> a = q.getAnswers();
		for (int i = 0; i < a.size(); i++){
			if (a.get(i).getId().equals(answerID)){
				a.get(i).addComment(comment);
			}
		}
	}
	
	
	/********************************************Getters********************************************/
	
	
	
	public Context getContext() {
		return this.context;
	}
	
	public UserPostCollector getUPC(){
		return upc;
	}
	
	/**
	 * Returns a subset of the total list of questions from the server.
	 * @return subQuestions A list of Question objects
	 */
	public ArrayList<Question> getQuestionsInstance(){
		if (subQuestions == null){
			subQuestions = new ArrayList<Question>();
		}
		return subQuestions;
	}
	
	/**
	 * Returns the list of comments to the specified question object
	 * @param questionID
	 * @return ArrayList<Comment> A list of Comment objects
	 */
	public ArrayList<Comment> getCommentsToQuestion(String questionID){
		for (int i = 0; i < subQuestions.size(); i++){
			if (subQuestions.get(i).getId().equals(questionID)){
				return subQuestions.get(i).getComments();
			}
		}
		return null;
	}
	
	/**
	 * Returns the list of comments to the specified answer object
	 * @param answerID
	 * @return ArrayList<Comment> A list of Comment objects
	 */
	public ArrayList<Comment> getCommentsToAnswer(String questionID,String answerID){
		Question q = getQuestion(questionID);
		ArrayList<Answer> a = q.getAnswers();
		for (int i = 0; i < a.size(); i++){
			if (a.get(i).getId().equals(answerID)){
				return a.get(i).getComments();
			}
		}
		return null;
	}
	
	
	/**
	 * Returns an answer from a question given the question id and answer id.
	 * @param answerId A String representing an ID
	 * @param questionId A String representing an ID
	 * @return Answer An Answer object
	 */
	public Answer getAnswer(String answerId,String questionId){
		Question q = getQuestion(questionId);
		ArrayList<Answer> a = q.getAnswers();
		for (int i = 0; i < a.size(); i++){
				if (a.get(i).getId().equals(answerId))
					return a.get(i);
		}
		return null;
	}
	
	/**
	 * Returns the question object given the question id.
	 * @param qID A String representing an ID
	 * @return Question A Question Object
	 */
	public Question getQuestion(String qID){
		for (int i = 0; i < getQuestionsInstance().size(); i++){
			if (getQuestionsInstance().get(i).getId().equals(qID)){
				return getQuestionsInstance().get(i);
			}
		}
		return null;
	}
		
	/**
	 * Returns the list of favorite questions.
	 * This method only pulls from the question bank the questions 
	 * whose IDs are in the favorite ID's list .
	 * @return favoriteArray A list of Question objects.
	 */
	public ArrayList<Question> getFavoriteQuestions() {
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idArray = local.loadFavorites();
		ArrayList<Question> favoriteArray = getQuestionsFromID(idArray, local);
		return favoriteArray;
	}
	
	/**
	 * Returns the list of read questions.
	 * This method only pulls from the question bank the questions 
	 * whose IDs are in the read ID's list .
	 * @return readArray A list of Question objects.
	 */
	public ArrayList<Question> getReadQuestions() {
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idArray = local.loadRead();
		ArrayList<Question> readArray = getQuestionsFromID(idArray, local);
		return readArray;
	}
	
	/**
	 * Returns the list of "to read" questions.
	 * This method only pulls from the question bank the questions 
	 * whose IDs are in the "to read" ID's list .
	 * @return toReadArray A list of Question objects.
	 */
	public ArrayList<Question> getToReadQuestions() {
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idArray = local.loadToRead();
		ArrayList<Question> toReadArray = getQuestionsFromID(idArray, local);
		return toReadArray;
	}
	
	/**
	 * Returns the list of posted questions.
	 * This method only pulls from the question bank the questions 
	 * whose IDs are in the post questions ID's list .
	 * @return postedArray A list of Question objects.
	 */
	public ArrayList<Question> getUserPostedQuestions() {
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idArray = local.loadPostedQuestions();
		ArrayList<Question> postedArray = getQuestionsFromID(idArray, local);
		return postedArray;
	}
	
	
	
	/********************************************Private methods*****************************************************/
	
	
	
	private ArrayList<Question> getQuestionsFromID(ArrayList<String> idArray, LocalDataManager local) {
		ArrayList<Question> returnedArray = new ArrayList<Question>();
		ArrayList<Question> questionArray = local.loadQuestions();
		
		for (int i = 0; i < idArray.size(); i++) {
			for(int j = 0; j < questionArray.size(); j++) {
				if (idArray.get(i).equals(questionArray.get(j).getId())) {
					returnedArray.add(questionArray.get(j));
				}
			}
		}
		return returnedArray;
	}
	
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
	
	
	/* These methods are currently not being used and are commented out
	 * We may want to re-implement them later so they are saved down here.
	 * Not revised for style or correctness.
	 */
	
	/*
	public void setUsername(String name){
		this.username = name;
	}*/
	
	/*
	public String getUsername(){
		return username;
	}*/
	
	/*
	*/
	
	/*
	// Pushes new posts to server, returns true if connectivity attained and
	// pushed
	// returns false otherwise
	// This makes testing easier

	public Boolean pushNewPosts(){
		if (checkConnectivity()){
			dm = new ServerDataManager();
			//dm.save(pushQuestions);

			// need a method for saving new to be pushed answers to server
			// dm.saveAnswers(pushAnswers);

			pushQuestions.clear();
			pushAnswers.clear();
			return true;
		}
		return false;
	}*/
	
	/*
	// Creates a LocalDataManager and then calls all the saving methods
	// on each of the UPC's arrays.
	// I don't understand why this exists --- Eric
	//This is for updating the lists in the data manager --Carly
	public void saveUserPosts(){
		dm = new LocalDataManager(context);
		((LocalDataManager) dm).saveFavoritesID(upc.getFavoriteQuestions());
		((LocalDataManager) dm).savePostedQuestionsID(upc.getPostedQuestions());
		((LocalDataManager) dm).saveReadID(upc.getReadQuestions());
		((LocalDataManager) dm).saveToReadID(upc.getToReadQuestions());
	}*/
	
	/*
	// when we load server posts, it should be based on a query
	// add code to reflect this later
	// Returns false if no questions loaded
	// Returns true otherwise
	// This makes testing easier
	public Boolean loadServerPosts(){
		if (!checkConnectivity()) return false;
		dm = new ServerDataManager();
		//subQuestions = dm.load();
		// subAnswers = dm.loadAnswers()
		// this answers will be all of the children of the
		// questions we just loaded
		if (subQuestions == null) return false;
		return true;

	}*/
	
	/*
	 * This method is not needed, but if we decide to need to save user posted answers
	 * then just uncomment.
	 * 
	public void addUserPost(Answer a, Context context) {
		LocalDataManager local = new LocalDataManager(context);
		String id = a.getId();
		local.savePostedAnswersID(id);
		local.saveAnswer(a);
	}
	*/
	
	// This should not be needed anymore.  Unless we're saving answers independent of questions.
//	public void addFavoriteAnswer(Answer a) {
//		LocalDataManager local = new LocalDataManager(getContext());
//		String id = a.getId();
//	//	local.saveFavoriteAnswerID(id);
//	//	local.saveAnswer(a);
//	}
}
