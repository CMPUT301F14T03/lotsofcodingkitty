/*README: this is a basic test it is testing nothing really....
 * its basically useless...but it will eventually become useful
 * probably around proj3
 */


import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.Answer;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.PostController;
import ca.ualberta.cs.cmput301t03app.Question;
import android.test.ActivityInstrumentationTestCase2;


public class TestBrowseQuestions extends ActivityInstrumentationTestCase2<MainActivity> {
	public TestBrowseQuestions(){
		super(MainActivity.class);
	}
	
	// Creates a new postcontroller and an ArrayList<Question>, and then sets the array
	// to be the uninitiated question list (all values null)
	// Asserts that the size of the list is empty
	
	public void testEmptyQuestions(){
		PostController controller = new PostController();
		ArrayList<Question> listofquestions = controller.getQuestionInstance();
		assertTrue("Question list should be empty.", listofquestions.size() == 0);
	}
	
	// Creates a new postcontroller and a new Question, then adds the question to
	// the postcontrollers question list. Asserts that the size of the list returned by 
	// getQuestionInstance is larger than zero, which it should be given that
	// we've just added a question to it.
	
	public void testGetQuestions(){
		PostController controller = new PostController();
		Question q = new Question("Title", "body");
		controller.addQuestion(q);
		ArrayList<Question> listofquestions = controller.getQuestionInstance();
		assertTrue("Failed to get questions, question list empty.", listofquestions.size() > 0);
	}	
	
	// Creates an answer and postcontroller, then adds the question to the post controllers
	// answer list and then asserts that the size of the answer list is 1, which it should be
	// given that we just added exactly one answer to the answerlist
	
	// however I think the way we do this test should be related to testing the statistic function
	// that calculates the number of answers to a certain question
	// i.e creating question, adding answer children, having the function count
	// the number of expected children of that question
	
	public void testViewNumberOfAnswersToQuestion() {
		
		Answer a1 = new Answer();
		PostController q1 = new PostController();
		q1.addAnswer(a1);
		assertEquals("Not expected number of answers.", q1.getAnswers().size(),1);
	}
}
