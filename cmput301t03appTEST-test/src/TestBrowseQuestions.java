/*README: this is a basic test it is testing nothing really....
 * its basically useless...but it will eventually become useful
 * probably around proj3
 */


import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;
import android.test.ActivityInstrumentationTestCase2;


public class TestBrowseQuestions extends ActivityInstrumentationTestCase2<MainActivity> {
	public TestBrowseQuestions(){
		super(MainActivity.class);
	}
	
	// Creates a new postcontroller and an ArrayList<Question>, and then sets the array
	// to be the uninitiated question list (all values null)
	// Asserts that the size of the list is empty
	
	public void testEmptyQuestions(){
		PostController controller = new PostController(getInstrumentation().getTargetContext());
		ArrayList<Question> listofquestions = controller.getQuestionsInstance();
		assertTrue("Question list should be empty.", listofquestions.size() == 0);
	}
	
	// Creates a new postcontroller and a new Question, then adds the question to
	// the postcontrollers question list. Asserts that the size of the list returned by 
	// getQuestionInstance is larger than zero, which it should be given that
	// we've just added a question to it.
	
	public void testGetQuestions(){
		PostController controller = new PostController(getInstrumentation().getTargetContext());
		Question q = new Question("Title1","TextBody1", "author");
		controller.addQuestion(q);
		ArrayList<Question> listofquestions = controller.getQuestionsInstance();
		assertTrue("Failed to get questions, question list empty.", listofquestions.size() > 0);
	}	
	
	// Creates a question and then creates an arbitrary amount of answers (it is currently 35)
	// and then assigns them as children of the question
	// The test then asserts that the counting method (size() of the arraylist of answers)
	// returns the expected number of children
	
	public void testViewNumberOfAnswersToQuestion() {
		
		Question q1 = new Question("Title1","TextBody1", "author");
		for(int j = 0; j < 35; j++){
			Answer answer = new Answer("title", "author","1");
			q1.addAnswer(answer);
		}
		assertEquals("Not expected number of answers.", q1.countAnswers(),35);
	}
}
