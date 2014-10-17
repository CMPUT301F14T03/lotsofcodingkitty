import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.cmput301t03app.MainActivity;
import ca.ualberta.cs.cmput301t03app.Question;
import ca.ualberta.cs.cmput301t03app.QuestionList;
import ca.ualberta.cs.cmput301t03app.ServerDatabase;
import android.test.ActivityInstrumentationTestCase2;


public class SearchTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public SearchTest() {
		super(MainActivity.class);
	}
	
	public void testSearch() {
		
		String searchWord = "test";
		QuestionList ql = new QuestionList();
		ArrayList<Question> searchResults = new ArrayList<Question>();
		ServerDatabase sdb = new ServerDatabase();
		
		//Create Questions and add to the array
		Date date = new Date(14, 0, 28);
		Question q1 = new Question(date,"This is test question?","TextBody1");
		Question q2 = new Question(date,"This question should not be returned?","TextBody2");
		
		ql.addQuestion(q1);
		ql.addQuestion(q2);
		
		List<Question> questionArray = ql.getQuestionList();
		
		//Save questionArray into the server
		
		searchResults = sbd.search(searchWord); //Searches for searchWord in the list of questions in the server
		assertNotNull(searchResults);
		
		Question q3 = searchResults.get(0);
		assertEquals(q3.getSubject(),q1.getSubject());
		
		
	}

}
