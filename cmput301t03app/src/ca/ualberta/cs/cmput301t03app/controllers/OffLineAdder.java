package ca.ualberta.cs.cmput301t03app.controllers;

import java.util.ArrayList;

import android.content.Context;
import ca.ualberta.cs.cmput301t03app.datamanagers.ServerDataManager;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.models.tuple;


public class OffLineAdder
{
	private OffLineTracker olt=new OffLineTracker();
	private ServerDataManager sdm =new ServerDataManager();
	private PostController pc;
	Context context;
	
	public OffLineAdder(Context con){
		this.context= con;
		pc=new PostController(this.context);
		
	}
	
		
	private void getMode(){
		int listSize = olt.getOffLineSize();
		ArrayList<Question> returnedQuestions;
		Question q;
		ArrayList<tuple> tuples=olt.getOffLineInstance();
		String qId = null;
		tuple tuple =null;
		for (int i=0;i<listSize;i++){
			tuple = tuples.get(i);
			if (tuple.getaId()!=null && tuple.getComment()!=null){
				addCommentToAnswer(tuple);
			}else if (tuple.getaId()==null && tuple.getComment()!=null){
				addCommentToQuestion(tuple);
			}else if (tuple.getaId()!=null && tuple.getComment()==null){
				addAnswerToQuestion(tuple);
			}
		}
	}
	
	private void addAnswerToQuestion(tuple tuple)
	{

		// TODO Auto-generated method stub
		
	}


	private void addCommentToQuestion(tuple tuple)
	{

		// TODO Auto-generated method stub
		
	}


	private void addCommentToAnswer(tuple tuple){
		String qId = tuple.getqId();
		ArrayList<Question> returnedQuestions= null;
		Question returnedQ= null;
		returnedQuestions=sdm.searchQuestions(qId, "_id");
		returnedQ=returnedQuestions.get(0);
		
		for (int i=0;i<returnedQ.getAnswers().size();i++){
			if (returnedQ.getAnswers().get(i).getId().equals(tuple.getaId())){
				if (!returnedQ.getAnswers().get(i).getComments().contains(tuple.getComment())){
					returnedQ.getAnswers().get(i).getComments().add(tuple.getComment());
				}
			}
		}
		sdm.updateQuestion(returnedQ);
		
	}
	
}
