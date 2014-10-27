package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

import android.content.Context;

public class PostController {
	private static ArrayList<Question> subQuestions = new ArrayList<Question>();
	private static ArrayList<Answer> subAnswers = new ArrayList<Answer>();
	private ArrayList<Question> pushQuestions = new ArrayList<Question>();
	private ArrayList<Answer> pushAnswers = new ArrayList<Answer>();
	UserPostCollector upc = new UserPostCollector();
	iDataManager dm;
	QuestionFilter qf =new QuestionFilter();
	private String username;
	private Context context; //THIS IS SOLELY FOR TESTING PURPOSES -Carly
	
	public void setUsername(String name){
		this.username = name;
	}
	
	public String getUsername(){
		return username;
	}
	
	public UserPostCollector getUPCInstance(){
		return upc;
	}
	
	public ArrayList<Question> getQuestionsInstance(){
		return subQuestions;
	}
	
	public ArrayList<Answer> getAnswersInstance(){
		return subAnswers;
	}
	
	public int countAnswers(Question q) {
		return q.countAnswers();
	}
	
	public Answer getAnswer(String Id){
		for(int i = 0; i < subAnswers.size(); i++) {
			if(subAnswers.get(i).getId() == Id) {
				return subAnswers.get(i);
			}
		}
		return null;
	}
	public Question getQuestion(String qID){
		for (int i = 0; i<getQuestionsInstance().size(); i++){
			if (getQuestionsInstance().get(i).getId().equals(qID)){
				return getQuestionsInstance().get(i);
			}
		}
		return null;
	}
	
	public Boolean checkConnectivity(){
		return null;
	}
	
	// Pushes new posts to server, returns true if connectivity attained and pushed
	// returns false otherwise
	// This makes testing easier
	
	public Boolean pushNewPosts(){
		if (checkConnectivity()) {
			dm = new ServerDataManager();
			dm.save(pushQuestions);
			
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
	
	public void saveUserPosts() {
		dm = new LocalDataManager(context);
		((LocalDataManager) dm).saveFavorites(upc.getFavoriteQuestions());
		((LocalDataManager) dm).savePostedQuestions(upc.getPostedQuestions());
		((LocalDataManager) dm).saveRead(upc.getReadQuestions());
		((LocalDataManager) dm).saveToRead(upc.getToReadQuestions());
	}
	
	// Creates a LocalDataManager and then populates the UPC
	// with arrays loaded from the local cache.
	
	public void loadUserPosts() {
		dm = new LocalDataManager(context);
		upc = new UserPostCollector(((LocalDataManager)dm).loadFavorites(), ((LocalDataManager)dm).loadRead(), 
				((LocalDataManager)dm).loadToRead(), ((LocalDataManager)dm).loadPostedQuestions(), 
				((LocalDataManager)dm).loadPostedAnswers());
	}
	
	// when we load server posts, it should be based on a query
	// add code to reflect this later
	
	// Returns false if no questions loaded
	// Returns true otherwise
	// This makes testing easier
	
	public Boolean loadServerPosts() {
		if (!checkConnectivity()) {
			return false;
		}
		dm = new ServerDataManager();
		subQuestions = dm.load();
		// subAnswers = dm.loadAnswers()
		// this answers will be all of the children of the
		// questions we just loaded
		
		if (subQuestions == null) {
			return false;
		}
		return true;
		
	}
	
	// When an answer is added it is added to the subAnswers list,
	// the pushAnswers list, and then the UPC.
	
	public void addAnswer(Answer answer){
		subAnswers.add(answer);
		pushAnswers.add(answer);
		upc.addUserAnswer(answer);
		pushNewPosts();
	}
	
	// When an question is added it is added to the subQuestions list,
	// the pushQuestions list, and then the UPC.
	
	public void addQuestion(Question question){
		subQuestions.add(question);
		pushQuestions.add(question);
		upc.addPostedQuestion(question);
		pushNewPosts();
	}
	
	
	// Add a comment when you only know the Question or Answer parentId
	
	public void addComment(Comment comment, String parentId){
		for (int i = 0; i < subQuestions.size(); i++) {
			if (subQuestions.get(i).getId().equals(parentId)) {
				subQuestions.get(i).addComment(comment);
			}
		}
		for (int i = 0; i < subAnswers.size(); i++) {
			if (subAnswers.get(i).getId().equals(parentId)) {
				subAnswers.get(i).addComment(comment);
			}
		}
	}
}
