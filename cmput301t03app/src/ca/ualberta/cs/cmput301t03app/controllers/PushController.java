package ca.ualberta.cs.cmput301t03app.controllers;

import java.util.ArrayList;

import android.content.Context;
import ca.ualberta.cs.cmput301t03app.datamanagers.LocalDataManager;
import ca.ualberta.cs.cmput301t03app.datamanagers.ServerDataManager;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.models.Tuple;


public class PushController
{
	private OffLineTracker olt=new OffLineTracker();
	private ServerDataManager sdm =new ServerDataManager();
	private PostController pc;
	private Context context;
	
	public PushController(Context con){
		this.context= con;
		pc=new PostController(this.context);
		
	}
	
		
	public void pushAnswersAndComments(){
		ArrayList<Tuple> tupleArray = pc.getTupleForPush();
		Tuple tuple;
		//ArrayList<Question> returnedQuestions;
		//Question q;
		//String qId = null;
		for (int i=0;i<tupleArray.size();i++){
			tuple = tupleArray.get(i);
			if (tuple.getaId()!=null && tuple.getComment()!=null){
				addCommentToAnswer(tuple);
			}else if (tuple.getaId()==null && tuple.getComment()!=null){
				addCommentToQuestion(tuple);
			}else if (tuple.getaId()!=null && tuple.getComment()==null){
				addAnswerToQuestion(tuple);
			}
		}
		LocalDataManager local = new LocalDataManager(getContext());
		local.deletePushTuplelist();
	}
	
	private Question getQuestionFromServer(Tuple tuple) {
		String qID = tuple.getqId();
		ArrayList<Question> packagedQuestion = sdm.searchQuestions(qID, "_id");
		Question returnedQuestion = packagedQuestion.get(0);
		return returnedQuestion;
	}
	
	private void addAnswerToQuestion(Tuple tuple)
	{
		Question question = getQuestionFromServer(tuple);
		Answer answer = pc.getAnswerToPush(tuple.getqId(), tuple.getaId());
		if (!question.getAnswers().contains(answer)) {
			question.getAnswers().add(answer);
			sdm.updateQuestion(question);
		}
//		for (int i=0; i<question.getAnswers().size(); i++) {
//			if (question.getAnswers().get(i).getId().equals(answer.getId())) {
//				break;
//			}
//			if (i == question.getAnswers().size()-1 && 
//					!question.getAnswers().get(i).getId().equals(answer.getId())) {
//				question.getAnswers().add(answer);
//				sdm.updateQuestion(question);
//			}
//		}		
	}


	private void addCommentToQuestion(Tuple tuple)
	{
		Question question = getQuestionFromServer(tuple);
		if (!question.getComments().contains(tuple.getComment())) {
			question.getComments().add(tuple.getComment());
			sdm.updateQuestion(question);
		}
	}


	private void addCommentToAnswer(Tuple tuple){
		Question question = getQuestionFromServer(tuple);		
		for (int i=0; i < question.getAnswers().size(); i++){
			if (question.getAnswers().get(i).getId().equals(tuple.getaId())){
				if (!question.getAnswers().get(i).getComments().contains(tuple.getComment())){
					question.getAnswers().get(i).getComments().add(tuple.getComment());
				}
			}
		}
		sdm.updateQuestion(question);
		
	}
	
	public Context getContext() {
		return this.context;
	}
}
