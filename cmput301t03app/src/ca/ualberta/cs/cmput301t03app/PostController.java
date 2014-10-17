package ca.ualberta.cs.cmput301t03app;

import java.util.ArrayList;

public class PostController {
	ArrayList<Question> questions=null;
	UserPostCollector upc=null;
	DataManager dm=new DataManager();
	QuestionFilter qf =new QuestionFilter();
	
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
	public Object saveServerPosts(){
		return null;
	}
	public Object loadServerPosts(){
		return null;
	}
	public Object saveUserPosts(){
		return null;
	}
	public Object loadUserPosts(){
		return null;
	}
	public Object checkConnectivity(){
		return null;
	}
	public Object pushNewPosts(){
		return null;
	}
	public Object upVote(){
		return null;
	}
	public Object addAnswer(){
		return null;
	}
	public Object addQuestion(){
		return null;
	}
	public Object addComment(){
		return null;
	}
}
