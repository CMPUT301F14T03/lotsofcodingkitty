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
		assertTrue("listofquestions should be empty", listofquestions.size() == 0);
	}
	
	//this is testing that the questionlist should not be empty
	public void testGetQuestions(){
		PostController controller = new PostController();
		ArrayList<Question> listofquestions = controller.getQuestionInstance();
		assertTrue("listofquestions not empty", listofquestions.size() > 0);
	}	
	
	public void testViewNumberOfAnswersToQuestion() {
		
		Question q1 = new Question();
		Answer a1 = new Answer();
		
		q1.addAnswer(a1);
		assertEquals(q1.getAnswers().size(),1);
	}
	
	public void testUpvoteQuestion() {
		
		PostController pc = new PostController();
		Question q1 = new Question();
		
		pc.addQuestion(q1);
		pc.upVote();
		
		assertEquals(q1.getRating(),1);
	}
	
	public void testUpvoteAnswer() {
		
		PostController pc = new PostController();
		Answer a1 = new Answer();
		
		pc.addAnswer(a1);
		pc.upVote();
		
		assertEquals(a1.getRating(),1);
	}
}
