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
	
	public Context getContext() {
		return this.context;
	}
	
	public void setUsername(String name){
		this.username = name;
	}

	public String getUsername(){
		return username;
	}
	
	/*
	 * The upc is never null, you instantiated it up there.
	 */
	public UserPostCollector getUPC(){
	//	if (upc == null){
	//		upc = new UserPostCollector();
	//	}
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
	
	public int countComments(Question q) {
		return q.countComments();
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
	//This is for updating the lists in the data manager --Carly
	public void saveUserPosts(){

		dm = new LocalDataManager(context);
		((LocalDataManager) dm).saveFavoritesID(upc.getFavoriteQuestions());
		((LocalDataManager) dm).savePostedQuestionsID(upc.getPostedQuestions());
		((LocalDataManager) dm).saveReadID(upc.getReadQuestions());
		((LocalDataManager) dm).saveToReadID(upc.getToReadQuestions());
	}



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
	public void addFavoriteQuestion(Question q) {
		upc.initFavoriteID(getContext());  // I need to load the lists if they haven't been loaded yet.
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
	
	// This should not be needed anymore.  Unless we're saving answers independent of questions.
	public void addFavoriteAnswer(Answer a) {
		LocalDataManager local = new LocalDataManager(getContext());
		String id = a.getId();
	//	local.saveFavoriteAnswerID(id);
	//	local.saveAnswer(a);
	}
	
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
	 * addUserPost() is overloaded.  Pass in the correct object and it'll do the rest.
	 * @param q
	 */
	public void addUserPost(Question q) {
		upc.initPostedQuestionID(getContext());  // I need to load the lists if they haven't been loaded yet.
		upc.initQuestionBank(getContext());
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idList = upc.getPostedQuestions();
		String id = q.getId();
		
		if (!idList.contains(id)) {
			idList.add(id);
			local.savePostedQuestionsID(idList);
			checkExistanceOfQuestion(q, local);
		}
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
	
	public ArrayList<Question> getReadQuestions() {
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idArray = local.loadRead();
		ArrayList<Question> readArray = getQuestionsFromID(idArray, local);
		return readArray;
	}
	
	public ArrayList<Question> getToReadQuestions() {
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idArray = local.loadToRead();
		ArrayList<Question> toReadArray = getQuestionsFromID(idArray, local);
		return toReadArray;
	}
	
	public ArrayList<Question> getUserPostedQuestions() {
		LocalDataManager local = new LocalDataManager(getContext());
		ArrayList<String> idArray = local.loadToRead();
		ArrayList<Question> postedArray = getQuestionsFromID(idArray, local);
		return postedArray;
	}
	
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
	
	/**
	 * End of what Eric wrote
	 */
}
