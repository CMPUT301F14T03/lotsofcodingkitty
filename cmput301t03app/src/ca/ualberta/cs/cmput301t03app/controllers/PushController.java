package ca.ualberta.cs.cmput301t03app.controllers;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import ca.ualberta.cs.cmput301t03app.datamanagers.LocalDataManager;
import ca.ualberta.cs.cmput301t03app.datamanagers.ServerDataManager;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.models.Tuple;
import ca.ualberta.cs.cmput301t03app.models.UserPostCollector;

/**
 * This class is a controller responsible for all the Pushing activities that the program must, including Pushing all
 * Questions, Answers and Comments made while online AND while offline.
 *
 */

public class PushController
{
	private ServerDataManager sdm =new ServerDataManager();
	private PostController pc;
	private Context context;
	
	public PushController(Context con){
		this.context= con;
		pc=new PostController(this.context);
		
	}
	
	/**
	 * Determines which push method to call depending on the information stored in the Tuple.
	 */
	public void pushAnswersAndComments(){
		ArrayList<Tuple> tupleArray = pc.getTupleForPush();
		Tuple tuple;
		//ArrayList<Question> returnedQuestions;
		//Question q;
		//String qId = null;
		for (int i=0;i<tupleArray.size();i++){
			tuple = tupleArray.get(i);
			if (tuple.getAnswerID()!=null && tuple.getComment()!=null){
				addCommentToAnswer(tuple);
			}else if (tuple.getAnswerID()==null && tuple.getComment()!=null){
				addCommentToQuestion(tuple);
			}else if (tuple.getAnswerID()!=null && tuple.getComment()==null){
				addAnswerToQuestion(tuple);
			}
		}
		LocalDataManager local = new LocalDataManager(getContext());
		local.deletePushTuplelist();
	}
	
	/**
	 * Retrieves a Question object from the server by searching using the Question ID attribute.
	 * 
	 * @param tuple The tuple stored on local file that contains the question ID desired.
	 * @return The Question object retrieved from the server
	 */
	private Question getQuestionFromServer(Tuple tuple) {
		String qID = tuple.getQuestionID();
		ArrayList<Question> packagedQuestion = sdm.searchQuestions(qID, "_id");
		Question returnedQuestion = packagedQuestion.get(0);
		return returnedQuestion;
	}
	
	/**
	 * Pushes Questions made while offline to the server.  Pushing via sync-ing.
	 * 
	 */
	public void pushNewPosts() {

		if (pc.checkConnectivity()) {
			// Toast.makeText(this.getContext(), "Attempting to push to server",
			// Toast.LENGTH_SHORT).show();
			ServerDataManager sdm = new ServerDataManager();
			// sdm.pushPosts(getPushPostsInstance());
			LocalDataManager local = new LocalDataManager(getContext());
			UserPostCollector upc = new UserPostCollector();
			upc.initPushQuestionID(getContext());
			upc.initPushAnsCommTuple(getContext());
			upc.initQuestionBank(getContext());
			ArrayList<String> qID = upc.getPushQuestions();
			ArrayList<Question> qList = upc.getQuestionBank();

			if (qID.size() != 0) {
				Log.d("Debug", "Array size is" + qID.size());
				for (int i = 0; i < qID.size(); i++) {
					for (int j = 0; j < qList.size(); j++) {
						if (qList.get(j).getId().equals(qID.get(i))) {
							// Toast.makeText(this.getContext(),
							// qList.get(j).getSubject()+" should be added to server",
							// Toast.LENGTH_SHORT).show();
							sdm.addQuestion(qList.get(j));
						}
					}
				}
			} else {
				Log.d("Debug", "Array size should be == 0");
			}
			// Wait questions to be pushed before clearing
			upc.clearPushQuestions();
			local.deletePushQuestionsIDList();
			// ldm.savePushPosts(pushPosts);
		} else {
			// ldm = new LocalDataManager(getContext());
			// ldm.savePushPosts(pushPosts);
		}
	}
	
