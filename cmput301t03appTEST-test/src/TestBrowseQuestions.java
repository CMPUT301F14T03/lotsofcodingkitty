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
	//this is testing that the question list should be empty
	public void testEmptyQuestions(){
		PostController controller = new PostController();
		ArrayList<Question> listofquestions = controller.getQuestionInstance();
		assertTrue("Question list should be empty.", listofquestions.size() == 0);
	}
	
	//this is testing that the questionlist should not be empty
	public void testGetQuestions(){
		PostController controller = new PostController();
		ArrayList<Question> listofquestions = controller.getQuestionInstance();
		assertTrue("Failed to get questions, question list empty.", listofquestions.size() > 0);
	}	
	
	public void testViewNumberOfAnswersToQuestion() {
		
		Question q1 = new Question();
		Answer a1 = new Answer();
		
		q1.addAnswer(a1);
		assertEquals("Not expected number of answers.", q1.getAnswers().size(),1);
	}
}
