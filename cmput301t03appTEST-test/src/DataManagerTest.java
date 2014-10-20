import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.DataManager;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.PostController;
import ca.ualberta.cs.cmput301t03app.Question;
import ca.ualberta.cs.cmput301t03app.UserPostCollector;


public class DataManagerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	/*Cache read questions and answers 	
	 * Allows the user save read questions/answers and questions/answers 
	 * marked as read to be automatically saved to local cache */
	public DataManagerTest() {
		
		super(MainActivity.class);
	}
	
	// This test will need to be changed to reflect our new UML.
	
	public void testSaveLoadQuestionList() {		
		
		DataManager dataManager = new DataManager();
		ArrayList<Question> q = new ArrayList<Question>();
		Question q1 = new Question("Title1","TextBody1");
		Question q2 = new Question("Title2","TextBody2");
		
		q.add(q1);
		q.add(q2);
		
		
		dataManager.localSave();
		
		Object newQuestionArray;
		
		newQuestionArray = dataManager.localLoad();
		assertNotNull("No questions loaded.",newQuestionArray);
	}
	
	// Creates a new post controller and adds a question into the controller
	// Asserts that the questions returned from pc.getQuestionInstance is not an empty list,
	// which it should not be given that we have added a question to it.
	
	public void testBrowseQuestions() {
		
		PostController pc = new PostController();
		Question q = new Question("Title", "Body");
		pc.addQuestion(q);
		assertNotNull("No questions found.", pc.getQuestionInstance());
	}
	
	
	// Asserts that when a question is added to the favorite questions array
	// and then when the function getFavoriteQuestions() is called, it will
	// return a corresponding array of all specific favorited questions.
	// This question is also already in another class already,
	// should delete one of these.
	
	public void testSaveFavorites(){
		
		
		ArrayList<Question> questionArray;
		UserPostCollector userCollect = new UserPostCollector();
		ArrayList<Question> ql = new ArrayList<Question>();
		
		Question q = new Question("Title1","TextBody1");
		
		ql.add(q);
		
		userCollect.addFavoriteQuestions(q);
		
		questionArray = userCollect.getFavoriteQuestions();
		
		assertEquals("Favorites not saved.", ql, questionArray);
		
	}
	
	
	
	
	// Kind of an important test case
	// should probably get this one coded
	
	
//	public void testCachedAnswersAndQuestions(){
//
//		DataManager dm = new DataManager();
//		ArrayList<Answer> cachedAnswers=new ArrayList<Answer>();
//		ArrayList<Question> cachedQuestions=new ArrayList<Question>();
//		cachedAnswers=dm.localLoadAnswers();
//		cachedQuestions=dm.localLoadQuestions();
//		assertTrue("There are no cached answers",cachedAnswers.size()>0);
//		assertTrue("There are no cached questions",cachedQuestions.size()>0);
//		
//	}
}
