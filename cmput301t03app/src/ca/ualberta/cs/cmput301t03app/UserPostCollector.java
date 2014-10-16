package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

public class UserPostCollector {
	ArrayList<Answer> favoriteAnswers;
	ArrayList<Question> favoriteQuestions;
	ArrayList<Question> readQuestions;
	ArrayList<Question> toReadQuestions;
	ArrayList<Question> postedQuestions;
	ArrayList<Answer> postedAnswers;
	public ArrayList<Answer> getFavoriteAnswers() {
		return favoriteAnswers;
	}
	public void addFavoriteAnswers(Answer answers) {
		getFavoriteAnswers().add(answers);
	}
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
	public void setPostedQuestions(Question question) {
		getPostedQuestions().add(question);
	}
	public ArrayList<Answer> getPostedAnswers() {
		return postedAnswers;
	}
	public void setUserAnswers(Answer answer) {
		getPostedAnswers().add(answer);
	}
	
	

}