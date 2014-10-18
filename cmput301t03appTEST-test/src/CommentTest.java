import ca.ualberta.cs.cmput301t03app.Answer;
import ca.ualberta.cs.cmput301t03app.Comment;
import ca.ualberta.cs.cmput301t03app.PostController;
import ca.ualberta.cs.cmput301t03app.Question;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.UserPostCollector;
import android.test.ActivityInstrumentationTestCase2;

public class CommentTest extends ActivityInstrumentationTestCase2<MainActivity> {

	/*
	* View newest comment by default  User, Author
  	* Write comments, questions and answers offline  Author
  	* Push comments, questions and answers when connected  Author
  	* Write comments, questions and answers
	* */
	
	
	public CommentTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	public void testPullFromServer() {
		PostController pc = new PostController();
		while (!pc.checkConnectivity()) {
		}
		Object posts = new Object();
		posts = pc.loadServerPosts();
		assertNotSame(posts, null);
	}
	
	public void testPushToServer() {
		
		PostController pc = new PostController();
		Question question = new Question("Question title", "Question body");
		Object posts = new Object();
		pc.addQuestion(question);
		while (!pc.checkConnectivity()) {
		}
		pc.saveServerPosts();
		posts = pc.loadServerPosts();
		assertEquals(posts, question);
	}
	
	public void testWritePostsOffline() {
		/* Write comments, questions and answers */
		
		/* Check connection */
		
		
			
		PostController pc = new PostController();
		Question q1 = new Question("My question", "My question body");
		
		pc.addQuestion(q1);
		
		Answer a1 = new Answer("My answer");
		
		pc.addAnswer(a1);
		
		Comment c1 = new Comment("My comment");
		
		
		// loop until you gain connectivity? I don't know
		
		while(!pc.checkConnectivity()) {
		}
		
		/* save to server */
		
		pc.pushNewPosts();
		
		/* Assert that after pushing, pulling(loading user posts) returns a result */
		
		assertFalse(pc.loadUserPosts().equals(null));
	}
		
	public void testWritePostsOnline() {
		/* Write comments, questions and answers */
		
		/* Check connection */
		
		PostController pc = new PostController();
		Question q1 = new Question("My question", "My question body");
		
		pc.addQuestion(q1);
		
		Answer a1 = new Answer("My answer");
		
		pc.addAnswer(a1);
		
		Comment c1 = new Comment("My comment");
		
		if (pc.checkConnectivity()) {
			/* save to server */
			
			pc.pushNewPosts();
			
			/* Assert that after pushing, pulling(loading user posts) returns a result */
			
			assertFalse(pc.loadUserPosts().equals(null));
		}
	}
	
}