	/**
	 * Pushes the Answer object made while offline to the corresponding Question object based on ID matching.  Pushing via sync-ing.
	 * 
	 * @param tuple  The Tuple that contains the ID of tne Answer object to push and the ID of the Question object the Answer belongs to
	 */
	private void addAnswerToQuestion(Tuple tuple)
	{
		Question question = getQuestionFromServer(tuple);
		Answer answer = pc.getAnswerToPush(tuple.getQuestionID(), tuple.getAnswerID());
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

	/**
	 * Pushes the Comment object made while offline to the corresponding Question object based on ID matching.  Pushing via sync-ing.
	 * 
	 * @param tuple The Tuple that contains the Comment object and the ID of the Question that the Comment object belongs to
	 */
	private void addCommentToQuestion(Tuple tuple)
	{
		Question question = getQuestionFromServer(tuple);
		if (!question.getComments().contains(tuple.getComment())) {
			question.getComments().add(tuple.getComment());
			sdm.updateQuestion(question);
		}
	}

	/**
	 * Pushes the Comment object made while offline to the corresponding Answer object based on ID matching.  Pushing via sync-ing.
	 * 
	 * @param tuple The Tuple that contains the Comment object, the ID of the Answer the Comment object belongs to and the ID of the Question that the Answer object belongs to 
	 * 
	 */
	private void addCommentToAnswer(Tuple tuple){
		Question question = getQuestionFromServer(tuple);		
		for (int i=0; i < question.getAnswers().size(); i++){
			if (question.getAnswers().get(i).getId().equals(tuple.getAnswerID())){
				if (!question.getAnswers().get(i).getComments().contains(tuple.getComment())){
					question.getAnswers().get(i).getComments().add(tuple.getComment());
				}
			}
		}
		sdm.updateQuestion(question);
		
	}
	
	/**
	 * Pushing an Answer to the server.  Requires connectivity.
	 * 
	 * READ THIS: DO NOT CHANGE THE ADAPTOR OR ADD TO THE SUBQUESTIONS LIST IN THIS METHOD
	 * 
	 * @param answer
	 *            The answer that is being given.
	 * @param question
	 *            The question that is being answered.
	 */
	public void answerAQuestionToServer(Answer answer, String qID) {
		Question q = sdm.getQuestion(qID);
		q.addAnswer(answer);
		sdm.updateQuestion(q);
	}
	
	/**
	 * Pushes a Comment to an Answer.  Requires connectivity.
	 * 
	 * READ THIS: DO NOT CHANGE THE ADAPTOR OR ADD TO THE SUBQUESTIONS LIST IN THIS METHOD
	 * 
	 * @param comment The comment you want to add
	 * @param aID The ID of the answer you wish to comment
	 * @param qID The ID of the question the answer belongs to
	 */
	public void commentAnAnswerToServer(Comment comment, String aID, String qID) {
		Question q = sdm.getQuestion(qID);
		ArrayList<Answer> a = q.getAnswers();
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i).getId().equals(aID)) {
				a.get(i).addComment(comment);
			}
		}
		sdm.updateQuestion(q);
	}
	
	/**
	 * Pushes a Comment to a Question.  Requires connectivity.
	 * 
	 * READ THIS: DO NOT CHANGE THE ADAPTOR OR ADD TO THE SUBQUESTIONS LIST IN THIS METHOD
	 * 
	 * @param comment
	 *            The comment you want to add.
	 * @param question
	 *            The question you are commenting on or the parent question of
	 *            an answer you are commenting on.
	 */
	public void commentAQuestionToServer(Comment comment, String qID) {
		Question q = sdm.getQuestion(qID);
		q.addComment(comment);
		sdm.updateQuestion(q);
	}
	
	/**
	 * Pushes a Question to the server.  Requires connectivity.
	 * 
	 * READ THIS: DO NOT CHANGE THE ADAPTOR OR ADD TO THE SUBQUESTIONS LIST IN THIS METHOD
	 * 
	 * @param question
	 *            a Question object that the user wants to be added.
	 */
	public void addQuestionToServer(Question question) {
		sdm.addQuestion(question);
	}
	
	public Context getContext() {
		return this.context;
	}
}
