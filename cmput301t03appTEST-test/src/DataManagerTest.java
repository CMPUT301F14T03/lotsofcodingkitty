import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.PostController;
import ca.ualberta.cs.cmput301t03app.Question;
import ca.ualberta.cs.cmput301t03app.ServerDataManager;
import ca.ualberta.cs.cmput301t03app.UserPostCollector;
import ca.ualberta.cs.cmput301t03app.iDataManager;

/**
 * Tests all saving and loading methods in both the LocalDataManager
 * and the ServerDataManager.
 */

public class DataManagerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	public DataManagerTest() {
		
		super(MainActivity.class);
	}
	
	/** 
	 * This test uses the favoriteQuestions list to test the saving and loading
	 * functionality to cache. This works with any other questions list in the
	 * UserPostCollector. Asserts all questions have the same content.
	 */
	public void testSuccessfulSavingAndLoadingFromCache() {
		
		ArrayList<Question> questionArray;
		PostController postController = new PostController(getInstrumentation().getTargetContext());
//		UserPostCollector userPostCollector = postController.getUPCInstance();
		ArrayList<Question> ql = new ArrayList<Question>();
		
		Question q = new Question("This is a test question for caching a favorite question.","This is some random text to fill out the textbody.", "Tonberry");	
		Question q2 = new Question("This is a another test question for caching a favorite question.","This is some random text to fill out the textbody.", "Tonberry");
		ql.add(q);
		ql.add(q2);
		
		postController.addFavoriteQuestion(q);
		postController.addFavoriteQuestion(q2);
		questionArray = postController.getFavoriteQuestions();
		
		assertEquals("Questions saved not same as questions retrieved.", ql, questionArray);
	}
	
	/**
	 * This test will need to be changed to reflect our new UML.	
	 * 
	 */
	public void testSuccessfulSavingAndLoadingToServer() {		
		//testing if question posted saved to cache
		iDataManager dataManager = new ServerDataManager();
		ArrayList<Question> q = new ArrayList<Question>();
		Question q1 = new Question("Title1","TextBody1", "author");
		Question q2 = new Question("Title1","TextBody1", "author");
		q.add(q1);
		q.add(q2);
		
		
		dataManager.saveToQuestionBank(q);
		
		Object newQuestionArray;
		
		newQuestionArray = dataManager.loadQuestions();
		assertNotNull("No questions loaded.",newQuestionArray);
	}
}
