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
	
	/** 
	 * Asserts that when a question is added to the favorite questions array
	 * and then when the function getFavoriteQuestions() is called, it will
	 * return a corresponding array of all specific favorited questions.
	 * This test is also already in another class already,
	 * should delete one of these.
	 * 
	 */
	public void testSuccessfulSavingAndLoadingFromCache(){
		
		ArrayList<Question> questionArray;
		UserPostCollector userPostCollector = new UserPostCollector(getInstrumentation().getTargetContext());
		ArrayList<Question> ql = new ArrayList<Question>();
		
		Question q = new Question("This is a test question for caching a favorite question.","This is some random text to fill out the textbody.", "Tonberry");	
		Question q2 = new Question("This is a another test question for caching a favorite question.","This is some random text to fill out the textbody.", "Tonberry");
		ql.add(q);
		ql.add(q2);
		
		userPostCollector.addFavoriteQuestions(q);	
		userPostCollector.addFavoriteQuestions(q2);	
		questionArray = userPostCollector.getFavoriteQuestions();
		
		for (int i = 0; i<ql.size(); i++) {
			assertEquals("Favorites not saved.", ql.get(i).getAuthor(), questionArray.get(i).getAuthor());
			assertEquals("Favorites not saved.", ql.get(i).getBody(), questionArray.get(i).getBody());
			assertEquals("Favorites not saved.", ql.get(i).getRating(), questionArray.get(i).getRating());
			assertEquals("Favorites not saved.", ql.get(i).getSubject(), questionArray.get(i).getSubject());
			assertEquals("Favorites not saved.", ql.get(i).getAnswers(), questionArray.get(i).getAnswers());
			assertEquals("Favorites not saved.", ql.get(i).getComments(), questionArray.get(i).getComments());
			assertEquals("Favorites not saved.", ql.get(i).getDate().getTime(), questionArray.get(i).getDate().getTime());
			assertEquals("Favorites not saved.", ql.get(i).getPicture(), questionArray.get(i).getPicture());
		}
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
		
		
		dataManager.save(q);
		
		Object newQuestionArray;
		
		newQuestionArray = dataManager.load();
		assertNotNull("No questions loaded.",newQuestionArray);
	}
}
