import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.controllers.PushController;
import ca.ualberta.cs.cmput301t03app.datamanagers.LocalDataManager;
import ca.ualberta.cs.cmput301t03app.datamanagers.ServerDataManager;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.models.Tuple;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class PushPullTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	
	public PushPullTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	// Test creates a new PostController object, infinitely loops until
	// connectivity is determined, then attempts to pull a posts object
	// from the server.

		//Object posts = new Object();
		//posts = pc.loadServerPosts();
		//assertNotSame("No posts loaded from server.", posts, null);
	
	// Test creates a new post object, then pushes object to server.
	// Asserts that the push object exists on the server by pulling
	// it from the server and ensuring it's the same object
	
	public void testPushAndPull() {
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		Question question = new Question("Question title", "Question body", "author");
		ServerDataManager sdm = new ServerDataManager();
		PushController pushCtrl = new PushController(getInstrumentation().getTargetContext());
		pushCtrl.addQuestionToServer(question);
		Question q2 = sdm.getQuestion(question.getId());
		assertEquals("The question should be the same", q2.getId(), question.getId());
		sdm.deleteQuestion(question.getId());
		//while (!pc.checkConnectivity()) {
		}
		//pc.pushNewPosts();
		//posts = pc.loadServerPosts();
		//assertEquals("Retrieved server questions not same and saved questions.", posts, question);
	
	// Tests if the questions, answers and comments you write offline are saved
	// properly to the local device for pushing to the server once the user is able
	// to connect a network
	public void testWritePostsOffline() {
		/* Write comments, questions and answers */
		/* Check connection */
			
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		PushController pushCtrl = new PushController(getInstrumentation().getTargetContext());
		LocalDataManager ldm = new LocalDataManager(getInstrumentation().getTargetContext());
		ldm.deletePushQuestionsIDList();
		ldm.deletePushTuplelist();
		Question q1 = new Question("Question title", "Question body", "author");
		
		pc.addPushQuestion(q1);
		
		ArrayList<String> qIDList = ldm.loadPosts();
		assertTrue("The question ID is not saved properly", qIDList.contains(q1.getId()));
		//pushCtrl.addQuestionToServer(q1);
		
		pc.getQuestionsInstance().add(q1);
		Answer a1 = new Answer("My answer", "author","1");
		
		
		Comment c1 = new Comment("My comment", "author");
		pc.addPushAnsAndComm(q1.getId(), a1.getId(), c1);
		ArrayList<Tuple> tupleList = pc.getTupleForPush();
		assertEquals("The answer ID is not saved properly", tupleList.get(0).getAnswerID(), a1.getId());
		String body = tupleList.get(0).getComment().getCommentBody();
		assertEquals("The comment is not saved properly", body, c1.getCommentBody());
		
		ServerDataManager sdm = new ServerDataManager();
		sdm.deleteQuestion(q1.getId());
		
		
		// loop until you gain connectivity? I don't know
		
		//while(!pc.checkConnectivity()) {
	}

	// Tests to ensure that a question, answer or comment made is being
	// pushed to the server properly.  Done by pushing the question, and then
	// retrieving the same question from the server to ensure that the question
	// is actually being pushed up.
	public void testWritePostsOnline() {
	
		/* Write comments, questions and answers */
		/* Check connection */
		ServerDataManager sdm = new ServerDataManager();
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		PushController pushCtrl = new PushController(getInstrumentation().getTargetContext());

		Question q1 = new Question("Question title", "Question body", "author");
		pushCtrl.addQuestionToServer(q1);
		Question q2 = sdm.getQuestion(q1.getId());
		Log.d("Debug", "Question ID into server is: "+ q1.getId());
		assertEquals("The questions should be the same", q2.getId(), q1.getId());
		
		Answer answer = new Answer("Answer title", "Answer body", "author");
		Comment comment = new Comment("Comment body", "author");
		
		q1.addAnswer(answer);
		q1.addComment(comment);
		
		sdm.updateQuestion(q1);
		q2 = sdm.getQuestion(q1.getId());
		
		Answer a2 = q2.getAnswers().get(0);
		Comment c2 = q2.getComments().get(0);
		
		assertEquals("The questions should be the same", q2.getId(), q1.getId());
		assertEquals("The answers should be the same", a2.getId(), answer.getId());
		assertEquals("The comment should be the same", c2.getCommentBody(), comment.getCommentBody());
		
		sdm.deleteQuestion(q1.getId());
		
		//if (pc.checkConnectivity()) {
			/* save to server */
			
		//	pc.pushNewPosts();
			
			/* Assert that after pushing, pulling(loading user posts) returns a result */
			
		//	assertFalse("No posts pushed to server.", pc.loadServerPosts().equals(null));
		}
	}
