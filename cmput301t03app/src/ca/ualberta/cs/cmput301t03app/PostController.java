package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

import android.content.Context;

public class PostController{

	private static ArrayList<Question> subQuestions = null;
	private ArrayList<Question> pushQuestions = new ArrayList<Question>();
	private ArrayList<Answer> pushAnswers = new ArrayList<Answer>();
	private static UserPostCollector upc = new UserPostCollector();
	private iDataManager dm;
	private QuestionFilter qf = new QuestionFilter();
	private String username;
	private Context context; // THIS IS SOLELY FOR TESTING PURPOSES -Carly

	// THIS IS SOLELY FOR TESTING PURPOSES -Carly
	public PostController(Context context) {
		this.context = context;
	}
	
	public PostController(){}
	
	public void setUsername(String name){
		this.username = name;
	}

	public String getUsername(){
		return username;
	}

	public UserPostCollector getUPCInstance(){
		if (upc == null){
			upc = new UserPostCollector();
		}
		return upc;
	}

	public ArrayList<Question> getQuestionsInstance(){
		if (subQuestions == null){
			subQuestions = new ArrayList<Question>();
		}
		return subQuestions;
	}

	public int countAnswers(Question q){
		return q.countAnswers();
	}

	public Answer getAnswer(String answerId,String questionId){
		Question q = getQuestion(questionId);
		ArrayList<Answer> a = q.getAnswers();
		for (int i = 0; i < a.size(); i++){
				if (a.get(i).getId().equals(answerId))
					return a.get(i);
		}
		return null;
	}

	public Question getQuestion(String qID){
		for (int i = 0; i < subQuestions.size(); i++){
			if (subQuestions.get(i).getId().equals(qID)){
				return subQuestions.get(i);
			}
		}
		return null;
	}

	public Boolean checkConnectivity(){
		return null;
	}

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
	}

	// Creates a LocalDataManager and then calls all the saving methods
	// on each of the UPC's arrays.
	// I don't understand why this exists --- Eric
	/**
	public void saveUserPosts(){

		dm = new LocalDataManager(context);
		((LocalDataManager) dm).saveFavorites(upc.getFavoriteQuestions());
		((LocalDataManager) dm).savePostedQuestions(upc.getPostedQuestions());
		((LocalDataManager) dm).saveRead(upc.getReadQuestions());
		((LocalDataManager) dm).saveToRead(upc.getToReadQuestions());
	}

	// Creates a LocalDataManager and then populates the UPC
	// with arrays loaded from the local cache.
	 * 
	 */

	public void loadUserPosts(){

		dm = new LocalDataManager(context);
		upc = new UserPostCollector(((LocalDataManager) dm).loadFavorites(),
				((LocalDataManager) dm).loadRead(),
				((LocalDataManager) dm).loadToRead(),
				((LocalDataManager) dm).loadPostedQuestions(),
				((LocalDataManager) dm).loadPostedAnswers());
	}

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

	}

	// When an answer is added it is added to the subAnswers list,
	// the pushAnswers list, and then the UPC.

	public void addAnswer(Answer answer,String questionID){
		getQuestion(questionID).addAnswer(answer);
	}

	// When an question is added it is added to the subQuestions list,
	// the pushQuestions list, and then the UPC.

	public void addQuestion(Question question){

		subQuestions.add(question);
	//	pushQuestions.add(question);
	//	upc.addPostedQuestion(question);
	//	pushNewPosts();
	}

	// Add a comment when you only know the Question or Answer parentId

	public void addCommentToQuestion(Comment comment, String parentId){

		for (int i = 0; i < subQuestions.size(); i++){
			if (subQuestions.get(i).getId().equals(parentId)){
				subQuestions.get(i).addComment(comment);
			}
		}
	}
	public ArrayList<Comment> getCommentsToQuestion(String questionID){

		for (int i = 0; i < subQuestions.size(); i++){
			if (subQuestions.get(i).getId().equals(questionID)){
				return subQuestions.get(i).getComments();
			}
		}
		return null;
	}

	public void addCommentToAnswer(Comment comment, String questionID,String answerID){
		Question q = getQuestion(questionID);
		ArrayList<Answer> a = q.getAnswers();
		for (int i = 0; i < a.size(); i++){
			if (a.get(i).getId().equals(questionID)){
				a.get(i).addComment(comment);
			}
		}
	}
	public ArrayList<Comment> getCommentsToAnswer(String questionID,String answerID){
		Question q = getQuestion(questionID);
		ArrayList<Answer> a = q.getAnswers();
		for (int i = 0; i < a.size(); i++){
			if (a.get(i).getId().equals(questionID)){
				return a.get(i).getComments();
			}
		}
		return null;
	}
	
	/**
	 * These are all communicating with the UPC to tell it to save stuff
	 * Please pass in the context, because it is needed for saving (Json DEMANDS IT!)
	 * Direct questions on these to Eric
	 */
