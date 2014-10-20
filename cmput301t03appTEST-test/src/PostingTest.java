import ca.ualberta.cs.cmput301t03app.Answer;
import ca.ualberta.cs.cmput301t03app.Comment;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.PostController;
import ca.ualberta.cs.cmput301t03app.Question;
import android.test.ActivityInstrumentationTestCase2;


public class PostingTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
public PostingTest() {
		
		super(MainActivity.class);
	}

	// Creates a new post contrtoller and new question
	// Adds the question to the newly created post controller
	// Asserts that the question getter method of the post controller
	// returns the earlier created question

	public void testPostQuestion() {
		
		PostController pc = new PostController();
		Question q1 = new Question();
		pc.add(q1);
		
		assertEquals("Question not posted correctly.", pc.getQuestion(),q1);
	}
	
	// Same as above but with answer object
	
	public void testPostAnswer() {
		
		PostController pc = new PostController();
		Question q1 = new Question();
		Answer a1 = new Answer();
		q1.addAnswer(a1);
		
		assertEquals("Answer not posted correctly.",pc.getAnswer(),a1);
	}
	
	// Same as above but with comment object
	
	public void testPostCommentToQuestion() {
		

		PostController pc = new PostController();
		Comment c1 = new Comment("Hello World.");
		pc.addComment(c1);
		
		assertEquals("Comment not posted correctly.",pc.getComment(),c1);
	}
}
