import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;

/**
 * This tests the simple posting methods of the postcontroller ie. post
 * comments, answers, and questions.
 * @author rdejesus
 * @category Unit Testing
 * 
 */

public class PostingTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public PostingTest() {

		super(MainActivity.class);
	}

	/**
	 * Testing that when a question is added by the postcontroller, it is adding
	 * that question properly.
	 * <br>
	 * Also testing that getter methods are working.
	 */
	// Creates a new post controller and new question
	// Adds the question to the newly created post controller
	// Asserts that the question getter method of the post controller
	// returns the earlier created question

	public void testPostQuestion() {

		PostController pc = new PostController(getInstrumentation()
				.getTargetContext());
		Question q1 = new Question("Title1", "TextBody1", "author");
		pc.addQuestion(q1);

		assertEquals("Question not posted correctly.",
				pc.getQuestion(q1.getId()), q1);
	}

	/**
	 * Testing that when an answer is added by the postcontroller it is adding
	 * that answer properly.
	 * 
	 * Testing that getter methods are working.
	 */
	// Same as above but with answer object

	public void testPostAnswer() {

		PostController pc = new PostController(getInstrumentation()
				.getTargetContext());
		Question q1 = new Question("Title1", "TextBody1", "author");
		Answer a1 = new Answer("answer", "author", "1");
		q1.addAnswer(a1);
		pc.addQuestion(q1);
		Answer al = pc.getAnswer(a1.getId(), q1.getId());
		assertEquals("Answer not posted correctly.", al, a1);
	}

	// Makes an instance of post controller, adds a comment to a question and
	// checks
	// to see if it does exist.

	/**
	 * Testing that when an comment is added by the postcontroller to a Question it is adding
	 * that comment properly.
	 * 
	 * Testing that getter methods are working
	 * 
	 */
	public void testPostCommentToQuestion() {

		PostController pc = new PostController(getInstrumentation()
				.getTargetContext());
		Question q1 = new Question("Title1", "TextBody1", "author");
		pc.addQuestion(q1);
		Comment c1 = new Comment("Hello World.", "author2");
		pc.addCommentToQuestion(c1, q1.getId());

		assertEquals("Comment not posted correctly.", pc
				.getQuestion(q1.getId()).getComments().get(0), c1);
	}

	
	/**
	 * Testing that when a comment is added by the postcontroller to an Answer it is adding
	 * that comment properly.
	 * 
	 * Testing that getter methods are working
	 */
	public void testPostCommentToAnswer() {

		PostController pc = new PostController(getInstrumentation()
				.getTargetContext());
		Question q1 = new Question("Title1", "TextBody1", "author");
		pc.addQuestion(q1);
		Answer a1 = new Answer("Title1", "TextBody1", "author");
		q1.addAnswer(a1);
		Comment c1 = new Comment("Hello World.", "author2");
		pc.addCommentToAnswer(c1, q1.getId(), a1.getId());
		Answer a2 = pc.getAnswer(a1.getId(), q1.getId());

		assertEquals("Comment not posted correctly.", a2.getComments().get(0),
				c1);
	}
}
