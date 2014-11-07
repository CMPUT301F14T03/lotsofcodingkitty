import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Comment;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;


public class PostingTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
public PostingTest() {
		
		super(MainActivity.class);
	}

	// Creates a new post controller and new question
	// Adds the question to the newly created post controller
	// Asserts that the question getter method of the post controller
	// returns the earlier created question

	public void testPostQuestion() {
		
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		Question q1 = new Question("Title1","TextBody1", "author");
		pc.addQuestion(q1);
		
		assertEquals("Question not posted correctly.", pc.getQuestion(q1.getId()),q1);
	}
	
	// Same as above but with answer object
	
	public void testPostAnswer() {
		
		PostController pc = new PostController(getInstrumentation().getTargetContext());
		Question q1 = new Question("Title1","TextBody1", "author");
		Answer a1 = new Answer("answer", "author","1");
		q1.addAnswer(a1);
		pc.addQuestion(q1);
		Answer al = pc.getAnswer(a1.getId(), q1.getId());
		assertEquals("Answer not posted correctly.",al,a1);
	}
	
	//Makes an instance of post controller, adds a comment to a question and checks
	//to see if it does exist.
	
	public void testPostCommentToQuestion() {
		

		PostController pc = new PostController(getInstrumentation().getTargetContext());
		Question q1 = new Question("Title1","TextBody1", "author");
		pc.addQuestion(q1);
		Comment c1 = new Comment("Hello World.", "author2");
		pc.addCommentToQuestion(c1, q1.getId());
		
		assertEquals("Comment not posted correctly.",pc.getQuestion(q1.getId()).getComments().get(0),c1);
	}
	
	// Same as above but adds the comment to an answer
	public void testPostCommentToAnswer() {
		

		PostController pc = new PostController(getInstrumentation().getTargetContext());
		Question q1 = new Question("Title1","TextBody1", "author");
		pc.addQuestion(q1);
		Answer a1 = new Answer("Title1","TextBody1", "author");
		q1.addAnswer(a1);
		Comment c1 = new Comment("Hello World.", "author2");
		pc.addCommentToAnswer(c1, q1.getId(), a1.getId());
		Answer a2 = pc.getAnswer(a1.getId(), q1.getId());
		
		assertEquals("Comment not posted correctly.",a2.getComments().get(0),c1);
	}
}
