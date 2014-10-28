import ca.ualberta.cs.cmput301t03app.Answer;
import ca.ualberta.cs.cmput301t03app.Comment;
import ca.ualberta.cs.cmput301t03app.PostController;
import ca.ualberta.cs.cmput301t03app.Question;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.UserPostCollector;
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
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		while (!pc.checkConnectivity()) {
		}
		Object posts = new Object();
		posts = pc.loadServerPosts();
		assertNotSame("No posts loaded from server.", posts, null);
	}
	
	// Test creates a new post object, waits for server connectivity,
	// calls the postcontroller to save the object to the server
	// and then loads the pushed object. Asserts that loaded object
	// and posted object are the same.
	
	public void testPushToServer() {
		
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		Question question = new Question("Question title", "Question body", "author");
		Object posts = new Object();
		pc.addQuestion(question);
		while (!pc.checkConnectivity()) {
		}
		pc.pushNewPosts();
		posts = pc.loadServerPosts();
		assertEquals("Retrieved server questions not same and saved questions.", posts, question);
	}
	
	public void testWritePostsOffline() {
		/* Write comments, questions and answers */
		/* Check connection */
			
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		Question q1 = new Question("Question title", "Question body", "author");
		pc.addQuestion(q1);
		Answer a1 = new Answer("My answer", "author","1");
		pc.addAnswer(a1);
		Comment c1 = new Comment("My comment");
		
		
		// loop until you gain connectivity? I don't know
		
		while(!pc.checkConnectivity()) {
		}
		
		/* save to server */
		
		pc.pushNewPosts();
		
		/* Assert that after pushing, pulling(loading user posts) returns a result */
		
		assertFalse("No posts pushed to server.", pc.loadServerPosts().equals(null));
	}
		
	public void testWritePostsOnline() {
		
		/* Write comments, questions and answers */
		/* Check connection */
		
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		Question q1 = new Question("Question title", "Question body", "author");
		pc.addQuestion(q1);
		Answer a1 = new Answer("My answer", "author","2");
		pc.addAnswer(a1);
		Comment c1 = new Comment("My comment");
		
		if (pc.checkConnectivity()) {
			/* save to server */
			
			pc.pushNewPosts();
			
			/* Assert that after pushing, pulling(loading user posts) returns a result */
			
			assertFalse("No posts pushed to server.", pc.loadServerPosts().equals(null));
		}
	}
	
}
