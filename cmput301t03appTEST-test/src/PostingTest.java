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

	// Creates a new post controller and new question
	// Adds the question to the newly created post controller
	// Asserts that the question getter method of the post controller
	// returns the earlier created question

	public void testPostQuestion() {
		
		String Id;
		PostController pc = new PostController();
		Question q1 = new Question("Title1","TextBody1", "author");
		Id = q1.getId();
		pc.addQuestion(q1);
		
		assertEquals("Question not posted correctly.", pc.getQuestion(Id),q1);
	}
	
	// Same as above but with answer object
	
	public void testPostAnswer() {
		
		String Id;
		PostController pc = new PostController();
		Answer a1 = new Answer("answer", "author","1");
		Id = a1.getId();
		pc.addAnswer(a1);
		
		assertEquals("Answer not posted correctly.",pc.getAnswer(Id),a1);
	}
	
	// Same as above but with comment object
	
	public void testPostCommentToAnswer() {
		
		Answer a1 = new Answer(null, null, null);
		Comment c1 = new Comment("Hello World.");
		a1.addComment(c1);
		
		assertEquals("Comment not posted correctly.", a1.getComments().get(0),c1);
	}
	
	public void testPostCommentToQuestion() {
		
		Question q1 = new Question(null, null, null);
		Comment c1 = new Comment("Hello World.");
		q1.addComment(c1);
		
		assertEquals("Comment not posted correctly.",q1.getComments().get(0),c1);
	}
}
