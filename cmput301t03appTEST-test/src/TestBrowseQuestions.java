

import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
/**
 * This tests that the user is able to browse questions
 * and that the post controller is working.
 * @category Unit Testing
 */

public class TestBrowseQuestions extends ActivityInstrumentationTestCase2<MainActivity> {
	public TestBrowseQuestions(){
		super(MainActivity.class);
	}

	
	/**
	 * This tests that the postcontroller is adding a question to the question 
	 * list it holds.
	 * @throws InterruptedException 
	 */
	
	public void testGetQuestions() throws InterruptedException{
		PostController controller = new PostController(getInstrumentation().getTargetContext());
		Question q = new Question("Title1","TextBody1", "author");
		controller.getQuestionsInstance().add(q);
		Thread.sleep(2000);
		assertTrue("Failed to get questions, question list empty.", controller.getQuestionsInstance().size() > 0);
	}	
	
	// Creates a question and then creates an arbitrary amount of answers (it is currently 35)
	// and then assigns them as children of the question
	// The test then asserts that the counting method (size() of the arraylist of answers)
	// returns the expected number of children
	/**
	 * This test that the answers are being added 35 times when 
	 * an answer is created and added to that question
	 */
	public void testViewNumberOfAnswersToQuestion() {
		
		Question q1 = new Question("Title1","TextBody1", "author");
		for(int j = 0; j < 35; j++){
			Answer answer = new Answer("title", "author","1");
			q1.addAnswer(answer);
		}
		assertEquals("Not expected number of answers.", q1.countAnswers(),35);
	}
}
