package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

public class PostController {
	ArrayList<Question> questions=null;
	UserPostCollector upc=null;
	iDataManager dm;
	QuestionFilter qf =new QuestionFilter();
	private String username;
	
	public void setUsername(String name){
		this.username = name;
	}
	
	public String getUsername(){
		return username;
	}
	
	public UserPostCollector getUPCInstance(){
		if (upc==null){
			upc=new UserPostCollector();
			}
		return upc;
	}
	
	public ArrayList<Question> getQuestionInstance(){
		if (questions==null){
			questions=new ArrayList<Question>();
			}
		return questions;
	}
	
	public Object getAnswer(){
		return null ;
	}
	public Object getQuestion(){
		return null;
	}
	public Object getComment(){
		return null;
	}
	public Object save(){
		return null;
	}

	public Object load(){
		return null;
	}
	public Boolean checkConnectivity(){
		return null;
	}
	public Object pushNewPosts(){
		return null;
	}
	public Object upVote(){
		return null;
	}
	public Object addAnswer(Answer answer){
		return null;
	}
	public Object addQuestion(Question question){
		return null;
	}
	public Object addComment(Comment comment){
		return null;
	}
}
