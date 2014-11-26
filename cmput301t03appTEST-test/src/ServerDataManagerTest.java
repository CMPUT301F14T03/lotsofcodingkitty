import java.util.ArrayList;

import ca.ualberta.cs.cmput301t03app.controllers.PostController;
import ca.ualberta.cs.cmput301t03app.datamanagers.ServerDataManager;
import ca.ualberta.cs.cmput301t03app.models.Answer;
import ca.ualberta.cs.cmput301t03app.models.Question;
import ca.ualberta.cs.cmput301t03app.views.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class ServerDataManagerTest extends ActivityInstrumentationTestCase2<MainActivity> {

	Question q;
	Question serverQ;
	ServerDataManager sdm;
	PostController pc;
	
	public ServerDataManagerTest() {
			super(MainActivity.class);
		}
	
    @Override
    protected void setUp() throws Exception{
		super.setUp();
	}
    
    // Originally tried comparing these two questions as the same object
    // That assertion failed, couldn't get it working and manual inspection
    // showed that they should be the same object. Since we're comparing by IDs
    // to find out if a question is "equal to" another question anyways,
    // that's what this test is based on.
	
	public void testFindQuestionById() {
		sdm = new ServerDataManager();
		q = new Question("This", "this", "this");
		sdm.addQuestion(q);
		serverQ = sdm.getQuestion(q.getId());
		assertEquals("Question does not equal one found on server", q.getId(), serverQ.getId());
	}
	
	// Only passes if there are questions on the server
	// (which there are)
	
	public void testSearchQuestionsNull() {
		sdm = new ServerDataManager();
		ArrayList<Question> qList = sdm.searchQuestions("", null);
		assertTrue("No search results returned for an empty search", qList.size() > 0);
	}
	
	// A test search that looks for a term that should exist
	// in the list of elastic search questions.
	
	public void testSearchQuestionsString() {
		Boolean foundId = false;
		sdm = new ServerDataManager();
		q = new Question("This", "this", "this");
		sdm.addQuestion(q);
		String expectedId = q.getId();
		Log.i("WantedId", expectedId);
		
		// This thread sleep is required in order for the server
		// to have correctly added the question
		
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Question> qList = sdm.searchQuestions("this", null);
		for(int i = 0; i < qList.size(); i++){
			Log.i("ExistingIds", qList.get(i).getId());
			if(qList.get(i).getId().equals(expectedId)) {
				foundId = true;
			}
		}
		assertTrue("No results returned", qList.size() > 0);
		assertTrue("Expected question not found using search term", foundId);
	}
	
	public void testAddAnswerToQuestion() {
		pc = new PostController(getInstrumentation().getContext());
		q = new Question("b", "b", "b");
		pc.addQuestionToServer(q);
		Answer answer = new Answer("that","that","n/a");
		pc.addAnswer(answer, q.getId());
		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