<<<<<<< HEAD
	public void addFavoriteQuestion(Question q, Context context) {
		upc.initFavoriteID(context);
		LocalDataManager local = new LocalDataManager(context);
		String id = q.getId();
		if (!upc.getFavoriteQuestions().contains(id)) {
			local.saveFavoritesID(id);
			local.saveQuestion(q);
		}
=======
	public void addFavoriteQuestion(Question q) {
		LocalDataManager local = new LocalDataManager(context);
		String id = q.getId();
		
		UserPostCollector upc = getUPCInstance();
		upc.addFavoriteQuestion(id);
		
		local.saveFavoritesID(id);
		local.appendToQuestionBank(q);
	}
	
	/**
	 * Returns the list of favorite questions.
	 * This method only pulls from the question bank the questions 
	 * whose IDs are in the favorite ID's list .
	 * @return favoriteArray A list of Question objects.
	 */
	public ArrayList<Question> getFavoriteQuestions() {
		LocalDataManager local = new LocalDataManager(context);
		ArrayList<Question> favoriteArray = new ArrayList<Question>();
		ArrayList<Question> questionArray = local.loadQuestions();
		ArrayList<String> idArray = local.loadFavorites();
		
		for (String id : idArray) {
			for (int i = 0; i<questionArray.size(); i++) {
				Question question = questionArray.get(i);
				if (id == question.getId()) {
					favoriteArray.add(question);
				}
			}
		}
		return favoriteArray;
>>>>>>> upstream/master
	}
	
	// This should not be needed anymore.  Unless we're saving answers independent of questions.
	public void addFavoriteAnswer(Answer a, Context context) {
		LocalDataManager local = new LocalDataManager(context);
		String id = a.getId();
		local.saveFavoriteAnswerID(id);
		local.saveAnswer(a);
	}
	
	public void addReadQuestion(Question q, Context context) {
		upc.initReadID(context);
		LocalDataManager local = new LocalDataManager(context);
		String id = q.getId();
<<<<<<< HEAD
		if (!upc.getReadQuestions().contains(id)){
			local.saveReadID(id);
			local.saveQuestion(q);		
		}
=======
		local.saveReadID(id);
		local.appendToQuestionBank(q);		
>>>>>>> upstream/master
	}
	
	public void addToRead(Question q, Context context) {
		upc.initToReadID(context);
		LocalDataManager local = new LocalDataManager(context);
		String id = q.getId();
<<<<<<< HEAD
		if (!upc.getToReadQuestions().contains(id)){
			local.saveToReadID(id);
			local.saveQuestion(q);
		}
=======
		local.saveToReadID(id);
		local.appendToQuestionBank(q);
>>>>>>> upstream/master
	}
	
	/**
	 * addUserPost() is overloaded.  Pass in the correct object and it'll do the rest.
	 * @param q
	 */
	public void addUserPost(Question q, Context context) {
		upc.initPostedQuestionID(context);
		LocalDataManager local = new LocalDataManager(context);
		String id = q.getId();
<<<<<<< HEAD
		if (!upc.getPostedQuestions().contains(id)) {
			local.savePostedQuestionsID(id);
			local.saveQuestion(q);
		}
=======
		local.savePostedQuestionsID(id);
		local.appendToQuestionBank(q);
>>>>>>> upstream/master
	}
	
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
	
	/**
	 * End of what Eric wrote
	 */
}
