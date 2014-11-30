import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.controllers.PushController;
import ca.ualberta.cs.cmput301t03app.datamanagers.ServerDataManager;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

public class CommentTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	
	public CommentTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	// Test creates a new PostController object, infinitely loops until
	// connectivity is determined, then attempts to pull a posts object
	// from the server.
	
	public void testPullFromServer() {
		}
		//Object posts = new Object();
		//posts = pc.loadServerPosts();
		//assertNotSame("No posts loaded from server.", posts, null);
	
	// Test creates a new post object, waits for server connectivity,
	// calls the postcontroller to save the object to the server
	// and then loads the pushed object. Asserts that loaded object
	// and posted object are the same.
	
	public void testPushToServer() {
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		Question question = new Question("Question title", "Question body", "author");
		PushController pushCtrl = new PushController(getInstrumentation().getTargetContext());
		pushCtrl.addQuestionToServer(question);
		ServerDataManager sdm = new ServerDataManager();
		sdm.deleteQuestion(question.getId());
		//while (!pc.checkConnectivity()) {
		}
		//pc.pushNewPosts();
		//posts = pc.loadServerPosts();
		//assertEquals("Retrieved server questions not same and saved questions.", posts, question);
	
	public void testWritePostsOffline() {
		/* Write comments, questions and answers */
		/* Check connection */
			
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		PushController pushCtrl = new PushController(getInstrumentation().getTargetContext());

		Question q1 = new Question("Question title", "Question body", "author");
		
		/*
		 * This method requires connectivity.  Contradicts what your test method is trying to do.  Please double check.
		 */
		pushCtrl.addQuestionToServer(q1);
		
		pc.getQuestionsInstance().add(q1);
		Answer a1 = new Answer("My answer", "author","1");
		
		/*
		 * Please check the post controller for the method you want.  I'm not sure what you're trying to test here, as there is no assertion.  I'm assuming you want
		 * addPushAnsAndComm(String qID, String aID, Comment comment), in which case you need to make the comment first and then add everything all at once.  This
		 * comment applies the the 3 lines below (one is commented out).
		 */
		//pc.addAnswer(a1, q1.getId());	
		Comment c1 = new Comment("My comment", "author");
		a1.addComment(c1);
		
		ServerDataManager sdm = new ServerDataManager();
		sdm.deleteQuestion(q1.getId());
		
		
		// loop until you gain connectivity? I don't know
		
		//while(!pc.checkConnectivity()) {
	}
		
		/* save to server */
		
		//pc.pushNewPosts();
		
		/* Assert that after pushing, pulling(loading user posts) returns a result */
		
		//assertFalse("No posts pushed to server.", pc.loadServerPosts().equals(null));

	public void testWritePostsOnline() {
		
		/* Write comments, questions and answers */
		/* Check connection */
		
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		PushController pushCtrl = new PushController(getInstrumentation().getTargetContext());

		Question q1 = new Question("Question title", "Question body", "author");
		pushCtrl.addQuestionToServer(q1);
		pc.getQuestionsInstance().add(q1);
		Answer a1 = new Answer("My answer", "author","2");
		
		/*
		 * Please check the push controller for the method you want.  Again, I'm assuming off the test suite name, as I see no assertion.
		 * I think the method you are looking for is answerAQuestionToServer(Answer answer, String qID).  I commented out the line
		 */
		//pc.addAnswer(a1, q1.getId());
		
		/*
		 * I don't think this is the method you want -- this will simply add the comment to the answer object.  If you're trying to push it to the server,
		 * you'll want to use the push controller method: commentAnAnswerToServer(Comment comment, String aID, String qID)
		 * This will add the comment to the corresponding answer (and checks for the corresponding question the answer belongs to).
		 */
		Comment c1 = new Comment("My comment", "author");
		a1.addComment(c1);
		
		ServerDataManager sdm = new ServerDataManager();
		sdm.deleteQuestion(q1.getId());
		
		//if (pc.checkConnectivity()) {
			/* save to server */
			
		//	pc.pushNewPosts();
			
			/* Assert that after pushing, pulling(loading user posts) returns a result */
			
		//	assertFalse("No posts pushed to server.", pc.loadServerPosts().equals(null));
		}
	}
