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


	public void testPostQuestion() {
		
		PostController pc = new PostController();
		Question q1 = new Question();
		pc.getQuestionInstance().add(q1);
		
		assertEquals(pc.getQuestion(),q1);
	}
	
	public void testPostAnswer() {
		
		PostController pc = new PostController();
		Question q1 = new Question();
		Answer a1 = new Answer();
		q1.addAnswer(a1);
		
		assertEquals(pc.getAnswer(),a1);
	}
	
	public void testPostCommentToQuestion() {
		

		PostController pc = new PostController();
		Comment c1 = new Comment("Hello World.");
		pc.addComment(c1);
		
		assertEquals(pc.getComment(),c1);
	}
}
