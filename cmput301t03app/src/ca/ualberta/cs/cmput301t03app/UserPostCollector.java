package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

public class UserPostCollector {
	private static ArrayList<Question> favoriteQuestions;
	private static ArrayList<Question> readQuestions;
	private static ArrayList<Question> toReadQuestions;
	private static ArrayList<Question> postedQuestions;
	private static ArrayList<Answer> postedAnswers;
	
	
	public ArrayList<Question> getFavoriteQuestions() {
		return favoriteQuestions;
	}
	
	public void addFavoriteQuestions(Question question) {
		getFavoriteQuestions().add(question);
	}
	
	public ArrayList<Question> getReadQuestions() {
		return readQuestions;
	}
	
	public void addReadQuestions(Question question) {
		getReadQuestions().add(question);
	}
	
	public ArrayList<Question> getToReadQuestions() {
		return toReadQuestions;
	}
	
	public void addtoReadQuestions(Question question) {
		getToReadQuestions().add(question);
	}
	
	public ArrayList<Question> getPostedQuestions() {
		return postedQuestions;
	}
	
	public void addPostedQuestion(Question question) {
		getPostedQuestions().add(question);
	}
	
	public ArrayList<Answer> getPostedAnswers() {
		return postedAnswers;
	}
	
	public void addUserAnswer(Answer answer) {
		getPostedAnswers().add(answer);
	}
	
	

}