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
	
	ArrayList<Question> questionArray;
	PostController postController;
	ArrayList<Question> ql;
	Question q;
	Question q2;
	public DataManagerTest() {
		
		super(MainActivity.class);
	}
	
	/** 
	 * This test uses the favoriteQuestions list to test the saving and loading
	 *  Asserts all questions have the same content.
	 */
	public void testSuccessfulSavingAndLoadingFavoritesFromCache() {
		
		setup();
		
		postController.addFavoriteQuestion(q);
		postController.addFavoriteQuestion(q2);
		questionArray = postController.getFavoriteQuestions();
		
		checkCorrect();
	}
	
	/** 
	 * This test uses the readQuestions list to test the saving and loading
	 * Asserts all questions have the same content.
	 */
	public void testSuccessfulSavingAndLoadingReadFromCache() {
		
		setup();
		
		postController.addReadQuestion(q);
		postController.addReadQuestion(q2);
		questionArray = postController.getReadQuestions();
		
		checkCorrect();
	}
	
	/** 
	 * This test uses the readQuestions list to test the saving and loading
	 * Asserts all questions have the same content.
	 */
	public void testSuccessfulSavingAndLoadingToReadFromCache() {
		
		setup();
		
		postController.addToRead(q);
		postController.addToRead(q2);
		questionArray = postController.getToRead();
		
		checkCorrect();
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
	
	private void setup() {
		
		postController = new PostController(getInstrumentation().getTargetContext());
		ql = new ArrayList<Question>();
		
		q = new Question("This is a test question for caching a favorite question.","This is some random text to fill out the textbody.", "Tonberry");	
		q2 = new Question("This is a another test question for caching a favorite question.","This is some random text to fill out the textbody.", "Tonberry");
		ql.add(q);
		ql.add(q2);
	}
	
	private void checkCorrect() {
		assertNotNull("Loaded array is not empty", questionArray);
		assertEquals("Loaded array is the same size as ql", questionArray.size(), ql.size());
		assertSame("The first index of loaded array has the same question as the first question added", q, questionArray.get(0));
		assertSame("The second index of the loaded array has the same question as the second question added", q2, questionArray.get(1));
		assertEquals("The loaded array is the same as the area saved", ql, questionArray);
		//assertEquals("Questions saved not same as questions retrieved.", ql, questionArray);
	}
}
