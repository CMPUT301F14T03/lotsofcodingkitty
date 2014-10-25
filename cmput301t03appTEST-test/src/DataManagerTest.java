import java.util.ArrayList;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.PostController;
import ca.ualberta.cs.cmput301t03app.Question;
import ca.ualberta.cs.cmput301t03app.ServerDataManager;
import ca.ualberta.cs.cmput301t03app.UserPostCollector;
import ca.ualberta.cs.cmput301t03app.iDataManager;


public class DataManagerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	/*Cache read questions and answers 	
	 * Allows the user save read questions/answers and questions/answers 
	 * marked as read to be automatically saved to local cache */
	public DataManagerTest() {
		
		super(MainActivity.class);
	}
	
	// This test will need to be changed to reflect our new UML.
	
	public void testSaveLoadQuestionList() {		
		//testing if question posted saved to cache
		iDataManager dataManager = new ServerDataManager();
		ArrayList<Question> q = new ArrayList<Question>();
		Question q1 = new Question("Title1","TextBody1", "author");
		Question q2 = new Question("Title1","TextBody1", "author");
		q.add(q1);
		q.add(q2);
		
		
		dataManager.save(q);
		
		Object newQuestionArray;
		
		newQuestionArray = dataManager.load();
		assertNotNull("No questions loaded.",newQuestionArray);
	}
	
	// Creates a new post controller and adds a question into the controller
	// Asserts that the questions returned from pc.getQuestionInstance is not an empty list,
	// which it should not be given that we have added a question to it.
	
	public void testBrowseQuestions() {
		
		PostController pc = new PostController();
		Question q = new Question("Title1","TextBody1", "author");
		pc.addQuestion(q);
		assertNotNull("No questions found.", pc.getQuestionInstance());
	}
	
	
	// Asserts that when a question is added to the favorite questions array
	// and then when the function getFavoriteQuestions() is called, it will
	// return a corresponding array of all specific favorited questions.
	// This test is also already in another class already,
	// should delete one of these.
	
	public void testSaveAndLoadFromCache(){
		
		ArrayList<Question> questionArray;
		UserPostCollector userPostCollector = new UserPostCollector(getInstrumentation().getTargetContext());
		ArrayList<Question> ql = new ArrayList<Question>();
		
		Question q = new Question("This is a test question for caching a favorite question.","This is some random text to fill out the textbody.", "Tonberry");	
		ql.add(q);
		
		userPostCollector.addFavoriteQuestions(q);
		
		questionArray = userPostCollector.getFavoriteQuestions();
		
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
